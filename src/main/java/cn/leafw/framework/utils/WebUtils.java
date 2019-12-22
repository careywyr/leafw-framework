package cn.leafw.framework.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class WebUtils {

    /**
     * 获取请求的真实IP地址
     */
    public static String getRealIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip==null?"":ip;
    }

    /**
     * 判断是否为ajax请求
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        boolean isAjax = "XMLHttpRequest".equals(header) ? true : false;
        return isAjax;
    }

    /**
     * 描述: 解析请求URl的方法
     * @param url 当前请求的URL地址
     * @param contextPath 当前项目部署根路径
     */
    public static String resolutionUrl(String url, String contextPath) {
        String path = null;
        if (StringUtils.isEmpty(contextPath)) {
            if (url.startsWith("/")) {
                path = url.substring(1);
            }
        } else {
            path = url.substring(url.indexOf(contextPath) + contextPath.length() + 1);
        }
        if (url.indexOf(";") > -1) {
            path = url.substring(0, url.lastIndexOf(";"));
        }
        return path;
    }

}
