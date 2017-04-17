package com.jkdroid.smstransfer.dao;

import com.jkdroid.smstransfer.BaseApplication;

import java.util.List;

/**
 *
 * Created by alan on 2017/4/15.
 */

public class MySmsDaoImpl implements MySmsDao {
    @Override
    public List<Sms> querySmsLimit(int start, int limit) {
        return BaseApplication.getDaoInstant().getSmsDao()
                .queryBuilder()
                .offset(start)
                .limit(limit)
                .list();
    }

    @Override
    public long insertSms(Sms smsBean) {
        return BaseApplication.getDaoInstant().getSmsDao()
                .insert(smsBean);
    }

    @Override
    public Sms querySmsById(long id) {
        return BaseApplication.getDaoInstant().getSmsDao()
                .queryBuilder()
                .where(SmsDao.Properties.Id.eq(id))
                .build()
                .unique();
    }

    @Override
    public void insertOrUpdateSms(Sms sms) {
        BaseApplication.getDaoInstant().getSmsDao()
                .insertOrReplace(sms);
    }

    @Override
    public long count() {
        return BaseApplication.getDaoInstant().getSmsDao()
                .count();
    }
}
