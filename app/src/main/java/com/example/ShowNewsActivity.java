package com.example;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;

public class ShowNewsActivity extends AppCompatActivity {
    TextToSpeech textToSpeech;
    Connection con;
    String str;
    SQLconnection sqlconnection;
    ResultSet rs;
    String content;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_news);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sqlconnection = new SQLconnection();


        preferences = PreferenceManager.getDefaultSharedPreferences(this);




        ImageButton Playbutton = findViewById(R.id.Playbutton);
        TextView Content = findViewById(R.id.NewsContent);
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });

        Playbutton.setOnClickListener(view -> textToSpeech.speak(Content.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, null));

        // Apply the font size
        String savedFontSize = preferences.getString("font_size", "Medium");
        setFontSize(savedFontSize);

        // Listener for font size change in SharedPreferences
        preferences.registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> {
            if (key.equals("font_size")) {
                String fontSize = sharedPreferences.getString("font_size", "Medium");
                setFontSize(fontSize);
            }
        });
    }

    @Override
    protected void onPause() {
        if (textToSpeech != null) {
            textToSpeech.stop();
        }
        super.onPause();
    }





    // Method to apply the font size to all text views
    private void setFontSize(String fontSize) {
        float size = 16;
        switch (fontSize) {
            case "small":
                size = 12;
                break;
            case "medium":
                size = 16;
                break;
            case "large":
                size = 20;
                break;
        }

        TextView Content = findViewById(R.id.NewsContent);
        if (Content != null) {
            Content.setTextSize(size);
        }
    }
}