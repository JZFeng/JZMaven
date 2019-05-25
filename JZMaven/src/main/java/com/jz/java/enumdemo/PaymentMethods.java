package com.jz.java.enumdemo;

public enum PaymentMethods {


    NotSelected("NotSelected", "NSC", -3),
    ReqTotal("ReqTotal", "RQT", -2);

    private final String paymentCode;
    private final String details;
    private final int index;

    private PaymentMethods(String paymentCode, String details, int index) {
        this.paymentCode = paymentCode;
        this.details = details;
        this.index = index;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public String getDetails() {
        return details;
    }

    public int getIndex() {
        return index;
    }

    class PaymentMethod {

    }


}
