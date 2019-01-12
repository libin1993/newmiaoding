package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2018/3/14 16:00
 * Email：1993911441@qq.com
 * Describe：
 */
public class NewsListBean {


    /**
     * code : 10000
     * data : {"pages":{"totalnum":12,"everypage":3,"totalpage":4,"page":1},"article":[{"name":"首页","id":1,"title":"#新品上市1","sub_title":"1/4新定价系列，仅售奢侈品同款产品1/4价格","img":"public/20181229/3556da9db6fe77e8a4e539af92eeccdd26868ea7.png","view_nums":1,"like_nums":4,"reply_nums":7,"img_info":"1.610091","is_love":0,"is_collect":1},{"name":"搭配","id":2,"title":"#新品上市2","sub_title":"1/4新定价系列，仅售奢侈品同款产品1/4价格2","img":"public/20181229/132517a628b10116fe12bec09a79257efd7b22b9.png|1053|654","view_nums":1,"like_nums":2,"reply_nums":1},{"name":"首页","id":3,"title":"#新品上市3","sub_title":"1/4新定价系列，仅售奢侈品同款产品1/4价格","img":"public/20181229/17a97cbbff1bac2cf15214fab7bbba0d6203def7.png|1053|654","view_nums":1,"like_nums":2,"reply_nums":1}]}
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
         * pages : {"totalnum":12,"everypage":3,"totalpage":4,"page":1}
         * article : [{"name":"首页","id":1,"title":"#新品上市1","sub_title":"1/4新定价系列，仅售奢侈品同款产品1/4价格","img":"public/20181229/3556da9db6fe77e8a4e539af92eeccdd26868ea7.png","view_nums":1,"like_nums":4,"reply_nums":7,"img_info":"1.610091","is_love":0,"is_collect":1},{"name":"搭配","id":2,"title":"#新品上市2","sub_title":"1/4新定价系列，仅售奢侈品同款产品1/4价格2","img":"public/20181229/132517a628b10116fe12bec09a79257efd7b22b9.png|1053|654","view_nums":1,"like_nums":2,"reply_nums":1},{"name":"首页","id":3,"title":"#新品上市3","sub_title":"1/4新定价系列，仅售奢侈品同款产品1/4价格","img":"public/20181229/17a97cbbff1bac2cf15214fab7bbba0d6203def7.png|1053|654","view_nums":1,"like_nums":2,"reply_nums":1}]
         */

        private PagesBean pages;
        private List<ArticleBean> article;

        public PagesBean getPages() {
            return pages;
        }

        public void setPages(PagesBean pages) {
            this.pages = pages;
        }

        public List<ArticleBean> getArticle() {
            return article;
        }

        public void setArticle(List<ArticleBean> article) {
            this.article = article;
        }

        public static class PagesBean {
            /**
             * totalnum : 12
             * everypage : 3
             * totalpage : 4
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

        public static class ArticleBean {
            /**
             * name : 首页
             * id : 1
             * title : #新品上市1
             * sub_title : 1/4新定价系列，仅售奢侈品同款产品1/4价格
             * img : public/20181229/3556da9db6fe77e8a4e539af92eeccdd26868ea7.png
             * view_nums : 1
             * like_nums : 4
             * reply_nums : 7
             * img_info : 1.610091
             * is_love : 0
             * is_collect : 1
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
