package com.ensa.akinator.Utils;

import java.util.ArrayList;
import java.util.List;

import com.ensa.akinator.Models.Question;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Functions {
    public static void printList(List<?> list) {
        for (Object object : list) {
            System.out.println(object);
        }
    }

    public static void printListQuestionsIds(ArrayList<Question> list) {
        ArrayList<Integer> listIds = new ArrayList<>();

        for (Question object : list) {
            listIds.add(object.getId());
        }

        System.out.println("Questions (" + listIds.size() + ") : " + listIds);
    }

    public static ArrayList<Integer> intersection(ArrayList<Integer> list1, ArrayList<Integer> list2) {
        ArrayList<Integer> result = new ArrayList<>();

        for (Integer integer : list1) {
            if (list2.contains(integer))
                result.add(integer);
        }

        return result;
    }

    // ! JAVAFX Functions
    public static void showErrorAlert(String title, String message) {
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    public static void showInfoAlert(String title, String message) {
        Alert errorAlert = new Alert(AlertType.INFORMATION);
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }
}
