package cn.cloudworkshop.miaoding.constant;

/**
 * Author：Libin on 2016/7/7 17:40
 * Email：1993911441@qq.com
 * Describe：接口常量
 */

public class Constant {
    public static final String HOST = "http://www.cloudworkshop.cn";
    //    public static final String NEW_HOST = "http://192.168.1.120/index.php";
    public static final String NEW_HOST = "https://api.cloudworkshop.cn";
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
    //定制商品筛选标签
    public static final String GOODS_TAG = NEW_HOST + "/user/goods/goods_choose_option";
    //定制商品
    public static final String GOODS_LIST = NEW_HOST + "/user/goods/custom_series";
    //腔调作品
    public static final String ACCENT_WORKS = NEW_HOST + "/user/goods/products";
    //腔调作品
    public static final String WORKS_STOCK = NEW_HOST + "/goods/sku";
    //定制商品详情
    public static final String GOODS_DETAILS = NEW_HOST + "/user/goods/goods_one";
    //定制商品配件
    public static final String CUSTOMIZE_PARTS = NEW_HOST + "/goods/detail";
    //定制配件（老版）
    public static final String CUSTOMIZE = HOST_INDEX + "customize";
    //定制配件（当前版）
    public static final String CUSTOMIZE_NEW = HOST_INDEX + "customizenew";
    //定制配件（弃用）
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
    public static final String COLLECTION = NEW_HOST + "/user/collects/my_collect";
    //穿衣测试
    public static final String DRESSING_TEST = HOST_INDEX + "add_user_data";
    //意见反馈
    public static final String FEED_BACK = NEW_HOST + "/user_center/add_suggest";
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
    public static final String JOIN_US = NEW_HOST + "/user/index/get_img";
    //申请入驻
    public static final String APPLY_JOIN = NEW_HOST + "/user/center/apply_in";
    //用户信息
    public static final String USER_INFO = NEW_HOST + "/user_center/index";
    //加入购物车
    public static final String ADD_CART = NEW_HOST + "/shopping_cart/add";
    //订单信息
    public static final String ORDER_INFO = NEW_HOST + "/order/confirm";
    //确认订单
    public static final String CONFIRM_ORDER = NEW_HOST + "/order/cart";
    //修改用户信息
    public static final String CHANGE_INFO = NEW_HOST + "/user_center/edit";
    //购物车
    public static final String SHOPPING_CART = NEW_HOST + "/shopping_cart/list";
    //修改购物车数量
    public static final String CART_COUNT = NEW_HOST + "/shopping_cart/update";
    //删除购物车
    public static final String DELETE_CART = NEW_HOST + "/shopping_cart/delete";
    //我的订单
    public static final String GOODS_ORDER = NEW_HOST + "/order/list";
    //订单详情
    public static final String ORDER_DETAIL = NEW_HOST + "/order/detail";
    //取消订单
    public static final String CANCEL_ORDER = NEW_HOST + "/order/cancel";
    //删除订单
    public static final String DELETE_ORDER = NEW_HOST + "/order/delete";
    //确认收货
    public static final String CONFIRM_RECEIVE = NEW_HOST + "/order/confirm_delivery";
    //添加收藏
    public static final String ADD_COLLECTION = NEW_HOST + "/user/collects/add_user_collect";
    //购物车跳转定制详情
    public static final String CART_TO_CUSTOM = HOST_INDEX + "dz_car_data";
    //预约量体
    public static final String APPLY_MEASURE = NEW_HOST + "/user/volume/prepare_volume";
    //常见问题
    public static final String QUESTION_CLASSIFY = NEW_HOST + "/user_center/help_classify";
    public static final String QUESTION_LIST = NEW_HOST + "/user_center/help_list";
    public static final String QUESTION_DETAIL = NEW_HOST + "/user_center/help_detail";
    //检测更新
    public static final String CHECK_UPDATE = NEW_HOST + "/version/get_lastest";
    //支付宝付款
    public static final String ALI_PAY = NEW_HOST + "/alipay/order_pay_info";
    //物流信息
    public static final String LOGISTICS_TRACK = NEW_HOST + "/order/sf_express";
    //微信付款
    public static final String WE_CHAT_PAY = NEW_HOST + "/wxpay/order_pay_info";
    //优惠券
    public static final String MY_COUPON = NEW_HOST + "/ticket/list";
    //兑换优惠券
    public static final String EXCHANGE_COUPON = NEW_HOST + "/ticket/add";
    //选择优惠券
    public static final String SELECT_COUPON = NEW_HOST + "/ticket/cart_assoc";
    //检查登录
    public static final String CHECK_LOGIN = NEW_HOST + "/token/is_valid";
    //消息中心
    public static final String MESSAGE_TYPE = NEW_HOST + "/notifications/type";
    //消息详情
    public static final String MESSAGE_DETAIL = NEW_HOST + "/notifications/list";
    //已读消息
    public static final String READ_MSG = NEW_HOST + "/notifications/read";
    //售后
    public static final String AFTER_SALE = HOST_INDEX + "add_sh_order";
    //设备Id
    public static final String CLIENT_ID = HOST_INDEX + "add_device";
    //预约状态
    public static final String APPOINTMENT_STATUS = NEW_HOST + "/user/volume/get_yuyue_status";
    //引导图
    public static final String GUIDE_IMG = NEW_HOST + "/user/extra/get_guide_img";
    //购物车商品推荐
    public static final String GOODS_RECOMMEND = NEW_HOST + "/shopping_cart/recommend_goods";
    //会员等级
    public static final String MEMBER_GRADE = NEW_HOST + "/user/user/user_privilege";
    //会员规则
    public static final String MEMBER_RULE = NEW_HOST + "/user/user/user_help";
    //会员成长
    public static final String MEMBER_GROWTH = NEW_HOST + "/user/user/user_credit_record";
    //升级礼包
    public static final String UPGRADE_GIFT = NEW_HOST + "/user/user/user_upgrade_gift";
    //生日礼包
    public static final String BIRTHDAY_GIFT = NEW_HOST + "/user/user/get_birthday_gift";
    //拍照上传
    public static final String TAKE_PHOTO = NEW_HOST + "/user/volume/save_volumes";
    //设计师列表
    public static final String DESIGNER_LIST = HOST_INDEX + "get_designer_list";
    //设计师详情
    public static final String DESIGNER_DETAILS = HOST_INDEX + "user_intro";
    //优惠券规则
    public static final String COUPON_RULE = NEW_HOST + "/ticket/rule";
    //订单评价
    public static final String EVALUATE = NEW_HOST + "/user/order/save_order_comment";
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
    public static final String NEWS_LIST = NEW_HOST + "/user/article/article_list";
    //三方登录
    public static final String QUICK_LOGIN = HOST + "/index.php/index/login/partLogin";
    //再次购买
    public static final String BUY_AGAIN = NEW_HOST + "/order/buy_again";
    //量体数据
    public static final String MEASURE_DATA = NEW_HOST + "/volume/list";
    //设置默认量体数据
    public static final String DEFAULT_MEASURE_DATA = NEW_HOST + "/user/volume/is_lt";
    //量体数据详情
    public static final String MEASURE_DETAIL = NEW_HOST + "/volume/detail";
    //客服电话
    public static final String SERVICE_PHONE = NEW_HOST + "/user/sysset/get_tel";
    //确认支付
    public static final String CONFIRM_PAY = NEW_HOST + "/order/order_confirm";
    //礼品卡支付
    public static final String GIFT_CARD_PAY = NEW_HOST + "/order/gift_card_buy";
    //商品id解密
    public static final String GOODS_DECODE = NEW_HOST + "/user/goods/goods_category";
    //商品id加密
    public static final String GOODS_ENCODE = NEW_HOST + "/user/user/get_goods_id_encrypted";

    //WebView
    //咨讯详情
    public static final String HOMEPAGE_INFO = WEB_HOST + "/webapp/html/designer.html";
    //咨讯详情分享
    public static final String HOMEPAGE_SHARE = WEB_HOST + "/share/html/designer.html";
    //定制商品分享
    public static final String CUSTOM_SHARE = WEB_HOST + "/dd/index.html";
    //设计师成品分享(过期)
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



