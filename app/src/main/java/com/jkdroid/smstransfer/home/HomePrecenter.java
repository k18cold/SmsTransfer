package com.jkdroid.smstransfer.home;

import com.jkdroid.core.manager.SpManager;
import com.jkdroid.smstransfer.bean.ConfigBean;
import com.jkdroid.smstransfer.bean.Contants;

/**
 *
 * Created by alan on 2017/4/10.
 */

class HomePrecenter implements HomeContracts.Precenter, HomeContracts.InteractorOutpeut{
    private final HomeContracts.View mView;
    private final HomeContracts.Router mRouter;
    private final HomeContracts.Interactor mInteractor;
    HomePrecenter(HomeContracts.View view) {
        this.mView = view;
        this.mRouter = new HomeRouter(mView.getActivity());
        this.mInteractor = new HomeInteractor(this);
    }

    @Override
    public void onLeftButtonPressed() {
        mView.toast("left");
    }

    @Override
    public void onRightButtonPressed() {
        mView.toast("right");
        mRouter.goToSettingScreen();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onResume() {
        mInteractor.getConfigBean(SpManager.getInstance(mView.getActivity().getApplicationContext(), Contants.SP_FILE_CONFIG));
    }

    @Override
    public void ongetConfigBeanFinished(ConfigBean bean) {
        mRouter.startServiceForUpdate(bean);
    }
}
