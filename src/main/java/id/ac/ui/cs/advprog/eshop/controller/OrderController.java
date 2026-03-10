package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService; // Kita suntikkan PaymentService di sini

    @GetMapping("/create")
    public String createOrderPage(Model model) {
        return "createOrder";
    }

    @PostMapping("/create")
    public String createOrderPost(@RequestParam String author) {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        Order order = new Order(UUID.randomUUID().toString(), products, System.currentTimeMillis(), author);
        orderService.createOrder(order);
        return "redirect:/order/history";
    }

    @GetMapping("/history")
    public String historyOrderPage(Model model) {
        return "orderHistory";
    }

    @PostMapping("/history")
    public String historyOrderPost(@RequestParam String author, Model model) {
        List<Order> orders = orderService.findAllByAuthor(author);
        model.addAttribute("orders", orders);
        return "orderHistory";
    }

    @GetMapping("/pay/{orderId}")
    public String payOrderPage(@PathVariable String orderId, Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        return "paymentOrder";
    }

    @PostMapping("/pay/{orderId}")
    public String payOrderPost(@PathVariable String orderId, Model model) {
        Order order = orderService.findById(orderId);

        // 1. Buat data voucher dummy yang valid
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        // 2. Eksekusi pembuatan Payment
        Payment payment = paymentService.addPayment(order, "VOUCHER", paymentData);

        // 3. Ubah status Payment menjadi SUCCESS (otomatis mengubah Order jadi SUCCESS juga)
        paymentService.setStatus(payment, "SUCCESS");

        model.addAttribute("paymentId", payment.getId());
        return "paymentSuccess";
    }
}