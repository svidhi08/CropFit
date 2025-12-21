package com.example.cropfit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.cropfit.databinding.FragmentUserBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserFragment extends Fragment {
    private FragmentUserBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater, container, false);
        loadUserDetails();
        return binding.getRoot();
    }

    private void loadUserDetails() {
        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) return;

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // 1. Load Name AND Email from User Profile
        db.collection("Users").document(uid).get().addOnSuccessListener(doc -> {
            if (isAdded() && binding != null && doc.exists()) {
                // Fetch name
                String name = doc.getString("name");
                binding.tvProfileName.setText(name != null ? name : "No Name Found");

                // FETCH EMAIL (The part that was missing)
                String email = doc.getString("email");
                binding.tvProfileEmail.setText(email != null ? email : "No Email Found");
            }
        });

        // 2. Count current items in History collection (as you requested)
        db.collection("Users").document(uid).collection("History").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (isAdded() && binding != null) {
                        int count = queryDocumentSnapshots.size();
                        binding.tvTotalPredictions.setText("Total Predictions: " + count);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}