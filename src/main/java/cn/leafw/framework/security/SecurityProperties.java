package cn.leafw.framework.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author <a href="mailto:wyr95626@95626.cn">CareyWYR</a>
 * @date 2020/3/21
 */
@Data
@Component
@ConfigurationProperties("leafw.security")
public class SecurityProperties {

    /**开启JWT校验*/
    private Boolean jwtCheck;

    /**不用进行认证的访问地址的前缀*/
    private String[] ignoreurl;

    /**jwt的过期时间，单位：毫秒。默认为30天*/
    private Long jwtExpMillis = 30 * 24 * 60 * 60 * 1000L;

    /**jwt的秘钥*/
    private String jwtSecret;
}

