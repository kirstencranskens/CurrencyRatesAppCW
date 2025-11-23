package org.me.gcu.cranskens_kirsten_s2433486;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;

public class CurrencyDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView titleText;
    private TextView rateText;
    private TextView dateText;
    private EditText gbpInput;
    private Button gbpToCurrButton;
    private Button currToGbpButton;
    private TextView resultText;
    private View detailMainView;

    private float rate = 0.0f;

    // 🔹 NEW: store the currency code so all methods can use it
    private String currencyCode = "???";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_detail);
        detailMainView = (View) findViewById(R.id.detailMainView);

        // Link to layout widgets
        titleText = (TextView) findViewById(R.id.titleText);
        rateText = (TextView) findViewById(R.id.rateText);
        dateText = (TextView) findViewById(R.id.dateText);
        gbpInput = (EditText) findViewById(R.id.gbpInput);
        gbpToCurrButton = (Button) findViewById(R.id.gbpToCurrButton);
        currToGbpButton = (Button) findViewById(R.id.currToGbpButton);
        resultText = (TextView) findViewById(R.id.resultText);

        gbpToCurrButton.setOnClickListener(this);
        currToGbpButton.setOnClickListener(this);

        // Get data from Intent extras (sent from MainActivity)
        String title = getIntent().getStringExtra("title");

        // 🔹 Extract the currency code from the title (text inside final parentheses)
        //    Example title: "British Pound Sterling(GBP)/US Dollar(USD)"
        int lastOpen = title.lastIndexOf("(");
        int lastClose = title.lastIndexOf(")");

        ImageView flagImage = findViewById(R.id.flagImage);


        this.currencyCode = FlagUtils.extractCurrencyCodeFromTitle(title);

// Get the flag resource ID
        int resId = FlagUtils.getFlagResIdFromCurrency(this, currencyCode);

// If found, display it; otherwise use a globe
        if (resId != 0) {
            flagImage.setImageResource(resId);
        } else {
            int globeId = getResources().getIdentifier("ic_globe", "drawable", getPackageName());
            if (globeId != 0) flagImage.setImageResource(globeId);
        }


        if (lastOpen != -1 && lastClose != -1 && lastClose > lastOpen) {
            currencyCode = title.substring(lastOpen + 1, lastClose);
        }

        String date = getIntent().getStringExtra("pubDate");
        rate = getIntent().getFloatExtra("rate", 0.0f);

        // Set the text fields
        titleText.setText(title);
        rateText.setText("Rate: " + rate);
        dateText.setText("Last updated: " + date);
        applyStrengthColours();

        // 🔹 Set dynamic button text using currencyCode
        gbpToCurrButton.setText("GBP → " + currencyCode);
        currToGbpButton.setText(currencyCode + " → GBP");

        Button backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(v -> finish());
    }

    private void applyStrengthColours() {
        if (rateText == null) {
            return;
        }

        // Default to black
        int textColour = getResources().getColor(R.color.Black, null);

        // Adjust text colour based on strength
        if (rate < 0.5f) {
            // Very strong GBP → cool green
            textColour = getResources().getColor(R.color.ForestGreen, null);
        }
        else if (rate < 1.0f) {
            // Strong GBP → lighter green
            textColour = getResources().getColor(R.color.MediumSeaGreen, null);
        }
        else if (rate < 2.0f) {
            // Medium → neutral dark
            textColour = getResources().getColor(R.color.DarkSlateGray, null);
        }
        else {
            // GBP is weak vs this currency → red
            textColour = getResources().getColor(R.color.FireBrick, null);
        }

        rateText.setTextColor(textColour);
    }

    @Override
    public void onClick(View v) {
        if (v == gbpToCurrButton) {
            doGbpToCurrency();
        } else if (v == currToGbpButton) {
            doCurrencyToGbp();
        }
    }

    private void doGbpToCurrency() {
        String inputText = gbpInput.getText().toString();

        if (inputText.equals("")) {
            Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            float gbpAmount = Float.parseFloat(inputText);

            if (rate == 0.0f) {
                Toast.makeText(this, "Rate not available", Toast.LENGTH_SHORT).show();
                return;
            }

            float converted = gbpAmount * rate;

            // 🔹 Use the real currency code instead of "in this currency"
            String message = String.format("%.2f GBP = %.2f %s",
                    gbpAmount, converted, currencyCode);

            resultText.setText(message);

        } catch (NumberFormatException e) {
            Log.e("Convert", "NumberFormatException: " + e);
            Toast.makeText(this, "Invalid number", Toast.LENGTH_SHORT).show();
        }
    }

    private void doCurrencyToGbp() {
        String inputText = gbpInput.getText().toString();

        if (inputText.equals("")) {
            Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            float currencyAmount = Float.parseFloat(inputText);

            if (rate == 0.0f) {
                Toast.makeText(this, "Rate not available", Toast.LENGTH_SHORT).show();
                return;
            }

            // 1 GBP = rate * currency
            // so amount_in_currency / rate = amount in GBP
            float converted = currencyAmount / rate;

            // 🔹 Again, use the real currency code
            String message = String.format("%.2f %s = %.2f GBP",
                    currencyAmount, currencyCode, converted);

            resultText.setText(message);

        } catch (NumberFormatException e) {
            Log.e("Convert", "NumberFormatException: " + e);
            Toast.makeText(this, "Invalid number", Toast.LENGTH_SHORT).show();
        }
    }

}
