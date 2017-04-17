package com.jkdroid.smstransfer.home;

import com.jkdroid.core.manager.SpManager;
import com.jkdroid.smstransfer.bean.ConfigBean;
import com.jkdroid.smstransfer.bean.Contants;
import com.jkdroid.smstransfer.dao.MySmsDao;
import com.jkdroid.smstransfer.dao.MySmsDaoImpl;
import com.jkdroid.smstransfer.dao.Sms;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * Created by alan on 2017/4/14.
 */

class HomeInteractor implements HomeContracts.Interactor {

    private final HomeContracts.InteractorOutpeut mOutpeut;
    private MySmsDao mSmsDao;
    HomeInteractor(HomeContracts.InteractorOutpeut outpeut) {
        this.mOutpeut = outpeut;
        mSmsDao = new MySmsDaoImpl();
    }

    @Override
    public void getConfigBean(SpManager spManager) {
        boolean sns =spManager.getBoolean(Contants.KEY_SEND_NUMBER_SWITCH, false);
        boolean fns = spManager.getBoolean(Contants.KEY_FROM_NUMBER_SWITCH, false);
        boolean fcs = spManager.getBoolean(Contants.KEY_FROM_CONTENT_SWITCH, false);

        String sn = spManager.getString(Contants.KEY_SEND_NUMBER, "");
        String fn = spManager.getString(Contants.KEY_FROM_NUMBER, "");
        String fc = spManager.getString(Contants.KEY_FROM_CONTENT, "");

        int group = spManager.getInt(Contants.KEY_GROUP, 0);

        ConfigBean bean = new ConfigBean();
        bean.setTransferOn(sns);
        bean.setRgxNumberOn(fns);
        bean.setRgxContentOn(fcs);
        bean.setTransferNumber(sn);
        bean.setRgxNumber(fn);
        bean.setRgxContent(fc);
        bean.setGroup(group);
        this.mOutpeut.ongetConfigBeanFinished(bean);
    }

    @Override
    public void getCurrentSms(final int start, final int limit) {
        Observable.create(new ObservableOnSubscribe<List<Sms>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Sms>> e) throws Exception {
                List<Sms> smses = mSmsDao.querySmsLimit(start, limit);
                Collections.sort(smses, new Comparator<Sms>() {
                    @Override
                    public int compare(Sms o1, Sms o2) {
                        long l = o2.getTime() - o1.getTime();
                        if (l > 0){
                            return 1;
                        }
                        if (l == 0){
                            return 0;
                        }
                        return -1;
                    }
                });
                e.onNext(smses);
                e.onComplete();
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<Sms>>() {
            @Override
            public void accept(List<Sms> smses) throws Exception {
                mOutpeut.onGetCurrentSmsFinished(smses);
            }
        });
    }

    @Override
    public void getLastestSmses(final int limit) {
        Observable.create(new ObservableOnSubscribe<List<Sms>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Sms>> e) throws Exception {
                long count = mSmsDao.count();
                int start = count > limit ? (int) (count - limit) : 0;
                List<Sms> smses = mSmsDao.querySmsLimit(start  , limit);
                Collections.sort(smses, new Comparator<Sms>() {
                    @Override
                    public int compare(Sms o1, Sms o2) {
                        long l = o2.getTime() - o1.getTime();
                        if (l > 0){
                            return 1;
                        }
                        if (l == 0){
                            return 0;
                        }
                        return -1;
                    }
                });
                e.onNext(smses);
                e.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Sms>>() {
                    @Override
                    public void accept(List<Sms> smses) throws Exception {
                        mOutpeut.onGetCurrentSmsFinished(smses);
                    }
                });
    }
}
