/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engduino_ide;

import java.util.ArrayList;

/**
 *
 * @author shehrozebhatti
 */
public class Allmodules {
    
    private ArrayList<String> flowControlList = new ArrayList<String>() ; ;
    
    private ArrayList<String> sensorList = new ArrayList<String>() ; ;
    
    private ArrayList<String> LEDs = new ArrayList<String>() ;  ;
    
    
    public void Allmodules(){
        
        fillFlowControlList() ;
        this.fillSensorList(); 
        this.fillLEDsList(); 
    }
    
    private void fillFlowControlList(){
        
        System.out.println("Filling list for flow control") ;
        
        flowControlList.add("IF..ELSE..") ;
        flowControlList.add("Switch") ;
        flowControlList.add("For Loop") ;
        flowControlList.add("Repeat Forever") ;
        flowControlList.add("Repeat Once") ;
        flowControlList.add("Wait") ;
        
        
        
    }
    
    private void fillSensorList(){
    
        this.sensorList.add("Magnetometer X Value") ;
        this.sensorList.add("Magnetometer Y Value") ;
        this.sensorList.add("Magnetometer Z Value") ;
        this.sensorList.add("Infrared") ;
        this.sensorList.add("Accelerometer X Value") ;
        this.sensorList.add("Accelerometer Y Value") ;
        this.sensorList.add("Accelerometer Z Value") ;
        
        this.sensorList.add("Light Sensor Value") ;
        this.sensorList.add("Thermistor Value") ;
        this.sensorList.add("Button") ;
    }
    
    private void fillLEDsList(){
       
        int i = 1 ;
        for(i = 1; i <=16 ;i++){
        
             this.LEDs.add("Blink LED " + i) ;
        }
    }
    
    public ArrayList<String> getFlowControlList(){
    
        
         return this.flowControlList ;
    }
    
    public ArrayList<String> getHardwareList(){
    
         return this.sensorList ;
    }
    
    public ArrayList<String> getLEDsList(){
    
         return this.LEDs  ;
    }
}
