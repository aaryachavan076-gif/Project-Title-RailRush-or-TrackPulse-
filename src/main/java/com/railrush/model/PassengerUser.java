package com.railrush.model;

/**
 * PassengerUser extending User to demonstrate Hierarchical Inheritance (Exp 03).
 */
public class PassengerUser extends User {
    private String preferredClass;
    private int loyaltyPoints;

    public PassengerUser() {
        super();
        this.role = "PASSENGER";
    }

    public PassengerUser(int userId, String fullName, String email, String passwordHash, String phoneNumber, String preferredClass) {
        super(userId, fullName, email, passwordHash, "PASSENGER", phoneNumber);
        this.preferredClass = preferredClass;
        this.loyaltyPoints = 0;
    }

    public String getPreferredClass() {
        return preferredClass;
    }

    public void setPreferredClass(String preferredClass) {
        this.preferredClass = preferredClass;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }
}
