package com.jkdroid.smstransfer.home;

import android.app.Activity;

import com.jkdroid.core.manager.SpManager;
import com.jkdroid.smstransfer.bean.ConfigBean;

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
    }

    public interface View{
        Activity getActivity();
        void toast(String msg);
    }

    interface Router{
        void goToSettingScreen();

        void startServiceForUpdate(ConfigBean intent);
    }

    interface Interactor{

        void getConfigBean(SpManager config);
    }

    interface InteractorOutpeut{

        void ongetConfigBeanFinished(ConfigBean intent);
    }
}
