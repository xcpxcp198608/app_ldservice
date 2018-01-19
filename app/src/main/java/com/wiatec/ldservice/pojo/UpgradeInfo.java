package com.wiatec.ldservice.pojo;

public class UpgradeInfo {

    private int id;
    private String url;
    private String version;
    private int code;
    private String info;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "UpdateInfo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", version='" + version + '\'' +
                ", code=" + code +
                ", info='" + info + '\'' +
                '}';
    }
}
