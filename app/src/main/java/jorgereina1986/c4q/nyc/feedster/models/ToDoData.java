package jorgereina1986.c4q.nyc.feedster.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c4q-Allison on 7/2/15.
 */
public class ToDoData extends CardData {

    List<String> toDoList = new ArrayList<>();


    public List<String> getToDoList() {
        return toDoList;
    }

    public void setToDoList(List<String> toDoList) {
        this.toDoList = toDoList;
    }

}
