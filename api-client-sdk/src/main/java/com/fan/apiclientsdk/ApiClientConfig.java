package com.fan.apiclientsdk;

import com.fan.apiclientsdk.client.ApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author 19677
 */
@Configuration
@ConfigurationProperties("api.client")
@Data
@ComponentScan
public class ApiClientConfig {

    private String accessKey;

    private String secretKey;

    @Bean
    public ApiClient apiClient(){
        return new ApiClient(accessKey,secretKey);
    }


}