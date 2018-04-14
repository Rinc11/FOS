package com.fos.tools;

//@toDo hier sollte eine zyklische referenz mit com.fos.database vorliege kann sie aber nicht finden
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.http.HttpServletRequest;

import static com.fos.tools.Helper.nullCheck;
import static com.fos.tools.Helper.nullValue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Testet die Klasse Helper
 */

public class HelperTest {
    private static final String TESTSTRING = "5. Item";
    private static final String TESTSTRING2 = "anderes Item";

    /**
     * updated die Datenbank auf den neusten Stand mit Testdaten
     */
    @BeforeClass
    public static void updateDatabase() throws Exception {
        TestHelper.loadDatabaseUpdates();
    }


    /**
     * testet ob die Methode getHash() die richtigen Hashes erstellt
     * Testhashes voraus berechnet
     */
    @Test
    public void shouldCreateTheCorrectHash() throws NoSuchAlgorithmException {
        Assert.assertEquals(Helper.getHash("1234"), "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4");
        Assert.assertEquals(Helper.getHash("hallo"), "d3751d33f9cd5049c4af2b462735457e4d3baf130bcbb87f389e349fbaeb20b9");
        Assert.assertEquals(Helper.getHash("Tomate"), "96eaefafc3db56ca49863a066b13a6a3c8139d28b49f083673c219bbd81f294e");
        Assert.assertEquals(Helper.getHash("dEtjE4kK2r"), "de500717bb2ef68cc94ccafe8ac05bfdf149d50cbe27dc3c97cfbe0454bacc2c");
    }

    /**
     * testet ob die Methode getHash() bei einem leeren String den richtigen Hash erzeugt
     */
    @Test
    public void shouldCreateTheCorrectHashWithEmptyValue() throws NoSuchAlgorithmException {
        Assert.assertEquals(Helper.getHash(""), "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
    }

    /**
     * testet die Methode getHash() mit NULL
     */
    @Test(expected = NullPointerException.class)
    public void shouldFailToCreateAHashWithNULL() throws NoSuchAlgorithmException {
        Assert.assertEquals(Helper.getHash(null), "");
    }


    /**
     * Tested ob die nullValue Methode funktioniert.
     */
    @Test
    public void testNullValue() {
        String[] array = new String[10];
        array[4] = TESTSTRING;
        Assert.assertEquals(TESTSTRING, array[4]);
        Assert.assertTrue(null == array[1]);
        String nullCheckValue1 = nullValue(() -> array[1], () -> "");
        String nullCheckValue4 = nullValue(() -> array[4], () -> "");
        Assert.assertEquals("", nullCheckValue1);
        Assert.assertEquals(TESTSTRING, nullCheckValue4);
    }

    /**
     * Tested ob die nullCheck Methode funktioniert.
     */
    @Test
    public void testNullCheck() {
        String test = null;
        Integer lengthNull = nullCheck(test, t -> t.length());
        //verhindert den Absturz der null Pointer Exeption
        Assert.assertTrue(null == lengthNull);

        test = TESTSTRING;
        Integer lengthTest = nullCheck(test, t -> t.length());
        Assert.assertTrue(7 == lengthTest);
    }

    /**
     * tested eine nützliche Kombination von nullValue und nullCheck
     */
    @Test
    public void testNullCheckNullValue() {
        String[] array = new String[10];
        array[4] = TESTSTRING;
        Assert.assertTrue(0 == nullValue(() -> nullCheck(array[1], a -> a.length()), () -> 0));
        Assert.assertTrue(7 == nullValue(() -> nullCheck(array[4], a -> a.length()), () -> 0));
    }

    /**
     * tested wenn der erste Fehler hinzugefügt wird.
     */
    @Test
    public void testAddFirstError() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("errorMessage")).thenReturn(null);

        Logging.messageToUser(request, TESTSTRING);
        ArgumentCaptor<List<String>> argument = ArgumentCaptor.forClass(List.class);
        verify(request).setAttribute(eq("errorMessage"), argument.capture());
        Assert.assertTrue(argument.getValue().contains(TESTSTRING));
    }


    /**
     * tested wenn ein weiterer Fehler hinzugefügt wird.
     */
    @Test
    public void testAddSecondError() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        List<String> errorList = new ArrayList<>();
        errorList.add(TESTSTRING);
        when(request.getAttribute("errorMessage")).thenReturn(errorList);
        Logging.messageToUser(request, TESTSTRING2);
        Assert.assertTrue(errorList.contains(TESTSTRING));
        Assert.assertTrue(errorList.contains(TESTSTRING2));
    }

    /**
     * testet die Methode getConnection ob das richtige Schema gesetzt wird.
     */
    @Test
    public void testGetConnection() throws SQLException {
        Assert.assertEquals("fostest", Helper.getConnection().getSchema());
    }
}
