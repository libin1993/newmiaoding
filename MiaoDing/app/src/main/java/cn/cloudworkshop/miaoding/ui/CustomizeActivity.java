package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.CustomizePartsBean;
import cn.cloudworkshop.miaoding.bean.GuideBean;
import cn.cloudworkshop.miaoding.bean.NewEmbroideryBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.view.CircleImageView;
import okhttp3.Call;

/**
 * Author：Libin on 2016/8/31 09:24
 * Email：1993911441@qq.com
 * Describe：定制选择配件页面（当前版）
 */
public class CustomizeActivity extends BaseActivity {

    @BindView(R.id.rv_tailor_cloth)
    RecyclerView rvTailor;
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.img_header_share)
    ImageView imgHeaderShare;
    @BindView(R.id.rv_tailor_item)
    RecyclerView rvTailorItem;
    @BindView(R.id.img_large_material)
    ImageView imgLargeMaterial;
    @BindView(R.id.rl_positive_tailor)
    RelativeLayout rlPositiveTailor;
    @BindView(R.id.rl_back_tailor)
    RelativeLayout rlBackTailor;
    @BindView(R.id.rgs_select_orientation)
    RadioGroup rgsSelectOrientation;
    @BindView(R.id.rl_inside_tailor)
    RelativeLayout rlInsideTailor;
    @BindView(R.id.img_tailor_reset)
    ImageView imgReset;
    @BindView(R.id.img_tailor_guide)
    ImageView imgGuide;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;
    @BindView(R.id.img_tailor_success)
    ImageView imgTailorSuccess;

    //配件
    private List<NewEmbroideryBean.DataBean.MustDisplayPartBean.SonBean> itemList = new ArrayList<>();
    //稀疏数组
    //选择部件id
    private SparseIntArray itemArray = new SparseIntArray();

    private CommonAdapter<NewEmbroideryBean.DataBean.MustDisplayPartBean.SonBean> itemAdapter;

    //当前部件位置
    private int currentPart;
    //首次选择
    private boolean firstSelect = true;
    //展示图滑动监听
    private float x1 = 0;
    private float x2 = 0;

    private GuideBean guideBean;
    //首次进入引导页面
    private boolean isFirstEntry;
    //是否长按
    private boolean isLongPress;

    private List<NewEmbroideryBean.DataBean.MustDisplayPartBean> dataList;
    private CustomizePartsBean partsBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);
        getData();
        initData();
    }

    /**
     * 商品信息
     */
    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            dataList = (List<NewEmbroideryBean.DataBean.MustDisplayPartBean>) bundle.getSerializable("parts");
            partsBean = (CustomizePartsBean) bundle.getSerializable("embroidery");
        }

    }


    /**
     * 获取网络数据
     */
    private void initData() {
        isFirstEntry = SharedPreferencesUtils.getBoolean(this, "tailor_guide", true);
        //首次进入弹出引导图
        if (isFirstEntry) {
            OkHttpUtils.get()
                    .url(Constant.GUIDE_IMG)
                    .addParams("id", "2")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {

                            guideBean = GsonUtils.jsonToBean(response, GuideBean.class);
                            if (guideBean.getData().getImg_urls() != null && guideBean.getData()
                                    .getImg_urls().size() > 0) {
                                imgGuide.setVisibility(View.VISIBLE);
                                Glide.with(CustomizeActivity.this)
                                        .load(Constant.IMG_HOST + guideBean.getData().getImg_urls().get(0).getImg())
                                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                        .into(imgGuide);
                            }
                        }
                    });
        }

        initView();

    }


    /**
     * 加载视图
     */
    private void initView() {
        tvHeaderTitle.setText(R.string.customize);
        ((RadioButton) rgsSelectOrientation.getChildAt(0)).setChecked(true);
        //进入页面显示默认部件图
        for (int i = 0; i < dataList.size(); i++) {
            ImageView img1 = new ImageView(this);
            img1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            ImageView img2 = new ImageView(this);
            img2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            ImageView img3 = new ImageView(this);
            img3.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            switch (dataList.get(i).getImg_mark()) {
                case 1:
                    for (NewEmbroideryBean.DataBean.MustDisplayPartBean.SonBean sonBean : dataList.get(i).getSon()) {
                        if (sonBean.getIs_default() == 1) {
                            Glide.with(getApplicationContext())
                                    .load(Constant.IMG_HOST + sonBean.getAndroid_max())
                                    .fitCenter()
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .into(img1);
                            break;
                        }
                    }

                    break;
                case 2:
                    for (NewEmbroideryBean.DataBean.MustDisplayPartBean.SonBean sonBean : dataList.get(i).getSon()) {
                        if (sonBean.getIs_default() == 1) {
                            Glide.with(getApplicationContext())
                                    .load(Constant.IMG_HOST + sonBean.getAndroid_max())
                                    .fitCenter()
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .into(img2);
                            break;
                        }
                    }
                    break;
                case 3:
                    rgsSelectOrientation.getChildAt(2).setVisibility(View.VISIBLE);
                    for (NewEmbroideryBean.DataBean.MustDisplayPartBean.SonBean sonBean : dataList.get(i).getSon()) {
                        if (sonBean.getIs_default() == 1) {
                            Glide.with(getApplicationContext())
                                    .load(Constant.IMG_HOST + sonBean.getAndroid_max())
                                    .fitCenter()
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .into(img3);
                            break;
                        }
                    }

                    break;
            }
            rlPositiveTailor.addView(img1);
            rlBackTailor.addView(img2);
            rlInsideTailor.addView(img3);
        }

        //正反面监听
        rgsSelectOrientation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                for (int m = 0; m < rgsSelectOrientation.getChildCount(); m++) {
                    RadioButton rBtn = (RadioButton) radioGroup.getChildAt(m);
                    if (rBtn.getId() == i) {
                        switch (m) {
                            case 0:
                                rlPositiveTailor.setVisibility(View.VISIBLE);
                                rlBackTailor.setVisibility(View.GONE);
                                rlInsideTailor.setVisibility(View.GONE);
                                break;
                            case 1:
                                rlBackTailor.setVisibility(View.VISIBLE);
                                rlPositiveTailor.setVisibility(View.GONE);
                                rlInsideTailor.setVisibility(View.GONE);
                                break;
                            case 2:
                                rlInsideTailor.setVisibility(View.VISIBLE);
                                rlPositiveTailor.setVisibility(View.GONE);
                                rlBackTailor.setVisibility(View.GONE);
                                break;
                        }
                    }
                }
            }
        });

        //正面，监听右滑动手势
        rlPositiveTailor.setOnTouchListener(new View.OnTouchListener() {
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
                            ((RadioButton) rgsSelectOrientation.getChildAt(1)).setChecked(true);
                        }
                        break;
                }
                return true;
            }
        });
        //背面，监听左右滑动
        rlBackTailor.setOnTouchListener(new View.OnTouchListener() {
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
                            ((RadioButton) rgsSelectOrientation.getChildAt(0)).setChecked(true);
                        } else if (x1 < x2) {
                            if (rgsSelectOrientation.getChildAt(2).getVisibility() == View.VISIBLE) {
                                ((RadioButton) rgsSelectOrientation.getChildAt(2)).setChecked(true);
                            }
                        }
                        break;
                }
                return true;
            }
        });
        //里子，监听左滑手势
        rlInsideTailor.setOnTouchListener(new View.OnTouchListener() {
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
                            ((RadioButton) rgsSelectOrientation.getChildAt(1)).setChecked(true);
                        }
                        break;
                }
                return true;
            }

        });

        //配件
        rvTailor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));
        CommonAdapter<NewEmbroideryBean.DataBean.MustDisplayPartBean> adapter = new
                CommonAdapter<NewEmbroideryBean.DataBean.MustDisplayPartBean>(CustomizeActivity.this,
                        R.layout.listitem_customize_parts, dataList) {
                    @Override
                    protected void convert(ViewHolder holder, NewEmbroideryBean.DataBean
                            .MustDisplayPartBean mustDisplayPartBean, int position) {
                        Glide.with(CustomizeActivity.this)
                                .load(Constant.IMG_HOST + mustDisplayPartBean.getAndroid_min())
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .placeholder(R.mipmap.place_holder_goods)
                                .into((ImageView) holder.getView(R.id.img_tailor_item));
                    }


                };
        rvTailor.setAdapter(adapter);
        //点击配件
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //首次进入弹出引导图
                if (isFirstEntry && guideBean.getData().getImg_urls().get(1).getImg() != null) {
                    imgGuide.setVisibility(View.VISIBLE);
                    Glide.with(CustomizeActivity.this)
                            .load(Constant.IMG_HOST + guideBean.getData().getImg_urls().get(1).getImg())
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(imgGuide);
                    isFirstEntry = false;
                    SharedPreferencesUtils.saveBoolean(CustomizeActivity.this,
                            "tailor_guide", false);
                }


                //选择正反面
                ((RadioButton) rgsSelectOrientation.getChildAt(dataList
                        .get(position).getImg_mark() - 1)).setChecked(true);
                tvHeaderTitle.setText(dataList.get(position).getType_name());
                //当前点击配件位置
                currentPart = position;
                //部件大图隐藏
                imgLargeMaterial.setVisibility(View.GONE);
                //子配件布局显示
                rvTailorItem.setVisibility(View.VISIBLE);
                //判断不可搭配
                noMatchSpec();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        //子配件
        rvTailorItem.setLayoutManager(new LinearLayoutManager(CustomizeActivity.this,
                LinearLayoutManager.HORIZONTAL, false));
        itemAdapter = new CommonAdapter<NewEmbroideryBean.DataBean.MustDisplayPartBean.SonBean>(
                CustomizeActivity.this, R.layout.listitem_customize_parts, itemList) {
            @Override
            protected void convert(ViewHolder holder, NewEmbroideryBean.DataBean.MustDisplayPartBean.SonBean
                    sonBean, int position) {
                Glide.with(CustomizeActivity.this)
                        .load(Constant.IMG_HOST + sonBean.getAndroid_min())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .placeholder(R.mipmap.place_holder_goods)
                        .into((CircleImageView) holder.getView(R.id.img_tailor_item));
            }

        };
        rvTailorItem.setAdapter(itemAdapter);

        //子配件长按显示大图处理
        rvTailorItem.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE || motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    imgLargeMaterial.setVisibility(View.GONE);
                }
                return false;
            }

        });

        //子配件点击事件
        itemAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (!isLongPress) {

                    //选择子配件，默认布局清空
                    if (firstSelect) {
                        for (int i = 0; i < rlPositiveTailor.getChildCount(); i++) {
                            ImageView positiveImg = (ImageView) rlPositiveTailor.getChildAt(i);
                            positiveImg.setImageDrawable(null);
                        }

                        for (int i = 0; i < rlBackTailor.getChildCount(); i++) {
                            ImageView backImg = (ImageView) rlBackTailor.getChildAt(i);
                            backImg.setImageDrawable(null);
                        }

                        for (int i = 0; i < rlInsideTailor.getChildCount(); i++) {
                            ImageView inSideImg = (ImageView) rlInsideTailor.getChildAt(i);
                            inSideImg.setImageDrawable(null);
                        }
                        firstSelect = false;
                    }
                    //重置按钮显示
                    imgReset.setVisibility(View.VISIBLE);
                    //当前选择的子配件数组
                    itemArray.put(currentPart, itemList.get(position).getPart_id());

                    //选择子配件，配件图片修改
                    CircleImageView img = (CircleImageView) rvTailor.findViewHolderForAdapterPosition(currentPart)
                            .itemView.findViewById(R.id.img_tailor_item);
                    Glide.with(CustomizeActivity.this)
                            .load(Constant.IMG_HOST + itemList.get(position).getAndroid_min())
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(img);
                    //当前子配件蒙版
                    View itemBg = rvTailor.findViewHolderForAdapterPosition(currentPart).itemView
                            .findViewById(R.id.view_tailor_item);
                    itemBg.setVisibility(View.VISIBLE);

                    //选择子配件对应的正反面
                    ((RadioButton) rgsSelectOrientation.getChildAt(dataList.get(currentPart)
                            .getImg_mark() - 1)).setChecked(true);

                    switch (dataList.get(currentPart).getImg_mark()) {
                        //当前子配件是正面
                        case 1:
                            ImageView positiveImg = (ImageView) rlPositiveTailor.getChildAt(currentPart);
                            Glide.with(CustomizeActivity.this)
                                    .load(Constant.IMG_HOST + itemList.get(position).getAndroid_max())
                                    .fitCenter()
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .into(positiveImg);

                            break;
                        //当前子配件是反面
                        case 2:
                            ImageView backImg = (ImageView) rlBackTailor.getChildAt(currentPart);
                            Glide.with(CustomizeActivity.this)
                                    .load(Constant.IMG_HOST + itemList.get(position).getAndroid_max())
                                    .fitCenter()
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .into(backImg);
                            break;
                        //当前子配件是里子
                        case 3:
                            ImageView inSideImg = (ImageView) rlInsideTailor.getChildAt(currentPart);
                            Glide.with(CustomizeActivity.this)
                                    .load(Constant.IMG_HOST + itemList.get(position).getAndroid_max())
                                    .fitCenter()
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .into(inSideImg);
                            break;
                    }
                    //标题显示当前子配件名称
                    tvHeaderTitle.setText(itemList.get(position).getPart_name());
                    //是否选择所有子配件
                    isAllSelect();
                } else {
                    //长按子配件显示大图，离开屏幕大图消失
                    isLongPress = false;
                    if (imgLargeMaterial.getVisibility() == View.VISIBLE) {
                        imgLargeMaterial.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                Glide.with(CustomizeActivity.this)
                        .load(Constant.IMG_HOST + itemList.get(position).getAndroid_middle())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(imgLargeMaterial);
                isLongPress = true;
                if (imgLargeMaterial.getVisibility() == View.GONE) {
                    imgLargeMaterial.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

    }

    /**
     * 不可搭配，子配件数据源更新
     */
    private void noMatchSpec() {

        List<String> noMatchIds = new ArrayList<>();
        for (int k = 0; k < itemArray.size(); k++) {
            for (int i = 0; i < dataList.size(); i++) {
                for (int j = 0; j < dataList.get(i).getSon().size(); j++) {
                    if (itemArray.valueAt(k) == dataList.get(i).getSon().get(j).getPart_id()) {
                        if (!TextUtils.isEmpty(dataList.get(i).getSon().get(j).getMutex_part())) {
                            if (dataList.get(i).getSon().get(j).getMutex_part().contains(",")) {
                                String[] split = dataList.get(i).getSon().get(j)
                                        .getMutex_part().split(",");
                                noMatchIds.addAll(Arrays.asList(split));
                            } else {
                                noMatchIds.add(dataList.get(i).getSon().get(j).getMutex_part());
                            }
                            break;
                        }
                    }
                }
            }
        }

        itemList.clear();

        for (int j = 0; j < dataList.get(currentPart).getSon().size(); j++) {
            if (!noMatchIds.contains(String.valueOf(dataList.get(currentPart)
                    .getSon().get(j).getPart_id()))) {
                itemList.add(dataList.get(currentPart).getSon().get(j));
            }
        }
        //刷新子配件布局
        itemAdapter.notifyDataSetChanged();

    }

    /**
     * 部件是否都选择
     *
     * @param
     */
    private void isAllSelect() {
        if (itemArray.size() == dataList.size()) {
            imgTailorSuccess.setVisibility(View.VISIBLE);
        }
    }


    @OnClick({R.id.img_header_back, R.id.img_tailor_success, R.id.rl_positive_tailor,
            R.id.img_tailor_reset, R.id.img_tailor_guide})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                finish();
                break;
            case R.id.img_tailor_success:
                nextStep();
                break;
            case R.id.rl_positive_tailor:
                imgLargeMaterial.setVisibility(View.GONE);
                break;
            case R.id.img_tailor_reset:
                reselect();
                break;
            case R.id.img_tailor_guide:
                imgGuide.setVisibility(View.GONE);
                break;

        }
    }

    /**
     * 重置，重现选择配件
     */
    private void reselect() {

        itemArray.clear();
        itemList.clear();
        itemAdapter.notifyDataSetChanged();
        firstSelect = true;
        imgTailorSuccess.setVisibility(View.GONE);
        rvTailorItem.setVisibility(View.GONE);
        imgReset.setVisibility(View.GONE);
        rlPositiveTailor.removeAllViews();
        rlBackTailor.removeAllViews();
        rlInsideTailor.removeAllViews();

        initView();
    }


    /**
     * 定制完成，下一步
     */
    private void nextStep() {
        Intent intent = new Intent(this, CustomizeResultActivity.class);
        Bundle bundle = new Bundle();


        //部件
        List<CustomizePartsBean.PartsBean> itemList = new ArrayList<>();
        String partIds = "";


        for (int i = 0; i < itemArray.size(); i++) {

            int value = itemArray.valueAt(i);

            for (int j = 0; j < dataList.size(); j++) {

                for (int k = 0; k < dataList.get(j).getSon().size(); k++) {
                    if (value == dataList.get(j).getSon().get(k).getPart_id()) {
                        partIds += value + ",";
                        CustomizePartsBean.PartsBean partsBean = new CustomizePartsBean.PartsBean();
                        partsBean.setImg(dataList.get(j).getSon().get(k).getAndroid_max());
                        partsBean.setTitle(dataList.get(j).getType_name());
                        partsBean.setName(dataList.get(j).getSon().get(k).getPart_name());
                        partsBean.setPositionId(dataList.get(j).getImg_mark());

                        itemList.add(partsBean);
                        break;
                    }
                }
            }
        }
        partIds = partIds.substring(0, partIds.length() - 1);

        partsBean.setMust_display_part_ids(partIds);
        partsBean.setPartsBeans(itemList);


        bundle.putSerializable("customize", partsBean);
        intent.putExtras(bundle);
        startActivity(intent);

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
