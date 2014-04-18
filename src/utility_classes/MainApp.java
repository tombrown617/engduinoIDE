/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utility_classes;

import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.util.ArrayList;
import java.util.prefs.Preferences;
import javafx.scene.control.Dialogs;
import javafx.stage.Stage;

/**
 *
 * @author shehrozebhatti
 */
public class MainApp {
    
    private Stage primaryStage ;
    
    public MainApp(Stage stage){
        this.primaryStage = stage ;
    }
    
    
    /**
 * Returns the person file preference, i.e. the file that was last opened.
 * The preference is read from the OS specific registry. If no such
 * preference can be found, null is returned.
 * 
 * @return
 */
    public File getPersonFilePath() {
      Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
      String filePath = prefs.get("filePath", null);
      if (filePath != null) {
        return new File(filePath);
      } else {
        return null;
      }
    }

/**
 * Sets the file path of the currently loaded file.
 * The path is persisted in the OS specific registry.
 * 
 * @param file the file or null to remove the path
 */
    public void setPersonFilePath(File file) {
      Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
      if (file != null) {
        prefs.put("filePath", file.getPath());

        // Update the stage title
        primaryStage.setTitle("AddressApp - " + file.getName());
      } else {
        prefs.remove("filePath");

        // Update the stage title
        primaryStage.setTitle("AddressApp");
      }
    }
    
    
    @SuppressWarnings("unchecked")
    public void loadPersonDataFromFile(File file) {
      XStream xstream = new XStream();
      xstream.alias("person", Person.class);

      try {
        String xml = FileUtility.readFile(file);

        ArrayList<Person> personList = (ArrayList<Person>) xstream.fromXML(xml);

        //personData.clear();
        //personData.addAll(personList);

        setPersonFilePath(file);
      } catch (Exception e) { // catches ANY exception
        Dialogs.showErrorDialog(primaryStage,
            "Could not load data from file:\n" + file.getPath(),
            "Could not load data", "Error", e);
      }
    }

    /**
     * Saves the current person data to the specified file.
     * 
     * @param file
     */
    public void savePersonDataToFile(File file) {
      XStream xstream = new XStream();
      xstream.alias("person", Person.class);

      // Convert ObservableList to a normal ArrayList
      //ArrayList<Person> personList = new ArrayList<>(personData);

      //String xml = xstream.toXML(personList);
      
      String xml = "<person>\n" +
"  <firstname>Joe</firstname>\n" +
"  <lastname>Walnes</lastname>\n" +
"  <phone>\n" +
"    <code>123</code>\n" +
"    <number>1234-456</number>\n" +
"  </phone>\n" +
"  <fax>\n" +
"    <code>123</code>\n" +
"    <number>9999-999</number>\n" +
"  </fax>\n" +
"</person>" ;
      
      try {
        FileUtility.saveFile(xml, file);

        setPersonFilePath(file);
      } catch (Exception e) { // catches ANY exception
        Dialogs.showErrorDialog(primaryStage,
            "Could not save data to file:\n" + file.getPath(),
            "Could not save data", "Error", e);
      }
    }
    
    public class Person {
        private String firstname;
        private String lastname;
        private PhoneNumber phone;
        private PhoneNumber fax;
        // ... constructors and methods
      }

      public class PhoneNumber {
        private int code;
        private String number;
        // ... constructors and methods
      }
    
}


