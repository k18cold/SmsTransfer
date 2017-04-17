package com.jkdroid.smstransfer.dao;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Sms实体类
 * Created by alan on 2017/4/15.
 */

@SuppressWarnings("WeakerAccess")
@Entity
public class Sms implements Parcelable {
    private static final String KEY_SMSBEAN = "key_smsbean";


    //表示收到的短信列表
    public static final int TYPE_RECEIVE = 0x01;

    @Id(autoincrement = true)
    private Long id;

    private String number;

    private String content;

    private int result;

    private long time;

    public Sms(String number, String content, long time){
        this.number = number;
        this.content = content;
        this.time = time;
    }

    public static Sms getByIntent(Intent intent) {
        if (intent == null){
            return null;
        }
        Parcelable parcelableExtra = intent.getParcelableExtra(KEY_SMSBEAN);
        if (parcelableExtra == null || !(parcelableExtra instanceof Sms)){
            return null;
        }

        return (Sms) parcelableExtra;
    }

    public Intent toIntent(){
        Intent intent = new Intent();
        intent.putExtra(KEY_SMSBEAN, this);
        return intent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.number);
        dest.writeString(this.content);
        dest.writeInt(this.result);
        dest.writeLong(this.time);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getResult() {
        return this.result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    protected Sms(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.number = in.readString();
        this.content = in.readString();
        this.result = in.readInt();
        this.time = in.readLong();
    }

    @Generated(hash = 751985797)
    public Sms(Long id, String number, String content, int result, long time) {
        this.id = id;
        this.number = number;
        this.content = content;
        this.result = result;
        this.time = time;
    }

    @Generated(hash = 172684796)
    public Sms() {
    }

    public static final Creator<Sms> CREATOR = new Creator<Sms>() {
        @Override
        public Sms createFromParcel(Parcel source) {
            return new Sms(source);
        }

        @Override
        public Sms[] newArray(int size) {
            return new Sms[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sms sms = (Sms) o;

        return time == sms.time;

    }

    @Override
    public int hashCode() {
        return (int) (time ^ (time >>> 32));
    }
}
