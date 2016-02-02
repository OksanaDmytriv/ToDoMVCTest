package todomvc4tasj.pageobjects_pagemodules0202.pagemodules;

import org.junit.Test;

import static todomvc4tasj.pageobjects_pagemodules0202.pagemodules.pages.ToDoMVCPage.*;

public class ToDoMVCTest extends BaseTest {

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
        assertEmptyTasks();
    }
}


