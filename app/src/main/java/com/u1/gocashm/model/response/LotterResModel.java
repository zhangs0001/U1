package com.u1.gocashm.model.response;


import android.graphics.Bitmap;

/**
 * @author hewei
 * @date 2018/9/12 0012 下午 1:59
 */
public class LotterResModel extends BasePhModel<LotterResModel.Award> {

    public static class Award extends BasePhModel.Body {
        private int id;
        private String title;
        private String file;
        private Bitmap bitmap;

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }
    }

}
