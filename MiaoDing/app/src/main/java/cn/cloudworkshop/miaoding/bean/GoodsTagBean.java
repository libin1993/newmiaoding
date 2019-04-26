package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2019/4/26 09:16
 * Email：1993911441@qq.com
 * Describe：
 */
public class GoodsTagBean {

    /**
     * code : 10000
     * data : ["白色","蓝色","纯色","条纹","印花","格纹"]
     */

    private int code;
    private List<String> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
