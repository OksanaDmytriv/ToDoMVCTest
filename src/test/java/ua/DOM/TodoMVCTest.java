package ua.DOM;

import static com.codeborne.selenide.CollectionCondition.*;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import org.junit.Test;

import java.util.Collection;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static java.util.Arrays.asList;

/**
 * Created by dreamliner on 12/4/15.
 * comment for git
 */
public class TodoMVCTest {
    @Test
    public void testCreateTask() {

        open("https://todomvc4tasj.herokuapp.com/");

        add("1", "2", "3", "4");
        assertTasksAre("1", "2", "3", "4");

        delete("2");
        assertTasksAre("1", "3", "4");

        toggle("4");
        сlearCompleted();
        assertTasksAre("1", "3");

        toggleAll();
        сlearCompleted();
        tasks.shouldHave(empty);
    }

    ElementsCollection tasks = $$("#todo-list li");

    private void assertTasksAre(String... taskTexts) {
        for (String text : taskTexts) {
            tasks.shouldHave(exactTexts(taskTexts));
        }
    }

    private void сlearCompleted() {
        $("#clear-completed").click();
    }

    private void toggleAll() {
        $("#toggle-all").click();
    }

    private void toggle(String taskText) {
        tasks.find(exactText(taskText)).$(".toggle").click();
    }

    private void add(String... taskTexts) {
        for (String text : taskTexts) {
            $("#new-todo").setValue(text).pressEnter();
        }
    }

    private void delete(String taskText) {
        tasks.find(exactText(taskText)).hover().$(".destroy").click();
    }
}