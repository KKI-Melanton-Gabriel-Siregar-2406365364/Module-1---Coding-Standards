package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    Map<String, String> paymentDataVoucher;
    Map<String, String> paymentDataBank;
    Order order;

    @BeforeEach
    void setUp() {
        paymentDataVoucher = new HashMap<>();
        paymentDataVoucher.put("voucherCode", "ESHOP1234ABC5678");

        paymentDataBank = new HashMap<>();
        paymentDataBank.put("bankName", "BCA");
        paymentDataBank.put("referenceCode", "REF12345");

        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);

        order = new Order("13652556-012a-4c07-b546-54eb1396d79b", products, 1708560000L, "Safira Sudrajat");
    }

    @Test
    void testCreatePaymentVoucherSuccess() {
        Payment payment = new Payment("1", "VOUCHER", paymentDataVoucher, order);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherRejected_Not16Chars() {
        paymentDataVoucher.put("voucherCode", "ESHOP123");
        Payment payment = new Payment("2", "VOUCHER", paymentDataVoucher, order);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherRejected_NotStartWithEshop() {
        paymentDataVoucher.put("voucherCode", "DISC1234ABC56789");
        Payment payment = new Payment("3", "VOUCHER", paymentDataVoucher, order);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherRejected_Not8Numerics() {
        paymentDataVoucher.put("voucherCode", "ESHOP1234ABCDEFF");
        Payment payment = new Payment("4", "VOUCHER", paymentDataVoucher, order);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferSuccess() {
        Payment payment = new Payment("5", "BANK_TRANSFER", paymentDataBank, order);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferRejected_EmptyBankName() {
        paymentDataBank.put("bankName", "");
        Payment payment = new Payment("6", "BANK_TRANSFER", paymentDataBank, order);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferRejected_NullReferenceCode() {
        paymentDataBank.put("referenceCode", null);
        Payment payment = new Payment("7", "BANK_TRANSFER", paymentDataBank, order);
        assertEquals("REJECTED", payment.getStatus());
    }
}