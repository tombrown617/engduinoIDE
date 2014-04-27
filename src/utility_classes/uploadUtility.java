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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.control.Dialogs;
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
    private File hexOutput;
    
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
                                       Path buildDirectory = null;
                                       try {
                                            //Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
                                           //add owners permission                                           
                                           buildDirectory = Files.createTempDirectory("engduinoBuild");
                                           File dir = buildDirectory.toFile();
                                           dir.setWritable(true, false);
                                       } catch (IOException ex) {
                                           Logger.getLogger(uploadUtility.class.getName()).log(Level.SEVERE, null, ex);
                                       }
                                       String code = sketch.getCodeViewController().getCode(true,false);
                                       code = "#include <Arduino.h>\n" + code + "\nint main(void) {\ninit();\nsetup();\nwhile(true){\nloop();\n}\n}";
                                       List headerList = sketch.getCodeViewController().getHeadersList();
                                       File codeFile = new File(buildDirectory.toString() + "/main.cpp");
                                       try (BufferedWriter codeWriter = new BufferedWriter (new FileWriter(codeFile))) {
                                           codeWriter.write(code);
                                       }
                                       if(compile(headerList, codeFile, buildDirectory)){
                                           List<String> serialList = Arrays.asList(SerialPortList.getPortNames());
                                           for(String s : serialList){
                                               System.out.println(s);
                                               if(s.contains("/dev/tty.usbmodem")||s.contains("/dev/ttyACM")||(s.contains("COM")&&isWindows())){                                                  
                                                   
                                                   if(!uploadToEngduino(s,hexOutput)){
                                                       Dialogs.showErrorDialog(stage, "Upload error", "Upload to Engduino on port " + s + " failed.", "Error!");
                                                   }
                                               }
                                           }
                                       }
                                       else{                                                                                   
                                            Dialogs.showErrorDialog(stage, "Compilation failed.", "Compiler error", "Error!");                                       
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
    
    
    
    
    public boolean compile (List<String> includes, File sketchCode, Path buildDirectory){
        
        String engduinoPath = "";
        try {
            engduinoPath = getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        } catch (URISyntaxException ex) {
            Logger.getLogger(uploadUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(engduinoPath);
        engduinoPath = engduinoPath.substring(0, engduinoPath.lastIndexOf("dist/"));
        String compilerPath = engduinoPath + "utils/avrcompilers";
        String osName = System.getProperty("os.name");
        String gccPath = "";
        String gppPath = "";
        String objcopyPath = "";
        String libraryPath = engduinoPath + "libraries/";
        String headerPath = engduinoPath + "libraries/headers";
        
        
        
        if(osName.equals("Linux")){
            compilerPath = compilerPath + "/linux";
            String osArch = System.getProperty("os.arch");
            if(osArch.equals("x86")||osArch.equals("i686")||osArch.equals("i386")){
                compilerPath = compilerPath + "/x86";
            } 
            else if (osArch.equals("x86_64")||osArch.equals("amd64")){
                compilerPath = compilerPath + "/x86_64";
                
            }
            /*else if (osArch.equals("armhf")){
                compilerPath = compilerPath + "/armhf";
            }
            else if (osArch.equals("armsf")){
                compilerPath = compilerPath + "/armsf";
            }*/
            else {
                //System.out.println("Linux OS Architecture not supported");
                return false;
            }            
            gccPath = compilerPath + "/bin/avr-gcc";
            gppPath = compilerPath + "/bin/avr-g++";
            objcopyPath = compilerPath + "/bin/avr-objcopy";
        }
        else if (osName.equals("Mac OS X")){
            compilerPath = compilerPath + "/osx";
            gccPath = compilerPath + "/bin/avr-gcc";
            gppPath = compilerPath + "/bin/avr-g++";
            objcopyPath = compilerPath + "/bin/avr-objcopy";
           
            
        }
        else if(isWindows()){
            compilerPath = compilerPath + "/windows";
            gccPath = compilerPath + "/bin/avr-gcc.exe";
            gppPath = compilerPath + "/bin/avr-g++.exe";
            objcopyPath = compilerPath + "/bin/avr-objcopy.exe";
       
        }
        else {
                //System.out.println("OS not supported");
                return false;
            }
        
        //Compile user code to object file with g++
        File gpp = new File(gppPath);
        String[] gppCommands = new String[15];
        gppCommands[0] = gpp.getAbsolutePath();
        gppCommands[1] = "-c";
        gppCommands[2] = "-g";
        gppCommands[3] = "-Wall";
        gppCommands[4] = "-fno-exceptions";
        gppCommands[5] = "-ffunction-sections";
        gppCommands[6] = "-fdata-sections";
        gppCommands[7] = "-mmcu=atmega32u4";
        gppCommands[8] = "-DF_CPU=8000000L";
        gppCommands[9] = "-DUSB_VID=0x1B4F";
        gppCommands[10] = "-DUSB_PID=0x9208";
        gppCommands[11] = "-DARDUINO=105";
        gppCommands[12] = "-I" + headerPath;
        gppCommands[13] = sketchCode.getAbsolutePath();
        gppCommands[14] = "-o" + buildDirectory.toString() + "/build.cpp.o";
        Process gppProcess;
        File sketchObject = null;
        try {
            ProcessBuilder gppBuilder = new ProcessBuilder(gppCommands);
            gppBuilder.redirectOutput(Redirect.INHERIT);
            gppBuilder.redirectError(Redirect.INHERIT);
            System.out.println(gppBuilder.command().toString());
            gppProcess = gppBuilder.start();            
            gppProcess.waitFor();
            sketchObject = new File(buildDirectory.toString() + "/build.cpp.o");
        } catch (InterruptedException | IOException ex) {
            Logger.getLogger(uploadUtility.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        if(!sketchObject.exists()){
            return false;
        }
        
        //Link objects together
        File gcc = new File(gccPath);
        
        String irLib = libraryPath + "engduino/EngduinoIR.cpp.o";
        String buttonLib = libraryPath + "engduino/EngduinoButton.cpp.o";
        String accelerometerLib = libraryPath +"engduino/EngduinoAccelerometer.cpp.o";
        String ledLib = libraryPath + "engduino/EngduinoLEDs.cpp.o";
        String lightLib = libraryPath + "engduino/EngduinoLight.cpp.o";
        String magLib = libraryPath + "engduino/EngduinoMagnetometer.cpp.o";
        String thermistorLib = libraryPath + "engduino/EngduinoThermistor.cpp.o";
        
        ArrayList<String> gccCommandList = new ArrayList();
        
        gccCommandList.add(gcc.getAbsolutePath());
        gccCommandList.add("-Os");
        gccCommandList.add("-Wl,--gc-sections");
        gccCommandList.add("-mmcu=atmega32u4");
        gccCommandList.add("-o" + buildDirectory.toString() + "/build.cpp.elf");
        gccCommandList.add(sketchObject.getAbsolutePath());
        File arduinoFolder = new File(libraryPath + "arduino");
        File[] arduinoLibraries = arduinoFolder.listFiles();
        for(int i = 0; i<arduinoLibraries.length; i++){
            gccCommandList.add(arduinoLibraries[i].getAbsolutePath());
        }
        for(String include : includes){
            if(include.contains("EngduinoLEDs.h") && !gccCommandList.contains(ledLib)){
                gccCommandList.add(ledLib);
            }
            if(include.contains("EngduinoAccelerometer.h") && !gccCommandList.contains(accelerometerLib)){
                gccCommandList.add(accelerometerLib);
            }
            if(include.contains("EngduinoButton.h") && !gccCommandList.contains(accelerometerLib)){
                gccCommandList.add(buttonLib);
            }
            if(include.contains("EngduinoIR.h") && !gccCommandList.contains(irLib)){
                gccCommandList.add(irLib);
            }
            if(include.contains("EngduinoLight.h") && !gccCommandList.contains(lightLib)){
                gccCommandList.add(lightLib);
            }
            if(include.contains("EngduinoMagnetometer.h") && !gccCommandList.contains(magLib)){
                gccCommandList.add(magLib);
            }
            if(include.contains("EngduinoThermistor.h") && !gccCommandList.contains(thermistorLib)){
                gccCommandList.add(thermistorLib);
            }
        }
        gccCommandList.add("-lm");
        gccCommandList.add("-L" + engduinoPath);
        Process gccProcess;
        File elfFile = null;
        ProcessBuilder gccBuilder = new ProcessBuilder(gccCommandList);
        try {
            gccBuilder.redirectOutput(Redirect.INHERIT);
            gccBuilder.redirectError(Redirect.INHERIT);
            System.out.println(gccBuilder.command().toString());
            gccProcess = gccBuilder.start();            
            gccProcess.waitFor();
            elfFile = new File(buildDirectory.toString() + "/build.cpp.elf");
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(uploadUtility.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        if(!elfFile.exists()){
            return false;
        }
        
        //Build the hex file for the Engduino
        File objcopy = new File (objcopyPath);
        String[] objcopyCommands = new String[5];
        objcopyCommands[0] = objcopy.getAbsolutePath();
        objcopyCommands[1] = "-Oihex";
        objcopyCommands[2] = "-R .eeprom";
        objcopyCommands[3] = elfFile.getAbsolutePath();
        objcopyCommands[4] = buildDirectory.toString() + "/build.hex";
        Process objcopyProcess;
        try {
            ProcessBuilder objcopyBuilder = new ProcessBuilder(objcopyCommands);
            objcopyBuilder.redirectOutput(Redirect.INHERIT);
            objcopyBuilder.redirectError(Redirect.INHERIT);
            System.out.println(objcopyBuilder.command().toString());
            objcopyProcess = objcopyBuilder.start();            
            objcopyProcess.waitFor();
            File hexFile = new File(buildDirectory.toString() + "/build.hex");
            hexOutput = hexFile;
        } 
        catch (IOException | InterruptedException ex) {
            Logger.getLogger(uploadUtility.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }            
              
       
        return true;
    }
    
    public File getHexOutput(){
        return hexOutput;
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
        engduinoPath = engduinoPath.substring(0, engduinoPath.lastIndexOf("dist/"));
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
                //System.out.println("Port change: " + differentPorts.get(0));
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
