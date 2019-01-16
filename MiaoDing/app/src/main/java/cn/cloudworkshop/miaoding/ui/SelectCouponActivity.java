package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
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
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.ResponseBean;
import cn.cloudworkshop.miaoding.bean.SelectCouponBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.DateUtils;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.MyLinearLayoutManager;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2016/12/17 11:28
 * Email：1993911441@qq.com
 * Describe：选择优惠券
 */
public class SelectCouponActivity extends BaseActivity {
    @BindView(R.id.rv_available_coupon)
    RecyclerView rvAvailable;
    @BindView(R.id.rv_unavailable_coupon)
    RecyclerView rvUnavailable;
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_not_use)
    TextView tvNotUse;
    @BindView(R.id.tv_disable_coupon)
    TextView tvDisableCoupon;
    @BindView(R.id.et_coupon_code)
    EditText etCouponCode;
    @BindView(R.id.tv_exchange)
    TextView tvExchange;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;

    private String cartIds;
    //优惠券数量
    private int couponNum;
    private List<SelectCouponBean.DataBean.ValidTicketsBean> usableList = new ArrayList<>();
    private List<SelectCouponBean.DataBean.InvalidTicketsBean> uselessList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seclect_coupon);
        ButterKnife.bind(this);
        getData();
        initData();
    }

    private void getData() {
        cartIds = getIntent().getStringExtra("cart_ids");
    }

    /**
     * 加载数据
     */
    private void initData() {
        tvHeaderTitle.setText(R.string.select_coupon);
        OkHttpUtils.get()
                .url(Constant.SELECT_COUPON)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("cart_id_s", cartIds)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        imgLoadError.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        imgLoadError.setVisibility(View.GONE);
                        SelectCouponBean couponBean = GsonUtils.jsonToBean(response, SelectCouponBean.class);
                        if (couponBean.getData().getValid_tickets() != null && couponBean.getData()
                                .getValid_tickets().size() > 0) {
                            usableList.addAll(couponBean.getData().getValid_tickets());
                        }

                        if (couponBean.getData().getInvalid_tickets() != null && couponBean.getData()
                                .getInvalid_tickets().size() > 0) {
                            uselessList.addAll(couponBean.getData().getInvalid_tickets());
                            tvDisableCoupon.setVisibility(View.VISIBLE);
                        }
                        couponNum = usableList.size();
                        initView();
                    }
                });

    }

    /**
     * 加载视图
     */
    private void initView() {

        etCouponCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etCouponCode.getText().toString().trim().length() > 0) {
                    tvExchange.setEnabled(true);
                    tvExchange.setBackgroundResource(R.drawable.btn_black_bg);
                } else {
                    tvExchange.setEnabled(false);
                    tvExchange.setBackgroundResource(R.drawable.btn_gray_bg);
                }
            }
        });

        MyLinearLayoutManager linearLayoutManager1 = new MyLinearLayoutManager(this);
        linearLayoutManager1.setScrollEnabled(false);
        rvAvailable.setLayoutManager(linearLayoutManager1);
        final CommonAdapter<SelectCouponBean.DataBean.ValidTicketsBean> usableAdapter = new CommonAdapter
                <SelectCouponBean.DataBean.ValidTicketsBean>(this, R.layout.listitem_coupon, usableList) {
            @Override
            protected void convert(ViewHolder holder, SelectCouponBean.DataBean.ValidTicketsBean validTicketsBean, int position) {
                holder.getView(R.id.ll_coupon_bg).setBackgroundResource(R.mipmap.icon_coupon_available);
                TextView tvMoney = holder.getView(R.id.tv_coupon_money);
                tvMoney.setTypeface(DisplayUtils.setTextType(SelectCouponActivity.this));
                tvMoney.setText("¥" + (int) Float.parseFloat(validTicketsBean.getMoney()));
                holder.setText(R.id.tv_coupon_range, validTicketsBean.getTitle());
                holder.setText(R.id.tv_coupon_discount, validTicketsBean.getRe_marks());
                String term = getString(R.string.validity_term) + "：" + DateUtils.formatTime(
                        "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", validTicketsBean.getS_time())
                        + getString(R.string.to) + DateUtils.formatTime("yyyy-MM-dd HH:mm:ss",
                        "yyyy-MM-dd", validTicketsBean.getE_time());
                holder.setText(R.id.tv_coupon_term, term);
            }

        };

        rvAvailable.setAdapter(usableAdapter);


        usableAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                Intent intent = new Intent();
                intent.putExtra("coupon_id", usableList.get(position).getId() + "");
                intent.putExtra("coupon_money", usableList.get(position).getMoney());
                intent.putExtra("coupon_content", usableList.get(position).getTitle()
                        + "(" + usableList.get(position).getRe_marks() + ")");
                intent.putExtra("coupon_min_money", usableList.get(position).getFull_money());
                intent.putExtra("cart_ids", usableList.get(position).getCart_id_s());
                //1:使用优惠券
                setResult(3, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });


        MyLinearLayoutManager linearLayoutManager2 = new MyLinearLayoutManager(this);
        linearLayoutManager2.setScrollEnabled(false);
        rvUnavailable.setLayoutManager(linearLayoutManager2);
        CommonAdapter<SelectCouponBean.DataBean.InvalidTicketsBean> uselessAdapter = new CommonAdapter
                <SelectCouponBean.DataBean.InvalidTicketsBean>(this, R.layout.listitem_coupon, uselessList) {
            @Override
            protected void convert(ViewHolder holder, SelectCouponBean.DataBean.InvalidTicketsBean invalidTicketsBean, int position) {
                holder.getView(R.id.ll_coupon_bg).setBackgroundResource(R.mipmap.icon_coupon_available);
                TextView tvMoney = holder.getView(R.id.tv_coupon_money);
                tvMoney.setTypeface(DisplayUtils.setTextType(SelectCouponActivity.this));
                tvMoney.setText("¥" + (int) Float.parseFloat(invalidTicketsBean.getMoney()));
                holder.setText(R.id.tv_coupon_range, invalidTicketsBean.getTitle());
                holder.setText(R.id.tv_coupon_discount, invalidTicketsBean.getRe_marks());
                String term = getString(R.string.validity_term) + "：" + DateUtils.formatTime(
                        "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", invalidTicketsBean.getS_time())
                        + getString(R.string.to) + DateUtils.formatTime("yyyy-MM-dd HH:mm:ss",
                        "yyyy-MM-dd", invalidTicketsBean.getE_time());
                holder.setText(R.id.tv_coupon_term, term);
            }

        };

        rvUnavailable.setAdapter(uselessAdapter);

    }

    @OnClick({R.id.img_header_back, R.id.tv_not_use, R.id.tv_exchange, R.id.img_load_error})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                //返回优惠券数量
                Intent intent = new Intent();
                intent.putExtra("coupon_num", couponNum);
                setResult(1, intent);
                finish();
                break;
            case R.id.tv_not_use:
                // 2: 不使用优惠券；3：使用优惠券
                Intent intent1 = new Intent();
                intent1.putExtra("coupon_num", couponNum);
                setResult(2, intent1);
                finish();
                break;
            case R.id.tv_exchange:
                exchangeCoupon();
                break;
            case R.id.img_load_error:
                initData();
                break;
        }
    }

    /**
     * 兑换优惠券
     */
    private void exchangeCoupon() {

        OkHttpUtils.post()
                .url(Constant.EXCHANGE_COUPON)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("exchange_code", etCouponCode.getText().toString().trim())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ResponseBean responseBean = GsonUtils.jsonToBean(response, ResponseBean.class);
                        if (responseBean.getCode() == 10000) {
                            usableList.clear();
                            uselessList.clear();
                            initData();
                        }
                        ToastUtils.showToast(SelectCouponActivity.this, responseBean.getMsg());
                        etCouponCode.setText("");
                    }
                });
    }

}
