package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2017/1/2 17:53
 * Email：1993911441@qq.com
 * Describe：
 */
public class MsgCenterBean {

    /**
     * code : 1
     * data : [{"id":1,"name":"系统消息","img":"/uploads/img/2017010217040957524810.png","last_msg":{"title":"消息01","is_read":0,"c_time":1483339033}},{"id":2,"name":"活动信息","img":"/uploads/img/2017010217043450489810.png","last_msg":null},{"id":3,"name":"其它信息","img":"/uploads/img/2017010217044949545797.png","last_msg":null}]
     * msg : 成功
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : 系统消息
         * img : /uploads/img/2017010217040957524810.png
         * last_msg : {"title":"消息01","is_read":0,"c_time":1483339033}
         */

        private String name;
        private String img;
        private int type;
        private int unread_message_num;

        public int getUnread_message_num() {
            return unread_message_num;
        }

        public void setUnread_message_num(int unread_message_num) {
            this.unread_message_num = unread_message_num;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

    }
}
