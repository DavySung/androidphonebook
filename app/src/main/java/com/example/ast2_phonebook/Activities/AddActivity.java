package com.example.ast2_phonebook.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ast2_phonebook.PhoneBookDB.PhonebookDb;
import com.example.ast2_phonebook.R;
import com.example.ast2_phonebook.Room.Entities.Phonebook;

public class AddActivity extends AppCompatActivity {
    PhonebookDb db;
    public enum Requestcode{
        VIEW_DETAIL_REQUEST_CODE,
    }

    @Override
    public void onBackPressed() {
        // when click on the back button, return a message to parent activity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("message", "add screen finished");
        setResult(RESULT_OK, resultIntent);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Button btnAddNumber = findViewById(R.id.btn_add);
        db = PhonebookDb.getDbInstance(this);
        btnAddNumber.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                TextView txtFirstName = findViewById(R.id.add_first_name);
                TextView txtLastName = findViewById(R.id.add_last_name);
                TextView txtPhone= findViewById(R.id.add_phone);
                TextView txtEmail = findViewById(R.id.add_email);
                db.phonebookDao().insertPhonebook(
                        new Phonebook(txtFirstName.getText().toString(), txtLastName.getText().toString(),
                                txtPhone.getText().toString(), txtEmail.getText().toString()) );
                Intent intent = new Intent(AddActivity.this, ListPageActivity.class);
                startActivityForResult(intent, ListPageActivity.Requestcode.VIEW_DETAIL_REQUEST_CODE.ordinal());
            }
        });

    }
}
