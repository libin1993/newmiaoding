package cn.cloudworkshop.miaoding.utils;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import java.util.List;

import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.application.MyApplication;

/**
 * 主界面 底部切换tab工具类（加载网络图片）
 */
public class FragmentTabUtils implements RadioGroup.OnCheckedChangeListener {
    private List<Fragment> fragmentList; // 一个tab页面对应一个Fragment
    private RadioGroup rgs; // 用于切换tab
    private FragmentManager fragmentManager; // Fragment所属的Activity
    private int flContainerId; // Activity中当前fragment的区域的id
    private Context mContext;

    /**
     * @param fragmentManager
     * @param fragmentList
     * @param flContainerId
     *
     *
     * @param rgs
     */
    public FragmentTabUtils(Context context, FragmentManager fragmentManager, List<Fragment> fragmentList,
                            int flContainerId, RadioGroup rgs) {
        this.mContext = context;
        this.fragmentList = fragmentList;
        this.rgs = rgs;
        this.fragmentManager = fragmentManager;
        this.flContainerId = flContainerId;

        rgs.setOnCheckedChangeListener(this);

        ((RadioButton) rgs.getChildAt(0)).setChecked(true);

    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        for (int i = 0; i < rgs.getChildCount(); i++) {
            RadioButton rBtn = ((RadioButton) rgs.getChildAt(i));
            if (rBtn.getId() == checkedId) {
                switchFragment(i);
            }
        }
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
        ((RadioButton) rgs.getChildAt(position)).setChecked(true);
    }

}