package keqing;

import keqing.exceptions.IllegalInputException;
import keqing.tasks.Deadline;
import keqing.tasks.Event;
import keqing.tasks.Task;
import keqing.tasks.ToDo;

import java.util.ArrayList;

import static keqing.Keqing.LINE;
import static keqing.KeqingUI.echoAdd;
import static keqing.KeqingUI.echoDelete;
import static keqing.KeqingParser.isNumeric;
import static keqing.tasks.Task.getTaskCount;

public class KeqingArrayList {
    public static ArrayList<Task> tasks = new ArrayList<Task>();

    public static void printTaskList() {
        System.out.println(LINE);
        if (getTaskCount() == 0) {
            System.out.println("The list is empty...!");
        }
        for (int i = 0; i < getTaskCount(); i++) {
            System.out.print((i + 1) + ".");
            System.out.println(tasks.get(i).toString());
        }
        System.out.println(LINE);
    }

    /**
     * to mark a task as done or unfinished
     *
     * @param currentID the ID of the task that is currently on
     * @param isDone the checker for the task status
     */
    public static void markTask(int currentID, boolean isDone) {
        System.out.println(LINE);
        if (currentID < 0 || currentID >= getTaskCount()) {
            System.out.println("Cannot find this task!");
        }
        else if (isDone) {
            tasks.get(currentID).setDone();
            System.out.println("Nice! I've marked this task as done:");
            System.out.println("   " + tasks.get(currentID).toString());
        }
        else {
            tasks.get(currentID).setUndone();
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println("   " + tasks.get(currentID).toString());
        }
        System.out.println(LINE);
    }

    /**
     * Read the toTo tasks using respective format
     *
     * @param content Content from the user input
     * @throws IllegalInputException
     */
    public static void readToDo(String content) throws IllegalInputException {
        if (content.equals("todo")) {
            throw new IllegalInputException("Keqing doesn't understand what you actually want to do...");
        }
        else {
            ToDo toDoTask = new ToDo(content);
            tasks.add(toDoTask);
            echoAdd();
        }
    }

    /**
     * Read the deadline tasks using respective format
     *
     * @param content Content from the user input
     * @throws IllegalInputException
     */
    public static void readDeadline(String content) throws IllegalInputException {
        if (content.contains("/by")) {
            int indexOfBy = content.indexOf("/by");
            if (indexOfBy + 3 < content.length()) {
                String description = content.substring(0, indexOfBy).trim();
                String by = content.substring(indexOfBy + 3).trim();
                Deadline deadlineTask = new Deadline(description, by);
                tasks.add(deadlineTask);
                echoAdd();
            }
            else {
                throw new IllegalInputException("Keqing doesn't think your input makes sense...");
            }
        }
        else {
            throw new IllegalInputException("Please check if you have typed in a valid deadline.");
        }
    }

    /**
     * Read the event tasks using respective format
     *
     * @param content Content from the user input
     * @throws IllegalInputException
     */
    public static void readEvent(String content) throws IllegalInputException {
        if (content.contains("/from") && content.contains("/to")) {
            int indexOfFrom = content.indexOf("/from");
            int indexOfTo = content.indexOf("/to");
            if (indexOfFrom < indexOfTo) {
                String description = content.substring(0, indexOfFrom).trim();
                String from = content.substring(indexOfFrom + 5, indexOfTo).trim();
                String to = content.substring(indexOfTo + 3).trim();
                Event eventTask = new Event(description, from, to);
                tasks.add(eventTask);
                echoAdd();
            }
            else {
                throw new IllegalInputException("Keqing doens't think your input makes sense...");
            }
        }
        else {
            throw new IllegalInputException("Please check if you have typed in the event duration in a valid form...");
        }
    }

    /**
     * 
     * @param content
     * @throws IllegalInputException
     */
    public static void deleteTask(String content) throws IllegalInputException {
        if (content.equals("all")) {
            for (int i = 0; i < tasks.size(); i++) {
                tasks.remove(i);
            }
        }
        if (isNumeric(content)) {
            int index = Integer.parseInt(content) - 1;    //switch to 0-based.
            if (index < getTaskCount()) {
                echoDelete(index);
                tasks.remove(index);
                Task.setTaskCount(getTaskCount() - 1);
            }
            else {
                throw new IllegalInputException("It's out of bound!!!");
            }
        }
    }
}