package ru.netology.manager;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.netology.comparator.IssueCountCommentsComparator;
import ru.netology.comparator.IssueCreatedTimeComparator;
import ru.netology.comparator.IssueUpdatedTimeComparator;
import ru.netology.domain.Issue;
import ru.netology.domain.State;
import ru.netology.exception.NotFoundException;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IssueManagerSingleElementRepositoryTest {

    private IssueManager manager = new IssueManager();

    private void setAssignees (HashSet<Integer> assigneesId, int id1, int id2, int id3) {
        assigneesId.add(id1);
        assigneesId.add(id2);
        assigneesId.add(id3);
    }

    private void setLabels (HashSet<String> labels, String text1, String text2, String text3) {
        labels.add(text1);
        labels.add(text2);
        labels.add(text3);
    }

    private void setProjects (HashSet<Integer> projectsId) {
        projectsId.add(93);
        projectsId.add(94);
        projectsId.add(95);
    }

    HashSet<Integer> assigneesId = new HashSet<>();
    HashSet<String> labels = new HashSet<>();
    HashSet<Integer> projectsId = new HashSet<>();

    private void setUp (IssueManager manager, Issue issue){

        setAssignees (assigneesId, 24, 25, 26);
        setLabels(labels, "Label 1", "Label 2", "Label 3");
        setProjects(projectsId);

        manager.add(issue);
    }

    private State StringToState(String stateString){

        switch(stateString) {
            case "State.CREATE":
                return State.CREATE;
            case "State.UPDATE":
                return State.UPDATE;
            case "State.CLOSE":
                return State.CLOSE;
            case "State.REOPEN":
                return State.REOPEN;
            default:
                throw new IllegalStateException("Unexpected value: " + stateString);
        }
    }

    @Nested
    public class FilterAndFindTest{
        @Nested
        public class EmptyResult{

            @Test
            void findOpenIssuesTest() {
                Issue issue = new Issue(1, "title 1", "test 1", 11, assigneesId, labels, projectsId, 1, 1, 4);
                setUp(manager, issue);
                manager.updateIssueById(1, State.CLOSE);

                ArrayList<Issue> actual = manager.findOpenIssues();
                ArrayList<Issue> expected = new ArrayList<>();

                assertEquals(actual, expected);
            }

            @Test
            void findClosedIssuesTest() {
                Issue issue = new Issue(1, "title 1", "test 1", 11, assigneesId, labels, projectsId, 1, 1, 4);
                setUp(manager, issue);

                ArrayList<Issue> actual = manager.findClosedIssues();
                ArrayList<Issue> expected = new ArrayList<>();

                assertEquals(actual, expected);
            }

            @Test
            void filterByAuthorTest() {
                Issue issue = new Issue(1, "title 1", "test 1", 11, assigneesId, labels, projectsId, 1, 1, 4);
                setUp(manager, issue);

                ArrayList<Issue> actual = manager.filterByAuthor(14);
                ArrayList<Issue> expected = new ArrayList<>();

                assertEquals(actual, expected);
            }

            @Test
            void filterByLabelTest() {
                Issue issue = new Issue(1, "title 1", "test 1", 11, assigneesId, labels, projectsId, 1, 1, 4);
                setUp(manager, issue);

                ArrayList<Issue> actual = manager.filterByLabel("Label 10");
                ArrayList<Issue> expected = new ArrayList<>();

                assertEquals(actual, expected);
            }

            @Test
            void filterByAssigneeTest() {
                Issue issue = new Issue(1, "title 1", "test 1", 11, assigneesId, labels, projectsId, 1, 1, 4);
                setUp(manager, issue);

                ArrayList<Issue> actual = manager.filterByAssignee(30);
                ArrayList<Issue> expected = new ArrayList<>();

                assertEquals(actual, expected);
            }
        }

        @Nested
        public class SingleElementResult{
            @Test
            void findOpenIssuesTest() {
                Issue issue = new Issue(1, "title 1", "test 1", 11, assigneesId, labels, projectsId, 1, 1, 4);
                setUp(manager, issue);

                ArrayList<Issue> actual = manager.findOpenIssues();
                ArrayList<Issue> expected = new ArrayList<>();
                expected.add(issue);

                assertEquals(actual, expected);
            }

            @Test
            void findClosedIssuesTest() {
                Issue issue = new Issue(1, "title 1", "test 1", 11, assigneesId, labels, projectsId, 1, 1, 4);
                setUp(manager, issue);
                manager.updateIssueById(1, State.CLOSE);

                ArrayList<Issue> actual = manager.findClosedIssues();
                ArrayList<Issue> expected = new ArrayList<>();
                expected.add(issue);

                assertEquals(actual, expected);
            }

            @Test
            void filterByAuthorTest() {
                Issue issue = new Issue(1, "title 1", "test 1", 11, assigneesId, labels, projectsId, 1, 1, 4);
                setUp(manager, issue);

                ArrayList<Issue> actual = manager.filterByAuthor(11);
                ArrayList<Issue> expected = new ArrayList<>();
                expected.add(issue);

                assertEquals(actual, expected);
            }

            @Test
            void filterByLabelTest() {
                Issue issue = new Issue(1, "title 1", "test 1", 11, assigneesId, labels, projectsId, 1, 1, 4);
                setUp(manager, issue);

                ArrayList<Issue> actual = manager.filterByLabel("Label 1");
                ArrayList<Issue> expected = new ArrayList<>();
                expected.add(issue);

                assertEquals(actual, expected);
            }

            @Test
            void filterByAssigneeTest() {
                Issue issue = new Issue(1, "title 1", "test 1", 11, assigneesId, labels, projectsId, 1, 1, 4);
                setUp(manager, issue);

                ArrayList<Issue> actual = manager.filterByAssignee(24);
                ArrayList<Issue> expected = new ArrayList<>();
                expected.add(issue);

                assertEquals(actual, expected);
            }
        }
    }

    @Nested
    public class SortTest{
        @Test
        void sortByCreatedTimeTest() {
            Issue issue = new Issue(1, "title 1", "test 1", 11, assigneesId, labels, projectsId, 1, 1, 4);
            setUp(manager, issue);

            IssueCreatedTimeComparator comparator = new IssueCreatedTimeComparator();

            ArrayList<Issue> actual = manager.sort(comparator);
            ArrayList<Issue> expected = new ArrayList<>();
            expected.add(issue);

            assertEquals(actual, expected);
        }

        @Test
        void sortByUpdatedTimeTest() {
            Issue issue = new Issue(1, "title 1", "test 1", 11, assigneesId, labels, projectsId, 1, 1, 4);
            setUp(manager, issue);

            IssueUpdatedTimeComparator comparator = new IssueUpdatedTimeComparator();

            ArrayList<Issue> actual = manager.sort(comparator);
            ArrayList<Issue> expected = new ArrayList<>();
            expected.add(issue);

            assertEquals(actual, expected);
        }

        @Test
        void sortByCountCommentsTest() {
            Issue issue = new Issue(1, "title 1", "test 1", 11, assigneesId, labels, projectsId, 1, 1, 4);
            setUp(manager, issue);

            IssueCountCommentsComparator comparator = new IssueCountCommentsComparator();

            ArrayList<Issue> actual = manager.sort(comparator);
            ArrayList<Issue> expected = new ArrayList<>();
            expected.add(issue);

            assertEquals(actual, expected);
        }
    }

    @Nested
    public class AddTest{
        @Test
        void addIssueTest(){
            Issue issue = new Issue(1, "title 1", "test 1", 11, assigneesId, labels, projectsId, 1, 1, 4);
            Issue issue2 = new Issue(2, "title 2", "test 2", 12, assigneesId, labels, projectsId, 1, 1, 4);
            setUp(manager, issue);

            manager.add(issue2);

            ArrayList<Issue> actual = manager.findAll();
            ArrayList<Issue> expected = new ArrayList<>();

            expected.add(issue);
            expected.add(issue2);

            assertEquals(actual, expected);
        }
    }

    @Nested
    public class UpdateIssueTest{

        @Nested
        public class ExistId{
            @ParameterizedTest(name = "{index} {0}")
            @CsvSource({
              "Set State.CREATE, State.CREATE, false",
              "Set State.UPDATE, State.UPDATE, true",
              "Set State.CLOSE, State.CLOSE, true",
              "Set State.REOPEN, State.REOPEN, false"})
            void updateIssueByIdIssueCreatedTest(String testName, String stateString, boolean expected) {
                Issue issue = new Issue(1, "title 1", "test 1", 11, assigneesId, labels, projectsId, 1, 1, 4);
                setUp(manager, issue);

                State state = StringToState(stateString);
                boolean actual = manager.updateIssueById(1, state);
                assertEquals(actual, expected);
            }

            @ParameterizedTest(name = "{index} {0}")
            @CsvSource({
              "Set State.CREATE, State.CREATE, false",
              "Set State.UPDATE, State.UPDATE, true",
              "Set State.CLOSE, State.CLOSE, true",
              "Set State.REOPEN, State.REOPEN, true"})
            void updateIssueByIdIssueUpdateTest(String testName, String stateString, boolean expected) {
                Issue issue = new Issue(1, "title 1", "test 1", 11, assigneesId, labels, projectsId, 1, 1, 4);
                setUp(manager, issue);
                manager.updateIssueById(1, State.UPDATE);

                State state = StringToState(stateString);

                boolean actual = manager.updateIssueById(1, state);
                assertEquals(actual, expected);
            }

            @ParameterizedTest(name = "{index} {0}")
            @CsvSource({
              "Set State.CREATE, State.CREATE, false",
              "Set State.UPDATE, State.UPDATE, false",
              "Set State.CLOSE, State.CLOSE, false",
              "Set State.REOPEN, State.REOPEN, true"})
            void updateIssueByIdIssueClosedTest(String testName, String stateString, boolean expected) {
                Issue issue = new Issue(1, "title 1", "test 1", 11, assigneesId, labels, projectsId, 1, 1, 4);
                setUp(manager, issue);
                manager.updateIssueById(1, State.CLOSE);

                State state = StringToState(stateString);

                boolean actual = manager.updateIssueById(1, state);
                assertEquals(actual, expected);
            }

            @ParameterizedTest(name = "{index} {0}")
            @CsvSource({
              "Set State.CREATE, State.CREATE, false",
              "Set State.UPDATE, State.UPDATE, true",
              "Set State.CLOSE, State.CLOSE, true",
              "Set State.REOPEN, State.REOPEN, false"})
            void updateIssueByIdIssueReopenTest(String testName, String stateString, boolean expected) {
                Issue issue = new Issue(1, "title 1", "test 1", 11, assigneesId, labels, projectsId, 1, 1, 4);
                setUp(manager, issue);
                manager.updateIssueById(1, State.CLOSE);
                manager.updateIssueById(1, State.REOPEN);

                State state = StringToState(stateString);

                boolean actual = manager.updateIssueById(1, state);
                assertEquals(actual, expected);
            }
        }

        @Nested
        public class NotExistId{

            @Test
            void updateIssueByIdTest() {
                Issue issue = new Issue(1, "title 1", "test 1", 11, assigneesId, labels, projectsId, 1, 1, 4);
                setUp(manager, issue);

                assertThrows(NotFoundException.class, () -> manager.updateIssueById(7, State.UPDATE));
            }
        }
    }
}