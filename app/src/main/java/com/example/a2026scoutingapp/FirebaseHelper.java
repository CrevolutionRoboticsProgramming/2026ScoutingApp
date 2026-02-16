package com.example.a2026scoutingapp;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;

import java.util.Map;
import java.util.Objects;


public class FirebaseHelper {
    private final FirebaseAuth auth;
    private final FirebaseFirestore db;

    public FirebaseHelper() {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }
    public void registerUser(String email, String password, FirebaseCallback callback) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        callback.onSuccess();
                    } else {
                        callback.onFailure(Objects.requireNonNull(task.getException()).getMessage());
                    }
                });
    }


    // Signs an existing user into Firebase Auth.
    public void signInUser(String email, String password, FirebaseCallback callback) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onFailure(Objects.requireNonNull(task.getException()).getMessage());
                    }
                });
    }

    public void submitMatchData(
            String date,
            String matchNumber,
            String teamNumber,
            String username,
            Map<String, Object> matchData,
            FirebaseCallback callback
    ) {
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            callback.onFailure("User not logged in");
            return;
        }

        String uid = user.getUid();

        db.collection("matchData")
                .document(date)
                .collection("matches")
                .document("match_" + matchNumber)
                .collection("users")
                .document(uid)
                .set(matchData)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure(e.toString()));
    }


    public interface FirebaseCallback {
        void onSuccess();
        void onFailure(String error);
    }
}

