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
        Vehicle vehicle = Vehicle.getVehicle(6, conn);

        Assert.assertEquals("6", vehicle.getVehicleID());
        Assert.assertEquals("1057", vehicle.getSerialnumber());
        Assert.assertEquals("OPEL", vehicle.getBrand());
        Assert.assertEquals("Astra", vehicle.getType());
        Assert.assertEquals("2014", vehicle.getBuildYear());
        Assert.assertTrue(Vehicle.VehicleFuelType.BENZIN == vehicle.getFuelType());
    }

    /**
     * Tested ob das Fahrzeug mit der VehicleID 6 in der Liste von allen Fahrzeugen erfasst ist.
     */
    @Test
    public void getAllVehicles() throws SQLException, NotLoadedExeption {
        Connection conn = Helper.getConnection();
        List<Vehicle> vehicles = Vehicle.getAllVehicles(conn);
        Assert.assertEquals("6", vehicles.stream().filter(f -> {
            try {
                return f.getVehicleID().equals("6");
            } catch (NotLoadedExeption notLoadedExeption) {
                notLoadedExeption.printStackTrace();
            }
            return false;


        }).findAny().get().getVehicleID());
    }
}
