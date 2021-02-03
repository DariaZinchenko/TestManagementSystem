package ru.netology.domain;

import lombok.*;

import java.util.*;

@Data
public class Issue implements Comparable<Issue>{
    private int id;
    private String title;
    private String text;
    private int authorId;
    @Setter(AccessLevel.PRIVATE)
    private ArrayList<State> stateHistory;
    @Setter(AccessLevel.PRIVATE)
    private ArrayList<GregorianCalendar> stateHistoryTime;
    private Set<Integer> assigneesId;
    private Set<String> labels;
    private Set<Integer> projectsId;
    private int milestoneId;
    private int pullRequestId;
    private int countOfComments;


    public Issue() {
        this.stateHistory = new ArrayList<>();
        this.stateHistoryTime = new ArrayList<>();

        this.stateHistory.add(State.CREATE);
        this.stateHistoryTime.add(new GregorianCalendar());
    }

    public Issue(int id, String title, String text, int authorId, Set<Integer> assigneesId, Set<String> labels, Set<Integer> projectsId, int milestoneId, int pullRequestId, int countOfComments) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.authorId = authorId;
        this.stateHistory = new ArrayList<>();
        this.stateHistoryTime = new ArrayList<>();
        this.assigneesId = assigneesId;
        this.labels = labels;
        this.projectsId = projectsId;
        this.milestoneId = milestoneId;
        this.pullRequestId = pullRequestId;
        this.countOfComments = countOfComments;

        this.stateHistory.add(State.CREATE);
        this.stateHistoryTime.add(new GregorianCalendar());
    }

    public boolean isOpen() {
        int closeIndex = stateHistory.lastIndexOf(State.CLOSE);
        int reopenIndex = stateHistory.lastIndexOf(State.REOPEN);

        if (reopenIndex >= closeIndex){
            return true;
        }
        return false;
    }

    public State getLastState(){
        return stateHistory.get(stateHistory.size() - 1);
    }

    public boolean updateState(State state, GregorianCalendar time) {
        if (state.equals(State.CREATE)) {
            System.out.println("You can't create created issue.");
            return false;
        }
        if (state.equals(State.UPDATE)) {
            if (getLastState().equals(State.CLOSE)) {
                System.out.println("You can't update closed issue.");
                return false;
            }
        }
        if (state.equals(State.CLOSE)) {
            if (getLastState().equals(State.CLOSE)) {
                System.out.println("Issue is already closed.");
                return false;
            }
        }
        if (state.equals(State.REOPEN)) {
            if (getLastState().equals(State.REOPEN) || getLastState().equals(State.CREATE)) {
                System.out.println("Issue is already opened.");
                return false;
            }
        }
        stateHistory.add(state);
        stateHistoryTime.add(time);
        return true;
    }

    public void setStateHistoryTime(int id, int year, int month, int date, int hour, int minute) {
        stateHistoryTime.get(id).set(year, month, date, hour, minute);
    }

    @Override
    public int compareTo(Issue o) {
        return id - o.getId();
    }
}
