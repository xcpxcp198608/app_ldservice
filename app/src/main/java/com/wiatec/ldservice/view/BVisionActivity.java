package com.wiatec.ldservice.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.px.common.adapter.BaseRecycleAdapter;
import com.px.common.utils.Logger;
import com.wiatec.ldservice.R;
import com.wiatec.ldservice.adapter.ChannelTypeBVisionAdapter;
import com.wiatec.ldservice.adapter.LDFamAdapter;
import com.wiatec.ldservice.databinding.ActivityBvisionBinding;
import com.wiatec.ldservice.instance.Application;
import com.wiatec.ldservice.instance.Constant;
import com.wiatec.ldservice.pojo.ChannelTypeInfo;
import com.wiatec.ldservice.pojo.ImageInfo;
import com.wiatec.ldservice.pojo.LDFamInfo;
import com.wiatec.ldservice.presenter.BVisionPresenter;
import com.wiatec.ldservice.task.TokenTask;
import com.wiatec.ldservice.widget.LDFamListView;

import java.util.List;

@Route(path = Constant.route.bvision)
public class BVisionActivity extends BaseActivity<BVisionPresenter> implements IBVision, View.OnClickListener  {

    private ActivityBvisionBinding binding;
    private LDFamAdapter ldFamAdapter;

    @Override
    protected BVisionPresenter createPresenter() {
        return new BVisionPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bvision);
        binding.llLdFam.setOnClickListener(this);
        presenter.loadChannelType(2 + "");
        presenter.loadLDFam();
        binding.btRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loadChannelType(2+"");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_ld_fam:
                Logger.d("sdf");
                binding.pbLdFam.setVisibility(View.VISIBLE);
                presenter.loadLDFam();
                break;
        }
    }

    @Override
    public void onLoadImage(boolean execute, ImageInfo imageInfo) {

    }

    @Override
    public void onLoadChannelType(boolean execute, final List<ChannelTypeInfo> channelTypeInfoList) {
        if(!execute){
            binding.pbLoading.setVisibility(View.GONE);
            binding.tvLoading.setText(getString(R.string.load_fail));
            binding.btRetry.setVisibility(View.VISIBLE);
            binding.btRetry.requestFocus();
            return;
        }
        binding.llLoading.setVisibility(View.GONE);
        ChannelTypeBVisionAdapter channelTypeBVisionAdapter = new ChannelTypeBVisionAdapter(channelTypeInfoList);
        binding.rcvChannelType.setAdapter(channelTypeBVisionAdapter);
        binding.rcvChannelType.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        binding.rcvChannelType.requestFocus();
        channelTypeBVisionAdapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Application.getExecutorService().execute(new TokenTask());
                ChannelTypeInfo channelTypeInfo = channelTypeInfoList.get(position);
                showChannel(channelTypeInfo);
            }
        });
    }

    private void showChannel(ChannelTypeInfo channelTypeInfo){
        if(channelTypeInfo.getFlag() == 1){
            Intent intent = new Intent(BVisionActivity.this, ChannelTypeActivity1.class);
            intent.putExtra("type", channelTypeInfo.getTag());
            startActivity(intent);
        }else if(channelTypeInfo.getFlag() == 2){
            Intent intent = new Intent(BVisionActivity.this, ChannelTypeActivity2.class);
            intent.putExtra("type", channelTypeInfo.getTag());
            startActivity(intent);
        }else {
            Intent intent = new Intent(BVisionActivity.this, ChannelActivity.class);
            intent.putExtra(Constant.key.channel_type, channelTypeInfo.getTag());
            startActivity(intent);
        }
    }

    @Override
    public void onLoadLDFam(boolean execute, List<LDFamInfo> ldFamInfoList) {
        if(ldFamInfoList != null) {
            binding.pbLdFam.setVisibility(View.GONE);
            if (ldFamAdapter == null) {
                ldFamAdapter = new LDFamAdapter(this, ldFamInfoList);
            }
            binding.lvLdFam.setAdapter(ldFamAdapter);
            ldFamAdapter.notifyChange(ldFamInfoList);
            binding.lvLdFam.start();
            binding.lvLdFam.setOnScrollFinishedListener(new LDFamListView.OnScrollFinishedListener() {
                @Override
                public void onFinished(boolean isFinished, int position) {
                    if (presenter != null) {
                        presenter.loadLDFam();
                    }
                }
            });
        }
    }
}

