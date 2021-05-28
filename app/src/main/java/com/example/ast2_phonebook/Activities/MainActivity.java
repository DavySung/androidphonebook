package com.example.ast2_phonebook.Activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ast2_phonebook.PhoneBookDB.PhonebookDb;
import com.example.ast2_phonebook.R;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class MainActivity extends AppCompatActivity {
    private int activeThemeResId;
    private final String TAG = this.getClass().getSimpleName();
    private final String isDarkThemeKey = "is_dark_theme_key";
    float x1, x2, y1, y2;
    public enum Requestcode{
        VIEW_DETAIL_REQUEST_CODE,
    }
    private AppCompatDelegate mDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadAndSetTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnDark).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDelegate.setDefaultNightMode(mDelegate.MODE_NIGHT_YES);
                switchAndSaveThemeSetting();
            }
        });

        findViewById(R.id.btnLight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDelegate.setDefaultNightMode(mDelegate.MODE_NIGHT_NO);
                switchAndSaveThemeSetting();
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1 < x2){
                Intent i = new Intent(MainActivity.this, ListPageActivity.class);
                startActivity(i);
            }else if(x1 >x2){
                Intent i = new Intent(MainActivity.this, ListPageActivity.class);
                startActivity(i);
            }
            break;
        }
        return false;
    }


    private void loadAndSetTheme(){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        boolean isDark = sharedPref.getBoolean(isDarkThemeKey, false);
        activeThemeResId = isDark ? mDelegate.MODE_NIGHT_YES : mDelegate.MODE_NIGHT_NO;
        setTheme(activeThemeResId);
    }

    private void switchAndSaveThemeSetting() {SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if (activeThemeResId == mDelegate.MODE_NIGHT_YES) {
            editor.putBoolean(isDarkThemeKey, false);
        } else {
            editor.putBoolean(isDarkThemeKey, true);
        }

        editor.apply();

        // recreate this activity. so that new theme will be applied.
        recreate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Requestcode.VIEW_DETAIL_REQUEST_CODE.ordinal()){
            if(data != null && data.hasExtra("message")){
                //get the return data from Detail Activity
                String message = data.getStringExtra("message");
                //display message with Toast
                Toast.makeText(
                        getApplicationContext(),
                        "Message: " + message,
                        Toast.LENGTH_LONG
                ).show();
            }
            else Log.d(TAG, "no message is returned from ListPageActivity");

        }else Log.d(TAG, "RequestCode(" + requestCode + ") not recognizable");
    }
}

