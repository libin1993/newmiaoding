package cn.cloudworkshop.miaoding.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.adapter.SectionedRecyclerViewAdapter;
import cn.cloudworkshop.miaoding.base.BaseFragment;
import cn.cloudworkshop.miaoding.bean.OrderInfoBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2019/1/12 16:09
 * Email：1993911441@qq.com
 * Describe：
 */
public class OrderFragment extends BaseFragment {
    @BindView(R.id.rv_order)
    RecyclerView rvOrder;
    @BindView(R.id.layout_refresh_order)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_no_order)
    TextView tvNoOrder;
    @BindView(R.id.ll_no_order)
    LinearLayout llNoOrder;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;
    private Unbinder unbinder;
    private SectionedRecyclerViewAdapter adapter;
    private List<OrderInfoBean.DataBean> dataList = new ArrayList<>();
    //订单状态
    private int orderStatus;
    //当前页
    private int page = 1;
    //刷新
    private boolean isRefresh;
    //加载更多
    private boolean isLoadMore;
    //总页数
    private int pages;
    private OnStateChangeListener onStateChangeListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        getData();
        initView();
        initData();
        return view;
    }

    /**
     * 加载视图
     */
    private void initView() {
        rvOrder.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new SectionedRecyclerViewAdapter() {
            @Override
            protected int getSectionCount() {
                return dataList.size();
            }

            @Override
            protected int getItemCountForSection(int section) {
                return dataList.get(section).getChildOrders().size();
            }

            @Override
            protected boolean hasFooterInSection(int section) {
                return true;
            }

            @Override
            protected HeaderViewHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.rv_order_header, parent, false);
                return new HeaderViewHolder(view);
            }

            @Override
            protected FooterViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.rv_order_footer, parent, false);
                return new FooterViewHolder(view);
            }

            @Override
            protected ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.rv_order_item, parent, false);
                return new ItemViewHolder(view);
            }

            @Override
            protected void onBindSectionHeaderViewHolder(HeaderViewHolder holder, int section) {
                TextView tvNo = (TextView) holder.getView(R.id.tv_order_number_header);
                TextView tvStatus = (TextView) holder.getView(R.id.tv_order_status_header);
                tvNo.setText(dataList.get(section).getOrder_sn());
                tvStatus.setText(dataList.get(section).getStatus_text());
            }

            @Override
            protected void onBindSectionFooterViewHolder(FooterViewHolder holder, int section) {

            }

            @Override
            protected void onBindItemViewHolder(ItemViewHolder holder, int section, int position) {
                Glide.with(getActivity())
                        .load(Constant.IMG_HOST + dataList.get(section).getChildOrders().get(position).getImg_info())
                        .placeholder(R.mipmap.place_holder_news)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into((ImageView) holder.getView(R.id.img_order_info));
                TextView tvGoodsName = (TextView) holder.getView(R.id.tv_order_name);
                tvGoodsName.setText(dataList.get(section).getChildOrders().get(position).getImg_info());
                tvGoodsName.setTypeface(DisplayUtils.setTextType(getActivity()));

                TextView tvNum = (TextView) holder.getView(R.id.tv_order_count);
                tvNum.setText(getString(R.string.together) + dataList.get(section).getChildOrders()
                        .get(position).getGoods_num() + getString(R.string.piece_goods));

            }
        };

        rvOrder.setAdapter(adapter);


        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (page < pages) {
                    isLoadMore = true;
                    page++;
                    initData();
                } else {
                    refreshLayout.finishLoadMore();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isRefresh = true;
                page = 1;
                initData();
            }
        });
    }

    /**
     * 获取订单数据
     */
    private void initData() {

        OkHttpUtils.get()
                .url(Constant.GOODS_ORDER)
                .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                .addParams("order_status", String.valueOf(orderStatus))
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
                        OrderInfoBean orderInfoBean = GsonUtils.jsonToBean(response, OrderInfoBean.class);

                        if (orderInfoBean.getPages() != null) {
                            pages = orderInfoBean.getPages().getTotalpage();
                        }

                        if (isRefresh) {
                            refreshLayout.finishRefresh();
                            isRefresh = false;
                            dataList.clear();
                        } else if (isLoadMore) {
                            refreshLayout.finishLoadMore();
                        } else {
                            dataList.clear();
                        }
                        if (orderInfoBean.getCode() == 10000 && orderInfoBean.getData().size() > 0) {
                            dataList.addAll(orderInfoBean.getData());
                            llNoOrder.setVisibility(View.GONE);
                        } else {
                            if (!isLoadMore) {
                                llNoOrder.setVisibility(View.VISIBLE);
                            }
                            if (orderInfoBean.getCode() == 10001) {
                                SharedPreferencesUtils.deleteStr(getActivity(), "token");
                            }
                        }
                        isLoadMore = false;
                        adapter.notifyDataSetChanged();
                    }
                });

    }

    private void getData() {
        Bundle bundle = getArguments();
        orderStatus = bundle.getInt("order");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onStateChangeListener = (OnStateChangeListener) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString() + "must implement OnButton2ClickListener");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_no_order, R.id.img_load_error})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_no_order:
                break;
            case R.id.img_load_error:
                break;
        }
    }

    public static OrderFragment newInstance(int orderStatus) {
        Bundle args = new Bundle();
        args.putInt("order", orderStatus);
        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 订单状态监听
     */
    public interface OnStateChangeListener {
        void onStateChange(int page);
    }
}
