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

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity  {

    PhonebookDb db;

    @Override
    public void onBackPressed() {
        // when click on the back button, return a message to parent activity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("message", "detail screen finished");
        setResult(RESULT_OK, resultIntent);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Button btnBack = findViewById(R.id.btn_back);

        TextView txt_first_name = findViewById(R.id.view_first_name);
        TextView txt_last_name = findViewById(R.id.view_last_name);
        TextView txt_phone = findViewById(R.id.view_phone);
        TextView txt_email = findViewById(R.id.view_email);

        db = PhonebookDb.getDbInstance(this);

        String fName = getIntent().getExtras().getString("firstName");
        String lName = getIntent().getExtras().getString("lastName");
        String phoneNum = getIntent().getExtras().getString("phone");
        String email = getIntent().getExtras().getString("email");

        txt_first_name.setText(fName);
        txt_last_name.setText(lName);
        txt_phone.setText(phoneNum);
        txt_email.setText(email);

        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
    }
}
