package database;

import com.fos.database.Config;
import com.fos.database.NotLoadedException;
import com.fos.tools.Helper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Testet die Config Klasse
 */
public class ConfigTest {

    /**
     * updated die Datenbank auf den neusten Stand mit Testdaten
     */
    @BeforeClass
    public static void updateDatabase() throws SQLException {
        tools.Helper.loadDatabaseUpdates();
    }

    /**
     * Testet die testConfig ob alle Werte so sind wie in der Datenbank.
     */
    @Test
    public void testIfTestConfigExists() throws SQLException, NotLoadedException {
        Connection conn = Helper.getConnection();
        List<Config> config = Config.getAllConfig(conn);

        Assert.assertTrue(0 <= Integer.parseInt(config.get(0).getValue()));
    }
}
