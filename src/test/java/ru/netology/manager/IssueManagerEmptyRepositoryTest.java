package ru.netology.manager;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.netology.comparator.IssueCountCommentsComparator;
import ru.netology.comparator.IssueCreatedTimeComparator;
import ru.netology.comparator.IssueUpdatedTimeComparator;
import ru.netology.domain.Issue;
import ru.netology.domain.State;
import ru.netology.exception.NotFoundException;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IssueManagerEmptyRepositoryTest {

    private IssueManager manager = new IssueManager();

    HashSet<Integer> assigneesId = new HashSet<>();
    HashSet<String> labels = new HashSet<>();
    HashSet<Integer> projectsId = new HashSet<>();

    @Nested
    public class FilterAndFindTest{

        @Test
        void findOpenIssuesTest() {
            ArrayList<Issue> actual = manager.findOpenIssues();
            ArrayList<Issue> expected = new ArrayList<>();

            assertEquals(actual, expected);
        }

        @Test
        void findClosedIssuesTest() {
            ArrayList<Issue> actual = manager.findClosedIssues();
            ArrayList<Issue> expected = new ArrayList<>();

            assertEquals(actual, expected);
        }

        @Test
        void filterByAuthorTest() {
            ArrayList<Issue> actual = manager.filterByAuthor(14);
            ArrayList<Issue> expected = new ArrayList<>();

            assertEquals(actual, expected);
        }

        @Test
        void filterByLabelTest() {
            ArrayList<Issue> actual = manager.filterByLabel("Label 10");
            ArrayList<Issue> expected = new ArrayList<>();

            assertEquals(actual, expected);
        }

        @Test
        void filterByAssigneeTest() {
            ArrayList<Issue> actual = manager.filterByAssignee(30);
            ArrayList<Issue> expected = new ArrayList<>();

            assertEquals(actual, expected);
        }
    }

    @Nested
    public class SortTest{
        @Test
        void sortByCreatedTimeTest() {
            IssueCreatedTimeComparator comparator = new IssueCreatedTimeComparator();
            ArrayList<Issue> actual = manager.sort(comparator);
            ArrayList<Issue> expected = new ArrayList<>();

            assertEquals(actual, expected);
        }

        @Test
        void sortByUpdatedTimeTest() {
            IssueUpdatedTimeComparator comparator = new IssueUpdatedTimeComparator();

            ArrayList<Issue> actual = manager.sort(comparator);
            ArrayList<Issue> expected = new ArrayList<>();

            assertEquals(actual, expected);
        }

        @Test
        void sortByCountCommentsTest() {
            IssueCountCommentsComparator comparator = new IssueCountCommentsComparator();
            ArrayList<Issue> actual = manager.sort(comparator);
            ArrayList<Issue> expected = new ArrayList<>();

            assertEquals(actual, expected);
        }
    }

    @Nested
    public class AddTest{
        @Test
        void addIssueTest(){
            Issue issue = new Issue(1, "title 1", "test 1", 11, assigneesId, labels, projectsId, 1, 1, 4);
            manager.add(issue);

            ArrayList<Issue> actual = manager.findAll();
            ArrayList<Issue> expected = new ArrayList<>();

            expected.add(issue);

            assertEquals(actual, expected);
        }
    }

    @Nested
    public class UpdateIssueTest{

        @Test
        void updateIssueByIdTest() {
            assertThrows(NotFoundException.class, () -> manager.updateIssueById(7, State.UPDATE));
        }
    }
}