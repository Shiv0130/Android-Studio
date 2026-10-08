package com.example.contacts;

public class Contact {
    private int contactID;
    private String contactName;
    private String phoneNumber;
    private String address;

    public int getContactID(){
        return this.contactID;
    }

    public String getContactName(){
        return this.contactName;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }


    public String getAddress(){
        return this.address;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public void setContactName(String name){
        contactName = name;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber =phoneNumber;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public Contact(){
        contactID = -1;
    }
}
