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
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.flyco.tablayout.SlidingTabLayout;
import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.adapter.GoodsFragmentAdapter;
import cn.cloudworkshop.miaoding.base.BaseFragment;
import cn.cloudworkshop.miaoding.bean.GoodsTitleBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.ui.ScanCodeActivity;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.PermissionUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import okhttp3.Call;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Author：Libin on 2017/9/25 18:57
 * Email：1993911441@qq.com
 * Describe：定制商品（当前版本）
 */
public class CustomizedGoodsFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks{
    @BindView(R.id.tab_goods)
    SlidingTabLayout tabGoods;
    @BindView(R.id.img_goods_code)
    ImageView imgGoods;
    @BindView(R.id.vp_goods)
    ViewPager vpGoods;
    @BindView(R.id.img_load_error)
    ImageView imgLoadingError;
    @BindView(R.id.view_loading)
    AVLoadingIndicatorView viewLoading;
    private Unbinder unbinder;

    private GoodsTitleBean titleBean;

    static final String[] permissionStr = new String[]{Manifest.permission.CAMERA};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods, container, false);
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
                        imgLoadingError.setVisibility(View.VISIBLE);
                        viewLoading.smoothToHide();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        imgLoadingError.setVisibility(View.GONE);
                        viewLoading.smoothToHide();
                        titleBean = GsonUtils.jsonToBean(response, GoodsTitleBean.class);
                        if (titleBean.getData() != null && titleBean.getData().size() > 0) {
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
        List<String> titleList = new ArrayList<>();
        for (int i = 0; i < titleBean.getData().size(); i++) {
//            fragmentList.add(SubGoodsFragment.newInstance(titleBean.getData().get(i).getId()));
            titleList.add(titleBean.getData().get(i).getName());
        }

        //tab个数低于5个平分，不可滑动
        if (titleList.size() <= 2) {
            tabGoods.setTabSpaceEqual(true);
            tabGoods.setPadding((int) DisplayUtils.dp2px(getActivity(), 70), 0,
                    (int) DisplayUtils.dp2px(getActivity(), 70), 0);
        } else {
            ViewGroup.LayoutParams layoutParams = tabGoods.getLayoutParams();
            layoutParams.width = (int) (DisplayUtils.getMetrics(getActivity()).widthPixels -
                    DisplayUtils.dp2px(getActivity(), 55));
            tabGoods.setLayoutParams(layoutParams);

            tabGoods.setTabSpaceEqual(false);

        }


        GoodsFragmentAdapter adapter = new GoodsFragmentAdapter(getChildFragmentManager(),
                fragmentList, titleList);
        vpGoods.setOffscreenPageLimit(titleList.size());
        vpGoods.setAdapter(adapter);
        tabGoods.setViewPager(vpGoods);
        tabGoods.setCurrentTab(0);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public static CustomizedGoodsFragment newInstance() {

        Bundle args = new Bundle();

        CustomizedGoodsFragment fragment = new CustomizedGoodsFragment();
        fragment.setArguments(args);
        return fragment;
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



    @OnClick({R.id.img_goods_code, R.id.img_load_error})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_goods_code:
                scanCode();
                break;
            case R.id.img_load_error:
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




