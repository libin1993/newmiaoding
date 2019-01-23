package cn.cloudworkshop.miaoding.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.umeng.analytics.MobclickAgent;
import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.OrderDetailsBean;
import cn.cloudworkshop.miaoding.bean.ResponseBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.BigDecimalUtils;
import cn.cloudworkshop.miaoding.utils.DateUtils;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.MyLinearLayoutManager;
import cn.cloudworkshop.miaoding.utils.RVItemDecoration;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2016/10/27 17:14
 * Email：1993911441@qq.com
 * Describe：订单详情
 */
public class OrderDetailActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_pay_time)
    TextView tvPayTime;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_status_order)
    TextView tvOrderStatus;
    @BindView(R.id.rv_order_details)
    RecyclerView rvOrderDetails;
    @BindView(R.id.tv_order_pay_money)
    TextView tvOrderPayMoney;
    @BindView(R.id.tv_order_cancel)
    TextView tvOrderCancel;
    @BindView(R.id.tv_order_user_name)
    TextView tvOrderUserName;
    @BindView(R.id.tv_order_user_phone)
    TextView tvOrderUserPhone;
    @BindView(R.id.tv_order_user_address)
    TextView tvOrderUserAddress;
    @BindView(R.id.tv_order_pay_style)
    TextView tvOrderPayStyle;
    @BindView(R.id.tv_order_total_money)
    TextView tvOrderTotalMoney;
    @BindView(R.id.tv_order_need_pay)
    TextView tvOrderNeedPay;
    @BindView(R.id.tv_order_after)
    TextView tvOrderAfter;
    @BindView(R.id.ll_order_address)
    LinearLayout llOrderAddress;
    @BindView(R.id.tv_coupon_discount)
    TextView tvCouponDiscount;
    @BindView(R.id.tv_giftcard_discount)
    TextView tvGiftcardDiscount;
    @BindView(R.id.tv_other_discount)
    TextView tvOtherDiscount;
    @BindView(R.id.view_loading)
    AVLoadingIndicatorView viewLoading;
    //订单id
    private String orderId;
    private OrderDetailsBean orderBean;
    //倒计时时间
    private long recLen;
    Timer timer = new Timer();

    private MyTimerTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);

        getData();
        viewLoading.smoothToShow();
        initData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    /**
     * 获取网络数据
     */
    private void initData() {
        OkHttpUtils.get()
                .url(Constant.ORDER_DETAIL)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("order_sn", orderId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        viewLoading.smoothToHide();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        viewLoading.smoothToHide();
                        orderBean = GsonUtils.jsonToBean(response, OrderDetailsBean.class);
                        if (orderBean.getData() != null) {
                            initView();
                        }
                    }
                });
    }

    /**
     * 加载视图
     */
    private void initView() {
        tvOrderTime.setText(orderBean.getData().getCreate_time());
        recLen = DateUtils.getTime("yyyy-MM-dd HH:mm:ss",
                orderBean.getData().getCreate_time()) / 1000 + 3600 - System.currentTimeMillis() / 1000;
        tvOrderStatus.setText(orderBean.getData().getStatus_text());
        switch (orderBean.getData().getStatus()) {
            case 1:
                if (task == null) {
                    task = new MyTimerTask();
                    timer.schedule(task, 1000, 1000);
                }
                tvPayTime.setTextColor(ContextCompat.getColor(this, R.color.dark_red));
                tvOrderCancel.setText(R.string.cancel_order);
                tvOrderPayMoney.setText(R.string.pay);
                tvOrderCancel.setVisibility(View.VISIBLE);
                tvOrderPayMoney.setVisibility(View.VISIBLE);
                tvOrderAfter.setVisibility(View.GONE);
                break;
            case 2:
                tvPayTime.setText(orderBean.getData().getPay_time());
                tvOrderCancel.setText(R.string.notice_send_goods);
                tvOrderCancel.setVisibility(View.VISIBLE);
                tvOrderPayMoney.setVisibility(View.GONE);
                tvOrderAfter.setVisibility(View.GONE);
                break;
            case 3:
                tvPayTime.setText(orderBean.getData().getPay_time());
                tvOrderAfter.setVisibility(View.GONE);
                tvOrderAfter.setText(R.string.after_sale);
                tvOrderCancel.setVisibility(View.VISIBLE);
                tvOrderPayMoney.setVisibility(View.VISIBLE);
                tvOrderCancel.setText(R.string.view_logistics);
                tvOrderPayMoney.setText(R.string.confirm_receive);
                break;
            case 4:
                tvPayTime.setText(orderBean.getData().getPay_time());
                tvOrderAfter.setVisibility(View.GONE);
                tvOrderAfter.setText(R.string.after_sale);
                tvOrderCancel.setVisibility(View.VISIBLE);
                tvOrderCancel.setText(R.string.buy_again);
                tvOrderPayMoney.setVisibility(View.GONE);
//                if (orderBean.getData().getOrder_comment().getId() == 0) {
//                    tvOrderPayMoney.setVisibility(View.VISIBLE);
//                    tvOrderPayMoney.setText(R.string.evaluate);
//                } else {
//                    tvOrderPayMoney.setVisibility(View.GONE);
//                }
                break;
            case 5:
                tvPayTime.setText(R.string.cancelled);
                tvOrderCancel.setVisibility(View.VISIBLE);
                tvOrderAfter.setVisibility(View.GONE);
                tvOrderPayMoney.setVisibility(View.GONE);
                tvOrderCancel.setText(R.string.delete_order);
                break;
            default:
                if (!TextUtils.isEmpty(orderBean.getData().getPay_time())) {
                    tvPayTime.setText(orderBean.getData().getPay_time());
                } else {
                    tvPayTime.setText(orderBean.getData().getStatus_text());
                }
                tvOrderCancel.setVisibility(View.GONE);
                tvOrderAfter.setVisibility(View.GONE);
                tvOrderPayMoney.setVisibility(View.GONE);
                break;
        }
        tvOrderNum.setText(orderBean.getData().getOrder_sn());


        tvOrderUserName.setText(orderBean.getData().getAccept_name());
        tvOrderUserPhone.setText(orderBean.getData().getAddress_phone());
        tvOrderUserAddress.setText(orderBean.getData().getProvince()
                + orderBean.getData().getCity()
                + orderBean.getData().getArea()
                + orderBean.getData().getAddress());


        tvOrderTotalMoney.setText("¥" + orderBean.getData().getOrder_amount());

        tvCouponDiscount.setText("¥" + orderBean.getData().getTicket_reduce_money());
        tvGiftcardDiscount.setText("¥" + orderBean.getData().getGiftcard_eq_money());

        BigDecimal otherDiscount = new BigDecimal(orderBean.getData().getOrder_amount())
                .subtract(new BigDecimal(orderBean.getData().getTicket_reduce_money()))
                .subtract(new BigDecimal(orderBean.getData().getPayable_amount()));

        tvOtherDiscount.setText("¥" + otherDiscount.toString());
        tvOrderNeedPay.setTypeface(DisplayUtils.setTextType(this));
        tvOrderNeedPay.setText("¥" + BigDecimalUtils.sub(orderBean.getData().getPayable_amount(),
                orderBean.getData().getGiftcard_eq_money()));

        tvOrderPayStyle.setText(orderBean.getData().getPay_type());

        MyLinearLayoutManager linearLayoutManager = new MyLinearLayoutManager(this);
        linearLayoutManager.setScrollEnabled(false);
        rvOrderDetails.setLayoutManager(linearLayoutManager);
        CommonAdapter<OrderDetailsBean.DataBean.ChildOrdersBean> adapter = new CommonAdapter<
                OrderDetailsBean.DataBean.ChildOrdersBean>(this,
                R.layout.listitem_shopping_cart, orderBean.getData().getChildOrders()) {
            @Override
            protected void convert(ViewHolder holder, final OrderDetailsBean.DataBean.ChildOrdersBean
                    childOrdersBean, final int position) {
                holder.setVisible(R.id.checkbox_goods_select, false);
                holder.setVisible(R.id.view_cart_divide, true);
                Glide.with(OrderDetailActivity.this)
                        .load(Constant.IMG_HOST + childOrdersBean.getImg_info())
                        .placeholder(R.mipmap.place_holder_news)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into((ImageView) holder.getView(R.id.img_item_goods));
                TextView tvGoodsName = holder.getView(R.id.tv_goods_name);
                TextView tvContent = holder.getView(R.id.tv_goods_content);
                tvGoodsName.setText(childOrdersBean.getGoods_name());
                tvGoodsName.setTypeface(DisplayUtils.setTextType(mContext));

                String parts = "";
                switch (childOrdersBean.getCategory_id()) {
                    case 1:
                        for (OrderDetailsBean.DataBean.ChildOrdersBean.PartBean partBean : childOrdersBean.getPart()) {
                            parts += partBean.getPart_name() + ":" + partBean.getPart_value() + ";";
                        }
                        parts = parts.substring(0, parts.length() - 1);
                        break;
                    case 2:
                        for (OrderDetailsBean.DataBean.ChildOrdersBean.SkuBean skuBean : childOrdersBean.getSku()) {
                            parts += skuBean.getType() + ":" + skuBean.getValue() + ";";
                        }
                        parts = parts.substring(0, parts.length() - 1);
                        break;
                }

                tvContent.setText(parts);

                holder.setText(R.id.tv_goods_price, "¥" + childOrdersBean.getSell_price());
                holder.setText(R.id.tv_goods_count, "x" + childOrdersBean.getGoods_num());
                holder.setVisible(R.id.view_cart, true);

                tvContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (childOrdersBean.getCategory_id() == 1 && childOrdersBean.getPart() != null) {
                            goodsPart(childOrdersBean.getPart());
                        }
                    }
                });
            }

        };
        rvOrderDetails.setAdapter(adapter);
    }

    /**
     * @param part 配件
     */
    private void goodsPart(List<OrderDetailsBean.DataBean.ChildOrdersBean.PartBean> part) {
        View popupView = getLayoutInflater().inflate(R.layout.ppw_goods_part, null);
        final PopupWindow mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        DisplayUtils.setBackgroundAlpha(this, true);

        RecyclerView rvPart = popupView.findViewById(R.id.rv_goods_part);
        rvPart.setLayoutManager(new LinearLayoutManager(this));
        rvPart.addItemDecoration(new RVItemDecoration((int) DisplayUtils.dp2px(this, 10)));
        CommonAdapter<OrderDetailsBean.DataBean.ChildOrdersBean.PartBean> adapter = new
                CommonAdapter<OrderDetailsBean.DataBean.ChildOrdersBean.PartBean>(OrderDetailActivity.this,
                        R.layout.rv_goods_part_item, part) {
                    @Override
                    protected void convert(ViewHolder holder, OrderDetailsBean.DataBean
                            .ChildOrdersBean.PartBean partBean, int position) {
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
                DisplayUtils.setBackgroundAlpha(OrderDetailActivity.this, false);
            }
        });
    }


    private void getData() {
        tvHeaderTitle.setText(R.string.order_detail);

        Intent intent = getIntent();
        orderId = intent.getStringExtra("id");

    }

    @OnClick({R.id.img_header_back, R.id.tv_order_pay_money, R.id.tv_order_cancel, R.id.tv_order_after})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                if (timer != null && task != null) {
                    timer.cancel();
                    task.cancel();
                }
                finish();
                break;
            case R.id.tv_order_pay_money:
                payOrder();
                break;
            case R.id.tv_order_cancel:
                controlOrder();
                break;
            case R.id.tv_order_after:
                afterSale();
                break;
        }
    }

    //定时器
    private class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {
                    recLen--;
                    if (recLen > 0) {
                        tvPayTime.setTextColor(ContextCompat.getColor(OrderDetailActivity.this, R.color.dark_red));
                        tvPayTime.setText(getString(R.string.please_in) + recLen / 60 + getString(R.string.minute) + recLen % 60 + getString(R.string.second) + getString(R.string.please_pay));
                    } else {
                        if (timer != null && task != null) {
                            timer.cancel();
                            task.cancel();
                        }
                        finish();
                        ToastUtils.showToast(OrderDetailActivity.this, getString(R.string.order_expired));
                    }
                }
            });
        }
    }

    /**
     * 售后服务
     */
    private void afterSale() {
        Intent intent1 = new Intent(this, AfterSalesActivity.class);
        intent1.putExtra("order_id", orderId);
        startActivity(intent1);
    }

    private void payOrder() {
        switch (orderBean.getData().getStatus()) {
            case 1:
                Intent intent = new Intent(this, PayOrderActivity.class);
                intent.putExtra("order_id", orderBean.getData().getOrder_sn());
                startActivity(intent);
                break;
            case 3:
                confirmReceive();
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

    private void controlOrder() {
        switch (orderBean.getData().getStatus()) {
            case 1:
                cancelOrder();
                break;
            case 2:
                ToastUtils.showToast(this, getString(R.string.has_notice));
                break;
            case 3:
                Intent intent = new Intent(this, LogisticsActivity.class);
                intent.putExtra("order_id", orderBean.getData().getOrder_sn());
                intent.putExtra("number", orderBean.getData().getExpress_no());
                intent.putExtra("img_url", orderBean.getData().getChildOrders().get(0).getImg_info());
                startActivity(intent);
                break;
            case 4:
                buyAgain();
                break;
            case 5:
                deleteOrder();
                break;
        }
    }

    /**
     * 再次购买
     */
    private void buyAgain() {
        OkHttpUtils.post()
                .url(Constant.BUY_AGAIN)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
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
                                Intent intent = new Intent(OrderDetailActivity.this, ConfirmOrderActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("cart_id", cartId);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else {
                                ToastUtils.showToast(OrderDetailActivity.this, msg);
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
    private void confirmReceive() {
        OkHttpUtils.post()
                .url(Constant.CONFIRM_RECEIVE)
                .addParams("token", SharedPreferencesUtils.getStr(OrderDetailActivity.this, "token"))
                .addParams("order_sn", orderId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {


                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ResponseBean responseBean = GsonUtils.jsonToBean(response, ResponseBean.class);
                        ToastUtils.showToast(OrderDetailActivity.this, responseBean.getMsg());
                        if (responseBean.getCode() == 10000) {
                            MobclickAgent.onEvent(OrderDetailActivity.this, "trade_success");
                            finish();
                        }
                    }
                });
    }


    /**
     * 删除订单
     */
    private void deleteOrder() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.setTitle(R.string.delete_order);
        dialog.setMessage(R.string.is_delete_order);
        //为“确定”按钮注册监听事件
        dialog.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OkHttpUtils.post()
                        .url(Constant.DELETE_ORDER)
                        .addParams("token", SharedPreferencesUtils.getStr(OrderDetailActivity.this, "token"))
                        .addParams("order_sn", orderId)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                ResponseBean responseBean = GsonUtils.jsonToBean(response, ResponseBean.class);
                                ToastUtils.showToast(OrderDetailActivity.this, responseBean.getMsg());
                                if (responseBean.getCode() == 10000) {
                                    finish();
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
    private void cancelOrder() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.setTitle(getString(R.string.cancel_order));
        dialog.setMessage(R.string.is_cancel_order);
        //为“确定”按钮注册监听事件
        dialog.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OkHttpUtils.get()
                        .url(Constant.CANCEL_ORDER)
                        .addParams("token", SharedPreferencesUtils.getStr(OrderDetailActivity.this, "token"))
                        .addParams("order_sn", orderId)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                ResponseBean responseBean = GsonUtils.jsonToBean(response, ResponseBean.class);
                                ToastUtils.showToast(OrderDetailActivity.this, responseBean.getMsg());
                                if (responseBean.getCode() == 10000) {
                                    MobclickAgent.onEvent(OrderDetailActivity.this, "cancel_order");
                                    finish();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (timer != null && task != null) {
                timer.cancel();
                task.cancel();
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}


