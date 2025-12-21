package com.example.cropfit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.cropfit.CropModel;
import com.example.cropfit.activities.ResultActivity;
import com.example.cropfit.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private final String[] crops = {"apple", "banana", "blackgram", "chickpea", "coconut", "coffee", "cotton", "grapes", "jute", "kidneybeans", "lentil", "maize", "mango", "mothbeans", "mungbean", "muskmelon", "orange", "papaya", "pigeonpeas", "pomegranate", "rice", "watermelon"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        setupSeekBars();
        binding.btnPredict.setOnClickListener(v -> runPrediction());
        return binding.getRoot();
    }

    private void setupSeekBars() {
        setSeekBarListener(binding.sbN, binding.tvNValue, "");
        setSeekBarListener(binding.sbP, binding.tvPValue, "");
        setSeekBarListener(binding.sbK, binding.tvKValue, "");
        setSeekBarListener(binding.sbTemp, binding.tvTempValue, "°C");
        setSeekBarListener(binding.sbHum, binding.tvHumValue, "%");
        setSeekBarListener(binding.sbPH, binding.tvPHValue, "");
        setSeekBarListener(binding.sbRain, binding.tvRainValue, " mm");
    }

    private void setSeekBarListener(SeekBar sb, TextView tv, String unit) {
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv.setText((sb == binding.sbPH ? String.format(Locale.getDefault(), "%.1f", progress / 10.0f) : progress) + unit);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void runPrediction() {
        try {
            double n = binding.sbN.getProgress();
            double p = binding.sbP.getProgress();
            double k = binding.sbK.getProgress();
            double t = binding.sbTemp.getProgress();
            double h = binding.sbHum.getProgress();
            double ph = binding.sbPH.getProgress() / 10.0;
            double r = binding.sbRain.getProgress();

            double[] scores = CropModel.score(new double[]{n, p, k, t, h, ph, r});
            int bestIndex = 0;
            double maxScore = scores[0];
            for (int i = 1; i < scores.length; i++) {
                if (scores[i] > maxScore) { maxScore = scores[i]; bestIndex = i; }
            }

            String predictedCrop = crops[bestIndex];
            saveToFirestore(n, p, k, ph, t, h, r, predictedCrop);

            Intent intent = new Intent(requireActivity(), ResultActivity.class);
            intent.putExtra("CROP_NAME", predictedCrop);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Prediction failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveToFirestore(double n, double p, double k, double ph, double t, double h, double r, String result) {
        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) return;

        Map<String, Object> data = new HashMap<>();
        data.put("n", (int)n);
        data.put("p", (int)p);
        data.put("k", (int)k);
        data.put("ph", ph);
        data.put("temp", (int)t);
        data.put("humidity", (int)h);
        data.put("rainfall", (int)r);
        data.put("result", result);
        data.put("date", new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(new Date()));

        FirebaseFirestore.getInstance().collection("Users").document(uid).collection("History").add(data);
    }

    @Override public void onDestroyView() { super.onDestroyView(); binding = null; }
}