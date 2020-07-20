package com.example.sportsclubmanagement.utils;

import android.util.Patterns;

import java.util.regex.Pattern;

public final class Validations {
    public static boolean emailValidation(String email) {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean nameValidation(String name) {
        return name.split(" ").length >= 2 && !Pattern.matches("/^[a-zA-Z\\s]*$/", name);
    }

    public static boolean passwordValidation(String pass) {
        return !pass.isEmpty() && Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$", pass);
    }

    public static boolean confirmPasswordValidation(String passInit, String passConf) {
        return !passInit.isEmpty() && !passInit.isEmpty() && passInit.equals(passConf) && Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$", passInit);
    }

    public static boolean ageValidation(String age) {
        if (age.isEmpty()) return false;
        int age_years = Integer.parseInt(age);
        return age_years >= Constants.minAge && age_years <= Constants.maxAge;
    }

    public static boolean heightValidation(String height) {
        if (height.isEmpty()) return false;
        int height_cm = Integer.parseInt(height);
        return height_cm >= Constants.minHeight && height_cm <= Constants.maxHeight;
    }

    public static boolean weightValidation(String weight) {
        if (weight.isEmpty()) return false;
        double weight_kg = Double.parseDouble(weight);
        return weight_kg >= Constants.minWeight && weight_kg <= Constants.maxWeight;
    }

    public static boolean workoutDurationValidation(String duration) {
        if (duration.isEmpty()) return false;
        double duration_seconds = Double.parseDouble(duration);
        return duration_seconds >= Constants.minWorkoutDuration && duration_seconds <= Constants.maxWorkoutDuration;
    }

    public static boolean heartRateValidation(String heartRate) {
        if (heartRate.isEmpty()) return false;
        int heart_rate = Integer.parseInt(heartRate);
        return heart_rate >= Constants.minWorkoutHeartRate && heart_rate <= Constants.maxWorkoutHeartRate;
    }

    public static boolean caloriesValidation(String calories) {
        if (calories.isEmpty()) return false;
        int calories_c = Integer.parseInt(calories);
        return calories_c >= Constants.minWorkoutCalories && calories_c <= Constants.maxWorkoutCalories;
    }

    public static boolean avSpeedValidation(String avSpeed) {
        if (avSpeed.isEmpty()) return false;
        double avSpeed_mps = Double.parseDouble(avSpeed);
        return avSpeed_mps >= Constants.minWorkoutAvSpeed && avSpeed_mps <= Constants.maxWorkoutAvSpeed;
    }

    public static boolean workoutDistanceValidation(String distance) {
        if (distance.isEmpty()) return false;
        int distance_meters = Integer.parseInt(distance);
        return distance_meters >= Constants.minWorkoutDistance && distance_meters <= Constants.maxWorkoutDistance;
    }
}
