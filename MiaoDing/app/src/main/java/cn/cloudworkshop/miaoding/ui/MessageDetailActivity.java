package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
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
import cn.cloudworkshop.miaoding.bean.MsgDetailBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.DateUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2016/12/23 11:05
 * Email：1993911441@qq.com
 * Describe：消息详情
 */
public class MessageDetailActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.rv_message_detail)
    RecyclerView rvMessage;
    @BindView(R.id.tv_no_message)
    TextView tvNoneMsg;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;
    @BindView(R.id.sfl_msg)
    SmartRefreshLayout refreshLayout;
    private int type;
    //消息详情布局id
    private int layoutId;
    private List<MsgDetailBean.DataBean> msgList = new ArrayList<>();

    private int page = 1;
    //总页数
    private int pages;
    //刷新
    private boolean isRefresh;
    //加载更多
    private boolean isLoadMore;
    private CommonAdapter<MsgDetailBean.DataBean> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        ButterKnife.bind(this);
        getData();
        initData();
        initView(layoutId);
    }


    /**
     * 加载数据
     */
    private void initData() {
        OkHttpUtils.get()
                .url(Constant.MESSAGE_DETAIL)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("type", String.valueOf(type))
                .addParams("page", String.valueOf(page))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        imgLoadError.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        imgLoadError.setVisibility(View.GONE);
                        MsgDetailBean msgBean = GsonUtils.jsonToBean(response, MsgDetailBean.class);


                        if (msgBean.getPages() != null) {
                            pages = msgBean.getPages().getTotalpage();
                        }

                        //是否刷新状态
                        if (isRefresh) {
                            refreshLayout.finishRefresh();
                            isRefresh = false;
                            msgList.clear();
                        } else if (isLoadMore) { //加载更多
                            refreshLayout.finishLoadMore();
                        } else {
                            msgList.clear();
                        }

                        if (msgBean.getCode() == 10000 && msgBean.getData() != null && msgBean.getData().size() > 0) {
                            msgList.addAll(msgBean.getData());
                            readMsg();
                            tvNoneMsg.setVisibility(View.GONE);
                        } else {
                            if (!isLoadMore) {
                                tvNoneMsg.setVisibility(View.VISIBLE);
                            }
                        }
                        isLoadMore = false;
                        adapter.notifyDataSetChanged();
                    }
                });

    }

    /**
     * 设置已读
     */
    private void readMsg() {
        for (int i = 0; i < msgList.size(); i++) {
            if (msgList.get(i).getStatus() == 1) {
                OkHttpUtils.post()
                        .url(Constant.READ_MSG)
                        .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                        .addParams("type", String.valueOf(type))
                        .addParams("msg_id", String.valueOf(msgList.get(i).getId()))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                            }
                        });
            }
        }
    }

    /**
     * @param layoutId 加载视图
     */
    private void initView(int layoutId) {
        rvMessage.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommonAdapter<MsgDetailBean.DataBean>(this, layoutId, msgList) {
            @Override
            protected void convert(ViewHolder holder, MsgDetailBean.DataBean dataBean, int position) {
                switch (type) {
                    case 1:
                        holder.setText(R.id.tv_notice_time, dataBean.getCreate_time());
                        holder.setText(R.id.tv_notice_content, dataBean.getContent());
                        holder.setText(R.id.tv_notice_date, DateUtils.formatTime("yyyy-MM-dd HH:mm:ss",
                                getString(R.string.year_month_day), dataBean.getCreate_time()));
                        break;
                    case 2:
                        holder.setText(R.id.tv_select_date, dataBean.getCreate_time());
                        holder.setText(R.id.tv_select_title, dataBean.getGoods_name());
                        Glide.with(MessageDetailActivity.this)
                                .load(Constant.IMG_HOST + dataBean.getCar_img())
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .into((ImageView) holder.getView(R.id.img_select_goods));
                        break;
                    case 3:
                        holder.setText(R.id.tv_logistics_date, dataBean.getCreate_time());
                        holder.setText(R.id.tv_logistics_title, getString(R.string.your_order) + dataBean.getRe_marks());
                        Glide.with(MessageDetailActivity.this)
                                .load(Constant.IMG_HOST + dataBean.getCar_img())
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .into((ImageView) holder.getView(R.id.img_logistics_goods));

                        holder.setText(R.id.tv_logistics_name, getString(R.string.logistics_no) + dataBean.getExpress_no());

                        holder.setText(R.id.tv_logistics_company, getString(R.string.send_company));
                        break;
                }
            }
        };
        rvMessage.setAdapter(adapter);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                switch (type) {
                    case 2:
                        Intent intent2 = null;
                        switch (msgList.get(position).getCategory_id()) {
                            case 1:
                                intent2 = new Intent(MessageDetailActivity.this, NewCustomizedGoodsActivity.class);
                                break;
                            case 2:
                                intent2 = new Intent(MessageDetailActivity.this, WorksDetailActivity.class);
                                break;
                        }
                        intent2.putExtra("id", String.valueOf(msgList.get(position).getGoods_id()));
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(MessageDetailActivity.this, LogisticsActivity.class);
                        intent3.putExtra("number", msgList.get(position).getExpress_no());
                        intent3.putExtra("order_id", msgList.get(position).getRe_marks());
                        intent3.putExtra("img_url", msgList.get(position).getCar_img());
                        startActivity(intent3);
                        break;
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });


        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (page < pages) {
                    isLoadMore = true;
                    page++;
                    initData();
                } else {
                    refreshLayout.finishLoadMore();
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                isRefresh = true;
                page = 1;
                initData();
            }
        });
    }

    private void getData() {
        type = getIntent().getIntExtra("type", 1);
        switch (type) {
            case 1:
                layoutId = R.layout.listitem_notice_message;
                tvHeaderTitle.setText(R.string.notic_msg);
                break;
            case 2:
                layoutId = R.layout.listitem_select_message;
                tvHeaderTitle.setText(R.string.activity_pop);
                break;
            case 3:
                layoutId = R.layout.listitem_logistics_message;
                tvHeaderTitle.setText(R.string.logistics_notice);
                break;
        }
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
