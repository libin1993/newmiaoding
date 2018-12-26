package cn.cloudworkshop.miaoding.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.indicators.BallSpinFadeLoaderIndicator;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.HOGDescriptor;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.camera.CustomCameraView;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.CameraDistance;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.LogUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import cn.cloudworkshop.miaoding.view.SensorView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Author：Libin on 2018/4/3 19:00
 * Email：1993911441@qq.com
 * Describe：
 */
public class NewCameraActivity extends BaseActivity implements SensorEventListener {
    @BindView(R.id.camera_view)
    CustomCameraView cameraView;
    @BindView(R.id.sensor_view)
    SensorView sensorView;
    @BindView(R.id.img_man_position)
    ImageView imgPosition;
    @BindView(R.id.img_take_picture)
    ImageView imgTakePhoto;
    @BindView(R.id.img_take_back)
    ImageView imgTakeBack;
    @BindView(R.id.view_loading)
    AVLoadingIndicatorView loadingView;
    @BindView(R.id.view_user_stroke)
    View viewStroke;
    @BindView(R.id.img_take_again)
    ImageView imgTakeAgain;
    @BindView(R.id.img_take_success)
    ImageView imgTakeSuccess;
    private final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CloudWorkshop/";
    //照片保存路径
    private String[] photoArray = {path + "camera0.jpg", path + "camera1.jpg", path + "camera2.jpg",
            path + "camera3.jpg"};
    //背景图片
    private int[] positionArray = {R.mipmap.camera_positive, R.mipmap.camera_left,
            R.mipmap.camera_back, R.mipmap.camera_right};
    //拍照次数
    private int count = 0;
    //传感器
    private SensorManager mSensorManager;
    //身高
    private String height;
    //体重
    private String weight;
    //姓名
    private String name;
    //是否默认量体数据
    private int isDefault;
    //店铺
    private String store;
    //胸围
    private String bust;
    //腰围
    private String waist;
    //臀围
    private String hip;
    private Thread myThread;

    private boolean mInitialized = false;
    private float mLastX = 0;
    private float mLastY = 0;
    private float mLastZ = 0;
    //屏幕宽
    int screenWidth;
    //屏幕高
    int screenHeight;
    //背景图高度
    int imgHeight;
    //防止连续点击
    private boolean isClickable = true;

    private double h;
    private double d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //透明导航栏
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_camera_new);
        ButterKnife.bind(this);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        getData();
        initStroke();
    }


    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            name = bundle.getString("name");
            height = bundle.getString("height");
            weight = bundle.getString("weight");
            isDefault = bundle.getInt("is_default");
            store = bundle.getString("store");
            bust = bundle.getString("bust");
            waist = bundle.getString("waist");
            hip = bundle.getString("hip");
        }

    }

    /**
     * 根据输入的身高，拍照距离3米左右，设置边框的尺寸大小
     */
    private void initStroke() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(viewStroke.getLayoutParams());
        //屏幕宽高
        screenWidth = DisplayUtils.getMetrics(this).widthPixels;
        screenHeight = DisplayUtils.getMetrics(this).heightPixels;
        //边框距离底部距离
        int marginBottom;
        //背景图等比例缩放
        //当屏幕宽高比大于背景图宽高比时，背景图高度铺满屏幕高
        //当屏幕宽高比小于背景图宽高比时，背景图宽度铺满屏幕宽
        if ((float) screenWidth / screenHeight > (float) 750 / (float) 1334) {
            imgHeight = screenHeight;
            marginBottom = imgHeight * 19 / 67;
        } else {
            imgHeight = screenWidth / 750 * 1334;
            marginBottom = (screenHeight - imgHeight) / 2 + imgHeight * 19 / 67;
        }
        layoutParams.width = (int) DisplayUtils.dp2px(this, 200);
        layoutParams.height = (int) DisplayUtils.dp2px(this, (float) (Float.parseFloat(height) * 2.2));
        layoutParams.bottomMargin = marginBottom;
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        viewStroke.setLayoutParams(layoutParams);
    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                    break;
                default:
                    super.onManagerConnected(status);
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        // 为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(
                Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);

        //初始化OpenCV
        if (!OpenCVLoader.initDebug()) {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0, this, mLoaderCallback);
        } else {
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
        //初始化OpenCV
        cameraView.setOnTakeFinish(new CustomCameraView.OnTakeFinish() {
            @Override
            public void takeFinish(Bitmap bitmap) {
                loadingView.smoothToShow();
                if (count == 0) {
                    distance(bitmap);
                } else {
                    takeSuccess();
                }
            }
        });

    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what) {
                case -1:
                    takeFail(getString(R.string.not_test_person));
                    break;
                case 0:
                    takeFail(getString(R.string.too_near));
                    break;
                case 1:
                    takeSuccess();
                    break;
                case 2:
                    takeFail(getString(R.string.too_far));
                    break;
            }
//            ToastUtils.showToast(NewCameraActivity.this, h + "," + d);
            return false;
        }
    });


    /**
     * 拍摄失败
     */
    private void takeFail(String msg) {
        loadingView.smoothToHide();
        ToastUtils.showToast(NewCameraActivity.this, msg);
        imgTakePhoto.setVisibility(View.VISIBLE);
    }

    /**
     * 拍摄成功
     */
    private void takeSuccess() {
        loadingView.smoothToHide();

        ToastUtils.showToast(NewCameraActivity.this, getString(R.string.take_success));
        imgTakeAgain.setVisibility(View.VISIBLE);
        imgTakeSuccess.setVisibility(View.VISIBLE);
        imgTakePhoto.setVisibility(View.GONE);
        if (count == 3) {
            imgTakeSuccess.setImageResource(R.mipmap.camera_take_finish);
        }
    }

    /**
     * 测距，  检测人物轮廓像素高度与输入身高成正比，与图片高度成正比，与拍摄距离成反比
     * 通过固定身高，固定图片高度，3米拍摄距离，求出比例系数
     * distance = K * person_height * img_height / H
     */
    private void distance(final Bitmap bitmap) {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                Mat mat = Imgcodecs.imread(photoArray[1]);
                Mat mat = new Mat();
                Utils.bitmapToMat(bitmap, mat);
                bitmap.recycle();
                //压缩图片
//                Mat detMat = new Mat();
//                Imgproc.resize(mat, detMat, new Size(mat.width() / 2, mat.height() / 2));
                //建立灰度图像存储空间
                Mat gray = new Mat(mat.size(), CvType.CV_8U);
                //彩色图像灰度化
                Imgproc.cvtColor(mat, gray, Imgproc.COLOR_BGR2GRAY);
                //人体轮廓检测
                HOGDescriptor hogDescriptor = new HOGDescriptor();
                hogDescriptor.setSVMDetector(HOGDescriptor.getDefaultPeopleDetector());
                MatOfRect matOfRect = new MatOfRect();
                MatOfDouble matOfDouble = new MatOfDouble();

                hogDescriptor.detectMultiScale(gray, matOfRect, matOfDouble, 0, new Size(
                                4, 4), new Size(8, 8), 1.2,
                        2, false);
                int maxHeight = 0;
                if (matOfRect.toArray().length > 0) { // 判断是否检测到目标对象，如果有就画矩形，没有就执行下一步
                    for (Rect r : matOfRect.toArray()) { // 检测到的目标转成数组形式，方便遍历
                        r.x += Math.round(r.width * 0.1);
                        r.width = (int) Math.round(r.width * 0.8);
                        r.y += Math.round(r.height * 0.045);
                        r.height = (int) Math.round(r.height * 0.85);
//                        Imgproc.rectangle(gray, r.tl(), r.br(), new Scalar(0, 0, 255), 2); // 画出矩形
                        if (maxHeight <= r.height) {
                            maxHeight = r.height;
                        }
                    }
                }

//                Imgcodecs.imwrite(Environment.getExternalStorageDirectory().getAbsolutePath()
//                        + "/CloudWorkshop/people" + System.currentTimeMillis() + ".jpg", gray); // 将已经完成检测的Mat对象写出，参数：输出路径，检测完毕的Mat对象。
                double distance = Float.parseFloat(height) * 0.8973 * gray.height() / maxHeight;
                //拍摄距离在2.7米和4米之间
                h = maxHeight;
                d = distance;

                if (maxHeight < 400) {
                    handler.sendEmptyMessage(-1);
                } else {
                    if (distance < 270) {
                        handler.sendEmptyMessage(0);
                    } else if (distance <= 400) {
                        handler.sendEmptyMessage(1);
                    } else {
                        handler.sendEmptyMessage(2);
                    }
                }
            }
        }).start();

    }


    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(this);
        finish();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mSensorManager.unregisterListener(this);
        super.onDestroy();
    }

    @OnClick({R.id.img_take_back, R.id.img_take_picture, R.id.img_take_again, R.id.img_take_success})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_take_success:
                if (count < 3) {
                    count++;
                    imgPosition.setImageResource(positionArray[count]);
                    imgTakeAgain.setVisibility(View.GONE);
                    imgTakeSuccess.setVisibility(View.GONE);
                    imgTakePhoto.setVisibility(View.VISIBLE);
                } else {
                    imgTakePhoto.setVisibility(View.GONE);
                    if (isClickable) {
                        isClickable = false;
                        submitData();
                    }
                }
                break;
            case R.id.img_take_picture:
                imgTakePhoto.setVisibility(View.GONE);
                cameraView.takePicture(photoArray[count]);
                break;
            case R.id.img_take_again:
                imgTakeAgain.setVisibility(View.GONE);
                imgTakeSuccess.setVisibility(View.GONE);
                imgTakePhoto.setVisibility(View.VISIBLE);
                break;
            case R.id.img_take_back:
                finish();
                break;

        }
    }

    /**
     * 上传照片
     */
    private void submitData() {
        loadingView.smoothToShow();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        for (int i = 0; i < photoArray.length; i++) {
            File file = new File(photoArray[i]);
            builder.addFormDataPart("img" + i, file.getName(),
                    RequestBody.create(MediaType.parse("image/png"), file));
        }

        builder.addFormDataPart("token", SharedPreferencesUtils.getStr(this, "token"));
        builder.addFormDataPart("phone", SharedPreferencesUtils.getStr(this, "phone"));
        builder.addFormDataPart("name", name);
        builder.addFormDataPart("height", height);
        builder.addFormDataPart("weight", weight);
        builder.addFormDataPart("is_index", String.valueOf(isDefault));
        builder.addFormDataPart("scale", "1,1,1,1");
        if (!TextUtils.isEmpty(store)) {
            builder.addFormDataPart("factory_id", store);
        }
        if (!TextUtils.isEmpty(bust)) {
            builder.addFormDataPart("xw", bust);
        }
        if (!TextUtils.isEmpty(waist)) {
            builder.addFormDataPart("yw", waist);
        }
        if (!TextUtils.isEmpty(hip)) {
            builder.addFormDataPart("tw", hip);
        }

        MultipartBody requestBody = builder.build();
        //构建请求
        Request request = new Request.Builder()
                .url(Constant.TAKE_PHOTO)
                .post(requestBody)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                isClickable = true;
            }

            @Override
            public void onResponse(Call call, final Response response) {
                isClickable = true;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadingView.smoothToHide();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            int code = jsonObject.getInt("code");
                            String msg = jsonObject.getString("msg");
                            ToastUtils.showToast(NewCameraActivity.this, msg);
                            if (code == 1) {
                                EventBus.getDefault().post("take_success");
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        // 获取手机触发event的传感器的类型
        int sensorType = event.sensor.getType();

        if (sensorType == Sensor.TYPE_ORIENTATION) {
            //绕Z轴旋转
            float zAngle = values[0];
            //绕X轴旋转
            //-180 -- 0，前0，后-180
            float verAngle = values[1];
            //绕Y轴旋转
            //-90 --  90，  左90，右-90
            float horAngle = values[2];

            //手机俯仰
            sensorView.verX = 15;
            if (verAngle >= -180 && verAngle <= 0) {
                sensorView.verY = screenHeight * Math.abs(verAngle) / 180 -
                        sensorView.verBubble.getHeight() / 2;
            }

            //左右旋转
            if (horAngle >= -90 && horAngle <= 90) {
                sensorView.horX = screenWidth / 2 - screenWidth / 2 * horAngle / 90 -
                        sensorView.horBubble.getWidth() / 2;
            }

            sensorView.horY = (float) (screenHeight / 2 + imgHeight / 3 - sensorView.horBubble.getHeight() / 2);
            sensorView.invalidate();


            //偏移角度
            if (verAngle > -100 && verAngle < -80 && horAngle > -10 && horAngle < 10) {
                imgTakePhoto.setEnabled(true);
            } else {
                imgTakePhoto.setEnabled(false);
            }

            //聚焦
            if (!mInitialized) {
                mLastX = zAngle;
                mLastY = verAngle;
                mLastZ = horAngle;
                mInitialized = true;
            }

            float deltaX = Math.abs(mLastX - zAngle);
            float deltaY = Math.abs(mLastY - verAngle);
            float deltaZ = Math.abs(mLastZ - horAngle);

            //手机偏移2°，重新聚焦
            if (deltaX > 2 || deltaY > 2 || deltaZ > 2) {
                if (CustomCameraView.camera != null) {
                    if (myThread != null) {
                        myThread.interrupt();
                    }
                    myThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            cameraView.setFocus();
                        }
                    });
                    myThread.start();
                }
            }

            mLastX = zAngle;
            mLastY = verAngle;
            mLastZ = horAngle;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {


    }


}







