package database;

import com.fos.database.NotLoadedExeption;
import com.fos.database.Person;
import com.fos.tools.Helper;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Tested ob die Klasse Peron sich richtig verhält
 */
public class PersonTest {

    /**
     * tested den testUser ob alle Werte so dind wie in der Datenbank.
     */
    @Test
    public void testIfTesUserExists() throws SQLException, NotLoadedExeption {
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
        Assert.assertEquals("03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4", person.getPasswordHash());
        Assert.assertEquals("1234", person.getPasswordHint());
        Assert.assertEquals(false, person.getLocked());
        Assert.assertTrue(0 == person.getLoginTry());
        Assert.assertTrue(Person.PersonUserType.MITARBEITER == person.getUserType());
        Assert.assertEquals(false, person.getDeleted());
    }

    /**
     * Tested ob das sperren und anmeldeverusch richtig funktionieren.
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
}
