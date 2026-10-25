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
 * Main course item. It has an extra charge: a packaging fee for each meal.
 */
public class MainCourse extends MenuItem {

    private static final double PACKAGING_FEE = 2.0;   // SAR for each meal

    public MainCourse(String name, double price) {
        super(name, price);
    }

    @Override
    public String getCategory() {
        return "Main Course";
    }

    @Override
    public double calculateLineTotal(int quantity) {
        return (getPrice() + PACKAGING_FEE) * quantity;
    }

    @Override
    public String getOfferDescription() {
        return "+2.00 packaging per meal";
    }
}
