package com.example.cropfit.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cropfit.databinding.ActivityResultBinding;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    private ActivityResultBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Setup View Binding
        // This connects the Java code to your activity_result.xml layout
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 2. Get the prediction result from HomeFragment
        // HomeFragment sends the crop name using the key "CROP_NAME"
        String cropResult = getIntent().getStringExtra("CROP_NAME");

        // 3. Display the result in the TextView
        if (cropResult != null && !cropResult.isEmpty()) {
            // Converts "rice" to "RICE" for a better UI look
            binding.tvFinalResult.setText(cropResult.toUpperCase(Locale.ROOT));
        } else {
            // Fallback in case something goes wrong with the intent
            binding.tvFinalResult.setText("RECOMMENDATION NOT FOUND");
        }

        // 4. Back Button logic
        // Clicking this takes the user back to the Home tab
        binding.btnBack.setOnClickListener(v -> {
            finish();
        });
    }
}