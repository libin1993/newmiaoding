package cn.cloudworkshop.miaoding.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author：Libin on 2019/1/4 15:28
 * Email：1993911441@qq.com
 * Describe：
 */
public class CustomizedGoodsBean {

    /**
     * code : 10000
     * data : {"id":1,"name":"白色Easycare易打理衬衫","goods_no":"YGCMNRWa7d917093","sell_price":"399.00","up_time":"2018-10-19 10:44:53","ad_img":[{"img":"public/20181019/63dec0863a01e2398b7b892399776a66631668af.png","ratio":"0.8047"}],"img_info":[{"img":"public/20181215/634c6affffb0c1556b3c28b1ca724fcd414bbd77.jpeg","ratio":"0.5561"},{"img":"public/20181215/1b2e1c981e039b005b6382bd1d6e56999faf49b8.jpeg","ratio":"1.4835"},{"img":"public/20181215/dc1d41962b77462a9846643d7f05ab8871a94aa2.jpeg","ratio":"1.0546"},{"img":"public/20181215/9473563564e1561eee5dfb8d7c51acebd75a7e2d.jpeg","ratio":"1.4080"},{"img":"public/20181215/f4caf2920fde9d7f670444d3b9667e463da137ef.jpeg","ratio":"0.9747"},{"img":"public/20181215/db115449a28cb1a9f64708abba6c28625c7173ef.jpeg","ratio":"2.5899"},{"img":"public/20181215/9a1bb9c9d74bdaf646448b9b239ee1323e67ee8f.jpeg","ratio":"1.5147"},{"img":"public/20181215/86e91ad7129fd4e39ea12447aba1c9747af3f9c1.jpeg","ratio":"1.3740"},{"img":"public/20181215/76910ac7295aed113c6ebbfd19749f86fed94917.jpeg","ratio":"1.3989"},{"img":"public/20181215/34254623003ed2e6bb6a7c3bf50525a70c881b10.jpeg","ratio":"4.2857"},{"img":"public/20181215/7fdcca8d4e787d9a46ec0530adf2376b0c6b578f.jpeg","ratio":"0.7970"},{"img":"public/20181215/824057888a89c924b317c90939e797011e89c09e.jpeg","ratio":"1.4419"},{"img":"public/20181215/1aca15690abe8e7c47d83dde693f8a6a00663a03.jpeg","ratio":"1.4400"},{"img":"public/20181215/c1e0c561efae70809b1e615bd45c2a47af4d52af.jpeg","ratio":"1.9148"},{"img":"public/20181215/79c7b103cc873e69680c7b346f3da351e7172751.jpeg","ratio":"1.4979"},{"img":"public/20181215/89d525a983b794f2a368dd9f9357672fa9184824.jpeg","ratio":"1.5472"}],"series":{"id":"4","name":"衬衫"},"fabric":[{"id":"1","name":"\bVBC 班长会团购款"}],"category":[{"id":1,"name":"定制商品"}]}
     */

    private int code;
    private DataBean data;

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

    public static class DataBean {
        /**
         * id : 1
         * name : 白色Easycare易打理衬衫
         * goods_no : YGCMNRWa7d917093
         * sell_price : 399.00
         * up_time : 2018-10-19 10:44:53
         * ad_img : [{"img":"public/20181019/63dec0863a01e2398b7b892399776a66631668af.png","ratio":"0.8047"}]
         * img_info : [{"img":"public/20181215/634c6affffb0c1556b3c28b1ca724fcd414bbd77.jpeg","ratio":"0.5561"},{"img":"public/20181215/1b2e1c981e039b005b6382bd1d6e56999faf49b8.jpeg","ratio":"1.4835"},{"img":"public/20181215/dc1d41962b77462a9846643d7f05ab8871a94aa2.jpeg","ratio":"1.0546"},{"img":"public/20181215/9473563564e1561eee5dfb8d7c51acebd75a7e2d.jpeg","ratio":"1.4080"},{"img":"public/20181215/f4caf2920fde9d7f670444d3b9667e463da137ef.jpeg","ratio":"0.9747"},{"img":"public/20181215/db115449a28cb1a9f64708abba6c28625c7173ef.jpeg","ratio":"2.5899"},{"img":"public/20181215/9a1bb9c9d74bdaf646448b9b239ee1323e67ee8f.jpeg","ratio":"1.5147"},{"img":"public/20181215/86e91ad7129fd4e39ea12447aba1c9747af3f9c1.jpeg","ratio":"1.3740"},{"img":"public/20181215/76910ac7295aed113c6ebbfd19749f86fed94917.jpeg","ratio":"1.3989"},{"img":"public/20181215/34254623003ed2e6bb6a7c3bf50525a70c881b10.jpeg","ratio":"4.2857"},{"img":"public/20181215/7fdcca8d4e787d9a46ec0530adf2376b0c6b578f.jpeg","ratio":"0.7970"},{"img":"public/20181215/824057888a89c924b317c90939e797011e89c09e.jpeg","ratio":"1.4419"},{"img":"public/20181215/1aca15690abe8e7c47d83dde693f8a6a00663a03.jpeg","ratio":"1.4400"},{"img":"public/20181215/c1e0c561efae70809b1e615bd45c2a47af4d52af.jpeg","ratio":"1.9148"},{"img":"public/20181215/79c7b103cc873e69680c7b346f3da351e7172751.jpeg","ratio":"1.4979"},{"img":"public/20181215/89d525a983b794f2a368dd9f9357672fa9184824.jpeg","ratio":"1.5472"}]
         * series : {"id":"4","name":"衬衫"}
         * fabric : [{"id":"1","name":"\bVBC 班长会团购款"}]
         * category : [{"id":1,"name":"定制商品"}]
         */

        private int id;
        private String name;
        private String goods_no;
        private String sell_price;
        private String up_time;
        private String series;
        private List<AdImgBean> ad_img;
        private List<ImgInfoBean> img_info;

        private int collect_num;
        private List<CollectListBean> collect_list;
        private String content;
        private int is_collect;
        private String part_id;
        private String moren_part_id;

        public void setSeries(String series) {
            this.series = series;
        }

        public String getMoren_part_id() {
            return moren_part_id;
        }

        public void setMoren_part_id(String moren_part_id) {
            this.moren_part_id = moren_part_id;
        }

        public String getPart_id() {
            return part_id;
        }

        public void setPart_id(String part_id) {
            this.part_id = part_id;
        }

        public int getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(int is_collect) {
            this.is_collect = is_collect;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<CollectListBean> getCollect_list() {
            return collect_list;
        }

        public void setCollect_list(List<CollectListBean> collect_list) {
            this.collect_list = collect_list;
        }

        public int getCollect_num() {

            return collect_num;
        }

        public void setCollect_num(int collect_num) {
            this.collect_num = collect_num;
        }

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

        public String getGoods_no() {
            return goods_no;
        }

        public void setGoods_no(String goods_no) {
            this.goods_no = goods_no;
        }

        public String getSell_price() {
            return sell_price;
        }

        public void setSell_price(String sell_price) {
            this.sell_price = sell_price;
        }

        public String getUp_time() {
            return up_time;
        }

        public void setUp_time(String up_time) {
            this.up_time = up_time;
        }


        public List<AdImgBean> getAd_img() {
            return ad_img;
        }

        public void setAd_img(List<AdImgBean> ad_img) {
            this.ad_img = ad_img;
        }

        public List<ImgInfoBean> getImg_info() {
            return img_info;
        }

        public void setImg_info(List<ImgInfoBean> img_info) {
            this.img_info = img_info;
        }


        public static class CollectListBean {
            private String head_ico;
            private int uid;
            private String username;

            public String getHead_ico() {
                return head_ico;
            }

            public void setHead_ico(String head_ico) {
                this.head_ico = head_ico;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }

        public static class AdImgBean {
            /**
             * img : public/20181019/63dec0863a01e2398b7b892399776a66631668af.png
             * ratio : 0.8047
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

        public static class ImgInfoBean implements Serializable {
            /**
             * img : public/20181215/634c6affffb0c1556b3c28b1ca724fcd414bbd77.jpeg
             * ratio : 0.5561
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
