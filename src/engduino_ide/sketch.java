/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engduino_ide;

import java.util.ArrayList;

/**
 *
 * @author shehrozebhatti
 */
public class sketch {
    
    private String sketch_name ;
    
    public sketch(){
        
    }
    
    public sketch(String name){
    
           this.sketch_name = name ;
    }
    
    public void setName(String name){
        this.sketch_name = name ;
    }
    
    public String getName(){
        return this.sketch_name ;
    }
    
    
    
    
}
