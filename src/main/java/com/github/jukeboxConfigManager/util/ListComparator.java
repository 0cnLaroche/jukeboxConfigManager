package com.github.jukeboxConfigManager.util;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/**
 * Utility class for comparing lists.
 * @param <E> list elements to be compared
 */
public class ListComparator<E extends Comparable> {

    /**
     * Verify if all elements of List in 1st parameter are included in List of 2nd parameter.
     * Duplications of same element included.
     * @param included
     * @param in
     * @return
     */
    public boolean isIncludedIn(List<E> included, List<E> in) {

        // Lists must ordered other algo won't work
        Collections.sort(included);
        Collections.sort(in);
        ListIterator<E> listIn = in.listIterator();
        ListIterator<E> listIncluded = included.listIterator();

        // O(n)
        while (listIn.hasNext() && listIncluded.hasNext()) {
            E objIn = listIn.next();
            E objIncluded = listIncluded.next();
            if (!(Objects.equals(objIn, objIncluded))) {
                boolean found = false;
                while(listIn.hasNext()) {
                    // Iterate through super list until we found corresponding element,
                    // skipping unrelated ones.
                    objIn = listIn.next();
                    if (Objects.equals(objIn, objIncluded)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    return false;
                }
            }
        }
        // if included list has remaining elements, means they are missing in the other
        // list. Otherwise, everything has been found in super list.
        return !listIncluded.hasNext();
    }
}
