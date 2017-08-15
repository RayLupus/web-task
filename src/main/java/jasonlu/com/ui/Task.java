package jasonlu.com.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author xiaochuan.luxc
 */
public class Task {

    public static List<Character> uniqueInOrder(String input) {
        List<Character> results = new ArrayList<Character>();
        if (input == null) return results;
        for (int i = 0; i < input.length(); i++) {
            char c = Character.toUpperCase(input.charAt(i));
            if (StringUtils.isBlank(String.valueOf(c))) continue;
            if (!Character.isLetter(c)) throw new IllegalArgumentException();
            if (!results.contains(c)) results.add(c);
        }
        Collections.sort(results, new Comparator<Character>() {

            @Override
            public int compare(Character o1, Character o2) {
                return o2.compareTo(o1);
            }

        });
        return results;
    }

}
