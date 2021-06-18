package com.example.ast2_phonebook.Activities;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ast2_phonebook.Helper.MyButtonClickListener;
import com.example.ast2_phonebook.Helper.MySwipeHelper;
import com.example.ast2_phonebook.Helper.RemotePhonebookDB;
import com.example.ast2_phonebook.Lib.MyHash;
import com.example.ast2_phonebook.Model.PhonebookModel;
import com.example.ast2_phonebook.PhoneBookDB.PhonebookDb;
import com.example.ast2_phonebook.R;
import com.example.ast2_phonebook.Room.Entities.Phonebook;
import com.example.ast2_phonebook.ViewModels.MyHashViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ListPageActivity extends AppCompatActivity {

    PhonebookDb db;
    public enum Requestcode{
        VIEW_DETAIL_REQUEST_CODE,
    }
    private final String TAG = this.getClass().getSimpleName();
   //private final static String LOG_TAG = this.getClass().getSimpleName();
    private MyHashViewModel hash;
    // for sorting
    private FloatingActionButton btnSortA2Z;
    private FloatingActionButton btnSortZ2A;

    // for searching
    private FloatingActionButton btnSearchSubmit;

    private RecyclerView rv;
    private ListPageActivityAdapter clickAdapter;
    CardView card;
    ConstraintLayout dropButton;
    int total, fail = 0;
    boolean success = false;
    private BroadcastReceiver MyReceiver = null;
    @Override
    public void onBackPressed() {
        // when click on the back button, return a message to parent activity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("message", "phonebook list screen finished");
        setResult(RESULT_OK, resultIntent);
        super.onBackPressed();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_page);


        db = PhonebookDb.getDbInstance(this);

        ArrayList<Phonebook> list = (ArrayList<Phonebook>) db.phonebookDao().getAllPhonebook();

        hash = new ViewModelProvider(this).get(MyHashViewModel.class);

        if(hash.myHash == null) {
            Log.d(TAG, "ViewModel has not been created yet.");
            // get data from db, and hash it.

            hash.myHash = new MyHash();
            hash.myHash.buildHashTable(list);
        } else {
            Log.d(TAG, "ViewModel has been created.");
        }

        rv = findViewById(R.id.recycler_view_main_list);
        clickAdapter = new ListPageActivityAdapter(list, new ListPageActivityAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Phonebook phoneList = list.get(position);
                //list item was clicked
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra("id", phoneList.id);
                intent.putExtra("firstName", phoneList.firstName);
                intent.putExtra("lastName", phoneList.lastName);
                intent.putExtra("phone", phoneList.phone);
                intent.putExtra("email", phoneList.email);
                //startActivityForResult(intent, Requestcode.VIEW_DETAIL_REQUEST_CODE.ordinal());
                view.getContext().startActivity(intent);
            }
        });

        FloatingActionButton btnAdd = findViewById(R.id.btn_add_page);

        //set button click method
        btnAdd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(ListPageActivity.this, AddActivity.class);
                    startActivityForResult(intent, Requestcode.VIEW_DETAIL_REQUEST_CODE.ordinal());
                }catch(Exception ex){
                    System.out.println(ex.getStackTrace());
                }

            }
        });

        FloatingActionButton btnDelete = findViewById(R.id.btn_delete);

        //set button click method
        btnDelete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

        // upload phonebook

          /*  for(Phonebook item :list)
            {
                PhonebookModel phone = new PhonebookModel();
                phone.firstName = item.firstName;
                phone.lastName = item.lastName;
                phone.phone = item.phone;
                phone.email = item.email;
                Call<PhonebookModel> phonebookCreate = service.PhonebookCreate(phone);
                //Call API
                phonebookCreate.enqueue(new Callback<PhonebookModel>() {
                    @Override
                    public void onResponse(Call<PhonebookModel> call, Response<PhonebookModel> response) {
                        PhonebookModel phonebooks = response.body();
                        Log.d(TAG, phonebooks.toString());
                        return;
                    }

                    @Override
                    public void onFailure(Call<PhonebookModel> call, Throwable t) {
                        Log.d(TAG, "onFailure");
                        return;
                    }
                });
            }*/

            }
        });

        MySwipeHelper swipeHelper = new MySwipeHelper(this, rv, 200) {
            @Override
            public void instantiateMyButton(RecyclerView.ViewHolder viewHolder, List<MySwipeHelper.MyButton> buffer) {
                buffer.add(new MyButton(ListPageActivity.this, "Update",
                        30,
                        R.drawable.ic_edit_white_24,
                        Color.parseColor("#FF9502"),
                        new MyButtonClickListener() {

                            @Override
                            public void onClick(int pos) {
                                Toast.makeText(ListPageActivity.this, "Update Click", Toast.LENGTH_SHORT).show();

                                Phonebook phoneList = list.get(pos);
                                //list item was clicked
                                Intent intent = new Intent(ListPageActivity.this, EditActivity.class);
                                intent.putExtra("id", phoneList.id);
                                intent.putExtra("firstName", phoneList.firstName);
                                intent.putExtra("lastName", phoneList.lastName);
                                intent.putExtra("phone", phoneList.phone);
                                intent.putExtra("email", phoneList.email);
                                ListPageActivity.this.startActivity(intent);

                            }
                        }));
                buffer.add(new MyButton(ListPageActivity.this, "Call",
                        30,
                        R.drawable.ic_call_24,
                        Color.parseColor("#98fb98"),
                        new MyButtonClickListener() {

                            @Override
                            public void onClick(int pos) {
                                Phonebook phoneList = list.get(pos);
                                //Toast.makeText(ListPageActivity.this, "Delete Click", Toast.LENGTH_SHORT).show();
                                if(phoneList.phone.isEmpty()){
                                    Toast.makeText(ListPageActivity.this, "Enter PhoneNumber", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    String s = phoneList.phone;
                                    Toast.makeText(ListPageActivity.this, "Call Click", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", s, null));

                                    startActivity(intent);
                                }
                            }
                        }));

            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
        };


        rv.setAdapter(clickAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        // for the 27 buttons, set the click functions.
        setAllNavBtnClickListener();

        // sort
        sortA2ZBtnClick();
        sortZ2ABtnClick();

        // search
        searchSubmitClick();


    }
    
    // for search button
    private void searchSubmitClick() {
        btnSearchSubmit = findViewById(R.id.btn_search);
        btnSearchSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wantedStr = ((EditText)findViewById(R.id.edt_main_search_input)).getText().toString();
                if(wantedStr != null && !wantedStr.isEmpty()) {
                    ListPageActivity.this.clickAdapter.reloadContactList(hash.myHash.shortList(wantedStr));
                }
            }
        });
    }

    // sort a~z
    private void sortA2ZBtnClick() {
        btnSortA2Z = findViewById(R.id.btn_sort_ascending);
        btnSortA2Z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPageActivity.this.clickAdapter.reloadContactList(hash.myHash.toList(false));
            }
        });
    }

    // sort z~a
    private void sortZ2ABtnClick() {
        btnSortZ2A = findViewById(R.id.btn_sort_descending);
        btnSortZ2A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPageActivity.this.clickAdapter.reloadContactList(hash.myHash.toList(true));
            }
        });
    }

    // navigate the RecyclerView to the position based on key
    private void navBtnClick(int key) {
        if (key < 0 || key > 26) {
            return;
        }
        // calculate the offset based on key
        int offset = hash.myHash.calcOffsetByKey(key);
        Log.d(TAG, "offset( " + key + ") = " + offset);

        // scroll the view
        ((LinearLayoutManager)rv.getLayoutManager()).scrollToPositionWithOffset(offset, 0);
    }

    private void setAllNavBtnClickListener(){
        // 26 buttons and their click functions are created statically.
        findViewById(R.id.btn_main_nav_ee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPageActivity.this.navBtnClick(0);
            }
        });
        findViewById(R.id.btn_main_nav_a).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPageActivity.this.navBtnClick(1);
            }
        });
        findViewById(R.id.btn_main_nav_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPageActivity.this.navBtnClick(2);
            }
        });
        findViewById(R.id.btn_main_nav_c).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPageActivity.this.navBtnClick(3);
            }
        });
        findViewById(R.id.btn_main_nav_d).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPageActivity.this.navBtnClick(4);
            }
        });
        findViewById(R.id.btn_main_nav_e).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPageActivity.this.navBtnClick(5);
            }
        });
        findViewById(R.id.btn_main_nav_f).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPageActivity.this.navBtnClick(6);
            }
        });
        findViewById(R.id.btn_main_nav_g).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPageActivity.this.navBtnClick(7);
            }
        });
        findViewById(R.id.btn_main_nav_h).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPageActivity.this.navBtnClick(8);
            }
        });
        findViewById(R.id.btn_main_nav_i).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPageActivity.this.navBtnClick(9);
            }
        });
        findViewById(R.id.btn_main_nav_j).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPageActivity.this.navBtnClick(10);
            }
        });
        findViewById(R.id.btn_main_nav_k).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPageActivity.this.navBtnClick(11);
            }
        });
        findViewById(R.id.btn_main_nav_l).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPageActivity.this.navBtnClick(12);
            }
        });
        findViewById(R.id.btn_main_nav_m).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPageActivity.this.navBtnClick(13);
            }
        });
        findViewById(R.id.btn_main_nav_n).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPageActivity.this.navBtnClick(14);
            }
        });
        findViewById(R.id.btn_main_nav_o).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPageActivity.this.navBtnClick(15);
            }
        });
        findViewById(R.id.btn_main_nav_p).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPageActivity.this.navBtnClick(16);
            }
        });
        findViewById(R.id.btn_main_nav_q).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPageActivity.this.navBtnClick(17);
            }
        });
        findViewById(R.id.btn_main_nav_r).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPageActivity.this.navBtnClick(18);
            }
        });
        findViewById(R.id.btn_main_nav_s).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPageActivity.this.navBtnClick(19);
            }
        });
        findViewById(R.id.btn_main_nav_t).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPageActivity.this.navBtnClick(20);
            }
        });
        findViewById(R.id.btn_main_nav_u).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPageActivity.this.navBtnClick(21);
            }
        });
        findViewById(R.id.btn_main_nav_v).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPageActivity.this.navBtnClick(22);
            }
        });
        findViewById(R.id.btn_main_nav_w).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPageActivity.this.navBtnClick(23);
            }
        });
        findViewById(R.id.btn_main_nav_x).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPageActivity.this.navBtnClick(24);
            }
        });
        findViewById(R.id.btn_main_nav_y).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPageActivity.this.navBtnClick(25);
            }
        });
        findViewById(R.id.btn_main_nav_z).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPageActivity.this.navBtnClick(26);
            }
        });


    }


}
