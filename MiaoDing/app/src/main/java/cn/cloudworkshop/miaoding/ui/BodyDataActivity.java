package cn.cloudworkshop.miaoding.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.BodyDataBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.MyGridLayoutManager;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import okhttp3.Call;

/**
 * Author: Libin on 2019/5/7 20:34
 * Contact: 1993911441@qq.com
 * 形体数据
 */
public class BodyDataActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView mImgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView mTvHeaderTitle;
    @BindView(R.id.ll_null_data)
    LinearLayout mLlNullData;
    @BindView(R.id.rv_body_data)
    RecyclerView mRvBodyData;
    @BindView(R.id.tv_recommend_height)
    TextView mTvRecommendHeight;
    @BindView(R.id.tv_recommend_size)
    TextView mTvRecommendSize;
    @BindView(R.id.ll_recommend_size)
    LinearLayout mLlRecommendSize;
    @BindView(R.id.iv_null_body_data)
    ImageView mIvNullBodyData;
    @BindView(R.id.sv_body_data)
    ScrollView mSvBodyData;

    private BodyDataBean dataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_data);
        ButterKnife.bind(this);
        mTvHeaderTitle.setText("形体数据");
        initData();
    }

    private void initData() {
        OkHttpUtils.get()
                .url(Constant.BODY_DATA)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        dataBean = GsonUtils.jsonToBean(response, BodyDataBean.class);
                        if (dataBean.getCode() == 10000 && dataBean.getData() != null && dataBean.getData().size() > 0) {
                            mIvNullBodyData.setVisibility(View.GONE);
                            mSvBodyData.setVisibility(View.VISIBLE);
                            initView();
                        } else {
                            mIvNullBodyData.setVisibility(View.VISIBLE);
                            mSvBodyData.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void initView() {
        if (!TextUtils.isEmpty(dataBean.getRecommend())) {
            mLlNullData.setVisibility(View.GONE);
            mLlRecommendSize.setVisibility(View.VISIBLE);
            if (dataBean.getRecommend().contains("/")) {
                String[] split = dataBean.getRecommend().split("/");
                mTvRecommendHeight.setText(split[0]);
                mTvRecommendSize.setText(split[1]);
            } else {
                mTvRecommendHeight.setText(dataBean.getRecommend());
            }
        } else {
            mLlNullData.setVisibility(View.VISIBLE);
            mLlRecommendSize.setVisibility(View.GONE);
        }

        MyGridLayoutManager gridLayoutManager = new MyGridLayoutManager(this, 2);
        gridLayoutManager.setScrollEnabled(false);
        mRvBodyData.setLayoutManager(gridLayoutManager);

        CommonAdapter<BodyDataBean.DataBean> adapter = new CommonAdapter<BodyDataBean.DataBean>(
                this, R.layout.rv_item_body_data, dataBean.getData()) {
            @Override
            protected void convert(ViewHolder holder, BodyDataBean.DataBean dataBean, int position) {
                holder.setText(R.id.tv_body_key, dataBean.getKey()+"：");
                holder.setText(R.id.tv_body_value, dataBean.getValue());

            }
        };
        mRvBodyData.setAdapter(adapter);

    }


    @OnClick(R.id.img_header_back)
    public void onViewClicked() {
        finish();
    }
}
