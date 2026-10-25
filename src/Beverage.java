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
 * Beverage item. It has a discount: 10% off when the quantity is 4 or more.
 */
public class Beverage extends MenuItem {

    private static final int DISCOUNT_QUANTITY = 4;
    private static final double DISCOUNT_RATE = 0.10;

    public Beverage(String name, double price) {
        super(name, price);
    }

    @Override
    public String getCategory() {
        return "Beverage";
    }

    @Override
    public double calculateLineTotal(int quantity) {
        double total = getPrice() * quantity;
        if (quantity >= DISCOUNT_QUANTITY) {
            total = total - total * DISCOUNT_RATE;
        }
        return total;
    }

    @Override
    public String getOfferDescription() {
        return "10% off for 4 or more";
    }
}
