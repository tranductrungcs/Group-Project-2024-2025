package com.example;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.view.View;
import android.view.LayoutInflater;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import android.widget.EditText;

public class SettingFragment extends AppCompatActivity {
    private ImageView avatarImageView;
    private TextView usernameTextView;
    private TextView changeUsernameLayout;
    private RadioGroup languageRadioGroup;
    private Switch darkLightSwitch;
    private RadioGroup fontSizeRadioGroup;
    private TextView developeTextView;
    private RadioGroup infoRadioGroup;
    private ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        avatarImageView = findViewById(R.id.avatar_image);
        usernameTextView = findViewById(R.id.username_text);
        changeUsernameLayout = findViewById(R.id.change_username_layout);
        languageRadioGroup = findViewById(R.id.language_group);
        darkLightSwitch = findViewById(R.id.dark_light_switch);
        fontSizeRadioGroup = findViewById(R.id.font_size_group);
        developeTextView = findViewById(R.id.developer_layout);
        infoRadioGroup = findViewById(R.id.info_group);
        backArrow = findViewById(R.id.back_arrow);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedFontSize = preferences.getString("font_size", "Medium");
        setFontSize(savedFontSize);

        backArrow.setOnClickListener(v -> {
            Intent intent = new Intent(this, VideoFragment.class);
            startActivity(intent);
            finish();
        });

        changeUsernameLayout.setOnClickListener(v -> showChangeUsernameDialog());

        languageRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
        });

        darkLightSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
        });

        fontSizeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            String fontSize;
            if (checkedId == R.id.font_small) {
                fontSize = "Small";
            } else if (checkedId == R.id.font_medium) {
                fontSize = "Medium";
            } else if (checkedId == R.id.font_large) {
                fontSize = "Large";
            } else {
                fontSize = "Medium";
            }
            saveFontSize(fontSize);
            setFontSize(fontSize);
        });

        developeTextView.setOnClickListener(v -> openDeveloperFragment());

        infoRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
        });
    }

    private void showChangeUsernameDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_change_username, null);
        EditText usernameEditText = dialogView.findViewById(R.id.usernameInput);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    String newUsername = usernameEditText.getText().toString();
                    updateUsername(newUsername);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {});

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openDeveloperFragment() {
        fragment_developer developerFragment = new fragment_developer();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.developer_layout, developerFragment)
                .addToBackStack(null)
                .commit();
    }

    private void updateUsername(String newUsername) {
        usernameTextView.setText(newUsername);
    }

    private void saveFontSize(String fontSize) {
        Log.d("FontSize", "Saving font size: " + fontSize);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("font_size", fontSize);
        editor.apply();
    }

    private void setFontSize(String fontSize) {
        Log.d("FontSize", "Setting font size: " + fontSize);
        float size = 16;
        switch (fontSize) {
            case "Small":
                size = 12;
                break;
            case "Medium":
                size = 16;
                break;
            case "Large":
                size = 20;
                break;
            default:
                size = 16;
        }
        usernameTextView.setTextSize(size);
        changeUsernameLayout.setTextSize(size);
        developeTextView.setTextSize(size);

    }
}