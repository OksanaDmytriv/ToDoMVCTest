package todomvc4tasj.pageobjects_pagemodules0202.pageobjects;

import org.junit.Test;
import todomvc4tasj.pageobjects_pagemodules0202.pageobjects.pages.ToDoMVCPage;

import static todomvc4tasj.pageobjects_pagemodules0202.pageobjects.pages.ToDoMVCPage.TaskType.ACTIVE;
import static todomvc4tasj.pageobjects_pagemodules0202.pageobjects.pages.ToDoMVCPage.TaskType.COMPLETED;

public class ToDoMVCActiveFilterTest extends BaseTest{

    ToDoMVCPage page = new ToDoMVCPage();

    @Test
    public void testAddAtActive() {
        page.givenAtActive(page.aTask("a", ACTIVE));

        page.assertTasks("a");
        page.assertItemsLeft(1);
    }

    @Test
    public void testCompeleteAtActive() {
        page.givenAtActive(page.aTask("a", ACTIVE));

        page.toggle("a");
        page.assertEmptyVisibleTasks();
        page.assertItemsLeft(0);
    }

    @Test
    public void testCompleteAllAtActive() {
        page.givenAtActive("a", "b");

        page.toggleAll();
        page.assertEmptyVisibleTasks();
        page.assertItemsLeft(0);
    }

    @Test
    public void testEditAtActive() {
        page.givenAtActive("a", "b");

        page.startEditing("a", "a edited").pressEnter();
        page.assertTasks("a edited", "b");
        page.assertItemsLeft(2);
    }

    @Test
    public void testCancelEditAtActive() {
        page.givenAtActive("a", "b");

        page.startEditing("b", "b edited").pressEscape();
        page.assertTasks("a", "b");
        page.assertItemsLeft(2);
    }

    @Test
    public void testDeleteWhileEditingAtActive() {
        page.givenAtActive(page.aTask("a", ACTIVE));

        page.startEditing("a", " ").pressEnter();
        page.assertEmptyVisibleTasks();
    }

    @Test
    public void testClearCompletedAtActive() {
        page.givenAtActive(page.aTask("a", COMPLETED));

        page.сlearCompleted();
        page.assertEmptyVisibleTasks();
    }

    @Test
    public void testDeleteAtActive() {
        page.givenAtActive(page.aTask("a", ACTIVE));

        page.delete("a");
        page.assertEmptyVisibleTasks();
    }
}
