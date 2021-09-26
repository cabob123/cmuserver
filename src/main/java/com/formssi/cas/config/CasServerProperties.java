package com.formssi.cas.config;



import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "com.***")
public class CasServerProperties {
    // cas 服务器地址
    private String casServerPath;
    // ticket 地址
    private String ticketPath;
    // 校验ticket 地址
    private String validatePath;
    // 绑定域名地址
    private String domain;
    // oath2.0 验证token 地址
    private String accessTokenPath;

    public String getCasServerPath() {
        return casServerPath;
    }
    public void setCasServerPath(String casServerPath) {
        this.casServerPath = casServerPath;
    }
    public String getTicketPath() {
        return ticketPath;
    }
    public void setTicketPath(String ticketPath) {
        this.ticketPath = ticketPath;
    }
    public String getValidatePath() {
        return validatePath;
    }
    public void setValidatePath(String validatePath) {
        this.validatePath = validatePath;
    }
    public String getDomain() {
        return domain;
    }
    public void setDomain(String domain) {
        this.domain = domain;
    }
    public String getAccessTokenPath() {
        return accessTokenPath;
    }
    public void setAccessTokenPath(String accessTokenPath) {
        this.accessTokenPath = accessTokenPath;
    }

}
