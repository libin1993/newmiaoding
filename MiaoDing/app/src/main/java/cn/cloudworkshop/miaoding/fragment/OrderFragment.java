package cn.cloudworkshop.miaoding.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.umeng.analytics.MobclickAgent;
import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

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
import cn.cloudworkshop.miaoding.bean.ResponseBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.ui.AfterSalesActivity;
import cn.cloudworkshop.miaoding.ui.ConfirmOrderActivity;
import cn.cloudworkshop.miaoding.ui.LogisticsActivity;
import cn.cloudworkshop.miaoding.ui.OrderDetailActivity;
import cn.cloudworkshop.miaoding.ui.PayOrderActivity;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.LogUtils;
import cn.cloudworkshop.miaoding.utils.RVItemDecoration;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
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
    @BindView(R.id.view_loading)
    AVLoadingIndicatorView viewLoading;
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
        viewLoading.smoothToShow();
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
            protected void onBindSectionHeaderViewHolder(HeaderViewHolder holder, final int section) {
                LinearLayout llOrder = (LinearLayout) holder.getView(R.id.ll_order_header);
                TextView tvNo = (TextView) holder.getView(R.id.tv_order_number_header);
                TextView tvStatus = (TextView) holder.getView(R.id.tv_order_status_header);
                tvNo.setText(dataList.get(section).getOrder_sn());
                tvStatus.setText(dataList.get(section).getStatus_text());
                llOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        orderDetail(dataList.get(section).getOrder_sn());
                    }
                });
            }

            @Override
            protected void onBindSectionFooterViewHolder(FooterViewHolder holder, final int section) {
                LinearLayout llOrder = (LinearLayout) holder.getView(R.id.ll_order_footer);
                TextView tvMoney = (TextView) holder.getView(R.id.tv_order_price_footer);
                TextView tvAfter = (TextView) holder.getView(R.id.tv_after_sale_footer);
                TextView tvControl = (TextView) holder.getView(R.id.tv_order_control_footer);
                TextView tvPay = (TextView) holder.getView(R.id.tv_order_pay_footer);
                tvMoney.setText("¥" + dataList.get(section).getPayable_amount());

                switch (dataList.get(section).getStatus()) {
                    case 1:
                        tvAfter.setVisibility(View.GONE);
                        tvControl.setVisibility(View.VISIBLE);
                        tvPay.setVisibility(View.VISIBLE);

                        tvControl.setText(R.string.cancel_order);
                        tvPay.setText(R.string.pay);
                        break;
                    case 2:
                        tvAfter.setVisibility(View.GONE);
                        tvControl.setVisibility(View.VISIBLE);
                        tvPay.setVisibility(View.GONE);
                        tvControl.setText(R.string.notice_send_goods);
                        break;
                    case 3:
                        tvAfter.setVisibility(View.GONE);
                        tvControl.setVisibility(View.VISIBLE);
                        tvPay.setVisibility(View.VISIBLE);

                        tvAfter.setText(R.string.after_sale);
                        tvControl.setText(R.string.view_logistics);
                        tvPay.setText(R.string.confirm_receive);
                        break;
                    case 4:
                        tvAfter.setVisibility(View.GONE);
                        tvControl.setVisibility(View.VISIBLE);
                        tvPay.setVisibility(View.GONE);

                        tvAfter.setText(R.string.after_sale);
                        tvControl.setText(R.string.buy_again);
                        //订单未评价
//                        if (dataBean.getComment_id() == 0) {
//                            holder.setVisible(R.id.tv_order_pay, true);
//                            holder.setText(R.id.tv_order_pay, getString(R.string.evaluate));
//                        } else {
//                            holder.setVisible(R.id.tv_order_pay, false);
//                        }
                        break;
                    case 5:
                        tvAfter.setVisibility(View.GONE);
                        tvControl.setVisibility(View.VISIBLE);
                        tvPay.setVisibility(View.GONE);

                        tvControl.setText(R.string.delete_order);
                        break;
                }


                tvAfter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(getActivity(), AfterSalesActivity.class);
                        intent1.putExtra("order_id", dataList.get(section).getOrder_sn());
                        startActivity(intent1);
                    }
                });

                tvControl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (dataList.get(section).getStatus()) {
                            case 1:
                                cancelOrder(dataList.get(section).getOrder_sn());
                                break;
                            case 2:
                                ToastUtils.showToast(getActivity(), getString(R.string.has_notice));
                                break;
                            case 3:
                                Intent intent = new Intent(getActivity(), LogisticsActivity.class);
                                intent.putExtra("order_id", dataList.get(section).getOrder_sn());
                                intent.putExtra("number", dataList.get(section).getExpress_no());
                                intent.putExtra("img_url", dataList.get(section).getChildOrders().get(0).getImg_info());
                                startActivity(intent);
                                break;
                            case 4:
                                buyAgain(dataList.get(section).getOrder_sn());
                                break;
                            case 5:
                                deleteOrder(dataList.get(section).getOrder_sn(), section);
                                break;
                        }
                    }
                });

                tvPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (dataList.get(section).getStatus()) {
                            case 1:
                                Intent intent = new Intent(getActivity(), PayOrderActivity.class);
                                intent.putExtra("order_id", dataList.get(section).getOrder_sn());
                                startActivity(intent);
                                break;
                            case 3:
                                confirmReceive(dataList.get(section).getOrder_sn());
                                break;
                            case 4:
                                //订单评价
//                Intent intent = new Intent(this, EvaluateActivity.class);
//                intent.putExtra("order_id", orderId);
//                intent.putExtra("cart_id", orderBean.getData().getCar_list().get(0).getId() + "");
//                intent.putExtra("goods_id", orderBean.getData().getCar_list().get(0).getGoods_id() + "");
//                intent.putExtra("goods_img", orderBean.getData().getCar_list().get(0).getGoods_thumb());
//                intent.putExtra("goods_name", orderBean.getData().getCar_list().get(0).getGoods_name());
//
//                switch (orderBean.getData().getCar_list().get(0).getGoods_type()) {
//                    case 2:
//                        intent.putExtra("goods_type", orderBean.getData().getCar_list().get(0).getSize_content());
//                        break;
//                    default:
//                        intent.putExtra("goods_type", getString(R.string.customize_type));
//                        break;
//                }
//
//                startActivity(intent);
                                break;
                        }
                    }
                });


                llOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        orderDetail(dataList.get(section).getOrder_sn());
                    }
                });
            }

            @Override
            protected void onBindItemViewHolder(ItemViewHolder holder, final int section, final int position) {
                RelativeLayout rlOrder = (RelativeLayout) holder.getView(R.id.rl_order_item);
                Glide.with(getActivity())
                        .load(Constant.IMG_HOST + dataList.get(section).getChildOrders().get(position).getImg_info())
                        .placeholder(R.mipmap.place_holder_news)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into((ImageView) holder.getView(R.id.img_order_info));
                TextView tvGoodsName = (TextView) holder.getView(R.id.tv_order_name);
                TextView tvContent = (TextView) holder.getView(R.id.tv_order_content);
                tvGoodsName.setText(dataList.get(section).getChildOrders().get(position).getGoods_name());
                tvGoodsName.setTypeface(DisplayUtils.setTextType(getActivity()));

                if (dataList.get(section).getChildOrders().get(position).getPart() != null) {
                    String parts = "";
                    for (OrderInfoBean.DataBean.ChildOrdersBean.PartBean partBean : dataList.get(section)
                            .getChildOrders().get(position).getPart()) {
                        parts += partBean.getPart_name() + ":" + partBean.getPart_value() + ";";
                    }
                    parts = parts.substring(0, parts.length() - 1);
                    tvContent.setText(parts);
                }

                TextView tvNum = (TextView) holder.getView(R.id.tv_order_count);
                tvNum.setText(getString(R.string.together) + dataList.get(section).getChildOrders()
                        .get(position).getGoods_num() + getString(R.string.piece_goods));

                rlOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        orderDetail(dataList.get(section).getOrder_sn());
                    }
                });
                tvContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dataList.get(section).getChildOrders().get(position).getPart() != null) {
                            goodsPart(dataList.get(section).getChildOrders().get(position).getPart());
                        }

                    }
                });

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
     * 再次购买
     */
    private void buyAgain(String orderId) {
        OkHttpUtils.post()
                .url(Constant.BUY_AGAIN)
                .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                .addParams("order_sn", orderId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int code = jsonObject.getInt("code");
                            String msg = jsonObject.getString("msg");
                            if (code == 10000) {
                                String cartId = jsonObject.getString("cart_id_s");
                                Intent intent = new Intent(getActivity(), ConfirmOrderActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("cart_id", cartId);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else {
                                ToastUtils.showToast(getActivity(), msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }


    /**
     * 确认收货
     */
    private void confirmReceive(String orderId) {
        OkHttpUtils.post()
                .url(Constant.CONFIRM_RECEIVE)
                .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                .addParams("order_sn", orderId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {


                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ResponseBean responseBean = GsonUtils.jsonToBean(response, ResponseBean.class);
                        ToastUtils.showToast(getActivity(), responseBean.getMsg());
                        if (responseBean.getCode() == 10000) {
                            MobclickAgent.onEvent(getActivity(), "trade_success");
                            onStateChangeListener.onStateChange(0);
                        }
                    }
                });
    }


    /**
     * 删除订单
     */
    private void deleteOrder(final String orderId, final int position) {
        LogUtils.log(position + "");
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(),
                R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.setTitle(R.string.delete_order);
        dialog.setMessage(R.string.is_delete_order);
        //为“确定”按钮注册监听事件
        dialog.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OkHttpUtils.post()
                        .url(Constant.DELETE_ORDER)
                        .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                        .addParams("order_sn", orderId)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                ResponseBean responseBean = GsonUtils.jsonToBean(response, ResponseBean.class);
                                ToastUtils.showToast(getActivity(), responseBean.getMsg());
                                if (responseBean.getCode() == 10000) {
                                    dataList.remove(position);
                                    adapter.notifyDataSetChanged();
                                    if (dataList.size() == 0 && orderStatus == 0) {
                                        llNoOrder.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        });
            }
        });
        //为“取消”按钮注册监听事件
        dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.create();
        dialog.show();
    }


    /**
     * 取消订单
     */
    private void cancelOrder(final String orderId) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(),
                R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.setTitle(getString(R.string.cancel_order));
        dialog.setMessage(R.string.is_cancel_order);
        //为“确定”按钮注册监听事件
        dialog.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OkHttpUtils.get()
                        .url(Constant.CANCEL_ORDER)
                        .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                        .addParams("order_sn", orderId)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                ResponseBean responseBean = GsonUtils.jsonToBean(response, ResponseBean.class);
                                ToastUtils.showToast(getActivity(), responseBean.getMsg());
                                if (responseBean.getCode() == 10000) {
                                    MobclickAgent.onEvent(getActivity(), "cancel_order");
                                    onStateChangeListener.onStateChange(0);
                                }
                            }
                        });
            }
        });
        //为“取消”按钮注册监听事件
        dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.create();
        dialog.show();
    }

    /**
     * @param orderId 订单详情
     */
    private void orderDetail(String orderId) {
        Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
        intent.putExtra("id", orderId);
        startActivity(intent);
    }

    /**
     * @param part 配件
     */
    private void goodsPart(List<OrderInfoBean.DataBean.ChildOrdersBean.PartBean> part) {
        View popupView = getActivity().getLayoutInflater().inflate(R.layout.ppw_goods_part, null);
        final PopupWindow mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mPopupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        DisplayUtils.setBackgroundAlpha(getActivity(), true);

        RecyclerView rvPart = popupView.findViewById(R.id.rv_goods_part);
        rvPart.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPart.addItemDecoration(new RVItemDecoration((int) DisplayUtils.dp2px(getActivity(), 10)));
        CommonAdapter<OrderInfoBean.DataBean.ChildOrdersBean.PartBean> adapter = new
                CommonAdapter<OrderInfoBean.DataBean.ChildOrdersBean.PartBean>(getActivity(),
                        R.layout.rv_goods_part_item, part) {
                    @Override
                    protected void convert(ViewHolder holder, OrderInfoBean.DataBean.ChildOrdersBean.PartBean partBean, int position) {
                        holder.setText(R.id.tv_part_title, partBean.getPart_name());
                        holder.setText(R.id.tv_part_name, partBean.getPart_value());
                    }
                };
        rvPart.setAdapter(adapter);

        TextView tvClose = popupView.findViewById(R.id.tv_close);

        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                DisplayUtils.setBackgroundAlpha(getActivity(), false);
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
                        viewLoading.smoothToHide();
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
                            viewLoading.smoothToHide();
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
