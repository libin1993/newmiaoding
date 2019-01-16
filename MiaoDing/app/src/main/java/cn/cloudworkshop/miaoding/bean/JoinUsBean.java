package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2016/10/25 17:32
 * Email：1993911441@qq.com
 * Describe：
 */
public class JoinUsBean {


    /**
     * code : 10000
     * data : {"img_list":[{"img":"public/20190115/5815012dccd7955b99587fe317f4fe4caf76102e.png","link":"设计师入住","title":"设计师招募","share_link":"","type":3,"banner_type":1,"ratio":"0.424688"}],"is_apply":0}
     * msg : 成功
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
         * img_list : [{"img":"public/20190115/5815012dccd7955b99587fe317f4fe4caf76102e.png","link":"设计师入住","title":"设计师招募","share_link":"","type":3,"banner_type":1,"ratio":"0.424688"}]
         * is_apply : 0
         */

        private int is_apply;
        private List<ImgListBean> img_list;

        public int getIs_apply() {
            return is_apply;
        }

        public void setIs_apply(int is_apply) {
            this.is_apply = is_apply;
        }

        public List<ImgListBean> getImg_list() {
            return img_list;
        }

        public void setImg_list(List<ImgListBean> img_list) {
            this.img_list = img_list;
        }

        public static class ImgListBean {
            /**
             * img : public/20190115/5815012dccd7955b99587fe317f4fe4caf76102e.png
             * link : 设计师入住
             * title : 设计师招募
             * share_link :
             * type : 3
             * banner_type : 1
             * ratio : 0.424688
             */

            private String img;
            private String link;
            private String title;
            private String share_link;
            private int type;
            private int banner_type;
            private String ratio;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getShare_link() {
                return share_link;
            }

            public void setShare_link(String share_link) {
                this.share_link = share_link;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getBanner_type() {
                return banner_type;
            }

            public void setBanner_type(int banner_type) {
                this.banner_type = banner_type;
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
