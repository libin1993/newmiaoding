package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2019/1/18 11:16
 * Email：1993911441@qq.com
 * Describe：
 */
public class AccentBean {


    /**
     * code : 10000
     * data : {"goods":[{"id":24,"name":"测试成品1","content":"测试成品1","sell_price":"100.00","ad_img":"public/20190118/8ac70fd3006cd53d8adc5622da816dca69344ebc.png","ad_img_info":"1.000000","is_love":0,"is_collect":0}],"pages":{"totalnum":1,"everypage":8,"totalpage":1,"page":1}}
     */

    private int code;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * goods : [{"id":24,"name":"测试成品1","content":"测试成品1","sell_price":"100.00","ad_img":"public/20190118/8ac70fd3006cd53d8adc5622da816dca69344ebc.png","ad_img_info":"1.000000","is_love":0,"is_collect":0}]
         * pages : {"totalnum":1,"everypage":8,"totalpage":1,"page":1}
         */

        private PagesBean pages;
        private List<GoodsBean> goods;

        public PagesBean getPages() {
            return pages;
        }

        public void setPages(PagesBean pages) {
            this.pages = pages;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class PagesBean {
            /**
             * totalnum : 1
             * everypage : 8
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

        public static class GoodsBean {
            /**
             * id : 24
             * name : 测试成品1
             * content : 测试成品1
             * sell_price : 100.00
             * ad_img : public/20190118/8ac70fd3006cd53d8adc5622da816dca69344ebc.png
             * ad_img_info : 1.000000
             * is_love : 0
             * is_collect : 0
             */

            private int id;
            private String name;
            private String content;
            private String sell_price;
            private String ad_img;
            private String ad_img_info;
            private int is_love;
            private int is_collect;
            private int love_num;

            public int getLove_num() {
                return love_num;
            }

            public void setLove_num(int love_num) {
                this.love_num = love_num;
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

            public String getAd_img() {
                return ad_img;
            }

            public void setAd_img(String ad_img) {
                this.ad_img = ad_img;
            }

            public String getAd_img_info() {
                return ad_img_info;
            }

            public void setAd_img_info(String ad_img_info) {
                this.ad_img_info = ad_img_info;
            }

            public int getIs_love() {
                return is_love;
            }

            public void setIs_love(int is_love) {
                this.is_love = is_love;
            }

            public int getIs_collect() {
                return is_collect;
            }

            public void setIs_collect(int is_collect) {
                this.is_collect = is_collect;
            }
        }
    }
}
