package com.example.ast2_phonebook.Room.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName="phonebook")
public class Phonebook  implements Comparable{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;

    @ColumnInfo(name="firstName")
    public String firstName;

    @ColumnInfo(name="lastName")
    public String lastName;

    @ColumnInfo(name="phone")
    public String phone;

    @ColumnInfo(name="email")
    public String email;

    public Phonebook(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Ignore
    public Phonebook(String firstName, String lastName, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }

    // compare the name string
    @Override
    public int compareTo(Object o) {
        return this.firstName.compareToIgnoreCase(((Phonebook)o).firstName);
    }

}
