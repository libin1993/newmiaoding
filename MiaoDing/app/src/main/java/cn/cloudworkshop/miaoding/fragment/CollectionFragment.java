package cn.cloudworkshop.miaoding.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import cn.cloudworkshop.miaoding.base.BaseFragment;
import cn.cloudworkshop.miaoding.bean.CollectionBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.ui.HomepageInfoActivity;
import cn.cloudworkshop.miaoding.ui.MainActivity;
import cn.cloudworkshop.miaoding.ui.NewCustomizeGoodsActivity;
import cn.cloudworkshop.miaoding.ui.NewCustomizedGoodsActivity;
import cn.cloudworkshop.miaoding.ui.StoreInfoActivity;
import cn.cloudworkshop.miaoding.ui.WorksDetailActivity;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.SpaceItemDecoration;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2017-07-18 17:52
 * Email：1993911441@qq.com
 * Describe：收藏
 */
public class CollectionFragment extends BaseFragment {
    @BindView(R.id.rv_goods)
    LRecyclerView rvCollection;
    @BindView(R.id.img_no_order)
    ImageView imgNoCollect;
    @BindView(R.id.tv_my_order)
    TextView tvCollection;
    @BindView(R.id.ll_null_order)
    LinearLayout llNullCollect;
    @BindView(R.id.img_load_error)
    ImageView imgLoadingError;
    @BindView(R.id.view_loading)
    AVLoadingIndicatorView viewLoading;
    private Unbinder unbinder;

    private List<CollectionBean.DataBean.CollectionsBean> itemList = new ArrayList<>();
    //页数
    private int page = 1;
    //刷新
    private boolean isRefresh;
    //加载更多
    private boolean isLoadMore;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    //收藏类型 1:资讯 2：商品 3：店铺

    private int type;
    private CommonAdapter<CollectionBean.DataBean.CollectionsBean> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager_item_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        getData();
        initData();
        initView();
        return view;
    }

    private void getData() {
        Bundle bundle = getArguments();
        type = bundle.getInt("type");
        viewLoading.smoothToShow();
    }

    private void initData() {
        OkHttpUtils.get()
                .url(Constant.COLLECTION)
                .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                .addParams("type", String.valueOf(type))
                .addParams("page", String.valueOf(page))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        imgLoadingError.setVisibility(View.VISIBLE);
                        if (viewLoading != null && viewLoading.isShown()) {
                            viewLoading.smoothToHide();
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        imgLoadingError.setVisibility(View.GONE);
                        if (viewLoading != null && viewLoading.isShown()) {
                            viewLoading.smoothToHide();
                        }
                        CollectionBean collectionBean = GsonUtils.jsonToBean(response, CollectionBean.class);
                        if (collectionBean.getData().getCollections() != null && collectionBean.getData().getCollections().size() > 0) {
                            if (isRefresh) {
                                itemList.clear();
                            }
                            itemList.addAll(collectionBean.getData().getCollections());
                            //刷新、加载更多
                            if (isLoadMore || isRefresh) {
                                rvCollection.refreshComplete(0);
                            }
                            mLRecyclerViewAdapter.notifyDataSetChanged();
                            isRefresh = false;
                            isLoadMore = false;
                            llNullCollect.setVisibility(View.GONE);
                            rvCollection.setVisibility(View.VISIBLE);

                        } else {
                            RecyclerViewStateUtils.setFooterViewState(getActivity(),
                                    rvCollection, 0, LoadingFooter.State.NoMore, null);
                            if (page == 1) {
                                rvCollection.setVisibility(View.GONE);
                                imgNoCollect.setImageResource(R.mipmap.icon_null_collection);
                                llNullCollect.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });

    }

    /**
     * 加载视图
     */
    private void initView() {
        switch (type) {
            case 1:
                rvCollection.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new CommonAdapter<CollectionBean.DataBean.CollectionsBean>(getActivity(),
                        R.layout.rv_news_item, itemList) {
                    @Override
                    protected void convert(ViewHolder holder, CollectionBean.DataBean.CollectionsBean collectionsBean, int position) {

                        SimpleDraweeView imgNews = holder.getView(R.id.iv_homepage_news);

                        if (!TextUtils.isEmpty(collectionsBean.getImg_info())) {
                            imgNews.setAspectRatio(Float.parseFloat(collectionsBean.getImg_info()));
                        }

                        imgNews.setImageURI(Constant.IMG_HOST + collectionsBean.getImg());
                        holder.setText(R.id.tv_homepage_news_title, collectionsBean.getTitle());
                        holder.setText(R.id.tv_homepage_news_content, collectionsBean.getSub_title());
                    }
                };
                break;
            case 2:
                rvCollection.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                rvCollection.addItemDecoration(new SpaceItemDecoration((int) DisplayUtils.dp2px(
                        getActivity(), 4.5f), true));

                adapter = new CommonAdapter<CollectionBean.DataBean.CollectionsBean>(getActivity(),
                        R.layout.listitem_sub_goods, itemList) {
                    @Override
                    protected void convert(ViewHolder holder, CollectionBean.DataBean.CollectionsBean itemBean, int position) {
                        SimpleDraweeView imgGoods = holder.getView(R.id.img_sub_goods);
                        imgGoods.setImageURI(Constant.IMG_HOST + itemBean.getImg());
                        imgGoods.setAspectRatio(Float.parseFloat(itemBean.getImg_info()));
                        holder.setText(R.id.tv_sub_title, itemBean.getName());
                        holder.setText(R.id.tv_sub_price, "¥ " + itemBean.getPrice());
                        holder.setText(R.id.tv_sub_content, itemBean.getSub_name());
                    }
                };
                break;
            case 3:
                rvCollection.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new CommonAdapter<CollectionBean.DataBean.CollectionsBean>(getActivity(),
                        R.layout.listitem_store, itemList) {
                    @Override
                    protected void convert(ViewHolder holder, CollectionBean.DataBean.CollectionsBean itemBean, int position) {
                        Glide.with(getActivity())
                                .load(Constant.IMG_HOST + itemBean.getImg())
                                .placeholder(R.mipmap.place_holder_goods)
                                .dontAnimate()
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .into((ImageView) holder.getView(R.id.img_store));
                        holder.setText(R.id.tv_store_name, itemBean.getFactory_name());
                        holder.setText(R.id.tv_store_love_num, getString(R.string.fans) + "： " + itemBean.getLovenum());
                        holder.setText(R.id.tv_address_store, getString(R.string.store_address) + "： " + itemBean.getAddress());
                    }
                };
                break;
        }

        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        rvCollection.setAdapter(mLRecyclerViewAdapter);

        //刷新
        rvCollection.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        isRefresh = true;
                        page = 1;
                        initData();
                    }
                }, 1000);
            }
        });
        //加载更多
        rvCollection.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                RecyclerViewStateUtils.setFooterViewState(getActivity(), rvCollection, 0,
                        LoadingFooter.State.Loading, null);
                isLoadMore = true;
                page++;
                initData();
            }
        });


        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = null;
                switch (type) {
                    case 1:
                        intent = new Intent(getActivity(), HomepageInfoActivity.class);
                        intent.putExtra("url", Constant.HOMEPAGE_INFO + "?content=1&id="
                                + itemList.get(position).getRid());
                        intent.putExtra("title", itemList.get(position).getTitle());
                        intent.putExtra("content", itemList.get(position).getSub_title());
                        intent.putExtra("img_url", itemList.get(position).getImg());
                        intent.putExtra("share_url", Constant.HOMEPAGE_SHARE +
                                "?content=1&id=" + itemList.get(position).getRid());
                        break;
                    case 2:
                        switch (itemList.get(position).getGoods_type()) {
                            case 1:
                                intent = new Intent(getActivity(), NewCustomizeGoodsActivity.class);
                                intent.putExtra("id", String.valueOf(itemList.get(position).getRid()));
                                break;
                            case 2:
                                intent = new Intent(getActivity(), WorksDetailActivity.class);
                                intent.putExtra("id", String.valueOf(itemList.get(position).getRid()));
                                break;
                            default:
                                intent = new Intent(getActivity(), MainActivity.class);
                                intent.putExtra("page", type - 1);
                                break;

                        }

                        break;
                    case 3:
                        intent = new Intent(getActivity(), StoreInfoActivity.class);
                        intent.putExtra("shop_id", itemList.get(position).getRid());
                        break;
                }
                startActivity(intent);
            }
        });

        mLRecyclerViewAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                cancelCollection(itemList.get(position).getRid(), position);
            }
        });

    }


    /**
     * 取消收藏
     */
    private void cancelCollection(final int cid, final int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(),
                R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.setTitle(getString(R.string.cancel_collect));
        dialog.setMessage(R.string.is_cancel_collect);
        //为“确定”按钮注册监听事件
        dialog.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OkHttpUtils.get()
                        .url(Constant.ADD_COLLECTION)
                        .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                        .addParams("type", String.valueOf(type))
                        .addParams("rid", String.valueOf(cid))
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
                                    String msg = jsonObject.getString("msg");
                                    if (code == 10000 && status == 2) {

                                        itemList.remove(position);
                                        adapter.notifyItemRemoved(position);
                                        if (position != itemList.size()) {
                                            adapter.notifyItemRangeChanged(position, itemList.size() - position);
                                        }
                                        if (itemList.size() == 0) {
                                            rvCollection.setVisibility(View.GONE);
                                            imgNoCollect.setImageResource(R.mipmap.icon_null_collection);
                                            llNullCollect.setVisibility(View.VISIBLE);
                                        }
                                    }
                                    ToastUtils.showToast(getActivity(), msg);

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

    public static CollectionFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        CollectionFragment fragment = new CollectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.tv_my_order, R.id.img_load_error})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_my_order:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("page", type - 1);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.img_load_error:
                initData();
                break;
        }
    }
}




