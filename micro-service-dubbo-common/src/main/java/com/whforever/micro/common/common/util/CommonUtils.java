/**
 * 
 */
package com.whforever.micro.common.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author robin
 * @data
 * @content
 */
public class CommonUtils {

    public static final int GUID_LENGTH = 16;
    private static Log logger = LogFactory.getLog(CommonUtils.class);

    /**
     * objectGUID转成string.
     *
     * @param guid objectGUID
     * @return string
     */
    public static String getGuid(Object guid) {
        byte[] guids = (byte[]) guid;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < GUID_LENGTH; i++) {
            str.append(addZero(guids[i] & 0xFF));
            if (i == 3 || i == 5 || i == 7 || i == 9) {
                str.append("-");
            }
        }
        return str.toString();
    }

    /**
     * 压缩文件的工具类.
     *
     * @param logPath logPath.
     * @throws IOException IOException.
     */
    public static String compressedFile(String logPath, String zipPath) throws IOException {

        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        ZipOutputStream zos = null;

        int index = logPath.lastIndexOf("/");
        String realPath = logPath.substring(0, index);

        File sourceFile = new File(realPath);
        if (sourceFile.exists() == false) {
            logger.info("待压缩的文件目录：" + realPath + "不存在.");
        } else {

            File zipFile = new File(zipPath);
            File[] sourceFiles = sourceFile.listFiles();
            if (null == sourceFiles || sourceFiles.length < 1) {
                logger.info("待压缩的文件目录：" + realPath + "里面不存在文件，无需压缩.");
            } else {
                fos = new FileOutputStream(zipFile);
                zos = new ZipOutputStream(new BufferedOutputStream(fos));
                byte[] bufs = new byte[1024 * 10];
                for (int i = 0; i < sourceFiles.length; i++) {
                    // 创建ZIP实体，并添加进压缩包
                    ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
                    zos.putNextEntry(zipEntry);
                    // 读取待压缩的文件并写进压缩包里
                    fis = new FileInputStream(sourceFiles[i]);
                    bis = new BufferedInputStream(fis, 1024 * 10);
                    int read = 0;
                    while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                        zos.write(bufs, 0, read);
                    }
                }
            }
        }
        // 关闭流
        if (null != bis) {
            bis.close();
        }

        if (null != zos) {
            zos.close();
        }
        return zipPath;
    }

    /**
     * 使用递归，输出指定文件夹内的包含指定时间戳的文件名.
     *
     * @param path 文件夹路径.
     * @param timestamp 时间戳.
     */
    public static List<String> getFileNamesByTimestamp(String path, String timestamp) {
        // 获得指定文件对象
        File file = new File(path);
        // 获得该文件夹内的所有文件
        File[] array = file.listFiles();

        List<String> fileNames = new ArrayList<>();

        for (int i = 0; i < array.length; i++) {
            if (array[i].isFile()) {

                // 若是文件.
                if (array[i].getName().contains(timestamp)) {
                    fileNames.add(array[i].getName());
                }
            } else if (array[i].isDirectory()) {
                // 若是文件夹.
                continue;
            }
        }
        return fileNames;
    }

    /**
     * 复制一个文件夹下的所有文件到另一个文件中.
     *
     * @param sourcePath 源文件.
     * @param path 目的文件.
     * @throws IOException IOException.
     */
    public static List<String> copyDir(String sourcePath, String path) throws IOException {
        File file = new File(sourcePath);
        String[] filePath = file.list();
        List<String> fileNames = new ArrayList<String>();

        File targetFile = new File(path);

        if (!targetFile.exists() && !targetFile.isDirectory()) {
            targetFile.mkdir();
        }

        for (int i = 0; i < filePath.length; i++) {

            if (new File(sourcePath + File.separator + filePath[i]).isFile()) {
                String fileName = copyFile(sourcePath + File.separator + filePath[i],
                        path + File.separator + filePath[i]);
                fileNames.add(fileName);
            }
        }

        return fileNames;
    }

    /**
     * 文件复制.
     *
     * @param oldPath 源文件.
     * @param newPath 目的文件.
     * @throws IOException IOException.
     */
    public static String copyFile(String oldPath, String newPath) throws IOException {
        File oldFile = new File(oldPath);
        File file = new File(newPath);
        String fileName = oldFile.getName();
        FileInputStream in = new FileInputStream(oldFile);
        FileOutputStream out = new FileOutputStream(file);;

        byte[] buffer = new byte[1024];

        while ((in.read(buffer)) != -1) {
            out.write(buffer);
        }

        return fileName;
    }

    /**
     * 删除文件夹及其下面的文件.
     *
     * @param dir 要删除的文件.
     * @return 文件删除的状态.
     */
    public static boolean removeDir(File dir) {

        if (dir.isDirectory()) {
            String[] children = dir.list();
            // 递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = removeDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * 删除历史增量文件.
     *
     * @param compareTime 要删除文件的时间戳.
     * @param sourcePath 删除文件的路径.
     * @return 文件删除状态.
     */
    public static boolean removeHistoryPartialFile(Long compareTime, String sourcePath) {
        File historyFile = new File(sourcePath);
        // String[] filePath = file.list();
        File[] files = historyFile.listFiles();
        for (File file : files) {
            if (file.getName().split("_").length > 2) {
                Long fileTime = Long.valueOf(file.getName().split("_")[2]);
                if (fileTime < compareTime) {
                    boolean deleteStatus = file.delete();
                    if (!deleteStatus) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 获取request中的json串.
     *
     * @param request HttpServletRequest.
     * @return json字符串.
     * @throws UnsupportedEncodingException UnsupportedEncodingException.
     * @throws IOException IOException.
     * @author whf.
     */
    public static String getJsonDataFromStream(HttpServletRequest request)
            throws UnsupportedEncodingException, IOException {
        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));

        StringBuilder stringBuilder = new StringBuilder();
        String temp = null;

        while ((temp = bufferedReader.readLine()) != null) {
            stringBuilder.append(temp);
        }

        bufferedReader.close();
        return stringBuilder.toString();
    }

    /**
     * 产生的定长的随机字母数字串.
     *
     * @param length length.
     * @return 指定长度的字母数字串.
     * @author whf.
     */
    public static String createRandomCharData(int length) {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();// 随机用以下三个随机生成器
        Random randdata = new Random();
        int data = 0;
        for (int i = 0; i < length; i++) {
            int index = rand.nextInt(3);
            // 目的是随机选择生成数字，大小写字母
            switch (index) {
                case 0:
                    data = randdata.nextInt(10);// 仅仅会生成0~9
                    sb.append(data);
                    break;
                case 1:
                    data = randdata.nextInt(26) + 65;// 保证只会产生65~90之间的整数
                    sb.append((char) data);
                    break;
                case 2:
                    data = randdata.nextInt(26) + 97;// 保证只会产生97~122之间的整数
                    sb.append((char) data);
                    break;
                default:
                    break;
            }
        }
        String result = sb.toString();
        return result;
    }

    public static String addZero(int k) {
        return (k <= 0xF) ? "0" + Integer.toHexString(k) : Integer.toHexString(k);
    }
}

