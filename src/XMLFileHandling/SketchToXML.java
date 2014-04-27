/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package XMLFileHandling;

import FlowControlClasses.Constant;
import FlowControlClasses.CustomSketchModule;
import FlowControlClasses.Module;
import SketchClasses.Sketch;
import java.util.ArrayList;
import javafx.scene.control.Dialogs;
import javafx.stage.Stage;

/**
 *
 * @author shehrozebhatti
 */
public class SketchToXML {
    
    private Sketch sketch ;
    
    private Stage stage ;
    
    private ArrayList<String> module_IDs_added ;
    
    public SketchToXML(Stage stage){
        this.stage = stage ;
        this.module_IDs_added = new ArrayList<String>() ;
    }
    
    
    public String getXML(Sketch sketch,String type){
        this.sketch = sketch ;
        String output = generateCodeForSketch(type) ;
        return output;
        
    }
    
    
    
    public String generateCodeForSketch(String type){
        
        boolean full = stage.isFullScreen() ;
        
        return "<" + type.toLowerCase()+ " name = '"+ this.sketch.getName() +"' full = '"+full+"'>" + this.generateCodeForConnections() + "</"+type.toLowerCase()+">" ;
    }
    
    public String generateCodeForConnections(){
        
        String output = "<connections>" ;
        
        if(sketch.getModuleConnectionController().getTotalModulesConnectedWithMain() == 1){
            output += generateCodeForModule(sketch.getModuleConnectionController().getModulesConnectedWithMain().get(0),null,0) ;
        }
        else if(sketch.getModuleConnectionController().getTotalModulesConnectedWithMain() == 2){
            output += generateCodeForModule(sketch.getModuleConnectionController().getModulesConnectedWithMain().get(0),null,0) ;
            output += generateCodeForModule(sketch.getModuleConnectionController().getModulesConnectedWithMain().get(1),null,0) ;
        }
        else{
            Dialogs.showErrorDialog(stage, "Cannot connect less than one or more than 2 modules to the Main Input Marker !", "Module Connection Error ", "Error") ;
            return null ;
        }
        
        output +=  "</connections>" ;
        output += moduleOutsideConnection() ;
        return output ;
    }
    
    private String moduleOutsideConnection(){
        
        String output = "<isolated>" ;
        String properties = "" ;
        ArrayList<Module> modules = this.sketch.getModuleController().getModuleList() ;
        
        for(int i = 0; i < modules.size(); i++){
            if(!this.module_IDs_added.contains(modules.get(i).getModuleID())){
                Module module = modules.get(i) ;
                
                if(module.getModuleType().equals("Constant") || module.getModuleType().equals("Wait") || module.getModuleType().equals("Blink LED")){
                    properties = "value = '" + module.getContent() + "'" ;
                }
                
                output += "<module "+ properties + " id = '"+module.getModuleID()+"' x = '"+module.getAnchor().getX()+"' y = '"+ module.getAnchor().getY()+"' type = '"+module.getModuleType()+"'></module>" ;
            }
        }
        output += "</isolated>" ;
        
        return output ;
    }
    
    public String generateCodeForModule(Module module,Module previous_module, int port){
        
        this.module_IDs_added.add(module.getModuleID()) ;
        
        String connection_from = "" ;    
            
            if(port == 0){
                connection_from = "main" ;
            }
            else{
                
                try{
                    connection_from = previous_module.getModuleID() ;
                }
                catch(NullPointerException e){
                    
                }
                
            }
        
        if(module.getAnchor().getTotalOutputMarkers() == 1){
            
            String properties = "" ;
            
            
            
            if(module.getModuleType().equals("Constant") || module.getModuleType().equals("Wait") || module.getModuleType().equals("Blink LED")){
                properties = "value = '" + module.getContent() + "'" ;
            }
            
            Module mod = sketch.getModuleConnectionController().getConnectedModule(module, 1) ;
            
            if(mod == null){
                
                if(module.getModuleType().equals("custom_module")){
                    
                    CustomSketchModule custom_sketch_module = (CustomSketchModule) module ;
                    return "<port number = '"+ port+"'><module connection_from_port = '"+sketch.getModuleConnectionController().getInputPort(previous_module, module) +"' " + properties + " module_id = '"+module.getModuleID() +"' connection_from = '"+ connection_from+ "' x = '"+module.getAnchor().getX()+"' y = '"+ module.getAnchor().getY()+"' type = '"+module.getModuleType()+"' file_path = '" + custom_sketch_module.getSketch().getSketchFile().getAbsolutePath() +"'></module></port>" ;
                
                }
                else{
                    return "<port number = '"+ port+"'><module connection_from_port = '"+sketch.getModuleConnectionController().getInputPort(previous_module, module) +"' " + properties + " module_id = '"+module.getModuleID() +"' connection_from = '"+ connection_from+ "' x = '"+module.getAnchor().getX()+"' y = '"+ module.getAnchor().getY()+"' type = '"+module.getModuleType()+"'></module></port>" ;
                }
            }
            else{
                
                if(module.getModuleType().equals("custom_module")){
                    
                    CustomSketchModule custom_sketch_module = (CustomSketchModule) module ;
                    return "<port number = '"+ port+"'><module connection_from_port = '"+sketch.getModuleConnectionController().getInputPort(previous_module, module) +"' "+ properties + " module_id = '"+module.getModuleID() +"' connection_from = '"+ connection_from+"' x = '"+module.getAnchor().getX()+"' y = '"+ module.getAnchor().getY()+"' type = '"+module.getModuleType()+"' file_path = '"+custom_sketch_module.getSketch().getSketchFile().getAbsolutePath()+"'>"+ generateCodeForModule(sketch.getModuleConnectionController().getConnectedModule(module, 1),module,1) +"</module></port>" ;
            
                }
                else{
                    return "<port number = '"+ port+"'><module connection_from_port = '"+sketch.getModuleConnectionController().getInputPort(previous_module, module) +"' "+ properties + " module_id = '"+module.getModuleID() +"' connection_from = '"+ connection_from+"' x = '"+module.getAnchor().getX()+"' y = '"+ module.getAnchor().getY()+"' type = '"+module.getModuleType()+"'>"+ generateCodeForModule(sketch.getModuleConnectionController().getConnectedModule(module, 1),module,1) +"</module></port>" ;
            
                }
                
            }
            
        }
        else if(module.getAnchor().getTotalOutputMarkers() == 2){
            
            Module mod1 = sketch.getModuleConnectionController().getConnectedModule(module, 1) ;
            Module mod2 = sketch.getModuleConnectionController().getConnectedModule(module, 2) ;
            
            if(mod1 != null && mod2 != null && port == 0){
                return "<module connection_from_port = '"+sketch.getModuleConnectionController().getInputPort(previous_module, module) +"' module_id = '"+module.getModuleID() +"' connection_from = 'main' x = '"+module.getAnchor().getX()+"' y = '"+ module.getAnchor().getY()+"' type = '"+module.getModuleType()+"'>"+  generateCodeForModule(sketch.getModuleConnectionController().getConnectedModule(module, 1),module,1) + generateCodeForModule(sketch.getModuleConnectionController().getConnectedModule(module, 2),module,2)  +"</module>" ;
            
            }
            else if(mod1 != null && mod2 != null && port != 0){
                return "<port number = '"+ port+"'><module connection_from_port = '"+sketch.getModuleConnectionController().getInputPort(previous_module, module) +"' module_id = '"+module.getModuleID() +"' connection_from = '"+connection_from+"' x = '"+module.getAnchor().getX()+"' y = '"+ module.getAnchor().getY()+"' type = '"+module.getModuleType()+"'>"+  generateCodeForModule(sketch.getModuleConnectionController().getConnectedModule(module, 1),module,1) + generateCodeForModule(sketch.getModuleConnectionController().getConnectedModule(module, 2),module,2)  +"</module></port>" ;
            
            }
            
        }
        else if(module.getAnchor().getTotalOutputMarkers() == 3){
            
            Module mod1 = sketch.getModuleConnectionController().getConnectedModule(module, 1) ;
            Module mod2 = sketch.getModuleConnectionController().getConnectedModule(module, 2) ;
            Module mod3 = sketch.getModuleConnectionController().getConnectedModule(module, 3) ;
            
            if(mod1 != null && mod2 != null && mod3 != null && port != 0){
                return "<port number = '"+ port+"'><module connection_from_port = '"+sketch.getModuleConnectionController().getInputPort(previous_module, module) +"' module_id = '"+module.getModuleID() +"' connection_from = '"+connection_from+"' x = '"+module.getAnchor().getX()+"' y = '"+ module.getAnchor().getY()+"' type = '"+module.getModuleType()+"'>"+  generateCodeForModule(sketch.getModuleConnectionController().getConnectedModule(module, 1),module,1) + generateCodeForModule(sketch.getModuleConnectionController().getConnectedModule(module, 2),module,2) + generateCodeForModule(sketch.getModuleConnectionController().getConnectedModule(module, 3),module,3)  +"</module></port>" ;
            }
        }
        
        return null ;
    }
    
    public String generateCodeForPort(){
        return null ;
    }
    
    
    
}
