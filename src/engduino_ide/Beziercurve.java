/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engduino_ide;

import ModuleClasses.Moduleanchor;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;

/**
 *
 * @author shehrozebhatti
 */
public class Beziercurve {
    
  private Moduleanchor starting_anchor ;
  
  private Moduleanchor end_anchor;
  
  private CubicCurve curve ;
  
  private int startX ;
  
  private int startY ;
  
  private int endX ;
  
  private int endY ;
    
  public Beziercurve(int startX, int startY, int endX, int endY){
    
    this.startX = startX ;
    this.startY = startY ;
    this.endX = endX ;
    this.endY = endY ;
      
    this.curve = createStartingCurve();
    //this.starting_anchor = new Anchor(Color.PALEGREEN, this.curve.startXProperty(),    this.curve.startYProperty());
    //this.end_anchor = new Anchor(Color.TOMATO,    this.curve.endXProperty(), this.curve.endYProperty());
    System.out.println("Thecstarting x = " + this.startX + "\nThe starting Y is " + this.startY + "\nThe end X is " + this.endX + "\nThe end Y is " + this.endY );
      
      
    this.starting_anchor = new Moduleanchor(new Image("http://i.imgur.com/7IKgTHk.png"), 1,this.curve.startXProperty(),    this.curve.startYProperty(),true);
    this.end_anchor = new Moduleanchor(new Image("http://i.imgur.com/7IKgTHk.png"), 2,this.curve.endXProperty(), this.curve.endYProperty(),false) ;
  }
  
  public Beziercurve(Moduleanchor first_anchor, Moduleanchor second_anchor){
    
    this.startX = ((int) first_anchor.getX())  ;
    this.startX = this.startX + 100 ;
    this.startY = (int) first_anchor.getY() ;
    this.endX = (int) second_anchor.getX() ;
    this.endY = (int) second_anchor.getY() ;
    
    System.out.println("Thecstarting x = " + first_anchor.getX() + "\nThe starting Y is " + this.startY + "\nThe end X is " + this.endX + "\nThe end Y is " + this.endY );
      
    this.curve = createStartingCurve();
    
    this.starting_anchor = first_anchor ;
    this.starting_anchor.doBinding(this.curve.startXProperty(), this.curve.startYProperty(), true);
    
    this.end_anchor = second_anchor ;
    this.end_anchor.doBinding(this.curve.endXProperty(), this.curve.endYProperty(), false);
  }
    
  private CubicCurve createStartingCurve() {
    CubicCurve curve = new CubicCurve();
    curve.setStartX(this.startX );
    curve.setStartY(this.startY);
    /*curve.setControlX1(150);
    curve.setControlY1(50);
    curve.setControlX2(250);
    curve.setControlY2(150);*/
    
    curve.setControlX1(this.startX);
    curve.setControlY1(this.startY);
    curve.setControlX2(this.endX);
    curve.setControlY2(this.endY);
    
    /*curve.setControlX1(this.startX + ((this.endX - this.startX) / 4));
    curve.setControlY1(this.startY);
    curve.setControlX2(this.startX + 3 * ((this.endX - this.startX) / 4));
    curve.setControlY2(this.endY);
    */
    
    curve.setEndX(this.endX);
    curve.setEndY(this.endY);
    curve.setStroke(Color.DARKGREY);
    curve.setStrokeWidth(4);
    curve.setStrokeLineCap(StrokeLineCap.ROUND);
    curve.setFill(Color.TRANSPARENT);
    return curve;
  }
  
  private void calculateControlX1(){
      
      if((this.startX - this.endX) < 10){
          
      }
      
  }
  
 
  
  public CubicCurve getCurve(){
      return this.curve ;
  }
  
  public Moduleanchor getStartingAnchor(){
      return this.starting_anchor ;
  }
  
  public Moduleanchor getEndingAnchor(){
      return this.end_anchor;
  }
  
  
    
}
