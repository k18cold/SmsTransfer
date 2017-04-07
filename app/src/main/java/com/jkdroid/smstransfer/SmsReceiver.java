package com.jkdroid.smstransfer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by guanzhihao on 2016/2/16.
 */
public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("mession", "收到发送短信回调结果");
        if (intent == null){
            return;
        }
        if ("SENT_SMS_ACTION".equalsIgnoreCase(intent.getAction())){
            boolean result = false;
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    result = true;
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                case SmsManager.RESULT_ERROR_RADIO_OFF:
                case SmsManager.RESULT_ERROR_NULL_PDU:
                default:
                    break;
            }
//            context.startService(MessionService.getSendSmsResultIntent(context, result));
        }else {
            Log.i("mession", "sms--检测收到短信");
            Bundle bundle = intent.getExtras();
            SmsMessage msg = null;
            String number = "";
            String content = "";
            if (null != bundle) {
                Object[] smsObj = (Object[]) bundle.get("pdus");
                for (Object object : smsObj) {
                    msg = SmsMessage.createFromPdu((byte[]) object);
                    number = msg.getOriginatingAddress();
                    content += msg.getDisplayMessageBody();
//                    Date date = new Date(msg.getTimestampMillis());//时间
//                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    String receiveTime = format.format(date);
//                    Log.e("sms", "sms--"+"number:" + msg.getOriginatingAddress()
//                            + "   body:" + msg.getDisplayMessageBody() + "  time:"
//                            + msg.getTimestampMillis());
                }
            }
            if (!TextUtils.isEmpty(number) && !TextUtils.isEmpty(content)){
                //有正常内容
                Log.i("SmsReceiver", content);

                context.startService(SmsService.getSmsIntent(context, number, content));
//                context.startService(MessionService.getReceiveSmsIntent(context, number, content));
            }
        }
    }
}
