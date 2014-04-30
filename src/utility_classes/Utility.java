/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utility_classes;

import SketchClasses.SketchController;
import engduino_ide.FXMLDocumentController;


public abstract class Utility {
    
    private String utilityName ;
    
    private SketchController sketch_controller ;
    
    private final FXMLDocumentController doc_controller ;
    
    public Utility(String name, FXMLDocumentController controller, SketchController sketch_controller){
        this.utilityName = name ;
        this.doc_controller = controller; 
        this.sketch_controller = sketch_controller ;
        
    }
    
    public FXMLDocumentController getDocumentController(){
        return this.doc_controller ;
    }
    
    public SketchController getSketchController(){
        return this.sketch_controller ;
    }
    
}
