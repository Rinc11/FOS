package database;

import com.fos.database.DbObject;
import com.fos.database.NotLoadedExeption;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tested die DbOject Klasse
 */
public class DbObjectTest {

    private Integer defaultTestInteger = 5;
    private String defaultTestString = "blabla";
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
     * prüft ob ein im DbObject gesetzter Wert
     * durch getValue wieder zurück gegeben wird
     */
    @Test
    public void testDbObject() throws NotLoadedExeption {
        DbObject<Integer> testObject = new DbObject<>(defaultTestInteger);
        Assert.assertTrue( testObject.isLoaded());
        Integer testValue = testObject.getValue();
        Assert.assertTrue(defaultTestInteger == testValue);
    }

    /**
     * prüft ob man durch die Methode setValue den Wert auf ein
     * nicht geladenes Object setzten kann.
     */
    @Test
    public void testSetValueOnNotLoadedObject() throws NotLoadedExeption {
        DbObject<Integer> testObject = new DbObject<>();
        testObject.setValue(defaultTestInteger);
        Assert.assertTrue(testObject.isLoaded());
        Assert.assertTrue(defaultTestInteger == testObject.getValue());
    }

    /**
     * prüft ob setValue eine Exception wirft, wenn das Objekt bereits geladen ist
     * und versucht wird der gleiche Wert dem Objekt erneut zuzuweisen.
     */
    @Test(expected = RuntimeException.class)
    public void testSetValueOnObjectLoaded(){
        DbObject<String> testObject = new DbObject<>(defaultTestString);
        testObject.setValue(defaultTestString);
    }

    /**
     * prüft ob setValue eine Exception wirft, wenn das Objekt bereits einen Value hat
     * und ein neuer Wert versucht wird zu setzten.
     */
    @Test(expected = RuntimeException.class)
    public void testSetValueOnObjectLoadedDifferentValue(){
        DbObject<String> testObject = new DbObject<>(defaultTestString);
        testObject.setValue(defaultTestString + "NewOne");
    }
    /**
     * prüft ob null zurückkommt auf ein nicht geladenes Objekt
     */
    @Test
    public void testGetValueOrNull(){
        DbObject<String> testObject = new DbObject<>();
        Assert.assertEquals(null, testObject.getValueOrNull());
        testObject.setValue(defaultTestString);
        Assert.assertTrue(!testObject.isNull());
        Assert.assertEquals(defaultTestString, testObject.getValueOrNull());
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
