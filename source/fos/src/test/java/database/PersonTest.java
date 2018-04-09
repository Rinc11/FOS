package database;

import com.fos.database.NotLoadedExeption;
import com.fos.database.Person;
import com.fos.tools.Helper;
import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Tested ob die Klasse Person sich richtig verhält
 * Benötigt Datenbankseitig den TestUser Personen Datensatz
 */
public class PersonTest {

    /**
     * tested den testUser ob alle Werte so sind wie in der Datenbank.
     * Voraussetzung für /F0001/ und /F0010/
     */
    @Test
    public void testIfTestUserExists() throws SQLException, NotLoadedExeption {
        Connection conn = Helper.getConnection();
        Person person = Person.getPerson("testUser", conn);

        person.setLocked(false, conn);
        person.setLoginTry(0, conn);

        Assert.assertEquals("testUser", person.getUserName());
        Assert.assertEquals("Hans", person.getFirstName());
        Assert.assertEquals("Test", person.getLastName());
        Assert.assertEquals("756.1234.5678.90", person.getAhv());
        Assert.assertEquals("Teststrasse 1", person.getStreet());
        Assert.assertEquals("Testdorf", person.getPlace());
        Assert.assertEquals("test.user@students.zhaw.ch", person.getEmail());
        Assert.assertEquals("03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4",
                            person.getPasswordHash());
        Assert.assertEquals("1234", person.getPasswordHint());
        Assert.assertEquals(false, person.getLocked());
        Assert.assertTrue(0 == person.getLoginTry());
        Assert.assertTrue(Person.PersonUserType.MITARBEITER == person.getUserType());
        Assert.assertEquals(false, person.getDeleted());
    }

    /**
     * Tested ob das Sperren^(1) und Anmeldeversuchsfunktion^(2) richtig funktionieren.
     * (1) /F0040/ Zif 2),  (2) /F0010/ Zif. 3)
     */
    @Test
    public void testSetLoginTry() throws SQLException, NotLoadedExeption {
        Connection conn = Helper.getConnection();
        Person person = Person.getPerson("testUser", conn);
        person.setLocked(false, conn);
        Assert.assertEquals(false, person.getLocked());


        person.setLoginTry(5, conn);
        Assert.assertTrue(5 == person.getLoginTry());

        person.setLoginTry(11, conn);
        Assert.assertTrue(11 == person.getLoginTry());
        Assert.assertEquals(true, person.getLocked());

        person.setLocked(false, conn);
        Assert.assertTrue(!person.getLocked());

        person.setLoginTry(0, conn);
        Assert.assertTrue(0 == person.getLoginTry());
    }

    /**
     * Tested ob die Person testUser in der Liste von allen Personen ist.
     * /F0090/
     */
    @Test
    public void testGetAllPersons() throws SQLException, NotLoadedExeption {
        Connection conn = Helper.getConnection();
        List<Person> persons = Person.getAllPersons(conn);
        Assert.assertEquals("testUser", persons.stream().filter(f -> {
            try {
                return f.getUserName().equals("testUser");
            } catch (NotLoadedExeption notLoadedExeption) {
                notLoadedExeption.printStackTrace();
            }
            return false;
        }).findAny().get().getUserName());
    }

    /**
     * testet, ob ein neuer Benutzer korrekt in die Datenbank gespeichert wird
     */
    @Test
    public void testAddNewPerson() throws SQLException, NotLoadedExeption {

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

        Connection conn = Helper.getConnection();
        Person.addNewPerson(username, firstName, lastName, ahv, street, place, email, password, passwordHint, userType
                , conn);
        Person person = Person.getPerson(username, conn);

        Assert.assertEquals(username, person.getUserName());
        Assert.assertEquals(firstName, person.getFirstName());
        Assert.assertEquals(lastName, person.getLastName());
        Assert.assertEquals(ahv, person.getAhv());
        Assert.assertEquals(street, person.getStreet());
        Assert.assertEquals(place, person.getPlace());
        Assert.assertEquals(email, person.getEmail());
        Assert.assertEquals(passwordHint, person.getPasswordHint());
        Assert.assertEquals(false, person.getLocked());
        Assert.assertTrue(0 == person.getLoginTry());
        Assert.assertTrue(Person.PersonUserType.MITARBEITER == person.getUserType());
        Assert.assertEquals(false, person.getDeleted());

        PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM fos.\"Person\"  WHERE \"Username\" = '"+ username +"'");
        preparedStatement.execute();
    }

    /**
     * testet, ob ein bestehender Benutzer gelöscht wird.
     */
    @Test
    public void testRemovePerson() throws SQLException, NotLoadedExeption {

        Connection conn = Helper.getConnection();
        Person.removePerson("testUser", conn);
        Person person = Person.getPerson("testUser", conn);

        Assert.assertEquals(true, person.getDeleted());

        PreparedStatement preparedStatement = conn.prepareStatement("UPDATE fos.\"Person\" SET \"Deleted_YN\" = false WHERE \"Username\" = 'testUser'");
        preparedStatement.execute();
    }

    /**
     * testet, ob eine bestehende Person richtig geupdatet wird
     */
    @Test
    public void testUpdatePerson() throws SQLException, NotLoadedExeption, NoSuchAlgorithmException {

        String username = "testUser";
        String firstName = "Hans";
        String lastName = "Muster";
        String ahv = "756.1234.5678.90";
        String street = "strasse 12";
        String place = "Winterthur";
        String email = "hans.muster@muster.ch";
        String password = "12345678";
        String passwordHint = "12345678";
        Boolean locked = true;
        String userType = "Admin";

        Connection conn = Helper.getConnection();

        Person.updatePerson(username, firstName, lastName, ahv, street, place, email, Helper.getHash(password), passwordHint, locked, userType, conn, true );
        Person person = Person.getPerson("testUser", conn);
        Assert.assertEquals(username, person.getUserName());
        Assert.assertEquals(firstName, person.getFirstName());
        Assert.assertEquals(lastName, person.getLastName());
        Assert.assertEquals(ahv, person.getAhv());
        Assert.assertEquals(street, person.getStreet());
        Assert.assertEquals(place, person.getPlace());
        Assert.assertEquals(email, person.getEmail());
        Assert.assertEquals(passwordHint, person.getPasswordHint());
        Assert.assertEquals(true, person.getLocked());
        Assert.assertTrue(0 == person.getLoginTry());
        Assert.assertTrue(Person.PersonUserType.ADMIN == person.getUserType());
        Assert.assertEquals(false, person.getDeleted());

        Person.updatePerson("testUser", "Hans", "Test","756.1234.5678.90","Teststrasse 1", "Testdorf", "test.user@students.zhaw.ch", "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","1234", false,"Mitarbeiter", conn, true);
    }

}
