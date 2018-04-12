import com.fos.UserPage;
import com.fos.database.NotLoadedException;
import com.fos.database.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * tested die UserPage
 */
public class UserPageTest {
    private final String testUserName = "testUser";

    private Connection conn;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;


    @Before
    public void initConnection() throws SQLException {
        conn = com.fos.tools.Helper.getConnection();

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

    }


    @Test
    public void testRemoveItemAsAdmin() throws SQLException, NotLoadedException {
        Person person = Person.getPerson(testUserName, conn);
        Assert.assertEquals(false, person.getDeleted());

        loggedInUserIsAdmin();

        when(request.getParameter("command")).thenReturn("removeUser:"+ testUserName);

        new UserPage(request, response);

        person = Person.getPerson(testUserName, conn);
        assertEquals(true, person.getDeleted());

        PreparedStatement preparedStatement = conn.prepareStatement("UPDATE \"Person\" SET \"Deleted_YN\" = false WHERE \"Username\" = ?");
        preparedStatement.setString(1, testUserName);
        preparedStatement.execute();
    }

    @Test
    public void testRemoveItemAsEmployee() throws SQLException, NotLoadedException {
        Person person = Person.getPerson(testUserName, conn);
        Assert.assertEquals(false, person.getDeleted());

        loggedInUserIsEmployee();

        when(request.getParameter("command")).thenReturn("removeUser:"+ testUserName);

        ArgumentCaptor<List<String>> errorListArgument = ArgumentCaptor.forClass(List.class);

        new UserPage(request, response);

        verify(request).setAttribute(eq("errorMessage"), errorListArgument.capture());
        assertEquals(1, errorListArgument.getValue().size());
        assertEquals("fehlende Rechte", errorListArgument.getValue().get(0));

        person = Person.getPerson(testUserName, conn);
        assertEquals(false, person.getDeleted());
    }

    private void loggedInUserIsAdmin() throws NotLoadedException {
        Person loggedInPerson = mock(Person.class);
        when(loggedInPerson.getIsAdmin()).thenReturn(true);

        when(session.getAttribute("userLoggedIn")).thenReturn(loggedInPerson);
    }


    private void loggedInUserIsEmployee() throws NotLoadedException {
        Person loggedInPerson = mock(Person.class);
        when(loggedInPerson.getIsAdmin()).thenReturn(false);

        when(session.getAttribute("userLoggedIn")).thenReturn(loggedInPerson);
    }
}