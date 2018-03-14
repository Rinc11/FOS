package database;

import com.fos.database.DbObject;
import com.fos.database.NotLoadedExeption;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tested die DbOject Klasse
 */
public class DbObjectTest {

    /**
     * getValue auf nicht geladenes Objekt sollte eine Exeption werfen
     */
    @Test(expected = NotLoadedExeption.class)
    public void testDbObjectNotLoaded() throws NotLoadedExeption {
        DbObject<Integer> testObject = new DbObject<>();
        Assert.assertEquals(false, testObject.isLoaded());
        Integer testValue = testObject.getValue();
    }

    /**
     * prüft ob der angegebene Wert duch getValue wieder zurück kommt
     */
    @Test
    public void testDbObject() throws NotLoadedExeption {
        DbObject<Integer> testObject = new DbObject<>(5);
        Assert.assertTrue( testObject.isLoaded());
        Integer testValue = testObject.getValue();
        Assert.assertTrue(5 == testValue);
    }

    /**
     * prüft ob man duch die Methode setValue den wert auf ein nicht geladenes Object setzten kann.
     */
    @Test
    public void testSetValueOnNotLoadedObject() throws NotLoadedExeption {
        DbObject<Integer> testObject = new DbObject<>();
        testObject.setValue(5);
        Assert.assertTrue(testObject.isLoaded());
        Assert.assertTrue(5 == testObject.getValue());
    }

    /**
     * prüft ob set Value eine Exeption wirft, wenn das Objekt bereits geladen ist.
     */
    @Test(expected = RuntimeException.class)
    public void testSetValueOnObjectLoaded(){
        DbObject<String> testObject = new DbObject<>("test");
        testObject.setValue("blabla");
    }

    /**
     * prüft ob null zurückkommt auf ein nicht geladenes Objekt
     */
    @Test
    public void testGetValueOrNull(){
        DbObject<String> testObject = new DbObject<>();
        Assert.assertEquals(null, testObject.getValueOrNull());
        testObject.setValue("test");
        Assert.assertTrue(!testObject.isNull());
        Assert.assertEquals("test", testObject.getValueOrNull());
    }

    /**
     * prüft ob null zurückkommt auf ein null Wert
     */
    @Test
    public void testGetValueAufNullWert() throws NotLoadedExeption {
        DbObject<String> testObject = new DbObject<>();
        Assert.assertTrue(testObject.isNull());
        testObject.setValue(null);
        Assert.assertTrue(testObject.isNull());

        Assert.assertEquals(null, testObject.getValue());
        Assert.assertEquals(null, testObject.getValueOrNull());
    }
}
