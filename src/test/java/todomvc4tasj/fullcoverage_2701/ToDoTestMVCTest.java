package todomvc4tasj.fullcoverage_2701;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Step;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;
import static todomvc4tasj.fullcoverage_2701.ToDoTestMVCTest.TaskType.ACTIVE;
import static todomvc4tasj.fullcoverage_2701.ToDoTestMVCTest.TaskType.COMPLETED;

public class ToDoTestMVCTest {

    @Before
    public void ensureOpenedToDoMVC() {
        if (url() != ("https://todomvc4tasj.herokuapp.com/")) {
            open("https://todomvc4tasj.herokuapp.com/");
        }
    }

    //ALL FILTER
    @Test
    public void testEditAtAllFilter() {
        givenAtAllFilter(aTask("a", COMPLETED));

        startEditing("a", "a edited").pressEnter();
        assertTasks("a edited");
        assertItemsLeft(0);
    }

    @Test
    public void testCancelEditAtAllFilter() {
        givenAtAllFilter(aTask("a", COMPLETED));

        startEditing("a", "a edited").pressEscape();
        assertTasks("a");
        assertItemsLeft(0);
    }

    @Test
    public void testEditAndClickOutsideAtAllFilter() {
        givenAtAll("a", "b");

        startEditing("a", "a edited");
        newTask.click();
        assertTasks("a edited", "b");
        assertItemsLeft(2);
    }

    @Test
    public void testToggleOneTaskAtAllFilter() {
        givenAtAllFilter(aTask("a", ACTIVE),
                aTask("b", COMPLETED));

        assertItemsLeft(1);
        toggle("a");
        assertItemsLeft(0);
    }

    @Test
    public void testClearCompletedAtAllFilter() {
        givenAtAllFilter(aTask("a", ACTIVE),
                aTask("b", COMPLETED));

        assertItemsLeft(1);
        toggle("a");
        сlearCompleted();
        assertEmptyVisibleTasks();
    }

    @Test
    public void testActivateOneAtAllFilter() {
        givenAtAllFilter(aTask("a", COMPLETED));

        assertItemsLeft(0);
        toggle("a");
        assertItemsLeft(1);
    }

    @Test
    public void testActivateAllAtAllFilter() {
        givenAtAllFilter(aTask("a", COMPLETED),
                aTask("b", COMPLETED));

        assertItemsLeft(0);
        toggleAll();
        assertItemsLeft(2);
    }


    //ACTIVE FILTER
    @Test
    public void testAddAtActiveFilter() {
        givenAtActiveFilter(aTask("a", ACTIVE));

        assertTasks("a");
        assertItemsLeft(1);
    }

    @Test
    public void testToogleOneAtActiveFilter() {
        givenAtActiveFilter(aTask("a", ACTIVE));

        toggle("a");
        assertEmptyVisibleTasks();
        assertItemsLeft(0);
    }

    @Test
    public void toggleAllAtActiveFilter() {
        givenAtActive("a", "b");

        toggleAll();
        assertEmptyVisibleTasks();
        assertItemsLeft(0);
    }

    @Test
    public void testEditAtActiveFilter() {
        givenAtActive("a", "b");

        startEditing("a", "a edited").pressEnter();
        assertTasks("a edited", "b");
        assertItemsLeft(2);
    }

    @Test
    public void testCancelEditAtActiveFilter() {
        givenAtActive("a", "b");

        startEditing("b", "b edited").pressEscape();
        assertTasks("a", "b");
        assertItemsLeft(2);
    }

    @Test
    public void testDeleteWhileEditingAtActiveFilter() {
        givenAtActiveFilter(aTask("a", ACTIVE));

        startEditing("a", " ").pressEnter();
        assertEmptyVisibleTasks();
    }

    @Test
    public void testClearCompletedAtActiveFilter() {
        givenAtActiveFilter(aTask("a", COMPLETED));

        сlearCompleted();
        assertEmptyVisibleTasks();
    }

    @Test
    public void testDeleteAtActiveFilter() {
        givenAtActiveFilter(aTask("a", ACTIVE));

        delete("a");
        assertEmptyVisibleTasks();
    }

    //COMPLETED FILTER
    @Test
    public void testEditAtCompletedFilter() {
        givenAtCompletedFilter(aTask("a", COMPLETED),
                aTask("b", COMPLETED));

        startEditing("a", "a edited").pressEnter();
        assertTasks("a edited", "b");
        assertItemsLeft(0);
    }

    @Test
    public void testCancelEditAtCompletedFilter() {
        givenAtCompletedFilter(aTask("a", COMPLETED));

        startEditing("a", "a edited").pressEscape();
        assertTasks("a");
        assertItemsLeft(0);
    }

    @Test
    public void testActivateAllOnCompletedFilter() {
        givenAtCompletedFilter(aTask("a", COMPLETED),
                aTask("b", COMPLETED));

        toggleAll();
        assertEmptyVisibleTasks();
        assertItemsLeft(2);
    }

    @Test
    public void testDeleteAtCompletedFilter() {
        givenAtCompletedFilter(aTask("a", COMPLETED),
                aTask("b", COMPLETED));

        delete("a");
        assertTasks("b");
        assertItemsLeft(0);
    }

    @Test
    public void testClearCompletedAtCompletedFilter() {
        givenAtCompletedFilter(aTask("a", COMPLETED),
                aTask("b", COMPLETED));

        assertItemsLeft(0);
        сlearCompleted();
        assertEmptyVisibleTasks();
    }

    @Test
    public void testTasksCommonFlow() {

        givenAll();
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
    private void givenAtAllFilter(Task... tasks) {
        String js = "localStorage.setItem('todos-troopjs', '[";
        for (Task task : tasks) {
            js += toJSON(task);
        }
        js = js.substring(0, (js.length() - 1)) + "]');";
        executeJavaScript(js);
        refresh();
    }

    @Step
    private void givenAtActiveFilter(Task... task) {
        givenAtAllFilter(task);
        filterActive();
    }

    @Step
    private void givenAtCompletedFilter(Task... task) {
        givenAtAllFilter(task);
        filterCompleted();
    }

    @Step
    private void givenAtAll(String... taskTexts) {
        String js = "localStorage.setItem('todos-troopjs', '[";
        for (String text : taskTexts) {
            js += "{\"completed\":false, \"title\":\"" + text + "\"},";
        }
        js = js.substring(0, (js.length() - 1)) + "]');";
        executeJavaScript(js);
        refresh();
    }

    @Step
    private void givenAtActive(String... taskTexts) {
        givenAtAll(taskTexts);
        filterActive();
    }

    @Step
    private void givenAll() {
        String js = "localStorage.setItem('todos-troopjs', '[{\"completed\":false, \"title\":\" \"}]');";
        // якщо написати отак: "localStorage.setItem('todos-troopjs', '[{\"completed\":false, \"title\":\"\"}]');" - то ось що виходить :)
        // http://take.ms/paMHMhttp://take.ms/paMHM
        executeJavaScript(js);
        refresh();
        clearData();
        refresh();
    }

    @Step
    private void givenActive() {
        givenAll();
        filterActive();
    }

    private void clearData() {
        executeJavaScript("localStorage.clear()");
    }

}


