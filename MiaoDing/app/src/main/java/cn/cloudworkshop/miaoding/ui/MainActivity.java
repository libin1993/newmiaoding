package cn.cloudworkshop.miaoding.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.application.MyApplication;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.AppIndexBean;
import cn.cloudworkshop.miaoding.bean.GuideBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.fragment.AccentFragment;
import cn.cloudworkshop.miaoding.fragment.CustomizedGoodsFragment;
import cn.cloudworkshop.miaoding.fragment.HomepageFragment;
import cn.cloudworkshop.miaoding.fragment.MyCenterFragment;
import cn.cloudworkshop.miaoding.fragment.NewCustomizeGoodsFragment;
import cn.cloudworkshop.miaoding.service.DownloadService;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.FragmentTabUtils;
import cn.cloudworkshop.miaoding.utils.LogUtils;
import cn.cloudworkshop.miaoding.utils.PermissionUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import okhttp3.Call;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Author：Libin on 2017-06-21 10:35
 * Email：1993911441@qq.com
 * Describe：首页
 */
public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.img_load_error)
    ImageView imgLoadingError;
    @BindView(R.id.rgs_main_tab)
    RadioGroup rgsMainTab;

    //fragment
    private List<Fragment> fragmentList = new ArrayList<>();
    private FragmentTabUtils fragmentUtils;
    //下载服务
    private DownloadService downloadService;
    //退出应用
    private long exitTime = 0;
    //是否检测更新
    private boolean isCheckUpdate = true;
    //读写权限
    String[] permissionStr = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        storagePermission();
        initView();
        checkUpdate();
        isLogin();
//        submitClientId();
    }


    /**
     * 读写权限
     */
    private void storagePermission() {
        if (!EasyPermissions.hasPermissions(this, permissionStr)) {
            EasyPermissions.requestPermissions(this, "", 1234, permissionStr);
        }
    }

//    /**
//     * 加载底部栏icon
//     */
//    private void initIcon() {
//        OkHttpUtils.get()
//                .url(Constant.APP_ICON)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        imgLoadingError.setVisibility(View.VISIBLE);
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        imgLoadingError.setVisibility(View.GONE);
//                        iconBean = GsonUtils.jsonToBean(response, AppIconBean.class);
//                        if (iconBean.getTab() != null && iconBean.getTab().size() > 0) {
//                            initView();
//                        }
//                    }
//                });
//    }


    /**
     * 个推推送，提交设备id
     */
    private void submitClientId() {
        String clientId = SharedPreferencesUtils.getStr(this, "client_id");
        if (clientId != null) {
            Map<String, String> map = new HashMap<>();
            map.put("device_id", clientId);

            String token = SharedPreferencesUtils.getStr(this, "token");
            if (!TextUtils.isEmpty(token)) {
                map.put("token", token);
            }

            OkHttpUtils.post()
                    .url(Constant.CLIENT_ID)
                    .params(map)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {

                        }
                    });
        }
    }


    /**
     * 检测是否登录
     */
    private void isLogin() {
        String token = SharedPreferencesUtils.getStr(this, "token");
        if (!TextUtils.isEmpty(token)) {
            OkHttpUtils.get()
                    .url(Constant.CHECK_LOGIN)
                    .addParams("token", token)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            if (response != null) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    int code = jsonObject.getInt("code");
                                    //token失效
                                    if (code == 10001) {
                                        SharedPreferencesUtils.deleteStr(MainActivity.this, "uid");
                                        SharedPreferencesUtils.deleteStr(MainActivity.this, "token");
                                        SharedPreferencesUtils.deleteStr(MainActivity.this, "username");
                                        SharedPreferencesUtils.deleteStr(MainActivity.this, "avatar");
                                        SharedPreferencesUtils.deleteStr(MainActivity.this, "phone");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
        }
    }


    /**
     * 检测更新
     */
    private void checkUpdate() {

        if (isCheckUpdate) {
            OkHttpUtils.get()
                    .url(Constant.CHECK_UPDATE)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            final AppIndexBean appIndexBean = GsonUtils.jsonToBean(response, AppIndexBean.class);
                            if (appIndexBean.getCode() == 10000 && appIndexBean.getData().getVersion_id() > getVersionCode()) {
                                MyApplication.updateUrl = appIndexBean.getData().getLink();
                                MyApplication.updateContent = appIndexBean.getData().getDescription();
                                //取消检测更新
                                isCheckUpdate = false;
                                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this,
                                        R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                                dialog.setTitle(R.string.check_new_version);
                                dialog.setMessage(appIndexBean.getData().getDescription());
                                //确定
                                dialog.setPositiveButton(R.string.update_immediately, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        downloadFile(appIndexBean.getData().getLink());
                                    }
                                });
                                //取消
                                dialog.setNegativeButton(R.string.cancel_update, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                dialog.create();
                                dialog.show();
                            }
                        }
                    });
        }

    }

    /**
     * 下载文件
     */
    private void downloadFile(String url) {
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(getString(R.string.app_name));
        request.setDescription(getString(R.string.downloading));
        // 设置下载可见
        request.setVisibleInDownloadsUi(true);
        //下载完成后通知栏可见
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //当前网络状态
        ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (mConnectivityManager != null) {
            networkInfo = mConnectivityManager.getActiveNetworkInfo();
        }
        if (networkInfo != null && networkInfo.isConnected()) {
            String type = networkInfo.getTypeName();
            if (type.equalsIgnoreCase("MOBILE")) {
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE);
            }
        }

        // 设置下载位置
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "CloudWorkshop", "miaoding.apk");
        if (file.exists()) {
            file.delete();
        }
        request.setDestinationUri(Uri.fromFile(file));

        downloadService = new DownloadService(file);
        registerReceiver(downloadService, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        if (manager != null) {
            manager.enqueue(request);
        }
    }

    /**
     * 获取版本号
     */

    private int getVersionCode() {
        PackageManager pm = this.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pi != null ? pi.versionCode : 0;
    }


    /**
     * 加载Fragment
     */
    public void initView() {
        fragmentList.add(HomepageFragment.newInstance());
        fragmentList.add(NewCustomizeGoodsFragment.newInstance());
        fragmentList.add(AccentFragment.newInstance());
        fragmentList.add(MyCenterFragment.newInstance());
        fragmentUtils = new FragmentTabUtils(this, getSupportFragmentManager(), fragmentList,
                R.id.frame_container, rgsMainTab);

    }


    /**
     * 首次进入，推荐注册
     */
    private void isFirstIn() {
        //默认首次
        boolean isFirst = SharedPreferencesUtils.getBoolean(this, "first_in", true);
        if (isFirst && TextUtils.isEmpty(SharedPreferencesUtils.getStr(this, "token"))) {
            OkHttpUtils.get()
                    .url(Constant.GUIDE_IMG)
                    .addParams("id", "5")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            GuideBean guideBean = GsonUtils.jsonToBean(response, GuideBean.class);
                            if (guideBean.getData().getImg_urls() != null && guideBean.getData()
                                    .getImg_urls().size() > 0) {

                                SharedPreferencesUtils.saveBoolean(MainActivity.this, "first_in", false);
                                View popupView = getLayoutInflater().inflate(R.layout.ppw_register, null);
                                final PopupWindow mPopupWindow = new PopupWindow(popupView,
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT);
                                mPopupWindow.setTouchable(true);
                                mPopupWindow.setFocusable(true);
                                mPopupWindow.setOutsideTouchable(true);
                                mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
                                if (!isFinishing()) {
                                    mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                                }
                                View viewRegister = popupView.findViewById(R.id.view_register);
                                View viewCancel = popupView.findViewById(R.id.view_cancel);
                                ImageView imgRegister = (ImageView) popupView.findViewById(R.id.img_register);

                                Glide.with(MainActivity.this)
                                        .load(Constant.IMG_HOST + guideBean.getData().getImg_urls().get(0).getImg())
                                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                        .into(imgRegister);
                                viewRegister.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mPopupWindow.dismiss();
                                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                    }
                                });
                                viewCancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mPopupWindow.dismiss();
                                    }
                                });
                            }
                        }
                    });
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }


    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == 1234){
            PermissionUtils.showPermissionDialog(this, getString(R.string.read_and_write));
        }
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return false;
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            isFirstIn();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtils.showToast(getApplicationContext(), getString(R.string.exit_app));
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        int currentPage = getIntent().getIntExtra("page", 0);
        if (fragmentUtils != null && !isFinishing()) {
            fragmentUtils.setCurrentFragment(currentPage);
        }
    }

    @Override
    protected void onDestroy() {
        if (downloadService != null) {
            unregisterReceiver(downloadService);
        }
        super.onDestroy();
    }

}



