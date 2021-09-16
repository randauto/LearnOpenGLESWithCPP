package com.bip.learnopengleswithcpp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.bip.learnopengleswithcpp.databinding.ActivityMainBinding;
import com.bip.learnopengleswithcpp.GameLibJNIWrapper;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Example of a call to a native method
        TextView tv = binding.sampleText;
        tv.setText(GameLibJNIWrapper.stringFromJNI());
    }

}