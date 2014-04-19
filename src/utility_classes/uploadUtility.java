/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utility_classes;

import SketchClasses.Sketch;
import SketchClasses.SketchController;
import engduino_ide.FXMLDocumentController;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

/**
 *
 * @author shehrozebhatti
 */
public class uploadUtility extends Utility {
    
     //private final FXMLDocumentController doc_controller ;
    
    public uploadUtility(final FXMLDocumentController doc_controller, final Stage stage, final SketchController sketch_controller){
        
        super("Verify", doc_controller, sketch_controller) ;
        //   IF..ELSE..   For Loop
        doc_controller.getUploadButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
       
            public void handle(MouseEvent mouseEvent) {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                        if(mouseEvent.getClickCount() == 2){
                            //System.out.println("Double clicked");    Turn All LEDs OFF
                        }
                        else{
                            
                           Sketch sketch ;
                           
                           for(int i = 0; i < doc_controller.getAllTabs().size(); i++){
                               if(doc_controller.getAllTabs().get(i).isSelected()){
                                   try {
                                       sketch = sketch_controller.getSketch(doc_controller.getAllTabs().get(i).getId()) ;
                                       
                                       // this get all the code for the current open sketch so add stuff to this
                                       String code = sketch.getCodeViewController().getCode(true) ;
                                       //List headerList = sketch.getCodeViewController().getHeadersList();
                                       
                                       
                                       
                                       File codeFile = File.createTempFile("sketch", ".c");
                                       try (BufferedWriter codeWriter = new BufferedWriter (new FileWriter(codeFile))) {
                                           codeWriter.write(code);
                                       }
                                       
                                       break;
                                   } catch (IOException ex) {
                                       Logger.getLogger(uploadUtility.class.getName()).log(Level.SEVERE, null, ex);
                                   }
                               }
                           }
                           
                           
                            
                        }
                    }
                }
            });
        
        
    }
    
    
    
    public boolean uploadToEngduino (String portName, File hexFile){
        
              
        List<String> initialPorts = Arrays.asList(SerialPortList.getPortNames());        
        touchPort(portName, 1200);
        String uploadPort;
        try{
            Thread.sleep(300);
            uploadPort = getUploadPort(initialPorts, portName);
        }
        catch(InterruptedException e){
            e.printStackTrace();
            uploadPort = portName;
        }        
        
        
        
        String engduinoPath = "";
        try {
            engduinoPath = getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        } catch (URISyntaxException ex) {
            Logger.getLogger(uploadUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        engduinoPath = engduinoPath.substring(0, engduinoPath.lastIndexOf("dist/engduino_IDE.jar"));
        String avrdudePath = engduinoPath + "utils/avrdude";
        String avrdudeBinaryPath;
        String osName = System.getProperty("os.name");
        
        if(osName.equals("Linux")){
            avrdudePath = avrdudePath + "/linux";
            String osArch = System.getProperty("os.arch");
            if(osArch.equals("x86")||osArch.equals("i686")||osArch.equals("i386")){
                avrdudePath = avrdudePath + "/x86";
            } 
            else if (osArch.equals("x86_64")||osArch.equals("amd64")){
                avrdudePath = avrdudePath + "/x86_64";
            }
            else if (osArch.equals("armhf")){
                avrdudePath = avrdudePath + "/armhf";
            }
            else if (osArch.equals("armsf")){
                avrdudePath = avrdudePath + "/armsf";
            }
            else {
                //System.out.println("Linux OS Architecture not supported");
                return false;
            }
            avrdudeBinaryPath = avrdudePath + "/avrdude";
            
        }
        else if (osName.equals("Mac OS X")){
            avrdudePath = avrdudePath + "/osx";
            avrdudeBinaryPath = avrdudePath + "/avrdude";
            
        }
        else if(isWindows()){
            avrdudePath = avrdudePath + "/windows";
            avrdudeBinaryPath = avrdudePath + "/avrdude.exe";
        }
        else {
                //System.out.println("OS not supported");
                return false;
            }
        
        File avrdude = new File(avrdudeBinaryPath);
        
        //Build command to be executed
        String[] commands = new String[8];
        commands[0] = avrdude.getAbsolutePath();
        commands[1] = "-patmega32u4";
        commands[2] = "-cavr109";
        commands[3] = "-P" + uploadPort;
        commands[4] = "-b57600";
        commands[5] = "-D";
        commands[6] = "-Uflash:w:" + hexFile.getAbsolutePath() + ":i";
        commands[7] = "-C"+ avrdudePath + "/avrdude.conf";
        Process process;
        try {
            ProcessBuilder pb = new ProcessBuilder(commands);
            //pb.redirectOutput(Redirect.INHERIT);
            pb.redirectError(Redirect.INHERIT);
            //System.out.println(pb.command().toString());
            process = pb.start();
            
            int result = process.waitFor();
        }  
        catch (IOException ex) {
            Logger.getLogger(uploadUtility.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (InterruptedException ex) {
            Logger.getLogger(uploadUtility.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
                
        return true;
    }
    
    private void touchPort(String portName, int baudRate){
        
        SerialPort serialPort = new SerialPort(portName);
        try{
            serialPort.openPort();
            serialPort.setParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            serialPort.closePort();            
        }
        catch (SerialPortException e){
            e.printStackTrace();
        }
        finally {
            if(serialPort.isOpened()){
                try {
                    serialPort.closePort();
                }
                catch (SerialPortException e){
                    e.printStackTrace();
                }
            }
        }
        
    }
    
    private String getUploadPort(List initialPorts, String sketchPort) throws InterruptedException{
        
        
        //The serial port used by the Engduino changes between when the bootloader is reset
        //using the 1200bps touch. This method monitors the serial ports as the bootloader
        //serial port replaces the sketch serial port and connects to the host.
        
        
        int timeElapsed = 0;
        //Check the list of serial ports to monitor change until success/timeout
        while (timeElapsed < 10000){
            List<String> currentPorts = Arrays.asList(SerialPortList.getPortNames());
            List<String> differentPorts = new ArrayList<>(currentPorts);
            differentPorts.removeAll(initialPorts);
            
            if(differentPorts.size() > 0){
                System.out.println("Port change: " + differentPorts.get(0));
                return differentPorts.get(0);
            }
            
            initialPorts = currentPorts;
            Thread.sleep(250);
            timeElapsed = timeElapsed + 250;
            
            //Timeout and return original port used for sketch.
            //Windows often requires a longer time to reconnect.
            if((!isWindows() && timeElapsed > 500) || timeElapsed > 5000){
                return sketchPort;
            }
        }      
        return sketchPort;
        
    }
    
    private boolean isWindows(){
        
        return System.getProperty("os.name").indexOf("Windows") != -1;   
                
    }
    
}
