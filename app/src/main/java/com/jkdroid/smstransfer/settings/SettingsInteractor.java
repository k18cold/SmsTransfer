package com.jkdroid.smstransfer.settings;

import com.jkdroid.core.manager.SpManager;
import com.jkdroid.smstransfer.bean.ConfigBean;
import com.jkdroid.smstransfer.bean.Contants;

/**
 *
 * Created by alan on 2017/4/11.
 */

class SettingsInteractor implements SettingsContracts.Interactor {

    private final SettingsContracts.InteractorOutpeut mOutput;

    SettingsInteractor(SettingsContracts.InteractorOutpeut interactorOutpeut) {
        this.mOutput = interactorOutpeut;
    }

    @Override
    public void saveConfig(SpManager instance, ConfigBean bean) {
        Contants.saveConfig(instance, bean);
    }

    @Override
    public void getConfigBean(SpManager instance) {
        ConfigBean bean = Contants.getConfigFromSp(instance);
        mOutput.onGetConfigBeanSucceed(bean);
    }
}
