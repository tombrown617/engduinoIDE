/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SketchClasses;

import FlowControlClasses.Module;
import engduino_ide.FXMLDocumentController;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author shehrozebhatti
 */
public class SketchController {
    
    
    private  FXMLDocumentController main_document_controller ;
    
    private HashMap<String,Sketch> sketches = new HashMap<String,Sketch>() ;
    
    private Module selectedModule = null ;
    
    public SketchController(FXMLDocumentController doc_controller){
     
           this.main_document_controller = doc_controller ;
           
    }
    
    public Sketch createSketch(String name){
        
        Sketch new_sketch = new Sketch(name,main_document_controller) ;
        return new_sketch ;
        
    }
    
    public void addSketchToList(Sketch sketch){
        
        this.sketches.put(sketch.getName(), sketch);
    }
    
    public Sketch getSketch(String name){
        
        return this.sketches.get(name) ;
    }
    
    public void setSelectedModule(Module module){
        this.selectedModule = module ;
    }
    
    public Module getSelectedModule(){
        return this.selectedModule ;
    }
    
    public int getTotalSketches(){
        return this.sketches.size() ;
    }
    
    public ArrayList<Sketch> getSketches(){
        return new ArrayList<Sketch>(this.sketches.values()) ;
    }
    
}
