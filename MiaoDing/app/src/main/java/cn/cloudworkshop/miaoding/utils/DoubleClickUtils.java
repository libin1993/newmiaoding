package cn.cloudworkshop.miaoding.utils;

/**
 * Author：Libin on 2019/1/22 14:50
 * Email：1993911441@qq.com
 * Describe：防止重复点击
 */
public class DoubleClickUtils {

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;

    }
}

