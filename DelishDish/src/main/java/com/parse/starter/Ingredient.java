package com.parse.starter;

/**
 * Created by jakegraham on 2/21/16.
 */
public class Ingredient {

    private String title;
    private String amount;
    private int quantity;

    // Constructor
    public Ingredient(String title, String amount, int quantity) {
        this.title = title;
        this.amount = amount;
        this.quantity = quantity;
    }

    // Setters
    public void setTitle(String newTitle) {
        title = newTitle;
    }
    public void setAmount(String newAmount) {
        amount = newAmount;
    }
    public void setQuantity(int newQuantity) {
        quantity = newQuantity;
    }

    // Getters
    public String getTitle() {
        return title;
    }
    public String getAmount() {
        return amount;
    }
    public int getQuantity() {
        return quantity;
    }

}
