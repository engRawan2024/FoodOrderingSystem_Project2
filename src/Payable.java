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
 * Interface for the payment operations of an order.
 * Any class that needs to calculate a bill can implement it.
 */
public interface Payable {
    double calculateSubtotal();     // total of all items before discount, tax and delivery
    double calculateDiscount();     // customer discount (deduction)
    double calculateTax();          // VAT
    double calculateDeliveryFee();  // delivery fee (free for big orders)
    double calculateFinalBill();    // the final amount the customer pays
}
