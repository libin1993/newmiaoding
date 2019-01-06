package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2017-06-22 10:53
 * Email：1993911441@qq.com
 * Describe：
 */
public class AppIconBean {


    /**
     * code : 10000
     * tab : [{"id":10,"name":"首页","img":"/public/20190102/6f64e8332b335421f73930f148671b135b1e4682.png","select_img":"/uploads/index/11.png"},{"id":11,"name":"定制","img":"/public/20190102/42880f96c5d8b81b1b58164d6ffc7e55e6cf4aa7.png","select_img":"/uploads/index/22.png"},{"id":12,"name":"腔调","img":"/public/20190102/93afdcfeefc154d02fdda8fba3c185dfa165505c.png","select_img":"/uploads/index/33.png"},{"id":13,"name":"我","img":"/public/20190102/ee24e5e0bbf49c13baedfb7c3de0a81990bd2b86.png","select_img":"/uploads/index/44.png"}]
     */

    private int code;
    private List<TabBean> tab;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<TabBean> getTab() {
        return tab;
    }

    public void setTab(List<TabBean> tab) {
        this.tab = tab;
    }

    public static class TabBean {
        /**
         * id : 10
         * name : 首页
         * img : /public/20190102/6f64e8332b335421f73930f148671b135b1e4682.png
         * select_img : /uploads/index/11.png
         */

        private int id;
        private String name;
        private String img;
        private String select_img;

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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getSelect_img() {
            return select_img;
        }

        public void setSelect_img(String select_img) {
            this.select_img = select_img;
        }
    }
}
