package com.digitalruiz.pizzadriver;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    String workingDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0,0);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.top_app_bar);
        toolbar.setOnMenuItemClickListener(this);
        Intent intent = getIntent();
        if(intent != null){
            workingDate = intent.getStringExtra("SelectedDate");
            if (workingDate == null){
                Date date = Calendar.getInstance().getTime();
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                workingDate = formatter.format(date);
            }
        }
        else {
            Date date = Calendar.getInstance().getTime();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            workingDate = formatter.format(date);
        }
        Log.d("TEST", "onViewCreated: " + workingDate);
        toolbar.setSubtitle(workingDate);
        toolbar.setNavigationOnClickListener(v -> {
            Intent settings = new Intent(MainActivity.this, settings.class);
            MainActivity.this.startActivity(settings);
        });

        SQLiteDBHelper pizzaDriverDB;
        pizzaDriverDB = new SQLiteDBHelper(this);
        pizzaDriverDB.getWritableDatabase();

        FloatingActionButton addOrder = findViewById(R.id.add);

        addOrder.setOnClickListener(view -> {
            Intent addOrderIntent = new Intent(MainActivity.this, addOrderNumber.class);
            MainActivity.this.startActivity(addOrderIntent);
        });


    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.settings:
                Intent date_picker = new Intent(MainActivity.this, DatePicker.class);
                date_picker.putExtra("SelectedDate", workingDate);
                MainActivity.this.startActivity(date_picker);
                return true;
        }
        return false;
    }

}
