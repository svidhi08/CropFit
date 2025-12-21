package com.example.cropfit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.cropfit.adapters.HistoryAdapter;
import com.example.cropfit.databinding.FragmentHistoryBinding;
import com.example.cropfit.models.CropData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {
    private FragmentHistoryBinding binding;
    private HistoryAdapter adapter;
    private List<CropData> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        list = new ArrayList<>();
        adapter = new HistoryAdapter(list);
        binding.rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvHistory.setAdapter(adapter);

        fetchHistory();
        return binding.getRoot();
    }

    private void fetchHistory() {
        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) return;

        FirebaseFirestore.getInstance().collection("Users").document(uid).collection("History")
                .orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener((value, error) -> {
                    if (!isAdded() || binding == null) return;
                    if (value != null) {
                        list.clear();
                        for (DocumentSnapshot doc : value.getDocuments()) {
                            CropData data = doc.toObject(CropData.class);
                            if (data != null) {
                                data.setId(doc.getId());
                                list.add(data);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        binding.tvNoData.setVisibility(list.isEmpty() ? View.VISIBLE : View.GONE);
                    }
                });

        setupDeleteListener(uid);
    }

    private void setupDeleteListener(String uid) {
        adapter.setOnLongClickListener((position, documentId) -> {
            if (getActivity() == null) return;
            new android.app.AlertDialog.Builder(getActivity())
                    .setTitle("Delete Record")
                    .setMessage("Remove this prediction?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        FirebaseFirestore.getInstance().collection("Users").document(uid)
                                .collection("History").document(documentId).delete();
                    })
                    .setNegativeButton("Cancel", null).show();
        });
    }

    @Override public void onDestroyView() { super.onDestroyView(); binding = null; }
}