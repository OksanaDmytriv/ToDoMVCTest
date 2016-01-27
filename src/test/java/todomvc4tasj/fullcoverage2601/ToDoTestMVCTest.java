package todomvc4tasj.fullcoverage2601;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Step;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static todomvc4tasj.fullcoverage2601.ToDoTestMVCTest.TaskType.ACTIVE;
import static todomvc4tasj.fullcoverage2601.ToDoTestMVCTest.TaskType.COMPLETED;

public class ToDoTestMVCTest extends AtTodoMVCPageWithClearedDataAfterEachTest {

    //All filter: edit, toggle, clear completed tasks
    @Test
    public void testEditAtAllFilter() {
        givenTasksAtAllFilter(aTask("a", ACTIVE));

        startEditing("a", "a edited").pressEnter();
        assertTasks("a edited");
        assertItemsLeft(1);
    }

    @Test
    public void testCancelEditAtAllFilter() {
        givenTasksAtAllFilter(aTask("a", ACTIVE));

        startEditing("a", "a edited").pressEscape();
        assertTasks("a");
        assertItemsLeft(1);
    }

    @Test
    public void testEditAndClickOutsideAtAllFilter() {
        givenTasksAtAllFilter(aTask("a", ACTIVE));

        startEditing("a", "a edited");
        newTask.click();
        assertTasks("a edited");
        assertItemsLeft(1);
    }

    @Test
    public void testDeleteWhileEditingAtAllFilter() {
        givenTasksAtAllFilter(aTask("a", ACTIVE),
                aTask("b", ACTIVE));

        startEditing("a", " ").pressEnter();
        assertTasks("b");
        assertItemsLeft(1);
    }

    @Test
    public void testToggleAtAllFilter() {
        givenTasksAtAllFilter(aTask("a", ACTIVE),
                aTask("b", COMPLETED));

        assertItemsLeft(1);
        toggleAll();
        assertItemsLeft(0);
    }

    @Test
    public void testClearCompletedAtAllFilter() {
        givenTasksAtAllFilter(aTask("a", ACTIVE),
                aTask("b", COMPLETED));

        assertItemsLeft(1);
        toggle("a");
        сlearCompleted();
        assertEmptyVisibleTasks();
    }

    //Active filter: add, toggle&toggle all, edit, clear completed, delete tasks
    @Test
    public void testAddAtActiveFilter() {
        givenTasksAtActiveFilter(aTask("a", ACTIVE));

        assertTasks("a");
        assertItemsLeft(1);
    }

    @Test
    public void testToogleAtActiveFilter() {
        givenTasksAtActiveFilter(aTask("a", ACTIVE));

        toggle("a");
        assertEmptyVisibleTasks();
        assertItemsLeft(0);
    }

    @Test
    public void toggleAllAtActiveFilter() {
        givenTasksAtActiveFilter(aTask("a", ACTIVE),
                aTask("b", ACTIVE));

        toggleAll();
        assertEmptyVisibleTasks();
        assertItemsLeft(0);
    }

    @Test
    public void testEditAtActiveFilter() {
        givenTasksAtActiveFilter(aTask("a", ACTIVE),
                aTask("b", ACTIVE));

        startEditing("a", "a edited").pressEnter();
        assertTasks("a edited", "b");
        assertItemsLeft(2);
    }

    @Test
    public void testCancelEditAtActiveFilter() {
        givenTasksAtActiveFilter(aTask("a", ACTIVE));

        startEditing("a", "a edited").pressEscape();
        assertTasks("a");
        assertItemsLeft(1);
    }

    @Test
    public void testEditAndClickOutsideAtActiveFilter() {
        givenTasksAtActiveFilter(aTask("a", ACTIVE));

        startEditing("a", "a edited");
        newTask.click();
        assertTasks("a edited");
        assertItemsLeft(1);
    }

    @Test
    public void testDeleteWhileEditingAtActiveFilter() {
        givenTasksAtActiveFilter(aTask("a", ACTIVE));

        startEditing("a", " ").pressEnter();
        assertEmptyVisibleTasks();
    }

    @Test
    public void testClearCompletedAtActiveFilter() {
        givenTasksAtActiveFilter(aTask("a", ACTIVE));

        toggle("a");
        assertItemsLeft(0);
        сlearCompleted();
        assertEmptyVisibleTasks();
    }

    //@Test
    public void testDeleteAtActiveFilter() {
        givenTasksAtActiveFilter(aTask("a", ACTIVE),
                aTask("b", ACTIVE));

        delete("a");
        assertTasks("b");
        assertItemsLeft(1);
    }

    //Completed filter: edit,activate all, delete tasks, clear completed
    @Test
    public void testEditAtCompletedFilter() {
        givenTasksAtCompletedFilter(aTask("a", COMPLETED),
                aTask("b", COMPLETED));

        startEditing("a", "a edited").pressEnter();
        assertTasks("a edited", "b");
        assertItemsLeft(0);
    }

    @Test
    public void testCancelEditAtCompletedFilter() {
        givenTasksAtCompletedFilter(aTask("a", COMPLETED));

        startEditing("a", "a edited").pressEscape();
        assertTasks("a");
        assertItemsLeft(0);
    }

    @Test
    public void testActivateAllOnCompletedFilter() {
        givenTasksAtCompletedFilter(aTask("a", COMPLETED),
                aTask("b", COMPLETED));

        toggleAll();
        assertEmptyVisibleTasks();
        assertItemsLeft(2);
    }

    @Test
    public void testDeleteAtCompletedFilter() {
        givenTasksAtCompletedFilter(aTask("a", COMPLETED),
                aTask("b", COMPLETED));

        delete("a");
        assertTasks("b");
        assertItemsLeft(0);
    }

    @Test
    public void testClearCompletedAtCompletedFilter() {
        givenTasksAtCompletedFilter(aTask("a", COMPLETED));

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
        String text;
        TaskType type;

        public Task(String text, TaskType type) {
            this.text = text;
            this.type = type;
        }
    }

    @Step
    private Task aTask(String taskText, TaskType taskType) {
        Task task = new Task(taskText, taskType);
        return task;
    }

    @Step
    private String toJSON(Task task) {
        if (ACTIVE == task.type) {
            return "{\"completed\":false, \"title\":\"" + task.text + "\"},";
        } else {
            return "{\"completed\":true, \"title\":\"" + task.text + "\"},";
        }
    }

    @Step
    private void givenTasksAtAllFilter(Task... tasks) {
        String js = "localStorage.setItem('todos-troopjs', '[";
        for (Task task : tasks) {
            js += "{\"completed\":" + ((ACTIVE == task.type) ? false : true) + ", \"title\":\"" + task.text + "\"},";
            //або
            //js += toJSON(task);
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

