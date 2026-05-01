package Utils;

import java.util.ArrayList;
import java.util.List;

import Entities.Question;

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

        System.out.println("Questions ("+ listIds.size() +") : " + listIds);
    }

    public static ArrayList<Integer> intersection(ArrayList<Integer> list1, ArrayList<Integer> list2) {
        ArrayList<Integer> result = new ArrayList<>();

        for (Integer integer : list1) {
            if (list2.contains(integer))
                result.add(integer);
        }

        return result;
    }
}
