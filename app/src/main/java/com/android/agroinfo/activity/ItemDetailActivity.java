package com.android.agroinfo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.agroinfo.R;

public class ItemDetailActivity extends AppCompatActivity {
    TextView nameTextView;
    TextView descriptionTextView;
    Button wikiButton;
    ImageView backButtonIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item_detail);

        nameTextView = findViewById(R.id.txtHeader);
        descriptionTextView = findViewById(R.id.txtDescription);
        wikiButton = findViewById(R.id.btn_login);
        backButtonIcon = findViewById(R.id.backButton);


        String name = getIntent().getStringExtra("NAME");
        String description = getIntent().getStringExtra("DESCRIPTION");

        nameTextView.setText(name);
        descriptionTextView.setText(description);

        backButtonIcon.setOnClickListener(view -> {
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
        });

        wikiButton.setOnClickListener(v -> {
            assert name != null;
            String url = "https://en.wikipedia.org/wiki/" + name.replaceAll(" ", "_");
            Intent webIntent = new Intent(Intent.ACTION_VIEW);
            webIntent.setData(Uri.parse(url));
            startActivity(webIntent);
        });
    }
}
