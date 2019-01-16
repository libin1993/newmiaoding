package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2017-06-22 11:26
 * Email：1993911441@qq.com
 * Describe：
 */
public class UserBean {


    /**
     * code : 10000
     * msg :
     * data : {"user_info":{"user_phone":"15167168495","unread_message_num":0,"birthday":"1980-01-01","user_grade":{"id":1,"name":"黄金","img":"public/20190110/5e5056380fc8a66fc6a53b2f10e1f9331432561c.png","img2":"public/20190110/dee3c2b9efb3fbf198583f0541059be99c670d46.png","min_credit":"0.00","max_credit":"999.00","user_privilege_ids":"1,2,3","ratio":"2.272727"},"is_yuyue":0,"uid":2096,"username":"哈哈哈哈","avatar":"public/20190109/45e88afbc08cad637b7332eacb7d048f417b7cf5.jpeg"},"icon_list":[{"id":1,"name":"我的订单","img":"public/20190102/b3fb07df9ff17b18b0f8f0dd3ccfd1cdfa318477.png","select_img":""},{"id":2,"name":"购物袋","img":"public/20190102/a26e1d6af2ea613deef7df8c8b036b9537c5d5e8.png","select_img":""},{"id":3,"name":"优惠券","img":"public/20190102/439e44a33d2b46cdd43a23db03b0b0bd78b1500c.png","select_img":""},{"id":4,"name":"我的收藏","img":"public/20190102/96e1c8e76c6261f677d10194d8bc5ee0c40a38a9.png","select_img":""},{"id":5,"name":"预约量体","img":"public/20190102/fff2f19cdaf54fcee3b5057c886195b13cefdcad.png","select_img":""},{"id":6,"name":"礼品卡","img":"public/20190102/3b565e532ec635f8eb4c5485a4618745c10f8361.png","select_img":""},{"id":7,"name":"拍照量体","img":"public/20190102/082692a2063c87dfd57a8132911e264918f11f78.png","select_img":""},{"id":8,"name":"私人顾问","img":"public/20190102/bdfd35c3f755b45c4029f8b172c5e6906b70ecaa.png","select_img":""},{"id":9,"name":"邀请好友","img":"public/20190102/f93dd87092c95e977d22c2fdfb7e1116e8345d3a.png","select_img":""}],"is_opencv":2}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * user_info : {"user_phone":"15167168495","unread_message_num":0,"birthday":"1980-01-01","user_grade":{"id":1,"name":"黄金","img":"public/20190110/5e5056380fc8a66fc6a53b2f10e1f9331432561c.png","img2":"public/20190110/dee3c2b9efb3fbf198583f0541059be99c670d46.png","min_credit":"0.00","max_credit":"999.00","user_privilege_ids":"1,2,3","ratio":"2.272727"},"is_yuyue":0,"uid":2096,"username":"哈哈哈哈","avatar":"public/20190109/45e88afbc08cad637b7332eacb7d048f417b7cf5.jpeg"}
         * icon_list : [{"id":1,"name":"我的订单","img":"public/20190102/b3fb07df9ff17b18b0f8f0dd3ccfd1cdfa318477.png","select_img":""},{"id":2,"name":"购物袋","img":"public/20190102/a26e1d6af2ea613deef7df8c8b036b9537c5d5e8.png","select_img":""},{"id":3,"name":"优惠券","img":"public/20190102/439e44a33d2b46cdd43a23db03b0b0bd78b1500c.png","select_img":""},{"id":4,"name":"我的收藏","img":"public/20190102/96e1c8e76c6261f677d10194d8bc5ee0c40a38a9.png","select_img":""},{"id":5,"name":"预约量体","img":"public/20190102/fff2f19cdaf54fcee3b5057c886195b13cefdcad.png","select_img":""},{"id":6,"name":"礼品卡","img":"public/20190102/3b565e532ec635f8eb4c5485a4618745c10f8361.png","select_img":""},{"id":7,"name":"拍照量体","img":"public/20190102/082692a2063c87dfd57a8132911e264918f11f78.png","select_img":""},{"id":8,"name":"私人顾问","img":"public/20190102/bdfd35c3f755b45c4029f8b172c5e6906b70ecaa.png","select_img":""},{"id":9,"name":"邀请好友","img":"public/20190102/f93dd87092c95e977d22c2fdfb7e1116e8345d3a.png","select_img":""}]
         * is_opencv : 2
         */

        private UserInfoBean user_info;
        private int is_opencv;
        private List<IconListBean> icon_list;

        public UserInfoBean getUser_info() {
            return user_info;
        }

        public void setUser_info(UserInfoBean user_info) {
            this.user_info = user_info;
        }

        public int getIs_opencv() {
            return is_opencv;
        }

        public void setIs_opencv(int is_opencv) {
            this.is_opencv = is_opencv;
        }

        public List<IconListBean> getIcon_list() {
            return icon_list;
        }

        public void setIcon_list(List<IconListBean> icon_list) {
            this.icon_list = icon_list;
        }

        public static class UserInfoBean {
            /**
             * user_phone : 15167168495
             * unread_message_num : 0
             * birthday : 1980-01-01
             * user_grade : {"id":1,"name":"黄金","img":"public/20190110/5e5056380fc8a66fc6a53b2f10e1f9331432561c.png","img2":"public/20190110/dee3c2b9efb3fbf198583f0541059be99c670d46.png","min_credit":"0.00","max_credit":"999.00","user_privilege_ids":"1,2,3","ratio":"2.272727"}
             * is_yuyue : 0
             * uid : 2096
             * username : 哈哈哈哈
             * avatar : public/20190109/45e88afbc08cad637b7332eacb7d048f417b7cf5.jpeg
             */

            private String user_phone;
            private int unread_message_num;
            private String birthday;
            private UserGradeBean user_grade;
            private int is_yuyue;
            private int uid;
            private String username;
            private String avatar;

            public String getUser_phone() {
                return user_phone;
            }

            public void setUser_phone(String user_phone) {
                this.user_phone = user_phone;
            }

            public int getUnread_message_num() {
                return unread_message_num;
            }

            public void setUnread_message_num(int unread_message_num) {
                this.unread_message_num = unread_message_num;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public UserGradeBean getUser_grade() {
                return user_grade;
            }

            public void setUser_grade(UserGradeBean user_grade) {
                this.user_grade = user_grade;
            }

            public int getIs_yuyue() {
                return is_yuyue;
            }

            public void setIs_yuyue(int is_yuyue) {
                this.is_yuyue = is_yuyue;
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

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public static class UserGradeBean {
                /**
                 * id : 1
                 * name : 黄金
                 * img : public/20190110/5e5056380fc8a66fc6a53b2f10e1f9331432561c.png
                 * img2 : public/20190110/dee3c2b9efb3fbf198583f0541059be99c670d46.png
                 * min_credit : 0.00
                 * max_credit : 999.00
                 * user_privilege_ids : 1,2,3
                 * ratio : 2.272727
                 */

                private int id;
                private String name;
                private String img;
                private String img2;
                private String min_credit;
                private String max_credit;
                private String user_privilege_ids;
                private String ratio;

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

                public String getImg2() {
                    return img2;
                }

                public void setImg2(String img2) {
                    this.img2 = img2;
                }

                public String getMin_credit() {
                    return min_credit;
                }

                public void setMin_credit(String min_credit) {
                    this.min_credit = min_credit;
                }

                public String getMax_credit() {
                    return max_credit;
                }

                public void setMax_credit(String max_credit) {
                    this.max_credit = max_credit;
                }

                public String getUser_privilege_ids() {
                    return user_privilege_ids;
                }

                public void setUser_privilege_ids(String user_privilege_ids) {
                    this.user_privilege_ids = user_privilege_ids;
                }

                public String getRatio() {
                    return ratio;
                }

                public void setRatio(String ratio) {
                    this.ratio = ratio;
                }
            }
        }

        public static class IconListBean {
            /**
             * id : 1
             * name : 我的订单
             * img : public/20190102/b3fb07df9ff17b18b0f8f0dd3ccfd1cdfa318477.png
             * select_img :
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
}
