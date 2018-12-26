package cn.cloudworkshop.miaoding.utils;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.application.MyApplication;
import cn.cloudworkshop.miaoding.bean.AppIconBean;
import cn.cloudworkshop.miaoding.constant.Constant;

/**
 * Author：Libin on 2017-06-21 10:45
 * Email：1993911441@qq.com
 * Describe：主界面 底部切换tab工具类（当前版）
 */
public class FragmentTabUtils implements TabLayout.OnTabSelectedListener {

    private List<Fragment> fragmentList; // 一个tab页面对应一个Fragment
    private TabLayout tabLayout; // 用于切换tab
    private FragmentManager fragmentManager; // Fragment所属的Activity
    private int fragmentContentId; // Activity中当前fragment的区域的id
    private int currentTab; // 当前Tab页面索引
    private Context mContext;
    private List<AppIconBean.DataBean> iconList;


    /**
     * @param fragmentManager
     * @param fragmentList
     * @param fragmentContentId
     * @param tabLayout
     */
    public FragmentTabUtils(Context context, FragmentManager fragmentManager, List<Fragment> fragmentList,
                            int fragmentContentId, TabLayout tabLayout, List<AppIconBean.DataBean> iconList) {
        this.mContext = context;
        this.fragmentList = fragmentList;
        this.tabLayout = tabLayout;
        this.fragmentManager = fragmentManager;
        this.fragmentContentId = fragmentContentId;
        this.iconList = iconList;
        MyApplication.homeEnterTime = DateUtils.getCurrentTime();
        initTab();
        tabLayout.addOnTabSelectedListener(this);
        tabLayout.getTabAt(0).select();

    }

    /**
     * 加载底部Tab
     */
    private void initTab() {
        for (int i = 0; i < iconList.size(); i++) {
            TabLayout.Tab tab = tabLayout.newTab();

            View view = LayoutInflater.from(mContext).inflate(R.layout.tablayout_item, null);
            ImageView imgTab = (ImageView) view.findViewById(R.id.img_tab);
            TextView tvTab = (TextView) view.findViewById(R.id.tv_tab);
            tvTab.setText(iconList.get(i).getName());
            if (i == 0) {
                tvTab.setTextColor(ContextCompat.getColor(mContext, R.color.dark_gray_22));
                Glide.with(mContext)
                        .load(Constant.IMG_HOST + iconList.get(i).getSelect_img())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(imgTab);
            } else {
                tvTab.setTextColor(ContextCompat.getColor(mContext, R.color.light_gray_4f));
                Glide.with(mContext)
                        .load(Constant.IMG_HOST + iconList.get(i).getImg())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(imgTab);
            }

            tab.setCustomView(view);
            tabLayout.addTab(tab);
        }

        switchFragment(0);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switchTab(tab, R.color.dark_gray_22, iconList.get(tab.getPosition()).getSelect_img());

        if (tab.getPosition() == 0) {
            MyApplication.homeEnterTime = DateUtils.getCurrentTime();
        }
        switchFragment(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        switchTab(tab, R.color.light_gray_4f, iconList.get(tab.getPosition()).getImg());

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    /**
     * @param tab
     * @param cid
     * @param imgUrl tab切换
     */
    private void switchTab(TabLayout.Tab tab, int cid, String imgUrl) {
        View customView = tab.getCustomView();
        TextView tvTab = (TextView) customView.findViewById(R.id.tv_tab);
        ImageView imgTab = (ImageView) customView.findViewById(R.id.img_tab);
        tvTab.setTextColor(ContextCompat.getColor(mContext, cid));
        Glide.with(mContext)
                .load(Constant.IMG_HOST + imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgTab);
    }


    /**
     * @param position 切换fragment
     */
    private void switchFragment(int position) {
        //开启事务
        FragmentTransaction ft = fragmentManager.beginTransaction();
        //遍历集合
        for (int i = 0; i < fragmentList.size(); i++) {
            Fragment fragment = fragmentList.get(i);
            if (i == position) {
                //显示fragment
                if (fragment.isAdded()) {
                    //如果这个fragment已经被事务添加,显示
                    ft.show(fragment);
                } else {
                    //如果这个fragment没有被事务添加过,添加
                    ft.add(fragmentContentId, fragment);
                }
            } else {
                //隐藏fragment
                if (fragment.isAdded()) {
                    ft.hide(fragment);
                }
            }
        }
        //提交事务
        ft.commitAllowingStateLoss();

    }

    /**
     * @param position 设置当前fragment
     */
    public void setCurrentFragment(int position) {
        tabLayout.getTabAt(position).select();
    }


}
