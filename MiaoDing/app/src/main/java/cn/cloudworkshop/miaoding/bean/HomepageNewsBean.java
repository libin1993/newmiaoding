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
     * data : {"banner":[{"id":3,"type":1,"name":"招募","img":"public/20181229/b52dda594339085003f43821ca028ccb436aef0f.jpeg","link":0,"img_info":1.6040100250626566},{"id":2,"type":3,"name":"妙定春季上新活动","img":"public/20181229/a08d4ad80d365c18d1f77d0508bad243d8f49be3.jpeg","link":8,"img_info":1.6023738872403561}],"indextype":[{"id":1,"type":1,"name":"轮播图1","img":"public/20181229/1187c1eb873c348b10dae13f04c616ac36653e1c.png","link":2,"img_info":1.11864406779661},{"id":2,"type":2,"name":"轮播图2","img":"public/20181229/c2bd5e0a2fbc9b903b41d14ec91ce8ce08bed8c9.png","link":13,"img_info":1.11864406779661},{"id":3,"type":3,"name":"轮播图3","img":"public/20181229/18f2017f9ad524c9be97f5b0de3abc6ddfc05cd3.png","link":1,"img_info":1.11864406779661}],"pages":{"totalnum":3,"everypage":3,"totalpage":1,"page":1},"article":[{"name":"首页","id":1,"title":"#新品上市3","sub_title":"1/4新定价系列，仅售奢侈品同款产品1/4价格","img":"public/20181229/3556da9db6fe77e8a4e539af92eeccdd26868ea7.png","view_nums":0,"like_nums":0,"img_info":1.610091743119266},{"name":"搭配","id":2,"title":"#新品上市2","sub_title":"1/4新定价系列，仅售奢侈品同款产品1/4价格2","img":"public/20181229/132517a628b10116fe12bec09a79257efd7b22b9.png","view_nums":0,"like_nums":0,"img_info":1.610091743119266},{"name":"首页","id":3,"title":"#新品上市","sub_title":"1/4新定价系列，仅售奢侈品同款产品1/4价格","img":"public/20181229/17a97cbbff1bac2cf15214fab7bbba0d6203def7.png","view_nums":0,"like_nums":0,"img_info":1.610091743119266}]}
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
         * banner : [{"id":3,"type":1,"name":"招募","img":"public/20181229/b52dda594339085003f43821ca028ccb436aef0f.jpeg","link":0,"img_info":1.6040100250626566},{"id":2,"type":3,"name":"妙定春季上新活动","img":"public/20181229/a08d4ad80d365c18d1f77d0508bad243d8f49be3.jpeg","link":8,"img_info":1.6023738872403561}]
         * indextype : [{"id":1,"type":1,"name":"轮播图1","img":"public/20181229/1187c1eb873c348b10dae13f04c616ac36653e1c.png","link":2,"img_info":1.11864406779661},{"id":2,"type":2,"name":"轮播图2","img":"public/20181229/c2bd5e0a2fbc9b903b41d14ec91ce8ce08bed8c9.png","link":13,"img_info":1.11864406779661},{"id":3,"type":3,"name":"轮播图3","img":"public/20181229/18f2017f9ad524c9be97f5b0de3abc6ddfc05cd3.png","link":1,"img_info":1.11864406779661}]
         * pages : {"totalnum":3,"everypage":3,"totalpage":1,"page":1}
         * article : [{"name":"首页","id":1,"title":"#新品上市3","sub_title":"1/4新定价系列，仅售奢侈品同款产品1/4价格","img":"public/20181229/3556da9db6fe77e8a4e539af92eeccdd26868ea7.png","view_nums":0,"like_nums":0,"img_info":1.610091743119266},{"name":"搭配","id":2,"title":"#新品上市2","sub_title":"1/4新定价系列，仅售奢侈品同款产品1/4价格2","img":"public/20181229/132517a628b10116fe12bec09a79257efd7b22b9.png","view_nums":0,"like_nums":0,"img_info":1.610091743119266},{"name":"首页","id":3,"title":"#新品上市","sub_title":"1/4新定价系列，仅售奢侈品同款产品1/4价格","img":"public/20181229/17a97cbbff1bac2cf15214fab7bbba0d6203def7.png","view_nums":0,"like_nums":0,"img_info":1.610091743119266}]
         */

        private PagesBean pages;
        private List<BannerBean> banner;
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
             * totalnum : 3
             * everypage : 3
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

        public static class BannerBean {
            /**
             * id : 3
             * type : 1
             * name : 招募
             * img : public/20181229/b52dda594339085003f43821ca028ccb436aef0f.jpeg
             * link : 0
             * img_info : 1.6040100250626566
             */

            private int id;
            private int type;
            private String name;
            private String img;
            private int link;
            private double img_info;

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

            public double getImg_info() {
                return img_info;
            }

            public void setImg_info(double img_info) {
                this.img_info = img_info;
            }
        }

        public static class IndextypeBean {
            /**
             * id : 1
             * type : 1
             * name : 轮播图1
             * img : public/20181229/1187c1eb873c348b10dae13f04c616ac36653e1c.png
             * link : 2
             * img_info : 1.11864406779661
             */

            private int id;
            private int type;
            private String name;
            private String img;
            private int link;
            private double img_info;

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

            public double getImg_info() {
                return img_info;
            }

            public void setImg_info(double img_info) {
                this.img_info = img_info;
            }
        }

        public static class ArticleBean {
            /**
             * name : 首页
             * id : 1
             * title : #新品上市3
             * sub_title : 1/4新定价系列，仅售奢侈品同款产品1/4价格
             * img : public/20181229/3556da9db6fe77e8a4e539af92eeccdd26868ea7.png
             * view_nums : 0
             * like_nums : 0
             * img_info : 1.610091743119266
             */

            private String name;
            private int id;
            private String title;
            private String sub_title;
            private String img;
            private int view_nums;
            private int like_nums;
            private double img_info;
            private int is_love;
            private int is_collect;
            private int reply_nums;

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

            public int getReply_nums() {
                return reply_nums;
            }

            public void setReply_nums(int reply_nums) {
                this.reply_nums = reply_nums;
            }

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

            public double getImg_info() {
                return img_info;
            }

            public void setImg_info(double img_info) {
                this.img_info = img_info;
            }
        }
    }
}
