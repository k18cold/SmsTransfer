package com.jkdroid.smstransfer.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.jkdroid.core.BaseActivity;
import com.jkdroid.smstransfer.R;
import com.jkdroid.smstransfer.view.bean.LayoutTitle;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements HomeContracts.View {

    private LayoutTitle mLayoutTitle;
    private HomeContracts.Precenter mPrecenter;
    private Toast mToast;
    @Override
    protected void onCreateAfter(Bundle bundle) {
        ButterKnife.bind(this);
        mToast = new Toast(this);
        mPrecenter = new HomePrecenter(this);
        mLayoutTitle.getBtnLeft().setVisibility(View.INVISIBLE);
        mLayoutTitle.setOnClickListener(new LayoutTitle.OnClickListener() {
            @Override
            public void onClickListener(View view, int position) {
                if (position == LayoutTitle.POSITION_LEFT){
                    mPrecenter.onLeftButtonPressed();
                }else if (position == LayoutTitle.POSITION_RIGHT){
                    mPrecenter.onRightButtonPressed();
                }
            }
        });
    }

    @Override
    protected View initView(Bundle bundle) {
        enableRebootWhenCrash(false);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = View.inflate(this, R.layout.activity_main, null);
        mLayoutTitle = LayoutTitle.getTitle(view);
        return view;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void toast(String msg) {
        if (mToast != null){
            mToast.cancel();
        }
        mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        mToast.show();
    }

    @Override
    protected void onDestroy() {
        mPrecenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPrecenter.onResume();
    }
}
