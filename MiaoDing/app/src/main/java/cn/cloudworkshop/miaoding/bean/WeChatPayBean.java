package cn.cloudworkshop.miaoding.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Author：Libin on 2016/11/21 14:09
 * Email：1993911441@qq.com
 * Describe：
 */
public class WeChatPayBean implements Serializable{


    /**
     * code : 10000
     * msg : 获取成功
     * data : {"appid":"wx07c2173e7686741e","noncestr":"CeI3qlUyGhJvNQjhIqV8HZJmDxOgRoFm","package":"Sign=WXPay","partnerid":"1396426002","prepayid":"wx101531363710014b953883f81530715497","timestamp":1547105499,"sign":"8FF36AF7BE1F863C33451BE08C7B46BB"}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * appid : wx07c2173e7686741e
         * noncestr : CeI3qlUyGhJvNQjhIqV8HZJmDxOgRoFm
         * package : Sign=WXPay
         * partnerid : 1396426002
         * prepayid : wx101531363710014b953883f81530715497
         * timestamp : 1547105499
         * sign : 8FF36AF7BE1F863C33451BE08C7B46BB
         */

        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private int timestamp;
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
