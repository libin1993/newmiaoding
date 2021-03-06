package cn.cloudworkshop.miaoding.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter;
import com.umeng.analytics.MobclickAgent;
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
import cn.cloudworkshop.miaoding.application.MyApplication;
import cn.cloudworkshop.miaoding.base.BaseFragment;
import cn.cloudworkshop.miaoding.bean.HomepageNewsBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.ui.DesignerDetailActivity;
import cn.cloudworkshop.miaoding.ui.DressingResultActivity;
import cn.cloudworkshop.miaoding.ui.HomepageInfoActivity;
import cn.cloudworkshop.miaoding.ui.JoinUsActivity;
import cn.cloudworkshop.miaoding.ui.LoginActivity;
import cn.cloudworkshop.miaoding.ui.NewCustomizeGoodsActivity;
import cn.cloudworkshop.miaoding.ui.NewCustomizedGoodsActivity;
import cn.cloudworkshop.miaoding.ui.NewsListActivity;
import cn.cloudworkshop.miaoding.ui.StoreListActivity;
import cn.cloudworkshop.miaoding.ui.WorksDetailActivity;
import cn.cloudworkshop.miaoding.utils.DateUtils;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.NetworkImageHolderView;
import cn.cloudworkshop.miaoding.utils.RVItemDecoration;
import cn.cloudworkshop.miaoding.utils.RecyclerItemDecoration;
import cn.cloudworkshop.miaoding.utils.ShareUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2017/12/6 13:13
 * Email：1993911441@qq.com
 * Describe：首页（当前版）
 */
public class HomepageFragment extends BaseFragment {
    @BindView(R.id.rv_homepage_news)
    LRecyclerView rvNews;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;
    @BindView(R.id.view_loading)
    AVLoadingIndicatorView viewLoading;
    private Unbinder unbinder;
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    private List<HomepageNewsBean.DataBean.ArticleBean> dataList = new ArrayList<>();
    private List<HomepageNewsBean.DataBean.BannerBean> bannerList = new ArrayList<>();
    private List<HomepageNewsBean.DataBean.IndextypeBean> indexList = new ArrayList<>();
    //当前页
    private int page = 1;
    //刷新
    private boolean isRefresh;
    //加载更多
    private boolean isLoadMore;
    private CommonAdapter<HomepageNewsBean.DataBean.ArticleBean> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage_news, container, false);
        unbinder = ButterKnife.bind(this, view);
        viewLoading.smoothToShow();
        initData();
        return view;
    }


    /**
     * 加载数据
     */
    private void initData() {
        OkHttpUtils.get()
                .url(Constant.HOMEPAGE_LIST)
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
                        if (viewLoading != null && viewLoading.getVisibility() == View.VISIBLE) {
                            viewLoading.smoothToHide();
                        }

                        HomepageNewsBean homepageBean = GsonUtils.jsonToBean(response, HomepageNewsBean.class);
                        if (homepageBean.getData() != null && homepageBean.getData().getArticle().size() > 0) {
                            //刷新，初始化数据
                            if (isRefresh) {
                                dataList.clear();

                                bannerList.clear();

                                indexList.clear();
                            }

                            if (!isLoadMore) {
                                bannerList.addAll(homepageBean.getData().getBanner());
                                indexList.addAll(homepageBean.getData().getIndextype());
                            }

                            dataList.addAll(homepageBean.getData().getArticle());

                            //刷新，加载完成
                            if (isLoadMore || isRefresh) {
                                rvNews.refreshComplete(0);
                                mLRecyclerViewAdapter.notifyDataSetChanged();
                            } else {
                                initView();
                            }
                            isLoadMore = false;
                            isRefresh = false;

                        } else {
                            RecyclerViewStateUtils.setFooterViewState(getActivity(), rvNews,
                                    0, LoadingFooter.State.NoMore, null);
                        }
                    }
                });
    }

    /**
     * 加载视图
     */
    private void initView() {
        rvNews.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CommonAdapter<HomepageNewsBean.DataBean.ArticleBean>(getActivity(),
                R.layout.rv_news_item, dataList) {
            @Override
            protected void convert(ViewHolder holder, final HomepageNewsBean.DataBean.ArticleBean articleBean, final int position) {

                SimpleDraweeView imgNews = holder.getView(R.id.iv_homepage_news);

                imgNews.setAspectRatio(Float.parseFloat(articleBean.getImg_info()));

                imgNews.setImageURI(Constant.IMG_HOST + articleBean.getImg());
                holder.setText(R.id.tv_homepage_news_title, articleBean.getTitle());
                holder.setText(R.id.tv_homepage_news_content, articleBean.getSub_title());


//                ImageView imgShare = (ImageView) holder.itemView.findViewById(R.id.img_news_share);
//                ImageView imgCollect = (ImageView) holder.itemView.findViewById(R.id.img_add_collect);
//
//                LinearLayout llLove = (LinearLayout) holder.itemView.findViewById(R.id.ll_news_love);
//                ImageView imgLove = (ImageView) holder.itemView.findViewById(R.id.img_love_num);
//                TextView tvLove = (TextView) holder.itemView.findViewById(R.id.tv_love_num);
//
//                LinearLayout llComment = (LinearLayout) holder.itemView.findViewById(R.id.ll_news_comment);
//                TextView tvComment = (TextView) holder.itemView.findViewById(R.id.tv_comment_num);
//
//
//
//                tvLove.setText(String.valueOf(articleBean.getLike_nums()));
//                tvComment.setText(String.valueOf(articleBean.getReply_nums()));
//
//                if (articleBean.getIs_collect() == 1) {
//                    imgCollect.setImageResource(R.mipmap.icon_collect_check);
//                } else {
//                    imgCollect.setImageResource(R.mipmap.icon_collect_normal);
//                }
//                if (articleBean.getIs_love() == 1) {
//                    imgLove.setImageResource(R.mipmap.icon_love_check);
//                } else {
//                    imgLove.setImageResource(R.mipmap.icon_love_normal);
//                }

                //图片点击
//                imgNews.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {

//                        toHomepageInfo(articleBean);
//                        switch (dataBean.getIs_type()) {
//                            case 1:
//                                toHomepageInfo(dataBean);
//                                break;
//                            case 3:
//                                Intent intent = new Intent(getActivity(), StoreInfoActivity.class);
//                                intent.putExtra("shop_id", dataBean.getId());
//                                startActivity(intent);
//                                break;
//                        }
//                    }
//                });
                //分享

//                imgShare.setVisibility(View.VISIBLE);
//                imgShare.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ShareUtils.showShare(getActivity(), Constant.IMG_HOST +
//                                articleBean.getImg(), articleBean.getTitle(), articleBean
//                                .getSub_title(), Constant.HOMEPAGE_SHARE +
//                                "?content=1&id=" + articleBean.getId());
//
//                    }
//                });
//
//
//                //收藏
//                imgCollect.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (!TextUtils.isEmpty(SharedPreferencesUtils.getStr(getActivity(), "token"))) {
//                            addCollection(articleBean.getId(), position - 2);
//                        } else {
//                            Intent login = new Intent(getActivity(), LoginActivity.class);
//                            login.putExtra("page_name", "收藏");
//                            startActivity(login);
//                        }
//
//                    }
//                });
//                //喜爱
//                llLove.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (!TextUtils.isEmpty(SharedPreferencesUtils.getStr(getActivity(), "token"))) {
//                            addLove(articleBean.getId(), position - 2, articleBean.getLike_nums());
//                        } else {
//                            Intent login = new Intent(getActivity(), LoginActivity.class);
//                            login.putExtra("page_name", "喜爱");
//                            startActivity(login);
//                        }
//
//                    }
//                });
//                //店铺隐藏评论
//                llComment.setVisibility(View.VISIBLE);
//                llComment.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        toHomepageInfo(articleBean);
//                    }
//                });


//                if (dataBean.getIs_type() == 1) {
//                    llComment.setVisibility(View.VISIBLE);
//                    llComment.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            toHomepageInfo(dataBean);
//                        }
//                    });
//                } else {
//                    llComment.setVisibility(View.GONE);
//                }

            }
        };

        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        rvNews.setAdapter(mLRecyclerViewAdapter);
        //添加头部
        mLRecyclerViewAdapter.addHeaderView(initHeader());

        mLRecyclerViewAdapter.setOnItemClickListener(new com.github.jdsjlzx.interfaces.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                toHomepageInfo(dataList.get(position));
            }
        });


        //刷新
        rvNews.setOnRefreshListener(new OnRefreshListener() {
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
        //加载下一页
        rvNews.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                RecyclerViewStateUtils.setFooterViewState(getActivity(), rvNews, 0,
                        LoadingFooter.State.Loading, null);
                isLoadMore = true;
                page++;
                initData();
            }
        });
    }

    /**
     * @param dataBean 跳转资讯详情
     */
    private void toHomepageInfo(HomepageNewsBean.DataBean.ArticleBean dataBean) {
//        homepageLog(dataBean.getTag_name());

        Intent intent = new Intent(getActivity(), HomepageInfoActivity.class);
        intent.putExtra("url", Constant.HOMEPAGE_INFO + "?content=1&id=" + dataBean.getId());
        intent.putExtra("title", dataBean.getTitle());
        intent.putExtra("content", dataBean.getSub_title());
        intent.putExtra("img_url", Constant.IMG_HOST + dataBean.getImg());
        intent.putExtra("share_url", Constant.HOMEPAGE_SHARE + "?content=1&id="
                + dataBean.getId());
        startActivity(intent);
    }

    /**
     * 添加收藏
     */
    private void addCollection(int id, final int position) {
        OkHttpUtils.post()
                .url(Constant.ADD_COLLECTION)
                .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                .addParams("rid", String.valueOf(id))
                .addParams("type", "1")
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
                                dataList.get(position).setIs_collect(status);
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
                .addParams("type", "1")
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
                                dataList.get(position).setIs_love(status);
                                if (status == 1) {
                                    dataList.get(position).setLike_nums(loveNum + 1);
                                } else {
                                    dataList.get(position).setLike_nums(loveNum - 1);
                                }
                                adapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    /**
     * 加载头部
     */
    private View initHeader() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_homepage_header, null);
        if (bannerList.size() > 0) {
            final ConvenientBanner banner = (ConvenientBanner) view.findViewById(R.id.banner_news);
            //设置banner宽高固定比
            ViewGroup.LayoutParams layoutParams1 = banner.getLayoutParams();
            int widthPixels = DisplayUtils.getMetrics(getActivity()).widthPixels;
            layoutParams1.width = widthPixels;
            double imgRatio = Double.parseDouble(bannerList.get(0).getImg_info());

            layoutParams1.height = (int) (widthPixels / imgRatio);
            banner.setLayoutParams(layoutParams1);

            banner.startTurning(4000);

            final List<String> bannerImg = new ArrayList<>();
            for (int i = 0; i < bannerList.size(); i++) {
                bannerImg.add(bannerList.get(i).getImg());
            }

            banner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                @Override
                public NetworkImageHolderView createHolder() {
                    return new NetworkImageHolderView();
                }
            }, bannerImg)
                    //设置两个点图片作为翻页指示器
                    .setPageIndicator(new int[]{R.drawable.indicator_normal, R.drawable.indicator_focus})
                    //设置指示器的方向
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);


            banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (bannerImg.size() < 2) {
                        banner.stopTurning();
                    }
                }
            });


            banner.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
//                    homepageLog("banner");
                    //banner点击事件统计
                    MobclickAgent.onEvent(getActivity(), "banner");
                    switch (bannerList.get(position).getType()) {
                        //咨询页webview
                        case 1:
                            Intent intent = new Intent(getActivity(), HomepageInfoActivity.class);
                            intent.putExtra("url", Constant.HOMEPAGE_INFO + "?content=1&id="
                                    + bannerList.get(position).getLink());
                            intent.putExtra("title", bannerList.get(position).getName());
                            intent.putExtra("content", "");
                            intent.putExtra("img_url", Constant.IMG_HOST +
                                    bannerList.get(position).getImg());
                            intent.putExtra("share_url", Constant.HOMEPAGE_SHARE + "?content=1&id="
                                    + bannerList.get(position).getLink());
                            startActivity(intent);

                            break;
                        //设计师申请入驻
                        case 2:
                            startActivity(new Intent(getActivity(), JoinUsActivity.class));
                            break;
                        //定制商品
                        case 3:
                            Intent intent1 = new Intent(getActivity(), NewCustomizeGoodsActivity.class);
                            intent1.putExtra("id", String.valueOf(bannerList.get(position).getLink()));
                            startActivity(intent1);
                            break;
                        //成品
                        case 4:
                            Intent intent2 = new Intent(getActivity(), WorksDetailActivity.class);
                            intent2.putExtra("id", String.valueOf(bannerList.get(position).getLink()));
                            startActivity(intent2);
                            break;
                        //设计师详情
                        case 5:
                            Intent intent3 = new Intent(getActivity(), DesignerDetailActivity.class);
                            intent3.putExtra("id", String.valueOf(bannerList.get(position).getLink()));
                            startActivity(intent3);
                            break;
                        //邀请好友
                        case 6:
                            if (!TextUtils.isEmpty(SharedPreferencesUtils.getStr(getActivity(), "token"))) {
                                String uid = SharedPreferencesUtils.getStr(getActivity(), "uid");
                                Intent invite = new Intent(getActivity(), DressingResultActivity.class);
                                invite.putExtra("title", getString(R.string.invite_gift));
                                invite.putExtra("share_title", getString(R.string.invite_friend_gift));
                                invite.putExtra("share_content", getString(R.string.friend) + SharedPreferencesUtils
                                        .getStr(getActivity(), "username") + getString(R.string.invite_your_friend));
                                invite.putExtra("url", Constant.INVITE_FRIENDS + "?uid=" + uid);
                                invite.putExtra("share_url", Constant.INVITE_SHARE + "?uid=" + uid);
                                startActivity(invite);
                            } else {
                                Intent login = new Intent(getActivity(), LoginActivity.class);
                                login.putExtra("page_name", "banner");
                                startActivity(login);
                            }
                            break;
                    }
                }
            });
        }


        RecyclerView rvGoods = (RecyclerView) view.findViewById(R.id.rv_news_recommend);

        rvGoods.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvGoods.addItemDecoration(new RecyclerItemDecoration((int) DisplayUtils.dp2px(getActivity(), 25)));
        CommonAdapter<HomepageNewsBean.DataBean.IndextypeBean> adapter = new CommonAdapter<HomepageNewsBean
                .DataBean.IndextypeBean>(getActivity(), R.layout.listitem_recommend_shop, indexList) {
            @Override
            protected void convert(ViewHolder holder, HomepageNewsBean.DataBean.IndextypeBean indextypeBean, int position) {
                SimpleDraweeView imgShop = holder.getView(R.id.img_recommend_shop);

                imgShop.setAspectRatio(Float.parseFloat(indextypeBean.getImg_info()));

                imgShop.setImageURI(Constant.IMG_HOST + indextypeBean.getImg());

                holder.setText(R.id.tv_recommend_name, indextypeBean.getGoods_name());
                holder.setText(R.id.tv_recommend_price, "¥ " + indextypeBean.getSell_price());
            }

        };

        rvGoods.setAdapter(adapter);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent;
                HomepageNewsBean.DataBean.IndextypeBean indextype = indexList.get(position);
                switch (indextype.getType()) {
                    //资讯
                    case 1:
                        intent = new Intent(getActivity(), NewsListActivity.class);
                        startActivity(intent);
                        break;
                    //定制
                    case 2:
                        intent = new Intent(getActivity(), NewCustomizeGoodsActivity.class);
                        intent.putExtra("id", String.valueOf(indextype.getLink()));
                        startActivity(intent);
                        break;
                    //成品
                    case 3:
                        intent = new Intent(getActivity(), WorksDetailActivity.class);
                        intent.putExtra("id", String.valueOf(indextype.getLink()));
                        startActivity(intent);
                        break;
                    //店铺
                    case 4:
                        intent = new Intent(getActivity(), StoreListActivity.class);
                        startActivity(intent);
                        break;
                    //设计师
                    case 5:
                        intent = new Intent(getActivity(), DesignerDetailActivity.class);
                        intent.putExtra("id", String.valueOf(indextype.getLink()));
                        startActivity(intent);
                        break;
                }

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        return view;
    }


    /**
     * 首页跟踪
     */
    private void homepageLog(String module_name) {
//        long time = DateUtils.getCurrentTime() - MyApplication.homeEnterTime;
//        OkHttpUtils.post()
//                .url(Constant.HOMEPAGE_LOG)
//                .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
//                .addParams("time", String.valueOf(time))
//                .addParams("p_module_name", "首页")
//                .addParams("module_name", module_name)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                    }
//                });

    }


    public static HomepageFragment newInstance() {

        Bundle args = new Bundle();

        HomepageFragment fragment = new HomepageFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.img_load_error)
    public void onViewClicked() {
        initData();
    }

}


