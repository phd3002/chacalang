package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.dto.OrderCheckoutDTO;
import com.thungcam.chacalang.entity.*;
import com.thungcam.chacalang.enums.OrderStatus;
import com.thungcam.chacalang.enums.PaymentStatus;
import com.thungcam.chacalang.enums.ShippingMethod;
import com.thungcam.chacalang.repository.*;
import com.thungcam.chacalang.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    final private OrderRepository orderRepository;

    final private OrderItemRepository orderItemRepository;

    final private CartService cartService;

    final private InvoiceRepository invoiceRepository;

    final private PaymentMethodRepository paymentMethodRepository;

    private final MailService mailService;

    private final UserAddressService userAddressService;

    private final BranchRepository branchRepository;

    private final BranchDistrictRepository branchDistrictRepository;

    @Transactional
    @Override
    public Orders createOrder(OrderCheckoutDTO dto, User user) {
        List<CartItem> cartItems = cartService.getCartItems(user.getId());
        if (cartItems.isEmpty()) throw new IllegalStateException("Giỏ hàng trống");

        // Tính phí và tổng
        BigDecimal shippingFee = "pickup".equals(dto.getShippingMethod()) ? BigDecimal.ZERO : new BigDecimal("10000");
        BigDecimal subtotal = cartItems.stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal total = subtotal.add(shippingFee);

        // Tìm payment method
        PaymentMethod paymentMethod = paymentMethodRepository.findById(dto.getPaymentMethod())
                .orElseThrow(() -> new RuntimeException("Phương thức thanh toán không hợp lệ"));

        // Tạo đơn hàng
        Orders order = new Orders();
        order.setUser(user);
        order.setCustomerEmail(user.getEmail());
        order.setOrderCode("HD" + System.currentTimeMillis());
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        ShippingMethod method = ShippingMethod.valueOf(dto.getShippingMethod().trim().toUpperCase());
        order.setShippingMethod(method);

        if (method == ShippingMethod.DELIVERY) {
            if (dto.getAddressId() == null)
                throw new RuntimeException("Vui lòng chọn địa chỉ giao hàng");

            UserAddress address = userAddressService.getAddressById(dto.getAddressId());
            if (address == null) throw new RuntimeException("Địa chỉ không tồn tại");

            District district = address.getDistrict();
            BranchDistrict branchDistrict = branchDistrictRepository.findFirstByDistrict(district);
            if (branchDistrict == null) {
                throw new RuntimeException("Không tìm thấy chi nhánh giao cho quận này");
            }
            Branch branch = branchDistrict.getBranch();
            order.setCustomerName(address.getFullName());
            order.setCustomerPhone(address.getPhone());
            order.setCustomerAddress(address.getAddress());
            order.setWard(String.valueOf(address.getWard().getName()));
            order.setDistrict(String.valueOf(address.getDistrict().getName()));
            order.setCity(address.getCity());
            order.setBranch(branch);
        } else {
            if (dto.getBranchId() == null)
                throw new RuntimeException("Vui lòng chọn chi nhánh cửa hàng");
            Branch branch = branchRepository.findById(dto.getBranchId())
                    .orElseThrow(() -> new RuntimeException("Chi nhánh không tồn tại"));
            order.setCustomerName(dto.getCustomerName());
            order.setCustomerPhone(dto.getCustomerPhone());
            order.setBranch(branch);
            order.setCustomerAddress(branch.getName() + " - " + branch.getAddress());
        }


        orderRepository.save(order);

        // Lưu từng món
        for (CartItem ci : cartItems) {
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setMenu(ci.getMenu());
            item.setQuantity(ci.getQuantity());
            item.setPrice(ci.getPrice());
            item.setSubtotal(ci.getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())));
            item.setCreatedAt(LocalDateTime.now());
            orderItemRepository.save(item);
        }

        // Tạo hóa đơn
        Invoice invoice = new Invoice();
        invoice.setOrder(order);
        invoice.setInvoiceCode("INV" + System.currentTimeMillis());
        invoice.setIssuedDate(LocalDateTime.now());
        invoice.setShippingFee(shippingFee);
        invoice.setTaxAmount(BigDecimal.ZERO);
        invoice.setDiscountAmount(BigDecimal.ZERO);
        invoice.setTotalAmount(total);
        invoice.setFinalAmount(total.add(shippingFee));
        invoice.setPaymentMethod(paymentMethod);
        invoice.setPaymentStatus(PaymentStatus.PENDING);
        invoice.setNotes("Tạo từ đơn hàng #" + order.getOrderCode());

        invoiceRepository.save(invoice);

        mailService.sendOrderConfirmation(order);

        cartService.clearCart(user.getId());

        return order;
    }
}