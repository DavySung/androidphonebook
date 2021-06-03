package com.example.ast2_phonebook.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ast2_phonebook.PhoneBookDB.PhonebookDb;
import com.example.ast2_phonebook.R;
import com.example.ast2_phonebook.Room.Entities.Phonebook;

public class EditActivity extends AppCompatActivity {
    PhonebookDb db;
    private final String TAG = this.getClass().getSimpleName();
    @Override
    public void onBackPressed() {
        // when click on the back button, return a message to parent activity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("message", "edit screen finished");
        setResult(RESULT_OK, resultIntent);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        String firstName, lastName, num, emailAddress;
        Button btnUpdate = findViewById(R.id.btn_update);

        db = PhonebookDb.getDbInstance(this);

        EditText edit_first_name = findViewById(R.id.edit_first_name);
        EditText edit_last_name = findViewById(R.id.edit_last_name);
        EditText edit_phone = findViewById(R.id.edit_phone);
        EditText edit_email = findViewById(R.id.edit_email);

        int id = getIntent().getExtras().getInt("id");
        String fName = getIntent().getExtras().getString("firstName");
        String lName = getIntent().getExtras().getString("lastName");
        String phoneNum = getIntent().getExtras().getString("phone");
        String email = getIntent().getExtras().getString("email");

        edit_first_name.setText(fName);
        edit_last_name.setText(lName);
        edit_phone.setText(phoneNum);
        edit_email.setText(email);



        btnUpdate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {


                EditText edF = findViewById(R.id.edit_first_name);
                EditText edL = findViewById(R.id.edit_last_name);
                EditText edP = findViewById(R.id.edit_phone);
                EditText edE = findViewById(R.id.edit_email);
                Phonebook newContact = new Phonebook(edF.getText().toString(), edL.getText().toString(),
                    edP.getText().toString(), edE.getText().toString());
                db.phonebookDao().updatePhonebook(id, edF.getText().toString(), edL.getText().toString(),
                        edP.getText().toString(), edE.getText().toString());

                Log.d(TAG,  edF.getText().toString() +  edL.getText().toString()+
                        edP.getText().toString()+ edE.getText().toString());
                Intent intent = new Intent(EditActivity.this, ListPageActivity.class);
                startActivityForResult(intent, ListPageActivity.Requestcode.VIEW_DETAIL_REQUEST_CODE.ordinal());
            }
        });
    }
}
