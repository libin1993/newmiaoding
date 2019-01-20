package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.umeng.analytics.MobclickAgent;
import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.adapter.BaseDelegateAdapter;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.CustomizePartsBean;
import cn.cloudworkshop.miaoding.bean.EmbroideryBean;
import cn.cloudworkshop.miaoding.bean.NewEmbroideryBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2019/1/10 14:09
 * Email：1993911441@qq.com
 * Describe：个性绣花
 */
public class NewEmbroideryActivity extends BaseActivity {
    @BindView(R.id.iv_embroidery_back)
    ImageView ivEmbroideryBack;
    @BindView(R.id.tv_fast_customize)
    TextView tvFastCustomize;
    @BindView(R.id.tv_personal_customize)
    TextView tvPersonalCustomize;
    @BindView(R.id.rv_embroidery)
    RecyclerView rvEmbroidery;
    @BindView(R.id.tv_goods_total_price)
    TextView tvPrice;
    @BindView(R.id.tv_add_to_cart)
    TextView tvAddCart;
    @BindView(R.id.tv_confirm_buy_goods)
    TextView tvConfirmBuy;
    @BindView(R.id.img_select_fabric)
    ImageView imgFabric;
    @BindView(R.id.view_loading)
    AVLoadingIndicatorView viewLoading;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;

    private int goodsId;
    private NewEmbroideryBean embroideryBean;
    private List<DelegateAdapter.Adapter> mAdapters;
    private List<NewEmbroideryBean.DataBean.SpecialMarkPartBean> typeList;
    private List<NewEmbroideryBean.DataBean.SpecialMarkPartBean> specialList;
    private String content;
    //是否长按
    private boolean isLongPress;
    //1:直接购买 2：加入购物袋
    private int type;
    private BaseDelegateAdapter embroideryAdapter;
    private BaseDelegateAdapter contentAdapter;
    private DelegateAdapter delegateAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_embroidery_new);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        getData();
        initData();
    }

    private void initData() {
        viewLoading.smoothToShow();
        OkHttpUtils.get()
                .url(Constant.CUSTOMIZE_PARTS)
                .addParams("goods_id", String.valueOf(goodsId))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        imgLoadError.setBackgroundColor(0xffededed);
                        imgLoadError.setVisibility(View.VISIBLE);
                        viewLoading.smoothToHide();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        imgLoadError.setVisibility(View.GONE);
                        viewLoading.smoothToHide();
                        embroideryBean = GsonUtils.jsonToBean(response, NewEmbroideryBean.class);
                        if (embroideryBean.getCode() == 10000 && embroideryBean.getData() != null) {
                            initView();
                        }
                    }
                });
    }

    /**
     *
     */
    private void initView() {
        tvPrice.setText("¥" + embroideryBean.getData().getSell_price());
        mAdapters = new LinkedList<>();

        typeList = new ArrayList<>();
        specialList = new ArrayList<>();
        for (int i = 0; i < embroideryBean.getData().getSpecial_mark_part().size(); i++) {
            if (embroideryBean.getData().getSpecial_mark_part().get(i).getSpecial_mark() == 1) {
                typeList.add(embroideryBean.getData().getSpecial_mark_part().get(i));
            } else {
                specialList.add(embroideryBean.getData().getSpecial_mark_part().get(i));
            }
        }

        //初始化
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(this);
        rvEmbroidery.setLayoutManager(layoutManager);

        //设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）：
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        rvEmbroidery.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);
        delegateAdapter = new DelegateAdapter(layoutManager, true);
        rvEmbroidery.setAdapter(delegateAdapter);

        //版型
        if (typeList.size() > 0) {
            BaseDelegateAdapter typeAdapter = new BaseDelegateAdapter(this, new LinearLayoutHelper(),
                    R.layout.rv_embroidery_item, 1, 1) {

                @Override
                public void onBindViewHolder(ViewHolder holder, int position) {
                    super.onBindViewHolder(holder, position);
                    final int index = position;
                    TextView tvTitle = holder.getView(R.id.tv_embroidery_title);
                    tvTitle.setText(typeList.get(position).getType_name());

                    final TextView tvContent = holder.getView(R.id.tv_embroidery_content);

                    RecyclerView rvType = holder.getView(R.id.rv_embroidery_item);


                    rvType.setLayoutManager(new LinearLayoutManager(NewEmbroideryActivity.this,
                            LinearLayoutManager.HORIZONTAL, false));
                    final CommonAdapter<NewEmbroideryBean.DataBean.SpecialMarkPartBean.SonBeanX> adapter = new CommonAdapter
                            <NewEmbroideryBean.DataBean.SpecialMarkPartBean.SonBeanX>(NewEmbroideryActivity.this,
                            R.layout.listitem_embroidery_fabric, typeList.get(position).getSon()) {
                        @Override
                        protected void convert(ViewHolder holder, NewEmbroideryBean.DataBean.SpecialMarkPartBean.SonBeanX sonBeanX, int position) {
                            Glide.with(NewEmbroideryActivity.this)
                                    .load(Constant.IMG_HOST + sonBeanX.getPart_img())
                                    .placeholder(R.mipmap.place_holder_goods)
                                    .dontAnimate()


                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .into((ImageView) holder.getView(R.id.img_embroidery_fabric));
                            if (sonBeanX.getIs_default() == 1) {
                                holder.setVisible(R.id.img_current_fabric, true);
                                tvContent.setText(sonBeanX.getPart_name());
                            } else {
                                holder.setVisible(R.id.img_current_fabric, false);
                            }

                            holder.setText(R.id.tv_current_fabric, sonBeanX.getPart_name());
                        }
                    };
                    rvType.setAdapter(adapter);


                    adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                            //当前选择
                            int currentParts = holder.getLayoutPosition();
                            for (int i = 0; i < typeList.get(index).getSon().size(); i++) {
                                if (i == currentParts) {
                                    typeList.get(index).getSon().get(i).setIs_default(1);
                                } else {
                                    typeList.get(index).getSon().get(i).setIs_default(0);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                            return false;
                        }
                    });
                }

            };
            mAdapters.add(typeAdapter);
        }


        //面料
        if (embroideryBean.getData().getFabric() != null) {
            BaseDelegateAdapter fabricAdapter = new BaseDelegateAdapter(this, new LinearLayoutHelper(),
                    R.layout.rv_embroidery_item, 1, 2) {
                @Override
                public void onBindViewHolder(ViewHolder holder, int position) {
                    super.onBindViewHolder(holder, position);
                    TextView tvTitle = holder.getView(R.id.tv_embroidery_title);
                    tvTitle.setText("面料 (长按显示大图)");

                    final TextView tvContent = holder.getView(R.id.tv_embroidery_content);

                    RecyclerView rvFabric = holder.getView(R.id.rv_embroidery_item);

                    for (NewEmbroideryBean.DataBean.FabricBean fabricBean : embroideryBean.getData().getFabric()) {
                        if (fabricBean.getId().equals(embroideryBean.getData().getMoren_fabric_id())) {
                            fabricBean.setIs_default(1);
                            break;
                        }
                    }


                    //选择面料
                    rvFabric.setLayoutManager(new LinearLayoutManager(NewEmbroideryActivity.this,
                            LinearLayoutManager.HORIZONTAL, false));
                    final CommonAdapter<NewEmbroideryBean.DataBean.FabricBean> adapter = new CommonAdapter
                            <NewEmbroideryBean.DataBean.FabricBean>(NewEmbroideryActivity.this,
                            R.layout.listitem_embroidery_fabric, embroideryBean.getData().getFabric()) {
                        @Override
                        protected void convert(ViewHolder holder, NewEmbroideryBean.DataBean.FabricBean fabricBean, int position) {
                            Glide.with(NewEmbroideryActivity.this)
                                    .load(Constant.IMG_HOST + fabricBean.getImg())
                                    .placeholder(R.mipmap.place_holder_goods)
                                    .dontAnimate()
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .into((ImageView) holder.getView(R.id.img_embroidery_fabric));
                            if (fabricBean.getIs_default() == 1) {
                                holder.setVisible(R.id.img_current_fabric, true);
                                tvContent.setText(fabricBean.getName());
                            } else {
                                holder.setVisible(R.id.img_current_fabric, false);
                            }

                            holder.setText(R.id.tv_current_fabric, fabricBean.getName());
                        }


                    };
                    rvFabric.setAdapter(adapter);


                    rvEmbroidery.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            imgFabric.setVisibility(View.GONE);
                            return false;
                        }
                    });
                    //长按显示大图
                    rvFabric.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            imgFabric.setVisibility(View.GONE);
                            return false;
                        }

                    });
                    adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                            if (!isLongPress) {
                                //当前选择面料
                                int currentFabric = holder.getLayoutPosition();
                                for (int i = 0; i < embroideryBean.getData().getFabric().size(); i++) {
                                    if (i == currentFabric) {
                                        embroideryBean.getData().getFabric().get(i).setIs_default(1);
                                    } else {
                                        embroideryBean.getData().getFabric().get(i).setIs_default(0);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            } else {
                                //长按子配件显示大图，离开屏幕大图消失
                                isLongPress = false;
                                if (imgFabric.getVisibility() == View.VISIBLE) {
                                    imgFabric.setVisibility(View.GONE);
                                }
                            }

                        }

                        @Override
                        public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                            Glide.with(NewEmbroideryActivity.this)
                                    .load(Constant.IMG_HOST + embroideryBean.getData().getFabric().get(position).getImg())
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .into(imgFabric);
                            isLongPress = true;
                            if (imgFabric.getVisibility() == View.GONE) {
                                imgFabric.setVisibility(View.VISIBLE);
                            }
                            return false;
                        }
                    });
                }
            };
            mAdapters.add(fabricAdapter);
        }


        BaseDelegateAdapter selectAdapter = new BaseDelegateAdapter(this, new LinearLayoutHelper(),
                R.layout.rv_select_embroidery, 1, 3) {
            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                CheckBox checkBox = holder.getView(R.id.checkbox_select_embroidery);
                //个性绣花布局收缩
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        isEmbroidery(isChecked);
                    }
                });
            }
        };
        mAdapters.add(selectAdapter);


        if (specialList.size() > 0) {
            embroideryAdapter = new BaseDelegateAdapter(this, new LinearLayoutHelper(),
                    R.layout.rv_embroidery_item, specialList.size(), 4) {

                @Override
                public void onBindViewHolder(ViewHolder holder, int position) {
                    super.onBindViewHolder(holder, position);
                    final int index = position;
                    TextView tvTitle = holder.getView(R.id.tv_embroidery_title);
                    tvTitle.setText(specialList.get(position).getType_name());

                    final TextView tvContent = holder.getView(R.id.tv_embroidery_content);

                    RecyclerView rvParts = holder.getView(R.id.rv_embroidery_item);


                    //个性化
                    rvParts.setLayoutManager(new LinearLayoutManager(NewEmbroideryActivity.this,
                            LinearLayoutManager.HORIZONTAL, false));
                    final CommonAdapter<NewEmbroideryBean.DataBean.SpecialMarkPartBean.SonBeanX> adapter = new CommonAdapter
                            <NewEmbroideryBean.DataBean.SpecialMarkPartBean.SonBeanX>(NewEmbroideryActivity.this,
                            R.layout.listitem_embroidery_fabric, specialList.get(position).getSon()) {
                        @Override
                        protected void convert(ViewHolder holder, NewEmbroideryBean.DataBean.SpecialMarkPartBean.SonBeanX sonBeanX, int position) {
                            Glide.with(NewEmbroideryActivity.this)
                                    .load(Constant.IMG_HOST + sonBeanX.getPart_img())
                                    .placeholder(R.mipmap.place_holder_goods)
                                    .dontAnimate()
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .into((ImageView) holder.getView(R.id.img_embroidery_fabric));
                            if (sonBeanX.getIs_default() == 1) {
                                holder.setVisible(R.id.img_current_fabric, true);
                                tvContent.setText(sonBeanX.getPart_name());
                            } else {
                                holder.setVisible(R.id.img_current_fabric, false);
                            }

                            holder.setText(R.id.tv_current_fabric, sonBeanX.getPart_name());
                        }
                    };
                    rvParts.setAdapter(adapter);


                    adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                            //当前选择
                            int currentParts = holder.getLayoutPosition();
                            for (int i = 0; i < specialList.get(index).getSon().size(); i++) {
                                if (i == currentParts) {
                                    specialList.get(index).getSon().get(i).setIs_default(1);
                                } else {
                                    specialList.get(index).getSon().get(i).setIs_default(0);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                            return false;
                        }
                    });
                }
            };
        }


        contentAdapter = new BaseDelegateAdapter(this, new LinearLayoutHelper(),
                R.layout.layout_embroidery_content, 1, 5) {
            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                EditText etContent = holder.getView(R.id.et_embroidery);

                etContent.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        content = s.toString();
                    }
                });
            }
        };

        delegateAdapter.setAdapters(mAdapters);
    }

    /**
     * @param b 是否绣花
     */
    private void isEmbroidery(boolean b) {
        if (b) {
            mAdapters.add(embroideryAdapter);
            mAdapters.add(contentAdapter);
        } else {
            mAdapters.remove(embroideryAdapter);
            mAdapters.remove(contentAdapter);
        }
        delegateAdapter.setAdapters(mAdapters);
    }


    private void getData() {
        Intent intent = getIntent();
        goodsId = intent.getIntExtra("goods_id", -1);
    }


    @OnClick({R.id.iv_embroidery_back, R.id.tv_personal_customize,
            R.id.tv_add_to_cart, R.id.tv_confirm_buy_goods, R.id.img_load_error})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_embroidery_back:
                finish();
                break;
            case R.id.tv_personal_customize:
                if (embroideryBean != null) {
                    toCustomize();
                }

                break;
            case R.id.tv_add_to_cart:
                if (embroideryBean != null) {
                    type = 2;
                    addToCart();
                }

                break;
            case R.id.tv_confirm_buy_goods:
                if (embroideryBean != null) {
                    type = 1;
                    addToCart();
                }
                break;
            case R.id.img_load_error:
                initData();
                break;
        }
    }


    /**
     * 手动选择配件，跳转定制页面
     */
    private void toCustomize() {
        Intent intent = new Intent(this, CustomizeActivity.class);
        Bundle bundle = new Bundle();
        CustomizePartsBean partsBean = new CustomizePartsBean();
        partsBean.setGoodsId(goodsId);
        partsBean.setGoodsImg(embroideryBean.getData().getAd_img().get(0).getImg());
        partsBean.setGoodsName(embroideryBean.getData().getName());
        if (!TextUtils.isEmpty(content)) {
            partsBean.setContent(content);
        }
        partsBean.setPrice(embroideryBean.getData().getSell_price());

        //面料
        CustomizePartsBean.FabricBean fabric = new CustomizePartsBean.FabricBean();

        for (NewEmbroideryBean.DataBean.FabricBean fabricBean : embroideryBean.getData().getFabric()) {
            if (fabricBean.getIs_default() == 1) {
                fabric.setFabricId(fabricBean.getId());
                fabric.setFabricName(fabricBean.getName());
                break;
            }
        }
        partsBean.setFabricBean(fabric);

        //个性化配件
        String embroideryIds = "";

        List<CustomizePartsBean.EmbroideryBean> embroiderys = new ArrayList<>();

        //版型
        for (NewEmbroideryBean.DataBean.SpecialMarkPartBean typeBean : typeList) {

            for (NewEmbroideryBean.DataBean.SpecialMarkPartBean.SonBeanX sonBeanX : typeBean.getSon()) {
                if (sonBeanX.getIs_default() == 1) {
                    embroideryIds += sonBeanX.getPart_id() + ",";

                    CustomizePartsBean.EmbroideryBean embroidery = new CustomizePartsBean.EmbroideryBean();
                    embroidery.setTitle(typeBean.getType_name());
                    embroidery.setName(sonBeanX.getPart_name());
                    embroiderys.add(embroidery);
                    break;
                }
            }
        }


        //绣花
        for (NewEmbroideryBean.DataBean.SpecialMarkPartBean specialMarkPartBean : specialList) {
            for (NewEmbroideryBean.DataBean.SpecialMarkPartBean.SonBeanX sonBeanX : specialMarkPartBean.getSon()) {
                if (sonBeanX.getIs_default() == 1) {
                    embroideryIds += sonBeanX.getPart_id() + ",";

                    CustomizePartsBean.EmbroideryBean embroidery = new CustomizePartsBean.EmbroideryBean();
                    embroidery.setTitle(specialMarkPartBean.getType_name());
                    embroidery.setName(sonBeanX.getPart_name());
                    embroiderys.add(embroidery);
                    break;
                }
            }
        }
        if (!TextUtils.isEmpty(embroideryIds)) {
            embroideryIds = embroideryIds.substring(0, embroideryIds.length() - 1);
            partsBean.setSpecial_mark_part_ids(embroideryIds);
            partsBean.setEmbroideryBeans(embroiderys);
        }


        bundle.putSerializable("embroidery", partsBean);
        bundle.putSerializable("parts", (Serializable) embroideryBean.getData().getMust_display_part());
        intent.putExtras(bundle);
        startActivity(intent);

    }

    /**
     * 加入购物车
     */
    private void addToCart() {
        Map<String, String> map = new HashMap<>();
        map.put("token", SharedPreferencesUtils.getStr(this, "token"));
        map.put("type", String.valueOf(type));
        map.put("goods_id", String.valueOf(goodsId));
        map.put("goods_num", "1");
        String fabricId = "";
        for (NewEmbroideryBean.DataBean.FabricBean fabricBean : embroideryBean.getData().getFabric()) {
            if (fabricBean.getIs_default() == 1) {
                fabricId = fabricBean.getId();
                break;
            }
        }
        map.put("fabric_id", fabricId);
        if (!TextUtils.isEmpty(content)) {
            map.put("re_marks", content);
        }

        String embroideryIds = "";

        for (NewEmbroideryBean.DataBean.SpecialMarkPartBean typeBean : typeList) {
            for (NewEmbroideryBean.DataBean.SpecialMarkPartBean.SonBeanX sonBeanX : typeBean.getSon()) {
                if (sonBeanX.getIs_default() == 1) {
                    embroideryIds += sonBeanX.getPart_id() + ",";
                    break;
                }
            }
        }

        for (NewEmbroideryBean.DataBean.SpecialMarkPartBean specialMarkPartBean : specialList) {
            for (NewEmbroideryBean.DataBean.SpecialMarkPartBean.SonBeanX sonBeanX : specialMarkPartBean.getSon()) {
                if (sonBeanX.getIs_default() == 1) {
                    embroideryIds += sonBeanX.getPart_id() + ",";
                    break;
                }
            }
        }

        if (!TextUtils.isEmpty(embroideryIds)) {
            embroideryIds = embroideryIds.substring(0, embroideryIds.length() - 1);
            map.put("special_mark_part_ids", embroideryIds);
        }


        String partIds = "";
        for (NewEmbroideryBean.DataBean.MustDisplayPartBean mustDisplayPartBean : embroideryBean.getData().getMust_display_part()) {
            for (NewEmbroideryBean.DataBean.MustDisplayPartBean.SonBean sonBean : mustDisplayPartBean.getSon()) {
                if (sonBean.getIs_default() == 1) {
                    partIds += sonBean.getPart_id() + ",";
                    break;
                }
            }
        }
        partIds = partIds.substring(0, partIds.length() - 1);
        map.put("must_display_part_ids", partIds);

        OkHttpUtils.post()
                .url(Constant.ADD_CART)
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
                                int cartId = jsonObject.getInt("cart_id");
                                if (type == 1) {
                                    Intent intent = new Intent(NewEmbroideryActivity.this, ConfirmOrderActivity.class);
                                    intent.putExtra("cart_id", String.valueOf(cartId));
                                    startActivity(intent);
                                } else {
                                    ToastUtils.showToast(NewEmbroideryActivity.this, msg);
                                }
                            } else {
                                ToastUtils.showToast(NewEmbroideryActivity.this, msg);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    /**
     * @param msg 下单成功，结束当前页面
     */
    @Subscribe
    public void orderSuccess(String msg) {
        if ("order_success".equals(msg)) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
