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
 * Manages the menu, the customers and the orders.
 * It implements the OrderOperations interface.
 */
public class OrderManager implements OrderOperations {

    private ArrayList<MenuItem> menu = new ArrayList<MenuItem>();
    private ArrayList<Customer> customers = new ArrayList<Customer>();
    private ArrayList<Order> orders = new ArrayList<Order>();
    private int nextOrderId = 1;

    // ---------- menu management ----------
    public boolean itemNameExists(String name) {
        for (MenuItem m : menu) {
            if (m.getName().equalsIgnoreCase(name.trim())) {
                return true;
            }
        }
        return false;
    }

    public void addMenuItem(MenuItem item) {
        if (itemNameExists(item.getName())) {
            throw new IllegalArgumentException("This item is already in the menu.");
        }
        menu.add(item);
    }

    public MenuItem findMenuItem(int code) {
        for (MenuItem m : menu) {
            if (m.getCode() == code) {
                return m;
            }
        }
        return null;
    }

    public ArrayList<MenuItem> getMenu() {
        return menu;
    }

    public void changeItemPrice(int code, double newPrice) {
        MenuItem item = findMenuItem(code);
        if (item == null) {
            throw new IllegalArgumentException("Item not found.");
        }
        item.setPrice(newPrice);
    }

    public void changeItemAvailability(int code, boolean available) {
        MenuItem item = findMenuItem(code);
        if (item == null) {
            throw new IllegalArgumentException("Item not found.");
        }
        item.setAvailable(available);
    }

    // ---------- customers ----------
    public Customer findCustomer(String id) {
        for (Customer c : customers) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public Customer registerCustomer(String name, String id, int type) {
        Customer c;
        if (type == 1) {
            c = new StudentCustomer(name, id);
        } else if (type == 2) {
            c = new RegularCustomer(name, id);
        } else {
            throw new IllegalArgumentException("Please enter 1 for Student or 2 for Regular.");
        }
        customers.add(c);
        return c;
    }

    public int getNumberOfCustomers() {
        return customers.size();
    }

    // ---------- OrderOperations methods ----------
    @Override
    public Order createOrder(Customer customer) {
        Order order = new Order(nextOrderId++, customer);
        orders.add(order);
        return order;
    }

    @Override
    public void addItemToOrder(int orderId, int itemCode, int quantity) {
        Order order = getOrder(orderId);
        MenuItem item = findMenuItem(itemCode);
        if (item == null) {
            throw new IllegalArgumentException("Item not found.");
        }
        order.addItem(item, quantity);
    }

    @Override
    public void changeItemQuantity(int orderId, int itemCode, int newQuantity) {
        Order order = getOrder(orderId);
        order.changeQuantity(findMenuItem(itemCode), newQuantity);
    }

    @Override
    public void removeItemFromOrder(int orderId, int itemCode) {
        Order order = getOrder(orderId);
        order.removeItem(findMenuItem(itemCode));
    }

    @Override
    public void cancelOrder(int orderId) {
        getOrder(orderId).cancel();
    }

    // ---------- searching and reports ----------
    public Order getOrder(int orderId) {
        for (Order o : orders) {
            if (o.getOrderId() == orderId) {
                return o;
            }
        }
        throw new IllegalArgumentException("Order not found.");
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void printAllOrders() {
        if (orders.isEmpty()) {
            System.out.println("No orders yet.");
            return;
        }
        System.out.println("===== All Orders =====");
        for (Order o : orders) {
            System.out.println(o.getSummaryLine());
        }
    }

    public void printSummary() {
        int active = 0;
        int cancelled = 0;
        double revenue = 0;
        double totalDiscount = 0;
        double totalTax = 0;
        Order biggest = null;

        for (Order o : orders) {
            if (o.isCancelled()) {
                cancelled++;
            } else {
                active++;
                revenue += o.calculateFinalBill();
                totalDiscount += o.calculateDiscount();
                totalTax += o.calculateTax();
                if (biggest == null || o.calculateFinalBill() > biggest.calculateFinalBill()) {
                    biggest = o;
                }
            }
        }

        System.out.println("===== Summary Report =====");
        System.out.println("Customers: " + customers.size());
        System.out.println("Menu items: " + menu.size());
        System.out.println("Total orders: " + orders.size() + " (Active: " + active + ", Cancelled: " + cancelled + ")");
        System.out.printf("Total revenue (active orders): %.2f SAR%n", revenue);
        System.out.printf("Total discounts given: %.2f SAR%n", totalDiscount);
        System.out.printf("Total VAT collected: %.2f SAR%n", totalTax);
        if (active > 0) {
            System.out.printf("Average bill: %.2f SAR%n", revenue / active);
            System.out.printf("Biggest order: #%d (%s) = %.2f SAR%n", biggest.getOrderId(),
                    biggest.getCustomer().getName(), biggest.calculateFinalBill());
        }
    }
}
