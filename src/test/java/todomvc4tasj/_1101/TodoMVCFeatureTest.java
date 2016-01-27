package todomvc4tasj._1101;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Step;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

/**
 * Created by dreamliner on 1/13/16.
 */
public class TodoMVCFeatureTest {

        @Before
        public void openSite() {
            open("https://todomvc4tasj.herokuapp.com/");
        }

        @After
        public void clearData() {
            executeJavaScript("localStorage.clear()");
        }

        @Test
        public void testEditTask() {
            add("a");

            startEditing("a", "a edited").pressEnter();
            assertTasks("a edited");
        }

        @Test
        public void testCancelEdit() {
            add("a");
            filterAll();

            startEditing("a", "a edited").pressEscape();
            assertTasks("a");
        }

        @Test
        public void testActivateAllOnCompletedFilter() {
            //given compeleted tasks
            add("a", "b");
            toggleAll();
            filterCompleted();

            toggleAll();
            assertEmptyVisibleTasks();
            assertItemsLeft(2);
        }

        @Test
        public void testTasksCommonFlow() {

            add("a");
            assertVisibleTasks("a");
            toggleAll();

            filterActive();
            assertEmptyVisibleTasks();

            add("b");
            toggle("b");
            assertEmptyVisibleTasks();

            filterCompleted();
            assertVisibleTasks("a", "b");

            //activate task
            toggle("a");
            assertVisibleTasks("b");

            сlearCompleted();
            assertEmptyVisibleTasks();

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
    }
