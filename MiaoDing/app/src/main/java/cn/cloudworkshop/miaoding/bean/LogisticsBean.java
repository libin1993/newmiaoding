package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2016/11/18 08:27
 * Email：1993911441@qq.com
 * Describe：
 */
public class LogisticsBean {


    /**
     * code : 10000
     * msg :
     * data : [{"time":"2018-12-13 09:31:00","context":"[哈尔滨市] 已签收,感谢使用顺丰,期待再次为您服务"},{"time":"2018-12-13 07:48:00","context":"[哈尔滨市] 【哈尔滨市】哈尔滨市松北区万达城营业点派件员 顺丰速运 95338正在为您派件"},{"time":"2018-12-12 19:45:00","context":"[哈尔滨市] 快件到达 【哈尔滨市松北区万达城营业点】"},{"time":"2018-12-12 16:08:00","context":"[哈尔滨市] 快件在【哈尔滨哈平集散中心】已装车,准备发往下一站"},{"time":"2018-12-12 13:11:00","context":"[哈尔滨市] 快件到达 【哈尔滨哈平集散中心】"},{"time":"2018-12-12 02:47:00","context":"[杭州市] 快件在【杭州总集散中心】已装车,准备发往 【哈尔滨哈平集散中心】"},{"time":"2018-12-12 02:37:00","context":"[杭州市] 快件到达 【杭州总集散中心】"},{"time":"2018-12-11 22:44:00","context":"[武汉市] 快件在【武汉总集散中心】已装车,准备发往 【杭州总集散中心】"},{"time":"2018-12-11 21:32:00","context":"[武汉市] 快件到达 【武汉总集散中心】"},{"time":"2018-12-11 20:38:00","context":"[武汉市] 快件在【武汉吴家山集散中心】已装车,准备发往 【武汉总集散中心】"},{"time":"2018-12-11 20:29:00","context":"[武汉市] 快件到达 【武汉吴家山集散中心】"},{"time":"2018-12-11 18:52:00","context":"[武汉市] 快件在【武汉东湖高新技术开发区流芳营业点】已装车,准备发往 【武汉吴家山集散中心】"},{"time":"2018-12-11 12:48:00","context":"[武汉市] 顺丰速运 已收取快件"},{"time":"2018-12-11 12:28:59","context":"[武汉市] 包裹正在等待揽收"}]
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
         * time : 2018-12-13 09:31:00
         * context : [哈尔滨市] 已签收,感谢使用顺丰,期待再次为您服务
         */

        private String time;
        private String context;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }
    }
}
