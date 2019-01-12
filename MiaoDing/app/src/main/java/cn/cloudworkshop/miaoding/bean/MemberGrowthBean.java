package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2017/2/16 12:50
 * Email：1993911441@qq.com
 * Describe：
 */
public class MemberGrowthBean {


    /**
     * code : 10000
     * data : {"page":{"totalnum":8,"everypage":10,"totalpage":1,"page":1},"data":[{"credit":"8.00","create_time":"2019-01-11 10:04:06","type_name":"购物"},{"credit":"7.00","create_time":"2019-01-11 10:04:06","type_name":"购物"},{"credit":"6.00","create_time":"2019-01-11 10:04:06","type_name":"购物"},{"credit":"5.00","create_time":"2019-01-11 10:04:06","type_name":"购物"},{"credit":"4.00","create_time":"2019-01-11 10:04:06","type_name":"购物"},{"credit":"3.00","create_time":"2019-01-11 10:04:06","type_name":"购物"},{"credit":"2.00","create_time":"2019-01-11 10:04:06","type_name":"购物"},{"credit":"1.00","create_time":"2019-01-11 10:04:06","type_name":"购物"}]}
     * msg : 成功
     */

    private int code;
    private DataBeanX data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBeanX {
        /**
         * page : {"totalnum":8,"everypage":10,"totalpage":1,"page":1}
         * data : [{"credit":"8.00","create_time":"2019-01-11 10:04:06","type_name":"购物"},{"credit":"7.00","create_time":"2019-01-11 10:04:06","type_name":"购物"},{"credit":"6.00","create_time":"2019-01-11 10:04:06","type_name":"购物"},{"credit":"5.00","create_time":"2019-01-11 10:04:06","type_name":"购物"},{"credit":"4.00","create_time":"2019-01-11 10:04:06","type_name":"购物"},{"credit":"3.00","create_time":"2019-01-11 10:04:06","type_name":"购物"},{"credit":"2.00","create_time":"2019-01-11 10:04:06","type_name":"购物"},{"credit":"1.00","create_time":"2019-01-11 10:04:06","type_name":"购物"}]
         */

        private PageBean page;
        private List<DataBean> data;

        public PageBean getPage() {
            return page;
        }

        public void setPage(PageBean page) {
            this.page = page;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class PageBean {
            /**
             * totalnum : 8
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
             * credit : 8.00
             * create_time : 2019-01-11 10:04:06
             * type_name : 购物
             */

            private String credit;
            private String create_time;
            private String type_name;

            public String getCredit() {
                return credit;
            }

            public void setCredit(String credit) {
                this.credit = credit;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }
        }
    }
}
