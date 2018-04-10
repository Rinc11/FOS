package database;

import com.fos.database.NotLoadedException;
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
     *
     * @throws SQLException
     */
    @BeforeClass
    public static void updateDatabase() throws Exception {
        tools.Helper.loadDatabaseUpdates();
    }


    /**
     * tested das testVehicle ob alle Werte so sind wie in der Datenbank.
     *
     * @throws SQLException
     * @throws NotLoadedException
     */
    @Test
    public void testIfTestVehicleExists() throws SQLException, NotLoadedException {
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
     *
     * @throws SQLException
     * @throws NotLoadedException
     */
    @Test
    public void testGetAllVehicles() throws SQLException, NotLoadedException {
        Connection conn = Helper.getConnection();
        List<Vehicle> vehicles = Vehicle.getAllVehicles(conn);
        Assert.assertTrue(vehicles.stream().anyMatch(f -> {
            try {
                return f.getBrand().equals("VW");
            } catch (NotLoadedException notLoadedExeption) {
                notLoadedExeption.printStackTrace();
            }
            return false;
        }));
    }


    /**
     * testet, ob ein neues Fahrzeug korrekt in die Datenbank gespeichert wird
     *
     * @throws SQLException
     * @throws NotLoadedException
     */
    @Test
    public void testAddNewVehicle() throws SQLException, NotLoadedException {

        String serialnumber = "136c8b4test";
        String brand = "Honda";
        String type = "Civic";
        Integer buildYear = 2010;
        String fuelType = "Benzin";

        Connection conn = Helper.getConnection();
        Vehicle.addNewVehicle(serialnumber, brand, type, buildYear, fuelType, conn);
        Integer vehicleID = Vehicle.getAllVehicles(conn).stream().filter(f -> {
            try {
                return f.getSerialnumber().equals(serialnumber);
            } catch (NotLoadedException notLoadedException) {
                return false;
            }
        }).findAny().get().getVehicleID();
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
     *
     * @throws SQLException
     * @throws NotLoadedException
     */
    @Test
    public void testRemoveVehicle() throws SQLException, NotLoadedException {

        Connection conn = Helper.getConnection();
        Integer vehicleID = Vehicle.getAllVehicles(conn).stream().filter(f -> {
            try {
                return f.getSerialnumber().equals("1057");
            } catch (NotLoadedException notLoadedException) {
                return false;
            }
        }).findAny().get().getVehicleID();

        Vehicle.removeVehicle(vehicleID, conn);

        Vehicle vehicle = Vehicle.getVehicle(vehicleID, conn);

        Assert.assertEquals(false, vehicle.isActive());

        PreparedStatement preparedStatement = conn.prepareStatement("UPDATE \"Vehicles\" SET \"Active_YN\" = TRUE WHERE \"VehicleID\" = ' " +vehicleID +"  '");
        preparedStatement.execute();
    }

    /**
     * testet, ob ein bestehendes Fahrzeug richtig geupdatet wird
     *
     * @throws SQLException
     * @throws NotLoadedException
     * @throws NoSuchAlgorithmException
     */
    @Test
    public void testUpdateVehicle() throws SQLException, NotLoadedException, NoSuchAlgorithmException {
        int vehicleID   = 2;
        String serialnumber = "136c8b4";
        String brand = "Honda";
        String type = "Civic";
        Integer buildYear = 2010;
        String fuelType = "Benzin";

        Connection conn = Helper.getConnection();

        Vehicle.updateVehicle(vehicleID, serialnumber, brand, type, buildYear, fuelType, conn);
        Vehicle vehicle = Vehicle.getVehicle(vehicleID, conn);

        Assert.assertEquals(serialnumber, vehicle.getSerialnumber());
        Assert.assertEquals(brand, vehicle.getBrand());
        Assert.assertEquals(type, vehicle.getType());
        Assert.assertEquals(buildYear, vehicle.getBuildYear());
        Assert.assertTrue(Vehicle.VehicleFuelType.BENZIN == vehicle.getFuelType());

        Vehicle.updateVehicle(2, "136c8b4", "Honda", "Civic", 2010, "Benzin", conn);
    }

}