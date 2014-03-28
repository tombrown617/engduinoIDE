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
    
    public Module getNodeModule(){
        return this.node_module ;
    }
    
    public boolean hasChildNode(){
        
        if(this.children_nodes.size() == 0){
            return false ;
        }
        else{
            
            return true ;
        }
        
    }
    
}
