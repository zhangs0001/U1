package com.u1.gocashm.model.response;



import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

import java.io.Serializable;
import java.util.List;

public class OrderDetailModel extends AbstractBaseRespBean<Status, OrderDetailModel.OrderDetail> {

    public static class OrderDetail implements Serializable {

        private int id;
        private String order_no;
        private String principal;
        private int loan_days;
        private String status;
        private String created_at;
        private String daily_rate;
        private String signed_time;
        private String paid_time;
        private String paid_amount;
        private String pay_channel;
        private String api_status;
        private Object api_status_text;
        private Object id_text;
        private String processing_fee;
        private String interest_fee;
        private String overdue_fee;
        private String receivable_amount;
        private String amount_due;
        private String part_repay_amount;
        private String overdue_days;
        private String appointment_paid_time;
        private String repay_time;
        private String repay_amount;
        private String reduction_fee;
        private boolean can_cancel;
        private boolean can_renewal;
        private String renewal_created_at;
        private boolean has_renewal;
        private int renewal_days;
        private double renewal_fee;
        private OrderDetailBean order_detail;
        private boolean show_part_repay;
        private String min_part_repay;
        private boolean can_part_repay;
        private Object order_no_text;
        private String status_text;
        private String daily_rate_text;
        private String gst_processing_fee;
        private RepaymentPlan first_progressing_repayment_plan;
        private List<RepaymentPlan> repayment_plans;
        private User user;
        private String reference_no;
        private String rejected_days_left;
        private String withdraw_no;

        public String getWithdraw_no() {
            return withdraw_no;
        }

        public void setWithdraw_no(String withdraw_no) {
            this.withdraw_no = withdraw_no;
        }

        public String getGst_processing_fee() {
            return gst_processing_fee;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getPrincipal() {
            return principal;
        }

        public void setPrincipal(String principal) {
            this.principal = principal;
        }

        public int getLoan_days() {
            return loan_days;
        }

        public void setLoan_days(int loan_days) {
            this.loan_days = loan_days;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getDaily_rate() {
            return daily_rate;
        }

        public void setDaily_rate(String daily_rate) {
            this.daily_rate = daily_rate;
        }

        public String getSigned_time() {
            return signed_time;
        }

        public void setSigned_time(String signed_time) {
            this.signed_time = signed_time;
        }

        public String getPaid_time() {
            return paid_time;
        }

        public void setPaid_time(String paid_time) {
            this.paid_time = paid_time;
        }

        public String getPaid_amount() {
            return paid_amount;
        }

        public void setPaid_amount(String paid_amount) {
            this.paid_amount = paid_amount;
        }

        public String getPay_channel() {
            return pay_channel;
        }

        public void setPay_channel(String pay_channel) {
            this.pay_channel = pay_channel;
        }

        public String getApi_status() {
            switch (api_status) {
                case "PENDING":
                    return "Verifying";
                case "PAYING":
                    return "Wait for Disbursement";
                case "REJECTED":
                    return "Application Failed";
                case "PAID":
                    return "Wait for repayment";
                case "FINISHED":
                    return "Cleared";
                case "CANCEL":
                    return "Application Failed";
            }
            return api_status;
        }

        public String getOApi_status() {
            return api_status;
        }

        public void setApi_status(String api_status) {
            this.api_status = api_status;
        }

        public Object getApi_status_text() {
            return api_status_text;
        }

        public void setApi_status_text(Object api_status_text) {
            this.api_status_text = api_status_text;
        }

        public Object getId_text() {
            return id_text;
        }

        public void setId_text(Object id_text) {
            this.id_text = id_text;
        }

        public String getProcessing_fee() {
            return processing_fee;
        }

        public void setProcessing_fee(String processing_fee) {
            this.processing_fee = processing_fee;
        }

        public String getInterest_fee() {
            return interest_fee;
        }

        public void setInterest_fee(String interest_fee) {
            this.interest_fee = interest_fee;
        }

        public String getOverdue_fee() {
            return overdue_fee;
        }

        public void setOverdue_fee(String overdue_fee) {
            this.overdue_fee = overdue_fee;
        }

        public String getReceivable_amount() {
            return receivable_amount;
        }

        public void setReceivable_amount(String receivable_amount) {
            this.receivable_amount = receivable_amount;
        }

        public String getAmount_due() {
            return amount_due;
        }

        public void setAmount_due(String amount_due) {
            this.amount_due = amount_due;
        }

        public String getPart_repay_amount() {
            return part_repay_amount;
        }

        public void setPart_repay_amount(String part_repay_amount) {
            this.part_repay_amount = part_repay_amount;
        }

        public String getOverdue_days() {
            return overdue_days;
        }

        public void setOverdue_days(String overdue_days) {
            this.overdue_days = overdue_days;
        }

        public String getAppointment_paid_time() {
            return appointment_paid_time;
        }

        public void setAppointment_paid_time(String appointment_paid_time) {
            this.appointment_paid_time = appointment_paid_time;
        }

        public String getRepay_time() {
            return repay_time;
        }

        public void setRepay_time(String repay_time) {
            this.repay_time = repay_time;
        }

        public String getRepay_amount() {
            return repay_amount;
        }

        public void setRepay_amount(String repay_amount) {
            this.repay_amount = repay_amount;
        }

        public String getReduction_fee() {
            return reduction_fee;
        }

        public void setReduction_fee(String reduction_fee) {
            this.reduction_fee = reduction_fee;
        }

        public boolean isCan_cancel() {
            return can_cancel;
        }

        public void setCan_cancel(boolean can_cancel) {
            this.can_cancel = can_cancel;
        }

        public boolean isCan_renewal() {
            return can_renewal;
        }

        public void setCan_renewal(boolean can_renewal) {
            this.can_renewal = can_renewal;
        }

        public String getRenewal_created_at() {
            return renewal_created_at;
        }

        public void setRenewal_created_at(String renewal_created_at) {
            this.renewal_created_at = renewal_created_at;
        }

        public boolean isHas_renewal() {
            return has_renewal;
        }

        public void setHas_renewal(boolean has_renewal) {
            this.has_renewal = has_renewal;
        }

        public int getRenewal_days() {
            return renewal_days;
        }

        public void setRenewal_days(int renewal_days) {
            this.renewal_days = renewal_days;
        }

        public double getRenewal_fee() {
            return renewal_fee;
        }

        public void setRenewal_fee(double renewal_fee) {
            this.renewal_fee = renewal_fee;
        }

        public OrderDetailBean getOrder_detail() {
            return order_detail;
        }

        public void setOrder_detail(OrderDetailBean order_detail) {
            this.order_detail = order_detail;
        }

        public boolean isShow_part_repay() {
            return show_part_repay;
        }

        public void setShow_part_repay(boolean show_part_repay) {
            this.show_part_repay = show_part_repay;
        }

        public String getMin_part_repay() {
            return min_part_repay;
        }

        public void setMin_part_repay(String min_part_repay) {
            this.min_part_repay = min_part_repay;
        }

        public boolean isCan_part_repay() {
            return can_part_repay;
        }

        public void setCan_part_repay(boolean can_part_repay) {
            this.can_part_repay = can_part_repay;
        }

        public Object getOrder_no_text() {
            return order_no_text;
        }

        public void setOrder_no_text(Object order_no_text) {
            this.order_no_text = order_no_text;
        }

        public String getStatus_text() {
            switch (status_text) {
                case "PENDING":
                    return "Verifying";
                case "PAYING":
                    return "Wait for Disbursement";
                case "REJECTED":
                    return "Application Failed";
                case "PAID":
                    return "Wait for repayment";
                case "FINISHED":
                    return "Cleared";
                case "CANCEL":
                    return "Application Failed";
            }
            return status_text;
        }

        public void setStatus_text(String status_text) {
            this.status_text = status_text;
        }

        public String getDaily_rate_text() {
            return daily_rate_text;
        }

        public void setDaily_rate_text(String daily_rate_text) {
            this.daily_rate_text = daily_rate_text;
        }

        public RepaymentPlan getFirst_progressing_repayment_plan() {
            return first_progressing_repayment_plan;
        }

        public void setFirst_progressing_repayment_plan(RepaymentPlan first_progressing_repayment_plan) {
            this.first_progressing_repayment_plan = first_progressing_repayment_plan;
        }

        public List<RepaymentPlan> getRepayment_plans() {
            return repayment_plans;
        }

        public void setRepayment_plans(List<RepaymentPlan> repayment_plans) {
            this.repayment_plans = repayment_plans;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public String getReference_no() {
            return reference_no;
        }

        public void setReference_no(String reference_no) {
            this.reference_no = reference_no;
        }

        public String getRejected_days_left() {
            return rejected_days_left;
        }

        public void setRejected_days_left(String rejected_days_left) {
            this.rejected_days_left = rejected_days_left;
        }

        public static class OrderDetailBean implements Serializable{

            private String loan_location;
            private String loan_position;
            private String loan_ip;
            private String processing_rate;
            private String gst_processing_rate;
            private String gst_penalty_rate;
            private String imei;
            private String installment;
            private String last_installment_free_on;
            private String loan_reason;
            private String intention_principal;
            private String intention_loan_days;
            private String bank_card_no;
            private String bank_name;

            public String getLoan_location() {
                return loan_location;
            }

            public void setLoan_location(String loan_location) {
                this.loan_location = loan_location;
            }

            public String getLoan_position() {
                return loan_position;
            }

            public void setLoan_position(String loan_position) {
                this.loan_position = loan_position;
            }

            public String getLoan_ip() {
                return loan_ip;
            }

            public void setLoan_ip(String loan_ip) {
                this.loan_ip = loan_ip;
            }

            public String getProcessing_rate() {
                return processing_rate;
            }

            public void setProcessing_rate(String processing_rate) {
                this.processing_rate = processing_rate;
            }

            public String getGst_processing_rate() {
                return gst_processing_rate;
            }

            public void setGst_processing_rate(String gst_processing_rate) {
                this.gst_processing_rate = gst_processing_rate;
            }

            public String getGst_penalty_rate() {
                return gst_penalty_rate;
            }

            public void setGst_penalty_rate(String gst_penalty_rate) {
                this.gst_penalty_rate = gst_penalty_rate;
            }

            public String getImei() {
                return imei;
            }

            public void setImei(String imei) {
                this.imei = imei;
            }

            public String getInstallment() {
                return installment;
            }

            public void setInstallment(String installment) {
                this.installment = installment;
            }

            public String getLast_installment_free_on() {
                return last_installment_free_on;
            }

            public void setLast_installment_free_on(String last_installment_free_on) {
                this.last_installment_free_on = last_installment_free_on;
            }

            public String getLoan_reason() {
                return loan_reason;
            }

            public void setLoan_reason(String loan_reason) {
                this.loan_reason = loan_reason;
            }

            public String getIntention_principal() {
                return intention_principal;
            }

            public void setIntention_principal(String intention_principal) {
                this.intention_principal = intention_principal;
            }

            public String getIntention_loan_days() {
                return intention_loan_days;
            }

            public void setIntention_loan_days(String intention_loan_days) {
                this.intention_loan_days = intention_loan_days;
            }

            public String getBank_card_no() {
                return bank_card_no;
            }

            public void setBank_card_no(String bank_card_no) {
                this.bank_card_no = bank_card_no;
            }

            public String getBank_name() {
                return bank_name;
            }

            public void setBank_name(String bank_name) {
                this.bank_name = bank_name;
            }

        }

        public static class RepaymentPlan implements Serializable{
            private int id;
            private int merchant_id;
            private int user_id;
            private int order_id;
            private String no;
            private int status;
            private int overdue_days;
            private String appointment_paid_time;
            private String repay_time;
            private String repay_amount;
            private String repay_channel;
            private String reduction_fee;
            private String created_at;
            private String updated_at;
            private String reduction_valid_date;
            private String principal;
            private String interest_fee;
            private String overdue_fee;
            private String gst_processing;
            private String gst_penalty;
            private int installment_num;
            private String repay_proportion;
            private int repay_days;
            private int loan_days;
            private Object part_repay_amount;
            private Object can_part_repay;
            private Object ost_prncp;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getMerchant_id() {
                return merchant_id;
            }

            public void setMerchant_id(int merchant_id) {
                this.merchant_id = merchant_id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }

            public String getNo() {
                return no;
            }

            public void setNo(String no) {
                this.no = no;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getOverdue_days() {
                return overdue_days;
            }

            public void setOverdue_days(int overdue_days) {
                this.overdue_days = overdue_days;
            }

            public String getAppointment_paid_time() {
                return appointment_paid_time;
            }

            public void setAppointment_paid_time(String appointment_paid_time) {
                this.appointment_paid_time = appointment_paid_time;
            }

            public String getRepay_time() {
                return repay_time;
            }

            public void setRepay_time(String repay_time) {
                this.repay_time = repay_time;
            }

            public String getRepay_amount() {
                return repay_amount;
            }

            public void setRepay_amount(String repay_amount) {
                this.repay_amount = repay_amount;
            }

            public String getRepay_channel() {
                return repay_channel;
            }

            public void setRepay_channel(String repay_channel) {
                this.repay_channel = repay_channel;
            }

            public String getReduction_fee() {
                return reduction_fee;
            }

            public void setReduction_fee(String reduction_fee) {
                this.reduction_fee = reduction_fee;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public String getReduction_valid_date() {
                return reduction_valid_date;
            }

            public void setReduction_valid_date(String reduction_valid_date) {
                this.reduction_valid_date = reduction_valid_date;
            }

            public String getPrincipal() {
                return principal;
            }

            public void setPrincipal(String principal) {
                this.principal = principal;
            }

            public String getInterest_fee() {
                return interest_fee;
            }

            public void setInterest_fee(String interest_fee) {
                this.interest_fee = interest_fee;
            }

            public String getOverdue_fee() {
                return overdue_fee;
            }

            public void setOverdue_fee(String overdue_fee) {
                this.overdue_fee = overdue_fee;
            }

            public String getGst_processing() {
                return gst_processing;
            }

            public void setGst_processing(String gst_processing) {
                this.gst_processing = gst_processing;
            }

            public String getGst_penalty() {
                return gst_penalty;
            }

            public void setGst_penalty(String gst_penalty) {
                this.gst_penalty = gst_penalty;
            }

            public int getInstallment_num() {
                return installment_num;
            }

            public void setInstallment_num(int installment_num) {
                this.installment_num = installment_num;
            }

            public String getRepay_proportion() {
                return repay_proportion;
            }

            public void setRepay_proportion(String repay_proportion) {
                this.repay_proportion = repay_proportion;
            }

            public int getRepay_days() {
                return repay_days;
            }

            public void setRepay_days(int repay_days) {
                this.repay_days = repay_days;
            }

            public int getLoan_days() {
                return loan_days;
            }

            public void setLoan_days(int loan_days) {
                this.loan_days = loan_days;
            }

            public Object getPart_repay_amount() {
                return part_repay_amount;
            }

            public void setPart_repay_amount(Object part_repay_amount) {
                this.part_repay_amount = part_repay_amount;
            }

            public Object getCan_part_repay() {
                return can_part_repay;
            }

            public void setCan_part_repay(Object can_part_repay) {
                this.can_part_repay = can_part_repay;
            }

            public Object getOst_prncp() {
                return ost_prncp;
            }

            public void setOst_prncp(Object ost_prncp) {
                this.ost_prncp = ost_prncp;
            }
        }
    }


}
