package com.u1.gocashm.util.model;

/**
 * @author hewei
 * @date 2020/3/10 0010 下午 3:53
 */
public class FacebookModel {
    private NameValuePairsBeanXX nameValuePairs;

    public NameValuePairsBeanXX getNameValuePairs() {
        return nameValuePairs;
    }

    public void setNameValuePairs(NameValuePairsBeanXX nameValuePairs) {
        this.nameValuePairs = nameValuePairs;
    }

    public static class NameValuePairsBeanXX {

        private String id;
        private String name;
        private String email;
        private String first_name;
        private String last_name;
        private String name_format;
        private String short_name;
        private PictureBean picture;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getName_format() {
            return name_format;
        }

        public void setName_format(String name_format) {
            this.name_format = name_format;
        }

        public String getShort_name() {
            return short_name;
        }

        public void setShort_name(String short_name) {
            this.short_name = short_name;
        }

        public PictureBean getPicture() {
            return picture;
        }

        public void setPicture(PictureBean picture) {
            this.picture = picture;
        }

        public static class PictureBean {

            private NameValuePairsBeanX nameValuePairs;

            public NameValuePairsBeanX getNameValuePairs() {
                return nameValuePairs;
            }

            public void setNameValuePairs(NameValuePairsBeanX nameValuePairs) {
                this.nameValuePairs = nameValuePairs;
            }

            public static class NameValuePairsBeanX {

                private DataBean data;

                public DataBean getData() {
                    return data;
                }

                public void setData(DataBean data) {
                    this.data = data;
                }

                public static class DataBean {

                    private NameValuePairsBean nameValuePairs;

                    public NameValuePairsBean getNameValuePairs() {
                        return nameValuePairs;
                    }

                    public void setNameValuePairs(NameValuePairsBean nameValuePairs) {
                        this.nameValuePairs = nameValuePairs;
                    }

                    public static class NameValuePairsBean {

                        private int height;
                        private boolean is_silhouette;
                        private String url;
                        private int width;

                        public int getHeight() {
                            return height;
                        }

                        public void setHeight(int height) {
                            this.height = height;
                        }

                        public boolean isIs_silhouette() {
                            return is_silhouette;
                        }

                        public void setIs_silhouette(boolean is_silhouette) {
                            this.is_silhouette = is_silhouette;
                        }

                        public String getUrl() {
                            return url;
                        }

                        public void setUrl(String url) {
                            this.url = url;
                        }

                        public int getWidth() {
                            return width;
                        }

                        public void setWidth(int width) {
                            this.width = width;
                        }
                    }
                }
            }
        }
    }
}
