package com.u1.gocashm.model.response;



import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

import java.util.List;

/**
 * @author hewei
 * @date 2018/11/9 0009 上午 11:45
 */
public class CouponModel extends AbstractBaseRespBean<Status, List<CouponModel.Coupon>> {


    public static class Coupon {
        private boolean isSelected;
        private int id;
        private String title;
        private int coupon_type;
        private int used_amount;
        private int usage;
        private int with_amount;
        private int overdue_use;
        private String end_time;
        private int status;
        private int create_user;
        private String created_at;
        private int update_user;
        private String updated_at;
        private int used;
        private String use_time;
        private String coupon_id;
        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
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

        public int getCoupon_type() {
            return coupon_type;
        }

        public void setCoupon_type(int coupon_type) {
            this.coupon_type = coupon_type;
        }

        public int getUsed_amount() {
            return used_amount;
        }

        public void setUsed_amount(int used_amount) {
            this.used_amount = used_amount;
        }

        public int getUsage() {
            return usage;
        }

        public void setUsage(int usage) {
            this.usage = usage;
        }

        public int getWith_amount() {
            return with_amount;
        }

        public void setWith_amount(int with_amount) {
            this.with_amount = with_amount;
        }

        public int getOverdue_use() {
            return overdue_use;
        }

        public void setOverdue_use(int overdue_use) {
            this.overdue_use = overdue_use;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getCreate_user() {
            return create_user;
        }

        public void setCreate_user(int create_user) {
            this.create_user = create_user;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getUpdate_user() {
            return update_user;
        }

        public void setUpdate_user(int update_user) {
            this.update_user = update_user;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public int getUsed() {
            return used;
        }

        public void setUsed(int used) {
            this.used = used;
        }

        public String getUse_time() {
            return use_time;
        }

        public void setUse_time(String use_time) {
            this.use_time = use_time;
        }

        public String getCoupon_id() {
            return coupon_id;
        }

        public void setCoupon_id(String coupon_id) {
            this.coupon_id = coupon_id;
        }
    }
}
