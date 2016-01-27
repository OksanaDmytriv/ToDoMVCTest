package ua.DOM;

import static com.codeborne.selenide.CollectionCondition.*;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import org.junit.Test;

import java.util.Collection;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static java.util.Arrays.asList;
import static java.util.Arrays.parallelSetAll;

/**
 * Created by dreamliner on 12/27/15.
 */
public class TodoMVCTest {
    @Test
    public void testCreateTask() {

        open("https://todomvc4tasj.herokuapp.com/");

        //create tasks from All tab
        add("1", "2", "3");

        //create, edit tasks from Active tab
        openActiveTab();
        add("4", "5", "6");
        assertTaskCounter(6);
        editTask("1", "1a");
        editTaskClickOutside("2", "2b");

        editTask("3", "");
        cancelEditTaskWithESC("4", "4c");
        assertTasksAre("1a", "2b", "4", "5", "6");

        //delete from All tab
        openAllTab();
        delete("5");
        assertTaskCounter(4);

        //complete from Active tab
        openActiveTab();
        toggle("4");
        toggle("6");

        //delete, activate 1 task from Completed tab
        openCompletedTab();
        delete("6");
        toggle("4");
        assertTaskCounter(3);

        //complete all at All tab
        openAllTab();
        toggleAll();

        //activate all from Completed tab
        openCompletedTab();
        toggleAll();

        //complete all at Active tab
        openActiveTab();
        toggleAll();
        assertTaskCounter(0);

        //complete all from Completed tab
        openCompletedTab();
        сlearCompleted();
        tasks.shouldHave(empty);
    }

    ElementsCollection tasks = $$("#todo-list li");

    private void add(String... taskTexts) {
        for (String text : taskTexts) {
            $("#new-todo").setValue(text).pressEnter();
        }
    }

    private void assertTasksAre(String... taskTexts) {
        for (String text : taskTexts) {
            tasks.shouldHave(exactTexts(taskTexts));
        }
    }

    private void assertTaskCounter(int taskCounter) {
        if (taskCounter == 0 || taskCounter > 1) {
            $("#todo-count").shouldHave(text((taskCounter + " items left")));
        }
        if (taskCounter == 1) {
            $("#todo-count").shouldHave(text((taskCounter + " item left")));
        }
    }

    private void сlearCompleted() {
        $("#clear-completed").click();
    }

    private void toggle(String taskText) {
        tasks.find(exactText(taskText)).$(".toggle").click();
    }

    private void toggleAll() {
        $("#toggle-all").click();
    }

    private void editTask(String taskText, String newTask) {
        tasks.find(exactText(taskText)).doubleClick();
        $(".editing .edit").setValue(newTask).pressEnter();
    }

    private void cancelEditTaskWithESC(String taskText, String newTask) {
        $$("#todo-list li").find(exactText(taskText)).doubleClick();
        $(".editing .edit").setValue(newTask).pressEscape();
    }

    private void editTaskClickOutside(String taskText, String newTask) {
        $$("#todo-list li").find(exactText(taskText)).doubleClick();
        $(".editing .edit").setValue(newTask).pressEnter();
        $("#new-todo").click();
    }

    private void openAllTab() {
        $("#filters li:nth-child(1)").click();
    }

    private void openActiveTab() {
        $("#filters li:nth-child(2)").click();
    }

    private void openCompletedTab() {
        $("#filters li:nth-child(3)").click();
    }

    private void delete(String taskText) {
        tasks.find(exactText(taskText)).hover().$(".destroy").click();
    }
}