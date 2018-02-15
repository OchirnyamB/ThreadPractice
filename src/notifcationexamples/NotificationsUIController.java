/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notifcationexamples;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import taskers.*;

/**
 * FXML Controller class
 *
 * @author dalemusser
 */
public class NotificationsUIController implements Initializable, Notifiable {

    @FXML
    private TextArea textArea;    
    @FXML
    private Button endButtonTask1;
    @FXML
    private Button endButtonTask2;
    @FXML
    private Button endButtonTask3;
    
    private Task1 task1;
    private Task2 task2;
    private Task3 task3;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void start(Stage stage) {
        
        endButtonTask1.setVisible(false);
        endButtonTask2.setVisible(false);
        endButtonTask3.setVisible(false);
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if (task1 != null) task1.end();
                if (task2 != null) task2.end();
                if (task3 != null) task3.end();
            }
        });
    }
    
    @FXML
    public void startTask1(ActionEvent event) {
        System.out.println("Start Task 1");
        if(task1 == null){
            task1 = new Task1(2147483647, 1000000);
            task1.setNotificationTarget(this);
            task1.start();
            endButtonTask1.setVisible(true);      
        }
    }
    
    @Override
    public void notify(String message) {
        if (message.equals("Task 1 requested to stop")) {
            task1 = null;
        }else if(message.equals("Task 2 requested to stop")){
            task2 = null;
        }else{
            task3 = null;
        }
        textArea.appendText(message + "\n");
    }
    
    @FXML
    public void startTask2(ActionEvent event) {
        System.out.println("Start Task 2");
        if (task2 == null) {
            task2 = new Task2(2147483647, 1000000);
            task2.setOnNotification((String message) -> {
                textArea.appendText(message + "\n");
            });
            
            task2.start();
            endButtonTask2.setVisible(true); 
        }        
    }
    
    @FXML
    public void startTask3(ActionEvent event) {
        System.out.println("Start Task 3");
        if (task3 == null) {
            task3 = new Task3(2147483647, 1000000);
            // this uses a property change listener to get messages
            task3.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                textArea.appendText((String)evt.getNewValue() + "\n");
            });
            
            task3.start();
            endButtonTask3.setVisible(true);
        }
    } 
    
    @FXML
    public void endTask1(ActionEvent event) {
        task1.end();
        notify("Task 1 requested to stop");
        endButtonTask1.setVisible(false);
    }
    
    @FXML
    public void endTask2(ActionEvent event) {
        task2.end();
        notify("Task 2 requested to stop");
        endButtonTask2.setVisible(false);
    }
    
    @FXML
    public void endTask3(ActionEvent event) {
        task3.end();
        notify("Task 3 requested to stop");
        endButtonTask3.setVisible(false);
    }
    
}
