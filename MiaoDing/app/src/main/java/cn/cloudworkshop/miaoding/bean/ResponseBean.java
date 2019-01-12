package cn.cloudworkshop.miaoding.bean;

/**
 * Author：Libin on 2019/1/11 15:10
 * Email：1993911441@qq.com
 * Describe：
 */
public class ResponseBean {
    private int code;

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

    private String msg;
}
