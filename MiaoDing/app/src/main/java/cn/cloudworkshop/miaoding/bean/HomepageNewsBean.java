package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2017-04-28 18:14
 * Email：1993911441@qq.com
 * Describe：
 */
public class HomepageNewsBean {


    /**
     * code : 10000
     * data : {"banner":[{"id":2,"type":3,"name":"妙定春季上新活动","img":"public/20181229/a08d4ad80d365c18d1f77d0508bad243d8f49be3.jpeg","link":8,"img_info":"1.602373"},{"id":1,"type":2,"name":"招募","img":"public/20181229/b52dda594339085003f43821ca028ccb436aef0f.jpeg","link":0,"img_info":"1.604010"}],"hot_goods":[{"name":"藏蓝色格纹衬衫","content":"","sell_price":"599.00","ad_img":"public/20190419/58a9e9e3e5b719102e0a2ca5326fd3b3556a5c1d.jpeg","hot":0,"ad_img_info":"0.973404"},{"name":"浅蓝色细格纹衬衫","content":"","sell_price":"599.00","ad_img":"","hot":0,"ad_img_info":"1.000000"},{"name":"藏蓝色休闲条纹衬衫","content":"","sell_price":"599.00","ad_img":"","hot":0,"ad_img_info":"1.000000"},{"name":"活波律动蓝条纹衬衫","content":"","sell_price":"599.00","ad_img":"","hot":0,"ad_img_info":"1.000000"},{"name":"蓝色格纹文雅衬衫","content":"","sell_price":"599.00","ad_img":"","hot":0,"ad_img_info":"1.000000"},{"name":"纯净蓝格纹衬衫","content":"","sell_price":"599.00","ad_img":"","hot":0,"ad_img_info":"1.000000"}],"indextype":[{"id":1,"type":1,"name":"轮播图1","img":"public/20181229/7fafec38094d1bb54f968c6045958caab8ecdd62.jpeg","link":2,"img_info":"1.604010"},{"id":2,"type":2,"name":"轮播图2","img":"public/20181229/e2fb3b0b9d5486bbc11a81835974272dc4d07ace.jpeg","link":13,"img_info":"1.604010"}],"pages":{"totalnum":36,"everypage":3,"totalpage":12,"page":1},"article":[{"name":"首页","id":72,"title":"#新品上市","sub_title":"1/4新定价系列，仅售奢侈品同款产品1/4价格","img":"public/20190227/48e6d8fc7f9405367facd96cef746bb492b8ae59.png","view_nums":999,"like_nums":257,"reply_nums":1,"img_info":"1.610091","is_love":0,"is_collect":0},{"name":"搭配","id":71,"title":"#新品上市","sub_title":"一挂自然平，穿2天不走样","img":"public/20190227/22dfbc4dffcc92f6f44d4326e0cd0718da03186e.png","view_nums":999,"like_nums":468,"reply_nums":0,"img_info":"1.610091","is_love":0,"is_collect":0},{"name":"资讯","id":65,"title":"#时尚资讯","sub_title":"让中国男人更优雅","img":"public/20190227/bd8f1effd2234220fa318ca66104435e3771f1b7.png","view_nums":999,"like_nums":236,"reply_nums":1,"img_info":"1.610091","is_love":0,"is_collect":0}]}
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
         * banner : [{"id":2,"type":3,"name":"妙定春季上新活动","img":"public/20181229/a08d4ad80d365c18d1f77d0508bad243d8f49be3.jpeg","link":8,"img_info":"1.602373"},{"id":1,"type":2,"name":"招募","img":"public/20181229/b52dda594339085003f43821ca028ccb436aef0f.jpeg","link":0,"img_info":"1.604010"}]
         * hot_goods : [{"name":"藏蓝色格纹衬衫","content":"","sell_price":"599.00","ad_img":"public/20190419/58a9e9e3e5b719102e0a2ca5326fd3b3556a5c1d.jpeg","hot":0,"ad_img_info":"0.973404"},{"name":"浅蓝色细格纹衬衫","content":"","sell_price":"599.00","ad_img":"","hot":0,"ad_img_info":"1.000000"},{"name":"藏蓝色休闲条纹衬衫","content":"","sell_price":"599.00","ad_img":"","hot":0,"ad_img_info":"1.000000"},{"name":"活波律动蓝条纹衬衫","content":"","sell_price":"599.00","ad_img":"","hot":0,"ad_img_info":"1.000000"},{"name":"蓝色格纹文雅衬衫","content":"","sell_price":"599.00","ad_img":"","hot":0,"ad_img_info":"1.000000"},{"name":"纯净蓝格纹衬衫","content":"","sell_price":"599.00","ad_img":"","hot":0,"ad_img_info":"1.000000"}]
         * indextype : [{"id":1,"type":1,"name":"轮播图1","img":"public/20181229/7fafec38094d1bb54f968c6045958caab8ecdd62.jpeg","link":2,"img_info":"1.604010"},{"id":2,"type":2,"name":"轮播图2","img":"public/20181229/e2fb3b0b9d5486bbc11a81835974272dc4d07ace.jpeg","link":13,"img_info":"1.604010"}]
         * pages : {"totalnum":36,"everypage":3,"totalpage":12,"page":1}
         * article : [{"name":"首页","id":72,"title":"#新品上市","sub_title":"1/4新定价系列，仅售奢侈品同款产品1/4价格","img":"public/20190227/48e6d8fc7f9405367facd96cef746bb492b8ae59.png","view_nums":999,"like_nums":257,"reply_nums":1,"img_info":"1.610091","is_love":0,"is_collect":0},{"name":"搭配","id":71,"title":"#新品上市","sub_title":"一挂自然平，穿2天不走样","img":"public/20190227/22dfbc4dffcc92f6f44d4326e0cd0718da03186e.png","view_nums":999,"like_nums":468,"reply_nums":0,"img_info":"1.610091","is_love":0,"is_collect":0},{"name":"资讯","id":65,"title":"#时尚资讯","sub_title":"让中国男人更优雅","img":"public/20190227/bd8f1effd2234220fa318ca66104435e3771f1b7.png","view_nums":999,"like_nums":236,"reply_nums":1,"img_info":"1.610091","is_love":0,"is_collect":0}]
         */

        private PagesBean pages;
        private List<BannerBean> banner;
        private List<HotGoodsBean> hot_goods;
        private List<IndextypeBean> indextype;
        private List<ArticleBean> article;

        public PagesBean getPages() {
            return pages;
        }

        public void setPages(PagesBean pages) {
            this.pages = pages;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<HotGoodsBean> getHot_goods() {
            return hot_goods;
        }

        public void setHot_goods(List<HotGoodsBean> hot_goods) {
            this.hot_goods = hot_goods;
        }

        public List<IndextypeBean> getIndextype() {
            return indextype;
        }

        public void setIndextype(List<IndextypeBean> indextype) {
            this.indextype = indextype;
        }

        public List<ArticleBean> getArticle() {
            return article;
        }

        public void setArticle(List<ArticleBean> article) {
            this.article = article;
        }

        public static class PagesBean {
            /**
             * totalnum : 36
             * everypage : 3
             * totalpage : 12
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

        public static class BannerBean {
            /**
             * id : 2
             * type : 3
             * name : 妙定春季上新活动
             * img : public/20181229/a08d4ad80d365c18d1f77d0508bad243d8f49be3.jpeg
             * link : 8
             * img_info : 1.602373
             */

            private int id;
            private int type;
            private String name;
            private String img;
            private int link;
            private String img_info;

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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getLink() {
                return link;
            }

            public void setLink(int link) {
                this.link = link;
            }

            public String getImg_info() {
                return img_info;
            }

            public void setImg_info(String img_info) {
                this.img_info = img_info;
            }
        }

        public static class HotGoodsBean {
            /**
             * name : 藏蓝色格纹衬衫
             * content :
             * sell_price : 599.00
             * ad_img : public/20190419/58a9e9e3e5b719102e0a2ca5326fd3b3556a5c1d.jpeg
             * hot : 0
             * ad_img_info : 0.973404
             */
            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            private String content;
            private String sell_price;
            private String ad_img;
            private int hot;
            private String ad_img_info;

            public int getCategory_id() {
                return category_id;
            }

            public void setCategory_id(int category_id) {
                this.category_id = category_id;
            }

            private int category_id;

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

            public int getHot() {
                return hot;
            }

            public void setHot(int hot) {
                this.hot = hot;
            }

            public String getAd_img_info() {
                return ad_img_info;
            }

            public void setAd_img_info(String ad_img_info) {
                this.ad_img_info = ad_img_info;
            }
        }

        public static class IndextypeBean {
            /**
             * id : 1
             * type : 1
             * name : 轮播图1
             * img : public/20181229/7fafec38094d1bb54f968c6045958caab8ecdd62.jpeg
             * link : 2
             * img_info : 1.604010
             */

            private int id;
            private int type;
            private String name;
            private String img;
            private int link;
            private String img_info;
            private String goods_name;
            private String sell_price;

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getSell_price() {
                return sell_price;
            }

            public void setSell_price(String sell_price) {
                this.sell_price = sell_price;
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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getLink() {
                return link;
            }

            public void setLink(int link) {
                this.link = link;
            }

            public String getImg_info() {
                return img_info;
            }

            public void setImg_info(String img_info) {
                this.img_info = img_info;
            }
        }

        public static class ArticleBean {
            /**
             * name : 首页
             * id : 72
             * title : #新品上市
             * sub_title : 1/4新定价系列，仅售奢侈品同款产品1/4价格
             * img : public/20190227/48e6d8fc7f9405367facd96cef746bb492b8ae59.png
             * view_nums : 999
             * like_nums : 257
             * reply_nums : 1
             * img_info : 1.610091
             * is_love : 0
             * is_collect : 0
             */

            private String name;
            private int id;
            private String title;
            private String sub_title;
            private String img;
            private int view_nums;
            private int like_nums;
            private int reply_nums;
            private String img_info;
            private int is_love;
            private int is_collect;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getView_nums() {
                return view_nums;
            }

            public void setView_nums(int view_nums) {
                this.view_nums = view_nums;
            }

            public int getLike_nums() {
                return like_nums;
            }

            public void setLike_nums(int like_nums) {
                this.like_nums = like_nums;
            }

            public int getReply_nums() {
                return reply_nums;
            }

            public void setReply_nums(int reply_nums) {
                this.reply_nums = reply_nums;
            }

            public String getImg_info() {
                return img_info;
            }

            public void setImg_info(String img_info) {
                this.img_info = img_info;
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
