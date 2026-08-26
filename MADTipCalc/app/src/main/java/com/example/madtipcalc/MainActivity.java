//My code
// package com.example.madtipcalc;
//
//import android.os.Bundle;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//    }
//}

//Lecturer code
package com.example.madtipcalc;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{
    private EditText editBillAmount;
    private TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Bind the XML layout resource to this Activity controller [16]
        setContentView(R.layout.activity_main);

        // Bridge the XML UI components to Java variables using unique IDs [18]
        editBillAmount = findViewById(R.id.editBillAmount);
        textResult = findViewById(R.id.textResult);

        // Bind click events to the respective tip percentages [19]
        initTipButton(R.id.button15, 0.15);
        initTipButton(R.id.button18, 0.18);
        initTipButton(R.id.button20, 0.20);
    }

    /**
     * Reusable initialization helper to set click listeners on the tip buttons.
     */
    private void initTipButton(int buttonId, final double percentage) {
        Button button = findViewById(buttonId);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateTip(percentage);
            }
        });
    }

    /**
     * Executes logic to validate input, calculate tip & total, and update UI.
     */
    private void calculateTip(double percentage) {
        String billInput = editBillAmount.getText().toString();

        // Defensive check: prevent application crashes if input is empty
        if (billInput.isEmpty()) {
            Toast.makeText(this, "Please enter a valid bill amount", Toast.LENGTH_SHORT).show();
            return;
        } else {
            double billAmount = Double.parseDouble(billInput);
            double tip = billAmount * percentage;
            double totalBill = billAmount + tip;

            // Fetch string resources dynamically to avoid hard-coded string logic [15]
            Resources res = getResources();
            String formattedTip = String.format("%.2f", tip);
            String formattedTotal = String.format("%.2f", totalBill);

            // Populate placeholders dynamically [14, 15]
            String displayMessage = res.getString(R.string.tip_result_form, formattedTip, formattedTotal);
            textResult.setText(displayMessage);
        }
    }
}
