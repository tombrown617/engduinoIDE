/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SketchClasses;

/**
 *
 * @author shehrozebhatti
 */
public class Sketch {
    
    private String name ;
    
    public Sketch(String name){
        this.name = name ;
    }
    
    public String getName(){
        return this.name ;
    }
    
    public void setName(String new_name){
        this.name = new_name ;
    }
}
