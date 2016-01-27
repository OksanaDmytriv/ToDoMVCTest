package todomvc4tasj._1101;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

/**
 * Created by dreamliner on 1/11/16.
 */
public class TodoMVCTest {

    @Before
    public void openApp() {
        open("https://todomvc4tasj.herokuapp.com/");
    }

    @After
    public void clearData() {
        executeJavaScript("localStorage.clear()");
    }

    @Test
    public void testAddTasks() {
        add("a", "b", "c", "d");
        assertTasks("a", "b", "c", "d");
    }

    @Test
    public void cancelEdit() {
        add("a");
        startEditing("a", "a edited").pressEscape();
        assertTasks("a");
    }

    @Test
    public void editTaskAndClickOutside() {
        add("a");
        startEditing("a", "a edited");
        newTask.click();
        assertTasks("a edited");
    }

    @Test
    public void editTasks() {
        add("a");
        startEditing("a", "a edited").pressEnter();
        assertTasks("a edited");
    }

    @Test
    public void toggleAllTasks() {
        add("a", "b", "c", "d");
        toggleAll();
        assertEmptyVisibleTasks();
    }

    @Test
    public void testTasksCommonFlow() {

        add("a");
        toggle("a");

        filterActive();
        assertEmptyVisibleTasks();

        add("b");
        toggle("b");

        filterCompleted();
        assertTasks("a", "b");

        //activate task
        toggle("a");
        assertItemsLeft(1);

        сlearCompleted();
        assertEmptyVisibleTasks();

        filterActive();
        //toggleAll();
        assertEmptyVisibleTasks();

        filterAll();
        delete("a");
        assertTasksEmpty();
    }


    ElementsCollection tasks = $$("#todo-list li");

    SelenideElement newTask = $("#new-todo");

    public void add(String... taskTexts) {
        for (String text : taskTexts) {
            newTask.setValue(text).pressEnter();
        }
    }

    private void assertItemsLeft(int number) {
        $("#todo-count>strong").shouldHave(exactText(Integer.toString(number)));
    }

    private void сlearCompleted() {
        $("#clear-completed").click();
        $("#clear-completed").shouldBe(hidden);
    }

    private void toggle(String taskText) {
        tasks.find(exactText(taskText)).$(".toggle").click();
    }

    private void toggleAll() {
        $("#toggle-all").click();
    }

    private SelenideElement startEditing(String oldText, String newText) {
        tasks.find(exactText(oldText)).doubleClick();
        return tasks.find(cssClass("editing")).$(".edit").setValue(newText);
    }

    private void filterAll() {
        $("[href='#/']").click();
    }

    private void filterActive() {
        $("[href='#/active']").click();
    }

    private void filterCompleted() {
        $("[href='#/completed']").click();
    }

    private void delete(String taskText) {
        tasks.find(exactText(taskText)).hover().$(".destroy").click();
    }

    private void assertTasks(String... taskTexts) {
        tasks.shouldHave(exactTexts(taskTexts));
    }

    private void assertTasksEmpty() {
        tasks.shouldBe(empty);
    }

    private void assertVisibleTasks(String... taskTexts) {
        tasks.filter(visible).shouldHave(exactTexts((taskTexts)));
    }

    private void assertEmptyVisibleTasks() {
        tasks.filter(visible).shouldBe(empty);
    }
}

