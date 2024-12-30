package com.example;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.view.View;
import android.view.LayoutInflater;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import android.widget.EditText;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {
    private ImageView avatarImageView;
    private TextView usernameTextView;
    private TextView changeUsernameLayout;
    private RadioGroup languageRadioGroup;
    private Switch darkLightSwitch;
    private RadioGroup fontSizeRadioGroup;
    private TextView developeTextView;
    private RadioGroup infoRadioGroup;
    private ImageView backArrow;

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView dialogAvatarImageView;

    private SharedPreferences preferences;

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

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener);

        String savedFontSize = preferences.getString("font_size", "Medium");
        setFontSize(savedFontSize);

        backArrow.setOnClickListener(v -> {
            Intent intent = new Intent(this, VideoFragment.class);
            startActivity(intent);
            finish();
        });

        changeUsernameLayout.setOnClickListener(v -> showChangeUsernameDialog());

        languageRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {});

        darkLightSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {});

        fontSizeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            String fontSize;
            if (checkedId == R.id.font_small) {
                fontSize = "small";
            } else if (checkedId == R.id.font_medium) {
                fontSize = "medium";
            } else if (checkedId == R.id.font_large) {
                fontSize = "large";
            } else {
                fontSize = "medium";
            }
            saveFontSize(fontSize);
            setFontSize(fontSize);
        });

        developeTextView.setOnClickListener(v -> openDeveloperFragment());

        infoRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {});
    }

    private void showChangeUsernameDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_change_username, null);
        EditText usernameEditText = dialogView.findViewById(R.id.usernameInput);
        ImageView dialogAvatarImageView = dialogView.findViewById(R.id.avatarImageView);
        Button changeAvatarButton = dialogView.findViewById(R.id.changeAvatarButton);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    String newUsername = usernameEditText.getText().toString().trim();
                    if (!newUsername.isEmpty()) {
                        updateUsername(newUsername);
                    } else {
                        Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();

        changeAvatarButton.setOnClickListener(v -> openGallery(dialogAvatarImageView));
    }

    private void openGallery(ImageView dialogAvatarImageView) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
        this.dialogAvatarImageView = dialogAvatarImageView;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            setAvatarImage(imageUri);
        }
    }

    private void setAvatarImage(Uri imageUri) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
            dialogAvatarImageView.setImageBitmap(bitmap); // Update the dialog's ImageView
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
        }
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
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("font_size", fontSize);
        editor.apply();
    }

    private void setFontSize(String fontSize) {
        Log.d("FontSize", "Setting font size: " + fontSize);
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

        ViewGroup mainLayout = findViewById(R.id.main_layout);
        setTextViewFontSize(mainLayout, size);
    }

    private void setTextViewFontSize(ViewGroup group, float size) {
        for (int i = 0; i < group.getChildCount(); i++) {
            View child = group.getChildAt(i);
            if (child instanceof TextView) {
                ((TextView) child).setTextSize(size);
            } else if (child instanceof ViewGroup) {
                setTextViewFontSize((ViewGroup) child, size);
            }
        }
    }

    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals("font_size")) {
                String fontSize = sharedPreferences.getString("font_size", "Medium");
                setFontSize(fontSize);  // Update font size
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister the listener when the activity is destroyed
        preferences.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
    }
}
