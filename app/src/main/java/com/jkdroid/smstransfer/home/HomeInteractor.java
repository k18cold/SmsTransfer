package com.jkdroid.smstransfer.home;

import com.jkdroid.core.manager.SpManager;
import com.jkdroid.smstransfer.bean.ConfigBean;
import com.jkdroid.smstransfer.bean.Contants;

/**
 *
 * Created by alan on 2017/4/14.
 */

class HomeInteractor implements HomeContracts.Interactor {
    private final HomeContracts.InteractorOutpeut mOutpeut;

    HomeInteractor(HomeContracts.InteractorOutpeut outpeut) {
        this.mOutpeut = outpeut;
    }

    @Override
    public void getConfigBean(SpManager spManager) {
        boolean sns =spManager.getBoolean(Contants.KEY_SEND_NUMBER_SWITCH, false);
        boolean fns = spManager.getBoolean(Contants.KEY_FROM_NUMBER_SWITCH, false);
        boolean fcs = spManager.getBoolean(Contants.KEY_FROM_CONTENT_SWITCH, false);

        String sn = spManager.getString(Contants.KEY_SEND_NUMBER, "");
        String fn = spManager.getString(Contants.KEY_FROM_NUMBER, "");
        String fc = spManager.getString(Contants.KEY_FROM_CONTENT, "");

        ConfigBean bean = new ConfigBean();
        bean.setTransferOn(sns);
        bean.setRgxNumberOn(fns);
        bean.setRgxContentOn(fcs);
        bean.setTransferNumber(sn);
        bean.setRgxNumber(fn);
        bean.setRgxContent(fc);

        this.mOutpeut.ongetConfigBeanFinished(bean);
    }
}
