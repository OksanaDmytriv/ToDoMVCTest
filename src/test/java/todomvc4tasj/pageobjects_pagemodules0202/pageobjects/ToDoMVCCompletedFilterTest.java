package todomvc4tasj.pageobjects_pagemodules0202.pageobjects;

import org.junit.Test;
import todomvc4tasj.pageobjects_pagemodules0202.pageobjects.pages.ToDoMVCPage;

import static todomvc4tasj.pageobjects_pagemodules0202.pageobjects.pages.ToDoMVCPage.TaskType.COMPLETED;

public class ToDoMVCCompletedFilterTest extends BaseTest {

    ToDoMVCPage page = new ToDoMVCPage();

    @Test
    public void testEditAtCompleted() {
        page.givenAtCompleted(page.aTask("a", COMPLETED),
                page.aTask("b", COMPLETED));

        page.startEditing("a", "a edited").pressEnter();
        page.assertTasks("a edited", "b");
        page.assertItemsLeft(0);
    }

    @Test
    public void testCancelEditAtCompleted() {
        page.givenAtCompleted(page.aTask("a", COMPLETED));

        page.startEditing("a", "a edited").pressEscape();
        page.assertTasks("a");
        page.assertItemsLeft(0);
    }

    @Test
    public void testActivateAllAtCompleted() {
        page.givenAtCompleted(page.aTask("a", COMPLETED),
                page.aTask("b", COMPLETED));

        page.toggleAll();
        page.assertEmptyVisibleTasks();
        page.assertItemsLeft(2);
    }

    @Test
    public void testDeleteAtCompleted() {
        page.givenAtCompleted(page.aTask("a", COMPLETED),
                page.aTask("b", COMPLETED));

        page.delete("a");
        page.assertTasks("b");
        page.assertItemsLeft(0);
    }

    @Test
    public void testClearCompletedAtCompleted() {
        page.givenAtCompleted(page.aTask("a", COMPLETED),
                page.aTask("b", COMPLETED));

        page.assertItemsLeft(0);
        page.сlearCompleted();
        page.assertEmptyVisibleTasks();
    }


}
