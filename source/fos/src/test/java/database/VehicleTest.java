package database;

import com.fos.database.NotLoadedExeption;
import com.fos.database.Person;
import com.fos.database.Vehicle;
import com.fos.tools.Helper;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Testet ob die Klasse Vehicle sich richtig verhält
 * Benötigt Datenbankseitig den TestFahrzeug Vehicle Datensatz
 */
public class VehicleTest {

    /**
     * tested das testVehicle ob alle Werte so sind wie in der Datenbank.
     */
    @Test
    public void testIfTestVehicleExists() throws SQLException, NotLoadedExeption {
        Connection conn = Helper.getConnection();
        Vehicle vehicle = Vehicle.getVehicle(1234567, conn);

        Assert.assertEquals("1234567", vehicle.getVehicleID());
        Assert.assertEquals("7777777", vehicle.getSerialnumber());
        Assert.assertEquals("Audi", vehicle.getBrand());
        Assert.assertEquals("LKW", vehicle.getType());
        Assert.assertEquals("2000", vehicle.getBuildYear());
        Assert.assertTrue(Vehicle.VehicleFuelType.BENZIN == vehicle.getFuelType());
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
    public void getAllPersons() throws SQLException, NotLoadedExeption {
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
