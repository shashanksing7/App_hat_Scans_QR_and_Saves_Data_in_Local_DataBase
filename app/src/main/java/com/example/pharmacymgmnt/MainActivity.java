package com.example.pharmacymgmnt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private RecyclerView recyclerView;
    private ArrayList<String> searchResults;

    private SearchResultsAdapter adapter;
    //    private ArrayAdapter<String> adapter;
    private DatabaseHelper databaseHelper;

    private String textdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        recyclerView = findViewById(R.id.recyclerView);
        Intent intent=getIntent();
        textdata=intent.getStringExtra("Data");
        editText.setText(textdata);

        searchResults = new ArrayList<>();
        adapter = new SearchResultsAdapter(searchResults); // Initialize your custom adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);



        databaseHelper = new DatabaseHelper(this);
    }

    public void onSaveButtonClicked(View view) {
        String text = editText.getText().toString();
        long id = databaseHelper.insertText(text);
        if (id != -1) {
            Toast.makeText(this, "Text saved to database with ID: " + id, Toast.LENGTH_SHORT).show();
            editText.setText("");
        } else {
            Toast.makeText(this, "Failed to save text to database.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onSearchButtonClicked(View view) {
        String query = editText.getText().toString();
        searchResults.clear();
        searchResults.addAll(databaseHelper.searchByText(query));
        adapter.notifyDataSetChanged();
    }
}
