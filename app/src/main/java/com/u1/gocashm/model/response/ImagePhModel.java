package com.u1.gocashm.model.response;

/**
 * @author hewei
 * @date 2018/9/20 0020 下午 7:04
 */
public class ImagePhModel {

    private String imageType;
    private String imageUrl;
    private int imageId;
    private long applyId;

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public long getApplyId() {
        return applyId;
    }

    public void setApplyId(long applyId) {
        this.applyId = applyId;
    }
}
