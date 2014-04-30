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
import FlowControlClasses.Infrared;
import FlowControlClasses.LED;
import FlowControlClasses.LightSensor;
import FlowControlClasses.Loop;
import FlowControlClasses.Magnetometer;
import FlowControlClasses.NOTOperator;
import FlowControlClasses.OROperator;
import FlowControlClasses.Thermistor;
import FlowControlClasses.Constant;
import FlowControlClasses.CustomSketchModule;
import FlowControlClasses.EqualityOperator;
import FlowControlClasses.Print;
import FlowControlClasses.Wait;
import FlowControlClasses.WhileLoop;
import SketchClasses.Sketch;
import static com.sun.javafx.fxml.expression.Expression.NOT;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javafx.scene.image.Image;

import javafx.scene.layout.AnchorPane;


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
           
           new_module = new Condition("cond_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch,type.trim()) ;
           
           
       }else if(type.indexOf("Wait") != -1){
           
           if(type.indexOf("Wait for Button") != -1){
                 new_module = new EngButton("butt_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch,false, type.trim()) ;
           }
           else{
               new_module = new Wait("wait_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch,type.trim()) ;
           }
           
           
       }
       else if(type.indexOf("Infrared") != -1){
           
           new_module = new Infrared("infr_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch,type.trim()) ;
           new_module.setHeader(header_list.get("Infrared"));
       }
       else if(type.indexOf("For Loop") != -1){
           
           new_module = new Loop("forl_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch,type.trim()) ;
       }
       else if(type.indexOf("Magnetometer") != -1){
           
           String data = type.trim() ;
           String value = data.substring(19) ;
           //System.out.println("Mag = " + value);
           
           new_module = new Magnetometer("magn_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch,value,type.trim()) ;
           new_module.setHeader(header_list.get("Magnetometer"));
       }
       else if(type.indexOf("Accelerometer") != -1){
           
           String data = type.trim() ;
           String value = data.substring(20) ;
           new_module = new Accelerometer("accl_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch, value,type.trim()) ;
           new_module.setHeader(header_list.get("Accelerometer"));
       }
       else if(type.indexOf("Light Sensor") != -1){
           
           new_module = new LightSensor("lsen_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch,type.trim()) ;
           new_module.setHeader(header_list.get("Light Sensor"));
       }
       else if(type.indexOf("Temperature") != -1){
           
           String data = type.trim() ;
           String value = data.substring(21) ;
           new_module = new Thermistor("ther_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch, value,type.trim()) ;
           new_module.setHeader(header_list.get("Thermistor"));
       }
       else if(type.indexOf("Button") != -1){
           new_module = new EngButton("butt_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch,false,type.trim()) ;
           new_module.setHeader(header_list.get("Button"));
       }
       else if(type.indexOf("Turn All LEDs") != -1){
        
           String data = type.trim() ;
           String color = data.substring(14);
           
           new_module = new LED("leds_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch, color,type.trim()) ;
           new_module.setHeader(header_list.get("LED"));
       }
       else if(type.indexOf("Blink LED") != -1){
           
           new_module = new LED("ledi_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch,0,"GREEN",type.trim()) ;
           new_module.setHeader(header_list.get("LED"));
       }
       else if(type.indexOf("While Loop") != -1){
           
           new_module = new WhileLoop("whil_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch,type.trim()) ;
       }
       else if(type.indexOf("NOT") != -1){
           
           new_module = new NOTOperator("notl_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch,type.trim()) ;
       }
       else if(type.indexOf("OR") != -1){
           
           new_module = new OROperator("orlo_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch,type.trim()) ;
       }
       else if(type.indexOf("AND") != -1){
           
           new_module = new ANDOperator("andl_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch,type.trim()) ;
       }
       else if(type.indexOf("Constant") != -1){
           
           new_module = new Constant("cons_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch,type.trim()) ;
       }
       else if(type.indexOf("Greater Than Equals") != -1){
           
           new_module = new EqualityOperator(new Image("graphics/draggables/greater_than_equals.png"), "equa_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch, ">", type.trim()) ;
       }
       else if(type.indexOf("Greater Than") != -1){
           
           new_module = new EqualityOperator(new Image("graphics/draggables/greater_than.png"), "equa_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch, ">", type.trim()) ;
       }
       else if(type.indexOf("Less Than Equals") != -1){
           
           new_module = new EqualityOperator(new Image("graphics/draggables/less_than_equals.png"), "equa_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch, "<",type.trim()) ;
       }
       else if(type.indexOf("Less Than") != -1){
           
           new_module = new EqualityOperator(new Image("graphics/draggables/less_than.png"), "equa_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch, "<",type.trim()) ;
       }
       else if(type.indexOf("Equals") != -1){
           
           new_module = new EqualityOperator(new Image("graphics/draggables/equals.png"), "equa_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch, "==",type.trim()) ;
       }
       else if(type.indexOf("Print") != -1){
           
           new_module = new Print( "equa_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch,type.trim()) ;
       }
       else{
           
           //System.out.println("The module type is " + type);
           new_module = new CustomSketchModule("cust_module_" + id_num, x_coordinate, y_coordinate,sketch, this.main_sketch,type.trim()) ;
       }
       
       module_list.put(new_module.getModuleID(), new_module) ;
       return new_module ;
       
    }
    
    public ArrayList<Module> getModuleList(){
        return new ArrayList<Module>(this.module_list.values()) ;
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
        this.header_list.put("Magnetometer", "#include <EngduinoMagnetometer.h>\n#include <Wire.h> ") ;
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
    
    public int getTotalModuleOfType(String type){
        
        int output = 0 ;
        ArrayList<Module> modules = new ArrayList<Module>(this.module_list.values()) ;
        
        for(int i = 0; i < modules.size(); i++){
            if(modules.get(i).getModuleType().equals(type)){
                output += 1 ;
            }
        }
        
        return output ;
        
    }
    
    public Module getLastModuleOfType(String type){
        
        
        ArrayList<Module> modules = new ArrayList<Module>(this.module_list.values()) ;
        
        for(int i = modules.size() - 1; i >= 0; i--){
            if(modules.get(i).getModuleType().equals(type)){
                return modules.get(i) ;
            }
        }
        
        return null ;
        
    }
    
    public void removeModule(String id){
     
         ArrayList<Module> modules = new ArrayList<Module>(this.module_list.values()) ;
        
        for(int i = 0 ; i < modules.size(); i++){
            if(modules.get(i).getModuleID().equals(id)){
                this.module_list.remove(modules.get(i).getModuleID()) ;
            }
        }
        
    }
    
   
    
}
