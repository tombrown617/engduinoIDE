/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ModuleClasses;

import FlowControlClasses.ANDOperator;
import FlowControlClasses.Accelerometer;
import FlowControlClasses.Module;
import FlowControlClasses.Condition;
import FlowControlClasses.EngButton;
import FlowControlClasses.InfinityLoop;
import FlowControlClasses.Infrared;
import FlowControlClasses.LED;
import FlowControlClasses.LightSensor;
import FlowControlClasses.Loop;
import FlowControlClasses.Magnetometer;
import FlowControlClasses.NOTOperator;
import FlowControlClasses.OROperator;
import FlowControlClasses.Thermistor;
import FlowControlClasses.Constant;
import FlowControlClasses.EqualityOperator;
import FlowControlClasses.Wait;
import FlowControlClasses.XOROperator;
import SketchClasses.Sketch;
import static com.sun.javafx.fxml.expression.Expression.NOT;
import java.util.HashMap;
import javafx.scene.image.Image;

import javafx.scene.layout.AnchorPane;

/**
 *
 * @author shehrozebhatti
 */
public class ModuleController {
    
    private HashMap<String,Module> module_list = new HashMap<String,Module>() ;
    
    private final HashMap<Integer,String> module_types = new HashMap<Integer,String>() ;
    
    private final HashMap<String,String> header_list = new HashMap<String,String>() ;
    
    private MainInputMarker main_input_marker ;
    
    private MainOutputMarker main_output_marker ;
    
    private Sketch main_sketch ;
    
    public ModuleController(){
        
        this.fillModuleTypesList();
        this.fillModuleHeaderList(); 
        
    }
    
    public void setSketch(Sketch sketch){
        this.main_sketch = sketch ;
    }
    
    public Module createModule(String type, int id_num, double x_coordinate, double y_coordinate, AnchorPane sketch){
       
       Module new_module ;
        
       if(type.indexOf("IF..ELSE..") != -1){
           
           new_module = new Condition("cond_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch) ;
           
           
       }else if(type.indexOf("Wait") != -1){
           
           new_module = new Wait("wait_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch) ;
           
       }
       else if(type.indexOf("Infrared") != -1){
           
           new_module = new Infrared("infr_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch) ;
           new_module.setHeader(header_list.get("Infrared"));
       }
       else if(type.indexOf("Loop") != -1){
           
           new_module = new Loop("forl_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch) ;
       }
       else if(type.indexOf("Repeat Forever") != -1){
           
           new_module = new InfinityLoop("inft_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch) ;
       }
       else if(type.indexOf("Magnetometer") != -1){
           
           new_module = new Magnetometer("magn_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch) ;
           new_module.setHeader(header_list.get("Magnetometer"));
       }
       else if(type.indexOf("Accelerometer") != -1){
           
           new_module = new Accelerometer("accl_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch) ;
           new_module.setHeader(header_list.get("Accelerometer"));
       }
       else if(type.indexOf("Light Sensor") != -1){
           
           new_module = new LightSensor("lsen_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch) ;
           new_module.setHeader(header_list.get("Light Sensor"));
       }
       else if(type.indexOf("Thermistor") != -1){
           new_module = new Thermistor("ther_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch) ;
           new_module.setHeader(header_list.get("Thermistor"));
       }
       else if(type.indexOf("Button") != -1){
           new_module = new EngButton("butt_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch) ;
           new_module.setHeader(header_list.get("Button"));
       }
       else if(type.indexOf("Turn All LEDs") != -1){
           
           String color = type.substring(0, 17) ;
           System.out.println("Color = " + color);
           new_module = new LED("leds_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch) ;
           new_module.setHeader(header_list.get("LED"));
       }
       else if(type.indexOf("Blink LED") != -1){
           new_module = new LED("leds_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch) ;
           new_module.setHeader(header_list.get("LED"));
       }
       else if(type.indexOf("Repeat Once") != -1){
           
           new_module = new Loop("once_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch) ;
       }
       else if(type.indexOf("NOT") != -1){
           
           new_module = new NOTOperator("notl_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch) ;
       }
       else if(type.indexOf("XOR") != -1){
           
           new_module = new XOROperator("xorl_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch) ;
       }
       else if(type.indexOf("OR") != -1){
           
           new_module = new OROperator("orlo_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch) ;
       }
       else if(type.indexOf("AND") != -1){
           
           new_module = new ANDOperator("andl_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch) ;
       }
       else if(type.indexOf("Constant") != -1){
           
           new_module = new Constant("cons_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch) ;
       }
       else if(type.indexOf("Greater Than") != -1){
           
           new_module = new EqualityOperator(new Image("graphics/draggables/greater_than.png"), "grea_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch) ;
       }
       else if(type.indexOf("Less Than") != -1){
           
           new_module = new EqualityOperator(new Image("graphics/draggables/less_than.png"), "less_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch) ;
       }
       else if(type.indexOf("Equals") != -1){
           
           new_module = new EqualityOperator(new Image("graphics/draggables/equals.png"), "equa_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch) ;
       }
       else{
           new_module = new Condition("mode_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch) ;
       }
       
       module_list.put(new_module.getModuleID(), new_module) ;
       return new_module ;
       
    }
    
    public void addModule(Module module){
        String type = "" ;
        //this.module_list.put(type, module) ;
    }
    
    public void addModule(Module module, String type){
        
        //this.module_list.put(type, module) ;
    }
    
    public Module getModule(String name){
        return this.module_list.get(name);
    }
    
     private void fillModuleTypesList(){
        
         this.module_types.put(1, "Condition") ;
         this.module_types.put(2, "ForLoop") ;
         this.module_types.put(3, "WhileLoop") ;
         this.module_types.put(4, "Function") ;
         
        
    }
     
    private void fillModuleHeaderList(){
        
        this.header_list.put("LED", "#include <EngduinoLEDs.h>") ;
        this.header_list.put("Button", "#include <EngduinoButton.h>") ;
        this.header_list.put("Accelerometer", "#include <EngduinoAccelerometer.h>") ;
        this.header_list.put("Infrared", "#include <EngduinoIR.h>") ;
        this.header_list.put("Light Sensor", "#include <EngduinoLight.h>") ;
        this.header_list.put("Magnetometer", "#include <EngduinoMagnetometer.h>") ;
        this.header_list.put("Thermistor", "#include <EngduinoThermistor.h>") ;
        
    } 
    
    public void setMainOutputMarker(MainOutputMarker main_output){
        this.main_output_marker = main_output ;
    }
    
    public void setMainInputMarker(MainInputMarker main_input){
        
        this.main_input_marker = main_input ;
    }
    
    public MainOutputMarker getMainOututMarker(){
        return this.main_output_marker ;
    }
    
    public MainInputMarker getMainInputMarker(){
        return this.main_input_marker ;
    }
    
}
