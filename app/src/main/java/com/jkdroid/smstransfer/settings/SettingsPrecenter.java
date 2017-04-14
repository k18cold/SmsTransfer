package com.jkdroid.smstransfer.settings;

import android.util.Log;

import com.jkdroid.core.manager.SpManager;
import com.jkdroid.smstransfer.bean.ConfigBean;
import com.jkdroid.smstransfer.bean.Contants;

/**
 *
 * Created by alan on 2017/4/11.
 */

class SettingsPrecenter implements SettingsContracts.Precenter , SettingsContracts.InteractorOutpeut {


    private final SettingsContracts.View mView;
    private final SettingsContracts.Interactor mInteractor;
    private final SettingsContracts.Router mRouter;

    SettingsPrecenter(SettingsContracts.View view) {
        this.mView = view;
        this.mRouter = new SettingsRouter(view.getActivity());
        this.mInteractor = new SettingsInteractor(this);
    }

    @Override
    public void showConfig() {
        Log.i("测试", "showConfig");
        mInteractor.getConfigBean(SpManager.getInstance(mView.getActivity().getApplicationContext(), Contants.SP_FILE_CONFIG));
    }

    @Override
    public void saveConfig() {
        ConfigBean bean = mView.getCurConfigBean();
        mInteractor.saveConfig(SpManager.getInstance(mView.getActivity().getApplicationContext(), Contants.SP_FILE_CONFIG), bean);
    }

    @Override
    public void onGetConfigBeanSucceed(ConfigBean bean) {
        mView.showConfig(bean);
    }
}
