/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SketchClasses;

import CodeView.CodeViewController;
import FlowControlClasses.Module;
import ModuleClasses.ModuleConnectionController;
import ModuleClasses.ModuleController;
import engduino_ide.FXMLDocumentController;
import java.io.File;
import java.util.prefs.Preferences;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Sketch {
    
    private String name ;
    
    private ModuleController module_controller ;
    
    private ModuleConnectionController module_connection_controller ;
    
    private CodeViewController codeViewController ;
    
    private AnchorPane sketch_pane ;
    
    private FXMLDocumentController main_gui_controller ;
    
    private Module selectedModule = null ;
    
    private File sketchFile = null ;
    
    private Tab sketch_tab ;
    
    private boolean isSketchCustom = false ;
    
    public Sketch(String name){
        
        
        this.name = name ;
        ModuleController new_module_controller = new ModuleController() ;
        this.module_controller = new_module_controller ;
        this.module_controller.setSketch(this);
       
        this.module_connection_controller = new ModuleConnectionController() ;
        this.codeViewController = new CodeViewController(this) ;
    }
    
     public Sketch(String name, FXMLDocumentController doc_controller){
        
        
        this.name = name ;
        ModuleController new_module_controller = new ModuleController() ;
        this.module_controller = new_module_controller ;
        this.module_controller.setSketch(this);
       
        this.module_connection_controller = new ModuleConnectionController() ;
        this.codeViewController = new CodeViewController(this) ;
        this.main_gui_controller = doc_controller ;
        
    }
    
   
    
    public String getName(){
        return this.name ;
    }
    
    public void setName(String new_name){
        this.name = new_name ;
        this.main_gui_controller.getActiveSketchTab().setText(new_name);
    }
    
    public ModuleController getModuleController(){
        return this.module_controller ;
    }
    
    public CodeViewController getCodeViewController(){
        return this.codeViewController ;
    }
    
    public ModuleConnectionController getModuleConnectionController(){
        
        return this.module_connection_controller ;
    }
    
    
    public void setSketchanchorPane(AnchorPane sketch_anchor_pane){
        this.sketch_pane = sketch_anchor_pane ;
    }
    
    public AnchorPane getSketchAnchorPane(){
        return this.sketch_pane ;
    }
    
    public FXMLDocumentController getMainGUIController(){
        return this.main_gui_controller ;
    }
    
    public void setSelectedModule(Module module){
        this.selectedModule = module ;
    }
    
    
    public void setFilePath(File file,Stage stage) {
    Preferences prefs = Preferences.userNodeForPackage(Sketch.class);
    if (file != null) {
      prefs.put("filePath", file.getPath());

      // Update the stage title
      stage.setTitle("Engduino IDE - " + file.getName());
    } else {
      prefs.remove("filePath");

      // Update the stage title
      stage.setTitle("Engduino IDE");
    }
  }
    
    public File getFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(Sketch.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
          return new File(filePath);
        } else {
          return null;
        }
   }
    
    public File getSketchFile(){
        return this.sketchFile ;
    }
    
    public void setSketchFile(File file ){
        this.sketchFile = file ;
    }
    
    public void setSketchTab(Tab tab){
        this.sketch_tab = tab ;
    }
    
    public Tab getSketchTab(){
        return this.sketch_tab ;
    }
    
    public boolean isSketchForModule(){
        return this.isSketchCustom ;
    }
    
    public void setSketchType(boolean custom_sketch){
        
        this.isSketchCustom = custom_sketch ;
    }
    
    
}
