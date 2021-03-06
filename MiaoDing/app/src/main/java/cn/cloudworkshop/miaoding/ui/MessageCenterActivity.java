package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.MsgCenterBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.LogUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.view.BadgeView;
import okhttp3.Call;

/**
 * Author：Libin on 2016/12/22 16:16
 * Email：1993911441@qq.com
 * Describe：消息中心
 */
public class MessageCenterActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.rv_message_center)
    RecyclerView rvMessage;
    @BindView(R.id.img_load_error)

    ImageView imgLoadError;
    private List<MsgCenterBean.DataBean> msgList = new ArrayList<>();
    private CommonAdapter<MsgCenterBean.DataBean> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_center);
        ButterKnife.bind(this);
        tvHeaderTitle.setText(R.string.msg_center);
        initData();
        initView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    /**
     * 加载数据
     */
    private void initData() {

        OkHttpUtils.get()
                .url(Constant.MESSAGE_TYPE)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        imgLoadError.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        imgLoadError.setVisibility(View.GONE);
                        MsgCenterBean messageBean = GsonUtils.jsonToBean(response, MsgCenterBean.class);
                        msgList.clear();
                        if (messageBean.getCode() == 10000 && messageBean.getData() != null &&
                                messageBean.getData().size() > 0) {
                            msgList.addAll(messageBean.getData());
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

    }

    /**
     * 加载视图
     */
    private void initView() {
        rvMessage.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommonAdapter<MsgCenterBean.DataBean>(this, R.layout.listitem_message_center, msgList) {
            @Override
            protected void convert(ViewHolder holder, MsgCenterBean.DataBean dataBean, int position) {
                ImageView ivIcon = holder.getView(R.id.img_message_icon);
                Glide.with(MessageCenterActivity.this)
                        .load(Constant.IMG_HOST + dataBean.getImg())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(ivIcon);
                BadgeView badgeView = new BadgeView(MessageCenterActivity.this);
                if (dataBean.getUnread_message_num() > 0) {
                    badgeView.setVisibility(View.VISIBLE);
                    badgeView.setBackgroundResource(R.drawable.badgeview_bg);
                    badgeView.setWidth(25);
                    badgeView.setHeight(25);
                    badgeView.setTextSize(4);
                    badgeView.setTargetView(ivIcon);
                    if (msgList.get(position).getUnread_message_num() < 99) {
                        badgeView.setBadgeCount(dataBean.getUnread_message_num());
                    } else {
                        badgeView.setText("99+");
                    }
                } else {
                    badgeView.setVisibility(View.GONE);
                }

                holder.setText(R.id.tv_message_title, dataBean.getName());
            }
        };
        rvMessage.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(MessageCenterActivity.this, MessageDetailActivity.class);
                intent.putExtra("type", msgList.get(position).getType());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }


    @OnClick({R.id.img_header_back, R.id.img_load_error})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                finish();
                break;
            case R.id.img_load_error:
                initData();
                break;
        }
    }
}
