package com.u1.gocashm.model.request;

public class ContactsBody {


        /**
         * dbTen : qwer1
         * dbDienthoai : 123456
         */

        private String p;//dttype   DIAL-主叫，DIALED-被叫
        private String n;//姓名
        private String t;//手机号
        private String d;//通话时长
        private String i;//通话时间

        public ContactsBody(String p, String n, String t, String d, String i) {
            this.p = p;
            this.n = n;
            this.t = t;
            this.d = d;
            this.i = i;
        }

        @Override
        public String toString() {
            return "Contacts1Bean{" +
                    "p='" + p + '\'' +
                    ", n='" + n + '\'' +
                    ", t='" + t + '\'' +
                    ", d='" + d + '\'' +
                    ", i='" + i + '\'' +
                    '}';
        }
    }
