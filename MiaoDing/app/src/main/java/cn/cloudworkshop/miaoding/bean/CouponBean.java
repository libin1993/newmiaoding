package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2016/12/19 09:58
 * Email：1993911441@qq.com
 * Describe：
 */
public class CouponBean {

    /**
     * code : 1
     * data : [{"id":3,"uid":18,"title":"满50减30","sub_title":"满50减30","ticket_no":"2016121946531","money":null,"ticket_id":1,"status":1,"c_time":1482111896,"s_time":1482111073,"e_time":1483113600}]
     * msg : 成功
     */

    private int code;
    private String msg;
    private List<TicketsBean> tickets;

    public List<TicketsBean> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketsBean> tickets) {
        this.tickets = tickets;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public static class TicketsBean {
        /**
         * id : 3
         * uid : 18
         * title : 满50减30
         * sub_title : 满50减30
         * ticket_no : 2016121946531
         * money : null
         * ticket_id : 1
         * status : 1
         * c_time : 1482111896
         * s_time : 1482111073
         * e_time : 1483113600
         */

        private int id;
        private String title;

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

        private String sub_title;
        private String money;
        private String s_time;
        private String e_time;
        private String re_marks;
        private String full_money;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRe_marks() {
            return re_marks;
        }

        public void setRe_marks(String re_marks) {
            this.re_marks = re_marks;
        }

        public String getFull_money() {
            return full_money;
        }

        public void setFull_money(String full_money) {
            this.full_money = full_money;
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


    }
}
