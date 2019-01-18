package cn.cloudworkshop.miaoding.bean;

/**
 * Author：Libin on 2016/11/10 15:25
 * Email：1993911441@qq.com
 * Describe：默认配置
 */
public class AppIndexBean {


    /**
     * code : 10000
     * data : {"version_id":1,"version_name":"version 1.0.0","description":"第一版","link":"http://api.cloudworkshop.cn/andriod/shop.1.0.0.apk"}
     * msg :
     */

    private int code;
    private DataBean data;
    private String msg;

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * version_id : 1
         * version_name : version 1.0.0
         * description : 第一版
         * link : http://api.cloudworkshop.cn/andriod/shop.1.0.0.apk
         */

        private int version_id;
        private String version_name;
        private String description;
        private String link;

        public int getVersion_id() {
            return version_id;
        }

        public void setVersion_id(int version_id) {
            this.version_id = version_id;
        }

        public String getVersion_name() {
            return version_name;
        }

        public void setVersion_name(String version_name) {
            this.version_name = version_name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}
