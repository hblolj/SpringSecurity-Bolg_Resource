package com.hblolj.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: hblolj
 * @Date: 2019/3/18 11:04
 * @Description:
 * @Version:
 **/
@Data
@Component("customSecurityProperties")
@ConfigurationProperties(prefix = "hblolj.security")
public class SecurityProperties {

    private BrowserProperties browser = new BrowserProperties();

    private SocialProperties social = new SocialProperties();
}
