package ru.netology.comparator;

import ru.netology.domain.Issue;

import java.util.Comparator;

public class IssueCreatedTimeComparator implements Comparator<Issue> {

    public int compare(Issue o1, Issue o2) {
        return o1.getStateHistoryTime().get(0).compareTo(o2.getStateHistoryTime().get(0));
    }
}