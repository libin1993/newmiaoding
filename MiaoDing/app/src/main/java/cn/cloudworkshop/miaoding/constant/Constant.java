package cn.cloudworkshop.miaoding.constant;

/**
 * Author：Libin on 2016/7/7 17:40
 * Email：1993911441@qq.com
 * Describe：接口常量
 */

public class Constant {
    public static final String HOST = "https://www.cloudworkshop.cn";
    //    public static final String NEW_HOST = "http://192.168.1.254/localtest/ygc/public/index.php";
    public static final String NEW_HOST = "http://api.cloudworkshop.cn";
    public static final String WEB_HOST = "https://h5.morder.cn";

    //oss图片
    public static final String IMG_HOST = "https://newupload.oss-cn-beijing.aliyuncs.com/";
    //微信appid
    public static final String APP_ID = "wx07c2173e7686741e";
    //图片上传
    public static final String UPLOAD_FILE = NEW_HOST + "/user/together/uploads";
    private static final String HOST_INDEX = HOST + "/index.php/index/index5_4/";
    //首页资讯
    public static final String HOMEPAGE_LIST = NEW_HOST + "/user/index/index";
    //定制商品分类
    public static final String GOODS_TITLE = NEW_HOST + "/user/goods/get_goods_type";
    //定制商品
    public static final String GOODS_LIST = NEW_HOST + "/user/goods/custom_series";
    //腔调作品
    public static final String DESIGNER_WORKS = HOST_INDEX + "cobbler_goods_list";
    //定制商品详情
    public static final String GOODS_DETAILS = NEW_HOST + "/goods/detail";
    //定制配件（老版）
    public static final String CUSTOMIZE = HOST_INDEX + "customize";
    //定制配件（最新版）
    public static final String CUSTOMIZE_NEW = HOST_INDEX + "customizenew";
    //定制配件（新版）
    public static final String NEW_CUSTOMIZE = HOST_INDEX + "new_customize";
    //发送验证码
    public static final String IDENTIFY_CODE = NEW_HOST + "/user/login/send";
    //登录
    public static final String LOGIN = NEW_HOST + "/md/login";
    //注册协议
    public static final String SIGN_AGREEMENT = NEW_HOST + "/user/login/sys_set";
    //退出登录
    public static final String LOGOUT = NEW_HOST + "/md/logout";
    //收藏
    public static final String COLLECTION = HOST_INDEX + "my_collect";
    //穿衣测试
    public static final String DRESSING_TEST = HOST_INDEX + "add_user_data";
    //意见反馈
    public static final String FEED_BACK = HOST_INDEX + "add_suggest";
    //新增地址
    public static final String ADD_ADDRESS = NEW_HOST + "/delivery_address/add";
    //修改地址
    public static final String UPDATE_ADDRESS = NEW_HOST + "/delivery_address/update";
    //收货地址
    public static final String MY_ADDRESS = NEW_HOST + "/delivery_address/list";
    //删除地址
    public static final String DELETE_ADDRESS = NEW_HOST + "/delivery_address/delete";
    //默认地址
    public static final String DEFAULT_ADDRESS = NEW_HOST + "/delivery_address/set_default";
    //个性绣花
    public static final String EMBROIDERY_CUSTOMIZE = HOST_INDEX + "new_goods_gxh";
    //设计师入驻
    public static final String JOIN_US = HOST_INDEX + "get_img";
    //申请入驻
    public static final String APPLY_JOIN = HOST_INDEX + "apply_in";
    //用户信息
    public static final String USER_INFO = NEW_HOST + "/user_center/index";
    //加入购物车
    public static final String ADD_CART = HOST_INDEX + "add_car";
    //订单信息
    public static final String ORDER_INFO = HOST_INDEX + "buy";
    //确认订单
    public static final String CONFIRM_ORDER = HOST_INDEX + "add_order_v4";
    //修改用户信息
    public static final String CHANGE_INFO = NEW_HOST + "/user_center/edit";
    //购物车
    public static final String SHOPPING_CART = NEW_HOST + "/shopping_cart/list";
    //修改购物车数量
    public static final String CART_COUNT = NEW_HOST + "/shopping_cart/update";
    //删除购物车
    public static final String DELETE_CART = NEW_HOST + "/shopping_cart/delete";
    //我的订单
    public static final String GOODS_ORDER = HOST_INDEX + "goods_order_v5_2";
    //订单详情
    public static final String ORDER_DETAIL = HOST_INDEX + "new_order_detail_v5_2";
    //取消订单
    public static final String CANCEL_ORDER = HOST_INDEX + "cancel_order";
    //删除订单
    public static final String DELETE_ORDER = HOST_INDEX + "delete_order";
    //确认收货
    public static final String CONFIRM_RECEIVE = HOST_INDEX + "confirm_order";
    //添加收藏
    public static final String ADD_COLLECTION = NEW_HOST + "/user/collects/add_user_collect";
    //购物车跳转定制详情
    public static final String CART_TO_CUSTOM = HOST_INDEX + "dz_car_data";
    //预约量体
    public static final String APPOINTMENT_ORDER = HOST_INDEX + "add_order_list";
    //常见问题
    public static final String QUESTION_CLASSIFY = HOST_INDEX + "help_classify";
    public static final String QUESTION_LIST = HOST_INDEX + "help_list";
    public static final String QUESTION_DETAIL = HOST_INDEX + "help_detail";
    //系统配置
    public static final String APP_INDEX = HOST + "/index.php/index/sys/index";
    //支付宝付款
    public static final String ALI_PAY = HOST_INDEX + "mk_pay_order_v4";
    //物流信息
    public static final String LOGISTICS_TRACK = HOST_INDEX + "kdcx";
    //微信付款
    public static final String WE_CHAT_PAY = HOST + "/index.php/index/wxpay/mk_pay_order_v4";
    //优惠券
    public static final String MY_COUPON = NEW_HOST + "/ticket/list";
    //兑换优惠券
    public static final String EXCHANGE_COUPON = NEW_HOST + "/ticket/add";
    //选择优惠券
    public static final String SELECT_COUPON = HOST + "/index.php/index/ticket/get_car_ticket";
    //检查登录
    public static final String CHECK_LOGIN = NEW_HOST + "/token/is_valid";
    //消息中心
    public static final String MESSAGE_TYPE = HOST + "/index.php/index/message/message_type";
    //消息详情
    public static final String MESSAGE_DETAIL = HOST + "/index.php/index/message/message_list";
    //售后
    public static final String AFTER_SALE = HOST_INDEX + "add_sh_order";
    //量体数据
    public static final String MEASURE_DATA1 = HOST_INDEX + "lt_data";
    //设备Id
    public static final String CLIENT_ID = HOST_INDEX + "add_device";
    //预约状态
    public static final String APPOINTMENT_STATUS = HOST_INDEX + "get_yuyue_status";
    //引导图
    public static final String GUIDE_IMG = HOST_INDEX + "get_guide_img";
    //订单评价
    public static final String GOODS_RECOMMEND = HOST_INDEX + "goods_recommend";
    //会员等级
    public static final String MEMBER_GRADE = HOST + "/index.php/index/user/user_privilege";
    //会员规则
    public static final String MEMBER_RULE = HOST + "/index.php/index/user/user_help";
    //会员成长
    public static final String MEMBER_GROWTH = HOST + "/index.php/index/user/user_credit_record";
    //升级礼包
    public static final String UPGRADE_GIFT = HOST + "/index.php/index/user/user_upgrade_gift";
    //生日礼包
    public static final String BIRTHDAY_GIFT = HOST + "/index.php/index/user/get_birthday_gift";
    //拍照上传
    public static final String TAKE_PHOTO = HOST + "/index.php/web/cc/cameraAndroidUpload_new";
    //设计师列表
    public static final String DESIGNER_LIST = HOST_INDEX + "get_designer_list";
    //设计师详情
    public static final String DESIGNER_DETAILS = HOST_INDEX + "user_intro";
    //优惠券规则
    public static final String COUPON_RULE = HOST + "/index.php/index/ticket/get_ticket_introduce";
    //订单评价
    public static final String EVALUATE = HOST_INDEX + "save_order_comment";
    //商品全部评价
    public static final String EVALUATE_LIST = HOST_INDEX + "get_goods_comment_list";
    //app icon
    public static final String APP_ICON = NEW_HOST + "/user_center/tab";
    //记录
    public static final String HOMEPAGE_LOG = HOST_INDEX + "save_index_log";
    public static final String GOODS_LOG = HOST_INDEX + "save_goods_log";
    public static final String LOGIN_LOG = HOST_INDEX + "save_user_login_log";
    //礼品卡
    public static final String GIFT_CARD = NEW_HOST + "/gift_card/balance";
    //兑换礼品卡
    public static final String EXCHANGE_CARD = NEW_HOST + "/gift_card/add";
    //喜爱
    public static final String ADD_LOVE = NEW_HOST + "/user/love/add_user_love";
    //店铺列表
    public static final String STORE_LIST = HOST_INDEX + "shop_list";
    //店铺信息
    public static final String STORE_INFO = HOST_INDEX + "shop_list_info";
    //店铺搜素
    public static final String STORE_SEARCH = HOST_INDEX + "shop_list_select";
    //店铺加入购物车
    public static final String STORE_ADD_CART = HOST_INDEX + "add_carInsert";
    //店铺下单
    public static final String STORE_ORDER = HOST_INDEX + "quick_order";
    //资讯列表
    public static final String NEWS_LIST = HOST_INDEX + "index_news_list";
    //三方登录
    public static final String QUICK_LOGIN = HOST + "/index.php/index/login/partLogin";
    //再次购买
    public static final String BUY_AGAIN = HOST_INDEX + "cloumorder";
    //量体数据
    public static final String MEASURE_DATA = NEW_HOST + "/volume/list";
    //设置默认量体数据
    public static final String DEFAULT_MEASURE_DATA = HOST_INDEX + "is_lt";
    //量体数据详情
    public static final String MEASURE_DETAIL = NEW_HOST + "/volume/detail";

    //WebView
    //咨讯详情
    public static final String HOMEPAGE_INFO = WEB_HOST + "/webapp/html/designer.html";
    //咨讯详情分享
    public static final String HOMEPAGE_SHARE = WEB_HOST + "/share/html/designer.html";
    //定制商品分享
    public static final String CUSTOM_SHARE = WEB_HOST + "/dd/index.html";
    //设计师成品分享
    public static final String WORKS_SHARE = WEB_HOST + "/share/html/chengping.html";
    //穿衣测试结果
    public static final String DRESSING_TEST_RESULT = WEB_HOST + "/webapp/html/clo.html";
    //穿衣测试分享
    public static final String DRESSING_TEST_SHARE = WEB_HOST + "/share/html/clo.html";
    //设计师分享
    public static final String DESIGNER_SHARE = WEB_HOST + "/share/html/jiangxin.html";
    //邀请好友
    public static final String INVITE_FRIENDS = WEB_HOST + "/share/html/yaoqingyouli.html";
    //邀请有礼分享
    public static final String INVITE_SHARE = WEB_HOST + "/share/html/yaoqing.html";
    //分享红包
    public static final String SHARE_COUPON = WEB_HOST + "/share/html/invitation_1000.html";

}



