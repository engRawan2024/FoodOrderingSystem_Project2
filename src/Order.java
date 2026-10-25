/*
 * ------------------------------------------------------------
 * Names: Jana Abdulrahman   | 445810234
 *        Hanenn Saeed Ahmad | 447802061
 *        Fai Ali Alqahtani  | 445810696
 * Group: 2
 * Project 2: Food Ordering Management System (OOP version)
 * Issues: None
 * AI Usage: Claude (AI) helped us write the code and the report.
 *           We reviewed the code, ran it in Eclipse and tested it.
 * ------------------------------------------------------------
 */

import java.util.ArrayList;

/**
 * One customer order. It has many order lines and calculates the bill.
 * Order implements the Payable interface.
 */
public class Order implements Payable {

    public static final int MAX_LINES = 10;                // different items in one order
    public static final double DELIVERY_FEE = 15.0;        // SAR
    public static final double FREE_DELIVERY_LIMIT = 150.0; // subtotal >= 150 gets free delivery
    public static final double VAT_RATE = 0.15;            // 15% VAT

    private int orderId;
    private Customer customer;
    private ArrayList<OrderLine> lines = new ArrayList<OrderLine>();
    private boolean cancelled;

    public Order(int orderId, Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("An order must have a customer.");
        }
        this.orderId = orderId;
        this.customer = customer;
        this.cancelled = false;
    }

    // ---------- getters ----------
    public int getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public int getNumberOfLines() {
        return lines.size();
    }

    public String getStatus() {
        return cancelled ? "Cancelled" : "Active";
    }

    // ---------- order changes ----------
    private void checkNotCancelled() {
        if (cancelled) {
            throw new IllegalArgumentException("Order " + orderId + " is cancelled and cannot be changed.");
        }
    }

    private OrderLine findLine(MenuItem item) {
        for (OrderLine line : lines) {
            if (line.getItem() == item) {
                return line;
            }
        }
        return null;
    }

    public void addItem(MenuItem item, int quantity) {
        checkNotCancelled();
        if (item == null) {
            throw new IllegalArgumentException("Item not found.");
        }
        if (!item.isAvailable()) {
            throw new IllegalArgumentException(item.getName() + " is not available now.");
        }
        OrderLine existing = findLine(item);
        if (existing != null) {
            // same item again: add to the old quantity (setQuantity checks the limit)
            existing.setQuantity(existing.getQuantity() + quantity);
        } else {
            if (lines.size() >= MAX_LINES) {
                throw new IllegalArgumentException("An order can have only " + MAX_LINES + " different items.");
            }
            lines.add(new OrderLine(item, quantity));
        }
    }

    public void changeQuantity(MenuItem item, int newQuantity) {
        checkNotCancelled();
        OrderLine line = findLine(item);
        if (line == null) {
            throw new IllegalArgumentException("This item is not in the order.");
        }
        line.setQuantity(newQuantity);
    }

    public void removeItem(MenuItem item) {
        checkNotCancelled();
        OrderLine line = findLine(item);
        if (line == null) {
            throw new IllegalArgumentException("This item is not in the order.");
        }
        lines.remove(line);
    }

    public void cancel() {
        checkNotCancelled();
        cancelled = true;
    }

    // ---------- Payable methods ----------
    @Override
    public double calculateSubtotal() {
        double subtotal = 0;
        for (OrderLine line : lines) {
            subtotal += line.getLineTotal();
        }
        return subtotal;
    }

    @Override
    public double calculateDiscount() {
        // the customer type decides the discount rate (polymorphism)
        return calculateSubtotal() * customer.getDiscountRate();
    }

    @Override
    public double calculateTax() {
        return (calculateSubtotal() - calculateDiscount()) * VAT_RATE;
    }

    @Override
    public double calculateDeliveryFee() {
        if (lines.isEmpty() || calculateSubtotal() >= FREE_DELIVERY_LIMIT) {
            return 0;
        }
        return DELIVERY_FEE;
    }

    @Override
    public double calculateFinalBill() {
        return calculateSubtotal() - calculateDiscount() + calculateTax() + calculateDeliveryFee();
    }

    // ---------- printing ----------
    public void printBill() {
        System.out.println("===== Order #" + orderId + " (" + getStatus() + ") =====");
        System.out.println("Name: " + customer.getName());
        System.out.println("KKU ID: " + customer.getId());
        System.out.println("Customer type: " + customer.getType());
        System.out.println("--------------------------------------------------------------");
        if (lines.isEmpty()) {
            System.out.println("No items in this order yet.");
            System.out.println("==============================================================");
            return;
        }
        System.out.printf("%-4s %-18s %-5s %-9s %-10s %s%n", "No.", "Item", "Qty", "Price", "Total", "Rule");
        for (int i = 0; i < lines.size(); i++) {
            OrderLine line = lines.get(i);
            MenuItem item = line.getItem();
            System.out.printf("%-4d %-18s %-5d %-9.2f %-10.2f %s%n", item.getCode(), item.getName(),
                    line.getQuantity(), item.getPrice(), line.getLineTotal(), item.getOfferDescription());
        }
        System.out.println("--------------------------------------------------------------");
        System.out.printf("Subtotal: %.2f SAR%n", calculateSubtotal());
        if (calculateDiscount() > 0) {
            System.out.printf("%s discount (%d%%): -%.2f SAR%n", customer.getType(),
                    (int) Math.round(customer.getDiscountRate() * 100), calculateDiscount());
        }
        System.out.printf("VAT (15%%): %.2f SAR%n", calculateTax());
        if (calculateDeliveryFee() == 0) {
            System.out.println("Delivery: FREE (subtotal is " + (int) FREE_DELIVERY_LIMIT + " SAR or more)");
        } else {
            System.out.printf("Delivery fee: %.2f SAR%n", calculateDeliveryFee());
        }
        System.out.printf("TOTAL: %.2f SAR%n", calculateFinalBill());
        System.out.println("==============================================================");
    }

    // one short line for the "display all orders" list
    public String getSummaryLine() {
        return String.format("#%-3d %-20s %-9s %-10s %-3d items   %8.2f SAR", orderId, customer.getName(),
                customer.getType(), getStatus(), lines.size(), calculateFinalBill());
    }
}
