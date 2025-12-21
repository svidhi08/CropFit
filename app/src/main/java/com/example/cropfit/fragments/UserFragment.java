package com.example.cropfit.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cropfit.databinding.FragmentUserBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class UserFragment extends Fragment {
    private FragmentUserBinding binding;
    private FirebaseFirestore db;
    private String uid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater, container, false);
        db = FirebaseFirestore.getInstance();
        uid = FirebaseAuth.getInstance().getUid();

        loadUserDetails();

        // Click on Name to edit
        binding.tvProfileName.setOnClickListener(v -> showEditDialog("name", binding.tvProfileName.getText().toString()));

        // Click on Email to edit
        binding.tvProfileEmail.setOnClickListener(v -> showEditDialog("email", binding.tvProfileEmail.getText().toString()));

        // Logout functionality
        binding.btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            requireActivity().finish();
        });

        return binding.getRoot();
    }

    private void loadUserDetails() {
        if (uid == null) return;

        db.collection("Users").document(uid).get().addOnSuccessListener(doc -> {
            if (isAdded() && binding != null && doc.exists()) {
                String name = doc.getString("name");
                String email = doc.getString("email");
                binding.tvProfileName.setText(name != null ? name : "Click to set Name");
                binding.tvProfileEmail.setText(email != null ? email : "Click to set Email");
            }
        });

        db.collection("Users").document(uid).collection("History").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (isAdded() && binding != null) {
                        binding.tvTotalPredictions.setText("Total Predictions: " + queryDocumentSnapshots.size());
                    }
                });
    }

    private void showEditDialog(String field, String currentValue) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Update " + field);

        final EditText input = new EditText(getContext());
        input.setText(currentValue);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String newValue = input.getText().toString().trim();
            if (!newValue.isEmpty()) {
                updateUserField(field, newValue);
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void updateUserField(String field, String value) {
        Map<String, Object> update = new HashMap<>();
        update.put(field, value);

        // Using set with Merge handles the "Document does not exist" issue shown in your screenshot
        db.collection("Users").document(uid)
                .set(update, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Updated!", Toast.LENGTH_SHORT).show();
                    loadUserDetails();
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}