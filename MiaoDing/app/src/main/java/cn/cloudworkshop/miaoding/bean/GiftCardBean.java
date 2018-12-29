package cn.cloudworkshop.miaoding.bean;

/**
 * Author：Libin on 2017-09-20 17:23
 * Email：1993911441@qq.com
 * Describe：
 */
public class GiftCardBean {


    /**
     * code : 10000
     * data : {"giftcard_money":"0.00","giftcard_rule":"public/20181019/b3524ac85b60727a15ae768ddd14f3bc79a864d5.png"}
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
         * giftcard_money : 0.00
         * giftcard_rule : public/20181019/b3524ac85b60727a15ae768ddd14f3bc79a864d5.png
         */

        private String giftcard_money;
        private String giftcard_rule;

        public String getGiftcard_money() {
            return giftcard_money;
        }

        public void setGiftcard_money(String giftcard_money) {
            this.giftcard_money = giftcard_money;
        }

        public String getGiftcard_rule() {
            return giftcard_rule;
        }

        public void setGiftcard_rule(String giftcard_rule) {
            this.giftcard_rule = giftcard_rule;
        }
    }
}
