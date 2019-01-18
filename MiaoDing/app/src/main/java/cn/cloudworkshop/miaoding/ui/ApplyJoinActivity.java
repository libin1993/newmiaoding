package cn.cloudworkshop.miaoding.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.umeng.analytics.MobclickAgent;
import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.indicators.BallSpinFadeLoaderIndicator;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.ResponseBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.ImageEncodeUtils;
import cn.cloudworkshop.miaoding.utils.LogUtils;
import cn.cloudworkshop.miaoding.utils.PhoneNumberUtils;
import cn.cloudworkshop.miaoding.utils.PermissionUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import me.iwf.photopicker.utils.AndroidLifecycleUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Libin on 2016/10/24.
 * 入驻申请
 */

public class ApplyJoinActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.et_apply_name)
    EditText etApplyName;
    @BindView(R.id.et_apply_phone)
    EditText etApplyPhone;
    @BindView(R.id.et_apply_qq)
    EditText etApplyQq;
    @BindView(R.id.et_apply_email)
    EditText etApplyEmail;
    @BindView(R.id.img_apply_works)
    ImageView imgApplyWorks;
    @BindView(R.id.rv_apply_works)
    RecyclerView rvApplyWorks;
    @BindView(R.id.img_apply_company)
    ImageView imgApplyCompany;
    @BindView(R.id.rv_apply_company)
    RecyclerView rvApplyCompany;
    @BindView(R.id.tv_submit_apply)
    TextView tvSubmitApply;
    @BindView(R.id.view_loading)
    AVLoadingIndicatorView loadingView;

    private CommonAdapter photoAdapter1;
    private CommonAdapter photoAdapter2;
    private ArrayList<String> selectedPhotos1 = new ArrayList<>();
    private ArrayList<String> selectedPhotos2 = new ArrayList<>();
    //当前选中
    private int currentItem = 0;

    private String imgList1;
    private String imgList2;

    //相机权限
    static final String[] permissionStr = {Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_join);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ButterKnife.bind(this);

        tvHeaderTitle.setText(R.string.apply_join);
    }



    private CommonAdapter<String> initPhotos(RecyclerView recyclerView, final ArrayList<String>
            selectedPhotos, final int index) {
        recyclerView.setLayoutManager(new LinearLayoutManager(ApplyJoinActivity.this,
                LinearLayoutManager.HORIZONTAL, false));
        CommonAdapter<String> adapter = new CommonAdapter<String>(ApplyJoinActivity.this,
                R.layout.listitem_picker_photo, selectedPhotos) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                Uri uri = Uri.fromFile(new File(s));
                boolean canLoadImage = AndroidLifecycleUtils.canLoadImage(ApplyJoinActivity.this);
                if (canLoadImage) {
                    Glide.with(mContext)
                            .load(uri)
                            .centerCrop()
                            .placeholder(me.iwf.photopicker.R.drawable.__picker_ic_photo_black_48dp)
                            .error(me.iwf.photopicker.R.drawable.__picker_ic_broken_image_black_48dp)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into((ImageView) holder.getView(R.id.img_picker_photo));
                }
            }
        };

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                PhotoPreview.builder()
                        .setPhotos(selectedPhotos)
                        .setCurrentItem(position)
                        .start(ApplyJoinActivity.this);
                currentItem = index;
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        return adapter;
    }

    @OnClick({R.id.img_header_back, R.id.img_apply_works, R.id.img_apply_company, R.id.tv_submit_apply})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                finish();
                break;
            case R.id.img_apply_works:
                if (!EasyPermissions.hasPermissions(this, permissionStr)) {
                    EasyPermissions.requestPermissions(this, "", 123, permissionStr);
                } else {
                    currentItem = 1;
                    photoAdapter1 = initPhotos(rvApplyWorks, selectedPhotos1, 1);
                    PhotoPicker.builder()
                            .setPhotoCount(4)
                            .setShowCamera(true)
                            .setSelected(selectedPhotos1)
                            .start(this);
                }
                break;
            case R.id.img_apply_company:
                if (!EasyPermissions.hasPermissions(this, permissionStr)) {
                    EasyPermissions.requestPermissions(this, "", 123, permissionStr);
                } else {
                    currentItem = 2;
                    photoAdapter2 = initPhotos(rvApplyCompany, selectedPhotos2, 2);
                    PhotoPicker.builder()
                            .setPhotoCount(2)
                            .setShowCamera(true)
                            .setSelected(selectedPhotos2)
                            .start(this);
                }

                break;
            case R.id.tv_submit_apply:
                submitApply();
                break;
        }
    }

    /**
     * 提交申请
     */
    private void submitApply() {
        if (TextUtils.isEmpty(etApplyName.getText().toString().trim())
                || TextUtils.isEmpty(etApplyQq.getText().toString().trim())
                || TextUtils.isEmpty(etApplyEmail.getText().toString().trim())
                || !PhoneNumberUtils.judgePhoneNumber(etApplyPhone.getText().toString().trim())
                || selectedPhotos1.size() == 0
                || selectedPhotos2.size() == 0) {
            ToastUtils.showToast(this, getString(R.string.please_input_personal_information));
        } else {
            loadingView.smoothToShow();
            tvSubmitApply.setEnabled(false);
            imgList1 = null;
            imgList2 = null;
            uploadImg(1, selectedPhotos1);
            uploadImg(2, selectedPhotos2);
        }
    }


    /**
     * @param
     */
    private void uploadImg(final int type, List<String> photos) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (int i = 0; i < photos.size(); i++) {
            File file = new File(photos.get(i));
            builder.addFormDataPart("file[]", file.getName(),
                    RequestBody.create(MediaType.parse("image/png"), file));
        }
        MultipartBody requestBody = builder.build();
        //构建请求
        Request request = new Request.Builder()
                .url(Constant.UPLOAD_FILE)
                .post(requestBody)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, final Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    int code = jsonObject.getInt("code");
                    if (code == 10000) {
                        String imgUrl = jsonObject.getString("info");
                        if (type == 1) {
                            imgList1 = imgUrl;
                        } else {
                            imgList2 = imgUrl;
                        }

                        if (imgList1 != null && imgList2 != null) {
                            apply();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 申请
     */
    private void apply() {
        OkHttpUtils.post()
                .url(Constant.APPLY_JOIN)
                .addParams("token", SharedPreferencesUtils.getStr(ApplyJoinActivity.this, "token"))
                .addParams("name", etApplyName.getText().toString().trim())
                .addParams("phone", etApplyPhone.getText().toString().trim())
                .addParams("weixin", etApplyQq.getText().toString().trim())
                .addParams("email", etApplyEmail.getText().toString().trim())
                .addParams("img_list1", imgList1)
                .addParams("img_list2", imgList2)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        loadingView.smoothToHide();
                        tvSubmitApply.setEnabled(true);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        loadingView.smoothToHide();
                        tvSubmitApply.setEnabled(true);
                        ResponseBean responseBean = GsonUtils.jsonToBean(response,ResponseBean.class);
                        if (responseBean.getCode() == 10000){
                            //监听申请入驻事件
                            MobclickAgent.onEvent(ApplyJoinActivity.this, "apply_join");

                            Intent intent = new Intent(ApplyJoinActivity.this, AppointmentActivity.class);
                            intent.putExtra("type", "apply_join");
                            startActivity(intent);
                            finish();
                        }
                        ToastUtils.showToast(ApplyJoinActivity.this,responseBean.getMsg());
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {
            if (currentItem == 1) {
                List<String> photos1 = null;
                if (data != null) {
                    photos1 = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                }

                selectedPhotos1.clear();
                if (photos1 != null) {
                    selectedPhotos1.addAll(photos1);
                    photoAdapter1.notifyDataSetChanged();
                }

            } else if (currentItem == 2) {
                List<String> photos2 = null;
                if (data != null) {
                    photos2 = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                }

                selectedPhotos2.clear();
                if (photos2 != null) {
                    selectedPhotos2.addAll(photos2);
                    photoAdapter2.notifyDataSetChanged();
                }
            }
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

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return false;
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        PermissionUtils.showPermissionDialog(this, getString(R.string.camera));
    }
}


