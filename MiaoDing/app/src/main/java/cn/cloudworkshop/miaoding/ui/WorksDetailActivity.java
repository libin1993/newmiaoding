package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.drawee.view.SimpleDraweeView;
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
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.AccentDetailBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.jazzyviewpager.JazzyViewPager;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.LogUtils;
import cn.cloudworkshop.miaoding.utils.ShareUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import cn.cloudworkshop.miaoding.view.CircleImageView;
import cn.cloudworkshop.miaoding.view.TyperTextView;
import okhttp3.Call;


/**
 * Author：Libin on 2017-09-08 13:34
 * Email：1993911441@qq.com
 * Describe：设计师作品详情（当前版）
 */
public class WorksDetailActivity extends BaseActivity {
    @BindView(R.id.vp_works_detail)
    JazzyViewPager vpWorks;
    @BindView(R.id.img_add_bag)
    ImageView imgAddBag;
    @BindView(R.id.img_buy_works)
    ImageView imgBuyWorks;
    @BindView(R.id.rgs_indicator_works)
    RadioGroup rgsIndicator;
    @BindView(R.id.img_goods_back)
    ImageView imgBack;
    @BindView(R.id.img_goods_share)
    ImageView imgShare;
    @BindView(R.id.tv_content_goods)
    TyperTextView tvContent;
    @BindView(R.id.img_works_info)
    ImageView imgWorksInfo;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;

    //商品id
    private String id;
    private AccentDetailBean worksBean;

    //库存
    private int stock;
    //购买数量
    private int count = 1;
    //1、直接购买  2、加入购物车
    private int type;
    private TextView tvStock;
    private TextView tvCount;

    private List<AccentDetailBean.DataBean.SkuBeanX> typeList = new ArrayList<>();
    //配件id
    private String partIds = "";


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_works_new);
        ButterKnife.bind(this);

        getData();
        initData();

    }

    private void getData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
    }


    /**
     * 加载数据
     */
    private void initData() {
        OkHttpUtils.get()
                .url(Constant.CUSTOMIZE_PARTS)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("goods_id", id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        imgLoadError.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        imgLoadError.setVisibility(View.GONE);
                        worksBean = GsonUtils.jsonToBean(response, AccentDetailBean.class);
                        if (worksBean.getCode() == 10000 && worksBean.getData() != null) {
                            initView();
                        }
                    }
                });

    }

    /**
     * 加载视图
     */
    private void initView() {

        if (worksBean.getData().getAd_img() != null && worksBean.getData().getAd_img().size() > 0){
            if (!TextUtils.isEmpty(worksBean.getData().getAd_img().get(0).getDesc())) {
                typerText(worksBean.getData().getAd_img().get(0).getDesc());
            }

            vpWorks.setOffscreenPageLimit(worksBean.getData().getAd_img().size());
            vpWorks.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return worksBean.getData().getAd_img().size();
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }

                @Override
                public Object instantiateItem(ViewGroup container, final int position) {
                    View view = LayoutInflater.from(WorksDetailActivity.this)
                            .inflate(R.layout.viewpager_goods_details, null);
                    ImageView img = (ImageView) view.findViewById(R.id.img_goods_picture);
                    Glide.with(WorksDetailActivity.this)
                            .load(Constant.IMG_HOST + worksBean.getData().getAd_img().get(position).getImg())
                            .placeholder(R.mipmap.place_holder_news)
                            .dontAnimate()
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(img);
                    container.addView(view);
                    return view;
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView((View) object);
                }
            });
            vpWorks.setCurrentItem(0);


            for (int i = 0; i < worksBean.getData().getAd_img().size(); i++) {
                RadioButton radioButton = new RadioButton(this);

                RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(18, 18);
                layoutParams.setMargins(10, 10, 10, 10);
                radioButton.setLayoutParams(layoutParams);
                radioButton.setButtonDrawable(null);
                radioButton.setClickable(false);
                radioButton.setBackgroundResource(R.drawable.viewpager_indicator);
                rgsIndicator.addView(radioButton);
            }
            ((RadioButton) rgsIndicator.getChildAt(0)).setChecked(true);
        }


        vpWorks.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tvContent.setAlpha(1 - (float) positionOffsetPixels / vpWorks.getWidth());
            }

            @Override
            public void onPageSelected(int position) {
                ((RadioButton) rgsIndicator.getChildAt(position)).setChecked(true);
                if (!TextUtils.isEmpty(worksBean.getData().getAd_img().get(0).getDesc())) {
                    tvContent.setVisibility(View.VISIBLE);
                    typerText(worksBean.getData().getAd_img().get(0).getDesc());
                } else {
                    tvContent.setVisibility(View.GONE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    /**
     * @param content 逐字显示，首个文字字体变大
     */
    private void typerText(String content) {

        String str = "<font><big><big>" +
                content.charAt(0) +
                "</big></big></font>" +
                content.substring(1);

        tvContent.animateText(Html.fromHtml(str));
    }

    @OnClick({R.id.img_add_bag, R.id.img_buy_works, R.id.img_goods_back, R.id.img_goods_share,
            R.id.img_works_info, R.id.img_load_error})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_add_bag:
                if (TextUtils.isEmpty(SharedPreferencesUtils.getStr(this, "token"))) {
                    Intent login = new Intent(WorksDetailActivity.this, LoginActivity.class);
                    login.putExtra("page_name", "立即购买");
                    startActivity(login);
                } else {
                    type = 2;
                    showWorksType();
                }
                break;
            case R.id.img_buy_works:
                if (TextUtils.isEmpty(SharedPreferencesUtils.getStr(this, "token"))) {
                    Intent login = new Intent(WorksDetailActivity.this, LoginActivity.class);
                    login.putExtra("page_name", "立即购买");
                    startActivity(login);
                } else {
                    type = 1;
                    showWorksType();
                }
                break;
            case R.id.img_goods_back:
                finish();
                break;
            case R.id.img_goods_share:
                if (worksBean != null && worksBean.getData() != null) {
                    String share_url = Constant.CUSTOM_SHARE + "?goods_id=" + id;

                    ShareUtils.showShare(this, Constant.IMG_HOST + worksBean
                                    .getData().getAd_img().get(0).getImg(), worksBean.getData().getName(),
                            worksBean.getData().getContent(), share_url);
                }
                break;
            case R.id.img_works_info:
                if (worksBean != null && worksBean.getData() != null) {
                    showWorksDetail();
                }
                break;
            case R.id.img_load_error:
                initData();
                break;
        }
    }

    /**
     * 商品详情
     */
    private void showWorksDetail() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.ppw_works_detail, null);
        PopupWindow popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(contentView);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        if (!isFinishing()) {
            popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.LEFT & Gravity.TOP, 0, 0);
        }

        DisplayUtils.setBackgroundAlpha(this, true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                DisplayUtils.setBackgroundAlpha(WorksDetailActivity.this, false);
            }
        });

        TextView tvWorks = (TextView) contentView.findViewById(R.id.tv_works_info);
        tvWorks.setText(worksBean.getData().getDetail());

    }


    /**
     * 商品规格
     */
    private void showWorksType() {
        if (worksBean != null && worksBean.getData() != null) {
            final View contentView = LayoutInflater.from(this).inflate(R.layout.ppw_select_type, null);
            final PopupWindow mPopupWindow = new PopupWindow(contentView,
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setContentView(contentView);
            mPopupWindow.setTouchable(true);
            mPopupWindow.setFocusable(true);
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
            if (!isFinishing()) {
                mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
            }

            DisplayUtils.setBackgroundAlpha(this, true);
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    DisplayUtils.setBackgroundAlpha(WorksDetailActivity.this, false);
                }
            });


            ImageView imageView = (ImageView) contentView.findViewById(R.id.img_works_icon);
            TextView tvPrice = (TextView) contentView.findViewById(R.id.tv_works_price);
            tvStock = (TextView) contentView.findViewById(R.id.tv_works_stock);
            final ImageView imgCancel = (ImageView) contentView.findViewById(R.id.img_cancel_buy);
            RecyclerView rvType = (RecyclerView) contentView.findViewById(R.id.rv_works_type);

            TextView tvReduce = (TextView) contentView.findViewById(R.id.tv_reduce_works);
            tvCount = (TextView) contentView.findViewById(R.id.tv_buy_count);
            TextView tvAdd = (TextView) contentView.findViewById(R.id.tv_add_works);
            TextView tvBuy = (TextView) contentView.findViewById(R.id.tv_buy_works);

            if (worksBean.getData().getAd_img() != null && worksBean.getData().getAd_img().size() > 0) {
                Glide.with(getApplicationContext())
                        .load(Constant.IMG_HOST + worksBean.getData().getAd_img().get(0).getImg())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .placeholder(R.mipmap.place_holder_goods)
                        .dontAnimate()
                        .into(imageView);
            }

            tvPrice.setTypeface(DisplayUtils.setTextType(this));
            tvPrice.setText("¥" + worksBean.getData().getSell_price());


            typeList.clear();
            typeList.addAll(worksBean.getData().getSku());
            count = 1;

            for (AccentDetailBean.DataBean.SkuBeanX skuBeanX : typeList) {
                for (int i = 0; i < skuBeanX.getSku().size(); i++) {
                    if (i == 0) {
                        skuBeanX.getSku().get(i).setDefault(true);
                    } else {
                        skuBeanX.getSku().get(i).setDefault(false);
                    }
                }
            }

            getStock();

            rvType.setLayoutManager(new LinearLayoutManager(this));
            CommonAdapter<AccentDetailBean.DataBean.SkuBeanX> typeAdapter = new CommonAdapter<
                    AccentDetailBean.DataBean.SkuBeanX>(
                    this, R.layout.rv_works_type_item, typeList) {
                @Override
                protected void convert(ViewHolder holder, final AccentDetailBean.DataBean.SkuBeanX skuBeanX, int position) {
                    holder.setText(R.id.tv_goods_size, skuBeanX.getType());
                    RecyclerView rvItem = holder.getView(R.id.rv_works_size);

                    rvItem.setLayoutManager(new LinearLayoutManager(WorksDetailActivity.this,
                            LinearLayoutManager.HORIZONTAL, false));
                    final CommonAdapter<AccentDetailBean.DataBean.SkuBeanX.SkuBean> sizeAdapter = new
                            CommonAdapter<AccentDetailBean.DataBean.SkuBeanX.SkuBean>(WorksDetailActivity.this,
                                    R.layout.listitem_works_color, skuBeanX.getSku()) {
                                @Override
                                protected void convert(ViewHolder holder, AccentDetailBean.DataBean
                                        .SkuBeanX.SkuBean skuBean, int position) {
                                    TextView tvSize = holder.getView(R.id.tv_current_size);
                                    tvSize.setText(skuBean.getName());

                                    SimpleDraweeView imgColor = holder.getView(R.id.iv_works_size);
                                    SimpleDraweeView imgMask = holder.getView(R.id.iv_current_size);
                                    imgColor.setImageURI(Constant.IMG_HOST + skuBean.getImg());

                                    if (skuBean.isDefault()) {
                                        imgMask.setVisibility(View.VISIBLE);
                                    } else {
                                        imgMask.setVisibility(View.GONE);
                                    }
                                }
                            };
                    rvItem.setAdapter(sizeAdapter);


                    sizeAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                            for (int i = 0; i < skuBeanX.getSku().size(); i++) {
                                if (i == position) {
                                    skuBeanX.getSku().get(i).setDefault(true);
                                } else {
                                    skuBeanX.getSku().get(i).setDefault(false);
                                }
                            }
                            sizeAdapter.notifyDataSetChanged();

                            getStock();
                        }

                        @Override
                        public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                            return false;
                        }
                    });
                }
            };

            rvType.setAdapter(typeAdapter);


            //增加数量
            tvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (count < stock) {
                        count++;
                        tvCount.setText(String.valueOf(count));
                    }
                }
            });

            //减少数量
            tvReduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (count > 1) {
                        count--;
                        tvCount.setText(String.valueOf(count));
                    }
                }
            });

            //确定购买
            tvBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopupWindow.dismiss();
                    if (count < stock) {
                        addToCart();
                    } else {
                        ToastUtils.showToast(WorksDetailActivity.this, getString(R.string.stock_pull));
                    }

                }
            });


            //取消购买
            imgCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopupWindow.dismiss();
                }
            });
        }
    }

    /**
     * 库存
     */
    private void getStock() {
        partIds = "";
        for (AccentDetailBean.DataBean.SkuBeanX skuBeanX : typeList) {
            for (AccentDetailBean.DataBean.SkuBeanX.SkuBean skuBean : skuBeanX.getSku()) {
                if (skuBean.isDefault()) {
                    partIds += skuBean.getId() + ",";
                }
            }
        }
        partIds = partIds.substring(0, partIds.length() - 1);

        OkHttpUtils.get()
                .url(Constant.WORKS_STOCK)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("goods_id", String.valueOf(id))
                .addParams("sku_id_s", partIds)
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
                                stock = jsonObject.getInt("stock");
                                tvStock.setText(getString(R.string.stock) + stock + getString(R.string.piece));
                                if (count > stock) {
                                    count = stock;
                                    tvCount.setText(String.valueOf(count));
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    /**
     * 加入购物车
     */
    private void addToCart() {
        OkHttpUtils.post()
                .url(Constant.ADD_CART)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("goods_id", String.valueOf(id))
                .addParams("goods_num", String.valueOf(count))
                .addParams("type", String.valueOf(type))
                .addParams("sku_id_s", partIds)
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
                                int cartId = jsonObject.getInt("cart_id");
                                if (type == 1) {
                                    Intent intent = new Intent(WorksDetailActivity.this, ConfirmOrderActivity.class);
                                    intent.putExtra("cart_id", String.valueOf(cartId));
                                    startActivity(intent);
                                } else {
                                    ToastUtils.showToast(WorksDetailActivity.this, msg);
                                }
                            } else {
                                ToastUtils.showToast(WorksDetailActivity.this, msg);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
