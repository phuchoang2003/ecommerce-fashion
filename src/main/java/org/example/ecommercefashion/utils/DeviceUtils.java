package org.example.ecommercefashion.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeviceUtils {
    public static String getClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("X-Real-IP");
        }
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
        }

        return clientIp.contains(",") ? clientIp.substring(0, clientIp.indexOf(",")) : clientIp;
    }


    public static String getDevice(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent").toLowerCase();

        Pattern pattern = Pattern.compile("\\((.*?)\\)");
        Matcher matcher = pattern.matcher(userAgent);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "unknown";
    }

    public static String getBrowser(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent").toLowerCase();

        int lastIndex = userAgent.lastIndexOf(')');
        if (lastIndex != -1) {
            return userAgent.substring(lastIndex + 1).trim();
        }
        return "unknown";
    }


    public static String getDeviceId(HttpServletRequest request) {
        String deviceId = request.getHeader("x-device-id");
        if (deviceId != null) {
            return deviceId;
        }
        return "unknown";
    }
}
