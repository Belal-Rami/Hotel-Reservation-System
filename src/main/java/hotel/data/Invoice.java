package hotel.data;

import hotel.enums.PaymentMethod;
import java.time.LocalDate;

public class Invoice {
    private double totalAmount;
    private PaymentMethod paymentMethod;
    private LocalDate paymentDate;

    public Invoice() {
    }

    public Invoice(double totalAmount, PaymentMethod paymentMethod, LocalDate paymentDate) {
        setTotalAmount(totalAmount);
        setPaymentMethod(paymentMethod);
        setPaymentDate(paymentDate);
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        if (totalAmount < 0) {
            throw new IllegalArgumentException("Total amount cannot be negative");
        }
        this.totalAmount = totalAmount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Payment method cannot be empty");
        }
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        if (paymentDate == null) {
            throw new IllegalArgumentException("Payment date cannot be empty");
        }
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
        return "Total Amount: " + totalAmount +
                "\nPayment Method: " + paymentMethod +
                "\nPayment Date: " + paymentDate;
    }
}