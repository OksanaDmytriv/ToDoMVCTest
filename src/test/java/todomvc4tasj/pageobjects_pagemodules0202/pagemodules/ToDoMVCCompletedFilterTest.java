package todomvc4tasj.pageobjects_pagemodules0202.pagemodules;

import org.junit.Test;

import static todomvc4tasj.pageobjects_pagemodules0202.pagemodules.pages.ToDoMVCPage.*;
import static todomvc4tasj.pageobjects_pagemodules0202.pagemodules.pages.ToDoMVCPage.TaskType.*;

public class ToDoMVCCompletedFilterTest {

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
    public void testActivateAllAtCompleted() {
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


}
