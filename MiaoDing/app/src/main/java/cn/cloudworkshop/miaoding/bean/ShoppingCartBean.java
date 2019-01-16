package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2016/10/27 09:17
 * Email：1993911441@qq.com
 * Describe：
 */
public class ShoppingCartBean {


    /**
     * code : 10000
     * pages : {"totalnum":4,"everypage":10,"totalpage":1,"page":1}
     * data : [{"cart_id":10,"goods_num":3,"price":"399.00","goods_name":"白色Easycare易打理衬衫","goods_img":"public/20181019/3760d0add196a18a1b7ab9742494138da940fc3c.png","part":[{"part_name":"面料","part_value":"\bVBC 班长会团购款"},{"part_name":"版型","part_value":"正常"},{"part_name":"领型宽","part_value":"5.5CM"},{"part_name":"下领高度","part_value":"3"},{"part_name":"领子相拼","part_value":"正常"},{"part_name":"领子样式","part_value":"尖领"},{"part_name":"领子硬度","part_value":"中"},{"part_name":"门襟","part_value":"暗门襟"},{"part_name":"口袋","part_value":"圆袋"},{"part_name":"下摆","part_value":"直摆"},{"part_name":"袖克夫","part_value":"小(6cm)"},{"part_name":"袖克夫相拼","part_value":"正常"},{"part_name":"袖口样式","part_value":"圆头一粒扣"},{"part_name":"后背省褶","part_value":"双褶"},{"part_name":"绣花位置","part_value":"无"},{"part_name":"绣花字体","part_value":"无"},{"part_name":"绣花颜色","part_value":"无"}]},{"cart_id":13,"goods_num":4,"price":"399.00","goods_name":"白色Easycare易打理衬衫","goods_img":"public/20181019/3760d0add196a18a1b7ab9742494138da940fc3c.png","part":[{"part_name":"面料","part_value":"\bVBC 班长会团购款"},{"part_name":"版型","part_value":"正常"},{"part_name":"领型宽","part_value":"5.5CM"},{"part_name":"下领高度","part_value":"3"},{"part_name":"领子相拼","part_value":"正常"},{"part_name":"领子样式","part_value":"尖领"},{"part_name":"领子硬度","part_value":"中"},{"part_name":"门襟","part_value":"明门襟"},{"part_name":"口袋","part_value":"圆袋"},{"part_name":"下摆","part_value":"直摆"},{"part_name":"袖克夫","part_value":"小(6cm)"},{"part_name":"袖克夫相拼","part_value":"正常"},{"part_name":"袖口样式","part_value":"圆头一粒扣"},{"part_name":"后背省褶","part_value":"双褶"},{"part_name":"绣花位置","part_value":"无"},{"part_name":"绣花字体","part_value":"无"},{"part_name":"绣花颜色","part_value":"无"}]},{"cart_id":21,"goods_num":1,"price":"399.00","goods_name":"白色Easycare易打理衬衫","goods_img":"public/20181019/3760d0add196a18a1b7ab9742494138da940fc3c.png","part":[{"part_name":"面料","part_value":"\bVBC 班长会团购款"},{"part_name":"版型","part_value":"正常"},{"part_name":"领型宽","part_value":"5.5CM"},{"part_name":"下领高度","part_value":"3"},{"part_name":"领子相拼","part_value":"正常"},{"part_name":"领子样式","part_value":"八字领"},{"part_name":"领子硬度","part_value":"中"},{"part_name":"门襟","part_value":"里三褶门襟"},{"part_name":"口袋","part_value":"无袋"},{"part_name":"下摆","part_value":"圆摆"},{"part_name":"袖克夫","part_value":"小(6cm)"},{"part_name":"袖克夫相拼","part_value":"正常"},{"part_name":"袖口样式","part_value":"法式斜切角袖口"},{"part_name":"后背省褶","part_value":"腰省"},{"part_name":"绣花位置","part_value":"无"},{"part_name":"绣花字体","part_value":"无"},{"part_name":"绣花颜色","part_value":"无"}]},{"cart_id":22,"goods_num":1,"price":"3999.00","goods_name":"VBC 5999 套西 上衣","goods_img":"public/20181106/f083c82df9a941541d2fc964a3edd53184bf527a.jpeg","part":[{"part_name":"面料","part_value":"VBC5999套西"},{"part_name":"版型","part_value":"正常"},{"part_name":"工艺","part_value":"半麻衬"},{"part_name":"门襟","part_value":"单排一粒扣"},{"part_name":"底摆","part_value":"圆"},{"part_name":"驳领","part_value":"平驳领7.5CM"},{"part_name":"驳头眼","part_value":"机器"},{"part_name":"胸袋","part_value":"船型"},{"part_name":"前身扣眼","part_value":"机器"},{"part_name":"面袋","part_value":"直袋+有袋盖"},{"part_name":"袖开衩","part_value":"真"},{"part_name":"袖纽扣","part_value":"叠4"},{"part_name":"袖口眼线","part_value":"顺色"},{"part_name":"后下摆","part_value":"双开衩"},{"part_name":"内里","part_value":"全里"},{"part_name":"内珠边","part_value":"无"},{"part_name":"袖里","part_value":"专业袖里"},{"part_name":"内镶边条","part_value":"无"},{"part_name":"内袋","part_value":"里袋+笔袋+烟袋"},{"part_name":"汗巾","part_value":"三角"},{"part_name":"挂面","part_value":"弯挂面"},{"part_name":"刺绣位置","part_value":"无"},{"part_name":"刺绣字体","part_value":"无"},{"part_name":"绣花颜色","part_value":"无"}]}]
     */

    private int code;
    private PagesBean pages;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public PagesBean getPages() {
        return pages;
    }

    public void setPages(PagesBean pages) {
        this.pages = pages;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class PagesBean {
        /**
         * totalnum : 4
         * everypage : 10
         * totalpage : 1
         * page : 1
         */

        private int totalnum;
        private int everypage;
        private int totalpage;
        private int page;

        public int getTotalnum() {
            return totalnum;
        }

        public void setTotalnum(int totalnum) {
            this.totalnum = totalnum;
        }

        public int getEverypage() {
            return everypage;
        }

        public void setEverypage(int everypage) {
            this.everypage = everypage;
        }

        public int getTotalpage() {
            return totalpage;
        }

        public void setTotalpage(int totalpage) {
            this.totalpage = totalpage;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }
    }

    public static class DataBean {
        /**
         * cart_id : 10
         * goods_num : 3
         * price : 399.00
         * goods_name : 白色Easycare易打理衬衫
         * goods_img : public/20181019/3760d0add196a18a1b7ab9742494138da940fc3c.png
         * part : [{"part_name":"面料","part_value":"\bVBC 班长会团购款"},{"part_name":"版型","part_value":"正常"},{"part_name":"领型宽","part_value":"5.5CM"},{"part_name":"下领高度","part_value":"3"},{"part_name":"领子相拼","part_value":"正常"},{"part_name":"领子样式","part_value":"尖领"},{"part_name":"领子硬度","part_value":"中"},{"part_name":"门襟","part_value":"暗门襟"},{"part_name":"口袋","part_value":"圆袋"},{"part_name":"下摆","part_value":"直摆"},{"part_name":"袖克夫","part_value":"小(6cm)"},{"part_name":"袖克夫相拼","part_value":"正常"},{"part_name":"袖口样式","part_value":"圆头一粒扣"},{"part_name":"后背省褶","part_value":"双褶"},{"part_name":"绣花位置","part_value":"无"},{"part_name":"绣花字体","part_value":"无"},{"part_name":"绣花颜色","part_value":"无"}]
         */

        private int cart_id;
        private int goods_num;
        private String price;
        private String goods_name;
        private String goods_img;
        private List<PartBean> part;
        private boolean isSelect = true;
        private int category_id;
        private int goods_id;

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public int getCart_id() {
            return cart_id;
        }

        public void setCart_id(int cart_id) {
            this.cart_id = cart_id;
        }

        public int getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(int goods_num) {
            this.goods_num = goods_num;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_img() {
            return goods_img;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
        }

        public List<PartBean> getPart() {
            return part;
        }

        public void setPart(List<PartBean> part) {
            this.part = part;
        }

        public static class PartBean {
            /**
             * part_name : 面料
             * part_value : VBC 班长会团购款
             */

            private String part_name;
            private String part_value;

            public String getPart_name() {
                return part_name;
            }

            public void setPart_name(String part_name) {
                this.part_name = part_name;
            }

            public String getPart_value() {
                return part_value;
            }

            public void setPart_value(String part_value) {
                this.part_value = part_value;
            }
        }
    }
}
