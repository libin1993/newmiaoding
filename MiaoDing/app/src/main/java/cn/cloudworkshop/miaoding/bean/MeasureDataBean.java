package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2018/4/25 12:49
 * Email：1993911441@qq.com
 * Describe：
 */
public class MeasureDataBean {


    /**
     * code : 10000
     * pages : {"totalnum":1,"everypage":10,"totalpage":1,"page":1}
     * data : [{"id":162,"name":"","height":0,"weight":0,"is_default":0}]
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
         * id : 162
         * name :
         * height : 0
         * weight : 0
         * is_default : 0
         */

        private int id;
        private String name;
        private double height;
        private double weight;
        private int is_default;

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

        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public int getIs_default() {
            return is_default;
        }

        public void setIs_default(int is_default) {
            this.is_default = is_default;
        }
    }
}
