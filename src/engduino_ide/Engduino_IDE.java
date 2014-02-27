/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engduino_ide;

import utility_classes.UtilitiesFactory;
import SketchClasses.SketchController;
import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.CheckMenuItemBuilder;
import javafx.scene.control.Dialogs;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.scene.control.MenuBar ;
import javafx.scene.control.Menu ;
import javafx.scene.control.MenuItem ; 
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.RadioMenuItemBuilder;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;


/**
 *
 * @author shehrozebhatti
 */
public class Engduino_IDE extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        
        
        //Parent root = FXMLLoader.load(getClass().getResource("new_engduino.fxml"));
        
        
        
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = (Parent) fxmlLoader.load(getClass().getResource("new_engduino.fxml").openStream());
        FXMLDocumentController main_doc_controller = (FXMLDocumentController) fxmlLoader.getController();
        
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(this.getClass().getResource("/css/main_ide_stylesheet.css").toExternalForm());
       
        primaryStage.setScene(scene);
        primaryStage.setTitle("Engduino - *New Sketch");
        primaryStage.show();
        
        SketchController sketchController = new SketchController(main_doc_controller) ;
        main_doc_controller.setSketchController(sketchController);
        UtilitiesFactory utilityFactory = new UtilitiesFactory(main_doc_controller, primaryStage, sketchController) ;
        
       
     
        
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }
    
    
    
}
