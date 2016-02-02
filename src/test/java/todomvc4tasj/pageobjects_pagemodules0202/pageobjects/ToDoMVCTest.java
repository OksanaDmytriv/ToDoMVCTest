package todomvc4tasj.pageobjects_pagemodules0202.pageobjects;

import org.junit.Test;
import todomvc4tasj.pageobjects_pagemodules0202.pageobjects.pages.ToDoMVCPage;

public class ToDoMVCTest extends BaseTest {

    ToDoMVCPage page = new ToDoMVCPage();

    @Test
    public void testTasksCommonFlow() {

        page.givenAtAll();
        page.add("a");
        page.assertVisibleTasks("a");
        page.assertItemsLeft(1);
        page.toggleAll();

        page.filterActive();
        page.assertEmptyVisibleTasks();

        page.filterCompleted();
        page.assertVisibleTasks("a");

        //activate task
        page.toggle("a");
        page.assertEmptyVisibleTasks();
        page.assertItemsLeft(1);

        page.filterAll();
        page.assertTasks("a");

        page.filterCompleted();
        page.assertEmptyVisibleTasks();

        page.filterActive();
        page.assertTasks("a");

        page.filterAll();
        page.delete("a");
        page.assertEmptyTasks();
    }
}


