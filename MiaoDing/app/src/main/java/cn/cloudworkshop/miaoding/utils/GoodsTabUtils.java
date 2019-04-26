package cn.cloudworkshop.miaoding.utils;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.List;

/**
 * Author：Libin on 2019/4/25 16:03
 * Email：1993911441@qq.com
 * Describe：
 */
public class GoodsTabUtils implements TabLayout.OnTabSelectedListener {
    private List<Fragment> fragmentList; // 一个tab页面对应一个Fragment
    private TabLayout tabLayout; // 用于切换tab
    private FragmentManager fragmentManager; // Fragment所属的Activity
    private int flContainerId; // Activity中当前fragment的区域的id
    private Context mContext;

    /**
     * @param fragmentManager
     * @param fragmentList
     * @param flContainerId
     */
    public GoodsTabUtils(Context context, FragmentManager fragmentManager, List<Fragment> fragmentList,
                         int flContainerId, TabLayout tabLayout) {
        this.mContext = context;
        this.fragmentList = fragmentList;
        this.tabLayout = tabLayout;
        this.fragmentManager = fragmentManager;
        this.flContainerId = flContainerId;

        tabLayout.addOnTabSelectedListener(this);

       switchFragment(0);

    }


    /**
     * 切换fragment
     *
     * @param position
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
                    ft.add(flContainerId, fragment);
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
        switchFragment(position);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        switchFragment(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
