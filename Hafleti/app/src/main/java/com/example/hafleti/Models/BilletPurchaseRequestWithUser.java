package com.example.hafleti.Models;

public class BilletPurchaseRequestWithUser {
    private BilletPurchaseRequest request;
    private Long client_id; // null if guest
    private String email; // for guest
    private String fullName; // for guest
    public BilletPurchaseRequestWithUser() {}

    public BilletPurchaseRequestWithUser(BilletPurchaseRequest billetPurchaseRequest,
                                         Long clientId, String email, String fullName) {
        this.request = billetPurchaseRequest;
        this.client_id = clientId;
        this.email = email;
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public BilletPurchaseRequest getRequest() {
        return request;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getClientId() {
        return client_id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setClientId(Long clientId) {
        this.client_id = clientId;
    }

    public void setRequest(BilletPurchaseRequest request) {
        this.request = request;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "BilletPurchaseRequestWithUser{" +
                "request=" + request +
                ", clientId=" + client_id +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}