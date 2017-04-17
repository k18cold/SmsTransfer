package com.jkdroid.smstransfer.home;

import android.app.Activity;

import com.jkdroid.core.manager.SpManager;
import com.jkdroid.smstransfer.bean.ConfigBean;
import com.jkdroid.smstransfer.dao.Sms;

import java.util.List;

/**
 *
 * Created by alan on 2017/4/10.
 */

class HomeContracts {

    interface Precenter{
        void onLeftButtonPressed();
        void onRightButtonPressed();
        void onDestroy();

        void onResume();

        void onPause();

        void onReceiveNewSms(Sms bean);
    }

    public interface View{
        Activity getActivity();

        void registFontReceiver();

        void unregistFontReceiver();

        void updateList(Sms bean, List<Sms> smsList);
    }

    interface Router{
        void goToSettingScreen();

        void startServiceForUpdate(ConfigBean intent);
    }

    interface Interactor{

        void getConfigBean(SpManager config);

        void getCurrentSms(int start, int limit);

        void getLastestSmses(int limit);
    }

    interface InteractorOutpeut{

        void ongetConfigBeanFinished(ConfigBean intent);

        void onGetCurrentSmsFinished(List<Sms> smses);
    }
}
