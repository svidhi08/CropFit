package com.example.cropfit.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cropfit.R;
import com.example.cropfit.models.CropData;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<CropData> historyList;
    private OnLongClickListener listener; // Correctly placed member variable

    // Interface definition
    public interface OnLongClickListener {
        void onLongClick(int position, String documentId);
    }

    public void setOnLongClickListener(OnLongClickListener listener) {
        this.listener = listener;
    }

    public HistoryAdapter(List<CropData> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CropData data = historyList.get(position);

        // Safety check and Capitalization
        String crop = data.getResult();
        if (crop != null && !crop.isEmpty()) {
            holder.cropName.setText(crop.substring(0, 1).toUpperCase() + crop.substring(1));
        } else {
            holder.cropName.setText("Unknown Crop");
        }

        holder.date.setText(data.getDate());
        holder.npkSummary.setText("N:" + data.getN() + " P:" + data.getP() + " K:" + data.getK());
        holder.cropIcon.setImageResource(R.drawable.ic_leaf);

        // Long click listener for deletion
        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null && data.getId() != null) {
                listener.onLongClick(position, data.getId());
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView cropName, date, npkSummary;
        ImageView cropIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cropName = itemView.findViewById(R.id.tvHistoryCrop);
            date = itemView.findViewById(R.id.tvHistoryDate);
            npkSummary = itemView.findViewById(R.id.tvHistoryNPK);
            cropIcon = itemView.findViewById(R.id.ivHistoryIcon);
        }
    }
}