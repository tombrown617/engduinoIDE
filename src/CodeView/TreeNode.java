/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package CodeView;

import FlowControlClasses.Module;
import java.util.ArrayList;

/**
 *
 * @author shehrozebhatti
 */
public class TreeNode {
    
    private ArrayList<TreeNode> children_nodes = new ArrayList<TreeNode>() ;
    
    private Module node_module ;
    
    public TreeNode(Module node_module){
        this.node_module = node_module; 
    }
    
    public TreeNode(){
        
    }
    
    public Module getNodeModule(){
        return this.node_module ;
    }
    
    public void setNodeModule(Module module){
        this.node_module = module ;
    }
    
    public TreeNode createChild(Module node){
        TreeNode child_node = new TreeNode(node) ;
        this.children_nodes.add(child_node) ;
        
        return child_node ;
    }
    
    public boolean hasChildNode(){
        
        if(this.children_nodes.size() == 0){
            return false ;
        }
        else{
            
            return true ;
        }
        
    }
    
    public int getTotalChildren(){
        return this.children_nodes.size() ;
    }
    
    public boolean containsModule(Module module){
        
        boolean output = false ;
        
        for(int i = 0; i < this.children_nodes.size(); i++){
            
            
            
        }
        
        return output ;
    }
    
    public TreeNode getChild(int num){
        
        return this.children_nodes.get(num) ;
        
    }
    
    public void addChild(TreeNode child){
        this.children_nodes.add(child) ;
    }
    
}
