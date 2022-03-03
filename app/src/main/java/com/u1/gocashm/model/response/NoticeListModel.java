package com.u1.gocashm.model.response;

import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

import java.util.List;


public class NoticeListModel extends AbstractBaseRespBean<Status, NoticeListModel> {
    private int unread;
    private List<Notice> items;

    public int getUnread() {
        return unread;
    }

    public void setUnread(int unread) {
        this.unread = unread;
    }

    public List<Notice> getItems() {
        return items;
    }

    public void setItems(List<Notice> items) {
        this.items = items;
    }

    public static class Notice{
        /**
         * "id": 54,
         * "title": "tests11",//标题
         * "content": "tests11",//内容
         * "datetime": "2021-06-03 13:55:00",//推送时间
         * "type": "notice",//通知类型
         * "is_read": 0,//是否已读
         * "tags": "urgent",//紧急公告
         * "link_address": "www.baidu.om"//公告链接地址
         */

        private int id;
        private String title;
        private String content;
        private String datetime;
        private String type;
        private int is_read;
        private String tags;
        private String link_address;

        public boolean isUrgent() {
            return "urgent".equals(tags);
        }

        public boolean isRead() {
            return is_read == 1;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getIs_read() {
            return is_read;
        }

        public void setIs_read(int is_read) {
            this.is_read = is_read;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getLink_address() {
            return link_address;
        }

        public void setLink_address(String link_address) {
            this.link_address = link_address;
        }
    }
}
