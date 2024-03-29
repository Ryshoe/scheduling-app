package com.dbclientapp.contact;

import com.dbclientapp.util.DataTransferObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Handles contacts as an object.
 */
public class Contact implements DataTransferObject {

    private int id;
    private final StringProperty contactName = new SimpleStringProperty();
    private String email;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName.get();
    }

    public void setContactName(String contactName) {
        this.contactName.set(contactName);
    }

    public StringProperty contactNameProperty() {
        return contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", contactName='" + contactName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
