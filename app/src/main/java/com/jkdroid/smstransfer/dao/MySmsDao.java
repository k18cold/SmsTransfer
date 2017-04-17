package com.jkdroid.smstransfer.dao;

import java.util.List;

/**
 * Created by al
 * an on 2017/4/15.
 */

public interface MySmsDao {
    List<Sms> querySmsLimit(int start, int limit);

    long insertSms(Sms smsBean);

    Sms querySmsById(long id);

    void insertOrUpdateSms(Sms sms);

    long count();
}
