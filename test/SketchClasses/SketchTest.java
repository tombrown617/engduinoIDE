/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SketchClasses;

import ModuleClasses.ModuleController;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tom
 */
public class SketchTest {
    
    public SketchTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getName method, of class Sketch.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        
        String expResult = "SketchName";
        Sketch instance = new Sketch(expResult);
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of setName method, of class Sketch.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String new_name = "";
        Sketch instance = new Sketch("pikachu");
        instance.setName(new_name);
    }

    /**
     * Test of getModuleController method, of class Sketch.
     */
    @Test
    public void testGetModuleController() {
        System.out.println("getModuleController");
        Sketch instance = new Sketch("charizard");
        ModuleController result = instance.getModuleController();
        assertNotNull(result);
    }
    
}
