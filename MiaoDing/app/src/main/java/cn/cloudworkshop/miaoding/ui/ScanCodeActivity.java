package cn.cloudworkshop.miaoding.ui;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yanzhenjie.zbar.camera.CameraPreview;
import com.yanzhenjie.zbar.camera.ScanCallback;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.ScanBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2017-06-15 08:48
 * Email：1993911441@qq.com
 * Describe：扫描二维码
 */
public class ScanCodeActivity extends BaseActivity {
    @BindView(R.id.capture_preview)
    CameraPreview capturePreview;
    @BindView(R.id.capture_crop_view)
    RelativeLayout captureCropView;
    @BindView(R.id.capture_scan_line)
    ImageView captureScanLine;
    @BindView(R.id.img_scan_back)
    ImageView imgScanBack;


    private ValueAnimator mScanAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        capturePreview.setScanCallback(new ScanCallback() {
            @Override
            public void onScanResult(String content) {
                if (content != null) {
                    vibrator();

                    Uri uri = Uri.parse(content);
                    String goodsId = uri.getQueryParameter("goods_id");
                    if (!TextUtils.isEmpty(goodsId)) {
                        getGoodsInfo(goodsId);
                    } else {
                        scanFail();
                    }

                } else {
                    scanFail();
                }
            }
        });
    }

    /**
     * @param goodsId 解析加密数据，获取id
     */
    private void getGoodsInfo(String goodsId) {
        OkHttpUtils.get()
                .url(Constant.GOODS_DECODE)
                .addParams("goods_id", goodsId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        scanFail();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ScanBean scanBean = GsonUtils.jsonToBean(response, ScanBean.class);
                        if (scanBean.getCode() == 10000) {
                            switch (scanBean.getData().getCategory_id()) {
                                case 1:
                                    toGoodsDetail(NewCustomizedGoodsActivity.class, String.valueOf(scanBean.getData().getGoods_id()));
                                    break;
                                case 2:
                                    toGoodsDetail(WorksDetailActivity.class, String.valueOf(scanBean.getData().getGoods_id()));
                                    break;
                                default:
                                    scanFail();
                                    break;
                            }
                        } else {
                            scanFail();
                        }
                    }
                });

    }

    /**
     * 二维码扫描结果非本平台商品
     */
    private void scanFail() {
        ToastUtils.showToast(this, getString(R.string.support_platform));
        finish();
    }

    /**
     * @param cls
     * @param goodsId 跳转商品详情
     */
    private void toGoodsDetail(Class<? extends Activity> cls, String goodsId) {
        Intent intent = new Intent(ScanCodeActivity.this, cls);
        intent.putExtra("id", goodsId);
        startActivity(intent);
        finish();
    }


    /**
     * 振动
     */
    private void vibrator() {
        Vibrator vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        if (vib != null) {
            vib.vibrate(100);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mScanAnimator != null) {
            if (capturePreview.start()) {
                mScanAnimator.start();
            }
        }
    }

    @Override
    public void onPause() {
        // Must be called here, otherwise the camera should not be released properly.
        stopScan();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mScanAnimator.cancel();
        capturePreview.stop();
    }

    /**
     * Stop scan.
     */
    private void stopScan() {
        mScanAnimator.cancel();
        capturePreview.stop();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (mScanAnimator == null) {
            int height = captureCropView.getMeasuredHeight() - 25;
            mScanAnimator = ObjectAnimator.ofFloat(captureScanLine, "translationY", 0F, height).setDuration(3000);
            mScanAnimator.setInterpolator(new LinearInterpolator());
            mScanAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mScanAnimator.setRepeatMode(ValueAnimator.REVERSE);

            if (capturePreview.start()) {
                mScanAnimator.start();
            }
        }
    }


    @OnClick(R.id.img_scan_back)
    public void onViewClicked() {
        finish();
    }
}

