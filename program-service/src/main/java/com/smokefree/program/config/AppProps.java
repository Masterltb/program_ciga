package com.smokefree.program.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProps {
    /** UTC time HH:mm để nhắc hằng ngày, mặc định 00:30 */
    private String notifDailyUtc = "00:30";
    public String getNotifDailyUtc() { return notifDailyUtc; }
    public void setNotifDailyUtc(String v) { this.notifDailyUtc = v; }
}
