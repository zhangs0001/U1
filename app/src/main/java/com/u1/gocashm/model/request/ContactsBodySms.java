package com.u1.gocashm.model.request;

public class ContactsBodySms {
        /**
         * dbTen : qwer1
         * dbDienthoai : 123456
         */

        private String n;//电话号码
        private String b;//短信内容
        private String d;//短信时间

        public ContactsBodySms(String n, String b, String d) {
            this.n = n;
            this.b = b;
            this.d = d;
        }

        @Override
        public String toString() {
            return "SmsBean{" +
                    "n='" + n + '\'' +
                    ", b='" + b + '\'' +
                    ", d='" + d + '\'' +
                    '}';
        }
    }
