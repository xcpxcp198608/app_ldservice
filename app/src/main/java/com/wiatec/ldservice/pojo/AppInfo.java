package com.wiatec.ldservice.pojo;

/**
 * Created by patrick on 2018/5/21.
 * create time : 3:16 PM
 */

public class AppInfo {

    private int id;
    private String label;
    private String packageName;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", packageName='" + packageName + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
