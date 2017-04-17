package com.jkdroid.smstransfer.home;

import com.jkdroid.core.manager.SpManager;
import com.jkdroid.smstransfer.bean.ConfigBean;
import com.jkdroid.smstransfer.bean.Contants;
import com.jkdroid.smstransfer.dao.Sms;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by alan on 2017/4/10.
 */

class HomePrecenter implements HomeContracts.Precenter, HomeContracts.InteractorOutpeut{
    private HomeContracts.View mView;
    private HomeContracts.Router mRouter;
    private final HomeContracts.Interactor mInteractor;
    private final ArrayList<Sms> mSmsList = new ArrayList<>();
    HomePrecenter(HomeContracts.View view) {
        this.mView = view;
        this.mRouter = new HomeRouter(mView.getActivity());
        this.mInteractor = new HomeInteractor(this);
    }

    @Override
    public void onLeftButtonPressed() {
    }

    @Override
    public void onRightButtonPressed() {
        mRouter.goToSettingScreen();
    }

    @Override
    public void onDestroy() {
        mView = null;
        mRouter = null;
    }

    @Override
    public void onResume() {
        mInteractor.getConfigBean(SpManager.getInstance(mView.getActivity().getApplicationContext(), Contants.SP_FILE_CONFIG));
        new Thread(new Runnable() {
            @Override
            public void run() {
                mInteractor.getCurrentSms(0, 10);
            }
        }).start();
        mView.registFontReceiver();
    }

    @Override
    public void onPause() {
        mView.unregistFontReceiver();
    }

    @Override
    public void onAutoTransferSmsSucceed(Sms bean) {
        //把这条信息插入到当前的数组
        mSmsList.add(bean);
        mView.updateList(bean, mSmsList);
    }

    @Override
    public void ongetConfigBeanFinished(ConfigBean bean) {
        mRouter.startServiceForUpdate(bean);
    }

    @Override
    public void onGetCurrentSmsFinished(List<Sms> smses) {
        mView.updateList(null, smses);
    }
}
