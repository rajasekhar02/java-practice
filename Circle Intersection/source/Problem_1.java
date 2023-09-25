
package code;

import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.ScrollPane;

/**
 * Problem Class contains the implementation of the problem pane
 * 
 * @author Raja Sekhar Pothina
 */
class Problem{
    /**
     * Used for holding the pane object passed from super class
     */
    Pane primaryPane;
    /**
     * Used for holding all the elements of the problem pane
     */
    VBox rootPane;
    /**
     * Used for the controlling the scrolling if text exceed the view port
     */
    ScrollPane rootScrollPane;
    /**
     * Used for holding the problem description text 
     */
    Text problemDescriptionText;

    /**
     * Initializes the creation of all elements in the application
     * 
     * @param primaryPane it is of type pane
     */
    Problem(Pane primaryPane){
        this.primaryPane = primaryPane;
        createProblemPane();
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
    public void createProblemPane(){
        rootPane = new VBox();
        rootPane.setMinWidth(0.0);
        rootPane.setStyle("-fx-background-color: #FFFFFF;");
        rootScrollPane = new ScrollPane();
        rootScrollPane.prefViewportHeightProperty().bind(this.primaryPane.heightProperty());
        rootScrollPane.prefViewportWidthProperty().bind(this.primaryPane.widthProperty());
        rootScrollPane.setContent(rootPane);
        createProblemDescription();
    }
    /**
     * Initializes the text control element with the description text
     */
    void createProblemDescription(){
        String st = "This HW is a variation of HW07 based problem 14.21 and 14.22 on page 590 in the book."
        +"\n1. Create data directory to include infile.txt  to read it into the textfield."
        +"\n2. Create a Prompt for the user to input values in the textfield. "
        +"\n3. The program reads input: center x1,y1, radius r1, for first circle(blue), then center x2,y2, radius r2, for"
        +" circle(red) from the textfield. You will use integers for pixel coordinates and double for radius."
        +"\nGood practice: Read all numbers as one text string and split it into array of string "
        +"elements, convert the component strings into appropriate format "
        +"\nProgram determines the spatial relation which can is the case\n "
        +"\n1. Equal "
        +"\n2. Disjoint "
        +"\n3. Externally Touching (Trivial intersect) "
        +"\n4. Proper overlap (non-trivial intersect. none is completely inside the other) "
        +"\n5. C1 is inside C2 , not touching"
        +"\n6. C1 is inside C2 , touching"
        +"\n7. C2 is inside C1 , not touching"
        +"\n8. C2 is inside C1 , touching"
        +"\n Add a button to select a circle out of two circles."
        +"\n Add slider interaction  to update radius of the circle."
        +"\n Add mouse interaction to update center of the circle.";
        problemDescriptionText = new Text(st);
        problemDescriptionText.wrappingWidthProperty().bind(this.primaryPane.widthProperty().subtract(20));
        problemDescriptionText.setStyle("-fx-fill: #0000ff;");
        rootPane.getChildren().add(problemDescriptionText);
    }
}