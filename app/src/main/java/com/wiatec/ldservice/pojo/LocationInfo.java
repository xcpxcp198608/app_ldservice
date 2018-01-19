package com.wiatec.ldservice.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by patrick on 21/07/2017.
 * create time : 10:38 AM
 */

public class LocationInfo implements Parcelable {

    private String country;
    private String countryCode;
    private String timezone;
    private String regionName;
    private String city;
    private String lat;
    private String lon;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "LocationInfo{" +
                "country='" + country + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", timezone='" + timezone + '\'' +
                ", regionName='" + regionName + '\'' +
                ", city='" + city + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.country);
        dest.writeString(this.countryCode);
        dest.writeString(this.timezone);
        dest.writeString(this.regionName);
        dest.writeString(this.city);
        dest.writeString(this.lat);
        dest.writeString(this.lon);
    }

    public LocationInfo() {
    }

    protected LocationInfo(Parcel in) {
        this.country = in.readString();
        this.countryCode = in.readString();
        this.timezone = in.readString();
        this.regionName = in.readString();
        this.city = in.readString();
        this.lat = in.readString();
        this.lon = in.readString();
    }

    public static final Parcelable.Creator<LocationInfo> CREATOR = new Parcelable.Creator<LocationInfo>() {
        @Override
        public LocationInfo createFromParcel(Parcel source) {
            return new LocationInfo(source);
        }

        @Override
        public LocationInfo[] newArray(int size) {
            return new LocationInfo[size];
        }
    };
}
