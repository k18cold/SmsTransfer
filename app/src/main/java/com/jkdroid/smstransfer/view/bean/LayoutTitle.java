package com.jkdroid.smstransfer.view.bean;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jkdroid.smstransfer.R;

/**
 *
 * Created by alan on 2017/4/10.
 */

public class LayoutTitle implements View.OnClickListener {

    private ImageButton mBtnLeft;
    private ImageButton mBtnRight;
    private TextView mTv;
    private OnClickListener mOnClickListener;
    public static final int POSITION_LEFT = 1;
    public static final int POSITION_RIGHT = 2;
    private LayoutTitle() {
    }

    private LayoutTitle(ImageButton bl, TextView tv, ImageButton br) {
        this.mBtnLeft = bl;
        this.mTv = tv;
        this.mBtnRight = br;
        bl.setOnClickListener(this);
        br.setOnClickListener(this);
    }

    public static LayoutTitle getTitle(View view) {
        if (view == null) {
            return null;
        }
        View bl = view.findViewById(R.id.title_btn_left);
        View tv = view.findViewById(R.id.title_tv);
        View br = view.findViewById(R.id.title_btn_right);
        if (bl == null || tv == null || br == null) {
            return null;
        }
        return new LayoutTitle((ImageButton) bl, (TextView) tv, (ImageButton) br);
    }

    public ImageButton getBtnLeft() {
        return mBtnLeft;
    }

    public ImageButton getBtnRight() {
        return mBtnRight;
    }

    public TextView getTv() {
        return mTv;
    }

    public void setOnClickListener(OnClickListener listener){
        this.mOnClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnClickListener == null){
            return ;
        }
        if (v == mBtnLeft){
            mOnClickListener.onClickListener(v, POSITION_LEFT);
        }else if (v == mBtnRight){
            mOnClickListener.onClickListener(v, POSITION_RIGHT);
        }
    }

    public interface OnClickListener{
        void onClickListener(View view, int position);
    }
}