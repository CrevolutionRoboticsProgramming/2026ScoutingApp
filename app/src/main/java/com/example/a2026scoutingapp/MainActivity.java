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
    private TextView teleopFuel, teleopMovement, teleopTower,
            teleopShots, teleopPasses, teleopPoints,
            autonFuel, autonMovement, autonTower,
            autonShots, autonPasses, autonPoints,
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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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
        teleopShots = findViewById(R.id.teleopShots);
        teleopPasses = findViewById(R.id.teleopPasses);
        teleopPoints = findViewById(R.id.teleopPoints);

        autonFuel = findViewById(R.id.autonFuelText);
        autonMovement = findViewById(R.id.autonMovementText);
        autonTower = findViewById(R.id.autonTowerText);
        autonShots = findViewById(R.id.autonShots);
        autonPasses = findViewById(R.id.autonPasses);
        autonPoints = findViewById(R.id.autonPoints);

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
        teleopPointsPlusButton.setOnClickListener(v -> changeValue(teleopPoints, +1));
        teleopPointsMinusButton.setOnClickListener(v -> changeValue(teleopPoints, -1));
        teleopShotsPlusButton.setOnClickListener(v -> changeValue(teleopShots, +1));
        teleopShotsMinusButton.setOnClickListener(v -> changeValue(teleopShots, -1));
        teleopPassesPlusButton.setOnClickListener(v -> changeValue(teleopPasses, +1));
        teleopPassesMinusButton.setOnClickListener(v -> changeValue(teleopPasses, -1));

        autonPointsPlusButton.setOnClickListener(v -> changeValue(autonPoints, +1));
        autonPointsMinusButton.setOnClickListener(v -> changeValue(autonPoints, -1));
        autonShotsPlusButton.setOnClickListener(v -> changeValue(autonShots, +1));
        autonShotsMinusButton.setOnClickListener(v -> changeValue(autonShots, -1));
        autonPassesPlusButton.setOnClickListener(v -> changeValue(autonPasses, +1));
        autonPassesMinusButton.setOnClickListener(v -> changeValue(autonPasses, -1));

        // --- Submit ---
        submitButton.setOnClickListener(v -> {
            Map<String, Object> matchData = new HashMap<>();

            matchData.put("date", LocalDate.now().toString());
            matchData.put("matchNumber", matchNumber.getText().toString());
            matchData.put("teamNumber", teamNumber.getText().toString());
            matchData.put("user", userTextView.getText().toString());
            matchData.put("comments", commentEditText.getText().toString());

            matchData.put("teleopPoints", teleopPoints.getText().toString());
            matchData.put("teleopShots", teleopShots.getText().toString());
            matchData.put("teleopPasses", teleopPasses.getText().toString());

            matchData.put("autonPoints", autonPoints.getText().toString());
            matchData.put("autonShots", autonShots.getText().toString());
            matchData.put("autonPasses", autonPasses.getText().toString());

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
        int value = Integer.parseInt(textView.getText().toString());
        value = Math.max(0, value + delta);
        textView.setText(String.valueOf(value));
    }

    private void setAutonVisibility(int v) {
        autonFuel.setVisibility(v);
        autonMovement.setVisibility(v);
        autonTower.setVisibility(v);
        autonShots.setVisibility(v);
        autonPasses.setVisibility(v);
        autonPoints.setVisibility(v);
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
        teleopShots.setVisibility(v);
        teleopPasses.setVisibility(v);
        teleopPoints.setVisibility(v);
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
