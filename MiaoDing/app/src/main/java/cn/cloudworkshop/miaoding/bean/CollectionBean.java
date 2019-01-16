package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2016-10-21 15:06
 * Email：1993911441@qq.com
 * Describe：
 */

public class CollectionBean {


    /**
     * code : 10000
     * msg : 成功
     * data : {"collections":[{"id":3,"type":1,"rid":3,"tags_name":"首页","img_info":"1.610091","img":"public/20181229/17a97cbbff1bac2cf15214fab7bbba0d6203def7.png","title":"#新品上市","sub_title":"1/4新定价系列，仅售奢侈品同款产品1/4价格","link":""},{"id":1,"type":1,"rid":1,"tags_name":"首页","img_info":"1.610091","img":"public/20181229/3556da9db6fe77e8a4e539af92eeccdd26868ea7.png","title":"#新品上市3","sub_title":"1/4新定价系列，仅售奢侈品同款产品1/4价格","link":"17,1"}],"page":{"totalnum":2,"everypage":10,"totalpage":1,"page":1}}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * collections : [{"id":3,"type":1,"rid":3,"tags_name":"首页","img_info":"1.610091","img":"public/20181229/17a97cbbff1bac2cf15214fab7bbba0d6203def7.png","title":"#新品上市","sub_title":"1/4新定价系列，仅售奢侈品同款产品1/4价格","link":""},{"id":1,"type":1,"rid":1,"tags_name":"首页","img_info":"1.610091","img":"public/20181229/3556da9db6fe77e8a4e539af92eeccdd26868ea7.png","title":"#新品上市3","sub_title":"1/4新定价系列，仅售奢侈品同款产品1/4价格","link":"17,1"}]
         * page : {"totalnum":2,"everypage":10,"totalpage":1,"page":1}
         */

        private PageBean page;
        private List<CollectionsBean> collections;

        public PageBean getPage() {
            return page;
        }

        public void setPage(PageBean page) {
            this.page = page;
        }

        public List<CollectionsBean> getCollections() {
            return collections;
        }

        public void setCollections(List<CollectionsBean> collections) {
            this.collections = collections;
        }

        public static class PageBean {
            /**
             * totalnum : 2
             * everypage : 10
             * totalpage : 1
             * page : 1
             */

            private int totalnum;
            private int everypage;
            private int totalpage;
            private int page;

            public int getTotalnum() {
                return totalnum;
            }

            public void setTotalnum(int totalnum) {
                this.totalnum = totalnum;
            }

            public int getEverypage() {
                return everypage;
            }

            public void setEverypage(int everypage) {
                this.everypage = everypage;
            }

            public int getTotalpage() {
                return totalpage;
            }

            public void setTotalpage(int totalpage) {
                this.totalpage = totalpage;
            }

            public int getPage() {
                return page;
            }

            public void setPage(int page) {
                this.page = page;
            }
        }

        public static class CollectionsBean {
            /**
             * id : 3
             * type : 1
             * rid : 3
             * tags_name : 首页
             * img_info : 1.610091
             * img : public/20181229/17a97cbbff1bac2cf15214fab7bbba0d6203def7.png
             * title : #新品上市
             * sub_title : 1/4新定价系列，仅售奢侈品同款产品1/4价格
             * link :
             */

            private int id;
            private int type;
            private int rid;
            private String img;
            private String title;
            private String sub_title;
            private String name;
            private String sub_name;
            private String price;
            private int goods_type;
            private String factory_name;
            private String address;
            private int lovenum;
            private String img_info;

            public String getImg_info() {
                return img_info;
            }

            public void setImg_info(String img_info) {
                this.img_info = img_info;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSub_name() {
                return sub_name;
            }

            public void setSub_name(String sub_name) {
                this.sub_name = sub_name;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getGoods_type() {
                return goods_type;
            }

            public void setGoods_type(int goods_type) {
                this.goods_type = goods_type;
            }

            public String getFactory_name() {
                return factory_name;
            }

            public void setFactory_name(String factory_name) {
                this.factory_name = factory_name;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getLovenum() {
                return lovenum;
            }

            public void setLovenum(int lovenum) {
                this.lovenum = lovenum;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getRid() {
                return rid;
            }

            public void setRid(int rid) {
                this.rid = rid;
            }


            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSub_title() {
                return sub_title;
            }

            public void setSub_title(String sub_title) {
                this.sub_title = sub_title;
            }
        }
    }
}
