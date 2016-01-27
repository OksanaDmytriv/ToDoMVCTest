package todomvc4tasj.fullcoverage1801;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.google.gson.Gson;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Step;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static todomvc4tasj.fullcoverage1801.ToDoTestMVCTest.TaskType.*;

public class ToDoTestMVCTest extends AtTodoMVCPageWithClearedDataAfterEachTest {

    //All filter: edit, toggle, clear completed tasks
    @Test
    public void testEditAtAllFilter() {
        givenTasksAtAllFilter(new Task("a", ACTIVE));

        startEditing("a", "a edited").pressEnter();
        assertTasks("a edited");
        assertItemsLeft(1);
    }

    @Test
    public void testCancelEditAtAllFilter() {
        givenTasksAtAllFilter(new Task("a", ACTIVE));

        startEditing("a", "a edited").pressEscape();
        assertTasks("a");
        assertItemsLeft(1);
    }

    @Test
    public void testEditAndClickOutsideAtAllFilter() {
        givenTasksAtAllFilter(new Task("a", ACTIVE));

        startEditing("a", "a edited");
        newTask.click();
        assertTasks("a edited");
        assertItemsLeft(1);
    }

    @Test
    public void testDeleteWhileEditingAtAllFilter() {
        givenTasksAtAllFilter(new Task("a", ACTIVE),
                new Task("b", ACTIVE));

        startEditing("a", " ").pressEnter();
        assertTasks("b");
        assertItemsLeft(1);
    }

    @Test
    public void testToggleAtAllFilter() {
        givenTasksAtAllFilter(new Task("a", ACTIVE),
                new Task("b", COMPLETED));

        assertItemsLeft(1);
        toggleAll();
        assertItemsLeft(0);
    }

    @Test
    public void testClearCompletedAtAllFilter() {
        givenTasksAtAllFilter(new Task("a", ACTIVE),
                new Task("b", COMPLETED));

        assertItemsLeft(1);
        toggle("a");
        сlearCompleted();
        assertEmptyVisibleTasks();
    }

    //Active filter: add, toggle&toggle all, edit, clear completed, delete tasks
    @Test
    public void testAddAtActiveFilter() {
        givenTasksAtActiveFilter(new Task("a", ACTIVE));

        assertTasks("a");
        assertItemsLeft(1);
    }

    @Test
    public void testToogleAtActiveFilter() {
        givenTasksAtActiveFilter(new Task("a", ACTIVE));

        toggle("a");
        assertEmptyVisibleTasks();
        assertItemsLeft(0);
    }

    @Test
    public void toggleAllAtActiveFilter() {
        givenTasksAtActiveFilter(new Task("a", ACTIVE),
                new Task("b", ACTIVE));

        toggleAll();
        assertEmptyVisibleTasks();
        assertItemsLeft(0);
    }

    @Test
    public void testEditAtActiveFilter() {
        givenTasksAtActiveFilter(new Task("a", ACTIVE),
                new Task("b", ACTIVE));

        startEditing("a", "a edited").pressEnter();
        assertTasks("a edited", "b");
        assertItemsLeft(2);
    }

    @Test
    public void testCancelEditAtActiveFilter() {
        givenTasksAtActiveFilter(new Task("a", ACTIVE));

        startEditing("a", "a edited").pressEscape();
        assertTasks("a");
        assertItemsLeft(1);
    }

    @Test
    public void testEditAndClickOutsideAtActiveFilter() {
        givenTasksAtActiveFilter(new Task("a", ACTIVE));

        startEditing("a", "a edited");
        newTask.click();
        assertTasks("a edited");
        assertItemsLeft(1);
    }

    @Test
    public void testDeleteWhileEditingAtActiveFilter() {
        givenTasksAtActiveFilter(new Task("a", ACTIVE));

        startEditing("a", " ").pressEnter();
        assertEmptyVisibleTasks();
    }

    @Test
    public void testClearCompletedAtActiveFilter() {
        givenTasksAtActiveFilter(new Task("a", ACTIVE));

        toggle("a");
        assertItemsLeft(0);
        сlearCompleted();
        assertEmptyVisibleTasks();
    }

    //@Test
    public void testDeleteAtActiveFilter() {
        givenTasksAtActiveFilter(new Task("a", ACTIVE),
                new Task("b", ACTIVE));

        delete("a");
        assertTasks("b");
        assertItemsLeft(1);
    }

    //Completed filter: edit,activate all, delete tasks, clear completed
    @Test
    public void testEditAtCompletedFilter() {
        givenTasksAtCompletedFilter(new Task("a", COMPLETED),
                new Task("b", COMPLETED));

        startEditing("a", "a edited").pressEnter();
        assertTasks("a edited", "b");
        assertItemsLeft(0);
    }

    @Test
    public void testCancelEditAtCompletedFilter() {
        givenTasksAtCompletedFilter(new Task("a", COMPLETED));

        startEditing("a", "a edited").pressEscape();
        assertTasks("a");
        assertItemsLeft(0);
    }

    @Test
    public void testActivateAllOnCompletedFilter() {
        givenTasksAtCompletedFilter(new Task("a", COMPLETED),
                new Task("b", COMPLETED));

        toggleAll();
        assertEmptyVisibleTasks();
        assertItemsLeft(2);
    }

    @Test
    public void testDeleteAtCompletedFilter() {
        givenTasksAtCompletedFilter(new Task("a", COMPLETED),
                new Task("b", COMPLETED));

        delete("a");
        assertTasks("b");
        assertItemsLeft(0);
    }

    @Test
    public void testClearCompletedAtCompletedFilter() {
        givenTasksAtCompletedFilter(new Task("a", COMPLETED));

        assertItemsLeft(0);
        сlearCompleted();
        assertEmptyVisibleTasks();
    }

    @Test
    public void testTasksCommonFlow() {

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
        String taskText;
        TaskType taskType;

        public Task(String taskText, TaskType taskType) {
            this.taskText = taskText;
            this.taskType = taskType;
        }

        public String toJSON(Task task) {

            Gson gson = new Gson();
            String json = gson.toJson(task);
            return json;
        }
    }

    @Step
    private void givenTasksAtAllFilter(Task... tasks) {
        String js = "localStorage.setItem('todos-troopjs', '[";
        for (Task task : tasks) {
            js += (ACTIVE == task.taskType) ? "{\"completed\":false, \"title\":\"" + task.taskText + "\"}," : "{\"completed\":true, \"title\":\"" + task.taskText + "\"},";
        }
        js = js.substring(0, (js.length() - 1)) + "]');";
        executeJavaScript(js);
        refresh();
    }

    @Step
    private void givenTasksAtActiveFilter(Task... task) {
        givenTasksAtAllFilter(task);
        filterActive();
    }

    @Step
    private void givenTasksAtCompletedFilter(Task... task) {
        givenTasksAtAllFilter(task);
        filterCompleted();
    }
}

