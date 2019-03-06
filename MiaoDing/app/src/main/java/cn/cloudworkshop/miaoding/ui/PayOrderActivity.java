package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.application.MyApplication;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.PayOrderBean;
import cn.cloudworkshop.miaoding.bean.PayResultBean;
import cn.cloudworkshop.miaoding.bean.PayTypeBean;
import cn.cloudworkshop.miaoding.bean.ResponseBean;
import cn.cloudworkshop.miaoding.bean.WeChatPayBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.MyLinearLayoutManager;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2019/1/12 11:30
 * Email：1993911441@qq.com
 * Describe：确认支付
 */
public class PayOrderActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_header_next)
    TextView tvHeaderNext;
    @BindView(R.id.rv_pay_order)
    RecyclerView rvOrder;
    @BindView(R.id.switch_giftcard)
    Switch switchCard;
    @BindView(R.id.tv_giftcard_money)
    TextView tvCardMoney;
    @BindView(R.id.tv_actual_money)
    TextView tvActualMoney;
    @BindView(R.id.tv_exchange_card)
    TextView tvExchangeCard;
    @BindView(R.id.tv_pay_money)
    TextView tvPayMoney;
    @BindView(R.id.iv_more_order)
    ImageView ivMoreOrder;
    @BindView(R.id.ll_more_order)
    LinearLayout llMoreOrder;

    //订单号
    private String orderId;
    private List<PayOrderBean.DataBean> orderList = new ArrayList<>();
    private List<PayOrderBean.DataBean> dataList = new ArrayList<>();
    private CommonAdapter<PayOrderBean.DataBean> adapter;
    private PayOrderBean payOrderBean;
    //礼品卡余额
    private String giftCardMoney;
    //是否使用礼品卡
    private int useGiftCard = 1;
    //实付金额
    private String actualMoney;

    private static final int SDK_PAY_FLAG = 1;
    private IWXAPI api;
    private boolean isDisplayMore = false;
    private int payType = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_order);
        ButterKnife.bind(this);
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID);
        getData();
        initData();
        initView();
    }

    private void initView() {
        MyLinearLayoutManager myLinearLayoutManager = new MyLinearLayoutManager(this);
        myLinearLayoutManager.setScrollEnabled(false);
        rvOrder.setLayoutManager(myLinearLayoutManager);

        adapter = new CommonAdapter<PayOrderBean.DataBean>(
                this, R.layout.rv_pay_order_item, dataList) {
            @Override
            protected void convert(ViewHolder holder, PayOrderBean.DataBean dataBean, int position) {
                holder.setText(R.id.tv_order_no, dataBean.getOrder_sn());
                holder.setText(R.id.tv_order_coupon_discount, "优惠券减：¥" + dataBean.getTicket_reduce_money());
                BigDecimal otherDiscount = new BigDecimal(dataBean.getOrder_amount())
                        .subtract(new BigDecimal(dataBean.getTicket_reduce_money()))
                        .subtract(new BigDecimal(dataBean.getPayable_amount()));
                holder.setText(R.id.tv_order_other_discount, "其他扣减：¥" + otherDiscount.toString());
                holder.setText(R.id.tv_order_actual_money, "实需付款：¥" + dataBean.getReal_amount());
                holder.setText(R.id.tv_order_total_price, "¥" + dataBean.getOrder_amount());
            }
        };
        rvOrder.setAdapter(adapter);
    }

    private void initData() {
        OkHttpUtils.get()
                .url(Constant.CONFIRM_PAY)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("order_sn_s", orderId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        payOrderBean = GsonUtils.jsonToBean(response, PayOrderBean.class);
                        if (payOrderBean.getCode() == 10000) {
                            orderList.clear();
                            orderList.addAll(payOrderBean.getData());

                            dataList.clear();
                            if (orderList.size() > 3) {
                                isDisplayMore = false;
                                llMoreOrder.setVisibility(View.VISIBLE);
                                ivMoreOrder.setImageResource(R.mipmap.ic_more);
                                for (int i = 0; i < 3; i++) {
                                    dataList.add(orderList.get(i));
                                }
                            } else {
                                llMoreOrder.setVisibility(View.GONE);
                                dataList.addAll(orderList);
                            }

                            adapter.notifyDataSetChanged();
                            giftCardMoney = payOrderBean.getGiftcard_money();
                            initGiftCard();

                        }
                    }
                });
    }

    /**
     * 礼品卡
     */
    private void initGiftCard() {
        tvCardMoney.setText("礼品卡余额：¥" + giftCardMoney);

        if (switchCard.isChecked() && Double.parseDouble(giftCardMoney) != 0) {
            useGiftCard = 1;
        } else {
            useGiftCard = 0;
        }

        switchCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && Double.parseDouble(giftCardMoney) != 0) {
                    useGiftCard = 1;
                } else {
                    useGiftCard = 0;
                }

                iniPrice();
            }
        });

        iniPrice();
    }

    /**
     * 付款金额
     */
    private void iniPrice() {
        BigDecimal needPay = new BigDecimal("0");
        for (PayOrderBean.DataBean dataBean : orderList) {
            needPay = needPay.add(new BigDecimal(dataBean.getReal_amount()));
        }

        if (useGiftCard == 1) {
            BigDecimal subtract = needPay.subtract(new BigDecimal(giftCardMoney));
            if (subtract.doubleValue() > 0) {
                actualMoney = subtract.toString();
            } else {
                actualMoney = "0.00";
            }
        } else {
            actualMoney = needPay.toString();
        }
        tvActualMoney.setText("¥" + actualMoney);

    }

    private void getData() {
        orderId = getIntent().getStringExtra("order_id");
        tvHeaderTitle.setText("待处理订单");
        tvHeaderNext.setVisibility(View.VISIBLE);
        tvHeaderNext.setText("刷新");
        MyApplication.orderNo = orderId;
    }


    /**
     * 支付宝支付回调
     */
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResultBean payResultBean = new PayResultBean((Map<String, String>) msg.obj);
                    String resultInfo = payResultBean.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResultBean.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        ToastUtils.showToast(PayOrderActivity.this, getString(R.string.pay_success));
                        MobclickAgent.onEvent(PayOrderActivity.this, "pay");

                        Intent intent = new Intent(PayOrderActivity.this, AppointmentActivity.class);
                        intent.putExtra("type", "pay_success");
                        startActivity(intent);
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastUtils.showToast(PayOrderActivity.this, getString(R.string.pay_fail));

                        Intent intent = new Intent(PayOrderActivity.this, AppointmentActivity.class);
                        intent.putExtra("type", "pay_fail");
                        startActivity(intent);
                        finish();
                    }
                    break;
                }
            }
            return false;
        }
    });

    @OnClick({R.id.img_header_back, R.id.tv_header_next, R.id.tv_exchange_card, R.id.tv_pay_money,
            R.id.ll_more_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                finish();
                break;
            case R.id.tv_header_next:
                initData();
                break;
            case R.id.tv_exchange_card:
                if (payOrderBean != null) {
                    addGiftCard();
                }
                break;
            case R.id.tv_pay_money:
                if (payOrderBean != null) {
                    if (Double.parseDouble(actualMoney) > 0) {
                        payMoney();
                    } else {
                        giftCardPay();
                    }
                }
                break;
            case R.id.ll_more_order:
                if (isDisplayMore) {
                    isDisplayMore = false;
                    ivMoreOrder.setImageResource(R.mipmap.ic_more);

                    dataList.clear();
                    for (int i = 0; i < 3; i++) {
                        dataList.add(orderList.get(i));
                    }

                    adapter.notifyDataSetChanged();
                } else {
                    isDisplayMore = true;
                    ivMoreOrder.setImageResource(R.mipmap.ic_shrink);

                    dataList.clear();
                    dataList.addAll(orderList);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    /**
     * 礼品卡支付
     */
    public void giftCardPay() {
        OkHttpUtils.post()
                .url(Constant.GIFT_CARD_PAY)
                .addParams("order_sn_s", orderId)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ResponseBean responseBean = GsonUtils.jsonToBean(response, ResponseBean.class);
                        ToastUtils.showToast(PayOrderActivity.this, responseBean.getMsg());
                        Intent intent = new Intent(PayOrderActivity.this, AppointmentActivity.class);
                        if (responseBean.getCode() == 10000) {
                            intent.putExtra("type", "pay_success");
                        } else {
                            intent.putExtra("type", "pay_fail");
                        }
                        startActivity(intent);
                        finish();
                    }
                });
    }

    /**
     * 支付
     */
    public void payMoney() {
        final View popupView = getLayoutInflater().inflate(R.layout.ppw_pay_order, null);
        final PopupWindow mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        DisplayUtils.setBackgroundAlpha(this, true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                DisplayUtils.setBackgroundAlpha(PayOrderActivity.this, false);
            }
        });

        RecyclerView recyclerView = (RecyclerView) popupView.findViewById(R.id.rv_pay_type);
        TextView tvTotalPrice = (TextView) popupView.findViewById(R.id.tv_pay_price);
        TextView tvConfirmBuy = (TextView) popupView.findViewById(R.id.tv_pay_confirm);

        tvTotalPrice.setText(getString(R.string.total_price) + "   ¥" + actualMoney);


        final List<PayTypeBean> payList = new ArrayList<>();
        payList.add(new PayTypeBean(R.mipmap.icon_ali_pay, getString(R.string.alipay),
                getString(R.string.recommend_alipay)));
        payList.add(new PayTypeBean(R.mipmap.icon_wechat_pay, getString(R.string.wechat),
                getString(R.string.recommend_wechat)));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        final CommonAdapter<PayTypeBean> adapter = new CommonAdapter<PayTypeBean>(this,
                R.layout.listitem_pay_type, payList) {
            @Override
            protected void convert(ViewHolder holder, PayTypeBean payTypeEntity, int position) {
                holder.setImageResource(R.id.img_pay_type, payList.get(position).img);
                holder.setText(R.id.tv_pay_type, payList.get(position).payType);
                holder.setText(R.id.tv_pay_content, payList.get(position).payContent);
                if (payType == position) {
                    ((CheckBox) holder.getView(R.id.checkbox_pay_type)).setChecked(true);
                } else {
                    ((CheckBox) holder.getView(R.id.checkbox_pay_type)).setChecked(false);
                }
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                payType = holder.getLayoutPosition();
                adapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        tvConfirmBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (payType) {
                    case 0:
                        aliPay();
                        break;
                    case 1:
                        weChatPay();
                        break;
                }
                mPopupWindow.dismiss();
            }
        });


    }


    /**
     * 微信支付
     */
    private void weChatPay() {
        OkHttpUtils.post()
                .url(Constant.WE_CHAT_PAY)
                .addParams("order_sn_s", orderId)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("use_gift_card", useGiftCard + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        WeChatPayBean weChatPay = GsonUtils.jsonToBean(response, WeChatPayBean.class);
                        if (weChatPay.getCode() == 10000) {
                            if (!api.isWXAppInstalled()) {
                                ToastUtils.showToast(PayOrderActivity.this, getString(R.string.not_install_wechat));
                            }
                            if (!api.isWXAppSupportAPI()) {
                                ToastUtils.showToast(PayOrderActivity.this, getString(R.string.not_support_wechat));
                            }
                            PayReq req = new PayReq();
                            req.appId = weChatPay.getData().getAppid();
                            req.partnerId = weChatPay.getData().getPartnerid();
                            req.prepayId = weChatPay.getData().getPrepayid();
                            req.nonceStr = weChatPay.getData().getNoncestr();
                            req.timeStamp = weChatPay.getData().getTimestamp() + "";
                            req.packageValue = weChatPay.getData().getPackageX();
                            req.sign = weChatPay.getData().getSign();
                            api.sendReq(req);
                        } else {
                            ToastUtils.showToast(PayOrderActivity.this, weChatPay.getMsg());
                            Intent intent = new Intent(PayOrderActivity.this, OrderActivity.class);
                            intent.putExtra("page", 0);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }


    /**
     * 支付宝支付
     */
    private void aliPay() {
        OkHttpUtils.post()
                .url(Constant.ALI_PAY)
                .addParams("order_sn_s", orderId)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("use_gift_card", useGiftCard + "")
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
                            if (code == 10000) {
                                final String orderInfo = jsonObject.getString("data");
                                Runnable payRunnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        PayTask aliPay = new PayTask(PayOrderActivity.this);
                                        Map<String, String> result = aliPay.payV2(orderInfo, true);
                                        Message msg = new Message();
                                        msg.what = SDK_PAY_FLAG;
                                        msg.obj = result;
                                        mHandler.sendMessage(msg);
                                    }
                                };
                                Thread payThread = new Thread(payRunnable);
                                payThread.start();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    /**
     * 添加礼品卡
     */
    private void addGiftCard() {
        View popupView = getLayoutInflater().inflate(R.layout.ppw_add_card, null);
        final PopupWindow mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, -300);
        DisplayUtils.setBackgroundAlpha(this, true);

        final EditText etNumber = (EditText) popupView.findViewById(R.id.et_card_number);
        final EditText etPwd = (EditText) popupView.findViewById(R.id.et_card_pwd);
        TextView tvConfirm = (TextView) popupView.findViewById(R.id.tv_confirm_card);
        TextView tvCancel = (TextView) popupView.findViewById(R.id.tv_cancel_card);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etNumber.getText().toString().trim()) || TextUtils.isEmpty(etPwd.getText().toString().trim())) {
                    ToastUtils.showToast(PayOrderActivity.this, getString(R.string.input_no_pwd));
                } else {
                    exchangeCard(etNumber.getText().toString(), etPwd.getText().toString());
                    mPopupWindow.dismiss();
                }
            }

        });

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                DisplayUtils.setBackgroundAlpha(PayOrderActivity.this, false);
            }
        });

    }

    /**
     * @param number 兑换礼品卡金额
     */
    private void exchangeCard(String number, String code) {
        OkHttpUtils.post()
                .url(Constant.EXCHANGE_CARD)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("giftcard_no", number)
                .addParams("exchange_code", code)
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
                            ToastUtils.showToast(PayOrderActivity.this, msg);
                            if (code == 10000) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                giftCardMoney = data.getString("giftcard_money");

                                initGiftCard();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}


