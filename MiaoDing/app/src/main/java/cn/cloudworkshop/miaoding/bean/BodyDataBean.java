package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author: Libin on 2019/5/7 20:37
 * Contact: 1993911441@qq.com
 */
public class BodyDataBean {

    /**
     * code : 10000
     * msg :
     * data : [{"k":"height","key":"身    高","value":175},{"k":"weight","key":"体    重","value":69},{"k":"bust_product","key":"胸    围","value":0},{"k":"hipline_product","key":"臀    围","value":0},{"k":"neck_product","key":"领    围","value":0},{"k":"cuff_left_product","key":"袖口左","value":0},{"k":"cuff_right_product","key":"袖口右","value":0},{"k":"waist_circumference_product","key":"中腰围","value":0},{"k":"shoulder_product","key":"肩    宽","value":0},{"k":"pants_waist_product","key":"裤腰围","value":0},{"k":"shirt_sleeve_left_product","key":"袖长左","value":0},{"k":"shirt_sleeve_right_product","key":"袖长右","value":0},{"k":"thigh_circumference_product","key":"大腿围","value":0},{"k":"shirt_coatlength_product","key":"后衣长","value":0},{"k":"full_gear_product","key":"全    档","value":0},{"k":"trousers_length_product","key":"裤    长","value":0}]
     * recommend :
     */

    private int code;
    private String msg;
    private String recommend;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * k : height
         * key : 身    高
         * value : 175
         */

        private String k;
        private String key;
        private String value;

        public String getK() {
            return k;
        }

        public void setK(String k) {
            this.k = k;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
