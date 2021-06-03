package com.example.ast2_phonebook.PhoneBookDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ast2_phonebook.Room.Dao.PhonebookDao;
import com.example.ast2_phonebook.Room.Entities.Phonebook;

@Database(
        entities = {Phonebook.class},
        version = 1,
        exportSchema = false
)

public abstract class PhonebookDb extends RoomDatabase {

    public abstract PhonebookDao phonebookDao();

    private static PhonebookDb phonebookDb;

    public static PhonebookDb getDbInstance(final Context context ){
        if(phonebookDb == null){
            phonebookDb = Room.databaseBuilder(context.getApplicationContext(),
                    PhonebookDb.class, "phonebook_room.db").allowMainThreadQueries().build();

        }
        return phonebookDb;
    }
}

