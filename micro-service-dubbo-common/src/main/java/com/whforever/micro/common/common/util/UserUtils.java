package com.whforever.micro.common.common.util;

public class UserUtils {

    public static final Integer STATUSERNOTACTIVE = 1; // 未激活
    public static final Integer STATUSACTIVE = 2; // 账户激活
    public static final Integer STATUSLOCK = 3; // 账户锁定，管理员锁定
    public static final Integer STATUSDISABLED = 4; // 账户停用
    public static final Integer STATUSLOCKPSWERROR = 5; // 账户锁定，密码输错超过设置的阈值，导致账户锁定
    public static final Integer PASSWORDOVERTIME = 6; // 用户密码过期

}