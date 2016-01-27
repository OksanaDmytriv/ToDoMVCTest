package todomvc4tasj.fullcoverage1801;

import org.junit.After;
import org.junit.Before;

import static com.codeborne.selenide.Selenide.*;

public class AtTodoMVCPageWithClearedDataAfterEachTest extends BaseTest {

    @Before
    public void openSite() {
        open("https://todomvc4tasj.herokuapp.com/");
    }

    @After
    public void clearData() {
        executeJavaScript("localStorage.clear()");
    }
}


