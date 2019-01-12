package cn.cloudworkshop.miaoding.ui;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.CustomResultBean;
import cn.cloudworkshop.miaoding.bean.CustomizePartsBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.MyLinearLayoutManager;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2016/8/31 10:23
 * Email：1993911441@qq.com
 * Describe：订制商品结果
 */
public class CustomizeResultActivity extends BaseActivity {

    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_buy_now)
    TextView tvBuyNow;
    @BindView(R.id.rl_tailor_positive)
    RelativeLayout rlTailorPositive;
    @BindView(R.id.tv_tailor_name)
    TextView tvTailorName;
    @BindView(R.id.rv_tailor_name)
    RecyclerView rvTailorName;
    @BindView(R.id.tv_tailor_price)
    TextView tvTailorPrice;
    @BindView(R.id.tv_add_bag)
    TextView tvAddBag;
    @BindView(R.id.img_header_share)
    ImageView imgShoppingBag;
    @BindView(R.id.rl_tailor_back)
    RelativeLayout rlTailorBack;
    @BindView(R.id.rl_tailor_inside)
    RelativeLayout rlTailorInside;
    @BindView(R.id.rgs_tailor_position)
    RadioGroup rgsTailorPosition;
    @BindView(R.id.img_default_item)
    ImageView imgDefaultItem;
    @BindView(R.id.rl_tailor_position)
    RelativeLayout rlTailorPosition;
    @BindView(R.id.rl_custom_result)
    RelativeLayout rlCustomResult;

    //1:直接购买 2：加入购物袋

    private int type;

    private float x1 = 0;
    private float x2 = 0;

    private CustomizePartsBean partsBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_result);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);
        tvHeaderTitle.setText(R.string.customize_detail);
        imgShoppingBag.setVisibility(View.VISIBLE);
        imgShoppingBag.setImageResource(R.mipmap.icon_shopping_bag);
        getData();
    }

    /**
     * 商品详情
     */

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            partsBean = (CustomizePartsBean) bundle.getSerializable("customize");
            if (partsBean != null) {
                initView();
            }
        }

    }


    /**
     * 加载视图
     */
    private void initView() {

        for (int i = 0; i < partsBean.getPartsBeans().size(); i++) {
            ImageView img = new ImageView(this);
            Glide.with(getApplicationContext())
                    .load(Constant.IMG_HOST + partsBean.getPartsBeans().get(i).getImg())
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(img);
            switch (partsBean.getPartsBeans().get(i).getPositionId()) {
                case 1:
                    rlTailorPositive.addView(img);
                    break;
                case 2:
                    rgsTailorPosition.getChildAt(1).setVisibility(View.VISIBLE);
                    rlTailorBack.addView(img);
                    break;
                case 3:
                    rgsTailorPosition.getChildAt(2).setVisibility(View.VISIBLE);
                    rlTailorInside.addView(img);
                    break;
            }

        }

        //正面
        ((RadioButton) rgsTailorPosition.getChildAt(0)).setChecked(true);
        rgsTailorPosition.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                for (int m = 0; m < rgsTailorPosition.getChildCount(); m++) {
                    RadioButton rBtn = (RadioButton) radioGroup.getChildAt(m);
                    if (rBtn.getId() == i) {
                        switch (m) {
                            case 0:
                                rlTailorPositive.setVisibility(View.VISIBLE);
                                rlTailorBack.setVisibility(View.GONE);
                                rlTailorInside.setVisibility(View.GONE);
                                break;
                            case 1:
                                rlTailorBack.setVisibility(View.VISIBLE);
                                rlTailorPositive.setVisibility(View.GONE);
                                rlTailorInside.setVisibility(View.GONE);
                                break;
                            case 2:
                                rlTailorInside.setVisibility(View.VISIBLE);
                                rlTailorPositive.setVisibility(View.GONE);
                                rlTailorBack.setVisibility(View.GONE);
                                break;
                        }
                    }
                }
            }
        });
        //正面，滑动监听
        rlTailorPositive.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = motionEvent.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x2 = motionEvent.getX();
                    case MotionEvent.ACTION_UP:
                        if (x1 < x2) {
                            if (rgsTailorPosition.getChildAt(1).getVisibility() == View.VISIBLE) {
                                ((RadioButton) rgsTailorPosition.getChildAt(1)).setChecked(true);
                            }
                        }
                        break;
                }
                return true;
            }
        });
        //反面，滑动监听
        rlTailorBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = motionEvent.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x2 = motionEvent.getX();
                    case MotionEvent.ACTION_UP:
                        if (x1 > x2) {
                            ((RadioButton) rgsTailorPosition.getChildAt(0)).setChecked(true);
                        } else if (x1 < x2) {
                            if (rgsTailorPosition.getChildAt(2).getVisibility() == View.VISIBLE) {
                                ((RadioButton) rgsTailorPosition.getChildAt(2)).setChecked(true);
                            }
                        }
                        break;
                }
                return true;
            }
        });

        //里子，滑动监听
        rlTailorInside.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = motionEvent.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x2 = motionEvent.getX();
                    case MotionEvent.ACTION_UP:
                        if (x1 > x2) {
                            ((RadioButton) rgsTailorPosition.getChildAt(1)).setChecked(true);
                        }
                        break;
                }
                return true;
            }
        });


        tvTailorName.setText(partsBean.getGoodsName());
        tvTailorPrice.setText("¥" + partsBean.getPrice());
        rvTailorName.setFocusable(false);


        List<CustomResultBean> itemList = new ArrayList<>();


        for (CustomizePartsBean.PartsBean bean : partsBean.getPartsBeans()) {
            CustomResultBean partsBean = new CustomResultBean();
            partsBean.setType(bean.getTitle());
            partsBean.setName(bean.getName());
            itemList.add(partsBean);
        }

        CustomResultBean fabricBean = new CustomResultBean();
        fabricBean.setType(getString(R.string.fabric));
        fabricBean.setName(partsBean.getFabricBean().getFabricName());
        itemList.add(fabricBean);


        for (CustomizePartsBean.EmbroideryBean embroideryBean : partsBean.getEmbroideryBeans()) {
            CustomResultBean embroidery = new CustomResultBean();
            embroidery.setType(embroideryBean.getTitle());
            embroidery.setName(embroideryBean.getName());
            itemList.add(embroidery);
        }

        if (!TextUtils.isEmpty(partsBean.getContent())){
            CustomResultBean contentBean = new CustomResultBean();
            contentBean.setType(getString(R.string.embroidery_content));
            contentBean.setName(partsBean.getContent());
            itemList.add(contentBean);
        }



        MyLinearLayoutManager linearLayoutManager = new MyLinearLayoutManager(this);
        linearLayoutManager.setScrollEnabled(false);
        rvTailorName.setLayoutManager(linearLayoutManager);
        CommonAdapter<CustomResultBean> adapter = new CommonAdapter<CustomResultBean>(this,
                R.layout.listitem_customize_result, itemList) {
            @Override
            protected void convert(ViewHolder holder, CustomResultBean tailorInfoBean, int position) {
                holder.setText(R.id.tv_tailor_type, tailorInfoBean.getType());
                holder.setText(R.id.tv_tailor_item, tailorInfoBean.getName());
            }
        };
        rvTailorName.setAdapter(adapter);
    }


    @OnClick({R.id.img_header_back, R.id.tv_buy_now, R.id.tv_add_bag, R.id.img_header_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                finish();
                break;
            case R.id.tv_buy_now:
                type = 1;
                addToCart();
                break;
            case R.id.tv_add_bag:
                type = 2;
                addToCart();
                break;
            case R.id.img_header_share:
                startActivity(new Intent(this, ShoppingCartActivity.class));
                break;
        }
    }


    /**
     * 加入购物车
     */
    private void addToCart() {
        Map<String, String> map = new HashMap<>();
        map.put("token", SharedPreferencesUtils.getStr(this, "token"));
        map.put("type", String.valueOf(type));
        map.put("goods_id", String.valueOf(partsBean.getGoodsId()));
        map.put("goods_num", "1");
        map.put("fabric_id", partsBean.getFabricBean().getFabricId());
        if (!TextUtils.isEmpty(partsBean.getContent())) {
            map.put("re_marks", partsBean.getContent());
        }

        map.put("special_mark_part_ids", partsBean.getSpecial_mark_part_ids());

        map.put("must_display_part_ids", partsBean.getMust_display_part_ids());

        OkHttpUtils.post()
                .url(Constant.ADD_CART)
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
                            String msg = jsonObject.getString("msg");
                            if (code == 10000) {
                                int cartId = jsonObject.getInt("cart_id");
                                if (type == 1) {
                                    Intent intent = new Intent(CustomizeResultActivity.this, ConfirmOrderActivity.class);
                                    intent.putExtra("cart_id", String.valueOf(cartId));
                                    startActivity(intent);
                                } else {
                                    ToastUtils.showToast(CustomizeResultActivity.this, msg);
                                    addCartAnim();
                                }
                            } else {
                                ToastUtils.showToast(CustomizeResultActivity.this, msg);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }


    /**
     * 加入购物车动画效果，经过一个抛物线（贝塞尔曲线），移动到购物车里
     */
    private void addCartAnim() {
        //1、添加执行动画效果的图片
        final ImageView imgGoods = new ImageView(this);
        Glide.with(this)
                .load(Constant.IMG_HOST + partsBean.getGoodsImg())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgGoods);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(80, 80);
        rlCustomResult.addView(imgGoods, params);


        //2、动画开始/结束点的坐标
        //贝塞尔曲线中间过程的点的坐标
        final float[] mCurrentPosition = new float[2];

        //父布局的起始点坐标
        int[] parentLocation = new int[2];
        rlCustomResult.getLocationInWindow(parentLocation);

        //商品图片的坐标
        int startLoc[] = new int[2];
        tvAddBag.getLocationInWindow(startLoc);

        //购物车图片的坐标
        int endLoc[] = new int[2];
        imgShoppingBag.getLocationInWindow(endLoc);

        //开始掉落的商品的起始点：商品起始点-父布局起始点+该商品图片的一半
        float startX = startLoc[0] - parentLocation[0] + tvAddBag.getWidth() / 3;
        float startY = startLoc[1] - parentLocation[1];

        //商品掉落后的终点坐标：购物车起始点-父布局起始点+购物车图片的一半
        float toX = endLoc[0] - parentLocation[0] + imgShoppingBag.getWidth() / 4;
        float toY = endLoc[1] - parentLocation[1] + imgShoppingBag.getHeight() / 2;

        //3、计算中间动画的插值坐标（贝塞尔曲线）
        //开始绘制贝塞尔曲线
        Path path = new Path();
        //移动到起始点（贝塞尔曲线的起点）
        path.moveTo(startX, startY);
        //使用二阶萨贝尔曲线
        path.quadTo(startX, toY, toX, toY);
        //mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标
        final PathMeasure mPathMeasure = new PathMeasure(path, false);

        //属性动画实现（从0到贝塞尔曲线的长度之间进行插值计算，获取中间过程的距离值）
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        valueAnimator.setDuration(700);
        // 匀速线性插值器
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 当插值计算进行时，获取中间的每个值，
                // 这里这个值是中间过程中的曲线长度（下面根据这个值来得出中间点的坐标值）
                float value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标mCurrentPosition
                mPathMeasure.getPosTan(value, mCurrentPosition, null);
                // 移动的商品图片（动画图片）的坐标设置为该中间点的坐标
                imgGoods.setTranslationX(mCurrentPosition[0]);
                imgGoods.setTranslationY(mCurrentPosition[1]);
            }
        });
        //4、开始执行动画
        valueAnimator.start();

        //5、动画结束后的处理
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            //当动画结束后：
            @Override
            public void onAnimationEnd(Animator animation) {
                // 把移动的图片从父布局里移除
                rlCustomResult.removeView(imgGoods);
                ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(250);
                imgShoppingBag.startAnimation(scaleAnimation);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    /**
     * @param msg 下单成功，结束当前页面
     */
    @Subscribe
    public void orderSuccess(String msg) {
        if ("order_success".equals(msg)) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
