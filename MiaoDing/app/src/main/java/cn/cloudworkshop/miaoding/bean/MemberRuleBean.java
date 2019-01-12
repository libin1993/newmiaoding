package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2017/2/16 11:05
 * Email：1993911441@qq.com
 * Describe：
 */
public class MemberRuleBean {


    /**
     * code : 10000
     * data : [{"id":1,"name":"会员等级","img_list":[{"img":"public/20190110/652c1115a2af5eebe785fe499c5e7cbddf9eeefd.jpeg","ratio":"0.635294"}]},{"id":2,"name":"会员特权","img_list":[{"img":"public/20190110/eb4fba40d6276b97d292914b51d4c4d116603629.jpeg","ratio":"0.427722"}]},{"id":3,"name":"会员成长值","img_list":[{"img":"public/20190110/d03151f309e38ea9261f141a0cf7f7ba23ad02e0.jpeg","ratio":"0.635294"}]}]
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
         * name : 会员等级
         * img_list : [{"img":"public/20190110/652c1115a2af5eebe785fe499c5e7cbddf9eeefd.jpeg","ratio":"0.635294"}]
         */

        private int id;
        private String name;
        private List<ImgListBean> img_list;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ImgListBean> getImg_list() {
            return img_list;
        }

        public void setImg_list(List<ImgListBean> img_list) {
            this.img_list = img_list;
        }

        public static class ImgListBean {
            /**
             * img : public/20190110/652c1115a2af5eebe785fe499c5e7cbddf9eeefd.jpeg
             * ratio : 0.635294
             */

            private String img;
            private String ratio;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getRatio() {
                return ratio;
            }

            public void setRatio(String ratio) {
                this.ratio = ratio;
            }
        }
    }
}
