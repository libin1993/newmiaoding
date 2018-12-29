package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2018/12/28 17:02
 * Email：1993911441@qq.com
 * Describe：
 */
public class CustomizedGoodsListBean {

    /**
     * code : 10000
     * goodsid : [{"id":10,"name":"camhan","sell_price":"9999.00","ad_img":"public/20180814/8f9b9425c739d6bcc6f9323744202b86b8b8b271.png|550|550"},{"id":16,"name":"西装","sell_price":"500.00","ad_img":"public/20180930/ebb968c8ff5fcef8c880a3317e12d09e5922b974.png|1125|1398"}]
     */

    private int code;
    private List<GoodsidBean> goodsid;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<GoodsidBean> getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(List<GoodsidBean> goodsid) {
        this.goodsid = goodsid;
    }

    public static class GoodsidBean {
        /**
         * id : 10
         * name : camhan
         * sell_price : 9999.00
         * ad_img : public/20180814/8f9b9425c739d6bcc6f9323744202b86b8b8b271.png|550|550
         */

        private int id;
        private String name;
        private String sell_price;
        private String ad_img;
        private double ad_img_info;
        private String content;

        public double getAd_img_info() {
            return ad_img_info;
        }

        public void setAd_img_info(double ad_img_info) {
            this.ad_img_info = ad_img_info;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSell_price() {
            return sell_price;
        }

        public void setSell_price(String sell_price) {
            this.sell_price = sell_price;
        }

        public String getAd_img() {
            return ad_img;
        }

        public void setAd_img(String ad_img) {
            this.ad_img = ad_img;
        }
    }
}
