package com.example.ast2_phonebook.Room.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ast2_phonebook.Room.Entities.Phonebook;

import java.util.List;

@Dao
public interface PhonebookDao {
    //create
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPhonebook(Phonebook... phonebooks);

    //update
    @Query("UPDATE phonebook SET firstName= :firstName, lastName=:lastName, phone=:phone, email=:email WHERE id=:id")
    void updatePhonebook(int id, String firstName, String lastName, String phone, String email);
    //delete
    /*@Delete
    void deletePhonebook(Phonebook... phonebooks);*/
    @Query("Delete FROM phonebook WHERE id=:id")
    void deletePhonebook(int id);

    //delete all
    @Query("DELETE FROM phonebook")
    void clearTable();

    //read all
    @Query("SELECT * FROM phonebook")
    List<Phonebook> getAllPhonebook();

    //read one by id
    @Query("SELECT * FROM phonebook WHERE id= :phonebookId")
    Phonebook getPhonebookById(int phonebookId);

    //find phonebook by first name and last name
    @Query("SELECT id FROM phonebook WHERE firstName= :firstName AND lastName=:lastName ")
    int getPhonebookIdByFirstNameAndLastName(String firstName, String lastName);

}
