package com.u1.gocashm.model.response;

import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

/**
 * @author hewei
 * @date 2018/9/6 0006 下午 3:10
 */
public class PhotoImagePhModel extends AbstractBaseRespBean<Status, PhotoImagePhModel.PhotoImage> {

    public static class PhotoImage {

        private int imageId;

        public int getImageId() {
            return imageId;
        }

        public void setImageId(int imageId) {
            this.imageId = imageId;
        }
    }
}
