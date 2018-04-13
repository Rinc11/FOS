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
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * testet die UserPage-Klasse
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

    /**
     * testet die Methode addNewItem als Admin-User
     *
     * @throws SQLException
     * @throws NotLoadedException
     */
    @Test
    public void testAddNewItemAsAdmin() throws SQLException, NotLoadedException {
        String username = "hansmuster";
        String firstName = "Hans";
        String lastName = "Muster";
        String ahv = "756.1234.5678.90";
        String street = "strasse 12";
        String place = "Winterthur";
        String email = "hans.muster@muster.ch";
        String password = "1234";
        String passwordHint = "1234";
        String userType = "Mitarbeiter";

        loggedInUserIsAdmin();

        when(request.getParameter("command")).thenReturn("addUser");

        when(request.getParameter("username")).thenReturn(username);
        when(request.getParameter("firstname")).thenReturn(firstName);
        when(request.getParameter("lastname")).thenReturn(lastName);
        when(request.getParameter("ahv")).thenReturn(ahv);
        when(request.getParameter("street")).thenReturn(street);
        when(request.getParameter("place")).thenReturn(place);
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);
        when(request.getParameter("passwordHint")).thenReturn(passwordHint);
        when(request.getParameter("usertype")).thenReturn(userType);

        new UserPage(request, response);

        Person person = Person.getPerson(username, conn);
        assertEquals(username, person.getUserName());
        assertEquals(firstName, person.getFirstName());
        assertEquals(lastName, person.getLastName());
        assertEquals(ahv, person.getAhv());
        assertEquals(street, person.getStreet());
        assertEquals(place, person.getPlace());
        assertEquals(email, person.getEmail());
        assertEquals(passwordHint, person.getPasswordHint());
        assertEquals(false, person.getLocked());
        assertTrue(0 == person.getLoginTry());
        assertTrue(Person.PersonUserType.MITARBEITER == person.getUserType());
        assertEquals(false, person.getDeleted());

        PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM \"Person\"  WHERE \"Username\" = ?");
        preparedStatement.setString(1, username);
        preparedStatement.execute();
    }

    /**
     * testet die Methode addNewItem als Mitarbeiter-User
     *
     * @throws SQLException
     * @throws NotLoadedException
     */
    @Test
    public void testAddNewItemAsEmployee() throws SQLException, NotLoadedException {
        String username = "hansmuster";
        String firstName = "Hans";
        String lastName = "Muster";
        String ahv = "756.1234.5678.90";
        String street = "strasse 12";
        String place = "Winterthur";
        String email = "hans.muster@muster.ch";
        String password = "1234";
        String passwordHint = "1234";
        String userType = "Mitarbeiter";

        loggedInUserIsEmployee();

        when(request.getParameter("command")).thenReturn("addUser");
        when(request.getParameter("username")).thenReturn(username);
        when(request.getParameter("firstname")).thenReturn(firstName);
        when(request.getParameter("lastname")).thenReturn(lastName);
        when(request.getParameter("ahv")).thenReturn(ahv);
        when(request.getParameter("street")).thenReturn(street);
        when(request.getParameter("place")).thenReturn(place);
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);
        when(request.getParameter("passwordHint")).thenReturn(passwordHint);
        when(request.getParameter("usertype")).thenReturn(userType);

        ArgumentCaptor<List<String>> errorListArgument = ArgumentCaptor.forClass(List.class);
        new UserPage(request, response);

        verify(request).setAttribute(eq("errorMessage"), errorListArgument.capture());
        assertEquals(1, errorListArgument.getValue().size());
        assertEquals("fehlende Rechte", errorListArgument.getValue().get(0));
    }
    /**
     * testet die Methode updateItem als Admin-User
     *
     * @throws SQLException
     * @throws NotLoadedException
     */
    @Test
    public void testUpdateItemAsAdmin() throws SQLException, NotLoadedException {
        String username = "testUser";
        String firstName = "Hans";
        String lastName = "Muster";
        String ahv = "756.1234.5678.90";
        String street = "strasse 12";
        String place = "Winterthur";
        String email = "hans.muster@muster.ch";
        String password = "12345678";
        String passwordHash = "ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f";
        String passwordHint = "12345678";
        String locked = "on";
        String userType = "Admin";

        loggedInUserIsAdmin();

        when(request.getParameter("command")).thenReturn("editUser:" + username);
        when(request.getParameter("username")).thenReturn(username);
        when(request.getParameter("firstname")).thenReturn(firstName);
        when(request.getParameter("lastname")).thenReturn(lastName);
        when(request.getParameter("ahv")).thenReturn(ahv);
        when(request.getParameter("street")).thenReturn(street);
        when(request.getParameter("place")).thenReturn(place);
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);
        when(request.getParameter("passwordHint")).thenReturn(passwordHint);
        when(request.getParameter("locked")).thenReturn(locked);
        when(request.getParameter("usertype")).thenReturn(userType);

        new UserPage(request, response);

        Person person = Person.getPerson(username, conn);
        assertEquals(username, person.getUserName());
        assertEquals(firstName, person.getFirstName());
        assertEquals(lastName, person.getLastName());
        assertEquals(ahv, person.getAhv());
        assertEquals(street, person.getStreet());
        assertEquals(place, person.getPlace());
        assertEquals(email, person.getEmail());
        assertEquals(passwordHash, person.getPasswordHash());
        assertEquals(passwordHint, person.getPasswordHint());
        assertEquals(true, person.getLocked());
        assertTrue(Person.PersonUserType.ADMIN == person.getUserType());
        assertEquals(false, person.getDeleted());

        //abgeänderter Test-Datensatz wieder zurücksetzen
        Person.updatePerson("testUser", "Hans", "Test","756.1234.5678.90","Teststrasse 1", "Testdorf", "test.user@students.zhaw.ch", "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","1234", false,"Mitarbeiter", conn, true);
    }

    /**
     * testet die Methode updateItem als Mitarbeiter-User
     *
     * @throws SQLException
     * @throws NotLoadedException
     */
    @Test
    public void testUpdateItemAsEmployee() throws SQLException, NotLoadedException {
        String username = "testUser";
        String firstName = "Hans";
        String lastName = "Muster";
        String ahv = "756.1234.5678.90";
        String street = "strasse 12";
        String place = "Winterthur";
        String email = "hans.muster@muster.ch";
        String password = "12345678";
        String passwordHash = "ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f";
        String passwordHint = "12345678";
        String locked = "on";
        String userType = "Admin";

        loggedInUserIsEmployee();

        when(request.getParameter("command")).thenReturn("editUser:" + username);
        when(request.getParameter("username")).thenReturn(username);
        when(request.getParameter("firstname")).thenReturn(firstName);
        when(request.getParameter("lastname")).thenReturn(lastName);
        when(request.getParameter("ahv")).thenReturn(ahv);
        when(request.getParameter("street")).thenReturn(street);
        when(request.getParameter("place")).thenReturn(place);
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);
        when(request.getParameter("passwordHint")).thenReturn(passwordHint);
        when(request.getParameter("locked")).thenReturn(locked);
        when(request.getParameter("usertype")).thenReturn(userType);

        ArgumentCaptor<List<String>> errorListArgument = ArgumentCaptor.forClass(List.class);
        new UserPage(request, response);

        verify(request).setAttribute(eq("errorMessage"), errorListArgument.capture());
        assertEquals(1, errorListArgument.getValue().size());
        assertEquals("fehlende Rechte", errorListArgument.getValue().get(0));
    }

    /**
     * testet die Methode removeItem als Admin-User
     *
     * @throws SQLException
     * @throws NotLoadedException
     */
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

    /**
     * testet die Methode removeItem als Mitarbert-User
     *
     * @throws SQLException
     * @throws NotLoadedException
     */
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