package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2016/10/27 16:10
 * Email：1993911441@qq.com
 * Describe：
 */
public class OrderInfoBean {


    /**
     * code : 10000
     * pages : {"totalnum":1,"everypage":10,"totalpage":1,"page":1}
     * data : [{"order_sn":"YGC20190112133254849161","status":7,"status_text":"其他","childOrders":[{"detail_order_sn":"YGC20190112133254849161_1","sell_price":"399.00","goods_num":1,"img_info":"public/20181215/634c6affffb0c1556b3c28b1ca724fcd414bbd77.jpeg"}]}]
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
         * order_sn : YGC20190112133254849161
         * status : 7
         * status_text : 其他
         * childOrders : [{"detail_order_sn":"YGC20190112133254849161_1","sell_price":"399.00","goods_num":1,"img_info":"public/20181215/634c6affffb0c1556b3c28b1ca724fcd414bbd77.jpeg"}]
         */

        private String order_sn;
        private int status;
        private String status_text;
        private List<ChildOrdersBean> childOrders;
        private String express_no;
        private String payable_amount;

        public String getPayable_amount() {
            return payable_amount;
        }

        public void setPayable_amount(String payable_amount) {
            this.payable_amount = payable_amount;
        }

        public String getExpress_no() {
            return express_no;
        }

        public void setExpress_no(String express_no) {
            this.express_no = express_no;
        }

        public String getOrder_sn() {

            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getStatus_text() {
            return status_text;
        }

        public void setStatus_text(String status_text) {
            this.status_text = status_text;
        }

        public List<ChildOrdersBean> getChildOrders() {
            return childOrders;
        }

        public void setChildOrders(List<ChildOrdersBean> childOrders) {
            this.childOrders = childOrders;
        }

        public static class ChildOrdersBean {
            /**
             * detail_order_sn : YGC20190112133254849161_1
             * sell_price : 399.00
             * goods_num : 1
             * img_info : public/20181215/634c6affffb0c1556b3c28b1ca724fcd414bbd77.jpeg
             */

            private String detail_order_sn;
            private String sell_price;
            private int goods_num;
            private String img_info;
            private String goods_name;

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            private List<PartBean> part;

            public List<PartBean> getPart() {
                return part;
            }

            public void setPart(List<PartBean> part) {
                this.part = part;
            }


            public String getDetail_order_sn() {
                return detail_order_sn;
            }

            public void setDetail_order_sn(String detail_order_sn) {
                this.detail_order_sn = detail_order_sn;
            }

            public String getSell_price() {
                return sell_price;
            }

            public void setSell_price(String sell_price) {
                this.sell_price = sell_price;
            }

            public int getGoods_num() {
                return goods_num;
            }

            public void setGoods_num(int goods_num) {
                this.goods_num = goods_num;
            }

            public String getImg_info() {
                return img_info;
            }

            public void setImg_info(String img_info) {
                this.img_info = img_info;
            }


            public static class PartBean {
                /**
                 * part_name : 面料
                 * part_value : VBC 班长会团购款
                 */

                private String part_name;
                private String part_value;

                public String getPart_name() {
                    return part_name;
                }

                public void setPart_name(String part_name) {
                    this.part_name = part_name;
                }

                public String getPart_value() {
                    return part_value;
                }

                public void setPart_value(String part_value) {
                    this.part_value = part_value;
                }
            }
        }
    }
}
