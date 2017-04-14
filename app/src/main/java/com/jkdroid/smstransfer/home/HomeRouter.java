package com.jkdroid.smstransfer.home;

import android.app.Activity;
import android.content.Intent;

import com.jkdroid.smstransfer.SmsService;
import com.jkdroid.smstransfer.bean.ConfigBean;
import com.jkdroid.smstransfer.settings.SettingsActivity;

import java.lang.ref.SoftReference;

/**
 *
 * Created by alan on 2017/4/10.
 */

class HomeRouter implements HomeContracts.Router{
    private final SoftReference<Activity> mActivitySoftReference;
    HomeRouter(Activity activity){
        mActivitySoftReference = new SoftReference<>(activity);
    }
    @Override
    public void goToSettingScreen() {
        Activity ac = mActivitySoftReference.get();
        if (ac == null){
            return;
        }
        Intent intent = new Intent(ac, SettingsActivity.class);
        ac.startActivity(intent);
    }

    @Override
    public void startServiceForUpdate(ConfigBean bean) {
        Activity ac = mActivitySoftReference.get();
        if (ac == null){
            return;
        }
        Intent i = SmsService.getConfigIntent(ac.getApplicationContext());
        i.putExtra(SmsService.KEY_CONFIG, bean);
        ac.startService(i);
    }
}
