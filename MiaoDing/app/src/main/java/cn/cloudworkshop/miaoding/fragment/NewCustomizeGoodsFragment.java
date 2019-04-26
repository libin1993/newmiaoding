package cn.cloudworkshop.miaoding.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseFragment;
import cn.cloudworkshop.miaoding.bean.GoodsTitleBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.ui.ScanCodeActivity;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GoodsTabUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.PermissionUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import me.iwf.photopicker.PhotoPicker;
import okhttp3.Call;
import pub.devrel.easypermissions.EasyPermissions;

import static android.support.design.widget.TabLayout.MODE_SCROLLABLE;

/**
 * Author：Libin on 2019/4/25 15:47
 * Email：1993911441@qq.com
 * Describe：
 */
public class NewCustomizeGoodsFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.iv_qrcode)
    ImageView ivQrcode;
    @BindView(R.id.fl_goods)
    FrameLayout flGoods;
    @BindView(R.id.img_load_error)
    ImageView ivLoadError;
    @BindView(R.id.view_loading)
    AVLoadingIndicatorView viewLoading;
    @BindView(R.id.tab_customize_goods)
    TabLayout tabGoods;

    private Unbinder unbinder;

    private GoodsTitleBean titleBean;

    static final String[] permissionStr = new String[]{Manifest.permission.CAMERA};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customized_goods, container, false);
        unbinder = ButterKnife.bind(this, view);
        viewLoading.smoothToShow();
        initTitle();
        return view;
    }


    /**
     * 商品分类
     */
    private void initTitle() {
        OkHttpUtils.post()
                .url(Constant.GOODS_TITLE)
                .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ivLoadError.setVisibility(View.VISIBLE);
                        viewLoading.smoothToHide();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ivLoadError.setVisibility(View.GONE);
                        viewLoading.smoothToHide();
                        titleBean = GsonUtils.jsonToBean(response, GoodsTitleBean.class);
                        if (titleBean.getCode() == 10000 && titleBean.getData() != null && titleBean.getData().size() > 0) {
                            initView();
                        }
                    }
                });
    }

    /**
     * 加载视图
     */
    private void initView() {
        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < titleBean.getData().size(); i++) {
            fragmentList.add(ShirtFragment.newInstance(titleBean.getData().get(i).getId(),
                    titleBean.getData().get(i).getType()));

            tabGoods.addTab(tabGoods.newTab().setText(titleBean.getData().get(i).getName()));

        }


        //tab个数低于5个平分，不可滑动
        if (tabGoods.getTabCount() <= 2) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);// 创建参数对
            layoutParams.setMargins((int) DisplayUtils.dp2px(getActivity(), 70), 0,
                    (int) DisplayUtils.dp2px(getActivity(), 70), 0);
            tabGoods.setLayoutParams(layoutParams);

            tabGoods.setTabMode(TabLayout.MODE_FIXED);
        } else {
            ViewGroup.LayoutParams layoutParams = tabGoods.getLayoutParams();
            layoutParams.width = (int) (DisplayUtils.getMetrics(getActivity()).widthPixels -
                    DisplayUtils.dp2px(getActivity(), 55));
            tabGoods.setLayoutParams(layoutParams);
            tabGoods.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
        setTabWidth(tabGoods);

        new GoodsTabUtils(getActivity(), getChildFragmentManager(), fragmentList, R.id.fl_goods, tabGoods);


    }


    /**
     * 设置下划线宽度
     */
    public void setTabWidth(final TabLayout tabLayout) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    Field mTabStripField = tabLayout.getClass().getDeclaredField("mTabStrip");
                    mTabStripField.setAccessible(true);

                    LinearLayout mTabStrip = (LinearLayout) mTabStripField.get(tabLayout);


                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = 30;
                        params.rightMargin = 30;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    /**
     * 扫码，检测权限
     */
    private void scanCode() {

        if (!EasyPermissions.hasPermissions(getActivity(), permissionStr)) {
            EasyPermissions.requestPermissions(this, "", 123, permissionStr);
        } else {
            Intent intent = new Intent(getActivity(), ScanCodeActivity.class);
            startActivity(intent);
        }

    }


    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog.
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    public static NewCustomizeGoodsFragment newInstance() {

        Bundle args = new Bundle();

        NewCustomizeGoodsFragment fragment = new NewCustomizeGoodsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick({R.id.iv_qrcode, R.id.view_loading})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_qrcode:
                scanCode();
                break;
            case R.id.view_loading:
                initTitle();
                break;
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Intent intent = new Intent(getActivity(), ScanCodeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        PermissionUtils.showPermissionDialog(getActivity(), getString(R.string.camera));
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return false;
    }
}
