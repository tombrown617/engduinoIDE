/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package XMLFileHandling;


public class XMLConnection {
    
    String first_module ;
    
    String second_module ;
    
    public XMLConnection(String first_module,String second_module){
        
        this.first_module = first_module ;
        this.second_module = second_module ;
    }
    
    public String getFirstModule(){
        return this.first_module ;
    }
    
    public String getSecondModule(){
        return this.second_module ;
    }
    
}
