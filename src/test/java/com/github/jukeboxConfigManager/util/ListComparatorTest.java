package com.github.jukeboxConfigManager.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ListComparatorTest {

    @Test
    public void isIncludedIn() {

        ListComparator<String> comparator = new ListComparator<>();
        // Base list
        String[] l1 = new String[] {"bateau", "avion","chameau","blah","blahblah","avion"};
        // Same but different order
        String[] l2 = new String[] {"avion","blahblah","bateau","chameau","blah","avion"};
        // Super set
        String[] l3 = new String[] {"avion","chameau","blah","bateau","blahblah","avion",
                "bateau","banane","avion","avion"};
        // missing element
        String[] l4 = new String[] {"avion","blahblah","chameau","blah","avion"};
        // > 2 elements are different
        String[] l5 = new String[] {"bvion","wouff","popcorn","chameau","blah","tvion"};

        List<String> included = new ArrayList<>(0);

        List<String> in = Arrays.asList(l2);

        assertTrue("In is same but different order", comparator.isIncludedIn(included, in));

        included = Arrays.asList(l1);

        assertTrue("In is same but different order", comparator.isIncludedIn(included, in));

        in = Arrays.asList(l3);

        assertTrue("In is Super set", comparator.isIncludedIn(included, in));

        in = new ArrayList<>(0);

        assertFalse("In is empty", comparator.isIncludedIn(included, in));

        in = Arrays.asList(l4);

        assertFalse("In is missing 1 element", comparator.isIncludedIn(included, in));

        in = Arrays.asList(l5);

        assertFalse("In has 2 different elements", comparator.isIncludedIn(included, in));
    }
}