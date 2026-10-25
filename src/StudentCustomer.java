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
 * Student customer. Gets a 10% discount (deduction).
 */
public class StudentCustomer extends Customer {

    public StudentCustomer(String name, String id) {
        super(name, id);
    }

    @Override
    public String getType() {
        return "Student";
    }

    @Override
    public double getDiscountRate() {
        return 0.10;
    }
}
