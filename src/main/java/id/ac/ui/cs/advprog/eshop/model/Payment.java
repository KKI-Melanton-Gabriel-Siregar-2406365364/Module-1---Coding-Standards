package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import java.util.Map;

@Getter
public class Payment {
    String id;
    String method;
    String status;
    Map<String, String> paymentData;
    Order order;

    public Payment(String id, String method, Map<String, String> paymentData, Order order) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.order = order;

        if ("VOUCHER".equals(method)) {
            String voucherCode = paymentData.get("voucherCode");
            boolean isValidVoucher = false;

            if (voucherCode != null && voucherCode.length() == 16 && voucherCode.startsWith("ESHOP")) {
                int numericCount = 0;
                for (char c : voucherCode.toCharArray()) {
                    if (Character.isDigit(c)) {
                        numericCount++;
                    }
                }
                if (numericCount == 8) {
                    isValidVoucher = true;
                }
            }

            if (isValidVoucher) {
                this.status = "SUCCESS";
            } else {
                this.status = "REJECTED";
            }

        } else if ("BANK_TRANSFER".equals(method)) {
            String bankName = paymentData.get("bankName");
            String referenceCode = paymentData.get("referenceCode");

            if (bankName == null || bankName.trim().isEmpty() || referenceCode == null || referenceCode.trim().isEmpty()) {
                this.status = "REJECTED";
            } else {
                this.status = "SUCCESS";
            }
        } else {
            this.status = "REJECTED";
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }
}