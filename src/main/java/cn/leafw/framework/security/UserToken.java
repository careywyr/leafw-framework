package cn.leafw.framework.security;

import lombok.Data;

import java.util.Map;

/**
 * TODO
 *
 * @author <a href="mailto:wyr95626@95626.cn">CareyWYR</a>
 * @date 2019/12/20
 */
@Data
public class UserToken {

    private Map<String, String> data;

    private String token;

    private String userIp;

    private Long userId;
}

