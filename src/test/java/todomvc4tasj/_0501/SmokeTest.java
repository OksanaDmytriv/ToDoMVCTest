package todomvc4tasj._0501;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.Test;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class SmokeTest {
    @Test
    public void testCreateTask() {
        open("https://todomvc4tasj.herokuapp.com/");

        add("a");
        startEditing("a", "a edited").pressEnter();
        toggle("a edited");
        filterCompleted();

        //activate task
        toggle("a edited");

        filterActive();
        startEditing("a edited", "a").pressEscape();
        delete("a edited");
        zeroTasks();

        add("b", "c");
        filterAll();
        assertItemsLeft("2");
        toggle("c");
        activeTasks("b");
        completedTasks("c");
        editTaskClickOutside("b", "b edited");
        filterActive();

        //delete task while removing all text
        startEditing("b edited", "").pressEnter();

        filterCompleted();
        сlearCompleted();
        zeroTasks();
    }

    ElementsCollection tasks = $$("#todo-list li");

    SelenideElement newTask = $("#new-todo");

    public void add(String... taskTexts) {
        for (String text : taskTexts) {
            newTask.setValue(text).pressEnter();
        }
    }

    private void assertItemsLeft(String taskCounter) {
        $("#todo-count").shouldHave(text(taskCounter));
    }

    private void сlearCompleted() {
        $("#clear-completed").click();
    }

    private void toggle(String taskText) {
        tasks.find(exactText(taskText)).$(".toggle").click();
    }

    private SelenideElement startEditing(String oldText, String newText) {
        tasks.find(exactText(oldText)).doubleClick();
        return $("#todo-list li .edit").setValue(newText);
    }

    private void editTaskClickOutside(String taskText, String newText) {
        tasks.find(exactText(taskText)).doubleClick();
        $("#todo-list li .edit").setValue(newText);
        newTask.click();
    }

    private void filterAll() {
        open("https://todomvc4tasj.herokuapp.com/#/");
    }

    private void filterActive() {
        open("https://todomvc4tasj.herokuapp.com/#/active");
    }

    private void filterCompleted() {
        open("https://todomvc4tasj.herokuapp.com/#/completed");
    }

    private void delete(String taskText) {
        tasks.find(exactText(taskText)).hover().$(".destroy").click();
    }

    private void zeroTasks() {
        tasks.shouldHave(empty);
    }

    private void activeTasks(String... taskTexts) {
        $$("#todo-list .active").shouldHave(exactTexts(taskTexts));
    }

    private void completedTasks(String... taskTexts) {
        $$("#todo-list .completed").shouldHave(exactTexts(taskTexts));
    }
}



