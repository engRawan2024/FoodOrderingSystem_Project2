/*
 * ------------------------------------------------------------
 * Names: Jana Abdulrahman   | 445810234
 *        Hanenn Saeed Ahmad | 447802061
 *        Fai Ali Alqahtani  | 445810696
 * Group: 2
 * Project 2: Food Ordering Management System (OOP version)
--------------------------------------------------------
 */

import java.util.Scanner;

/**
 * Main class: shows the menu and calls the other classes.
 */
public class FoodOrderingSystem {

    private static Scanner input = new Scanner(System.in);
    private static OrderManager manager = new OrderManager();

    public static void main(String[] args) {
        loadSampleMenu();
        int choice = 0;

        while (choice != 11) {
            printMenu();
            choice = readInt("Enter your choice: ");
            System.out.println();

            try {
                switch (choice) {
                    case 1:
                        addMenuItem();
                        break;
                    case 2:
                        displayMenu();
                        break;
                    case 3:
                        modifyMenuItem();
                        break;
                    case 4:
                        createOrder();
                        break;
                    case 5:
                        addItemsToOrder();
                        break;
                    case 6:
                        modifyOrder();
                        break;
                    case 7:
                        searchOrder();
                        break;
                    case 8:
                        manager.printAllOrders();
                        break;
                    case 9:
                        cancelOrder();
                        break;
                    case 10:
                        manager.printSummary();
                        break;
                    case 11:
                        System.out.println("Thank you for using the Food Ordering System. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice! Please enter a number between 1 and 11.");
                }
            } catch (IllegalArgumentException e) {
                // all validation errors from the classes come here
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println();
        }
        input.close();
    }

    // ---------- start data ----------
    private static void loadSampleMenu() {
        manager.addMenuItem(new MainCourse("Shawarma Sandwich", 25.0));
        manager.addMenuItem(new MainCourse("Chicken Kabsa", 35.0));
        manager.addMenuItem(new Beverage("Orange Juice", 8.0));
        manager.addMenuItem(new Beverage("Cola", 5.0));
        manager.addMenuItem(new Dessert("Kunafa", 15.0));
        manager.addMenuItem(new Dessert("Chocolate Cake", 18.0));
    }

    private static void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Add New Menu Item");
        System.out.println("2. Display Menu");
        System.out.println("3. Modify Menu Item (price / availability)");
        System.out.println("4. Create New Order");
        System.out.println("5. Add Items to Order");
        System.out.println("6. Modify Order (change quantity / remove item)");
        System.out.println("7. Search Order and Display Bill");
        System.out.println("8. Display All Orders");
        System.out.println("9. Cancel Order");
        System.out.println("10. Display Summary Report");
        System.out.println("11. Exit");
    }

    // ---------- menu item options ----------
    private static void addMenuItem() {
        int category;
        while (true) {
            category = readInt("Category (1 = Main Course, 2 = Beverage, 3 = Dessert): ");
            if (category >= 1 && category <= 3) {
                break;
            }
            System.out.println("Please enter 1, 2 or 3.");
        }

        String name;
        while (true) {
            System.out.print("Item name: ");
            name = input.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Item name cannot be empty.");
            } else if (manager.itemNameExists(name)) {
                System.out.println("This item is already in the menu.");
            } else {
                break;
            }
        }

        double price;
        while (true) {
            price = readDouble("Price (SAR): ");
            if (price >= MenuItem.MIN_PRICE && price <= MenuItem.MAX_PRICE) {
                break;
            }
            System.out.println("Invalid price! It must be between 1 and 500.");
        }

        MenuItem item;
        if (category == 1) {
            item = new MainCourse(name, price);
        } else if (category == 2) {
            item = new Beverage(name, price);
        } else {
            item = new Dessert(name, price);
        }
        manager.addMenuItem(item);
        System.out.println("Item added with code " + item.getCode() + ".");
    }

    private static void displayMenu() {
        System.out.println("===== Food Menu =====");
        System.out.printf("%-4s %-18s %-12s %-12s %-12s %s%n", "Code", "Name", "Category", "Price", "Status", "Rule");
        for (MenuItem item : manager.getMenu()) {
            System.out.println(item);
        }
    }

    private static void modifyMenuItem() {
        displayMenu();
        int code = readInt("Enter the item code: ");
        if (manager.findMenuItem(code) == null) {
            System.out.println("Item not found.");
            return;
        }
        int option = readInt("1 = Change price, 2 = Change availability: ");
        if (option == 1) {
            double newPrice = readDouble("New price (SAR): ");
            manager.changeItemPrice(code, newPrice);
            System.out.println("Price updated.");
        } else if (option == 2) {
            int status = readInt("1 = Available, 2 = Unavailable: ");
            if (status != 1 && status != 2) {
                System.out.println("Please enter 1 or 2.");
                return;
            }
            manager.changeItemAvailability(code, status == 1);
            System.out.println("Availability updated.");
        } else {
            System.out.println("Invalid option.");
        }
    }

    // ---------- order options ----------
    private static void createOrder() {
        String id;
        while (true) {
            System.out.print("Enter KKU ID Number: ");
            id = input.nextLine().trim();
            if (Customer.isValidId(id)) {
                break;
            }
            System.out.println("Invalid ID! ID must start with 4 and contain exactly 9 digits.");
        }

        Customer customer = manager.findCustomer(id);
        if (customer != null) {
            System.out.println("Existing customer found: " + customer);
        } else {
            String name;
            while (true) {
                System.out.print("Enter Customer Name: ");
                name = input.nextLine();
                if (!name.trim().isEmpty()) {
                    break;
                }
                System.out.println("Name cannot be empty.");
            }
            int type;
            while (true) {
                type = readInt("Customer type (1 = Student, 2 = Regular): ");
                if (type == 1 || type == 2) {
                    break;
                }
                System.out.println("Please enter 1 for Student or 2 for Regular.");
            }
            customer = manager.registerCustomer(name, id, type);
        }

        Order order = manager.createOrder(customer);
        System.out.println("Order created. Your order number is " + order.getOrderId() + ".");
    }

    private static void addItemsToOrder() {
        int orderId = readInt("Enter order number: ");
        Order order = manager.getOrder(orderId);   // throws an error if not found
        if (order.isCancelled()) {
            System.out.println("Order " + orderId + " is cancelled and cannot be changed.");
            return;
        }

        displayMenu();
        String again = "y";
        while (again.equalsIgnoreCase("y")) {
            int code = readInt("Enter item code: ");
            int quantity = readQuantity();
            try {
                manager.addItemToOrder(orderId, code, quantity);
                System.out.println("Item added to the order.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.print("Add another item? (y/n): ");
            again = input.nextLine().trim();
        }
        order.printBill();
    }

    private static void modifyOrder() {
        int orderId = readInt("Enter order number: ");
        Order order = manager.getOrder(orderId);
        order.printBill();
        if (order.isCancelled() || order.getNumberOfLines() == 0) {
            return;
        }

        int option = readInt("1 = Change quantity, 2 = Remove item: ");
        if (option != 1 && option != 2) {
            System.out.println("Invalid option.");
            return;
        }
        int code = readInt("Enter item code: ");
        if (option == 1) {
            int newQuantity = readQuantity();
            manager.changeItemQuantity(orderId, code, newQuantity);
            System.out.println("Quantity updated. New bill:");
        } else {
            manager.removeItemFromOrder(orderId, code);
            System.out.println("Item removed. New bill:");
        }
        order.printBill();
    }

    private static void searchOrder() {
        int orderId = readInt("Enter order number: ");
        manager.getOrder(orderId).printBill();
    }

    private static void cancelOrder() {
        int orderId = readInt("Enter order number to cancel: ");
        Order order = manager.getOrder(orderId);
        System.out.print("Are you sure you want to cancel order " + orderId + "? (y/n): ");
        String answer = input.nextLine().trim();
        if (answer.equalsIgnoreCase("y")) {
            manager.cancelOrder(orderId);
            System.out.println("Order " + orderId + " was cancelled.");
        } else {
            System.out.println("Cancel stopped. The order is still " + order.getStatus() + ".");
        }
    }

    // ---------- input helpers ----------
    // asks again until the quantity is between 1 and 20
    private static int readQuantity() {
        while (true) {
            int q = readInt("Quantity: ");
            if (q >= 1 && q <= OrderLine.MAX_QUANTITY) {
                return q;
            }
            System.out.println("Invalid quantity! It must be between 1 and " + OrderLine.MAX_QUANTITY + ".");
        }
    }

    // reads an integer safely (no crash if the user types letters)
    private static int readInt(String message) {
        while (true) {
            System.out.print(message);
            String line = input.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a whole number.");
            }
        }
    }

    // reads a double safely
    private static double readDouble(String message) {
        while (true) {
            System.out.print(message);
            String line = input.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }
}
