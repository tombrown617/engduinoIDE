/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SketchClasses;

import engduino_ide.FXMLDocumentController;
import java.util.HashMap;

/**
 *
 * @author shehrozebhatti
 */
public class SketchController {
    
    
    private  FXMLDocumentController main_document_controller ;
    
    private HashMap<String,Sketch> sketches = new HashMap<String,Sketch>() ;
    
    public SketchController(FXMLDocumentController doc_controller){
     
           this.main_document_controller = doc_controller ;
           
    }
    
    public Sketch createSketch(String name){
        
        Sketch new_sketch = new Sketch(name) ;
        return new_sketch ;
        
    }
    
    public void addSketchToList(Sketch sketch){
        
        this.sketches.put(sketch.getName(), sketch);
    }
    
    public Sketch getSketch(String name){
        
        return this.sketches.get(name) ;
    }
    
}
