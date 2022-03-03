package com.u1.gocashm.model.response;

import androidx.annotation.NonNull;

import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.KeyValuePhModel;
import com.u1.gocashm.model.Status;

import java.util.List;

/**
 * @author hewei
 * @date 2018/9/5 0005 下午 5:29
 */
public class DictionaryPhModel extends AbstractBaseRespBean<Status, List<DictionaryPhModel.Dictionary>> {

    public static class Dictionary implements KeyValuePhModel, Comparable<Dictionary> {

        private String createUser;
        private String createTime;
        private String updateUser;
        private String updateTime;
        private int version;
        private int dict_id;
        private String name;
        private String code;
        private String parentCode;
        private String nameCn;
        private String attr1;
        private String attr2;
        private String attr3;
        private int sort;
        private String remark;
        private String status;

        public String getAttr1() {
            return attr1;
        }

        public void setAttr1(String attr1) {
            this.attr1 = attr1;
        }

        public String getAttr2() {
            return attr2;
        }

        public void setAttr2(String attr2) {
            this.attr2 = attr2;
        }

        public String getAttr3() {
            return attr3;
        }

        public void setAttr3(String attr3) {
            this.attr3 = attr3;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public int getDict_id() {
            return dict_id;
        }

        public void setDict_id(int dict_id) {
            this.dict_id = dict_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNameCn() {
            return nameCn;
        }

        public void setNameCn(String nameCn) {
            this.nameCn = nameCn;
        }

        public String getParentCode() {
            return parentCode;
        }

        public void setParentCode(String parentCode) {
            this.parentCode = parentCode;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(String updateUser) {
            this.updateUser = updateUser;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        @Override
        public String getKey() {
            return code;
        }

        @Override
        public String getValue() {
            return name;
        }

        @Override
        public String getCnName() {
            return nameCn;
        }

        @Override
        public int compareTo(@NonNull Dictionary dictionary) {
            if (dictionary == null) {
                return this.dict_id;
            }
            return dict_id - dictionary.getDict_id();
        }

        @Override
        public String toString() {
            return "Dictionary{" +
                    "createUser='" + createUser + '\'' +
                    ", createTime='" + createTime + '\'' +
                    ", updateUser='" + updateUser + '\'' +
                    ", updateTime='" + updateTime + '\'' +
                    ", version=" + version +
                    ", dict_id=" + dict_id +
                    ", name='" + name + '\'' +
                    ", code='" + code + '\'' +
                    ", parentCode='" + parentCode + '\'' +
                    ", nameCn='" + nameCn + '\'' +
                    ", attr1='" + attr1 + '\'' +
                    ", attr2='" + attr2 + '\'' +
                    ", attr3='" + attr3 + '\'' +
                    ", sort=" + sort +
                    ", remark='" + remark + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }
}
