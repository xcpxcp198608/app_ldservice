package com.wiatec.ldservice.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * channel info
 */

public class LiveChannelInfo implements Parcelable {

    private int id;
    private int userId;
    private boolean available;
    private String username;
    private String userIcon;
    private int level;
    /**
     * 内容分级：0->G, 1->PG, 2->pg-13, 3->R, 4->NC-17
     */
    private int rating;
    private float price;
    private String title;
    private String message;
    /**
     * 1: live
     * 2: vod
     */
    private int type;
    private String category;
    private String streamId;
    private String videoId;
    private String pushUrl;
    private String playUrl;
    private String rtmpUrl;
    private String rtmpKey;
    private String preview;
    private String link;
    private String fileId;
    private String fileFormat;
    private String fileSize;
    private long duration;
    private long startTime;
    private long endTime;
    private String liveTime;
    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getRtmpUrl() {
        return rtmpUrl;
    }

    public void setRtmpUrl(String rtmpUrl) {
        this.rtmpUrl = rtmpUrl;
    }

    public String getRtmpKey() {
        return rtmpKey;
    }

    public void setRtmpKey(String rtmpKey) {
        this.rtmpKey = rtmpKey;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(String liveTime) {
        this.liveTime = liveTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "LiveChannelInfo{" +
                "id=" + id +
                ", userId=" + userId +
                ", available=" + available +
                ", username='" + username + '\'' +
                ", userIcon='" + userIcon + '\'' +
                ", level=" + level +
                ", rating=" + rating +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", type=" + type +
                ", category='" + category + '\'' +
                ", streamId='" + streamId + '\'' +
                ", videoId='" + videoId + '\'' +
                ", pushUrl='" + pushUrl + '\'' +
                ", playUrl='" + playUrl + '\'' +
                ", rtmpUrl='" + rtmpUrl + '\'' +
                ", rtmpKey='" + rtmpKey + '\'' +
                ", preview='" + preview + '\'' +
                ", link='" + link + '\'' +
                ", fileId='" + fileId + '\'' +
                ", fileFormat='" + fileFormat + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", duration=" + duration +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", liveTime='" + liveTime + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.userId);
        dest.writeByte(this.available ? (byte) 1 : (byte) 0);
        dest.writeString(this.username);
        dest.writeString(this.userIcon);
        dest.writeInt(this.level);
        dest.writeInt(this.rating);
        dest.writeFloat(this.price);
        dest.writeString(this.title);
        dest.writeString(this.message);
        dest.writeInt(this.type);
        dest.writeString(this.category);
        dest.writeString(this.streamId);
        dest.writeString(this.videoId);
        dest.writeString(this.pushUrl);
        dest.writeString(this.playUrl);
        dest.writeString(this.rtmpUrl);
        dest.writeString(this.rtmpKey);
        dest.writeString(this.preview);
        dest.writeString(this.link);
        dest.writeString(this.fileId);
        dest.writeString(this.fileFormat);
        dest.writeString(this.fileSize);
        dest.writeLong(this.duration);
        dest.writeLong(this.startTime);
        dest.writeLong(this.endTime);
        dest.writeString(this.liveTime);
        dest.writeString(this.createTime);
    }

    public LiveChannelInfo() {
    }

    protected LiveChannelInfo(Parcel in) {
        this.id = in.readInt();
        this.userId = in.readInt();
        this.available = in.readByte() != 0;
        this.username = in.readString();
        this.userIcon = in.readString();
        this.level = in.readInt();
        this.rating = in.readInt();
        this.price = in.readFloat();
        this.title = in.readString();
        this.message = in.readString();
        this.type = in.readInt();
        this.category = in.readString();
        this.streamId = in.readString();
        this.videoId = in.readString();
        this.pushUrl = in.readString();
        this.playUrl = in.readString();
        this.rtmpUrl = in.readString();
        this.rtmpKey = in.readString();
        this.preview = in.readString();
        this.link = in.readString();
        this.fileId = in.readString();
        this.fileFormat = in.readString();
        this.fileSize = in.readString();
        this.duration = in.readLong();
        this.startTime = in.readLong();
        this.endTime = in.readLong();
        this.liveTime = in.readString();
        this.createTime = in.readString();
    }

    public static final Parcelable.Creator<LiveChannelInfo> CREATOR = new Parcelable.Creator<LiveChannelInfo>() {
        @Override
        public LiveChannelInfo createFromParcel(Parcel source) {
            return new LiveChannelInfo(source);
        }

        @Override
        public LiveChannelInfo[] newArray(int size) {
            return new LiveChannelInfo[size];
        }
    };
}
