package com.u1.gocashm.model.response;


import android.graphics.Bitmap;

import java.util.List;

/**
 * @author hewei
 * @date 2018/9/12 0012 下午 1:59
 */
public class LotterModel extends BasePhModel<LotterModel.Lotter> {

    public static class Lotter extends BasePhModel.Body {

        private int id;
        private String title;
        private List<Award> awards;
        private int bonus_count;

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

        public List<Award> getAwards() {
            return awards;
        }

        public void setAwards(List<Award> awards) {
            this.awards = awards;
        }

        public int getBonus_count() {
            return bonus_count;
        }

        public void setBonus_count(int bonus_count) {
            this.bonus_count = bonus_count;
        }

        public static class Award{
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


}
