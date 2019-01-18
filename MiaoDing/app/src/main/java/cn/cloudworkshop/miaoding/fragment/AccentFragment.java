package cn.cloudworkshop.miaoding.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wang.avi.AVLoadingIndicatorView;
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
import butterknife.Unbinder;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseFragment;
import cn.cloudworkshop.miaoding.bean.AccentBean;
import cn.cloudworkshop.miaoding.bean.NewDesignWorksBean;
import cn.cloudworkshop.miaoding.bean.OrderInfoBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.ui.LoginActivity;
import cn.cloudworkshop.miaoding.ui.WorksDetailActivity;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.ShareUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2017/12/6 10:32
 * Email：1993911441@qq.com
 * Describe：腔调
 */
public class AccentFragment extends BaseFragment {

    @BindView(R.id.img_load_error)
    ImageView imgLoadError;
    @BindView(R.id.rv_accent)
    RecyclerView rvAccent;
    @BindView(R.id.sfl_accent)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.view_loading)
    AVLoadingIndicatorView viewLoading;
    private Unbinder unbinder;

    private List<AccentBean.DataBean.GoodsBean> accentList = new ArrayList<>();

    //当前页
    private int page = 1;
    //刷新
    private boolean isRefresh;
    //加载更多
    private boolean isLoadMore;
    //总页数
    private int pages;


    private CommonAdapter<AccentBean.DataBean.GoodsBean> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accent, container, false);
        unbinder = ButterKnife.bind(this, view);
        viewLoading.smoothToShow();
        initData();
        initView();
        return view;

    }

    /**
     * 加载数据
     */
    private void initData() {

        OkHttpUtils.get()
                .url(Constant.ACCENT_WORKS)
                .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                .addParams("page", String.valueOf(page))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        imgLoadError.setVisibility(View.VISIBLE);
                        viewLoading.smoothToHide();
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        if (imgLoadError != null) {
                            imgLoadError.setVisibility(View.GONE);
                        }
                        if (viewLoading != null && viewLoading.isShown()) {
                            viewLoading.smoothToHide();
                        }

                        AccentBean accentBean = GsonUtils.jsonToBean(response, AccentBean.class);
                        if (accentBean.getData().getPages() != null) {
                            pages = accentBean.getData().getPages().getTotalpage();
                        }

                        if (isRefresh) {
                            refreshLayout.finishRefresh();
                            isRefresh = false;
                            accentList.clear();
                        } else if (isLoadMore) {
                            refreshLayout.finishLoadMore();
                            isLoadMore = false;
                        } else {
                            accentList.clear();
                        }
                        if (accentBean.getCode() == 10000 && accentBean.getData().getGoods().size() > 0) {
                            accentList.addAll(accentBean.getData().getGoods());
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }


    /**
     * 加载视图
     */
    private void initView() {
        rvAccent.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CommonAdapter<AccentBean.DataBean.GoodsBean>(getActivity(),
                R.layout.listitem_accent, accentList) {
            @Override
            protected void convert(ViewHolder holder, final AccentBean.DataBean.GoodsBean dataBean, final int position) {
                holder.setText(R.id.tv_accent_name, dataBean.getName());
                SimpleDraweeView imgWorks = holder.getView(R.id.img_accent_works);
                if (!TextUtils.isEmpty(dataBean.getAd_img_info())) {
                    imgWorks.setAspectRatio(Float.parseFloat(dataBean.getAd_img_info()));
                }
                imgWorks.setImageURI(Constant.IMG_HOST + dataBean.getAd_img());


                ImageView imgShare = (ImageView) holder.itemView.findViewById(R.id.img_accent_share);
                ImageView imgCollect = (ImageView) holder.itemView.findViewById(R.id.img_accent_collect);
                LinearLayout llLove = (LinearLayout) holder.itemView.findViewById(R.id.ll_accent_love);
                ImageView imgLove = (ImageView) holder.itemView.findViewById(R.id.img_accent_love);
                TextView tvLove = (TextView) holder.itemView.findViewById(R.id.tv_accent_love);

                tvLove.setText(String.valueOf(dataBean.getLove_num()));

                if (dataBean.getIs_collect() == 1) {
                    imgCollect.setImageResource(R.mipmap.icon_collect_check);
                } else {
                    imgCollect.setImageResource(R.mipmap.icon_collect_normal);
                }
                if (dataBean.getIs_love() == 1) {
                    imgLove.setImageResource(R.mipmap.icon_love_check);
                } else {
                    imgLove.setImageResource(R.mipmap.icon_love_normal);
                }


                imgShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String share_url = Constant.CUSTOM_SHARE + "?goods_id=" + dataBean.getId();
                        ShareUtils.showShare(getActivity(), Constant.IMG_HOST + dataBean.getAd_img(),
                                dataBean.getName(), dataBean.getContent(), share_url);
                    }
                });

                imgCollect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(SharedPreferencesUtils.getStr(getActivity(), "token"))) {
                            addCollection(dataBean.getId(), position);
                        } else {
                            Intent login = new Intent(getActivity(), LoginActivity.class);
                            login.putExtra("page_name", "收藏");
                            startActivity(login);
                        }

                    }
                });

                llLove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(SharedPreferencesUtils.getStr(getActivity(), "token"))) {
                            addLove(dataBean.getId(), position, dataBean.getLove_num());
                        } else {
                            Intent login = new Intent(getActivity(), LoginActivity.class);
                            login.putExtra("page_name", "喜爱");
                            startActivity(login);
                        }

                    }
                });

            }

        };

        rvAccent.setAdapter(adapter);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(getActivity(), WorksDetailActivity.class);
                intent.putExtra("id", String.valueOf(accentList.get(position).getId()));
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });


        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (page < pages) {
                    isLoadMore = true;
                    page++;
                    initData();
                } else {
                    refreshLayout.finishLoadMore();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isRefresh = true;
                page = 1;
                initData();
            }
        });

    }

    /**
     * 添加收藏
     */
    private void addCollection(int id, final int position) {
        OkHttpUtils.get()
                .url(Constant.ADD_COLLECTION)
                .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                .addParams("type", "2")
                .addParams("rid", String.valueOf(id))
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
                                accentList.get(position).setIs_collect(status);
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    /**
     * 添加喜爱
     */
    private void addLove(int id, final int position, final int loveNum) {
        OkHttpUtils.get()
                .url(Constant.ADD_LOVE)
                .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                .addParams("rid", String.valueOf(id))
                .addParams("type", "2")
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
                                accentList.get(position).setIs_love(status);
                                if (status == 1) {
                                    accentList.get(position).setLove_num(loveNum + 1);
                                } else {
                                    accentList.get(position).setLove_num(loveNum - 1);
                                }
                                adapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    public static AccentFragment newInstance() {
        Bundle args = new Bundle();
        AccentFragment fragment = new AccentFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @OnClick(R.id.img_load_error)
    public void onViewClicked() {
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
