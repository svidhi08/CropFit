package com.example.cropfit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.example.cropfit.databinding.ActivitySignupBinding;
import com.example.cropfit.models.User;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {
    private ActivitySignupBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        binding.btnSignup.setOnClickListener(v -> {
            String name = binding.etName.getText().toString().trim();
            String email = binding.etEmail.getText().toString().trim();
            String pass = binding.etPassword.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || pass.length() < 6) {
                Toast.makeText(this, "Complete all fields (Pass min 6 chars)", Toast.LENGTH_SHORT).show();
            } else {
                registerUser(name, email, pass);
            }
        });
    }

    private void registerUser(String name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String uid = mAuth.getCurrentUser().getUid();
                User user = new User(name, email, uid);

                db.collection("Users").document(uid).set(user)
                        .addOnSuccessListener(aVoid -> {
                            startActivity(new Intent(this, MainActivity.class));
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "Profile Creation Failed: Check Internet", Toast.LENGTH_SHORT).show();
                        });
            } else {
                // Shows the real reason (e.g., "Email already in use" or "Network Timeout")
                Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}