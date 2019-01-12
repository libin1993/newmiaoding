package cn.cloudworkshop.miaoding.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author：Libin on 2019/1/10 16:34
 * Email：1993911441@qq.com
 * Describe：
 */
public class CustomizePartsBean implements Serializable {


    private String content;
    private String goodsImg;

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    private int goodsId;
    private String goodsName;
    private String special_mark_part_ids;
    private String must_display_part_ids;
    private String price;
    private FabricBean fabricBean;
    private List<EmbroideryBean> embroideryBeans;
    private List<PartsBean> partsBeans;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getSpecial_mark_part_ids() {
        return special_mark_part_ids;
    }

    public void setSpecial_mark_part_ids(String special_mark_part_ids) {
        this.special_mark_part_ids = special_mark_part_ids;
    }

    public String getMust_display_part_ids() {
        return must_display_part_ids;
    }

    public void setMust_display_part_ids(String must_display_part_ids) {
        this.must_display_part_ids = must_display_part_ids;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public FabricBean getFabricBean() {
        return fabricBean;
    }

    public void setFabricBean(FabricBean fabricBean) {
        this.fabricBean = fabricBean;
    }

    public List<EmbroideryBean> getEmbroideryBeans() {
        return embroideryBeans;
    }

    public void setEmbroideryBeans(List<EmbroideryBean> embroideryBeans) {
        this.embroideryBeans = embroideryBeans;
    }

    public List<PartsBean> getPartsBeans() {
        return partsBeans;
    }

    public void setPartsBeans(List<PartsBean> partsBeans) {
        this.partsBeans = partsBeans;
    }

    public static class FabricBean implements Serializable {
        private String fabricId;
        private String fabricName;

        public String getFabricId() {
            return fabricId;
        }

        public void setFabricId(String fabricId) {
            this.fabricId = fabricId;
        }

        public String getFabricName() {
            return fabricName;
        }

        public void setFabricName(String fabricName) {
            this.fabricName = fabricName;
        }
    }

    public static class EmbroideryBean implements Serializable {
        private String title;
        private String name;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


    public static class PartsBean implements Serializable {
        private String title;
        private String name;
        private int positionId;
        private String img;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPositionId() {
            return positionId;
        }

        public void setPositionId(int positionId) {
            this.positionId = positionId;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
