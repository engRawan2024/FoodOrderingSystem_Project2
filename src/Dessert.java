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
 * Dessert item. It has an offer: buy 2 and get the 3rd one free.
 */
public class Dessert extends MenuItem {

    public Dessert(String name, double price) {
        super(name, price);
    }

    @Override
    public String getCategory() {
        return "Dessert";
    }

    @Override
    public double calculateLineTotal(int quantity) {
        int freeItems = quantity / 3;               // every 3rd dessert is free
        return getPrice() * (quantity - freeItems);
    }

    @Override
    public String getOfferDescription() {
        return "Buy 2 get 1 free";
    }
}
