package com.jkdroid.smstransfer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jkdroid.core.manager.SpManager;
import com.jkdroid.core.util.PhoneUtils;
import com.jkdroid.core.util.ToastUtils;
import com.jkdroid.smstransfer.bean.ConfigBean;
import com.jkdroid.smstransfer.bean.Contants;
import com.jkdroid.smstransfer.dao.MySmsDao;
import com.jkdroid.smstransfer.dao.MySmsDaoImpl;
import com.jkdroid.smstransfer.dao.Sms;
import com.jkdroid.smstransfer.home.HomeActivity;

import java.util.LinkedList;

/**
 * 
 * Created by alan on 2017/4/6.
 */

public class SmsService extends Service {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     * <p>
     * name Used to name the worker thread, important only for debugging.
     */
    private static final String KEY_RECEIVED = "key_received";
    private static final String TAG = "SmsService";
    private static final String ACTION_CONFIG = "action_config";
    private static final String ACTION_SMS_RECEIVED = "action_sms_received";
    public static final String KEY_CONFIG = "key_config";
    private static final String ACTION_SEND_SMS_RESULT = "action_send_sms_result";

    private ConfigBean mConfigBean;

    private MySmsDao mSmsDao;

    private final LinkedList<Long> mIds = new LinkedList<>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(SmsService.TAG, "onStartCommand");
        if (intent != null) {
            switch (intent.getAction()) {
                case ACTION_CONFIG:
                    //配置修改
                    Parcelable parcelableExtra = intent.getParcelableExtra(KEY_CONFIG);
                    if (parcelableExtra != null && parcelableExtra instanceof ConfigBean) {
                        mConfigBean = (ConfigBean) parcelableExtra;
                    }
                    break;
                case ACTION_SMS_RECEIVED:
                    //收到短信
                    parcelableExtra = intent.getParcelableExtra(KEY_RECEIVED);
                    if (parcelableExtra != null && parcelableExtra instanceof Sms) {
                        Sms smsBean = intent.getParcelableExtra(KEY_RECEIVED);
                        if (mConfigBean == null) {
                            mConfigBean = Contants.getConfigFromSp(SpManager.getInstance(this.getApplicationContext(), Contants.SP_FILE_CONFIG));
                        }
                        boolean needTransfer = needTransfer(smsBean) && PhoneUtils.checkSIM(this);
                        if (BuildConfig.DEBUG){
                            needTransfer = true;
                        }
                        if (needTransfer) {
                            if (!BuildConfig.DEBUG){
                                PhoneUtils.sendSMS(this, mConfigBean.getTransferNumber(), smsBean.getContent());
                            }
                            if (mSmsDao == null) {
                                mSmsDao = new MySmsDaoImpl();
                            }
                            mIds.push(mSmsDao.insertSms(smsBean));
                            HomeActivity.sendUpdateSmsBroadcast(getApplicationContext(), smsBean);
                        }
//                        Log.i(SmsService.TAG, "trans:" + needTransfer + "," + smsBean.toString());
//                        ToastUtils.showLongImmediately(this, "trans:" + needTransfer);
                    }
                    break;
                case ACTION_SEND_SMS_RESULT:
                    boolean result = intent.getBooleanExtra("result", false);
                    if (result) {
                        ToastUtils.showLongImmediately(this, "send-succeed");
                    }
                    //TODO 改写数据库
                    if (!mIds.isEmpty()){
                        Long id = mIds.pop();
                        Sms sms = mSmsDao.querySmsById(id);
                        if (sms != null){
                            sms.setResult(result ? 1 : -1);
                            mSmsDao.insertOrUpdateSms(sms);
                            HomeActivity.sendUpdateSmsBroadcast(getApplicationContext(), sms);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        return Service.START_STICKY;
    }

    private boolean needTransfer(Sms smsBean) {
        if (!mConfigBean.isTransferOn()) {
            return false;
        }
        if (mConfigBean.isRgxNumberOn() && mConfigBean.getRgxNumber().contains(smsBean.getNumber())) {
            return true;
        } else if (mConfigBean.isRgxContentOn() && smsBean.getContent().contains(mConfigBean.getRgxContent())) {
            return true;
        }
        return false;
    }

    public SmsService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static Intent getSmsReceiveIntent(Context context, Intent intent) {
        Intent it = getSmsServiceIntent(context, intent);
        it.setAction(ACTION_SMS_RECEIVED);
        return it;
    }

    private static Intent getSmsServiceIntent(Context context, Intent intent) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (intent == null){
            intent = new Intent();
        }
        Intent it = new Intent(intent);
        it.setClass(context, SmsService.class);
        return it;
    }

    public static Intent getConfigIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SmsService.class);
        intent.setAction(ACTION_CONFIG);
        return intent;
    }

    public static Intent getSendSmsResultIntent(Context context, boolean result) {
        Intent intent = new Intent(context, SmsService.class);
        intent.setAction(ACTION_SEND_SMS_RESULT);
        intent.putExtra("result", result);
        return intent;
    }

    public static Intent toIntent(Sms smsBean) {
        Intent intent = new Intent();
        intent.putExtra(KEY_RECEIVED, smsBean);
        intent.setAction(ACTION_SMS_RECEIVED);
        return intent;
    }
}
