package com.jkdroid.smstransfer.settings;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;

import com.jkdroid.smstransfer.MyActivity;
import com.jkdroid.smstransfer.R;
import com.jkdroid.smstransfer.bean.ConfigBean;
import com.jkdroid.smstransfer.view.bean.LayoutTitle;

/**
 *
 * Created by alan on 2017/4/11.
 */

public class SettingsActivity extends MyActivity implements SettingsContracts.View{
    private LayoutTitle mLayoutTitle;

    CheckBox mCbAutoTransfer;
    CheckBox mCbNumber;
    CheckBox mCbContent;
    EditText mEtAutoTransfer;
    EditText mEtNumber;
    EditText mEtContent;

    private SettingsContracts.Precenter mPrecenter;
//    @Override
    protected void onCreateAfter(Bundle bundle) {
        mCbAutoTransfer = (CheckBox) findViewById(R.id.cb_auto_transfer);
        mEtAutoTransfer = (EditText) findViewById(R.id.et_auto_transfer);
        mCbNumber = (CheckBox) findViewById(R.id.cb_number);
        mEtNumber = (EditText) findViewById(R.id.et_number);
        mCbContent = (CheckBox) findViewById(R.id.cb_content);
        mEtContent = (EditText) findViewById(R.id.et_content);
        mPrecenter = new SettingsPrecenter(this);
        mLayoutTitle.getBtnRight().setVisibility(View.INVISIBLE);
        mLayoutTitle.getTv().setText("设置");
        mLayoutTitle.getBtnLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackBtnClick();
            }
        });
    }

    @Override
    public void onBackPressed() {
        onBackBtnClick();
    }

    private void onBackBtnClick() {
        finish();
    }

//    @Override
    protected View initView(Bundle bundle) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = View.inflate(this, R.layout.activity_settings, null);
        mLayoutTitle = LayoutTitle.getTitle(view);
        return view;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPrecenter.showConfig();
    }

    @Override
    protected void onStop() {
        mPrecenter.saveConfig();
        super.onStop();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showConfig(ConfigBean bean) {

        mCbAutoTransfer.setChecked(bean.isTransferOn());
        mCbNumber.setChecked(bean.isRgxNumberOn());
        mCbContent.setChecked(bean.isRgxContentOn());

        mEtAutoTransfer.setText(bean.getTransferNumber());
        mEtNumber.setText(bean.getRgxNumber());
        mEtContent.setText(bean.getRgxContent());
    }

    @Override
    public ConfigBean getCurConfigBean() {
        ConfigBean bean = new ConfigBean();
        boolean sns = mCbAutoTransfer.isChecked();
        boolean fns = mCbNumber.isChecked();
        boolean fcs = mCbContent.isChecked();

        String sn = mEtAutoTransfer.getText().toString().trim();
        String fn = mEtNumber.getText().toString().trim();
        String fc = mEtContent.getText().toString().trim();

        bean.setTransferOn(sns);
        bean.setRgxNumberOn(fns);
        bean.setRgxContentOn(fcs);
        bean.setTransferNumber(sn);
        bean.setRgxNumber(fn);
        bean.setRgxContent(fc);
        return bean;
    }

}
