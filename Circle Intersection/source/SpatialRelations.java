package code;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.io.InputStream;
/**
 * Spatial Relation Class contains the implementation of the circle intersections
 * 
 * @author Raja Sekhar Pothina
 */
public class SpatialRelations {
    /**
     * Used for holding the pane object passed from super class
     */
    Pane primaryPane;
    /**
     * Used for the controlling the scrolling if text exceed the view port
     */
    ScrollPane rootScrollPane;
    /**
     * Used for holding all the elements of the SpatialRelations pane
     */
    VBox rootPane;
    /**
     *  Layout to hold the circles
     */
    Pane circlesPane;
    /**
     * Used for displaying the circles given as input
     */
    Circle[] circles;
    /**
     * Used for displaying and taking circle coordinates as inputs
     */
    TextField inputTextField;
    /**
     * Used for displaying spatial relation result
     */
    Text spatialRelationResultText;
    /**
     * Used for selecting the circle for applying the updates
     */
    Button circleSelector;
    /**
     * Used for changing the radius of the selected circle
     */
    Slider radiusSlider;
    /**
     * Used of storing the circle index to apply the updates
     */
    int currCircleSelected;
    /**
     * Initializes the creation of all elements in the application
     * @param primaryPane it is of type pane
     */
    SpatialRelations(Pane primaryPane){
        this.primaryPane = primaryPane;
        currCircleSelected = 0;
        createSpatialRelationsPane();
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
     * Creates and initializes the root pane, clipping rectangle and adds the root pane to the scroll pane 
     */
    public void createSpatialRelationsPane(){
        rootPane = new VBox(8);
        circles = new Circle[2];
        createSpatialRelationText();
        String[] inputTokens = getDataFromFile("/data/inFile.txt");
        createInputTextField(String.join(" ", inputTokens));
        createCircleSelectorButton();
        createRadiusSlider();
        createCirclesPane();
        createCircle(inputTokens,0);
        createCircle(inputTokens,1);
        Rectangle rect = new Rectangle();
        rect.widthProperty().bind(this.primaryPane.widthProperty());
        rect.heightProperty().bind(this.primaryPane.heightProperty());
        circlesPane.setClip(rect);
        radiusSlider.setValue(circles[currCircleSelected].getRadius());
        setSpatialRelationText(getCirclesIntersectString(doesCirclesIntersect(circles[0],circles[1])));
    }

  

    /**
     * set the text to the spatial relation text
     * @param text text of type String to set for the text control element
     */
    void setSpatialRelationText(String text){
        spatialRelationResultText.setText(text);
    }

    /**
     *
     * Takes two circle objects as input and return the status of intersection of the two circles
     *
     * @param circle1 represent the circle1 of type Circle
     * @param circle2 represent the circle2 of type Circle
     * @return String based on the Spatial Relation between the circles
     */
    String doesCirclesIntersect(Circle circle1, Circle circle2){
        double c1Radius = circle1.getRadius();
        double c2Radius = circle2.getRadius();
        double distanceBtwCentersOfTwoCircles = Math.hypot(circle1.getCenterX()-circle2.getCenterX(),circle1.getCenterY()-circle2.getCenterY()) ;
        double sumOfRaduisOfTwoCircles = c1Radius + c2Radius;

        if(distanceBtwCentersOfTwoCircles > sumOfRaduisOfTwoCircles)
            return "DO_NOT_INTERSECT";
        if(distanceBtwCentersOfTwoCircles == sumOfRaduisOfTwoCircles)
            return "TOUCH_OUTSIDE";
        if(distanceBtwCentersOfTwoCircles == (c1Radius -c2Radius))
            return "C2_TOUCH_INSIDE_C1";
        if(distanceBtwCentersOfTwoCircles == (c2Radius -c1Radius))
            return "C1_TOUCH_INSIDE_C2";
        if(distanceBtwCentersOfTwoCircles < (c1Radius -c2Radius))
            return "C2_INSIDE_C1";
        if(distanceBtwCentersOfTwoCircles < (c2Radius -c1Radius))
            return "C1_INSIDE_C2";
        if(distanceBtwCentersOfTwoCircles == 0)
            return "C1_EQUALS_C2";
    
        return "INTERSECTS";
    }

    /**
     * Forms the string based on the status of the isIntersection parameter
     *
     * @param spatialRelation represent the spacial relation of the two circles
     * @return Appropriate String based on the isIntersecting parameter
     */
    public String getCirclesIntersectString(String spatialRelation){
        HashMap<String,String> relationToString = new HashMap<String,String>() {{
            put("DO_NOT_INTERSECT","Circles are not intersecting");
            put("TOUCH_OUTSIDE","Circle 1(blue) touches the Circle 2(red) externally");
            put("C2_TOUCH_INSIDE_C1","Circle 2(red) touch Circle 1(blue) from outside");
            put("C1_TOUCH_INSIDE_C2","Circle 1(blue) touch Circle 2(red) from inside ");
            put("C2_INSIDE_C1","Circle 2(red) is inside the Circle 1(blue)");
            put("C1_INSIDE_C2","Circles 1(blue) is inside the Circle 2(red)");
            put("C1_EQUALS_C2","Circles 1(blue) is same as Circle 2(red)");
            put("INTERSECTS","Circles 1(blue) intersects Circle 2(red)");
        }};
        return relationToString.get(spatialRelation);
    }

    /**
     * Reads the input from given file 
     *
     * @param path Path of the data file
     *
     * @return Circles coordinates of the type String[]
     */
    public String[] getDataFromFile(String path){
        String inputString = "";
        Scanner scanner;
        try{
            InputStream in = this.getClass().getResourceAsStream(path);
            scanner = new Scanner(in);
            inputString = scanner.nextLine();
            scanner.close();
        }catch(Exception ec){
            System.out.println(ec);
        }finally{
              return inputString.trim().split(" ");
        }
    }
    
    /**
     * Forms the String to display a NumberFormatException occured
     * 
     * @return the text to notify the user to provide proper input values
     */
    public String getNumberFormatExceptionLabelString() {
        return "Please provide inputs in the format of x1 y1 radius1 x2 y2 radius2 ";
    }

    /**
     * Create the Circles Coordinates String
     * @return String in the format of x1 y1 radius1 x2 y2 radius2
     */
    String getCirclesCoordinatesAsString(){
        return String.format("%.2f %.2f %.2f %.2f %.2f %.2f", 
                circles[0].getCenterX(),
                circles[0].getCenterY(),
                circles[0].getRadius(),
                circles[1].getCenterX(),
                circles[1].getCenterY(),
                circles[1].getRadius());
    }

    /**
     * Create and initializes the spatial relation text
     */
    void createSpatialRelationText(){
        spatialRelationResultText = new Text("Circles Intersect EachOther");
        rootPane.getChildren().add(spatialRelationResultText);
    }

    /**
     * Create and initializes the radius change slider
     */
    void createRadiusSlider(){
        radiusSlider = new Slider(30,300,10);
        radiusSlider.setShowTickLabels(true);
        radiusSlider.setShowTickMarks(true);
        radiusSlider.valueProperty().addListener(ov->{
            updateCircle(radiusSlider.getValue(),currCircleSelected);

        });
        rootPane.getChildren().add(radiusSlider);
    }

    /**
     *
     * Creates the pane layout to hold the circles
     *
     */
    public void createCirclesPane(){
        circlesPane = new Pane();
        circlesPane.setPrefSize(500,250);
        rootPane.getChildren().add(circlesPane);
    }

    /**
     * Creates the circle using the index and coordinates
     * 
     * @param inputStringTokens array of Strings represents the coordinates of the circle
     * @param index represents the circle id
     *
     */
    void createCircle(String[] inputStringTokens, int index) {
        String[] circleProperties = Arrays.copyOfRange(inputStringTokens,3*index,3*index+3);
        double centerX = Double.parseDouble(circleProperties[0]);
        double centerY = Double.parseDouble(circleProperties[1]);
        double radius  = Double.parseDouble(circleProperties[2]);
        circles[index] = new Circle(centerX,centerY,radius);
        String formatString = String.format("-fx-fill: %s;-fx-opacity: 0.5;", index==0 ? "blue":"red");
        circles[index].setStyle(formatString);
        // circles[index].relocate(centerX,centerY);
        circles[index].screenToLocal(centerX,centerY);
        circles[index].setOnMouseDragged(actionEvent->{
            updateCircle(actionEvent.getX(),actionEvent.getY(), currCircleSelected);
        });
        circlesPane.getChildren().add(circles[index]);
    }

   /**
    *
    * Creates the input text field to take circle coordinates as inputs
    * 
    * @param inputString represents the coordinates of the two circles
    *
    */
    void createInputTextField(String inputString){
        inputTextField = new TextField(inputString);
        inputTextField.setOnAction((actionEvent)->changeInputHandler(actionEvent));
        rootPane.getChildren().add(inputTextField);
    }

    /**
    *
    * Creates the button to switch between the circles
    *
    */
    void createCircleSelectorButton(){
        circleSelector = new Button(String.format("Switch to Circle %d",(currCircleSelected^1)+1));
        circleSelector.setOnAction((actionEvent)->changeTheCircleSelection(actionEvent));
        rootPane.getChildren().add(circleSelector);
    }

    /**
     * Updates the radius of the circle with given index
     * 
     * @param radius centerX of the circle with given index
     * @param index id of the cicle to update
     */

    void updateCircle(double radius,int index){
        circles[index].setRadius(radius);
        inputTextField.setText(getCirclesCoordinatesAsString());
        setSpatialRelationText(getCirclesIntersectString(doesCirclesIntersect(circles[0],circles[1])));
    }

    /**
     * Updates the center of the circle with given index
     * 
     * @param centerX centerX of the circle with given index
     * @param centerY centerY of the circle with given index
     * @param index id of the cicle to update
     */

    void updateCircle(double centerX,double centerY,int index){
        circles[index].setCenterX(centerX);
        circles[index].setCenterY(centerY);
        circles[index].screenToLocal(centerX,centerY);
        inputTextField.setText(getCirclesCoordinatesAsString());
        setSpatialRelationText(getCirclesIntersectString(doesCirclesIntersect(circles[0],circles[1])));
    }

    /**
     * Updates the circle using the updated coordinates and its circle id
     * 
     * @param inputStringTokens array of Strings represents the coordinates of the circle
     * @param index represents the circle id
     *
     */
    void updateCircle(String[] inputStringTokens, int index) {
        String[] circleProperties = Arrays.copyOfRange(inputStringTokens,3*index,3*index+3);
        double centerX = Double.parseDouble(circleProperties[0]);
        double centerY = Double.parseDouble(circleProperties[1]);
        double radius  = Double.parseDouble(circleProperties[2]);
        circles[index].setCenterX(centerX);
        circles[index].setCenterY(centerY);
        circles[index].setRadius(radius);
        circles[index].screenToLocal(centerX,centerY);
    }


    /**
     * 
     * Button Click event is handled
     *
     * @param actionEvent ActionEvent object emitted by the click event
     */
    public void changeTheCircleSelection(ActionEvent actionEvent){
        currCircleSelected = (currCircleSelected+1)%2;
        System.out.println(currCircleSelected);
        circleSelector.setText(String.format("Switch to Circle %d",(currCircleSelected^1)+1));
        radiusSlider.setValue(circles[currCircleSelected].getRadius());
    }

    /**
     * 
     * Input TextField Change event is handled
     *
     * @param actionEvent ActionEvent object emitted by the change event
     */
    public void changeInputHandler(ActionEvent actionEvent){
        int i;
        try {
            String[] inputTokens = inputTextField.getText().trim().split(" ");
            updateCircle(inputTokens, 0);
            updateCircle(inputTokens,1);
            setSpatialRelationText(getCirclesIntersectString(doesCirclesIntersect(circles[0],circles[1])));
            actionEvent.consume();
        } catch (Exception ecp) {
           setSpatialRelationText(getNumberFormatExceptionLabelString());
        }
    }

}