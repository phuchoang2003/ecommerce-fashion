package org.example.ecommercefashion.common.core.dto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.*;
import org.example.ecommercefashion.common.core.util.DeviceUtils;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeviceDetails {
    private String device;
    private String browser;
    private String ip;
    private String deviceId;

    public static DeviceDetails fromHeader(HttpServletRequest request) {
        return DeviceDetails.builder()
                .ip(DeviceUtils.getClientIp(request))
                .device(DeviceUtils.getDevice(request))
                .browser(DeviceUtils.getBrowser(request))
                .deviceId(DeviceUtils.getDeviceId(request))
                .build();

    }
}


