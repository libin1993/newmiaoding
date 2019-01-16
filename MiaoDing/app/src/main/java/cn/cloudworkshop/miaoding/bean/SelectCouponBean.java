package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2016/12/19 14:27
 * Email：1993911441@qq.com
 * Describe：
 */
public class SelectCouponBean {


    /**
     * code : 10000
     * data : {"valid_tickets":[],"invalid_tickets":[{"id":38,"type":2,"title":"新用户注册专享优惠券","sub_title":"新用户注册专享优惠券","money":"100.00","kouling":"20181221n5p8jJ9R","s_time":"2018-12-25 18:03:39","e_time":"2019-03-25 18:03:39","use_scope":4,"no_use_goodsid":"","use_goodsid":"4","use_rule":"","ticket_img":"","rule_img":"public/20181026/a54a0280aee8384ba577af8b487c12f70728c010.png|750|1334","rule_words":"","ticket_no":"20181221","status":"优惠券满500.00后可用","full_money":"500.00","exchange_code":"20181221n5p8jJ9R","has_use":0,"order_sn":null},{"id":32,"type":2,"title":"新用户注册专享优惠券","sub_title":"新用户注册专享优惠券","money":"100.00","kouling":"20181221VfOKQtT7","s_time":"2018-12-24 16:21:53","e_time":"2019-03-24 16:21:53","use_scope":4,"no_use_goodsid":"","use_goodsid":"4","use_rule":"","ticket_img":"","rule_img":"public/20181026/a54a0280aee8384ba577af8b487c12f70728c010.png|750|1334","rule_words":"","ticket_no":"20181221","status":"优惠券满500.00后可用","full_money":"500.00","exchange_code":"20181221VfOKQtT7","has_use":0,"order_sn":null},{"id":29,"type":2,"title":"新用户注册专享优惠券","sub_title":"新用户注册专享优惠券","money":"100.00","kouling":"20181221QauPq9B6","s_time":"2018-12-24 16:05:40","e_time":"2019-03-24 16:05:40","use_scope":4,"no_use_goodsid":"","use_goodsid":"4","use_rule":"","ticket_img":"","rule_img":"public/20181026/a54a0280aee8384ba577af8b487c12f70728c010.png|750|1334","rule_words":"","ticket_no":"20181221","status":"优惠券满500.00后可用","full_money":"500.00","exchange_code":"20181221QauPq9B6","has_use":0,"order_sn":null}]}
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
        private List<ValidTicketsBean> valid_tickets;
        private List<InvalidTicketsBean> invalid_tickets;

        public List<ValidTicketsBean> getValid_tickets() {
            return valid_tickets;
        }

        public void setValid_tickets(List<ValidTicketsBean> valid_tickets) {
            this.valid_tickets = valid_tickets;
        }

        public List<InvalidTicketsBean> getInvalid_tickets() {
            return invalid_tickets;
        }

        public void setInvalid_tickets(List<InvalidTicketsBean> invalid_tickets) {
            this.invalid_tickets = invalid_tickets;
        }

        public static class ValidTicketsBean {
            private int id;
            private String title;
            private String sub_title;
            private String money;
            private String s_time;
            private String e_time;
            private String full_money;
            private String re_marks;
            private String cart_id_s;

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

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getS_time() {
                return s_time;
            }

            public void setS_time(String s_time) {
                this.s_time = s_time;
            }

            public String getE_time() {
                return e_time;
            }

            public void setE_time(String e_time) {
                this.e_time = e_time;
            }

            public String getFull_money() {
                return full_money;
            }

            public void setFull_money(String full_money) {
                this.full_money = full_money;
            }

            public String getRe_marks() {
                return re_marks;
            }

            public void setRe_marks(String re_marks) {
                this.re_marks = re_marks;
            }

            public String getCart_id_s() {
                return cart_id_s;
            }

            public void setCart_id_s(String cart_id_s) {
                this.cart_id_s = cart_id_s;
            }
        }

        public static class InvalidTicketsBean {
            private int id;
            private String title;
            private String sub_title;
            private String money;
            private String s_time;
            private String e_time;
            private String full_money;
            private String re_marks;
            private String cart_ids;

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

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getS_time() {
                return s_time;
            }

            public void setS_time(String s_time) {
                this.s_time = s_time;
            }

            public String getE_time() {
                return e_time;
            }

            public void setE_time(String e_time) {
                this.e_time = e_time;
            }

            public String getFull_money() {
                return full_money;
            }

            public void setFull_money(String full_money) {
                this.full_money = full_money;
            }

            public String getRe_marks() {
                return re_marks;
            }

            public void setRe_marks(String re_marks) {
                this.re_marks = re_marks;
            }

            public String getCart_ids() {
                return cart_ids;
            }

            public void setCart_ids(String cart_ids) {
                this.cart_ids = cart_ids;
            }
        }
    }
}
