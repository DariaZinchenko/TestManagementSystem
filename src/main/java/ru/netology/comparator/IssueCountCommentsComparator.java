package ru.netology.comparator;

import ru.netology.domain.Issue;

import java.util.Comparator;

public class IssueCountCommentsComparator implements Comparator<Issue> {

    public int compare(Issue o1, Issue o2) {
        return o1.getCountOfComments() - o2.getCountOfComments();
    }
}