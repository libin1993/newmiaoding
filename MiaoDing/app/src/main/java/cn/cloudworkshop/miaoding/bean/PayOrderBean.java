package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2019/1/15 13:43
 * Email：1993911441@qq.com
 * Describe：
 */
public class PayOrderBean {

    /**
     * code : 10000
     * data : [{"order_sn":"YGC20190112110946226143","order_amount":"499.00","payable_amount":"499.00","ticket_reduce_money":"0.00","discount":"1.00","promotions":"0.00","real_freight":"0.00"}]
     * giftcard_money : 0.00
     */

    private int code;
    private String giftcard_money;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getGiftcard_money() {
        return giftcard_money;
    }

    public void setGiftcard_money(String giftcard_money) {
        this.giftcard_money = giftcard_money;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * order_sn : YGC20190112110946226143
         * order_amount : 499.00
         * payable_amount : 499.00
         * ticket_reduce_money : 0.00
         * discount : 1.00
         * promotions : 0.00
         * real_freight : 0.00
         */

        private String order_sn;
        private String order_amount;
        private String payable_amount;
        private String ticket_reduce_money;
        private String discount;
        private String promotions;
        private String real_freight;
        private String real_amount;

        public String getReal_amount() {
            return real_amount;
        }

        public void setReal_amount(String real_amount) {
            this.real_amount = real_amount;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getOrder_amount() {
            return order_amount;
        }

        public void setOrder_amount(String order_amount) {
            this.order_amount = order_amount;
        }

        public String getPayable_amount() {
            return payable_amount;
        }

        public void setPayable_amount(String payable_amount) {
            this.payable_amount = payable_amount;
        }

        public String getTicket_reduce_money() {
            return ticket_reduce_money;
        }

        public void setTicket_reduce_money(String ticket_reduce_money) {
            this.ticket_reduce_money = ticket_reduce_money;
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

        public String getReal_freight() {
            return real_freight;
        }

        public void setReal_freight(String real_freight) {
            this.real_freight = real_freight;
        }
    }
}
