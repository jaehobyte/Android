package com.example.intentpractice;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> datas;

    String category;

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
                    Toast toast = Toast.makeText(this, intent.getStringExtra("location"), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.main_list);
        listView.setOnItemClickListener(this);

        //항목 구성 데이터 획득
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select location from tb_data where category='0'", null);
        datas = new ArrayList<>();
        while (cursor.moveToNext()) {
            datas.add(cursor.getString(0));
        }
        db.close();

        //Adapter적용으로 ListView 구성
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas);
        listView.setAdapter(adapter);

    }

    //항목 선택 이벤트
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView textView = (TextView) view;
        category = textView.getText().toString();
        //Extra 데이터를 넘기면서 Intent 발생
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("category", category);
        launcher.launch(intent);
    }

}