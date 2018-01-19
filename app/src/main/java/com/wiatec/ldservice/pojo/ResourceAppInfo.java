package com.wiatec.ldservice.pojo;

/**
 * Created by patrick on 14/01/2018.
 * create time : 10:13 PM
 */

public class ResourceAppInfo {

    private int id;
    private String label;
    private String packageName;
    private int versionCode;
    private String url;
    private String icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "ResourceAppInfo{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", packageName='" + packageName + '\'' +
                ", versionCode=" + versionCode +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
