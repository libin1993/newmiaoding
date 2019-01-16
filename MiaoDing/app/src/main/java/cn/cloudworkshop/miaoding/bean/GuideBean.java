package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2017/2/8 13:53
 * Email：1993911441@qq.com
 * Describe：
 */
public class GuideBean {


    /**
     * code : 10000
     * data : {"name":"定制","img_urls":[{"img":"public/20190115/4b4d5e072b61c4fb90a743bb2f63105d12b59f4c.png","ratio":"0.562500"},{"img":"public/20190115/a68b49b944b0a60f3ddc864f26fa9944cc57dfe0.png","ratio":"0.580021"}]}
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
         * name : 定制
         * img_urls : [{"img":"public/20190115/4b4d5e072b61c4fb90a743bb2f63105d12b59f4c.png","ratio":"0.562500"},{"img":"public/20190115/a68b49b944b0a60f3ddc864f26fa9944cc57dfe0.png","ratio":"0.580021"}]
         */

        private String name;
        private List<ImgUrlsBean> img_urls;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ImgUrlsBean> getImg_urls() {
            return img_urls;
        }

        public void setImg_urls(List<ImgUrlsBean> img_urls) {
            this.img_urls = img_urls;
        }

        public static class ImgUrlsBean {
            /**
             * img : public/20190115/4b4d5e072b61c4fb90a743bb2f63105d12b59f4c.png
             * ratio : 0.562500
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
