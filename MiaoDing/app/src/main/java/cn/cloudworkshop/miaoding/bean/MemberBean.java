package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2017/2/15 16:50
 * Email：1993911441@qq.com
 * Describe：
 */
public class MemberBean {


    /**
     * code : 10000
     * data : {"user_info":{"id":2096,"username":"哈哈哈哈","user_phone":"15167168495","head_ico":"public/20190109/45e88afbc08cad637b7332eacb7d048f417b7cf5.jpeg","giftcard_money":"0.00","exp":0,"birthday":"1980-01-01","user_grade":{"id":1,"name":"黄金","img":"public/20190110/5e5056380fc8a66fc6a53b2f10e1f9331432561c.png","img2":"public/20190110/dee3c2b9efb3fbf198583f0541059be99c670d46.png","min_credit":"0.00","max_credit":"999.00","ratio":"2.272727"}},"user_grade":[{"name":"黄金","img":"public/20190110/5e5056380fc8a66fc6a53b2f10e1f9331432561c.png","img2":"public/20190110/dee3c2b9efb3fbf198583f0541059be99c670d46.png","user_privilege_ids":"1,2,3","min_credit":"0.00","max_credit":"999.00","ratio":"2.272727"},{"name":"白金","img":"public/20190110/55190fa4d17d3a1d2aac338a03adf5036b51fafc.png","img2":"public/20190110/e8ae6b7470f6b84c6117776ed673fa621fe1c2ab.png","user_privilege_ids":"1,2,3,4,5","min_credit":"1000.00","max_credit":"9999.00","ratio":"2.272727"},{"name":"钻石","img":"public/20190110/fdd44f8fb63f41064108e5474e79150f09b7c4a4.png","img2":"public/20190110/68ed394d06cdb4a0cab1ec5c3a858b836d5c5ec3.png","user_privilege_ids":"1,2,3,4,5,6","min_credit":"10000.00","max_credit":"49999.00","ratio":"2.272727"},{"name":"终身","img":"public/20190110/2c733debdc065da2276920a0f5e6dbd471519180.png","img2":"public/20190110/59f4055e2a2a3adbc30f2fd577ffbd317804f016.png","user_privilege_ids":"1,2,3,4,5,6,7","min_credit":"50000.00","max_credit":"999999.00","ratio":"2.272727"}],"user_privilege":[{"id":1,"name":"免费上门量体","img":"public/20190110/041eb113851e4d6c45ced89c805e58742699906f.png","desc":"每个等级的会员都可享受妙定提供的免费上门量体服务","is_get":1,"ratio":"1.000000"},{"id":2,"name":"专属时尚顾问","img":"public/20190110/b5bf5e0ccf5b9b3bade54ab4b28b96544015b8e0.png","desc":"每个等级的会员都可享受妙定提供的1对1专属时尚顾问","is_get":1,"ratio":"1.000000"},{"id":3,"name":"邀请好友返利","img":"public/20190110/5528a90203335837aa9c346b43aef3ca12fadd22.png","desc":"邀请好友，可获得相应的现金返利，详情可在妙定APP-我的-邀请有礼页面查看","is_get":1,"ratio":"1.000000"},{"id":4,"name":"会员升级礼包","img":"public/20190110/1590cd5135dd4a86ce319c3db9adeef2066e0bc0.png","desc":"· 50点成长值· 1张全场通用券，满2000-500· 1张衬衫优惠券，满1000-200· 礼包领取有效期：  会员升级后7天内有效","is_get":3,"ratio":"1.000000"},{"id":5,"name":"急速售后服务","img":"public/20190110/68f99c7bd1eda71c2ea697bb8511091dc48572fe.png","desc":"享受妙定提供的7*24小时全年无休的售后服务","is_get":1,"ratio":"1.000000"},{"id":6,"name":"多重生日惊喜","img":"public/20190110/41367954d915e7c4154b73a5ea77f9944a0408a3.png","desc":"· 100点成长值 · 1张全场通用券，满1500-500 · 1张衬衫优惠券，满1000-200 · 生日周期内购物享双倍积分· 礼包领取有效期：  生日前三天及后四天内有效","is_get":2,"ratio":"1.000000"},{"id":7,"name":"优先线下活动","img":"public/20190110/75d28e7ec5a36ea4e79abbfdd64e16f1e0fb0b73.png","desc":"妙定会不定期组织、举办线下活动，邀请V4会员参加","is_get":1,"ratio":"1.000000"}]}
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
         * user_info : {"id":2096,"username":"哈哈哈哈","user_phone":"15167168495","head_ico":"public/20190109/45e88afbc08cad637b7332eacb7d048f417b7cf5.jpeg","giftcard_money":"0.00","exp":0,"birthday":"1980-01-01","user_grade":{"id":1,"name":"黄金","img":"public/20190110/5e5056380fc8a66fc6a53b2f10e1f9331432561c.png","img2":"public/20190110/dee3c2b9efb3fbf198583f0541059be99c670d46.png","min_credit":"0.00","max_credit":"999.00","ratio":"2.272727"}}
         * user_grade : [{"name":"黄金","img":"public/20190110/5e5056380fc8a66fc6a53b2f10e1f9331432561c.png","img2":"public/20190110/dee3c2b9efb3fbf198583f0541059be99c670d46.png","user_privilege_ids":"1,2,3","min_credit":"0.00","max_credit":"999.00","ratio":"2.272727"},{"name":"白金","img":"public/20190110/55190fa4d17d3a1d2aac338a03adf5036b51fafc.png","img2":"public/20190110/e8ae6b7470f6b84c6117776ed673fa621fe1c2ab.png","user_privilege_ids":"1,2,3,4,5","min_credit":"1000.00","max_credit":"9999.00","ratio":"2.272727"},{"name":"钻石","img":"public/20190110/fdd44f8fb63f41064108e5474e79150f09b7c4a4.png","img2":"public/20190110/68ed394d06cdb4a0cab1ec5c3a858b836d5c5ec3.png","user_privilege_ids":"1,2,3,4,5,6","min_credit":"10000.00","max_credit":"49999.00","ratio":"2.272727"},{"name":"终身","img":"public/20190110/2c733debdc065da2276920a0f5e6dbd471519180.png","img2":"public/20190110/59f4055e2a2a3adbc30f2fd577ffbd317804f016.png","user_privilege_ids":"1,2,3,4,5,6,7","min_credit":"50000.00","max_credit":"999999.00","ratio":"2.272727"}]
         * user_privilege : [{"id":1,"name":"免费上门量体","img":"public/20190110/041eb113851e4d6c45ced89c805e58742699906f.png","desc":"每个等级的会员都可享受妙定提供的免费上门量体服务","is_get":1,"ratio":"1.000000"},{"id":2,"name":"专属时尚顾问","img":"public/20190110/b5bf5e0ccf5b9b3bade54ab4b28b96544015b8e0.png","desc":"每个等级的会员都可享受妙定提供的1对1专属时尚顾问","is_get":1,"ratio":"1.000000"},{"id":3,"name":"邀请好友返利","img":"public/20190110/5528a90203335837aa9c346b43aef3ca12fadd22.png","desc":"邀请好友，可获得相应的现金返利，详情可在妙定APP-我的-邀请有礼页面查看","is_get":1,"ratio":"1.000000"},{"id":4,"name":"会员升级礼包","img":"public/20190110/1590cd5135dd4a86ce319c3db9adeef2066e0bc0.png","desc":"· 50点成长值· 1张全场通用券，满2000-500· 1张衬衫优惠券，满1000-200· 礼包领取有效期：  会员升级后7天内有效","is_get":3,"ratio":"1.000000"},{"id":5,"name":"急速售后服务","img":"public/20190110/68f99c7bd1eda71c2ea697bb8511091dc48572fe.png","desc":"享受妙定提供的7*24小时全年无休的售后服务","is_get":1,"ratio":"1.000000"},{"id":6,"name":"多重生日惊喜","img":"public/20190110/41367954d915e7c4154b73a5ea77f9944a0408a3.png","desc":"· 100点成长值 · 1张全场通用券，满1500-500 · 1张衬衫优惠券，满1000-200 · 生日周期内购物享双倍积分· 礼包领取有效期：  生日前三天及后四天内有效","is_get":2,"ratio":"1.000000"},{"id":7,"name":"优先线下活动","img":"public/20190110/75d28e7ec5a36ea4e79abbfdd64e16f1e0fb0b73.png","desc":"妙定会不定期组织、举办线下活动，邀请V4会员参加","is_get":1,"ratio":"1.000000"}]
         */

        private UserInfoBean user_info;
        private List<UserGradeBeanX> user_grade;
        private List<UserPrivilegeBean> user_privilege;

        public UserInfoBean getUser_info() {
            return user_info;
        }

        public void setUser_info(UserInfoBean user_info) {
            this.user_info = user_info;
        }

        public List<UserGradeBeanX> getUser_grade() {
            return user_grade;
        }

        public void setUser_grade(List<UserGradeBeanX> user_grade) {
            this.user_grade = user_grade;
        }

        public List<UserPrivilegeBean> getUser_privilege() {
            return user_privilege;
        }

        public void setUser_privilege(List<UserPrivilegeBean> user_privilege) {
            this.user_privilege = user_privilege;
        }

        public static class UserInfoBean {
            /**
             * id : 2096
             * username : 哈哈哈哈
             * user_phone : 15167168495
             * head_ico : public/20190109/45e88afbc08cad637b7332eacb7d048f417b7cf5.jpeg
             * giftcard_money : 0.00
             * exp : 0
             * birthday : 1980-01-01
             * user_grade : {"id":1,"name":"黄金","img":"public/20190110/5e5056380fc8a66fc6a53b2f10e1f9331432561c.png","img2":"public/20190110/dee3c2b9efb3fbf198583f0541059be99c670d46.png","min_credit":"0.00","max_credit":"999.00","ratio":"2.272727"}
             */

            private int id;
            private String username;
            private String user_phone;
            private String head_ico;
            private String giftcard_money;
            private int exp;
            private String birthday;
            private UserGradeBean user_grade;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getUser_phone() {
                return user_phone;
            }

            public void setUser_phone(String user_phone) {
                this.user_phone = user_phone;
            }

            public String getHead_ico() {
                return head_ico;
            }

            public void setHead_ico(String head_ico) {
                this.head_ico = head_ico;
            }

            public String getGiftcard_money() {
                return giftcard_money;
            }

            public void setGiftcard_money(String giftcard_money) {
                this.giftcard_money = giftcard_money;
            }

            public int getExp() {
                return exp;
            }

            public void setExp(int exp) {
                this.exp = exp;
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

            public static class UserGradeBean {
                /**
                 * id : 1
                 * name : 黄金
                 * img : public/20190110/5e5056380fc8a66fc6a53b2f10e1f9331432561c.png
                 * img2 : public/20190110/dee3c2b9efb3fbf198583f0541059be99c670d46.png
                 * min_credit : 0.00
                 * max_credit : 999.00
                 * ratio : 2.272727
                 */

                private int id;
                private String name;
                private String img;
                private String img2;
                private String min_credit;
                private String max_credit;
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

                public String getRatio() {
                    return ratio;
                }

                public void setRatio(String ratio) {
                    this.ratio = ratio;
                }
            }
        }

        public static class UserGradeBeanX {
            /**
             * name : 黄金
             * img : public/20190110/5e5056380fc8a66fc6a53b2f10e1f9331432561c.png
             * img2 : public/20190110/dee3c2b9efb3fbf198583f0541059be99c670d46.png
             * user_privilege_ids : 1,2,3
             * min_credit : 0.00
             * max_credit : 999.00
             * ratio : 2.272727
             */

            private String name;
            private String img;
            private String img2;
            private String user_privilege_ids;
            private String min_credit;
            private String max_credit;
            private String ratio;

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

            public String getUser_privilege_ids() {
                return user_privilege_ids;
            }

            public void setUser_privilege_ids(String user_privilege_ids) {
                this.user_privilege_ids = user_privilege_ids;
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

            public String getRatio() {
                return ratio;
            }

            public void setRatio(String ratio) {
                this.ratio = ratio;
            }
        }

        public static class UserPrivilegeBean {
            /**
             * id : 1
             * name : 免费上门量体
             * img : public/20190110/041eb113851e4d6c45ced89c805e58742699906f.png
             * desc : 每个等级的会员都可享受妙定提供的免费上门量体服务
             * is_get : 1
             * ratio : 1.000000
             */

            private int id;
            private String name;
            private String img;
            private String desc;
            private int is_get;
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

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public int getIs_get() {
                return is_get;
            }

            public void setIs_get(int is_get) {
                this.is_get = is_get;
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
