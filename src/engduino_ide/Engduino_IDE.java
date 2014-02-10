/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engduino_ide;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.CheckMenuItemBuilder;
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


/**
 *
 * @author shehrozebhatti
 */
public class Engduino_IDE extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        /*Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        */
        
       // Group root2 = FXMLLoader.load(getClass().getResource("new_engduino.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("new_engduino.fxml"));
        
        Scene scene = new Scene(root);
        
        
        
        scene.getStylesheets().add(this.getClass().getResource("/css/main_ide_stylesheet.css").toExternalForm());
        
        MenuBar menuBar = new MenuBar(); 
 
 // File menu - new, save, exit 
        Menu menu = new Menu("File"); 
        menu.getItems().add(new MenuItem("New")); 
        menu.getItems().add(new MenuItem("Save")); 
        menu.getItems().add(new SeparatorMenuItem()); 
        menu.getItems().add(new MenuItem("Exit")); 

        menuBar.getMenus().add(menu); 

        // Cameras menu - camera 1, camera 2 
        Menu tools = new Menu("Cameras"); 
        tools.getItems().add(CheckMenuItemBuilder.create().text("Show Camera 1").selected(true).build()); 

        tools.getItems().add(CheckMenuItemBuilder.create().text("Show Camera 2").selected(true).build()); 
        
        menuBar.getMenus().add(tools); 

 
 // Alarm 
        Menu alarm = new Menu("Alarm"); 
        ToggleGroup tGroup = new ToggleGroup(); 
        RadioMenuItem soundAlarmItem = RadioMenuItemBuilder.create().toggleGroup(tGroup).text("Sound Alarm").build(); 
        RadioMenuItem stopAlarmItem = RadioMenuItemBuilder.create().toggleGroup(tGroup).text("Alarm Off").selected(true).build(); 

        alarm.getItems().add(soundAlarmItem); 
        alarm.getItems().add(stopAlarmItem); 

        Menu contingencyPlans = new Menu("Contingent Plans"); 
        contingencyPlans.getItems().add(new CheckMenuItem("Self Destruct in T minus 50")); 
        contingencyPlans.getItems().add(new CheckMenuItem("Turn off the coffee machine ")); 
        contingencyPlans.getItems().add(new CheckMenuItem("Run for your lives! ")); 

        alarm.getItems().add(contingencyPlans); 
        menuBar.getMenus().add(alarm); 
        
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty()); 
 
        //root.getChildren().add(menuBar); 
        //root.getChildren.add(menuBar) ;
       
        //root.getClass().
        
       
        //primaryStage.getIcons().add(new Image("testing_image.png"));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Engduino - *New Sketch");
        primaryStage.show();
        
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
