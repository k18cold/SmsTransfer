package com.jkdroid.smstransfer.settings;

import android.app.Activity;

import com.jkdroid.core.manager.SpManager;
import com.jkdroid.smstransfer.bean.ConfigBean;

/**
 *
 * Created by alan on 2017/4/11.
 */

class SettingsContracts {
    interface Precenter{
        void showConfig();

        void saveConfig();
    }

    public interface View{

        Activity getActivity();

        void showConfig(ConfigBean configFromSp);

        ConfigBean getCurConfigBean();
    }

    interface Router{

    }

    interface Interactor{

        void saveConfig(SpManager instance, ConfigBean bean);

        void getConfigBean(SpManager instance);
    }

    interface InteractorOutpeut{

        void onGetConfigBeanSucceed(ConfigBean bean);
    }
}
