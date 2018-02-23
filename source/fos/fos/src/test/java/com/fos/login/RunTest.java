package com.fos.login;

import org.junit.Test;

import static org.junit.Assert.*;

public class RunTest {

    @Test
    public void RunTest(){
        Login lo = new Login();
        assertTrue(lo.aTest() == "");
    }
}
