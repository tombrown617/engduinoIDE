/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ModuleClasses;

import javafx.scene.layout.AnchorPane;
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
public class ModuleControllerTest {
    
    public ModuleControllerTest() {
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
     * Test of addModule method, of class ModuleController.
     */
    @Test
    public void testAddModule_Module() {
        System.out.println("addModule");
        Module module = null;
        ModuleController instance = new ModuleController();
        instance.addModule(module);
    }

    /**
     * Test of addModule method, of class ModuleController.
     */
    @Test
    public void testAddModule_Module_String() {
        System.out.println("addModule");
        Module module = null;
        String type = "";
        ModuleController instance = new ModuleController();
        instance.addModule(module, type);
    }

    /**
     * Test of getModule method, of class ModuleController.
     */
    @Test
    public void testGetModule() {
        System.out.println("getModule");
        String name = "";
        ModuleController instance = new ModuleController();
        instance.getModule(name);
    }
    
}
