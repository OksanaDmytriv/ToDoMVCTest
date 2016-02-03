package todomvc4tasj.pageobjects_pagemodules0202.pageobjects;

import org.junit.Test;
import todomvc4tasj.pageobjects_pagemodules0202.pageobjects.pages.ToDoMVCPage;

import static todomvc4tasj.pageobjects_pagemodules0202.pageobjects.pages.ToDoMVCPage.TaskType.ACTIVE;
import static todomvc4tasj.pageobjects_pagemodules0202.pageobjects.pages.ToDoMVCPage.TaskType.COMPLETED;

public class ToDoMVCAllFilterTest extends BaseTest{

    ToDoMVCPage page = new ToDoMVCPage();

    @Test
    public void testEditAtAll() {
        page.givenAtAll(page.aTask("a", COMPLETED));

        page.startEditing("a", "a edited").pressEnter();
        page.assertTasks("a edited");
        page.assertItemsLeft(0);
    }

    @Test
    public void testCancelEditAtAll() {
        page.givenAtAll(page.aTask("a", COMPLETED));

        page.startEditing("a", "a edited").pressEscape();
        page.assertTasks("a");
        page.assertItemsLeft(0);
    }

    @Test
    public void testEditAndClickOutsideAtAll() {
        page.givenAtAll("a", "b");

        page.startEditing("a", "a edited");
        page.newTask.click();
        page.assertTasks("a edited", "b");
        page.assertItemsLeft(2);
    }

    @Test
    public void testCompleteAtAll() {
        page.givenAtAll(page.aTask("a", ACTIVE),
                page.aTask("b", COMPLETED));

        page.assertItemsLeft(1);
        page.toggle("a");
        page.assertItemsLeft(0);
    }

    @Test
    public void testClearCompletedAtAll() {
        page.givenAtAll(page.aTask("a", COMPLETED),
                page.aTask("b", COMPLETED));

        page.assertItemsLeft(0);
        page.сlearCompleted();
        page.assertEmptyVisibleTasks();
    }

    @Test
    public void testActivateAllAtAll() {
        page.givenAtAll(page.aTask("a", COMPLETED));

        page.assertItemsLeft(0);
        page.toggle("a");
        page.assertItemsLeft(1);
    }

    @Test
    public void testActivateTasksAtAll() {
        page.givenAtAll(page.aTask("a", COMPLETED),
                page.aTask("b", COMPLETED));

        page.assertItemsLeft(0);
        page.toggleAll();
        page.assertItemsLeft(2);
    }

}
