package tools;


import com.fos.tools.Helper;
import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

/**
 * Testet die Klasse Helper
 */

public class HelperTest {

    /**
     * testet ob die Methode getHash() die richtigen Hashes erstellt
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
}
