package com.example.zakatgoldapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvType, tvResultLabel, tvResult, tvResultLabel2, tvPayable, tvResultLabel3, tvTotalZakat;
    EditText etWeightGold, etCurrentGold;
    Button btnCalculate, btnReset;
    RadioButton rbWear, rbKeep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        tvType = findViewById(R.id.tvType);
        etWeightGold = findViewById(R.id.etWeightGold);
        etCurrentGold = findViewById(R.id.etCurrentGold);
        btnCalculate = findViewById(R.id.btnCalculate);
        rbWear = findViewById(R.id.rbWear);
        rbKeep = findViewById(R.id.rbKeep);
        tvResultLabel = findViewById(R.id.tvResultLabel);
        tvResult = findViewById(R.id.tvResult);
        btnReset = findViewById(R.id.btnReset);
        tvResultLabel2 = findViewById(R.id.tvResultLabel2);
        tvPayable = findViewById(R.id.tvPayable);
        tvResultLabel3 = findViewById(R.id.tvResultLabel3);
        tvTotalZakat = findViewById(R.id.tvTotalZakat);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Button Listeners
        btnCalculate.setOnClickListener(this);
        btnReset.setOnClickListener(v -> resetFields());

        // Edge-to-edge padding fix
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selected = item.getItemId();

        if (selected == R.id.menuAbout) {

            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        } else if (selected == R.id.menuShare) {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out my Zakat Gold App");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "https://github.com/yourusername/ZakatGoldApplication");
            startActivity(Intent.createChooser(shareIntent, "Share via"));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressLint("DefaultLocale")
    @Override
    public void onClick(View v) {
        try {
            String weightStr = etWeightGold.getText().toString().trim();
            String valueStr = etCurrentGold.getText().toString().trim();

            if (weightStr.isEmpty() || valueStr.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double weight = Double.parseDouble(weightStr);
            double goldValue = Double.parseDouble(valueStr);
            double nisab;

            if (rbKeep.isChecked()) {
                nisab = 85;
            } else if (rbWear.isChecked()) {
                nisab = 200;
            } else {
                Toast.makeText(this, "Please select gold type (Keep/Wear)", Toast.LENGTH_SHORT).show();
                return;
            }

            double totalGoldValue = weight * goldValue;

            double goldAboveNisab = weight - nisab;
            if (goldAboveNisab < 0) goldAboveNisab = 0;

            double payableGoldValue = goldAboveNisab * goldValue;

            double totalZakat = payableGoldValue * 0.025;

            // Display results
            tvResult.setText(String.format("RM %.2f", totalGoldValue));      // Total value of gold
            tvPayable.setText(String.format("RM %.2f", payableGoldValue));   // Payable gold value
            tvTotalZakat.setText(String.format("RM %.2f", totalZakat));      // Total zakat

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numeric values.", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetFields() {
        etWeightGold.setText("");
        etCurrentGold.setText("");
        rbKeep.setChecked(false);
        rbWear.setChecked(false);
        tvResult.setText("");
        tvPayable.setText("");
        tvTotalZakat.setText("");
        Toast.makeText(this, "All fields cleared", Toast.LENGTH_SHORT).show();
    }
}
