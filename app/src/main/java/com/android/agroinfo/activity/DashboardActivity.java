package com.android.agroinfo.activity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.android.agroinfo.adapter.ImageAdapter;
import com.android.agroinfo.utilities.ImageDBHelper;
import com.android.agroinfo.R;
import com.android.agroinfo.model.Images;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private ListView listView;
    private ImageAdapter imageAdapter;
    private final List<Images> imageList = new ArrayList<>();
    private ImageDBHelper imageDbHelper;
    private Button seeMoreButton;
    private int offset = 0;
    private static final String NO_MORE = "No more items";
    private String currentQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        listView = findViewById(R.id.list);
        seeMoreButton = findViewById(R.id.btn_login);
        SearchView searchView = findViewById(R.id.searchView);
        imageDbHelper = new ImageDBHelper(this);

        loadImages();

        seeMoreButton.setOnClickListener(v -> loadImages());

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                currentQuery = newText;
                if (imageAdapter != null) {
                    imageAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
    }

    private void loadImages() {
        int limit = 5;
        Cursor cursor = imageDbHelper.getDataWithLimit(offset, limit);

        List<Images> newImages = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String image_url = cursor.getString(cursor.getColumnIndex("url"));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
                newImages.add(Images.builder().name(name).imageUrl(image_url).description(description).build());
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        if (imageAdapter == null) {
            imageList.addAll(newImages);
            imageAdapter = new ImageAdapter(this, imageList);
            listView.setAdapter(imageAdapter);
        } else {
            imageAdapter.addMoreItems(newImages);
            if (!currentQuery.isEmpty()) {
                imageAdapter.getFilter().filter(currentQuery);
            }
        }

        offset += limit;

        Log.d("DashboardActivity", "Loaded items: " + imageList.size());
        Log.d("DashboardActivity", "Cursor count: " + (cursor != null ? cursor.getCount() : 0));

        if (cursor == null || cursor.getCount() < limit) {
            seeMoreButton.setEnabled(false);
            seeMoreButton.setText(NO_MORE);
        }
    }
}