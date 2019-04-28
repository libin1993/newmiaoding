package cn.cloudworkshop.miaoding.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnItemLongClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.CartDetailsBean;
import cn.cloudworkshop.miaoding.bean.CustomItemBean;
import cn.cloudworkshop.miaoding.bean.RecommendGoodsBean;
import cn.cloudworkshop.miaoding.bean.ShoppingCartBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.RVItemDecoration;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.SpaceItemDecoration;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2016/8/31 11:02
 * Email：1993911441@qq.com
 * Describe：购物袋
 */
public class ShoppingCartActivity extends BaseActivity {

    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.img_header_share)
    ImageView imgHeaderShare;
    @BindView(R.id.tv_my_bag)
    TextView tvMyBag;
    @BindView(R.id.rl_null_bag)
    RelativeLayout rlNullBag;
    @BindView(R.id.ll_cart_goods)
    LinearLayout llCartGoods;
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_next)
    TextView tvHeaderNext;
    @BindView(R.id.checkbox_all_select)
    CheckBox checkboxAllSelect;
    @BindView(R.id.tv_total_counts)
    TextView tvTotalCounts;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_goods_buy)
    TextView tvGoodsBuy;
    @BindView(R.id.rv_cart_recommend)
    RecyclerView rvRecommend;
    @BindView(R.id.rv_goods_cart)
    LRecyclerView rvGoodsCart;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;
    @BindView(R.id.view_loading)
    AVLoadingIndicatorView viewLoading;

    private List<ShoppingCartBean.DataBean> dataList = new ArrayList<>();
    //页数
    private int page = 1;
    //刷新
    private boolean isRefresh;
    //加载更多
    private boolean isLoadMore;
    private CommonAdapter<ShoppingCartBean.DataBean> adapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    //是否编辑状态
    private boolean isEdited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);
        tvHeaderTitle.setText(R.string.cart);
        tvHeaderNext.setText(R.string.edit);
        viewLoading.smoothToShow();
        initData();
        initView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isRefresh = true;
        page = 1;
        checkboxAllSelect.setChecked(true);
        initData();
    }

    /**
     * 获取数据
     */
    private void initData() {

        OkHttpUtils.get()
                .url(Constant.SHOPPING_CART)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("page", String.valueOf(page))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        imgLoadError.setVisibility(View.VISIBLE);
                        if (viewLoading != null && viewLoading.isShown()) {
                            viewLoading.smoothToHide();
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        imgLoadError.setVisibility(View.GONE);
                        if (viewLoading != null && viewLoading.isShown()) {
                            viewLoading.smoothToHide();
                        }
                        ShoppingCartBean cartBean = GsonUtils.jsonToBean(response, ShoppingCartBean.class);
                        if (cartBean.getData() != null && cartBean.getData().size() > 0) {
                            if (isRefresh) {
                                dataList.clear();
                            }
                            dataList.addAll(cartBean.getData());
                            if (isLoadMore || isRefresh) {
                                rvGoodsCart.refreshComplete(0);
                            }
                            getTotalCount();
                            getTotalPrice();
                            mLRecyclerViewAdapter.notifyDataSetChanged();

                            isRefresh = false;
                            isLoadMore = false;

                            rlNullBag.setVisibility(View.GONE);
                            llCartGoods.setVisibility(View.VISIBLE);
                            tvHeaderNext.setVisibility(View.VISIBLE);
                        } else {
                            RecyclerViewStateUtils.setFooterViewState(ShoppingCartActivity.this,
                                    rvGoodsCart, 0, LoadingFooter.State.NoMore, null);
                            if (page == 1) {
                                if (!isRefresh) {
                                    nullCart();
                                }
                            }
                        }
                    }
                });
    }

    /**
     * 空购物车
     */
    private void nullCart() {
        rlNullBag.setVisibility(View.VISIBLE);
        llCartGoods.setVisibility(View.GONE);
        tvHeaderNext.setVisibility(View.GONE);
        OkHttpUtils.get()
                .url(Constant.GOODS_RECOMMEND)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("num", "2")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        RecommendGoodsBean recommendBean = GsonUtils.jsonToBean(response,
                                RecommendGoodsBean.class);
                        if (recommendBean.getCode() == 10000 && recommendBean.getData() != null) {
                            recommendGoods(recommendBean.getData());
                        }
                    }
                });
    }

    /**
     * 推荐商品
     */
    private void recommendGoods(final List<RecommendGoodsBean.DataBean> recommendBean) {
        rvRecommend.setLayoutManager(new GridLayoutManager(ShoppingCartActivity.this, 2));
        rvRecommend.addItemDecoration(new SpaceItemDecoration((int) DisplayUtils.dp2px(this,
                4.5f), false));
        CommonAdapter<RecommendGoodsBean.DataBean> adapter = new CommonAdapter<RecommendGoodsBean
                .DataBean>(
                this, R.layout.listitem_sub_goods, recommendBean) {
            @Override
            protected void convert(ViewHolder holder, RecommendGoodsBean.DataBean dataBean,
                                   int position) {

                SimpleDraweeView imgGoods = holder.getView(R.id.img_sub_goods);
                imgGoods.setImageURI(Constant.IMG_HOST + dataBean.getCar_img());
                imgGoods.setAspectRatio(1);
                holder.setText(R.id.tv_sub_title, dataBean.getName());
                holder.setText(R.id.tv_sub_price, "¥ " + dataBean.getSell_price());
                holder.setText(R.id.tv_sub_content, dataBean.getContent());
            }
        };
        rvRecommend.setAdapter(adapter);


        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent;
                if (recommendBean.get(position).getCategory_id() == 1) {
                    intent = new Intent(ShoppingCartActivity.this, NewCustomizeGoodsActivity.class);
                } else {
                    intent = new Intent(ShoppingCartActivity.this, WorksDetailActivity.class);
                }

                intent.putExtra("id", String.valueOf(recommendBean.get(position).getId()));
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }


    /**
     * 加载视图
     */
    private void initView() {

        checkboxAllSelect.setChecked(true);
        rvGoodsCart.setLayoutManager(new LinearLayoutManager(ShoppingCartActivity.this));
        adapter = new CommonAdapter<ShoppingCartBean.DataBean>(ShoppingCartActivity.this,
                R.layout.listitem_shopping_cart, dataList) {
            @Override
            protected void convert(final ViewHolder holder, final ShoppingCartBean.DataBean
                    dataBean, final int position) {
                Glide.with(ShoppingCartActivity.this)
                        .load(Constant.IMG_HOST + dataBean.getGoods_img())
                        .placeholder(R.mipmap.place_holder_news)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into((ImageView) holder.getView(R.id.img_item_goods));
                TextView tvGoodsName = holder.getView(R.id.tv_goods_name);
                tvGoodsName.setTypeface(DisplayUtils.setTextType(mContext));
                tvGoodsName.setText(dataBean.getGoods_name());
                TextView tvContent = holder.getView(R.id.tv_goods_content);

                String parts = "";
                switch (dataBean.getCategory_id()) {
                    case 1:
                        for (ShoppingCartBean.DataBean.PartBean partBean : dataBean.getPart()) {
                            parts += partBean.getPart_name() + ":" + partBean.getPart_value() + ";";
                        }
                        parts = parts.substring(0, parts.length() - 1);
                        break;
                    case 2:
                        for (ShoppingCartBean.DataBean.SkuBean skuBean : dataBean.getSku()) {
                            parts += skuBean.getType() + ":" + skuBean.getValue() + ";";
                        }
                        parts = parts.substring(0, parts.length() - 1);
                        break;
                }
                tvContent.setText(parts);


                holder.setText(R.id.tv_goods_price, "¥" + DisplayUtils.decimalFormat(
                        Float.parseFloat(dataBean.getPrice())));
                holder.setText(R.id.tv_goods_count, "x" + dataBean.getGoods_num());
                holder.setVisible(R.id.ll_cart_edit, isEdited);
                holder.setVisible(R.id.tv_goods_content, !isEdited);
                holder.setVisible(R.id.tv_goods_price, !isEdited);
                holder.setVisible(R.id.tv_goods_count, !isEdited);
                holder.setText(R.id.tv_cart_count, dataBean.getGoods_num() + "");
                final CheckBox checkBox = holder.getView(R.id.checkbox_goods_select);
                checkBox.setOnCheckedChangeListener(null);
                holder.setChecked(R.id.checkbox_goods_select, dataBean.isSelect());

                tvContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dataBean.getCategory_id() == 1 && dataBean.getPart() != null) {
                            goodsPart(dataBean.getPart());
                        }

                    }
                });

                final TextView tvReduce = holder.getView(R.id.tv_cart_reduce);
                tvReduce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (dataBean.getGoods_num() > 1) {
                            changeCartCount(position - 1, dataBean.getGoods_num() - 1);
                        }
                    }
                });

                final TextView tvAdd = holder.getView(R.id.tv_cart_add);
                tvAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        changeCartCount(position - 1, dataBean.getGoods_num() + 1);
                    }
                });

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        dataBean.setSelect(b);
                        checkBox.setChecked(b);
                        int count = 0;
                        for (int i = 0; i < dataList.size(); i++) {
                            if (dataList.get(i).isSelect()) {
                                count++;
                            }
                        }
                        if (count == dataList.size()) {
                            checkboxAllSelect.setChecked(true);
                        } else {
                            checkboxAllSelect.setChecked(false);
                        }
                        getTotalCount();
                        getTotalPrice();
                    }
                });

            }
        };
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        rvGoodsCart.setAdapter(mLRecyclerViewAdapter);

        //刷新
        rvGoodsCart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        isRefresh = true;
                        page = 1;
                        checkboxAllSelect.setChecked(true);
                        initData();
                    }
                }, 1000);
            }
        });
        //加载更多
        rvGoodsCart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                RecyclerViewStateUtils.setFooterViewState(ShoppingCartActivity.this, rvGoodsCart,
                        0, LoadingFooter.State.Loading, null);
                isLoadMore = true;
                page++;
                initData();
            }
        });

        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!isEdited) {
                    switch (dataList.get(position).getCategory_id()) {
                        case 1:
                            Intent intent = new Intent(ShoppingCartActivity.this, NewCustomizeGoodsActivity.class);
                            intent.putExtra("id", String.valueOf(dataList.get(position).getGoods_id()));
                            startActivity(intent);
                            break;
                        case 2:
                            Intent intent1 = new Intent(ShoppingCartActivity.this, WorksDetailActivity.class);
                            intent1.putExtra("id", String.valueOf(dataList.get(position).getGoods_id()));
                            startActivity(intent1);
                            break;
                    }
                }
            }
        });

        mLRecyclerViewAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                List<Integer> itemList = new ArrayList<>();
                itemList.add(dataList.get(position).getCart_id());
                deleteGoods(itemList);
            }
        });

        checkboxAllSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkboxAllSelect.isChecked()) {
                    checkboxAllSelect.setChecked(true);
                    for (int i = 0; i < dataList.size(); i++) {
                        dataList.get(i).setSelect(true);
                    }
                } else {
                    checkboxAllSelect.setChecked(false);
                    for (int i = 0; i < dataList.size(); i++) {
                        dataList.get(i).setSelect(false);
                    }
                }
                adapter.notifyDataSetChanged();
                getTotalCount();
                getTotalPrice();

            }
        });

    }

    /**
     * @param part 配件
     */
    private void goodsPart(final List<ShoppingCartBean.DataBean.PartBean> part) {
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
        CommonAdapter<ShoppingCartBean.DataBean.PartBean> adapter = new CommonAdapter<ShoppingCartBean
                .DataBean.PartBean>(this, R.layout.rv_goods_part_item, part) {
            @Override
            protected void convert(ViewHolder holder, ShoppingCartBean.DataBean.PartBean partBean, int position) {
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
                DisplayUtils.setBackgroundAlpha(ShoppingCartActivity.this, false);
            }
        });
    }

    /**
     * 购物车跳转定制详情
     */
    private void cartToCustomResult(int position) {
        OkHttpUtils.get()
                .url(Constant.CART_TO_CUSTOM)
                .addParams("token", SharedPreferencesUtils.getStr(ShoppingCartActivity.this, "token"))
                .addParams("cart_id", dataList.get(position).getCart_id() + "")
                .addParams("phone_type", "3")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CartDetailsBean cartDetails = GsonUtils.jsonToBean(response, CartDetailsBean.class);

                        if (cartDetails.getData() != null) {
                            Intent intent = new Intent(ShoppingCartActivity.this, CustomizeResultActivity.class);
                            Bundle bundle = new Bundle();
                            CustomItemBean customItemBean = new CustomItemBean();
                            customItemBean.setId(cartDetails.getData().getGoods_id() + "");
                            customItemBean.setGoods_name(cartDetails.getData().getGoods_name());
                            customItemBean.setPrice(DisplayUtils.decimalFormat((float) cartDetails
                                    .getData().getPrice()));
                            customItemBean.setPrice_type(cartDetails.getData().getPrice_id() + "");
                            customItemBean.setImg_url(cartDetails.getData().getGoods_thumb());
                            customItemBean.setSpec_ids(cartDetails.getData().getSpec_ids());
                            customItemBean.setSpec_content(cartDetails.getData().getSpec_content());
                            customItemBean.setIs_scan(cartDetails.getData().getIs_scan());
                            //0：个性定制 1：定制同款
                            switch (cartDetails.getData().getIs_scan()) {
                                case 0:
                                    List<CustomItemBean.ItemBean> itemList = new ArrayList<>();
                                    for (int i = 0; i < cartDetails.getData().getImg_list().size(); i++) {
                                        CustomItemBean.ItemBean itemBean = new CustomItemBean.ItemBean();
                                        itemBean.setImg(cartDetails.getData().getImg_list().get(i).getImg_c());
                                        itemBean.setPosition_id(cartDetails.getData().getImg_list().get(i)
                                                .getPosition_id());
                                        itemList.add(itemBean);
                                    }
                                    //图片
                                    customItemBean.setItemBean(itemList);
                                    customItemBean.setDefault_img(cartDetails.getData().getGoods_thumb());
                                    break;
                                case 1:
                                    customItemBean.setDefault_img(cartDetails.getData().getGoods_thumb());
                                    break;
                            }

                            //面料
                            if (!TextUtils.isEmpty(cartDetails.getData().getMianliao_id())) {
                                customItemBean.setFabric_id(cartDetails.getData().getMianliao_id());
                            }

                            //版型
                            if (!TextUtils.isEmpty(cartDetails.getData().getBanxing_id())) {
                                customItemBean.setBanxing_id(cartDetails.getData().getBanxing_id());
                            }

                            //部件id
                            customItemBean.setSpec_ids(cartDetails.getData().getSpec_ids());
                            //部件名称
                            customItemBean.setSpec_content(cartDetails.getData().getSpec_content());
                            //个性定制
                            if (!TextUtils.isEmpty(cartDetails.getData().getDiy_content())) {
                                customItemBean.setDiy_contet(cartDetails.getData().getDiy_content());
                            }
                            bundle.putSerializable("tailor", customItemBean);

                            intent.putExtras(bundle);
                            startActivity(intent);
                        }

                    }
                });
    }

    /**
     * @param position 改变购物车数量
     */
    private void changeCartCount(final int position, final int count) {
        OkHttpUtils.post()
                .url(Constant.CART_COUNT)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("cart_id", String.valueOf(dataList.get(position).getCart_id()))
                .addParams("buy_num", String.valueOf(count))
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
                                dataList.get(position).setGoods_num(count);
                                adapter.notifyDataSetChanged();
                                getTotalPrice();
                                getTotalCount();
                            } else {
                                ToastUtils.showToast(ShoppingCartActivity.this, msg);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }


    /**
     * 删除购物车
     */
    private void deleteGoods(final List<Integer> itemList) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.setTitle(R.string.delete_goods);
        dialog.setMessage(R.string.is_delete_goods);
        //为“确定”按钮注册监听事件
        dialog.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < itemList.size(); i++) {
                    if (i == itemList.size() - 1) {
                        sb.append(itemList.get(i));
                    } else {
                        sb.append(itemList.get(i)).append(",");
                    }

                }

                OkHttpUtils.post()
                        .url(Constant.DELETE_CART)
                        .addParams("token", SharedPreferencesUtils.getStr(ShoppingCartActivity.this, "token"))
                        .addParams("cart_id", sb.toString())
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
                                        for (int i = 0; i < dataList.size(); i++) {
                                            for (int j = 0; j < itemList.size(); j++) {
                                                if (dataList.get(i).getCart_id() == (itemList.get(j))) {
                                                    dataList.remove(i);
                                                    adapter.notifyItemRemoved(i);
                                                    if (i != dataList.size()) {
                                                        adapter.notifyItemRangeChanged(i, dataList.size() - i);
                                                    }
                                                }
                                            }
                                        }
                                        if (dataList.size() == 0) {
                                            nullCart();
                                        } else {
                                            getTotalCount();
                                            getTotalPrice();
                                        }
                                        ToastUtils.showToast(ShoppingCartActivity.this, msg);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
            }
        });
        //为“取消”按钮注册监听事件
        dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 根据实际情况编写相应代码。
                dialog.dismiss();
            }
        });
        dialog.create();
        dialog.show();
    }


    @OnClick({R.id.img_header_back, R.id.tv_my_bag, R.id.tv_header_next, R.id.tv_goods_buy, R.id.img_load_error})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                finish();
                break;
            case R.id.tv_goods_buy:
                if (isEdited) {
                    if (getTotalCount() > 0) {
                        deleteGoods(getSelected());
                    } else {
                        ToastUtils.showToast(this, getString(R.string.please_select_goods));
                    }
                } else {
                    if (getTotalCount() > 0) {
                        buyGoods();
                    } else {
                        ToastUtils.showToast(this, getString(R.string.please_select_goods));
                    }
                }

                break;
            case R.id.tv_my_bag:
                Intent intent = new Intent(ShoppingCartActivity.this, MainActivity.class);
                intent.putExtra("page", 1);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_header_next:
                if (!isEdited) {
                    isEdited = true;
                    adapter.notifyDataSetChanged();
                    tvHeaderNext.setText(R.string.confirm);
                    tvGoodsBuy.setText(R.string.delete);
                } else {
                    isEdited = false;
                    adapter.notifyDataSetChanged();
                    tvHeaderNext.setText(R.string.edit);
                    getTotalCount();
                }
                break;
            case R.id.img_load_error:
                initData();
                break;
        }
    }


    /**
     * 下单
     */
    private void buyGoods() {
        Intent intent = new Intent(this, ConfirmOrderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("cart_id", getCartIds());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * @return 已勾选商品
     */
    private List<Integer> getSelected() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).isSelect()) {
                list.add(dataList.get(i).getCart_id());
            }
        }

        return list;
    }


    /**
     * 已选择商品ids
     */
    private String getCartIds() {

        List<Integer> idList = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).isSelect()) {
                idList.add(dataList.get(i).getCart_id());
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < idList.size(); i++) {
            if (i == idList.size() - 1) {
                sb.append(idList.get(i));
            } else {
                sb.append(idList.get(i)).append(",");
            }
        }
        return sb.toString();
    }


    /**
     * 获取总价格
     */
    public void getTotalPrice() {
        float sum = 0;
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).isSelect()) {
                sum += Float.parseFloat(dataList.get(i).getPrice()) * dataList.get(i).getGoods_num();
            }
        }
        tvTotalPrice.setText("¥" + DisplayUtils.decimalFormat(sum));
    }


    /**
     * 总数量
     */
    public int getTotalCount() {
        int selectCount = 0;
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).isSelect()) {
                selectCount += dataList.get(i).getGoods_num();
            }
        }
        if (isEdited) {
            tvGoodsBuy.setText(R.string.delete);
        } else {
            tvGoodsBuy.setText(getString(R.string.confirm_order) + "(" + selectCount + ")");
        }
        return selectCount;
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
