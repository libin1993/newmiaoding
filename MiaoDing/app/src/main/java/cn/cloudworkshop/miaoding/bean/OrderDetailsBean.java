package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2016/10/27 18:07
 * Email：1993911441@qq.com
 * Describe：
 */
public class OrderDetailsBean {


    /**
     * code : 10000
     * data : {"order_sn":"YGC20190114134306749291","status":1,"order_amount":"999.00","giftcard_eq_money":"0.00","ticket_reduce_money":"100.00","payable_amount":"899.00","discount":"1.00","promotions":"0.00","province":"北京市","city":"北京市","area":"朝阳区","address":"1","accept_name":"1","address_phone":"1","mobile":null,"real_freight":"0.00","pay_type":"其他","pay_time":"","create_time":"2019-01-14 13:43:06","status_text":"待付款","pay_count_down_time":0,"childOrders":[{"detail_order_sn":"YGC20190114134306749291_1","sell_price":"999.00","goods_name":"驼色棉羊绒混纺衬衫","goods_num":6,"img_info":"public/20181224/77872e452130c54cb2b1b8738f4a448dcfa043b3.jpeg","re_marks":"","part":[{"part_name":"面料","part_value":"常规款衬衣"},{"part_name":"版型","part_value":"正常"},{"part_name":"领型宽","part_value":"6CM"},{"part_name":"下领高度","part_value":"3"},{"part_name":"领子相拼","part_value":"正常"},{"part_name":"领子样式","part_value":"方开领"},{"part_name":"领子硬度","part_value":"中"},{"part_name":"门襟","part_value":"里三褶门襟"},{"part_name":"口袋","part_value":"无袋"},{"part_name":"下摆","part_value":"圆摆"},{"part_name":"袖克夫","part_value":"中(6.5cm)"},{"part_name":"袖口样式","part_value":"圆头一粒扣"},{"part_name":"绣花位置","part_value":"无"},{"part_name":"绣花字体","part_value":"无"},{"part_name":"绣花颜色","part_value":"无"}]}]}
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
         * order_sn : YGC20190114134306749291
         * status : 1
         * order_amount : 999.00
         * giftcard_eq_money : 0.00
         * ticket_reduce_money : 100.00
         * payable_amount : 899.00
         * discount : 1.00
         * promotions : 0.00
         * province : 北京市
         * city : 北京市
         * area : 朝阳区
         * address : 1
         * accept_name : 1
         * address_phone : 1
         * mobile : null
         * real_freight : 0.00
         * pay_type : 其他
         * pay_time :
         * create_time : 2019-01-14 13:43:06
         * status_text : 待付款
         * pay_count_down_time : 0
         * childOrders : [{"detail_order_sn":"YGC20190114134306749291_1","sell_price":"999.00","goods_name":"驼色棉羊绒混纺衬衫","goods_num":6,"img_info":"public/20181224/77872e452130c54cb2b1b8738f4a448dcfa043b3.jpeg","re_marks":"","part":[{"part_name":"面料","part_value":"常规款衬衣"},{"part_name":"版型","part_value":"正常"},{"part_name":"领型宽","part_value":"6CM"},{"part_name":"下领高度","part_value":"3"},{"part_name":"领子相拼","part_value":"正常"},{"part_name":"领子样式","part_value":"方开领"},{"part_name":"领子硬度","part_value":"中"},{"part_name":"门襟","part_value":"里三褶门襟"},{"part_name":"口袋","part_value":"无袋"},{"part_name":"下摆","part_value":"圆摆"},{"part_name":"袖克夫","part_value":"中(6.5cm)"},{"part_name":"袖口样式","part_value":"圆头一粒扣"},{"part_name":"绣花位置","part_value":"无"},{"part_name":"绣花字体","part_value":"无"},{"part_name":"绣花颜色","part_value":"无"}]}]
         */

        private String order_sn;
        private int status;
        private String order_amount;
        private String giftcard_eq_money;
        private String ticket_reduce_money;
        private String payable_amount;
        private String discount;
        private String promotions;
        private String province;
        private String city;
        private String area;
        private String address;
        private String accept_name;
        private String address_phone;
        private Object mobile;
        private String real_freight;
        private String pay_type;
        private String pay_time;
        private String create_time;
        private String status_text;
        private int pay_count_down_time;
        private List<ChildOrdersBean> childOrders;
        private String express_no;


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

        public String getOrder_amount() {
            return order_amount;
        }

        public void setOrder_amount(String order_amount) {
            this.order_amount = order_amount;
        }

        public String getGiftcard_eq_money() {
            return giftcard_eq_money;
        }

        public void setGiftcard_eq_money(String giftcard_eq_money) {
            this.giftcard_eq_money = giftcard_eq_money;
        }

        public String getTicket_reduce_money() {
            return ticket_reduce_money;
        }

        public void setTicket_reduce_money(String ticket_reduce_money) {
            this.ticket_reduce_money = ticket_reduce_money;
        }

        public String getPayable_amount() {
            return payable_amount;
        }

        public void setPayable_amount(String payable_amount) {
            this.payable_amount = payable_amount;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getPromotions() {
            return promotions;
        }

        public void setPromotions(String promotions) {
            this.promotions = promotions;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAccept_name() {
            return accept_name;
        }

        public void setAccept_name(String accept_name) {
            this.accept_name = accept_name;
        }

        public String getAddress_phone() {
            return address_phone;
        }

        public void setAddress_phone(String address_phone) {
            this.address_phone = address_phone;
        }

        public Object getMobile() {
            return mobile;
        }

        public void setMobile(Object mobile) {
            this.mobile = mobile;
        }

        public String getReal_freight() {
            return real_freight;
        }

        public void setReal_freight(String real_freight) {
            this.real_freight = real_freight;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getStatus_text() {
            return status_text;
        }

        public void setStatus_text(String status_text) {
            this.status_text = status_text;
        }

        public int getPay_count_down_time() {
            return pay_count_down_time;
        }

        public void setPay_count_down_time(int pay_count_down_time) {
            this.pay_count_down_time = pay_count_down_time;
        }

        public List<ChildOrdersBean> getChildOrders() {
            return childOrders;
        }

        public void setChildOrders(List<ChildOrdersBean> childOrders) {
            this.childOrders = childOrders;
        }

        public static class ChildOrdersBean {
            /**
             * detail_order_sn : YGC20190114134306749291_1
             * sell_price : 999.00
             * goods_name : 驼色棉羊绒混纺衬衫
             * goods_num : 6
             * img_info : public/20181224/77872e452130c54cb2b1b8738f4a448dcfa043b3.jpeg
             * re_marks :
             * part : [{"part_name":"面料","part_value":"常规款衬衣"},{"part_name":"版型","part_value":"正常"},{"part_name":"领型宽","part_value":"6CM"},{"part_name":"下领高度","part_value":"3"},{"part_name":"领子相拼","part_value":"正常"},{"part_name":"领子样式","part_value":"方开领"},{"part_name":"领子硬度","part_value":"中"},{"part_name":"门襟","part_value":"里三褶门襟"},{"part_name":"口袋","part_value":"无袋"},{"part_name":"下摆","part_value":"圆摆"},{"part_name":"袖克夫","part_value":"中(6.5cm)"},{"part_name":"袖口样式","part_value":"圆头一粒扣"},{"part_name":"绣花位置","part_value":"无"},{"part_name":"绣花字体","part_value":"无"},{"part_name":"绣花颜色","part_value":"无"}]
             */

            private String detail_order_sn;
            private String sell_price;
            private String goods_name;
            private int goods_num;
            private String img_info;
            private String re_marks;
            private List<PartBean> part;
            private List<SkuBean> sku;
            private int category_id;

            public int getCategory_id() {
                return category_id;
            }

            public void setCategory_id(int category_id) {
                this.category_id = category_id;
            }

            public List<SkuBean> getSku() {
                return sku;
            }

            public void setSku(List<SkuBean> sku) {
                this.sku = sku;
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

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
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

            public String getRe_marks() {
                return re_marks;
            }

            public void setRe_marks(String re_marks) {
                this.re_marks = re_marks;
            }

            public List<PartBean> getPart() {
                return part;
            }

            public void setPart(List<PartBean> part) {
                this.part = part;
            }

            public static class PartBean {
                /**
                 * part_name : 面料
                 * part_value : 常规款衬衣
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


            public static class SkuBean {
                /**
                 * part_name : 面料
                 * part_value : VBC 班长会团购款
                 */

                private String type;
                private String value;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }
        }
    }
}

