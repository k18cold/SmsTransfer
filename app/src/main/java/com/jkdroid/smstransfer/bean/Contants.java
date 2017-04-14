package com.jkdroid.smstransfer.bean;

import com.jkdroid.core.manager.SpManager;

/**
 *
 * Created by alan on 2017/4/14.
 */

public class Contants {
    public static final String KEY_SEND_NUMBER_SWITCH = "key_send_number_switch";
    public static final String KEY_FROM_NUMBER_SWITCH = "key_from_number_switch";
    public static final String KEY_FROM_CONTENT_SWITCH = "key_from_content_switch";
    public static final String KEY_SEND_NUMBER = "key_send_number";
    public static final String KEY_FROM_NUMBER = "key_from_number";
    public static final String KEY_FROM_CONTENT = "key_from_content";
    public static final String SP_FILE_CONFIG = "config";

    public static ConfigBean getConfigFromSp(SpManager spManager){
        boolean sns = spManager.getBoolean(Contants.KEY_SEND_NUMBER_SWITCH, false);
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
        return bean;
    }

    public static void saveConfig(SpManager instance, ConfigBean bean) {
        instance.saveBoolean(KEY_SEND_NUMBER_SWITCH, bean.isTransferOn());
        instance.saveBoolean(KEY_FROM_NUMBER_SWITCH, bean.isRgxNumberOn());
        instance.saveBoolean(KEY_FROM_CONTENT_SWITCH, bean.isRgxContentOn());

        instance.saveString(KEY_SEND_NUMBER, bean.getTransferNumber());
        instance.saveString(KEY_FROM_NUMBER, bean.getRgxNumber());
        instance.saveString(KEY_FROM_CONTENT, bean.getRgxContent());
    }
}
