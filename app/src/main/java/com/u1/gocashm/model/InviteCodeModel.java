package com.u1.gocashm.model;

public class InviteCodeModel extends AbstractBaseRespBean<Status,InviteCodeModel.InviteCode> {
    public static class InviteCode {
        private String invite_code;
        private String activity_time;
        private InviteAward invite_award;

        public String getInvite_code() {
            return invite_code;
        }

        public void setInvite_code(String invite_code) {
            this.invite_code = invite_code;
        }

        public String getActivity_time() {
            return activity_time;
        }

        public void setActivity_time(String activity_time) {
            this.activity_time = activity_time;
        }

        public InviteAward getInvite_award() {
            return invite_award;
        }

        public void setInvite_award(InviteAward invite_award) {
            this.invite_award = invite_award;
        }

        public static class InviteAward{
            private String  register;
            private String apply_a_loan;
            private String disbursement;
            private String on_time_repayment;

            public String getRegister() {
                return register;
            }

            public void setRegister(String register) {
                this.register = register;
            }

            public String getApply_a_loan() {
                return apply_a_loan;
            }

            public void setApply_a_loan(String apply_a_loan) {
                this.apply_a_loan = apply_a_loan;
            }

            public String getDisbursement() {
                return disbursement;
            }

            public void setDisbursement(String disbursement) {
                this.disbursement = disbursement;
            }

            public String getOn_time_repayment() {
                return on_time_repayment;
            }

            public void setOn_time_repayment(String on_time_repayment) {
                this.on_time_repayment = on_time_repayment;
            }
        }
    }
}

