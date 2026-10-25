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
 * One line in an order: a menu item and its quantity.
 */
public class OrderLine {

    public static final int MAX_QUANTITY = 20;

    private MenuItem item;
    private int quantity;

    public OrderLine(MenuItem item, int quantity) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be empty.");
        }
        this.item = item;
        setQuantity(quantity);
    }

    public MenuItem getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 1 || quantity > MAX_QUANTITY) {
            throw new IllegalArgumentException("Invalid quantity! It must be between 1 and " + MAX_QUANTITY + ".");
        }
        this.quantity = quantity;
    }

    // polymorphism: the item decides how the line total is calculated
    public double getLineTotal() {
        return item.calculateLineTotal(quantity);
    }
}
