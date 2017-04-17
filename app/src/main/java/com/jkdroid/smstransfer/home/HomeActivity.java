package com.jkdroid.smstransfer.home;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkdroid.smstransfer.MyActivity;
import com.jkdroid.smstransfer.R;
import com.jkdroid.smstransfer.dao.Sms;
import com.jkdroid.smstransfer.view.bean.LayoutTitle;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class HomeActivity extends MyActivity implements HomeContracts.View {

    private static final String ACTION_AUTO_SEND_MSG = "action_auto_send_msg";
    private LayoutTitle mLayoutTitle;
    private HomeContracts.Precenter mPrecenter;
    private RecyclerView mRv;
    private BroadcastReceiver mReceiver;
    private HomeAdapter mAdapter;

    @Override
    protected void onCreateAfter(Bundle bundle) {
        mRv = (RecyclerView) findViewById(R.id.rv_list);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        if (mAdapter == null){
            mAdapter = new HomeAdapter(null);
        }
        mRv.setAdapter(mAdapter);
        mPrecenter = new HomePrecenter(this);
        mLayoutTitle.getBtnLeft().setVisibility(View.INVISIBLE);
        mLayoutTitle.setOnClickListener(new LayoutTitle.OnClickListener() {
            @Override
            public void onClickListener(View view, int position) {
                if (position == LayoutTitle.POSITION_LEFT) {
                    mPrecenter.onLeftButtonPressed();
                } else if (position == LayoutTitle.POSITION_RIGHT) {
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
    public void registFontReceiver() {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //TODO 插入当前的数组中,并写入到数据库
                Sms bean = Sms.getByIntent(intent);
                mPrecenter.onReceiveNewSms(bean);
            }
        };
        IntentFilter filter = new IntentFilter(ACTION_AUTO_SEND_MSG);
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void unregistFontReceiver() {
        unregisterReceiver(mReceiver);
        mReceiver = null;
    }

    @Override
    public void updateList(Sms bean, List<Sms> smsList) {
        if (bean != null){
            mAdapter.notifyItemInserted(0);
            Log.i("result", "notifyItemInserted "+bean.getContent());
        }else {
            mAdapter.updateData(smsList);
            mAdapter.notifyDataSetChanged();
            Log.i("result", "notifyDataSetChanged");
        }
        mRv.scrollToPosition(0);
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

    @Override
    protected void onPause() {
        super.onPause();
        mPrecenter.onPause();
    }


    public static void sendUpdateSmsBroadcast(Context context, Sms sms) {
        Intent intent = sms.toIntent();
        intent.setAction(ACTION_AUTO_SEND_MSG);
        context.sendBroadcast(intent);
    }

    private class HomeAdapter extends RecyclerView.Adapter<MyViewHolder>{

        private List<Sms> mList;

        @SuppressWarnings("WeakerAccess")
        public HomeAdapter(List<Sms> list){
            this.mList = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(
                    HomeActivity.this).inflate(R.layout.item_home, parent,
                    false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Sms bean = mList.get(position);
            holder.mTvPhone.setText(bean.getNumber());
            holder.mTvDate.setText(DateFormat.getDateTimeInstance().format(new Date(bean.getTime())));
            holder.mTvContent.setText(bean.getContent());
            holder.mIvResult.setImageResource(bean.getResult() == 1 ? R.drawable.ico_good : R.drawable.ico_wrong);
        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }

        void updateData(List<Sms> list){
            this.mList = list;
        }

    }
    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTvPhone;
        TextView mTvDate;
        TextView mTvContent;
        ImageView mIvResult;

        MyViewHolder(View itemView) {
            super(itemView);
            mTvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
            mTvDate = (TextView) itemView.findViewById(R.id.tv_date);
            mTvContent = (TextView) itemView.findViewById(R.id.tv_content);
            mIvResult = (ImageView) itemView.findViewById(R.id.iv_result);
        }
    }
}
