package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2019/1/18 13:38
 * Email：1993911441@qq.com
 * Describe：
 */
public class AccentDetailBean {

    /**
     * code : 10000
     * data : {"id":24,"name":"测试成品1","content":"测试成品1","goods_no":"YGCFNMH35d783353","sell_price":"100.00","moren_fabric_id":"12","up_time":"2019-01-18 11:49:13","ad_img":[{"img":"public/20190118/8ac70fd3006cd53d8adc5622da816dca69344ebc.png","ratio":"1.000000"}],"img_info":[{"img":"public/20190118/ff351e53011babda0c6bb80d8bcc1bd4135951ac.png","ratio":"1.000000"}],"moren_part_id":"4,5,6,9,10","category_id":2,"sku":[{"type":"尺码","sku":[{"id":1,"name":"M"},{"id":2,"name":"L"},{"id":3,"name":"XL"},{"id":1,"name":"M"},{"id":2,"name":"L"},{"id":3,"name":"XL"}]},{"type":"颜色","sku":[{"id":4,"name":"黑色"},{"id":4,"name":"黑色"},{"id":5,"name":"白色"}]}],"collect_num":1,"collect_list":[{"uid":2096,"username":"哈哈哈哈","head_ico":"public/20190109/45e88afbc08cad637b7332eacb7d048f417b7cf5.jpeg"}]}
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

    public static class
    DataBean {
        /**
         * id : 24
         * name : 测试成品1
         * content : 测试成品1
         * goods_no : YGCFNMH35d783353
         * sell_price : 100.00
         * moren_fabric_id : 12
         * up_time : 2019-01-18 11:49:13
         * ad_img : [{"img":"public/20190118/8ac70fd3006cd53d8adc5622da816dca69344ebc.png","ratio":"1.000000"}]
         * img_info : [{"img":"public/20190118/ff351e53011babda0c6bb80d8bcc1bd4135951ac.png","ratio":"1.000000"}]
         * moren_part_id : 4,5,6,9,10
         * category_id : 2
         * sku : [{"type":"尺码","sku":[{"id":1,"name":"M"},{"id":2,"name":"L"},{"id":3,"name":"XL"},{"id":1,"name":"M"},{"id":2,"name":"L"},{"id":3,"name":"XL"}]},{"type":"颜色","sku":[{"id":4,"name":"黑色"},{"id":4,"name":"黑色"},{"id":5,"name":"白色"}]}]
         * collect_num : 1
         * collect_list : [{"uid":2096,"username":"哈哈哈哈","head_ico":"public/20190109/45e88afbc08cad637b7332eacb7d048f417b7cf5.jpeg"}]
         */

        private int id;
        private String name;
        private String content;
        private String goods_no;
        private String sell_price;
        private String moren_fabric_id;
        private String up_time;
        private String moren_part_id;
        private int category_id;
        private int collect_num;
        private List<AdImgBean> ad_img;
        private List<SkuBeanX> sku;
        private List<CollectListBean> collect_list;
        private String detail;

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getMoren_fabric_id() {
            return moren_fabric_id;
        }

        public void setMoren_fabric_id(String moren_fabric_id) {
            this.moren_fabric_id = moren_fabric_id;
        }

        public String getUp_time() {
            return up_time;
        }

        public void setUp_time(String up_time) {
            this.up_time = up_time;
        }

        public String getMoren_part_id() {
            return moren_part_id;
        }

        public void setMoren_part_id(String moren_part_id) {
            this.moren_part_id = moren_part_id;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public int getCollect_num() {
            return collect_num;
        }

        public void setCollect_num(int collect_num) {
            this.collect_num = collect_num;
        }

        public List<AdImgBean> getAd_img() {
            return ad_img;
        }

        public void setAd_img(List<AdImgBean> ad_img) {
            this.ad_img = ad_img;
        }


        public List<SkuBeanX> getSku() {
            return sku;
        }

        public void setSku(List<SkuBeanX> sku) {
            this.sku = sku;
        }

        public List<CollectListBean> getCollect_list() {
            return collect_list;
        }

        public void setCollect_list(List<CollectListBean> collect_list) {
            this.collect_list = collect_list;
        }

        public static class AdImgBean {
            /**
             * img : public/20190118/8ac70fd3006cd53d8adc5622da816dca69344ebc.png
             * ratio : 1.000000
             */

            private String img;
            private String ratio;
            private String desc;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

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


        public static class SkuBeanX {
            /**
             * type : 尺码
             * sku : [{"id":1,"name":"M"},{"id":2,"name":"L"},{"id":3,"name":"XL"},{"id":1,"name":"M"},{"id":2,"name":"L"},{"id":3,"name":"XL"}]
             */

            private String type;
            private List<SkuBean> sku;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<SkuBean> getSku() {
                return sku;
            }

            public void setSku(List<SkuBean> sku) {
                this.sku = sku;
            }

            public static class SkuBean {
                /**
                 * id : 1
                 * name : M
                 */

                private int id;
                private String name;
                private String img;
                private boolean isDefault;

                public boolean isDefault() {
                    return isDefault;
                }

                public void setDefault(boolean aDefault) {
                    isDefault = aDefault;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
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
            }
        }

        public static class CollectListBean {
            /**
             * uid : 2096
             * username : 哈哈哈哈
             * head_ico : public/20190109/45e88afbc08cad637b7332eacb7d048f417b7cf5.jpeg
             */

            private int uid;
            private String username;
            private String head_ico;

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

            public String getHead_ico() {
                return head_ico;
            }

            public void setHead_ico(String head_ico) {
                this.head_ico = head_ico;
            }
        }
    }
}
