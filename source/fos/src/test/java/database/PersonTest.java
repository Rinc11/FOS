package database;

import com.fos.database.NotLoadedExeption;
import com.fos.database.Person;
import com.fos.tools.Helper;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
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
}
