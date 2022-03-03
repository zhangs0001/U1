package com.u1.gocashm.model.response;

import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

/**
 * @author hewei
 * @date 2018/9/11 0011 上午 11:23
 */
public class VersionPhModel extends AbstractBaseRespBean<Status, VersionPhModel.Version> {

    public static class Version {

        private String version;
        private boolean updateable;
        private boolean forcible;
        private String title;
        private String content;
        private String hotRelativeVersion;
        private String url;
        private boolean hotUpdateable;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public boolean isUpdateable() {
            return updateable;
        }

        public void setUpdateable(boolean updateable) {
            this.updateable = updateable;
        }

        public boolean isForcible() {
            return forcible;
        }

        public void setForcible(boolean forcible) {
            this.forcible = forcible;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getHotRelativeVersion() {
            return hotRelativeVersion;
        }

        public void setHotRelativeVersion(String hotRelativeVersion) {
            this.hotRelativeVersion = hotRelativeVersion;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isHotUpdateable() {
            return hotUpdateable;
        }

        public void setHotUpdateable(boolean hotUpdateable) {
            this.hotUpdateable = hotUpdateable;
        }
    }
}
