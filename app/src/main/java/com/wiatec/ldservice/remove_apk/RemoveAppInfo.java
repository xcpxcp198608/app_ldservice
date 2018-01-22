package com.wiatec.ldservice.remove_apk;

/**
 * Created by patrick on 21/07/2017.
 * create time : 11:12 AM
 */

public class RemoveAppInfo {
    private int id;
    private String packageName;
    private int code;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "RemoveAppInfo{" +
                "id=" + id +
                ", packageName='" + packageName + '\'' +
                ", code=" + code +
                '}';
    }
}
