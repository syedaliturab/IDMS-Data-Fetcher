package com.drivesoft.idmsdatafetcher.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "ds.*")
public class ApplicationProperties {
    private String jwtSecret;
    private Long jwtExpirationInMilliseconds;
    private String idmsBaseUrl;
    private String idmsUsername;
    private String idmsPassword;
    private String idmsInstitutionId;
    private String idmsUserAuthApi;
    private String idmsGetAccountListApi;
    private String idmsAccountStatus;
    private String idmsLayoutId;
    private Integer vechileAccountPageSize;
}
