package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2017/2/9 15:26
 * Email：1993911441@qq.com
 * Describe：
 */
public class RecommendGoodsBean {


    /**
     * code : 10000
     * data : [{"id":2,"name":"驼色棉羊绒混纺衬衫","ad_img":"public/20181224/4159897eca6e128208bf9e4ecd114f20fafd1d0a.png","car_img":"public/20181224/a53ec0400d6ec030bb2c82baf176e5b22a9cf13c.png","content":"驼色棉羊绒混纺衬衫","sell_price":"999.00","category_id":1},{"id":1,"name":"白色Easycare易打理衬衫","ad_img":"public/20181019/63dec0863a01e2398b7b892399776a66631668af.png","car_img":"public/20181019/3760d0add196a18a1b7ab9742494138da940fc3c.png","content":"白色Easycare易打理衬衫","sell_price":"399.00","category_id":1}]
     */

    private int code;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2
         * name : 驼色棉羊绒混纺衬衫
         * ad_img : public/20181224/4159897eca6e128208bf9e4ecd114f20fafd1d0a.png
         * car_img : public/20181224/a53ec0400d6ec030bb2c82baf176e5b22a9cf13c.png
         * content : 驼色棉羊绒混纺衬衫
         * sell_price : 999.00
         * category_id : 1
         */

        private int id;
        private String name;
        private String ad_img;
        private String car_img;
        private String content;
        private String sell_price;
        private int category_id;

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

        public String getAd_img() {
            return ad_img;
        }

        public void setAd_img(String ad_img) {
            this.ad_img = ad_img;
        }

        public String getCar_img() {
            return car_img;
        }

        public void setCar_img(String car_img) {
            this.car_img = car_img;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getSell_price() {
            return sell_price;
        }

        public void setSell_price(String sell_price) {
            this.sell_price = sell_price;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }
    }
}
