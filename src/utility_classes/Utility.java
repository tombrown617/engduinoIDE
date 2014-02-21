/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utility_classes;

import engduino_ide.FXMLDocumentController;

/**
 *
 * @author shehrozebhatti
 */
public abstract class Utility {
    
    private String utilityName ;
    
    private final FXMLDocumentController doc_controller ;
    
    public Utility(String name, FXMLDocumentController controller){
        this.utilityName = name ;
        this.doc_controller = controller; 
    }
    
}
