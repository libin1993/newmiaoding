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
     * pages : {"totalnum":20,"everypage":10,"totalpage":2,"page":1}
     * data : [{"cart_id":571,"goods_img":"public/20181019/b3524ac85b60727a15ae768ddd14f3bc79a864d5.png","goods_num":10,"price":"428.00"},{"cart_id":602,"goods_img":"public/20181019/b3524ac85b60727a15ae768ddd14f3bc79a864d5.png","goods_num":7,"price":"912.00"},{"cart_id":827,"goods_img":"public/20181019/b3524ac85b60727a15ae768ddd14f3bc79a864d5.png","goods_num":7,"price":"782.00"},{"cart_id":471,"goods_img":"public/20181019/b3524ac85b60727a15ae768ddd14f3bc79a864d5.png","goods_num":2,"price":"521.00"},{"cart_id":227,"goods_img":"public/20181019/b3524ac85b60727a15ae768ddd14f3bc79a864d5.png","goods_num":4,"price":"792.00"},{"cart_id":263,"goods_img":"public/20181019/b3524ac85b60727a15ae768ddd14f3bc79a864d5.png","goods_num":3,"price":"691.00"},{"cart_id":536,"goods_img":"public/20181019/b3524ac85b60727a15ae768ddd14f3bc79a864d5.png","goods_num":2,"price":"924.00"},{"cart_id":900,"goods_img":"public/20181019/b3524ac85b60727a15ae768ddd14f3bc79a864d5.png","goods_num":10,"price":"210.00"},{"cart_id":466,"goods_img":"public/20181019/b3524ac85b60727a15ae768ddd14f3bc79a864d5.png","goods_num":2,"price":"202.00"},{"cart_id":551,"goods_img":"public/20181019/b3524ac85b60727a15ae768ddd14f3bc79a864d5.png","goods_num":5,"price":"254.00"}]
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
         * totalnum : 20
         * everypage : 10
         * totalpage : 2
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
         * cart_id : 571
         * goods_img : public/20181019/b3524ac85b60727a15ae768ddd14f3bc79a864d5.png
         * goods_num : 10
         * price : 428.00
         */

        private int cart_id;
        private String goods_img;
        private int goods_num;
        private String price;
        private boolean isSelect = true;
        private String goods_name;

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
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

        public String getGoods_img() {
            return goods_img;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
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
    }
}
