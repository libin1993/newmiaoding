package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.umeng.analytics.MobclickAgent;
import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.ConfirmOrderBean;
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
 * Author：Libin on 2016/10/7 14:54
 * Email：1993911441@qq.com
 * Describe：确认订单
 */
public class ConfirmOrderActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_phone)
    TextView tvUserPhone;
    @BindView(R.id.tv_user_address)
    TextView tvProvinceAddress;
    @BindView(R.id.tv_need_pay)
    TextView tvNeedPay;
    @BindView(R.id.tv_confirm_order)
    TextView tvConfirmOrder;
    @BindView(R.id.rv_confirm_order)
    RecyclerView rvConfirmOrder;
    @BindView(R.id.tv_no_address)
    TextView tvNoAddress;
    @BindView(R.id.tv_goods_total)
    TextView tvGoodsTotal;
    @BindView(R.id.tv_freight)
    TextView tvFreight;
    @BindView(R.id.tv_discount_money)
    TextView tvCouponDiscount;
    @BindView(R.id.ll_first_order)
    LinearLayout llFirstOrder;
    @BindView(R.id.view_first_order)
    View viewFirstOrder;
    @BindView(R.id.ll_select_coupon)
    LinearLayout llSelectCoupon;
    @BindView(R.id.tv_coupon_content)
    TextView tvCouponContent;
    @BindView(R.id.ll_user_address)
    LinearLayout llUserAddress;
    @BindView(R.id.tv_default_address)
    TextView tvDefaultAddress;
    @BindView(R.id.rl_select_address)
    RelativeLayout rlSelectAddress;
    @BindView(R.id.rl_add_address)
    RelativeLayout rlAddAddress;
    @BindView(R.id.img_load_error)
    ImageView imgLoadingError;
    @BindView(R.id.tv_measure_username)
    TextView tvMeasureName;
    @BindView(R.id.tv_measure_user_height)
    TextView tvMeasureHeight;
    @BindView(R.id.tv_measure_user_weight)
    TextView tvMeasurerWeight;
    @BindView(R.id.ll_measure)
    LinearLayout llMeasure;
    @BindView(R.id.view_loading)
    AVLoadingIndicatorView viewLoading;

    //购物车id
    private String cartIds;
    private ConfirmOrderBean confirmOrderBean;
    //收货地址
    private ConfirmOrderBean.DataBean.AddressListBean addressListBean;
    //优惠券id
    private String couponId;
    //优惠券金额
    private String couponMoney;
    //优惠券详情
    private String couponContent;
    //优惠券最低使用金额
    private String couponMinMoney;
    //优惠券适用的商品
    private String goodsIds;
    //显示优惠券优惠金额
    private double displayCoupon = 0.00;
    //实际优惠券优惠金额
    private double actualCoupon = 0.00;
    //商品adapter
    private CommonAdapter<ConfirmOrderBean.DataBean.CarListBean> adapter;


    //用户未创建收货地址，新建地址
    private boolean isNoAddress;
    //商品id
    private String goodsId;
    //商品名称
    private String goodsName;
    private String logId;
    private long goodsTime;
    private long dingzhiTime;

    private ConfirmOrderBean.DataBean.LtArrBean measureBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        viewLoading.smoothToShow();
        getData();
        initData();
    }


    /**
     * 获取网络数据
     */
    private void initData() {
        OkHttpUtils.get()
                .url(Constant.ORDER_INFO)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("cart_id_s", cartIds)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        imgLoadingError.setBackgroundColor(Color.WHITE);
                        imgLoadingError.setVisibility(View.VISIBLE);
                        viewLoading.smoothToHide();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        imgLoadingError.setVisibility(View.GONE);
                        viewLoading.smoothToHide();
                        confirmOrderBean = GsonUtils.jsonToBean(response, ConfirmOrderBean.class);
                        if (confirmOrderBean.getData() != null) {
                            initView();
                        }
                    }
                });

    }


    /**
     * 购物车信息
     */
    private void getData() {
        tvHeaderTitle.setText(R.string.confirm_order_page);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            cartIds = bundle.getString("cart_id");
            logId = bundle.getString("log_id");
            goodsTime = bundle.getLong("goods_time");
            dingzhiTime = bundle.getLong("dingzhi_time");
            goodsId = bundle.getString("goods_id");
            goodsName = bundle.getString("goods_name");
        }
    }

    /**
     * 加载视图
     */
    private void initView() {
        //无地址，提示用户新建地址
        if (confirmOrderBean.getData().getAddress_list() == null) {
            isNoAddress = true;
            addressListBean = null;
        } else {
            isNoAddress = false;
            addressListBean = confirmOrderBean.getData().getAddress_list();
        }

        if (confirmOrderBean.getData().getLt_arr() != null) {
            measureBean = confirmOrderBean.getData().getLt_arr();
        }

        initAddress();
        initMeasureData();
        initCoupon();


        MyLinearLayoutManager linearLayoutManager = new MyLinearLayoutManager(this);
        linearLayoutManager.setScrollEnabled(false);
        rvConfirmOrder.setLayoutManager(linearLayoutManager);
        adapter = new CommonAdapter<ConfirmOrderBean.DataBean.CarListBean>(this,
                R.layout.listitem_shopping_cart, confirmOrderBean.getData().getCar_list()) {
            @Override
            protected void convert(final ViewHolder holder, final ConfirmOrderBean.DataBean.CarListBean
                    carListBean, final int position) {
                holder.setVisible(R.id.checkbox_goods_select, false);
                holder.setVisible(R.id.view_cart_divide, true);
                Glide.with(ConfirmOrderActivity.this)
                        .load(Constant.IMG_HOST + carListBean.getImg_info())
                        .placeholder(R.mipmap.place_holder_news)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into((ImageView) holder.getView(R.id.img_item_goods));
                holder.setVisible(R.id.tv_goods_count, false);
                holder.setVisible(R.id.ll_cart_edit, false);
                holder.setVisible(R.id.ll_cart_edit1, true);
                holder.setText(R.id.tv_goods_name, carListBean.getGoods_name());
                TextView tvContent = holder.getView(R.id.tv_goods_content);


                String parts = "";
                switch (carListBean.getCategory_id()) {
                    case 1:
                        for (ConfirmOrderBean.DataBean.CarListBean.PartBean partBean : carListBean.getPart()) {
                            parts += partBean.getPart_name() + ":" + partBean.getPart_value() + ";";
                        }
                        parts = parts.substring(0, parts.length() - 1);
                        break;
                    case 2:
                        for (ConfirmOrderBean.DataBean.CarListBean.SkuBean skuBean : carListBean.getSku()) {
                            parts += skuBean.getType() + ":" + skuBean.getValue() + ";";
                        }
                        parts = parts.substring(0, parts.length() - 1);
                        break;
                }
                tvContent.setText(parts);


                holder.setText(R.id.tv_goods_price, "¥" + carListBean.getSell_price());
                holder.setText(R.id.tv_cart_count1, carListBean.getGoods_num() + "");
                holder.setVisible(R.id.view_cart, true);

                tvContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (carListBean.getCategory_id() == 1 && carListBean.getPart() != null) {
                            goodsPart(carListBean.getPart());
                        }
                    }
                });
                //增加购物车数量
                holder.getView(R.id.tv_cart_add1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int count = confirmOrderBean.getData().getCar_list().get(position).getGoods_num();
                        changeCartCount(position, count + 1);

                    }
                });
                //减少购物车数量
                holder.getView(R.id.tv_cart_reduce1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int count = confirmOrderBean.getData().getCar_list().get(position).getGoods_num();
                        if (count > 1) {
                            changeCartCount(position, count - 1);
                        }
                    }
                });
            }
        };
        rvConfirmOrder.setAdapter(adapter);
    }

    /**
     * 收货地址
     */
    private void initAddress() {
        if (addressListBean == null) {
            if (isNoAddress) {
                tvNoAddress.setText(R.string.add_address);
            } else {
                tvNoAddress.setText(R.string.select_address);
            }
            rlAddAddress.setVisibility(View.VISIBLE);
            llUserAddress.setVisibility(View.GONE);
            tvDefaultAddress.setVisibility(View.GONE);
        } else {
            rlAddAddress.setVisibility(View.GONE);
            llUserAddress.setVisibility(View.VISIBLE);

            tvUserName.setText(addressListBean.getAccept_name());
            tvUserPhone.setText(addressListBean.getPhone());
            tvProvinceAddress.setText(addressListBean.getProvince() + addressListBean.getCity() +
                    addressListBean.getArea() + addressListBean.getAddress());
        }
    }

    /**
     * 量体数据
     */
    private void initMeasureData() {
        if (measureBean != null) {
            tvMeasureName.setText(measureBean.getName());
            tvMeasureHeight.setText(measureBean.getHeight() + "");
            tvMeasurerWeight.setText(measureBean.getWeight() + "");
        }
    }


    /**
     * 是否选择优惠券或礼品卡
     */
    private void initCoupon() {
        if (couponId == null) {
            tvCouponContent.setText(R.string.please_select_coupon);
            tvCouponContent.setTextColor(ContextCompat.getColor(ConfirmOrderActivity.this,
                    R.color.dark_gray_22));
        } else {
            tvCouponContent.setText(couponContent);
            tvCouponContent.setTextColor(0xffea3a37);
        }

        getTotalPrice();
    }


    /**
     * @param position 改变购物车数量
     */
    private void changeCartCount(final int position, final int currentCount) {
        OkHttpUtils.post()
                .url(Constant.CART_COUNT)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("cart_id", confirmOrderBean.getData().getCar_list().get(position).getCart_id() + "")
                .addParams("buy_num", String.valueOf(currentCount))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ResponseBean responseBean = GsonUtils.jsonToBean(response, ResponseBean.class);
                        if (responseBean.getCode() == 10000) {
                            confirmOrderBean.getData().getCar_list().get(position).setGoods_num(currentCount);
                            adapter.notifyDataSetChanged();

                            if (couponId != null) {
                                //已选择优惠券，判断当前优惠券是否选择
                                if (!isCouponAvailable()) {
                                    couponId = null;
                                }
                            }
                            initCoupon();

                        } else {
                            ToastUtils.showToast(ConfirmOrderActivity.this, responseBean.getMsg());
                        }

                    }
                });
    }

    /**
     * 是否达到优惠券使用要求
     * 商品数量减少达不到关联优惠券要求，会取消优惠券
     */
    private boolean isCouponAvailable() {
        double maxPrice = 0.00;
        int count = 0;
        //该商品是否包含在优惠券中
        if (!TextUtils.isEmpty(goodsIds)) {
            String[] split = goodsIds.split(",");
            for (int i = 0; i < confirmOrderBean.getData().getCar_list().size(); i++) {
                if (Arrays.asList(split).contains(confirmOrderBean.getData().getCar_list().get(i)
                        .getCart_id() + "")) {
                    double price = Double.parseDouble(confirmOrderBean.getData().getCar_list().get(i).getSell_price());
                    int num = confirmOrderBean.getData().getCar_list().get(i).getGoods_num();
                    maxPrice = BigDecimalUtils.add(maxPrice, BigDecimalUtils.mul(price, num));
                    count++;
                }
            }
        }

        //可使用优惠券商品的总价格与优惠券最低消费金额
        if (maxPrice >= Double.parseDouble(couponMinMoney)) {
            if (Double.parseDouble(couponMoney) <= BigDecimalUtils.sub(maxPrice, BigDecimalUtils.mul(0.01, count))) {
                displayCoupon = Double.parseDouble(couponMoney);
                actualCoupon = Double.parseDouble(couponMoney);
            } else {
                displayCoupon = maxPrice > Double.parseDouble(couponMoney) ? Double.parseDouble(couponMoney) : maxPrice;
                actualCoupon = BigDecimalUtils.sub(maxPrice, BigDecimalUtils.mul(0.01, count));
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取总价格
     */
    private String getTotalPrice() {
        double totalPrice = 0.00;
        for (int i = 0; i < confirmOrderBean.getData().getCar_list().size(); i++) {
            double price = Double.parseDouble(confirmOrderBean.getData().getCar_list().get(i).getSell_price());
            int num = confirmOrderBean.getData().getCar_list().get(i).getGoods_num();
            totalPrice = BigDecimalUtils.add(totalPrice, BigDecimalUtils.mul(price, num));
        }
        tvGoodsTotal.setText("¥" + DisplayUtils.decimalFormat(totalPrice));
        //优惠金额
        if (couponId != null) {
            totalPrice = BigDecimalUtils.sub(totalPrice, actualCoupon);
            tvCouponDiscount.setText("- ¥" + DisplayUtils.decimalFormat(displayCoupon));
        }

        tvNeedPay.setText("¥" + DisplayUtils.decimalFormat(totalPrice));
        return DisplayUtils.decimalFormat(totalPrice);
    }

    /**
     * @param part 配件
     */
    private void goodsPart(final List<ConfirmOrderBean.DataBean.CarListBean.PartBean> part) {
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
        CommonAdapter<ConfirmOrderBean.DataBean.CarListBean.PartBean> adapter = new
                CommonAdapter<ConfirmOrderBean.DataBean.CarListBean.PartBean>(this,
                        R.layout.rv_goods_part_item, part) {
                    @Override
                    protected void convert(ViewHolder holder, ConfirmOrderBean.DataBean.CarListBean.PartBean partBean, int position) {
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
                DisplayUtils.setBackgroundAlpha(ConfirmOrderActivity.this, false);
            }
        });
    }

    @OnClick({R.id.img_header_back, R.id.rl_select_address, R.id.tv_confirm_order,
            R.id.ll_select_coupon, R.id.img_load_error, R.id.ll_measure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
//                customGoodsLog();
                finish();
                break;
            case R.id.rl_select_address:
                if (addressListBean == null) {
                    if (isNoAddress) {
                        //用户为创建收货地址，点击跳转新建地址页面
                        Intent intent = new Intent(ConfirmOrderActivity.this, AddAddressActivity.class);
                        intent.putExtra("type", 1);
                        startActivityForResult(intent, 2);
                    } else {
                        //用户已选择地址被删除，点击重新选择地址
                        Intent intent1 = new Intent(ConfirmOrderActivity.this, DeliveryAddressActivity.class);
                        intent1.putExtra("type", 2);
                        startActivityForResult(intent1, 3);
                    }
                } else {
                    //已选择收货地址，点击跳转选择地址页面
                    Intent intent = new Intent(ConfirmOrderActivity.this, DeliveryAddressActivity.class);
                    intent.putExtra("type", 2);
                    intent.putExtra("address_id", addressListBean.getId());
                    startActivityForResult(intent, 3);
                }
                break;
            case R.id.tv_confirm_order:
                if (addressListBean == null) {
                    ToastUtils.showToast(this, getString(R.string.select_address));
                } else {
                    confirmOrder();
                }
                break;
            case R.id.ll_select_coupon:
                Intent intent = new Intent(this, SelectCouponActivity.class);
                intent.putExtra("cart_ids", cartIds);
                startActivityForResult(intent, 1);
                break;
            case R.id.img_load_error:
                initData();
                break;
            case R.id.ll_measure:

                Intent intent1 = new Intent(ConfirmOrderActivity.this, MeasureDataActivity.class);
                intent1.putExtra("type", 2);
                startActivity(intent1);
                break;
        }
    }


    /**
     * 确认下单
     */
    private void confirmOrder() {
        Map<String, String> map = new HashMap<>();
        map.put("token", SharedPreferencesUtils.getStr(this, "token"));
        map.put("cart_id_s", cartIds);
        if (couponId != null) {
            map.put("ticket_record_id", couponId);
        }
        map.put("address_id", String.valueOf(addressListBean.getId()));
        if (measureBean != null) {
            map.put("volume_id", String.valueOf(measureBean.getId()));
        }

        OkHttpUtils.post()
                .url(Constant.CONFIRM_ORDER)
                .params(map)
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
                                String orderId = jsonObject.getString("order_sn");
                                if (orderId != null) {
                                    //下单事件监听
                                    MobclickAgent.onEvent(ConfirmOrderActivity.this, "place_order");
                                    //下单成功，结束前面的页面
                                    EventBus.getDefault().post("order_success");
                                    Intent intent = new Intent(ConfirmOrderActivity.this, PayOrderActivity.class);
                                    intent.putExtra("order_id", orderId);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                ToastUtils.showToast(ConfirmOrderActivity.this, msg);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            //优惠券
            case 1:
                switch (resultCode) {
                    //点击返回按钮
                    case 1:
                        initCoupon();
                        break;
                    //不使用优惠券
                    case 2:
                        couponId = null;
                        initCoupon();
                        break;
                    //使用优惠券
                    case 3:
                        couponId = data.getStringExtra("coupon_id");
                        couponMoney = data.getStringExtra("coupon_money");
                        couponContent = data.getStringExtra("coupon_content");
                        couponMinMoney = data.getStringExtra("coupon_min_money");
                        goodsIds = data.getStringExtra("cart_ids");
                        isCouponAvailable();
                        initCoupon();
                        break;
                }
                break;
            //新增地址
            case 2:
                if (resultCode == 1) {
                    getAddress(data);
                }
                break;
            //选择地址
            case 3:
                switch (resultCode) {
                    //已选择地址
                    case 1:
                        getAddress(data);
                        break;
                    //收货地址全部被删除，重新创建地址
                    case 2:
                        addressListBean = null;
                        isNoAddress = true;
                        initAddress();
                        break;
                    //已选择地址被删除，重新选择收货地址
                    case 3:
                        addressListBean = null;
                        isNoAddress = false;
                        initAddress();
                        break;
                    //收货地址被修改，刷新数据
                    case 4:
                        getAddress(data);
                        break;
                }
                break;
        }
    }

    /**
     * @param intent 地址返回值
     */
    private void getAddress(Intent intent) {
        addressListBean = (ConfirmOrderBean.DataBean.AddressListBean) intent.getSerializableExtra("address");
        initAddress();
    }

    /**
     * 商品订制跟踪
     */
    private void customGoodsLog() {
        if (logId != null) {
            OkHttpUtils.post()
                    .url(Constant.GOODS_LOG)
                    .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                    .addParams("id", logId)
                    .addParams("goods_time", String.valueOf(DateUtils.getCurrentTime() - goodsTime))
                    .addParams("dingzhi_time", String.valueOf(dingzhiTime))
                    .addParams("goods_id", goodsId)
                    .addParams("goods_name", goodsName)
                    .addParams("click_dingzhi", "1")
                    .addParams("click_pay", "0")
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

    /**
     * @param ltArrBean 选择量体数据回调
     */
    @Subscribe
    public void selectMeasureData(ConfirmOrderBean.DataBean.LtArrBean ltArrBean) {
        measureBean = ltArrBean;
        initMeasureData();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
