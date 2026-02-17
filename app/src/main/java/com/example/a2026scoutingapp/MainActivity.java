package com.example.a2026scoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private boolean isAutonMode = false;

    // EditTexts
    private EditText matchNumber, teamNumber, commentEditText;

    // Buttons
    private Button autonButton, submitButton;
    private Button teleopPointsPlusButton, teleopPointsMinusButton,
            teleopShotsPlusButton, teleopShotsMinusButton,
            teleopPassesPlusButton, teleopPassesMinusButton,
            autonPointsPlusButton, autonPointsMinusButton,
            autonShotsPlusButton, autonShotsMinusButton,
            autonPassesPlusButton, autonPassesMinusButton;

    // TextViews
    private TextView teleopFuel, teleopMovement, teleopTower,teleopShotsText,
            teleopPassesText, teleopPointsText, teleopShotsScore,
            teleopPassesScore, teleopPointsScore, autonFuel, autonMovement,
            autonTower, autonShotsText, autonPassesText, autonPointsText,
            autonShotsScore, autonPassesScore, autonPointsScore,
            userTextView;

    // CheckBoxes
    private CheckBox teleopClimbCheckbox, teleopL1Checkbox, teleopL2CheckBox,
            teleopL3CheckBox, teleopRampCheckbox, teleopHPCheckbox,
            teleopGroundPickupCheckbox, teleopTrenchCheckbox,
            autonClimbCheckbox, autonL1Checkbox, autonL2CheckBox,
            autonL3CheckBox, autonRampCheckbox, autonHPCheckbox,
            autonGroundPickupCheckbox, autonTrenchCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.activity_main);

        FirebaseHelper firebaseHelper = new FirebaseHelper();

        // --- Find Views ---
        matchNumber = findViewById(R.id.matchNumber);
        teamNumber = findViewById(R.id.teamNumber);
        commentEditText = findViewById(R.id.commentEditText);
        userTextView = findViewById(R.id.userTextView);

        autonButton = findViewById(R.id.autonButton);
        submitButton = findViewById(R.id.SubmitButton);

        teleopFuel = findViewById(R.id.teleopFuelText);
        teleopMovement = findViewById(R.id.teleopMovementText);
        teleopTower = findViewById(R.id.teleopTowerText);
        teleopShotsText = findViewById(R.id.teleopShots);
        teleopPassesText = findViewById(R.id.teleopPasses);
        teleopPointsText = findViewById(R.id.teleopPoints);
        teleopShotsScore = findViewById(R.id.teleopShotsScore);
        teleopPassesScore = findViewById(R.id.teleopPassesScore);
        teleopPointsScore = findViewById(R.id.teleopPointsScore);

        autonFuel = findViewById(R.id.autonFuelText);
        autonMovement = findViewById(R.id.autonMovementText);
        autonTower = findViewById(R.id.autonTowerText);
        autonShotsText = findViewById(R.id.autonShots);
        autonPassesText = findViewById(R.id.autonPasses);
        autonPointsText = findViewById(R.id.autonPoints);
        autonShotsScore = findViewById(R.id.autonShotsScore);
        autonPassesScore = findViewById(R.id.autonPassesScore);
        autonPointsScore = findViewById(R.id.autonPointsScore);

        teleopPointsPlusButton = findViewById(R.id.teleopPointsPlusButton);
        teleopPointsMinusButton = findViewById(R.id.teleopPointsMinusButton);
        teleopShotsPlusButton = findViewById(R.id.teleopShotsPlusButton);
        teleopShotsMinusButton = findViewById(R.id.teleopShotsMinusButton);
        teleopPassesPlusButton = findViewById(R.id.teleopPassesPlusButton);
        teleopPassesMinusButton = findViewById(R.id.teleopPassesMinusButton);

        autonPointsPlusButton = findViewById(R.id.autonPointsPlusButton);
        autonPointsMinusButton = findViewById(R.id.autonPointsMinusButton);
        autonShotsPlusButton = findViewById(R.id.autonShotsPlusButton);
        autonShotsMinusButton = findViewById(R.id.autonShotsMinusButton);
        autonPassesPlusButton = findViewById(R.id.autonPassesPlusButton);
        autonPassesMinusButton = findViewById(R.id.autonPassesMinusButton);

        teleopClimbCheckbox = findViewById(R.id.teleopClimbCheckbox);
        teleopL1Checkbox = findViewById(R.id.teleopL1Checkbox);
        teleopL2CheckBox = findViewById(R.id.teleopL2CheckBox);
        teleopL3CheckBox = findViewById(R.id.teleopL3Checkbox);
        teleopRampCheckbox = findViewById(R.id.teleopRampCheckbox);
        teleopHPCheckbox = findViewById(R.id.teleopHPCheckbox);
        teleopGroundPickupCheckbox = findViewById(R.id.teleopGroundPickupCheckbox);
        teleopTrenchCheckbox = findViewById(R.id.teleopTrenchCheckbox);

        autonClimbCheckbox = findViewById(R.id.autonClimbCheckbox);
        autonL1Checkbox = findViewById(R.id.autonL1Checkbox);
        autonL2CheckBox = findViewById(R.id.autonL2CheckBox);
        autonL3CheckBox = findViewById(R.id.autonL3Checkbox);
        autonRampCheckbox = findViewById(R.id.autonRampCheckbox);
        autonHPCheckbox = findViewById(R.id.autonHPCheckbox);
        autonGroundPickupCheckbox = findViewById(R.id.autonGroundPickupCheckbox);
        autonTrenchCheckbox = findViewById(R.id.autonTrenchCheckbox);

        // --- Initial Mode ---
        setAutonVisibility(View.GONE);
        setTeleopVisibility(View.VISIBLE);

        autonButton.setOnClickListener(v -> toggleMode());

        // --- Button Logic ---
        teleopPointsPlusButton.setOnClickListener(v -> changeValue(teleopPointsScore, +1));
        teleopPointsMinusButton.setOnClickListener(v -> changeValue(teleopPointsScore, -1));
        teleopShotsPlusButton.setOnClickListener(v -> changeValue(teleopShotsScore, +1));
        teleopShotsMinusButton.setOnClickListener(v -> changeValue(teleopShotsScore, -1));
        teleopPassesPlusButton.setOnClickListener(v -> changeValue(teleopPassesScore, +1));
        teleopPassesMinusButton.setOnClickListener(v -> changeValue(teleopPassesScore, -1));

        autonPointsPlusButton.setOnClickListener(v -> changeValue(autonPointsScore, +1));
        autonPointsMinusButton.setOnClickListener(v -> changeValue(autonPointsScore, -1));
        autonShotsPlusButton.setOnClickListener(v -> changeValue(autonShotsScore, +1));
        autonShotsMinusButton.setOnClickListener(v -> changeValue(autonShotsScore, -1));
        autonPassesPlusButton.setOnClickListener(v -> changeValue(autonPassesScore, +1));
        autonPassesMinusButton.setOnClickListener(v -> changeValue(autonPassesScore, -1));

        // --- Submit ---
        submitButton.setOnClickListener(v -> {
            Map<String, Object> matchData = new HashMap<>();

            matchData.put("date", LocalDate.now().toString());
            matchData.put("matchNumber", matchNumber.getText().toString());
            matchData.put("teamNumber", teamNumber.getText().toString());
            matchData.put("user", userTextView.getText().toString());
            matchData.put("comments", commentEditText.getText().toString());

            matchData.put("teleopPoints", teleopPointsScore.getText().toString());
            matchData.put("teleopShots", teleopShotsScore.getText().toString());
            matchData.put("teleopPasses", teleopPassesScore.getText().toString());

            matchData.put("autonPoints", autonPointsScore.getText().toString());
            matchData.put("autonShots", autonShotsScore.getText().toString());
            matchData.put("autonPasses", autonPassesScore.getText().toString());

            firebaseHelper.submitMatchData(
                    LocalDate.now().toString(),
                    matchNumber.getText().toString(),
                    teamNumber.getText().toString(),
                    userTextView.getText().toString(),
                    matchData,
                    new FirebaseHelper.FirebaseCallback() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(MainActivity.this, "Match Submitted!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                            finish();
                        }

                        @Override
                        public void onFailure(String error) {
                            Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
                        }
                    }
            );
        });
    }

    private void toggleMode() {
        isAutonMode = !isAutonMode;
        setAutonVisibility(isAutonMode ? View.VISIBLE : View.GONE);
        setTeleopVisibility(isAutonMode ? View.GONE : View.VISIBLE);
        autonButton.setText(isAutonMode ? "TELEOP" : "AUTON");
    }

    private void changeValue(TextView textView, int delta) {
        String text = textView.getText().toString().trim();

        int value = 0; // default safe value

        try {
            value = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            // If text is not a number, reset to 0
            value = 0;
        }

        value = Math.max(0, value + delta);
        textView.setText(String.valueOf(value));
    }


    private void setAutonVisibility(int v) {
        autonFuel.setVisibility(v);
        autonMovement.setVisibility(v);
        autonTower.setVisibility(v);
        autonPointsText.setVisibility(v);
        autonShotsText.setVisibility(v);
        autonPassesText.setVisibility(v);
        autonShotsScore.setVisibility(v);
        autonPassesScore.setVisibility(v);
        autonPointsScore.setVisibility(v);
        autonPointsPlusButton.setVisibility(v);
        autonPointsMinusButton.setVisibility(v);
        autonShotsPlusButton.setVisibility(v);
        autonShotsMinusButton.setVisibility(v);
        autonPassesPlusButton.setVisibility(v);
        autonPassesMinusButton.setVisibility(v);
        autonClimbCheckbox.setVisibility(v);
        autonL1Checkbox.setVisibility(v);
        autonL2CheckBox.setVisibility(v);
        autonL3CheckBox.setVisibility(v);
        autonRampCheckbox.setVisibility(v);
        autonHPCheckbox.setVisibility(v);
        autonGroundPickupCheckbox.setVisibility(v);
        autonTrenchCheckbox.setVisibility(v);
    }

    private void setTeleopVisibility(int v) {
        teleopFuel.setVisibility(v);
        teleopMovement.setVisibility(v);
        teleopTower.setVisibility(v);
        teleopPointsText.setVisibility(v);
        teleopShotsText.setVisibility(v);
        teleopPassesText.setVisibility(v);
        teleopShotsScore.setVisibility(v);
        teleopPassesScore.setVisibility(v);
        teleopPointsScore.setVisibility(v);
        teleopPointsPlusButton.setVisibility(v);
        teleopPointsMinusButton.setVisibility(v);
        teleopShotsPlusButton.setVisibility(v);
        teleopShotsMinusButton.setVisibility(v);
        teleopPassesPlusButton.setVisibility(v);
        teleopPassesMinusButton.setVisibility(v);
        teleopClimbCheckbox.setVisibility(v);
        teleopL1Checkbox.setVisibility(v);
        teleopL2CheckBox.setVisibility(v);
        teleopL3CheckBox.setVisibility(v);
        teleopRampCheckbox.setVisibility(v);
        teleopHPCheckbox.setVisibility(v);
        teleopGroundPickupCheckbox.setVisibility(v);
        teleopTrenchCheckbox.setVisibility(v);
    }
}
