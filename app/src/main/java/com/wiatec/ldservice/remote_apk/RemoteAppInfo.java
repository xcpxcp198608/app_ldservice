package com.wiatec.ldservice.remote_apk;

/**
 * Created by patrick on 21/07/2017.
 * create time : 11:12 AM
 */

public class RemoteAppInfo {
    private int id;
    private String name;
    private String packageName;
    private String url;
    private int code;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ApkInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", packageName='" + packageName + '\'' +
                ", url='" + url + '\'' +
                ", code=" + code +
                '}';
    }
}
