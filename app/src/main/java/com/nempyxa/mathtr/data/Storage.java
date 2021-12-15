package com.nempyxa.mathtr.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.nempyxa.mathtr.model.math.MathQuestionGenerationParameters;
import com.nempyxa.mathtr.model.math.Operation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Storage {
    public static final String NAME = "settings";

    private static final String LEFT_MIN = "left_min";
    private static final String LEFT_MAX = "left_max";
    private static final String RIGHT_MIN = "right_min";
    private static final String RIGHT_MAX = "right_max";
    private static final String OPERATIONS = "operations";

    public static final String SETTINGS_CHANGED = "settings_changes";

    private static final int DEFAULT_LEFT_MIN = 2;
    private static final int DEFAULT_LEFT_MAX = 5;
    private static final int DEFAULT_RIGHT_MIN = 10;
    private static final int DEFAULT_RIGHT_MAX = 15;
    private static final List<String> DEFAULT_OPERATIONS = Arrays.asList("ADDITION", "SUBTRACTION", "MULTIPLICATION", "DIVISION");

    public static void storeParameters(MathQuestionGenerationParameters parameters, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(LEFT_MIN, parameters.getLeftArgMin());
        editor.putInt(LEFT_MAX, parameters.getLeftArgMax());
        editor.putInt(RIGHT_MIN, parameters.getRightArgMin());
        editor.putInt(RIGHT_MAX, parameters.getRightArgMax());
        Set<String> set = new HashSet<String>();
        parameters.getOperations().forEach(operation -> set.add(operation.toString()));
        editor.putStringSet(OPERATIONS, set);
        editor.apply();
    }

    public static MathQuestionGenerationParameters readParameters(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        int leftMin = prefs.getInt(LEFT_MIN, DEFAULT_LEFT_MIN);
        int leftMax = prefs.getInt(LEFT_MAX, DEFAULT_LEFT_MAX);
        int rightMin = prefs.getInt(RIGHT_MIN, DEFAULT_RIGHT_MIN);
        int rightMax = prefs.getInt(RIGHT_MAX, DEFAULT_RIGHT_MAX);
        Set<String> set =  prefs.getStringSet(OPERATIONS, new HashSet<String>(DEFAULT_OPERATIONS));
        ArrayList<Operation> operations = new ArrayList<>();
        set.forEach(string -> operations.add(Operation.of(string)));
        return new MathQuestionGenerationParameters(leftMin, leftMax, rightMin, rightMax, operations);
    }

    public static boolean getSettingsChanged(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(SETTINGS_CHANGED, false);
    }

    public static void triggerSettingsChanged(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(SETTINGS_CHANGED, true);
        editor.apply();
    }

    public static void resetSettingsChanged(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(SETTINGS_CHANGED, false);
        editor.apply();
    }
}
