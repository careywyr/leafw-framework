package cn.leafw.framework.security;

import cn.leafw.framework.utils.StringUtils;
import cn.leafw.framework.utils.WebUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 权限校验
 *
 * @author <a href="mailto:wyr95626@95626.cn">CareyWYR</a>
 * @date 2019/12/20
 */
@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Value("${security.jwt.secret}")
    private String secret;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = httpServletRequest.getHeader("Authorization");
        if (StringUtils.hasText(authHeader)) {
            try {
                Claims claims = (Claims) Jwts.parser().setSigningKey(JwtUtil.generalKey(secret)).parseClaimsJws(authHeader).getBody();
                UserToken user = new UserToken();
                user.setUserId(Long.valueOf(claims.getSubject()));
                user.setData((Map)claims.get("datas", Map.class));
                user.setToken(authHeader);
                UserContext.setToken(user);
            } catch (Exception e) {
                log.error("invalid token: {}", authHeader, e);
            }
        }
        boolean verify = true;
        String currentPath = WebUtils.resolutionUrl(httpServletRequest.getRequestURI(), httpServletRequest.getContextPath());
        log.info("currentPath: {}", currentPath);
        if(currentPath.contains("login")){
            verify = false;
        }
        if(verify && StringUtils.isEmpty(authHeader)){
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}

