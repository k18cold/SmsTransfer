package com.jkdroid.smstransfer.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * Created by alan on 2017/4/14.
 */

public class ConfigBean implements Parcelable{
    private boolean transferOn;
    private boolean rgxNumberOn;
    private boolean rgxContentOn;

    private String transferNumber;
    private String rgxNumber;
    private String rgxContent;

    private int group;

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public void setTransferOn(boolean transferOn) {
        this.transferOn = transferOn;
    }

    public void setRgxNumberOn(boolean rgxNumberOn) {
        this.rgxNumberOn = rgxNumberOn;
    }

    public void setRgxContentOn(boolean rgxContentOn) {
        this.rgxContentOn = rgxContentOn;
    }

    public void setTransferNumber(String transferNumber) {
        this.transferNumber = transferNumber;
    }

    public void setRgxNumber(String rgxNumber) {
        this.rgxNumber = rgxNumber;
    }

    public void setRgxContent(String rgxContent) {
        this.rgxContent = rgxContent;
    }

    public boolean isTransferOn() {
        return transferOn;
    }

    public boolean isRgxNumberOn() {
        return rgxNumberOn;
    }

    public boolean isRgxContentOn() {
        return rgxContentOn;
    }

    public String getTransferNumber() {
        return transferNumber;
    }

    public String getRgxNumber() {
        return rgxNumber;
    }

    public String getRgxContent() {
        return rgxContent;
    }

    public ConfigBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.transferOn ? (byte) 1 : (byte) 0);
        dest.writeByte(this.rgxNumberOn ? (byte) 1 : (byte) 0);
        dest.writeByte(this.rgxContentOn ? (byte) 1 : (byte) 0);
        dest.writeString(this.transferNumber);
        dest.writeString(this.rgxNumber);
        dest.writeString(this.rgxContent);
        dest.writeInt(this.group);
    }

    protected ConfigBean(Parcel in) {
        this.transferOn = in.readByte() != 0;
        this.rgxNumberOn = in.readByte() != 0;
        this.rgxContentOn = in.readByte() != 0;
        this.transferNumber = in.readString();
        this.rgxNumber = in.readString();
        this.rgxContent = in.readString();
        this.group = in.readInt();
    }

    public static final Creator<ConfigBean> CREATOR = new Creator<ConfigBean>() {
        @Override
        public ConfigBean createFromParcel(Parcel source) {
            return new ConfigBean(source);
        }

        @Override
        public ConfigBean[] newArray(int size) {
            return new ConfigBean[size];
        }
    };
}
