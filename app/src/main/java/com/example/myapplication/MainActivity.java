package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickDeleteText(View view) {

        TextView textView = findViewById(R.id.hello);
        textView.setText("PuPa");
        textView.setTextColor(getResources().getColor(R.color.pupa));
        textView.setBackgroundColor(getResources().getColor(R.color.lupa));
    }

    public void onClickWriteText(View view) {
        TextView textView = findViewById(R.id.hello);
        textView.setText("LuPa");
        textView.setTextColor(getResources().getColor(R.color.lupa));
        textView.setBackgroundColor(getResources().getColor(R.color.pupa));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.line1:
                Toast.makeText(this, "Не тычь на инвалида", Toast.LENGTH_SHORT).show();
                break;
            case R.id.line2:
                Toast.makeText(this, "На него можно тыкать", Toast.LENGTH_SHORT).show();
                break;
            case R.id.line3:
                Snackbar.make(findViewById(R.id.hello), "Сюда-то ты вообще зачем залез?!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            default:
                Dialog1 dialog = new Dialog1();
                dialog.show(getSupportFragmentManager(),"Notice");
        }
        return  super.onOptionsItemSelected(item);
    }
}
