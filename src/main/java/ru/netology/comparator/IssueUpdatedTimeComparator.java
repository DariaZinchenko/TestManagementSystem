package ru.netology.comparator;

import ru.netology.domain.Issue;

import java.util.Comparator;

public class IssueUpdatedTimeComparator implements Comparator<Issue> {

    public int compare(Issue o1, Issue o2) {
        int lastIndex1 = o1.getStateHistoryTime().size() - 1;
        int lastIndex2 = o2.getStateHistoryTime().size() - 1;

        return o1.getStateHistoryTime().get(lastIndex1).compareTo(o2.getStateHistoryTime().get(lastIndex2));
    }
}