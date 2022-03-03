package com.u1.gocashm.model.response;

import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

import java.io.Serializable;

/**
 * @author hewei
 * @date 2019/12/23 0023 上午 10:50
 */
public class UserLevelModel extends AbstractBaseRespBean<Status, UserLevelModel.UserLevel> {

    public static class UserLevel implements Serializable {
        /**
         * 最小金额
         */
        private int minAmount;
        /**
         * 最大金额
         */
        private int maxAmount;

        /**
         * 级别
         */
        private Integer level;
        /**
         * 级别
         */
        private String levelName;

        public int getMinAmount() {
            return minAmount;
        }

        public void setMinAmount(int minAmount) {
            this.minAmount = minAmount;
        }

        public int getMaxAmount() {
            return maxAmount;
        }

        public void setMaxAmount(int maxAmount) {
            this.maxAmount = maxAmount;
        }

        public Integer getLevel() {
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }
    }
}
