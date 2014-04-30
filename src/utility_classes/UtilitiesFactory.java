/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utility_classes;

import SketchClasses.SketchController;
import engduino_ide.FXMLDocumentController;
import java.util.HashMap;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utility_classes.Utility;


public class UtilitiesFactory {
    
    private HashMap<String,Utility> utilityList = new HashMap<String,Utility>() ;
    
    private SketchController sketch_controller ;
    
    private Stage main_stage ;
    
    private  FXMLDocumentController main_document_controller ;
    
    public UtilitiesFactory(FXMLDocumentController doc_controller, Stage stage, SketchController sketch_controller) {
        
        try{
            this.main_document_controller = doc_controller ;
            this.main_stage = stage ;
            this.sketch_controller = sketch_controller ;
            
            Utility new_utility = new newUtility(doc_controller, stage,this.sketch_controller) ;
            this.utilityList.put("new", new_utility) ;
            
            Utility verify_code = new verifyCode(doc_controller, stage,this.sketch_controller) ;
            this.utilityList.put("verify", verify_code) ;
            
            
            Utility upload_code = new uploadUtility(doc_controller, stage,this.sketch_controller) ;
            this.utilityList.put("upload", upload_code) ;
            
            Utility save_utility = new saveUtility(doc_controller, stage,this.sketch_controller) ;
            this.utilityList.put("save", save_utility) ;
            
            Utility open_utility = new OpenUtility(doc_controller, stage,this.sketch_controller) ;
            this.utilityList.put("open", open_utility) ;
            
            Utility print_utility = new printUtility(doc_controller, stage, this.sketch_controller);
            this.utilityList.put("print", print_utility);
                        
        }
        catch(UnsupportedOperationException e ){
            
        }

    }
    
    public Utility getUtility(String name){
     
       return this.utilityList.get(name) ;
       
    }
}
