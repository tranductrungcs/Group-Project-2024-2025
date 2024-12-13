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

public class ShowNewsActivity extends AppCompatActivity {
    TextToSpeech textToSpeech;
    Connection con;
    String str;
    SQLconnection sqlconnection;
    ResultSet rs;
    String content;

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
        connect();



    }

    @Override
    protected void onPause(){
        if (textToSpeech != null) {
            textToSpeech.stop();
        }
        super.onPause();
    }

    public void btnClick(View view){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                con = sqlconnection.CONN();
                String query = "SELECT * FROM androidapi.api_article WHERE id = 2";
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                StringBuilder bStr = new StringBuilder("I test this but it should be fine\n");
                while (rs.next()) {
                    bStr.append(rs.getString("title")).append("\n");
                }
                content = bStr.toString();
            } catch (SQLException e){
                throw new RuntimeException(e);
            }
            runOnUiThread(() ->{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                TextView Content = findViewById(R.id.Title);
                Content.setText(content);
            });
        });
    }

    public void connect(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                con = sqlconnection.CONN();
                if (con == null) {
                    str = "connect success";
                } else {
                    str = "Error!";
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            runOnUiThread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
            });
        });
    }


}