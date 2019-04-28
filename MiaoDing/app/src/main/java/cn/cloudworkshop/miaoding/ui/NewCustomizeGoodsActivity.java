package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.analytics.MobclickAgent;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.CustomizedGoodsBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.ContactService;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.DoubleClickUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.MyLinearLayoutManager;
import cn.cloudworkshop.miaoding.utils.NetworkImageHolderView;
import cn.cloudworkshop.miaoding.utils.ShareUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2019/4/28 08:56
 * Email：1993911441@qq.com
 * Describe：
 */
public class NewCustomizeGoodsActivity extends BaseActivity {
    @BindView(R.id.rv_customize_goods)
    RecyclerView rvGoods;
    @BindView(R.id.iv_add_collection)
    ImageView ivAddCollection;
    @BindView(R.id.iv_goods_consult)
    ImageView ivGoodsConsult;
    @BindView(R.id.tv_buy_customize)
    TextView tvBuyGooods;
    @BindView(R.id.iv_back_goods)
    ImageView ivBackGoods;
    @BindView(R.id.iv_share_goods)
    ImageView ivShareGoods;
    @BindView(R.id.img_load_error)
    ImageView ivLoadError;

    //商品id
    private String id;
    private CustomizedGoodsBean customBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_goods_new);
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
                        ivLoadError.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        ivLoadError.setVisibility(View.GONE);
                        customBean = GsonUtils.jsonToBean(response, CustomizedGoodsBean.class);
                        if (customBean.getCode() == 10000 && customBean.getData() != null) {
                            initView();
                        } else {
                            ToastUtils.showToast(NewCustomizeGoodsActivity.this, customBean.getMsg());
                        }
                    }
                });

    }

    /**
     * 加载视图
     */
    private void initView() {

        rvGoods.setLayoutManager(new LinearLayoutManager(this));

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
        rvGoods.setAdapter(adapter);

    }


    @OnClick({R.id.iv_add_collection, R.id.iv_goods_consult, R.id.tv_buy_customize, R.id.iv_back_goods,
            R.id.iv_share_goods, R.id.img_load_error})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_add_collection:
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
            case R.id.iv_goods_consult:
                ContactService.contactService(this);
                break;
            case R.id.tv_buy_customize:
                if (TextUtils.isEmpty(SharedPreferencesUtils.getStr(this, "token"))) {
                    Intent login = new Intent(this, LoginActivity.class);
                    login.putExtra("page_name", "定制");
                    startActivity(login);
                } else {
                    if (customBean != null && customBean.getData() != null) {
                        if (DoubleClickUtils.isFastClick()) {
                            buyGoods();
                        }
                    }
                }
                break;
            case R.id.iv_back_goods:
                finish();
                break;
            case R.id.iv_share_goods:
                if (customBean != null && customBean.getData() != null) {
                    String token = SharedPreferencesUtils.getStr(this, "token");
                    String goodsImg = Constant.IMG_HOST + customBean.getData().getAd_img().get(0).getImg();

                    if (!TextUtils.isEmpty(token)) {
                        encodeGoods(token, id, goodsImg, customBean.getData().getName(),
                                customBean.getData().getContent());
                    } else {
                        String shareUrl = Constant.CUSTOM_SHARE + "?goods_id=" + id;
                        ShareUtils.showShare(this, goodsImg, customBean.getData().getName(),
                                customBean.getData().getContent(), shareUrl);
                    }

                }
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
        Intent intent = new Intent(this, NewEmbroideryActivity.class);
        intent.putExtra("goods_id", customBean.getData().getId());
        startActivity(intent);

    }

    /**
     * @param token
     * @param goodsId
     * @param goodsImg
     * @param goodsName
     * @param goodsContent 加密商品
     */
    private void encodeGoods(String token, final String goodsId, final String goodsImg,
                             final String goodsName, final String goodsContent) {
        OkHttpUtils.post()
                .url(Constant.GOODS_ENCODE)
                .addParams("token", token)
                .addParams("goods_id", String.valueOf(goodsId))
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
                                String goodsNo = jsonObject.getString("goods_id");
                                String shareUrl = Constant.CUSTOM_SHARE + "?goods_id=" + goodsNo;
                                ShareUtils.showShare(NewCustomizeGoodsActivity.this,
                                        goodsImg, goodsName, goodsContent, shareUrl);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
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
                                if (status == 1) {
                                    MobclickAgent.onEvent(NewCustomizeGoodsActivity.this, "collection");
                                    ivAddCollection.setImageResource(R.mipmap.icon_add_like);
                                    ToastUtils.showToast(NewCustomizeGoodsActivity.this,
                                            getString(R.string.collect_success));
                                } else {
                                    ivAddCollection.setImageResource(R.mipmap.icon_cancel_like);
                                    ToastUtils.showToast(NewCustomizeGoodsActivity.this,
                                            getString(R.string.cancel_collect));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
