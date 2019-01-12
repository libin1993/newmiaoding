package cn.cloudworkshop.miaoding.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author：Libin on 2016/10/26 14:51
 * Email：1993911441@qq.com
 * Describe：
 */
public class ConfirmOrderBean implements Serializable{


    /**
     * code : 1000
     * data : {"car_list":[{"id":4,"goods_num":3,"goods_id":1,"sell_price":"399.00","goods_name":"白色Easycare易打理衬衫","img_info":"public/20181215/634c6affffb0c1556b3c28b1ca724fcd414bbd77.jpeg"},{"id":5,"goods_num":1,"goods_id":1,"sell_price":"399.00","goods_name":"白色Easycare易打理衬衫","img_info":"public/20181215/634c6affffb0c1556b3c28b1ca724fcd414bbd77.jpeg"}],"address_list":{"id":1,"accept_name":"韦鹏","zcode":"","phone":"13916905124","country":"中国","province":"浙江省","city":"杭州市","area":"下城区","address":"星城发展大厦2-1203"},"lt_arr":{"id":1,"name":null,"height":0,"weight":0}}
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
         * car_list : [{"id":4,"goods_num":3,"goods_id":1,"sell_price":"399.00","goods_name":"白色Easycare易打理衬衫","img_info":"public/20181215/634c6affffb0c1556b3c28b1ca724fcd414bbd77.jpeg"},{"id":5,"goods_num":1,"goods_id":1,"sell_price":"399.00","goods_name":"白色Easycare易打理衬衫","img_info":"public/20181215/634c6affffb0c1556b3c28b1ca724fcd414bbd77.jpeg"}]
         * address_list : {"id":1,"accept_name":"韦鹏","zcode":"","phone":"13916905124","country":"中国","province":"浙江省","city":"杭州市","area":"下城区","address":"星城发展大厦2-1203"}
         * lt_arr : {"id":1,"name":null,"height":0,"weight":0}
         */

        private AddressListBean address_list;
        private LtArrBean lt_arr;
        private List<CarListBean> car_list;

        public AddressListBean getAddress_list() {
            return address_list;
        }

        public void setAddress_list(AddressListBean address_list) {
            this.address_list = address_list;
        }

        public LtArrBean getLt_arr() {
            return lt_arr;
        }

        public void setLt_arr(LtArrBean lt_arr) {
            this.lt_arr = lt_arr;
        }

        public List<CarListBean> getCar_list() {
            return car_list;
        }

        public void setCar_list(List<CarListBean> car_list) {
            this.car_list = car_list;
        }

        public static class AddressListBean implements Serializable {
            /**
             * id : 1
             * accept_name : 韦鹏
             * zcode :
             * phone : 13916905124
             * country : 中国
             * province : 浙江省
             * city : 杭州市
             * area : 下城区
             * address : 星城发展大厦2-1203
             */

            private int id;
            private String accept_name;
            private String zcode;
            private String phone;
            private String country;
            private String province;
            private String city;
            private String area;
            private String address;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAccept_name() {
                return accept_name;
            }

            public void setAccept_name(String accept_name) {
                this.accept_name = accept_name;
            }

            public String getZcode() {
                return zcode;
            }

            public void setZcode(String zcode) {
                this.zcode = zcode;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }
        }

        public static class LtArrBean implements Serializable{
            /**
             * id : 1
             * name : null
             * height : 0
             * weight : 0
             */

            private int id;
            private String name;
            private double height;
            private double weight;

            public double getHeight() {
                return height;
            }

            public double getWeight() {
                return weight;
            }

            public void setHeight(double height) {
                this.height = height;

            }

            public void setWeight(double weight) {
                this.weight = weight;
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

        public static class CarListBean {
            /**
             * id : 4
             * goods_num : 3
             * goods_id : 1
             * sell_price : 399.00
             * goods_name : 白色Easycare易打理衬衫
             * img_info : public/20181215/634c6affffb0c1556b3c28b1ca724fcd414bbd77.jpeg
             */

            private int cart_id;
            private int goods_num;
            private int goods_id;
            private String sell_price;
            private String goods_name;
            private String img_info;


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

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public String getSell_price() {
                return sell_price;
            }

            public void setSell_price(String sell_price) {
                this.sell_price = sell_price;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getImg_info() {
                return img_info;
            }

            public void setImg_info(String img_info) {
                this.img_info = img_info;
            }
        }
    }
}
