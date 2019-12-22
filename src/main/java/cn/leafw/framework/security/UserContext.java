package cn.leafw.framework.security;

import org.springframework.core.NamedThreadLocal;

/**
 * 用户登陆信息封装
 *
 * @author <a href="mailto:wyr95626@95626.cn">CareyWYR</a>
 * @date 2019/12/20
 */
public class UserContext {

    private static final ThreadLocal<UserToken> TOKEN_HOLDER = new NamedThreadLocal<>("leafw token");

    /**
     *
     * @param token token
     */
    public static void setToken(UserToken token) {
        if (token == null) {
            cleanToken();
        } else {
            TOKEN_HOLDER.set(token);
        }

    }

    public static void cleanToken() {
        TOKEN_HOLDER.remove();
    }

    public static UserToken getUser(){
        return TOKEN_HOLDER.get();
    }
}

