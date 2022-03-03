package com.u1.gocashm.model;


public interface KeyValuePhModel {
    String getKey();

    String getValue();

    String getCnName();

    class BaseKeyValuePhModel implements KeyValuePhModel {
        private String key;
        private String value;
        private String nameCn;

        public BaseKeyValuePhModel(String key, String value, String nameCn) {
            this.key = key;
            this.value = value;
            this.nameCn = nameCn;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public String getCnName() {
            return nameCn;
        }
    }
}
