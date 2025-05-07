package com.thungcam.chacalang.utils;

import ch.qos.logback.core.util.StringUtil;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.thymeleaf.util.StringUtils;

public class UrlUtils {
    public static String getBaseUrl(HttpServletRequest request) {
        String serverHost = request.getHeader("X-Forwarded-Host");
        if (!StringUtils.isEmpty(serverHost)) return serverHost;
        return request.getRequestURL().toString().replace(request.getRequestURI(), "");
    }
}
