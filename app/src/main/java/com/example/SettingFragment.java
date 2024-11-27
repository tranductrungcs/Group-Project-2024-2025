package com.example;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.SeekBar;
import android.widget.EditText;
import android.view.View;
import android.view.LayoutInflater;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;



public class SettingFragment extends AppCompatActivity {
    private ImageView avatarImageView;
    private TextView usernameTextView;
    private TextView changeUsernameLayout;
    private RadioGroup languageRadioGroup;
    private Switch darkLightSwitch;
    private SeekBar fontSizeSeekBar;
    private TextView fontSizeTextView;
    private TextView developeTextView;
    private RadioGroup infoRadioGroup;
    private ImageView backArrow;

    private static final int PICK_IMAGE_REQUEST = 1;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        avatarImageView = findViewById(R.id.avatar_image);
        usernameTextView = findViewById(R.id.username_text);
        changeUsernameLayout = findViewById(R.id.change_username_layout);
        languageRadioGroup = findViewById(R.id.language_group);
        darkLightSwitch = findViewById(R.id.dark_light_switch);
        fontSizeSeekBar = findViewById(R.id.font_size_seekbar);
        fontSizeTextView = findViewById(R.id.font_size_value);
        developeTextView = findViewById(R.id.developer_layout);
        infoRadioGroup = findViewById(R.id.info_group);
        backArrow = findViewById(R.id.back_arrow);


        backArrow.setOnClickListener(v -> {
            Intent intent = new Intent(this, VideoFragment.class);
            startActivity(intent);
            finish();
        });


        changeUsernameLayout.setOnClickListener(v -> showChangeUsernameDialog());


        languageRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.english_button) {

            } else if (checkedId == R.id.vietnamese_button) {

            }
        });


        darkLightSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {

            } else {

            }
        });


        fontSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int fontSize = progress + 12;
                fontSizeTextView.setText(fontSize + "sp");
                updateFontSize(fontSize);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        developeTextView.setOnClickListener(v -> openDeveloperFragment());


        infoRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.news_button) {

            } else if (checkedId == R.id.video_button) {

            }
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
                .setNegativeButton("Cancel", (dialog, which) -> {

                });

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

    private void updateFontSize(int fontSize) {

    }
}