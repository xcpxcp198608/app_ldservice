package com.wiatec.ldservice.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.px.common.adapter.BaseRecycleAdapter;
import com.px.common.animator.Zoom;
import com.px.common.utils.AppUtil;
import com.wiatec.ldservice.R;
import com.wiatec.ldservice.adapter.ChannelType2Adapter;
import com.wiatec.ldservice.databinding.ActivityChannelType2Binding;
import com.wiatec.ldservice.instance.Constant;
import com.wiatec.ldservice.pojo.ChannelType2Info;
import com.wiatec.ldservice.presenter.ChannelType2Presenter;

import java.util.List;

public class ChannelTypeActivity2 extends BaseActivity<ChannelType2Presenter> implements ChannelType2 {

    private ActivityChannelType2Binding binding;

    @Override
    protected ChannelType2Presenter createPresenter() {
        return new ChannelType2Presenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_channel_type2);
        String type = getIntent().getStringExtra("type");
        presenter.loadChannelType2(type);
        binding.rcvChannelType2.requestFocus();
    }

    @Override
    public void loadChannelType2(boolean execute, final List<ChannelType2Info> channelType2InfoList) {
        if(!execute){
            binding.pbLoading.setVisibility(View.GONE);
            binding.tvLoading.setText(getString(R.string.data_load_error));
            binding.btRetry.setVisibility(View.VISIBLE);
            binding.btRetry.requestFocus();
            return;
        }
        binding.llLoading.setVisibility(View.GONE);
        ChannelType2Adapter channelType2Adapter = new ChannelType2Adapter(channelType2InfoList);
        binding.rcvChannelType2.setAdapter(channelType2Adapter);
        binding.rcvChannelType2.setLayoutManager(new GridLayoutManager(this ,5));
        channelType2Adapter.setOnItemFocusListener(new BaseRecycleAdapter.OnItemFocusListener() {
            @Override
            public void onFocus(View view, int position, boolean hasFocus) {
                if(hasFocus){
                    view.setSelected(true);
                }else{
                    view.setSelected(false);
                }
            }
        });
        channelType2Adapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ChannelType2Info channelType2Info = channelType2InfoList.get(position);
                if(channelType2Info.getFlag() == 1){
                    AppUtil.launchApp(ChannelTypeActivity2.this, channelType2Info.getTag());
                } else {
                    Intent intent = new Intent(ChannelTypeActivity2.this, ChannelActivity.class);
                    intent.putExtra(Constant.key.channel_type, channelType2Info.getTag());
                    startActivity(intent);
                }
            }
        });
    }

}
