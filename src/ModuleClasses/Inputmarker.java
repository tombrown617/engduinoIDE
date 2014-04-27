/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ModuleClasses;

import SketchClasses.Sketch;
import engduino_ide.Beziercurve;
import javafx.beans.property.DoubleProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 *
 * @author shehrozebhatti
 */
public class Inputmarker extends ImageView {
    
    private double x_coordinate ;
    
    private double y_coordinate ;
    
    private int input_number ;
    
    private Sketch main_sketch ;
    
    private Moduleanchor mod_anchor ;
    
    public Inputmarker(double x, double y, AnchorPane sketch, Sketch main_sketch, int number, Moduleanchor module_anchor){
        
        this.main_sketch = main_sketch ;
        setImage(new Image("graphics/input_marker.png")) ;
        this.input_number = number ;
        this.mod_anchor = module_anchor ;
        
        //double new_x = x - 19 ;
        //double new_y = y + 14 ;
        
        setX(x) ; 
        setY(y) ;
        relocate(x,y) ;
        
        this.x_coordinate = x ;
        this.y_coordinate = y ;
        this.enableDrag(this,this.main_sketch);
        
        
        
        
    }
    
    public void updateCoordinates(double x, double y){
        
        double new_x = x - 19 ;
        double new_y = 0 ;
        
        if(this.input_number == 1){
            new_y = y + 14 ;
        }
        else if(this.input_number == 2){
            new_y = y + 30 ;
        }
         
        
        setX(new_x) ;
        setY(new_y) ;
        relocate(new_x, new_y) ;
        
        
    }
    
    private void enableDrag(final Inputmarker input_marker, final Sketch main_sketch) {
      
        
      final Delta dragDelta = new Delta();
      
      
      setOnDragOver(new EventHandler <DragEvent>() {
           @Override
           public void handle(DragEvent event) {
             
              event.acceptTransferModes(TransferMode.ANY);
              getScene().setCursor(Cursor.HAND);
              setImage(new Image("graphics/input_mouse_over.png")) ;
              //event.setDropCompleted(false);
              event.consume();
           }
       });
      
      setOnMouseDragOver(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setImage(new Image("graphics/input_mouse_over.png")) ;
            }
        }); 
      
      setOnMouseDragExited(new EventHandler<MouseDragEvent>(){
         @Override
         public void handle(MouseDragEvent e){
              setImage(new Image("graphics/input_marker.png")) ;
              
         }
      });
      
      /*setOnMouseEntered(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
          
            mouseEvent.setDragDetect(true);
          if (mouseEvent.isPrimaryButtonDown() == true) {
            getScene().setCursor(Cursor.HAND);
            setImage(new Image("graphics/input_mouse_over.png")) ;
            
          }
        }
      });*/
      
     setOnMouseDragReleased(new EventHandler<MouseDragEvent>(){
         @Override
         public void handle(MouseDragEvent e){
             
             double x_dropped = e.getX() ;
             double y_dropped = e.getY( );
             
             Clipboard db = Clipboard.getSystemClipboard();
             
             if(db.getString().equals("main")){
                 
                 MainInputMarker main_input = main_sketch.getModuleController().getMainInputMarker() ;
                 
                 Beziercurve new_final_curve = new Beziercurve(main_sketch.getSketchAnchorPane(), main_input,input_marker) ;
                 
                 main_sketch.getModuleConnectionController().createNewConnection(main_input, getModuleAnchor().getModule(), input_marker.getPort(),new_final_curve);
                  
                 
                 
             }else{
                 
                  int output_marker_number = Integer.parseInt(db.getString().substring(db.getString().length() - 1,db.getString().length()))   ;
                  Outputmarker output_marker = main_sketch.getModuleController().getModule(db.getString().substring(0,db.getString().length() - 9)).getAnchor().getMarker(output_marker_number) ;
                  Beziercurve new_final_curve = new Beziercurve(main_sketch.getSketchAnchorPane(), output_marker,input_marker) ;
                  
                  main_sketch.getModuleConnectionController().createNewConnection(output_marker.getModuleAnchor().getModule(), getModuleAnchor().getModule(), output_marker.getPort(), getPort(),new_final_curve);
                  
                  
             }
            
            
             //
                    
         }
     });
     
      setOnMouseExited(new EventHandler<MouseEvent>(){
          @Override public void handle(MouseEvent mouseEvent) {
          if (!mouseEvent.isPrimaryButtonDown()) {
            getScene().setCursor(Cursor.DEFAULT);
            setImage(new Image("graphics/input_marker.png")) ;
          }
        }
      });
      
       
      
      
      
    }
    
    private class Delta { double x, y; }
    
     public void doBinding(DoubleProperty x, DoubleProperty y, boolean first){
        
        if(first == true){
            x.bind(xProperty().add(100));
            y.bind(yProperty().add(15));
        }
        else{
           x.bind(xProperty().add(0));
           y.bind(yProperty().add(7)); 
        }
        
    }
     
     public Moduleanchor getModuleAnchor(){
        return this.mod_anchor ;
    }
     
     public int getPort(){
         return this.input_number ;
     }
            
}
