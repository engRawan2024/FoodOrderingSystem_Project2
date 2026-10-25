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
 * Abstract parent class for all food items.
 * The fields are private (encapsulation) and the subclasses
 * must write their own price rule (abstraction + polymorphism).
 */
public abstract class MenuItem {

    public static final double MIN_PRICE = 1.0;
    public static final double MAX_PRICE = 500.0;

    private static int nextCode = 1;   // used to give every item a unique code

    private int code;
    private String name;
    private double price;
    private boolean available;

    public MenuItem(String name, double price) {
        setName(name);
        setPrice(price);
        this.code = nextCode++;   // the code is given only after the name and price are valid
        this.available = true;
    }

    // ---------- getters ----------
    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    // ---------- setters with validation ----------
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be empty.");
        }
        this.name = name.trim();
    }

    public void setPrice(double price) {
        if (price < MIN_PRICE || price > MAX_PRICE) {
            throw new IllegalArgumentException(
                    "Invalid price! It must be between " + (int) MIN_PRICE + " and " + (int) MAX_PRICE + ".");
        }
        this.price = price;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    // ---------- abstract methods (each subclass writes its own version) ----------
    public abstract String getCategory();

    // total price of this item for the given quantity (after the item rule)
    public abstract double calculateLineTotal(int quantity);

    // short text that explains the special rule of the category
    public abstract String getOfferDescription();

    @Override
    public String toString() {
        return String.format("%-4d %-18s %-12s %8.2f SAR  %-12s %s", code, name, getCategory(), price,
                (available ? "Available" : "Unavailable"), getOfferDescription());
    }
}
