/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engduino_ide;

import java.util.HashMap;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import utility_classes.Utility;
import utility_classes.newUtility;

/**
 *
 * @author shehrozebhatti
 */
public class UtilitiesFactory {
    
    private HashMap<String,Utility> utilityList = new HashMap<String,Utility>() ;
    
    private  FXMLDocumentController main_document_controller ;
    
    public UtilitiesFactory(){
        
        
        
        
        
    }

    public UtilitiesFactory(FXMLDocumentController doc_controller) {
        //throw new UnsupportedOperationException("Not supported yet.");
        
        try{
            this.main_document_controller = doc_controller ;
            newUtility new_utility = new newUtility(doc_controller) ;
            this.utilityList.put("new", new_utility) ;
            
            
                        
        }
        catch(UnsupportedOperationException e ){
            System.out.println("This aint working");
        }

    }
    
    public Utility getUtility(String name){
     
       return this.utilityList.get(name) ;
       
    }
}
