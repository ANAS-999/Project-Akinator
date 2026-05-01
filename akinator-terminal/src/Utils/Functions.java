package Utils;

import java.util.ArrayList;
import java.util.List;

public class Functions {
    public static void printList(List<?> list) {
        for (Object object : list) {
            System.out.println(object);
        }
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
