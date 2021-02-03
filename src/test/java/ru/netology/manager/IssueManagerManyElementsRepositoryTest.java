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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class IssueManagerManyElementsRepositoryTest {

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

    private void setCreateTime() {
        first.setStateHistoryTime(0,2020, 12 , 25, 13, 42);
        second.setStateHistoryTime(0, 2021, 1 , 11, 16, 23);
        third.setStateHistoryTime(0, 2020, 10 , 4, 18, 26);
        fourth.setStateHistoryTime(0, 2019, 11 , 30, 9, 15);
        fifth.setStateHistoryTime(0, 2020, 10 , 12, 11, 52);
    }

    private void setUpdateTime() {
        first.updateState(State.CLOSE, new GregorianCalendar(2020, 12 , 27, 11, 30));
        third.updateState(State.UPDATE, new GregorianCalendar(2020, 10 , 25, 8, 0));
        fourth.updateState(State.CLOSE, new GregorianCalendar(2019, 12 , 28, 10, 0));
        fifth.updateState(State.CLOSE, new GregorianCalendar(2020, 10 , 13, 14, 0));
        fifth.updateState(State.REOPEN, new GregorianCalendar(2020, 10 , 13, 15, 0));
    }

    HashSet<Integer> assigneesId1 = new HashSet<>();
    HashSet<Integer> assigneesId2 = new HashSet<>();
    HashSet<String> labels1 = new HashSet<>();
    HashSet<String> labels2 = new HashSet<>();
    HashSet<String> labels3 = new HashSet<>();
    HashSet<Integer> projectsId = new HashSet<>();

    private Issue first = new Issue(1, "title 1", "test 1", 11, assigneesId1, labels3, projectsId, 1, 1, 4);
    private Issue second = new Issue(2, "title 2", "test 2", 12, assigneesId1, labels1, projectsId, 2, 1, 38);
    private Issue third = new Issue(3, "title 3", "test 3", 11, assigneesId1, labels2, projectsId, 3, 1, 15);
    private Issue fourth = new Issue(4, "title 4", "test 4", 13, assigneesId2, labels1, projectsId, 1, 1, 34);
    private Issue fifth = new Issue(5, "title 5", "test 5", 11, assigneesId1, labels2, projectsId, 4, 1, 23);

    private void setUp (IssueManager manager, Issue... issues){

        setAssignees (assigneesId1, 24, 25, 26);
        setAssignees (assigneesId2, 24, 25, 27);
        setLabels(labels1, "Label 1", "Label 2", "Label 3");
        setLabels(labels2, "Label 2", "Label 4", "Label 5");
        setLabels(labels3, "Label 5", "Label 3", "Label 7");
        setProjects(projectsId);
        setCreateTime();
        setUpdateTime();

        for (Issue issue: issues) {
            manager.add(issue);
        }
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
                setUp(manager, first, fourth);
                ArrayList<Issue> actual = manager.findOpenIssues();
                ArrayList<Issue> expected = new ArrayList<>();

                assertEquals(actual, expected);
            }

            @Test
            void findClosedIssuesTest() {
                setUp(manager, second, third, fifth);
                ArrayList<Issue> actual = manager.findClosedIssues();
                ArrayList<Issue> expected = new ArrayList<>();

                assertEquals(actual, expected);
            }

            @Test
            void filterByAuthorTest() {
                setUp(manager, first, second, third, fourth, fifth);
                ArrayList<Issue> actual = manager.filterByAuthor(14);
                ArrayList<Issue> expected = new ArrayList<>();

                assertEquals(actual, expected);
            }

            @Test
            void filterByLabelTest() {
                setUp(manager, first, second, third, fourth, fifth);
                ArrayList<Issue> actual = manager.filterByLabel("Label 10");
                ArrayList<Issue> expected = new ArrayList<>();

                assertEquals(actual, expected);
            }

            @Test
            void filterByAssigneeTest() {
                setUp(manager, first, second, third, fourth, fifth);
                ArrayList<Issue> actual = manager.filterByAssignee(30);
                ArrayList<Issue> expected = new ArrayList<>();

                assertEquals(actual, expected);
            }
        }

        @Nested
        public class SingleElementResult{
            @Test
            void findOpenIssuesTest() {
                setUp(manager, first, third, fourth);
                ArrayList<Issue> actual = manager.findOpenIssues();
                ArrayList<Issue> expected = new ArrayList<>();
                expected.add(third);

                assertEquals(actual, expected);
            }

            @Test
            void findClosedIssuesTest() {
                setUp(manager, first, second, third, fifth);
                ArrayList<Issue> actual = manager.findClosedIssues();
                ArrayList<Issue> expected = new ArrayList<>();
                expected.add(first);

                assertEquals(actual, expected);
            }

            @Test
            void filterByAuthorTest() {
                setUp(manager, first, second, third, fourth, fifth);
                ArrayList<Issue> actual = manager.filterByAuthor(12);
                ArrayList<Issue> expected = new ArrayList<>();
                expected.add(second);

                assertEquals(actual, expected);
            }

            @Test
            void filterByLabelTest() {
                setUp(manager, first, second, third, fourth, fifth);
                ArrayList<Issue> actual = manager.filterByLabel("Label 7");
                ArrayList<Issue> expected = new ArrayList<>();
                expected.add(first);

                assertEquals(actual, expected);
            }

            @Test
            void filterByAssigneeTest() {
                setUp(manager, first, second, third, fourth, fifth);
                ArrayList<Issue> actual = manager.filterByAssignee(27);
                ArrayList<Issue> expected = new ArrayList<>();
                expected.add(fourth);

                assertEquals(actual, expected);
            }
        }

        @Nested
        public class MultipleElementsResult{

            @Test
            void findOpenIssuesTest() {
                setUp(manager, first, second, third, fourth, fifth);
                ArrayList<Issue> actual = manager.findOpenIssues();
                ArrayList<Issue> expected = new ArrayList<>();
                expected.add(second);
                expected.add(third);
                expected.add(fifth);

                assertEquals(actual, expected);
            }

            @Test
            void findClosedIssuesTest() {
                setUp(manager, first, second, third, fourth, fifth);
                ArrayList<Issue> actual = manager.findClosedIssues();
                ArrayList<Issue> expected = new ArrayList<>();
                expected.add(first);
                expected.add(fourth);

                assertEquals(actual, expected);
            }

            @Test
            void filterByAuthorTest() {
                setUp(manager, first, second, third, fourth, fifth);
                ArrayList<Issue> actual = manager.filterByAuthor(11);
                ArrayList<Issue> expected = new ArrayList<>();
                expected.add(first);
                expected.add(third);
                expected.add(fifth);

                assertEquals(actual, expected);
            }

            @Test
            void filterByLabelTest() {
                setUp(manager, first, second, third, fourth, fifth);
                ArrayList<Issue> actual = manager.filterByLabel("Label 3");
                ArrayList<Issue> expected = new ArrayList<>();
                expected.add(first);
                expected.add(second);
                expected.add(fourth);

                assertEquals(actual, expected);
            }

            @Test
            void filterByAssigneeTest() {
                setUp(manager, first, second, third, fourth, fifth);
                ArrayList<Issue> actual = manager.filterByAssignee(26);
                ArrayList<Issue> expected = new ArrayList<>();
                expected.add(first);
                expected.add(second);
                expected.add(third);
                expected.add(fifth);

                assertEquals(actual, expected);
            }
        }

    }

    @Nested
    public class SortTest{
        @Test
        void sortByCreatedTimeTest() {
            setUp(manager, first, second, third, fourth, fifth);
            IssueCreatedTimeComparator comparator = new IssueCreatedTimeComparator();
            ArrayList<Issue> actual = manager.sort(comparator);
            ArrayList<Issue> expected = new ArrayList<>();
            expected.add(fourth);
            expected.add(third);
            expected.add(fifth);
            expected.add(first);
            expected.add(second);

            assertEquals(actual, expected);
        }

        @Test
        void sortByUpdatedTimeTest() {
            setUp(manager, first, second, third, fourth, fifth);
            IssueUpdatedTimeComparator comparator = new IssueUpdatedTimeComparator();

            ArrayList<Issue> actual = manager.sort(comparator);

            ArrayList<Issue> expected = new ArrayList<>();
            expected.add(fourth);
            expected.add(fifth);
            expected.add(third);
            expected.add(first);
            expected.add(second);

            assertEquals(actual, expected);
        }

        @Test
        void sortByCountCommentsTest() {
            setUp(manager, first, second, third, fourth, fifth);
            IssueCountCommentsComparator comparator = new IssueCountCommentsComparator();
            ArrayList<Issue> actual = manager.sort(comparator);
            ArrayList<Issue> expected = new ArrayList<>();
            expected.add(first);
            expected.add(third);
            expected.add(fifth);
            expected.add(fourth);
            expected.add(second);

            assertEquals(actual, expected);
        }
    }

    @Nested
    public class AddTest{
        @Test
        void addIssueTest(){
            setUp(manager, first, second, third, fourth);

            manager.add(fifth);

            ArrayList<Issue> actual = manager.findAll();
            ArrayList<Issue> expected = new ArrayList<>();

            expected.add(first);
            expected.add(second);
            expected.add(third);
            expected.add(fourth);
            expected.add(fifth);

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

                setUp(manager, first, second, third, fourth, fifth);
                State state = StringToState(stateString);
                boolean actual = manager.updateIssueById(2, state);
                assertEquals(actual, expected);
            }

            @ParameterizedTest(name = "{index} {0}")
            @CsvSource({
              "Set State.CREATE, State.CREATE, false",
              "Set State.UPDATE, State.UPDATE, true",
              "Set State.CLOSE, State.CLOSE, true",
              "Set State.REOPEN, State.REOPEN, true"})
            void updateIssueByIdIssueUpdateTest(String testName, String stateString, boolean expected) {

                setUp(manager, first, second, third, fourth, fifth);
                State state = StringToState(stateString);

                boolean actual = manager.updateIssueById(3, state);
                assertEquals(actual, expected);
            }

            @ParameterizedTest(name = "{index} {0}")
            @CsvSource({
              "Set State.CREATE, State.CREATE, false",
              "Set State.UPDATE, State.UPDATE, false",
              "Set State.CLOSE, State.CLOSE, false",
              "Set State.REOPEN, State.REOPEN, true"})
            void updateIssueByIdIssueClosedTest(String testName, String stateString, boolean expected) {

                setUp(manager, first, second, third, fourth, fifth);
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

                setUp(manager, first, second, third, fourth, fifth);
                State state = StringToState(stateString);

                boolean actual = manager.updateIssueById(5, state);
                assertEquals(actual, expected);
            }
        }

        @Nested
        public class NotExistId{

            @Test
            void updateIssueByIdTest() {

                setUp(manager, first, second, third, fourth, fifth);

                assertThrows(NotFoundException.class, () -> manager.updateIssueById(7, State.UPDATE));
            }
        }
    }
}