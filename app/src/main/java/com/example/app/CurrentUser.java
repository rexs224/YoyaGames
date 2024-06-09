package com.example.app;

import com.google.firebase.auth.FirebaseUser;

public class CurrentUser {
    private static FirebaseUser currentUser;

    public static FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(FirebaseUser user) {
        currentUser = user;
    }
}
