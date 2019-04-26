package cn.cloudworkshop.miaoding.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.adapter.GoodsFragmentAdapter;
import cn.cloudworkshop.miaoding.base.BaseFragment;
import cn.cloudworkshop.miaoding.bean.GoodsTagBean;
import cn.cloudworkshop.miaoding.bean.GoodsTitleBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2019/4/25 15:55
 * Email：1993911441@qq.com
 * Describe：
 */
public class ShirtFragment extends BaseFragment {
    @BindView(R.id.tab_shirt)
    SlidingTabLayout tabShirt;
    @BindView(R.id.vp_shirt)
    ViewPager vpShirt;
    private Unbinder unbinder;

    private int classifyId;
    private int type;
    private GoodsTagBean goodsTagBean;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shirt, container, false);
        unbinder = ButterKnife.bind(this, view);
        getData();
        initType();
        return view;
    }

    private void initType() {
        OkHttpUtils.post()
                .url(Constant.GOODS_TAG)
                .addParams("type", String.valueOf(type))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        goodsTagBean = GsonUtils.jsonToBean(response, GoodsTagBean.class);
                        initView();
                    }
                });
    }

    private void initView() {
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        titleList.add("全部");
        fragmentList.add(SubGoodsFragment.newInstance(classifyId, null));

        for (int i = 0; i < goodsTagBean.getData().size(); i++) {
            titleList.add(goodsTagBean.getData().get(i));
            fragmentList.add(SubGoodsFragment.newInstance(classifyId, goodsTagBean.getData().get(i)));
        }


        GoodsFragmentAdapter adapter = new GoodsFragmentAdapter(getChildFragmentManager(),
                fragmentList, titleList);
        vpShirt.setOffscreenPageLimit(titleList.size());
        vpShirt.setAdapter(adapter);
        tabShirt.setViewPager(vpShirt);
        tabShirt.setCurrentTab(0);
    }


    private void getData() {

        Bundle bundle = getArguments();
        classifyId = bundle.getInt("classify_id");
        type = bundle.getInt("type");
    }

    public static ShirtFragment newInstance(int classify_id, int type) {

        Bundle args = new Bundle();
        args.putInt("classify_id", classify_id);
        args.putInt("type", type);
        ShirtFragment fragment = new ShirtFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
