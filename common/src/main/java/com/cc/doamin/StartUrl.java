package com.cc.doamin;

public class StartUrl {

    private String id;
    private String url;
    private String statusCode; // 0 爬取成功        1 爬取失败        2 待爬取        3 爬取中

    public StartUrl() {
    }

    public StartUrl(String id, String url, String statusCode) {
        this.id = id;
        this.url = url;
        this.statusCode = statusCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "StartUrl{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", statusCode='" + statusCode + '\'' +
                '}';
    }
}
