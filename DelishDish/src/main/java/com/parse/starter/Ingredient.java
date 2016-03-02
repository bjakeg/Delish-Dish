package com.parse.starter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jakegraham on 2/21/16.
 */
public class Ingredient implements Parcelable {

    private String title;
    private String amount;
    private int quantity;

    // Constructor
    public Ingredient(String title, String amount, int quantity) {
        this.title = title;
        this.amount = amount;
        this.quantity = quantity;
    }

    protected Ingredient(Parcel in) {
        title = in.readString();
        amount = in.readString();
        quantity = in.readInt();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(amount);
        dest.writeInt(quantity);
    }
}
