package com.wiatec.ldservice.pojo;

public class ControlPlayInfo {

    private int id;
    private String mac;
    private String url;
    private boolean play;
    private int duration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isPlay() {
        return play;
    }

    public void setPlay(boolean play) {
        this.play = play;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "ControlPlayInfo{" +
                "id=" + id +
                ", mac='" + mac + '\'' +
                ", url='" + url + '\'' +
                ", play=" + play +
                ", duration=" + duration +
                '}';
    }
}
