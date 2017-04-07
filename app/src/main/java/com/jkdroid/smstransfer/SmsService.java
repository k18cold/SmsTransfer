package com.jkdroid.smstransfer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jkdroid.smstransfer.bean.SmsBean;

/**
 * Created by alan on 2017/4/6.
 */

public class SmsService extends Service {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    private static final String KEY_RECEIVED = "key_received";
    private static final String TAG = "SmsService";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(SmsService.TAG, "onStartCommand");
        if (intent == null || intent.getParcelableExtra(KEY_RECEIVED) == null){
            return Service.START_STICKY;
        }

        SmsBean smsBean = intent.getParcelableExtra(KEY_RECEIVED);
        Log.i(SmsService.TAG, smsBean.toString());
        return Service.START_STICKY;
    }

    public SmsService(){
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public static Intent getSmsIntent(Context context,  String number, String content){
        Intent intent = new Intent(context, SmsService.class);
        SmsBean bean = new SmsBean(number, content);
        intent.putExtra(KEY_RECEIVED, bean);
        return intent;
    }

}
