package code;

import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;

/**
 * 
 * Front Class contains the implementation of the front pane
 * 
 * @author Raja Sekhar Pothina
 */
public class Front{
    /**
     * Used for holding the pane object passed from super class
     */
    Pane primaryPane;
    /**
     * Used for holding all the elements of the front pane
     */
    VBox rootPane;
    /**
     * Used for the controlling the scrolling if text exceed the view port
     */
    ScrollPane rootScrollPane;
    /**
     * Used for holding the project header text 
     */
    Text projectHeaderText;
    /**
     * Used for holding the policy description text 
     */
    Text policyDescriptionText;
    /**
     * Used for holding the author name
     */
    Text nameText;
    /**
     * Used for holding the author image 
     */
    ImageView authorImageView;

    /**
     * Initializes the creation of all elements in the application
     * 
     * @param primaryPane it is of type pane
     */
    Front(Pane primaryPane){
        this.primaryPane = primaryPane;
        createFrontPane();
        rootScrollPane = new ScrollPane();
        rootScrollPane.prefViewportHeightProperty().bind(this.primaryPane.heightProperty());
        rootScrollPane.setContent(rootPane);
        rootScrollPane.setVisible(false);
        primaryPane.getChildren().add(rootScrollPane);
    }
    /**
     * Provides the rootScrollPane for accessing at other places
     * @return rootScrollPane 
     */
    public ScrollPane getRootPane(){
        return rootScrollPane;
    }
    /**
     * Creates and initializes the root pane, scroll pane and adds the root pane to the scroll pane 
     */
    public void createFrontPane()
    {
        rootPane = new VBox(8);
        rootPane.setMinWidth(0.0);
        rootPane.setAlignment(Pos.CENTER);
        createProjectHeader();
        createAuthorName();
        createAuthorImage();
        createPolicyDescription();
    }
    /**
     * Initializes the text control element with the project header text
     */
    void createProjectHeader(){
        projectHeaderText = new Text("Demonstration of Spatial Relations of Circles Assignment for\nJava, GUI and Visualization: CS5405");
        projectHeaderText.setFont(new Font(20));
        projectHeaderText.setTextAlignment(TextAlignment.CENTER);
        projectHeaderText.wrappingWidthProperty().bind(primaryPane.widthProperty().subtract(16));
        projectHeaderText.setFill(Color.BLUE);
        rootPane.getChildren().add(projectHeaderText);
    }
    /**
     * Initializes the text control element with the author name
     */
    void createAuthorName(){
        nameText = new Text("Author: Raja Sekhar Pothina (rp6kp@umsystem.edu)");
        nameText.setFont(new Font(16));
        nameText.setTextAlignment(TextAlignment.CENTER);
        nameText.setFill(Color.GREEN);
        nameText.wrappingWidthProperty().bind(primaryPane.widthProperty().subtract(16));
        rootPane.getChildren().add(nameText);
    }
    /**
     * Initializes the text control element with the author image
     */
    void createAuthorImage(){
        Image authorImage = new Image("images/authorImage.png");
        authorImageView = new ImageView(authorImage);
        authorImageView.setPreserveRatio(true);
        authorImageView.setSmooth(true);
        authorImageView.setFitWidth(100);
        authorImageView.setFitHeight(100);
        rootPane.getChildren().add(authorImageView);
    }
    /**
     * Initializes the text control element with the policy description text
     */
    void createPolicyDescription(){
        policyDescriptionText = new Text("1. This is my orignal code, No IDE used in the submission. \n2. I did not give my code to anyone or I did not use anyone's code in this work.");
        policyDescriptionText.wrappingWidthProperty().bind(primaryPane.widthProperty().subtract(16));
        policyDescriptionText.setTextAlignment(TextAlignment.LEFT);
        policyDescriptionText.setFill(Color.web("#3700B3"));
        rootPane.getChildren().add(policyDescriptionText);
    }
}