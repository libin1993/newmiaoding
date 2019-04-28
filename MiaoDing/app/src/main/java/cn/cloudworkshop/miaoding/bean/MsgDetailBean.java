package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2017/1/3 14:13
 * Email：1993911441@qq.com
 * Describe：
 */
public class MsgDetailBean {


    /**
     * code : 10000
     * pages : {"totalnum":1,"everypage":10,"totalpage":1,"page":1}
     * data : [{"id":169,"username":"哈哈哈哈","user_phone":"15167168495","status":0,"read_time":"1970-01-01 08:00:00","title":"21321","img":"213","content":"324324","type":1,"re_marks":"32442","create_time":"2019-01-17 09:33:10","type_txt":"通知消息","status_txt":"未读"}]
     */

    private int code;
    private PagesBean pages;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public PagesBean getPages() {
        return pages;
    }

    public void setPages(PagesBean pages) {
        this.pages = pages;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class PagesBean {
        /**
         * totalnum : 1
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

    public static class DataBean {
        /**
         * id : 169
         * username : 哈哈哈哈
         * user_phone : 15167168495
         * status : 0
         * read_time : 1970-01-01 08:00:00
         * title : 21321
         * img : 213
         * content : 324324
         * type : 1
         * re_marks : 32442
         * create_time : 2019-01-17 09:33:10
         * type_txt : 通知消息
         * status_txt : 未读
         */

        private int id;
        private int status;
        private String title;
        private String img;
        private String content;
        private String re_marks;
        private String create_time;
        private int goods_id;
        private int category_id;
        private String goods_name;
        private String car_img;
        private String express_no;
        private String username;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getCar_img() {
            return car_img;
        }

        public void setCar_img(String car_img) {
            this.car_img = car_img;
        }

        public String getExpress_no() {
            return express_no;
        }

        public void setExpress_no(String express_no) {
            this.express_no = express_no;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }


        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }


        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }


        public String getRe_marks() {
            return re_marks;
        }

        public void setRe_marks(String re_marks) {
            this.re_marks = re_marks;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

    }
}
