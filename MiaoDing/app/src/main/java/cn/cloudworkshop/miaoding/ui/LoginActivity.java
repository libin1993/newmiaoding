package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.umeng.analytics.MobclickAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.application.MyApplication;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.LogUtils;
import cn.cloudworkshop.miaoding.utils.PhoneNumberUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2016-10-20 11:12
 * Email：1993911441@qq.com
 * Describe：登录界面
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.img_cancel_login)
    ImageView imgCancelLogin;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.tv_verification_code)
    TextView tvVerificationCode;
    @BindView(R.id.et_user_password)
    EditText etUserPassword;
    @BindView(R.id.tv_user_agreement)
    TextView tvUserAgreement;
    @BindView(R.id.img_login)
    ImageView imgLogin;
    @BindView(R.id.img_login_bg)
    ImageView bgLogin;


    //是否输入手机号
    private boolean isPhone;
    //是否输入验证码
    private boolean isCode;
    private String logId;

    //注册协议点击事件处理
    private boolean isClickable = true;

    private MyHandler mHandler = new MyHandler(this);


    private static class MyHandler extends Handler {
        private WeakReference<LoginActivity> mActivity;

        private MyHandler(LoginActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            LoginActivity activity = mActivity.get();
            if (activity != null) {
                if (msg.what == 1) {
                    activity.tvVerificationCode.setText(activity.getString(R.string.send_again) + "(" + msg.arg1 + ")");
                    activity.tvVerificationCode.setBackgroundResource(R.drawable.bound_c7_15dp);
                } else if (msg.what == 2) {
                    activity.tvVerificationCode.setText(activity.getString(R.string.receive_code));
                    activity.tvVerificationCode.setClickable(true);
                    activity.tvVerificationCode.setBackgroundResource(R.drawable.bound_3d_15dp);
                }
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
//        loginLog();
        initView();
    }

    /**
     * 登录跟踪
     */
    private void loginLog() {
        String pageName = getIntent().getStringExtra("page_name");
        if (pageName != null) {
            OkHttpUtils.post()
                    .url(Constant.LOGIN_LOG)
                    .addParams("p_module_name", pageName)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                logId = jsonObject.getString("id");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }

    /**
     * 加载视图
     */
    private void initView() {
//        if (MyApplication.loginBg != null) {
//            Glide.with(this)
//                    .load(Constant.IMG_HOST + MyApplication.loginBg)
//                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                    .into(bgLogin);
//        }

        imgLogin.setEnabled(false);
        etUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (PhoneNumberUtils.judgePhoneNumber(etUserName.getText().toString().trim())) {
                    tvVerificationCode.setBackgroundResource(R.drawable.bound_3d_15dp);
                    isPhone = true;
                    if (isCode) {
                        imgLogin.setEnabled(true);
                    } else {
                        imgLogin.setEnabled(false);
                    }
                } else {
                    tvVerificationCode.setBackgroundResource(R.drawable.bound_c7_15dp);
                    isPhone = false;
                    imgLogin.setEnabled(false);
                }
            }
        });

        etUserPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(etUserPassword.getText().toString().trim())) {
                    isCode = false;
                    imgLogin.setEnabled(false);
                } else {
                    isCode = true;
                    if (isPhone) {
                        imgLogin.setEnabled(true);
                    } else {
                        imgLogin.setEnabled(false);
                    }
                }
            }
        });
    }


    @OnClick({R.id.img_cancel_login, R.id.tv_verification_code, R.id.tv_user_agreement, R.id.img_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_cancel_login:
                isFromCenter();
                break;
            case R.id.tv_verification_code:
                getVerificationCode();
                break;
            case R.id.img_login:
                confirmLogin();
                break;
            case R.id.tv_user_agreement:
                if (isClickable) {
                    isClickable = false;
                    getAgreement();
                }
                break;
        }
    }

    /**
     * 注册协议
     */
    private void getAgreement() {
        OkHttpUtils.post()
                .url(Constant.SIGN_AGREEMENT)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        isClickable = true;
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        isClickable = true;
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int code = jsonObject.getInt("code");
                            if (code == 10000) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                String signAgreement = data.getString("user_manual");
                                if (!TextUtils.isEmpty(signAgreement)) {
                                    Intent intent = new Intent(LoginActivity.this, UserRuleActivity.class);
                                    intent.putExtra("title", getString(R.string.user_aggrement));
                                    intent.putExtra("img_url", signAgreement);
                                    startActivity(intent);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    /**
     * 登录
     */
    private void confirmLogin() {

        Map<String, String> map = new HashMap<>();
        map.put("user_phone", etUserName.getText().toString().trim());
        map.put("sms", etUserPassword.getText().toString().trim());

        OkHttpUtils.post()
                .url(Constant.LOGIN)
                .params(map)
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
                            if (code == 10000) {

                                JSONObject data = jsonObject.getJSONObject("data");
                                String token = data.getString("token");
                                int uid = data.getInt("id");
                                String name = data.getString("username");
                                String avatar = data.getString("avatar");
                                String phone = data.getString("user_phone");

                                SharedPreferencesUtils.saveStr(LoginActivity.this,
                                        "token", token);
                                SharedPreferencesUtils.saveStr(LoginActivity.this,
                                        "uid", String.valueOf(uid));
                                SharedPreferencesUtils.saveStr(LoginActivity.this,
                                        "avatar", avatar);
                                SharedPreferencesUtils.saveStr(LoginActivity.this,
                                        "username", name);
                                SharedPreferencesUtils.saveStr(LoginActivity.this,
                                        "phone", phone);
                                MobclickAgent.onEvent(LoginActivity.this, "log_in");
                                finish();
                            }
                            ToastUtils.showToast(LoginActivity.this, jsonObject.getString("msg"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }


    /**
     * 获取验证码
     */
    private void getVerificationCode() {

        if (PhoneNumberUtils.judgePhoneNumber(etUserName.getText().toString().trim())) {
            tvVerificationCode.setClickable(false);
            sendPhoneNumber();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 30; i > 0; i--) {
                        Message msg = new Message();
                        msg.what = 1;
                        msg.arg1 = i;
                        mHandler.sendMessage(msg);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    mHandler.sendEmptyMessage(2);
                }
            }).start();
        } else {
            ToastUtils.showToast(this, getString(R.string.error_phone_number));
        }
    }


    /**
     * 验证码
     */
    private void sendPhoneNumber() {
        OkHttpUtils.post()
                .url(Constant.IDENTIFY_CODE)
                .addParams("user_phone", etUserName.getText().toString().trim())
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

                            ToastUtils.showToast(LoginActivity.this, jsonObject.getString("msg"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            isFromCenter();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }


    /**
     * 是否从个人中心跳转,取消登录跳转资讯页
     */
    private void isFromCenter() {
        String loginTag = getIntent().getStringExtra("log_in");
        if (!TextUtils.isEmpty(loginTag) && loginTag.equals("center")) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("page", 0);
            startActivity(intent);
        }
        finish();
    }
}
