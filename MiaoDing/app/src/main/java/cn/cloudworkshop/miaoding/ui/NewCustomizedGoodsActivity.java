package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.analytics.MobclickAgent;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.CustomGoodsBean;
import cn.cloudworkshop.miaoding.bean.CustomItemBean;
import cn.cloudworkshop.miaoding.bean.CustomizedGoodsBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.ContactService;
import cn.cloudworkshop.miaoding.utils.DateUtils;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.DoubleClickUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.ImageEncodeUtils;
import cn.cloudworkshop.miaoding.utils.MyLinearLayoutManager;
import cn.cloudworkshop.miaoding.utils.NetworkImageHolderView;
import cn.cloudworkshop.miaoding.utils.ShareUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import cn.cloudworkshop.miaoding.view.CircleImageView;
import cn.cloudworkshop.miaoding.view.ScrollViewContainer;
import okhttp3.Call;

/**
 * Author：Libin on 2019/1/4 15:23
 * Email：1993911441@qq.com
 * Describe：定制商品详情
 */
public class NewCustomizedGoodsActivity extends BaseActivity {
    @BindView(R.id.tv_goods_sort)
    TextView tvGoodsName;
    @BindView(R.id.tv_goods_introduce)
    TextView tvGoodsContent;
    @BindView(R.id.img_tailor_back)
    ImageView imgBack;
    @BindView(R.id.tv_goods_tailor)
    TextView tvTailor;
    @BindView(R.id.banner_goods)
    ConvenientBanner bannerGoods;
    @BindView(R.id.img_add_like)
    ImageView imgAddLike;
    @BindView(R.id.img_tailor_consult)
    ImageView imgConsult;
    @BindView(R.id.ll_goods_tailor)
    LinearLayout llGoodsTailor;
    @BindView(R.id.img_tailor_share)
    ImageView imgShare;
    @BindView(R.id.tv_custom_goods)
    TextView tvCustomGoods;
    @BindView(R.id.tv_collect_count)
    TextView tvCollectCount;
    @BindView(R.id.rv_collect_user)
    RecyclerView rvCollectUser;
    @BindView(R.id.tv_comment_count)
    TextView tvCommentCount;
    @BindView(R.id.tv_all_evaluate)
    TextView tvAllEvaluate;
    @BindView(R.id.img_user_avatar)
    CircleImageView imgUser;
    @BindView(R.id.tv_name_user)
    TextView tvUserName;
    @BindView(R.id.tv_comment_time)
    TextView tvCommentTime;
    @BindView(R.id.tv_evaluate_content)
    TextView tvEvaluateContent;
    @BindView(R.id.rv_evaluate_picture)
    RecyclerView rvEvaluate;
    @BindView(R.id.tv_goods_comment)
    TextView tvTypeGoods;
    @BindView(R.id.scroll_container)
    ScrollViewContainer scrollContainer;
    @BindView(R.id.img_user_grade)
    ImageView imgUserGrade;
    @BindView(R.id.ll_no_evaluate)
    LinearLayout llNoEvaluate;
    @BindView(R.id.ll_no_collection)
    LinearLayout llNoCollection;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;
    @BindView(R.id.rv_goods_detail)
    RecyclerView rvGoodsDetail;

    //商品id
    private String id;
    private String shop_id;
    private String market_id;
    private CustomizedGoodsBean customBean;
    //监听banner滑动状态
    private boolean isScrolled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_goods);
        ButterKnife.bind(this);
        getData();
        initData();
    }

    /**
     * 商品详情
     */
    private void getData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        shop_id = intent.getStringExtra("shop_id");
        market_id = intent.getStringExtra("market_id");
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
                .url(Constant.GOODS_DETAILS)
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
                        customBean = GsonUtils.jsonToBean(response, CustomizedGoodsBean.class);
                        if (customBean.getCode() == 10000 && customBean.getData() != null) {
                            initView();
                        }
                    }
                });

    }


    /**
     * 加载视图
     */
    private void initView() {

        tvGoodsName.setText(customBean.getData().getName());

        tvGoodsContent.setText(customBean.getData().getName());
        if (customBean.getData().getIs_collect() == 1) {
            imgAddLike.setImageResource(R.mipmap.icon_add_like);
        } else {
            imgAddLike.setImageResource(R.mipmap.icon_cancel_like);
        }
        bannerGoods.setCanLoop(false);
        bannerGoods.stopTurning();
        if (customBean.getData().getAd_img() != null && customBean.getData().getAd_img().size() > 0) {
            ViewGroup.LayoutParams layoutParams = bannerGoods.getLayoutParams();
            layoutParams.height = (int) (DisplayUtils.getMetrics(this).widthPixels / Float.parseFloat(customBean.getData().getAd_img().get(0).getRatio()));
        }
        final ArrayList<String> banners = new ArrayList<>();
        for (CustomizedGoodsBean.DataBean.AdImgBean adImgBean : customBean.getData().getAd_img()) {
            banners.add(adImgBean.getImg());
        }
        bannerGoods.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, banners)
                //设置两个点图片作为翻页指示器
                .setPageIndicator(new int[]{R.drawable.dot_black, R.drawable.dot_white})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        bannerGoods.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(NewCustomizedGoodsActivity.this, ImagePreviewActivity.class);
                intent.putExtra("current_pos", position);
                intent.putStringArrayListExtra("img_list", banners);
                startActivity(intent);
            }
        });

        bannerGoods.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        //划到最后一张，再左滑跳转详情页
                        if (!isScrolled && bannerGoods.getCurrentItem() == banners.size() - 1) {
                            Intent intent = new Intent(NewCustomizedGoodsActivity.this, GoodsDetailActivity.class);
                            intent.putExtra("img", (Serializable) customBean.getData().getImg_info());
                            startActivity(intent);
                        }
                        isScrolled = true;
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        isScrolled = false;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        isScrolled = true;
                        break;

                }

            }
        });


        //喜爱人数
        if (customBean.getData().getCollect_num() > 0) {
            tvCollectCount.setText(getString(R.string.love) + "  （" + customBean.getData().getCollect_num() + getString(R.string.person) + "）");
            rvCollectUser.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

            CommonAdapter<CustomizedGoodsBean.DataBean.CollectListBean> collectAdapter = new CommonAdapter
                    <CustomizedGoodsBean.DataBean.CollectListBean>(this, R.layout.listitem_user_collect,
                    customBean.getData().getCollect_list()) {
                @Override
                protected void convert(ViewHolder holder, CustomizedGoodsBean.DataBean.CollectListBean
                        collectListBean, int position) {
                    Glide.with(NewCustomizedGoodsActivity.this)
                            .load(Constant.IMG_HOST + collectListBean.getHead_ico())
                            .centerCrop()
                            .into((ImageView) holder.getView(R.id.img_avatar_collect));
                }

            };
            rvCollectUser.setAdapter(collectAdapter);
        } else {
            llNoCollection.setVisibility(View.GONE);
        }

        //评价人数
//        if (customBean.getData().getComment_num() > 0) {
//            tvCommentCount.setText("评价  （" + customBean.getData().getComment_num() + "）");
//            Glide.with(getApplicationContext())
//                    .load(Constant.IMG_HOST + customBean.getData().getNew_comment().getAvatar())
//                    .centerCrop()
//                    .into(imgUser);
//            tvUserName.setText(customBean.getData().getNew_comment().getUser_name());
//            Glide.with(getApplicationContext())
//                    .load(Constant.IMG_HOST + customBean.getData().getNew_comment().getUser_grade().getImg2())
//                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                    .into(imgUserGrade);
//            tvCommentTime.setText(DateUtils.getDate("yyyy-MM-dd", customBean.getData().getNew_comment().getC_time()));
//            tvEvaluateContent.setText(customBean.getData().getNew_comment().getContent());
//            if (customBean.getData().getNew_comment().getImg_list() != null && customBean.getData()
//                    .getNew_comment().getImg_list().size() > 0) {
//                rvEvaluate.setLayoutManager(new GridLayoutManager(CustomizedGoodsActivity.this, 3));
//                CommonAdapter<String> evaluateAdapter = new CommonAdapter<String>(CustomizedGoodsActivity
//                        .this, R.layout.listitem_user_comment, customBean.getData().getNew_comment().getImg_list()) {
//                    @Override
//                    protected void convert(ViewHolder holder, String s, int position) {
//                        Glide.with(CustomizedGoodsActivity.this)
//                                .load(Constant.IMG_HOST + s)
//                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                                .centerCrop()
//                                .into((ImageView) holder.getView(R.id.img_user_comment));
//                    }
//                };
//                rvEvaluate.setAdapter(evaluateAdapter);
//            }
//            tvTypeGoods.setText(customBean.getData().getNew_comment().getGoods_intro());
//        } else {
//            llNoEvaluate.setVisibility(View.GONE);
//        }

        llNoEvaluate.setVisibility(View.GONE);

        MyLinearLayoutManager linearLayoutManager = new MyLinearLayoutManager(this);
        linearLayoutManager.setScrollEnabled(false);
        rvGoodsDetail.setLayoutManager(linearLayoutManager);
        CommonAdapter<CustomizedGoodsBean.DataBean.ImgInfoBean> adapter = new CommonAdapter
                <CustomizedGoodsBean.DataBean.ImgInfoBean>(this, R.layout.rv_goods_detail_item,
                customBean.getData().getImg_info()) {
            @Override
            protected void convert(ViewHolder holder, CustomizedGoodsBean.DataBean.ImgInfoBean imgInfoBean, int position) {
                SimpleDraweeView ivGoods = holder.getView(R.id.iv_goods_detail);
                ivGoods.setImageURI(Constant.IMG_HOST + imgInfoBean.getImg());
                ivGoods.setAspectRatio(Float.parseFloat(imgInfoBean.getRatio()));
            }
        };
        rvGoodsDetail.setAdapter(adapter);

    }

    @OnClick({R.id.tv_goods_tailor, R.id.img_tailor_back, R.id.img_add_like, R.id.img_tailor_consult,
            R.id.img_tailor_share, R.id.tv_all_evaluate, R.id.img_load_error})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_goods_tailor:
                if (TextUtils.isEmpty(SharedPreferencesUtils.getStr(this, "token"))) {
                    Intent login = new Intent(this, LoginActivity.class);
                    login.putExtra("page_name", "定制");
                    startActivity(login);
                } else {
                    if (customBean != null && customBean.getData() != null) {
                        if (DoubleClickUtils.isFastClick()){
                            buyGoods();
                        }
                    }
                }
                break;
            case R.id.img_tailor_back:
                finish();
                break;
            case R.id.img_add_like:
                if (!TextUtils.isEmpty(SharedPreferencesUtils.getStr(this, "token"))) {
                    if (customBean != null && customBean.getData() != null) {
                        addCollection();
                    }
                } else {
                    Intent login = new Intent(this, LoginActivity.class);
                    login.putExtra("page_name", "收藏");
                    startActivity(login);
                }
                break;
            case R.id.img_tailor_consult:
                ContactService.contactService(this);
                break;
            case R.id.img_tailor_share:

                if (customBean != null && customBean.getData() != null) {
                    String share_url = Constant.CUSTOM_SHARE + "?goods_id=" + id;
                    if (shop_id != null) {
                        share_url += "&shop_id=" + shop_id;
                    }
                    if (market_id != null) {
                        share_url += "&market_id=" + market_id;
                    }
                    share_url += "&type=1";
                    String uid = SharedPreferencesUtils.getStr(this, "uid");
                    if (!TextUtils.isEmpty(uid)) {
                        share_url += "&shareout_id=" + uid;
                    }
                    ShareUtils.showShare(this, Constant.IMG_HOST + customBean.getData().getAd_img().get(0).getImg(),
                            customBean.getData().getName(), customBean.getData().getContent(), share_url);
                }
                break;
            case R.id.tv_all_evaluate:
//                if (customBean != null && customBean.getData().getComment_num() > 0) {
//                    Intent intent = new Intent(this, AllEvaluationActivity.class);
//                    intent.putExtra("goods_id", id);
//                    startActivity(intent);
//                }
                break;
            case R.id.img_load_error:
                initData();
                break;
        }
    }

    /**
     * 购买商品
     */
    private void buyGoods() {
        Intent intent = new Intent(this,NewEmbroideryActivity.class);
        intent.putExtra("goods_id",customBean.getData().getId());
        startActivity(intent);

    }


    /**
     * 添加收藏
     */
    private void addCollection() {
        OkHttpUtils.post()
                .url(Constant.ADD_COLLECTION)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("type", "2")
                .addParams("rid", id)
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
                            int status = jsonObject.getInt("status");
                            if (code == 10000) {
                                if (status == 1){
                                    MobclickAgent.onEvent(NewCustomizedGoodsActivity.this, "collection");
                                    imgAddLike.setImageResource(R.mipmap.icon_add_like);
                                    ToastUtils.showToast(NewCustomizedGoodsActivity.this, getString(R.string.collect_success));
                                }else {
                                    imgAddLike.setImageResource(R.mipmap.icon_cancel_like);
                                    ToastUtils.showToast(NewCustomizedGoodsActivity.this, getString(R.string.cancel_collect));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
