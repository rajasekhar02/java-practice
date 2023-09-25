package code;



import javafx.event.ActionEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.application.Application;
import javafx.scene.control.ScrollPane;
import java.util.HashMap;

import code.Front;
import code.Problem;
import code.SpatialRelations;

/**
 * 
 * Demo Class contains the implementation of the javafx application for
 * checking the intersection of two circles
 * 
 * @author Raja Sekhar Pothina
 */
public class Demo extends Application {

    /**
     * Used to hold all the label
     */
    VBox rootPane;
    /**
     * Used to hold buttons used for pane switching
     */
    HBox buttonsPane;
    /**
     * Used for controlling the visibility Front Pane
     */
    Button frontPaneButton;
    /**
     * Used for controlling the visibility Problem Pane
     */
    Button problemPaneButton;
    /**
     * Used for controlling the visibility SpatialRelations Pane
     */
    Button spatialRelationsButton;
    /**
     * Used for storing the current active pane
     */
    String currentButtonActive;
    /**
     * Used for storing the map of buttons id to panes object
     */
    HashMap<String,ScrollPane> buttonIdToPanes;
    /**
     * 
     * Initializes the creation of all elements in the application
     * 
     */
    public Demo() {
        rootPane = new VBox(8);   
        createButtonsForPaneControlling();
        StackPane stackPane = new StackPane();
        Front frontPane = new Front(stackPane);
        Problem problemPane = new Problem(stackPane);
        SpatialRelations spatialRelationsPane = new SpatialRelations(stackPane);
        ScrollPane frontRootPane = frontPane.getRootPane();
        rootPane.getChildren().add(stackPane);
        buttonIdToPanes = new HashMap<String, ScrollPane>() {{
            put("Front", frontRootPane);
            put("Problem",problemPane.getRootPane());
            put("SpatialRelations",spatialRelationsPane.getRootPane());
        }};
        currentButtonActive = "Front";
        frontRootPane.setVisible(true);
    }

    /**
     * Creates the Buttons for pane controlling like tabs
     *
     */
    void createButtonsForPaneControlling(){
        frontPaneButton = new Button("Introduction");
        frontPaneButton.setId("Front");
        frontPaneButton.setOnAction((actionEvent)->{
            PaneButtonHandler(actionEvent);
        });
        problemPaneButton = new Button("Problem");
        problemPaneButton.setId("Problem");
        problemPaneButton.setOnAction((actionEvent)->{
            PaneButtonHandler(actionEvent);
        });
        spatialRelationsButton = new Button("Solution");
        spatialRelationsButton.setId("SpatialRelations");
        spatialRelationsButton.setOnAction((actionEvent)->{
            PaneButtonHandler(actionEvent);
        });
        buttonsPane = new HBox(10, frontPaneButton,problemPaneButton,spatialRelationsButton);
        rootPane.getChildren().add(buttonsPane);
    }
    /**
     * Contains implementation action handling for a button click
     *
     * @param actionEvent ActionEvent object emitted by the ActionEvent by the Button
     */
    void PaneButtonHandler(ActionEvent actionEvent){
        ScrollPane paneContainer = buttonIdToPanes.get(currentButtonActive);
        paneContainer.setVisible(false);
        currentButtonActive = ((Button)actionEvent.getSource()).getId();
        paneContainer = buttonIdToPanes.get(currentButtonActive);
        paneContainer.setVisible(true);
    }
    /**
     * Creates the scene object with verticalLayout and initializes the Stage object
     * @param stage primary stage object which will be added to scene
     */
    @Override
    public void start(Stage stage) {
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(rootPane);
        stackPane.setPadding(new Insets(10));
        Scene scene = new Scene(stackPane);
        stage.setWidth(550);
        stage.setHeight(450);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Circle Intersection Checker");
    }

    /**
     * Main Function to start Application
     * 
     * @param args arguments from the command line
     */
    public static void main(String[] args) {
        launch(args);
    }

}
