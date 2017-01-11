package com.nostalgi.engine.Tests;

import junit.framework.TestCase;

/**
 * Created by ksdkrol on 2017-01-10.
 */

public class ExampleTest extends TestCase {

    protected int value1, value2;

    // assigning the values
    protected void setUp(){
        value1 = 3;
        value2 = 3;
    }

    // test method to add two values
    public void testAdd(){
        double result = value1 + value2;
        assertTrue(result == 6);
    }

}
