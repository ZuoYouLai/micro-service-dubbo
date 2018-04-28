package com.whforever.micro.common.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Ad/Ldap认证与数据同步接口.
 * @author user
 * @date 2017/11/29
 */
public class LdapUserLoginUtils {

    private static Logger logger = LoggerFactory.getLogger(LdapUserLoginUtils.class);

    /**
     * 验证AD或Ldap数据.
     * @param type type.
     * @param userName userName.
     * @param password password.
     * @param dn dn.
     * @param connectorName connectorName.
     * @return 验证结果.
     */
    public static Boolean ladpUserLogin(String type, String userName, String password, String dn,
            String connectorName) {
        Map<String, String> paramMap = new HashMap<>(4);
        paramMap.put("type", "AD_LDAP");
        paramMap.put("userName", userName);
        paramMap.put("password", password);
        paramMap.put("dn", dn);
        String param = JSON.toJSONString(paramMap);
        String localLogoUrl = PropertiesUtils.prop.get("ldapuser.url.login");
        Map<String, String> params = new HashMap<>(4);
        params.put("connectorName", connectorName);
        params.put("jsonStrData", param);
        String res = HttpTookit.doPost(localLogoUrl, params);
        Map resultMap = JSONObject.parseObject(res, Map.class);
        String flag = (String) resultMap.get("data");
        if ("true".equals(flag)) {
            return true;
        }
        return false;
    }

}
