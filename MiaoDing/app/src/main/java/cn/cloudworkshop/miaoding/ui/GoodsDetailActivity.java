package cn.cloudworkshop.miaoding.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.bean.CustomizedGoodsBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.BmpRecycleUtils;
import cn.cloudworkshop.miaoding.utils.ImageEncodeUtils;
import cn.cloudworkshop.miaoding.view.SwipeBackActivity;
import okhttp3.Call;

/**
 * Author：Libin on 2017-09-18 09:42
 * Email：1993911441@qq.com
 * Describe：商品详情
 */
public class GoodsDetailActivity extends SwipeBackActivity {
    @BindView(R.id.rv_customize_detail)
    RecyclerView rvGoods;

    private List<CustomizedGoodsBean.DataBean.ImgInfoBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        getData();
        initView();


    }

    private void getData() {
        list = (List<CustomizedGoodsBean.DataBean.ImgInfoBean>) getIntent().getSerializableExtra("img");
    }

    private void initView() {
        rvGoods.setLayoutManager(new LinearLayoutManager(this));
        CommonAdapter<CustomizedGoodsBean.DataBean.ImgInfoBean> adapter = new CommonAdapter<
                CustomizedGoodsBean.DataBean.ImgInfoBean>(this, R.layout.rv_goods_detail_item, list) {
            @Override
            protected void convert(ViewHolder holder, CustomizedGoodsBean.DataBean.ImgInfoBean imgInfoBean, int position) {
                SimpleDraweeView ivGoods = holder.getView(R.id.iv_goods_detail);
                ivGoods.setImageURI(Constant.IMG_HOST+imgInfoBean.getImg());
                ivGoods.setAspectRatio(Float.parseFloat(imgInfoBean.getRatio()));
            }
        };
        rvGoods.setAdapter(adapter);
    }

    @OnClick(R.id.img_back_goods)
    public void onViewClicked() {
        finish();
    }


}
