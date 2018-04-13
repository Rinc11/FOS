import com.fos.HomePage;
import com.fos.database.NotLoadedException;
import com.fos.database.Person;
import com.fos.tools.Helper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import tools.TestHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;


public class HomePageTest {
    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * updated die Datenbank auf den neusten Stand mit Testdaten
     */
    @BeforeClass
    public static void updateDatabase() throws Exception {
        TestHelper.loadDatabaseUpdates();
    }

    @Before
    public void createRequestAndResponse(){
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
    }

    @Test
    public void testGetLockedUserCount() throws SQLException, NotLoadedException {
        Connection conn = Helper.getConnection();

        Person testUser = Person.getPerson("testUser", conn);
        testUser.setLocked(false, conn);

        HomePage homePage = new HomePage(request, response);
        int oldLockCount = homePage.getLockedUserCount();
        testUser.setLocked(true, conn);

        int newLockCount = homePage.getLockedUserCount();
        assertEquals(oldLockCount+1, newLockCount);

        testUser.setLocked(false, conn);
    }

}
