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

/**
 * Interface for the ordering operations.
 * OrderManager implements these methods.
 * Each method throws IllegalArgumentException when the input is not valid.
 */
public interface OrderOperations {
    Order createOrder(Customer customer);
    void addItemToOrder(int orderId, int itemCode, int quantity);
    void changeItemQuantity(int orderId, int itemCode, int newQuantity);
    void removeItemFromOrder(int orderId, int itemCode);
    void cancelOrder(int orderId);
}
