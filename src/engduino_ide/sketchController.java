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
public class sketchController {
    
    private ArrayList<sketch> sketches ;
    
    public sketchController(){
        
        this.sketches = new ArrayList<sketch>() ;
        
    }
    
    
    public void createSketch(){
       sketch new_sketch = new sketch() ;
       sketches.add(new_sketch) ;
       
       
    }
    
    public void addSketch(sketch sketch_to_add){
        this.sketches.add(sketch_to_add) ;
    
    }
    
    
    
    
}
