package com.example.zakatgoldapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView githubLink = findViewById(R.id.tvGithubLink);
        Button btnBack = findViewById(R.id.btnBack);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Open GitHub link in browser
        githubLink.setOnClickListener(v -> {
            String url = "https://github.com/yourusername/ZakatGoldApplication";
            Intent openUrl = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(openUrl);
        });

        // Back button
        btnBack.setOnClickListener(v -> finish());
    }
}
