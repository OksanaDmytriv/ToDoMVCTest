package todomvc4tasj.fullcoverage_3001;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.ArrayList;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;
import static todomvc4tasj.fullcoverage_3001.FullCoverageMVCTest.TaskType.ACTIVE;
import static todomvc4tasj.fullcoverage_3001.FullCoverageMVCTest.TaskType.COMPLETED;

public class FullCoverageMVCTest extends BaseTest {

    //ALL FILTER

    @Test
    public void testEditAtAll() {
        givenAtAll(aTask("a", COMPLETED));

        startEditing("a", "a edited").pressEnter();
        assertTasks("a edited");
        assertItemsLeft(0);
    }

    @Test
    public void testCancelEditAtAll() {
        givenAtAll(aTask("a", COMPLETED));

        startEditing("a", "a edited").pressEscape();
        assertTasks("a");
        assertItemsLeft(0);
    }

    @Test
    public void testEditAndClickOutsideAtAll() {
        givenAtAllFilter("a", "b");

        startEditing("a", "a edited");
        newTask.click();
        assertTasks("a edited", "b");
        assertItemsLeft(2);
    }

    @Test
    public void testCompleteAtAll() {
        givenAtAll(aTask("a", ACTIVE),
                aTask("b", COMPLETED));

        assertItemsLeft(1);
        toggle("a");
        assertItemsLeft(0);
    }

    @Test
    public void testClearCompletedAtAll() {
        givenAtAll(aTask("a", COMPLETED),
                aTask("b", COMPLETED));

        assertItemsLeft(0);
        сlearCompleted();
        assertEmptyVisibleTasks();
    }

    @Test
    public void testActivateAtAll() {
        givenAtAll(aTask("a", COMPLETED));

        assertItemsLeft(0);
        toggle("a");
        assertItemsLeft(1);
    }

    @Test
    public void testActivateTasksAtAll() {
        givenAtAll(aTask("a", COMPLETED),
                aTask("b", COMPLETED));

        assertItemsLeft(0);
        toggleAll();
        assertItemsLeft(2);
    }

    //ACTIVE FILTER

    @Test
    public void testAddAtActive() {
        givenAtActive(aTask("a", ACTIVE));

        assertTasks("a");
        assertItemsLeft(1);
    }

    @Test
    public void testCompeleteAtActive() {
        givenAtActive(aTask("a", ACTIVE));

        toggle("a");
        assertEmptyVisibleTasks();
        assertItemsLeft(0);
    }

    @Test
    public void testCompleteTasksAtActive() {
        givenAtActiveFilter("a", "b");

        toggleAll();
        assertEmptyVisibleTasks();
        assertItemsLeft(0);
    }

    @Test
    public void testEditAtActive() {
        givenAtActiveFilter("a", "b");

        startEditing("a", "a edited").pressEnter();
        assertTasks("a edited", "b");
        assertItemsLeft(2);
    }

    @Test
    public void testCancelEditAtActive() {
        givenAtActiveFilter("a", "b");

        startEditing("b", "b edited").pressEscape();
        assertTasks("a", "b");
        assertItemsLeft(2);
    }

    @Test
    public void testDeleteWhileEditingAtActive() {
        givenAtActive(aTask("a", ACTIVE));

        startEditing("a", " ").pressEnter();
        assertEmptyVisibleTasks();
    }

    @Test
    public void testClearCompletedAtActive() {
        givenAtActive(aTask("a", COMPLETED));

        сlearCompleted();
        assertEmptyVisibleTasks();
    }

    @Test
    public void testDeleteAtActive() {
        givenAtActive(aTask("a", ACTIVE));

        delete("a");
        assertEmptyVisibleTasks();
    }

    //COMPLETED FILTER

    @Test
    public void testEditAtCompleted() {
        givenAtCompleted(aTask("a", COMPLETED),
                aTask("b", COMPLETED));

        startEditing("a", "a edited").pressEnter();
        assertTasks("a edited", "b");
        assertItemsLeft(0);
    }

    @Test
    public void testCancelEditAtCompleted() {
        givenAtCompleted(aTask("a", COMPLETED));

        startEditing("a", "a edited").pressEscape();
        assertTasks("a");
        assertItemsLeft(0);
    }

    @Test
    public void testActivateAtCompleted() {
        givenAtCompleted(aTask("a", COMPLETED),
                aTask("b", COMPLETED));

        toggleAll();
        assertEmptyVisibleTasks();
        assertItemsLeft(2);
    }

    @Test
    public void testDeleteAtCompleted() {
        givenAtCompleted(aTask("a", COMPLETED),
                aTask("b", COMPLETED));

        delete("a");
        assertTasks("b");
        assertItemsLeft(0);
    }

    @Test
    public void testClearCompletedAtCompleted() {
        givenAtCompleted(aTask("a", COMPLETED),
                aTask("b", COMPLETED));

        assertItemsLeft(0);
        сlearCompleted();
        assertEmptyVisibleTasks();
    }

    // COMMON TEST FLOW

    @Test
    public void testTasksCommonFlow() {

        givenAtAll();
        add("a");
        assertVisibleTasks("a");
        assertItemsLeft(1);
        toggleAll();

        filterActive();
        assertEmptyVisibleTasks();

        filterCompleted();
        assertVisibleTasks("a");

        //activate task
        toggle("a");
        assertEmptyVisibleTasks();
        assertItemsLeft(1);

        filterAll();
        assertTasks("a");

        filterCompleted();
        assertEmptyVisibleTasks();

        filterActive();
        assertTasks("a");

        filterAll();
        delete("a");
        assertTasksEmpty();
    }

    ElementsCollection tasks = $$("#todo-list li");

    SelenideElement newTask = $("#new-todo");

    @Step
    private void add(String... taskTexts) {
        for (String text : taskTexts) {
            newTask.setValue(text).pressEnter();
        }
    }

    @Step
    private void assertItemsLeft(int number) {
        $("#todo-count>strong").shouldHave(exactText(Integer.toString(number)));
    }

    @Step
    private void сlearCompleted() {
        $("#clear-completed").click();
        $("#clear-completed").shouldBe(hidden);
    }

    @Step
    private void toggle(String taskText) {
        tasks.find(exactText(taskText)).$(".toggle").click();
    }

    @Step
    private void toggleAll() {
        $("#toggle-all").click();
    }

    @Step
    private SelenideElement startEditing(String oldText, String newText) {
        tasks.find(exactText(oldText)).doubleClick();
        return tasks.find(cssClass("editing")).$(".edit").setValue(newText);
    }

    @Step
    private void filterAll() {
        $("[href='#/']").click();
    }

    @Step
    private void filterActive() {
        $("[href='#/active']").click();
    }

    @Step
    private void filterCompleted() {
        $("[href='#/completed']").click();
    }

    @Step
    private void delete(String taskText) {
        tasks.find(exactText(taskText)).hover().$(".destroy").click();
    }

    @Step
    private void assertTasks(String... taskTexts) {
        tasks.shouldHave(exactTexts(taskTexts));
    }

    @Step
    private void assertTasksEmpty() {
        tasks.shouldBe(empty);
    }

    @Step
    private void assertVisibleTasks(String... taskTexts) {
        tasks.filter(visible).shouldHave(exactTexts((taskTexts)));
    }

    @Step
    private void assertEmptyVisibleTasks() {
        tasks.filter(visible).shouldBe(empty);
    }

    public enum TaskType {
        ACTIVE, COMPLETED;
    }

    public class Task {
        String text;
        TaskType type;

        public Task(String text, TaskType type) {
            this.text = text;
            this.type = type;
        }
    }

    private Task aTask(String text, TaskType type) {
        Task task = new Task(text, type);
        return task;
    }

    @Step
    private String toJSON(Task task) {
        return "{\"completed\":" + ((ACTIVE == task.type) ? false : true) + ", \"title\":\"" + task.text + "\"},";
    }

    @Step
    private void givenAtAll(Task... tasks) {
        ensureOpenedToDoMVC();
        String js = "localStorage.setItem('todos-troopjs', '[";
        if (tasks.length > 0) {
            for (Task task : tasks) {
                js += toJSON(task);
            }
        }
        if (tasks.length == 0) {
            js += " ";
        }

        js = js.substring(0, (js.length() - 1)) + "]');";
        executeJavaScript(js);
        refresh();
    }

    public void ensureOpenedToDoMVC() {
        if (!url().equals("https://todomvc4tasj.herokuapp.com/"))
            open("https://todomvc4tasj.herokuapp.com/");
    }

    @Step
    private void givenAtActive(Task... task) {
        givenAtAll(task);
        filterActive();
    }

    @Step
    private void givenAtCompleted(Task... task) {
        givenAtAll(task);
        filterCompleted();
    }

    @Step
    private void givenAtAllFilter(String... taskTexts) {
        ArrayList<Task> tasks = new ArrayList<Task>();
        for (String text : taskTexts) {
            tasks.add(aTask(text, ACTIVE));
        }
        Task[] tasksArray = tasks.toArray(new Task[tasks.size()]);
        givenAtAll(tasksArray);
    }

    @Step
    private void givenAtActiveFilter(String... taskTexts) {
        givenAtAllFilter(taskTexts);
        filterActive();
    }

}


