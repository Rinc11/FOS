package database;

import com.fos.database.NotLoadedExeption;
import com.fos.database.Vehicle;
import com.fos.tools.Helper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Testet ob die Klasse Vehicle sich richtig verhält
 * Benötigt Datenbankseitig den TestFahrzeug Vehicle Datensatz
 */
public class VehicleTest {

    private Integer defaultTestVehicleID = 6;

    /**
     * updated die Datenbank auf den neusten Stand mit Testdaten
     */
    @BeforeClass
    public static void updateDatabase() throws SQLException {
        tools.Helper.loadDatabaseUpdates();
    }


    /**
     * tested das testVehicle ob alle Werte so sind wie in der Datenbank.
     */
    @Test
    public void testIfTestVehicleExists() throws SQLException, NotLoadedExeption {
        Connection conn = Helper.getConnection();
        Vehicle vehicle = Vehicle.getVehicle(defaultTestVehicleID, conn);

        Assert.assertEquals(Integer.valueOf(defaultTestVehicleID), vehicle.getVehicleID());
        Assert.assertEquals("1057", vehicle.getSerialnumber());
        Assert.assertEquals("OPEL", vehicle.getBrand());
        Assert.assertEquals("Astra", vehicle.getType());
        Assert.assertEquals(Integer.valueOf(2014), vehicle.getBuildYear());
        Assert.assertTrue(Vehicle.VehicleFuelType.BENZIN == vehicle.getFuelType());
    }

    /**
     * Tested ob das Fahrzeug mit der VehicleID 6 in der Liste von allen Fahrzeugen erfasst ist.
     */
    @Test
    public void testGetAllVehicles() throws SQLException, NotLoadedExeption {
        Connection conn = Helper.getConnection();
        List<Vehicle> vehicles = Vehicle.getAllVehicles(conn);
        Assert.assertEquals(Integer.valueOf(6), vehicles.stream().filter(f -> {
            try {
                return f.getVehicleID().equals(6);
            } catch (NotLoadedExeption notLoadedExeption) {
                notLoadedExeption.printStackTrace();
            }
            return false;


        }).findAny().get().getVehicleID());
    }


    /**
     * testet, ob ein neues Fahrzeug korrekt in die Datenbank gespeichert wird
     */
    @Test
    public void testAddNewVehicle() throws SQLException, NotLoadedExeption {

        String serialnumber = "136c8b4";
        String brand = "Honda";
        String type = "Civic";
        Integer buildYear = 2010;
        String fuelType = "Benzin";

        Connection conn = Helper.getConnection();
        Vehicle.addNewVehicle(serialnumber, brand, type, buildYear, fuelType, conn);
        Vehicle vehicle = Vehicle.getVehicle(vehicleID, conn);

        Assert.assertEquals(serialnumber, vehicle.getSerialnumber());
        Assert.assertEquals(brand, vehicle.getBrand());
        Assert.assertEquals(type, vehicle.getType());
        Assert.assertEquals(buildYear, vehicle.getBuildYear());
        Assert.assertTrue(Vehicle.VehicleFuelType.BENZIN == vehicle.getFuelType());

        PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM \"Vehicles\"  WHERE \"VehicleID\" = '" + vehicleID + "'");
        preparedStatement.execute();
    }

    /**
     * testet, ob ein bestehendes Fahrzeug gelöscht wird.
     */
    @Test
    public void testRemoveVehicle() throws SQLException, NotLoadedExeption {

        Connection conn = Helper.getConnection();
        Vehicle.removeVehicle("testVehicle", conn);
        Vehicle vehicle = Vehicle.getVehicle("testVehicle", conn);

        Assert.assertEquals(true, vehicle.getDeleted());

        PreparedStatement preparedStatement = conn.prepareStatement("UPDATE \"Vehicles\" SET \"Active_YN\" = FALSE WHERE \"VehicleID\" = 'testVehicle'");
        preparedStatement.execute();
    }

    /**
     * testet, ob ein bestehendes Fahrzeug richtig geupdatet wird
     */
    @Test
    public void testUpdateVehicle() throws SQLException, NotLoadedExeption, NoSuchAlgorithmException {

        String serialnumber = "136c8b4";
        String brand = "Honda";
        String type = "Civic";
        Integer buildYear = 2010;
        String fuelType = "Benzin";

        Connection conn = Helper.getConnection();

        Vehicle.updateVehicle(serialnumber, brand, type, buildYear, fuelType, conn, true);
        Vehicle vehicle = Vehicle.getVehicle("testVehicle", conn);
        Assert.assertEquals(serialnumber, vehicle.getSerialnumber());
        Assert.assertEquals(brand, vehicle.getBrand());
        Assert.assertEquals(type, vehicle.getType());
        Assert.assertEquals(buildYear, vehicle.getBuildYear());
        Assert.assertTrue(Vehicle.VehicleFuelType.BENZIN == vehicle.getFuelType());

        Vehicle.updateVehicle("testVehicle", "136c8b4", "Honda", "Civic", 2010, "Benzin", conn, true);
    }

}