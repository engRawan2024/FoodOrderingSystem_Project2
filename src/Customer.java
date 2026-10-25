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
 * Abstract parent class for customers.
 * The discount rate is different for each customer type (polymorphism).
 */
public abstract class Customer {

    private String name;
    private String id;

    public Customer(String name, String id) {
        setName(name);
        setId(id);
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty.");
        }
        this.name = toTitleCase(name);
    }

    public void setId(String id) {
        if (!isValidId(id)) {
            throw new IllegalArgumentException("Invalid ID! ID must start with 4 and contain exactly 9 digits.");
        }
        this.id = id;
    }

    public abstract String getType();

    public abstract double getDiscountRate();

    // converts any name to Title Case and removes extra spaces (from Project 1)
    public static String toTitleCase(String name) {
        name = name.trim().replaceAll("\\s+", " ");
        String[] words = name.split(" ");
        String result = "";
        for (String w : words) {
            if (w.length() > 0) {
                result += w.substring(0, 1).toUpperCase() + w.substring(1).toLowerCase() + " ";
            }
        }
        return result.trim();
    }

    // checks that the ID starts with 4 and has exactly 9 digits (from Project 1)
    public static boolean isValidId(String id) {
        if (id == null || id.length() != 9) {
            return false;
        }
        if (id.charAt(0) != '4') {
            return false;
        }
        for (int i = 0; i < id.length(); i++) {
            if (!Character.isDigit(id.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return name + " (ID: " + id + ", " + getType() + ")";
    }
}
