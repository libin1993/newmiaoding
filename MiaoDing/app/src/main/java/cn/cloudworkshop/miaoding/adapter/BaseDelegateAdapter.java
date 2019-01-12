package cn.cloudworkshop.miaoding.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Author：Libin on 2019/1/10 14:29
 * Email：1993911441@qq.com
 * Describe：
 */
public class BaseDelegateAdapter extends DelegateAdapter.Adapter<ViewHolder> {
    private LayoutHelper mLayoutHelper;
    private int mCount;
    private int mLayoutId;
    private Context mContext;
    private int mViewTypeItem;

    public BaseDelegateAdapter(Context context, LayoutHelper layoutHelper, int layoutId, int count, int viewTypeItem) {
        this.mContext = context;
        this.mCount = count;
        this.mLayoutHelper = layoutHelper;
        this.mLayoutId = layoutId;
        this.mViewTypeItem = viewTypeItem;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == mViewTypeItem) {
            return new ViewHolder(mContext, LayoutInflater.from(mContext).inflate(mLayoutId, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }


    /**
     * 必须重写不然会出现滑动不流畅的情况
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return mViewTypeItem;
    }

    /**
     * @return 条目数量
     */

    @Override
    public int getItemCount() {
        return mCount;
    }
}
