package com.linhongzheng.weixin.entity.menu;

/**
 * Created by linhz on 2015/8/17.
 */
public class CommonButton extends Button {
    private String type;
    private String key;
    private String url;

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
}
