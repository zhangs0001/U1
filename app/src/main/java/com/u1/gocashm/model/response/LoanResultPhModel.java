package com.u1.gocashm.model.response;

import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

import java.io.Serializable;
import java.util.List;

/**
 * @author hewei
 * @date 2018/9/7 0007 下午 5:00
 */
public class LoanResultPhModel extends AbstractBaseRespBean<Status, LoanResultPhModel.LoanResult> {

    public static class LoanResult {
        private String applyId;
        private int id;
        private String order_no;
        private String principal;
        private int loan_days;
        private String status;
        private String applyStatus;
        private String created_at;
        private String daily_rate;
        private String signed_time;
        private String paid_time;
        private String paid_amount;
        private String pay_channel;
        private String api_status;
        private Object id_text;
        private String penalty_fee;
        private String processing_rate;
        private String gst_processing_rate;
        private String gst_penalty_rate;
        private String gst_processing_fee;
        private String gst_penalty_fee;
        private String processing_fee_incl_gst;
        private String overdue_fee_incl_gst;
        private TipBean tip;
        private Object last_trade_amount;
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
        private int renewal_fee;
        private boolean is_installment;
        private int installment_count;
        private boolean can_deduction;
        private int current_installment_num;
        private boolean show_part_repay;
        private String min_part_repay;
        private boolean can_part_repay;
        private Object order_no_text;
        private String status_text;
        private String daily_rate_text;
        private String waitAuthMsg;
        private int rejectedTimeLeft;
        private Object first_progressing_repayment_plan;
        private Object trade_log_repay_success;
        private Last_trade_log last_trade_log;
        private List<?> waitAuths;
        private List<RepaymentPlan> repaymentPlans;
        private List<?> repayment_plan_renewal;
        private int it_service_signed;
        private int it_service_need;

        public int getIt_service_need() {
            return it_service_need;
        }

        public void setIt_service_need(int it_service_need) {
            this.it_service_need = it_service_need;
        }

        public int getIt_service_signed() {
            return it_service_signed;
        }

        public void setIt_service_signed(int it_service_signed) {
            this.it_service_signed = it_service_signed;
        }

        public String getApplyStatus() {
            return applyStatus;
        }

        public void setApplyStatus(String applyStatus) {
            this.applyStatus = applyStatus;
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
            return api_status;
        }

        public void setApi_status(String api_status) {
            this.api_status = api_status;
        }

        public Object getId_text() {
            return id_text;
        }

        public void setId_text(Object id_text) {
            this.id_text = id_text;
        }

        public String getPenalty_fee() {
            return penalty_fee;
        }

        public void setPenalty_fee(String penalty_fee) {
            this.penalty_fee = penalty_fee;
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

        public String getGst_processing_fee() {
            return gst_processing_fee;
        }

        public void setGst_processing_fee(String gst_processing_fee) {
            this.gst_processing_fee = gst_processing_fee;
        }

        public String getGst_penalty_fee() {
            return gst_penalty_fee;
        }

        public void setGst_penalty_fee(String gst_penalty_fee) {
            this.gst_penalty_fee = gst_penalty_fee;
        }

        public String getProcessing_fee_incl_gst() {
            return processing_fee_incl_gst;
        }

        public void setProcessing_fee_incl_gst(String processing_fee_incl_gst) {
            this.processing_fee_incl_gst = processing_fee_incl_gst;
        }

        public String getOverdue_fee_incl_gst() {
            return overdue_fee_incl_gst;
        }

        public void setOverdue_fee_incl_gst(String overdue_fee_incl_gst) {
            this.overdue_fee_incl_gst = overdue_fee_incl_gst;
        }

        public TipBean getTip() {
            return tip;
        }

        public void setTip(TipBean tip) {
            this.tip = tip;
        }

        public Object getLast_trade_amount() {
            return last_trade_amount;
        }

        public void setLast_trade_amount(Object last_trade_amount) {
            this.last_trade_amount = last_trade_amount;
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

        public int getRenewal_fee() {
            return renewal_fee;
        }

        public void setRenewal_fee(int renewal_fee) {
            this.renewal_fee = renewal_fee;
        }

        public boolean isIs_installment() {
            return is_installment;
        }

        public void setIs_installment(boolean is_installment) {
            this.is_installment = is_installment;
        }

        public int getInstallment_count() {
            return installment_count;
        }

        public void setInstallment_count(int installment_count) {
            this.installment_count = installment_count;
        }

        public boolean isCan_deduction() {
            return can_deduction;
        }

        public void setCan_deduction(boolean can_deduction) {
            this.can_deduction = can_deduction;
        }

        public int getCurrent_installment_num() {
            return current_installment_num;
        }

        public void setCurrent_installment_num(int current_installment_num) {
            this.current_installment_num = current_installment_num;
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

        public String getWaitAuthMsg() {
            return waitAuthMsg;
        }

        public void setWaitAuthMsg(String waitAuthMsg) {
            this.waitAuthMsg = waitAuthMsg;
        }

        public int getRejectedTimeLeft() {
            return rejectedTimeLeft;
        }

        public void setRejectedTimeLeft(int rejectedTimeLeft) {
            this.rejectedTimeLeft = rejectedTimeLeft;
        }

        public Object getFirst_progressing_repayment_plan() {
            return first_progressing_repayment_plan;
        }

        public void setFirst_progressing_repayment_plan(Object first_progressing_repayment_plan) {
            this.first_progressing_repayment_plan = first_progressing_repayment_plan;
        }

        public Object getTrade_log_repay_success() {
            return trade_log_repay_success;
        }

        public void setTrade_log_repay_success(Object trade_log_repay_success) {
            this.trade_log_repay_success = trade_log_repay_success;
        }

        public Last_trade_log getLast_trade_log() {
            return last_trade_log;
        }

        public void setLast_trade_log(Last_trade_log last_trade_log) {
            this.last_trade_log = last_trade_log;
        }

        public List<?> getWaitAuths() {
            return waitAuths;
        }

        public void setWaitAuths(List<?> waitAuths) {
            this.waitAuths = waitAuths;
        }

        public List<RepaymentPlan> getRepaymentPlans() {
            return repaymentPlans;
        }

        public void setRepaymentPlans(List<RepaymentPlan> repaymentPlans) {
            this.repaymentPlans = repaymentPlans;
        }

        public List<?> getRepayment_plan_renewal() {
            return repayment_plan_renewal;
        }

        public void setRepayment_plan_renewal(List<?> repayment_plan_renewal) {
            this.repayment_plan_renewal = repayment_plan_renewal;
        }

        public String getApplyId() {
            return applyId;
        }

        public void setApplyId(String applyId) {
            this.applyId = applyId;
        }

        public static class TipBean {
            /**
             * gstProcessingFeeTip : 18 %  on processing fee
             * gstPenaltyFeeTip : 18 %  on penalty fee
             * deductedTip : deducted from loan amount
             * chargingTip : [{"title":"Rate of Interest","content":"Our rate of interest is 0.1% p.d. Cash now calculate the interest by day. If you repay early, you only need to pay interest only till the date you pay."},{"title":"Processing Fee","content":"It will be 10% of the loan amount. For a example, we will charge Rs300 for a loan Rs 3000.00.The fee will be deducted before the loan disbursement."},{"title":"GST (18 % on processing fee)","content":"GST@18 % is levied in all fee. For a example, we will charge Rs54 for a processing fee Rs 300.The fee will be deducted before the loan disbursement."},{"title":"Penalty Fee","content":"Penalty fee will be charged at 0.5% p.d. On the total over due amount on dais basis.Meanwile, GST@18 % is levied in penalty fee.                         Remember overdue is for 3 days overdue ,We will revise your CBIL Report ,But it will effet your individual credit."},{"title":"GST(penalty fee)","content":"GST@18 % is levied in penalty fee"}]
             */

            private String gstProcessingFeeTip;
            private String gstPenaltyFeeTip;
            private String deductedTip;
            private List<ChargingTipBean> chargingTip;

            public String getGstProcessingFeeTip() {
                return gstProcessingFeeTip;
            }

            public void setGstProcessingFeeTip(String gstProcessingFeeTip) {
                this.gstProcessingFeeTip = gstProcessingFeeTip;
            }

            public String getGstPenaltyFeeTip() {
                return gstPenaltyFeeTip;
            }

            public void setGstPenaltyFeeTip(String gstPenaltyFeeTip) {
                this.gstPenaltyFeeTip = gstPenaltyFeeTip;
            }

            public String getDeductedTip() {
                return deductedTip;
            }

            public void setDeductedTip(String deductedTip) {
                this.deductedTip = deductedTip;
            }

            public List<ChargingTipBean> getChargingTip() {
                return chargingTip;
            }

            public void setChargingTip(List<ChargingTipBean> chargingTip) {
                this.chargingTip = chargingTip;
            }

            public static class ChargingTipBean {
                /**
                 * title : Rate of Interest
                 * content : Our rate of interest is 0.1% p.d. Cash now calculate the interest by day. If you repay early, you only need to pay interest only till the date you pay.
                 */

                private String title;
                private String content;

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
            }

        }

        public static class Last_trade_log {

            private String business_amount;
            private String bank_name;
            private String trade_account_no;
            private String trade_desc;
            private String trade_request_time;

            public String getBusiness_amount() {
                return business_amount;
            }

            public void setBusiness_amount(String business_amount) {
                this.business_amount = business_amount;
            }

            public String getBank_name() {
                return bank_name;
            }

            public void setBank_name(String bank_name) {
                this.bank_name = bank_name;
            }

            public String getTrade_account_no() {
                return trade_account_no;
            }

            public void setTrade_account_no(String trade_account_no) {
                this.trade_account_no = trade_account_no;
            }

            public String getTrade_desc() {
                return trade_desc;
            }

            public void setTrade_desc(String trade_desc) {
                this.trade_desc = trade_desc;
            }

            public String getTrade_request_time() {
                return trade_request_time;
            }

            public void setTrade_request_time(String trade_request_time) {
                this.trade_request_time = trade_request_time;
            }
        }
    }

    public static class RepaymentPlan implements Serializable {
        private String repayDate;
        private String repayAmount;
        private int installment_num;
        private String svcFee;
        private int interest;

        public String getRepayDate() {
            return repayDate;
        }

        public void setRepayDate(String repayDate) {
            this.repayDate = repayDate;
        }

        public String getRepayAmount() {
            return repayAmount;
        }

        public void setRepayAmount(String repayAmount) {
            this.repayAmount = repayAmount;
        }

        public int getInstallment_num() {
            return installment_num;
        }

        public void setInstallment_num(int installment_num) {
            this.installment_num = installment_num;
        }

        public String getSvcFee() {
            return svcFee;
        }

        public void setSvcFee(String svcFee) {
            this.svcFee = svcFee;
        }

        public int getInterest() {
            return interest;
        }

        public void setInterest(int interest) {
            this.interest = interest;
        }
    }
}
