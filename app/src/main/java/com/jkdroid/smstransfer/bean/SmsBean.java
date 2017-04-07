package com.jkdroid.smstransfer.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alan on 2017/4/6.
 */

public class SmsBean implements Parcelable {

    private String number;
    private String content;

    public SmsBean(String number, String content) {
        this.number = number;
        this.content = content;
    }

    @Override
    public String toString() {
        return "SmsBean{" +
                "number='" + number + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.number);
        dest.writeString(this.content);
    }

    public SmsBean() {
    }

    protected SmsBean(Parcel in) {
        this.number = in.readString();
        this.content = in.readString();
    }

    public static final Parcelable.Creator<SmsBean> CREATOR = new Parcelable.Creator<SmsBean>() {
        @Override
        public SmsBean createFromParcel(Parcel source) {
            return new SmsBean(source);
        }

        @Override
        public SmsBean[] newArray(int size) {
            return new SmsBean[size];
        }
    };
}
