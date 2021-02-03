package ru.netology.repository;

import ru.netology.domain.Issue;
import ru.netology.exception.NotFoundException;

import java.util.ArrayList;

public class IssueRepository {
    private ArrayList<Issue> issues = new ArrayList<>();

    public void save(Issue issue) {
        issues.add(issue);
    }

    public ArrayList<Issue> findAll() {
        return issues;
    }

    public Issue findById(int id) {
        for (Issue issue : issues) {
            if (issue.getId() == id) {
                return issue;
            }
        }
        return null;
    }

    public void removeById(int id) throws NotFoundException {
        issues.removeIf(item -> item.getId() == id);
    }
}
