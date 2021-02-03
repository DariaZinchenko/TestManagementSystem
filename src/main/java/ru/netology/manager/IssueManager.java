package ru.netology.manager;

import ru.netology.domain.Issue;
import ru.netology.domain.State;
import ru.netology.exception.NotFoundException;
import ru.netology.repository.IssueRepository;

import java.util.*;
import java.util.function.Predicate;

public class IssueManager {
    private IssueRepository repository = new IssueRepository();

    public void add(Issue issue) {
        repository.save(issue);
    }

    public ArrayList<Issue> findOpenIssues() {
        ArrayList<Issue> result = new ArrayList<>();
        ArrayList<Issue> issues = repository.findAll();

        for (Issue issue : issues) {
            if (issue.isOpen()) {
                result.add(issue);
            }
        }
        Collections.sort(result);
        return result;
    }

    public ArrayList<Issue> findClosedIssues() {
        ArrayList<Issue> result = new ArrayList<>();
        ArrayList<Issue> issues = repository.findAll();

        for (Issue issue : issues) {
            if (!issue.isOpen()) {
                result.add(issue);
            }
        }
        Collections.sort(result);
        return result;
    }

    public ArrayList<Issue> sort(Comparator<Issue> comparator) {
        ArrayList<Issue> result = repository.findAll();
        result.sort(comparator);
        return result;
    }

    public ArrayList<Issue> filterBy(Predicate<Issue> predicate) {
        ArrayList<Issue> issues = repository.findAll();
        ArrayList<Issue> result = new ArrayList<>();

        for (Issue issue: issues) {
            if (predicate.test(issue)){
                result.add(issue);
            }
        }
        return result;
    }

    public ArrayList<Issue> filterByAuthor(int authorId) {
        Predicate<Issue> predicate = i -> i.getAuthorId() == authorId;
        ArrayList<Issue> result = filterBy(predicate);
        Collections.sort(result);
        return result;
    }

    public ArrayList<Issue> filterByLabel(String label) {
        Predicate<Issue> predicate = i -> i.getLabels().contains(label);
        ArrayList<Issue> result = filterBy(predicate);
        Collections.sort(result);
        return result;
    }

    public ArrayList<Issue> filterByAssignee(int assigneesId) {
        Predicate<Issue> predicate = i -> i.getAssigneesId().contains(assigneesId);
        ArrayList<Issue> result = filterBy(predicate);
        Collections.sort(result);
        return result;
    }

    public boolean updateIssueById(int id, State state) {
        if(repository.findById(id) == null) {
            throw new NotFoundException("Element with id: " + id + " not found");
        }

        Issue issue = repository.findById(id);
        boolean result = issue.updateState(state, new GregorianCalendar());
        return result;
    }

    public ArrayList<Issue> findAll() {
        return repository.findAll();
    }
}
