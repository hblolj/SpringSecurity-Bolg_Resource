package com.hblolj.security.properties;

/**
 * @author: hblolj
 * @Date: 2019/3/18 11:04
 * @Description:
 * @Version:
 **/
public class SocialProperties {

    private String filterProcessesUrl = "/auth";

    private QQProperties qq = new QQProperties();

    public QQProperties getQq() {
        return qq;
    }

    public void setQq(QQProperties qq) {
        this.qq = qq;
    }

    public String getFilterProcessesUrl() {
        return filterProcessesUrl;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }
}
