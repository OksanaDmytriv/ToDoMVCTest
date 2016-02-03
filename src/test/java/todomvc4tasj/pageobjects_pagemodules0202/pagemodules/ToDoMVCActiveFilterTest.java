package todomvc4tasj.pageobjects_pagemodules0202.pagemodules;

import org.junit.Test;

import static todomvc4tasj.pageobjects_pagemodules0202.pagemodules.pages.ToDoMVC.TaskType.ACTIVE;
import static todomvc4tasj.pageobjects_pagemodules0202.pagemodules.pages.ToDoMVC.TaskType.COMPLETED;
import static todomvc4tasj.pageobjects_pagemodules0202.pagemodules.pages.ToDoMVC.*;

public class ToDoMVCActiveFilterTest extends BaseTest{

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
    public void testCompleteAllAtActive() {
        givenAtActive("a", "b");

        toggleAll();
        assertEmptyVisibleTasks();
        assertItemsLeft(0);
    }

    @Test
    public void testEditAtActive() {
        givenAtActive("a", "b");

        startEditing("a", "a edited").pressEnter();
        assertTasks("a edited", "b");
        assertItemsLeft(2);
    }

    @Test
    public void testCancelEditAtActive() {
        givenAtActive("a", "b");

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
}
