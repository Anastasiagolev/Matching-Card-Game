import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The Main program extends Application to create a GUI application to play the card Matching game.
 *
 * @author Anastasia Golev, Cristian Santos, Kevyn Muniz
 *
 */
public class Main extends Application{

    //declaring the welcome screen
    Scene welcomeScreen;
    //GridPane
    GridPane cardGrid = new GridPane();
    //Label is a welcome message
    Text welcomeMessage, secondMessage;

    //Three buttons to select the difficulty level the player wants.
    Button startButton;
    Button button1;
    Button button2;
    Button button3;

    public static final int ROWS = 4;
    public static final int COL = 4;//i am currently coding for even numbers
    int x = 0;
    int y = 0;
    private Scanner input= new Scanner(System.in);
    //private String[] board;
    boolean currentPlayer;//some point, need to see if it is your turn
    boolean win = false;
    HumanPlayer humanPlayer = new HumanPlayer();
    ComputerPlayer computerPlayer = new ComputerPlayer();
    Card card = new Card();
    Card [][] Grid = new Card[ROWS][COL];
    ArrayList<Card> board = new ArrayList<>();
    int totalCards;
    int matches = 0;
    Card choice1;
    Card choice2;
    int f = 0;
    int s = 0;
    static boolean chosen = false;
    Integer colIndex;
    Integer rowIndex;
    Stage myStage;

    @Override
    public void start(Stage primaryStage) throws DeucesException {

        //set totalCards to be equal to the initRC call of card object
        totalCards = card.initRC(ROWS, COL);

        initializeArrayList(board);



        //to structure the grid and give it shape
        defineList(Grid, board);

        //set the first turn to user
        currentPlayer = true;

        //call setUpCard method to take board
        setUpCard(board);

        welcomeMessage = new Text("\t\tWelcome to The Card Matching Game!!!!\n");
        secondMessage = new Text("\tThis game will test your memory. Can you memorize everything in time?\n");

        //set welcome message to font and size
        welcomeMessage.setFont(Font.font("Optima", 24));
        secondMessage.setFont(Font.font("Optima", 18));

        //set welcome message to color
        welcomeMessage.setFill(Color.NAVY);
        secondMessage.setFill(Color.NAVY);

        //set welcome message to center
        welcomeMessage.setTextAlignment(TextAlignment.CENTER);
        secondMessage.setTextAlignment(TextAlignment.CENTER);

        //set buttons to have values
        startButton = new Button("Start");
        button1 = new Button("Easy");
        button2 = new Button("Medium");
        button3 = new Button("Hard");


        //Load the sound file and create the MediaPlayer
        File soundFile = new File("/Users/anastasiagolev/Documents/CarelessWhisperTrimmed.m4a");
        Media media = new Media(soundFile.toURI().toString());
        MediaPlayer player = new MediaPlayer(media);

        //Event handler for the OnEndOfMedia event
        player.setOnEndOfMedia(() ->
        {
            player.stop();
        });

        //create button for music
        Button musicButton = new Button("Click here for Music!");

        //event handler for the play button
        musicButton.setOnAction(e ->{
            player.play();
        });

        //creating HBox to place buttons on screen
        HBox buttonPlacement = new HBox(15, startButton, button1, button2, button3, musicButton);
        buttonPlacement.setAlignment(Pos.TOP_CENTER);

        //set the buttons to create scene to be differentiated by difficulty level of Computer player
        button1.setOnAction(e -> primaryStage.setScene(createScene(cardGrid)));
        button2.setOnAction(e -> primaryStage.setScene(createScene(cardGrid)));
        button3.setOnAction(e -> primaryStage.setScene(createScene(cardGrid)));
        startButton.setOnAction(e -> primaryStage.setScene(createScene(cardGrid)));

        VBox layout1 = new VBox(20);
        //setting layout to have a background color
        layout1.setStyle("-fx-background-color: aliceBlue");

        //displays Label welcomeMessage and difficulty level buttons
        layout1.getChildren().addAll(welcomeMessage, secondMessage, buttonPlacement);

        //create welcome screen
        welcomeScreen = new Scene(layout1, 640, 200);

        //create animation for welcome message
        double sceneWidth = welcomeScreen.getWidth();
        double msgWidth = welcomeMessage.getLayoutBounds().getWidth();

        KeyValue initKeyValue = new KeyValue(welcomeMessage.translateXProperty(), sceneWidth);
        KeyFrame initFrame = new KeyFrame(Duration.ZERO, initKeyValue);

        KeyValue endKeyValue = new KeyValue(welcomeMessage.translateXProperty(), -1.0
                * msgWidth);
        KeyFrame endFrame = new KeyFrame(Duration.seconds(10), endKeyValue);

        Timeline timeline = new Timeline(initFrame, endFrame);

        //setting the timeline to playe indefinitely
        timeline.setCycleCount(Timeline.INDEFINITE);
        //play the timeline
        timeline.play();



        //set up the scene
        primaryStage.setScene(welcomeScreen);
        //primaryStage.setScene(createScene());

        //show the stage
        primaryStage.show();
        primaryStage.centerOnScreen();



        //atempting to get the x and y coordinates of the column index and row index
        cardGrid.setOnMouseClicked(e ->{
            Node source = (Node)e.getSource() ;

            System.out.println(source);
            Integer colIndex = GridPane.getColumnIndex(source);
            Integer rowIndex = GridPane.getRowIndex(source);
            //System.out.printf("Mouse entered cell [%d, %d]%n", colIndex.intValue(), rowIndex.intValue());
            System.out.println("mouse entered cell " + colIndex + ", " + rowIndex);
        });

    }//end start()


    /*private Scene createScene methods takes a Card parameter. This method creates the
     * window scene of cards. Displaying them in a grid manner
     */
    private Scene createScene(Node card) {

        StackPane root = new StackPane();
        //add the one card to the scene (you will be doing this a lot)
        root.getChildren().addAll(card);
        Scene scene = new Scene(root, 610, 720, true, SceneAntialiasing.BALANCED);

        scene.setCamera(new PerspectiveCamera());
        root.setStyle("-fx-background-color: green");


        return scene;
    }//end createScene()

    /**
     * private Node createCard method assigns the height, width and properties to
     * an imageView object.
     * @param showFront
     * @param front
     * @param back
     * @return
     */
    private Node createCard(BooleanProperty showFront, Image front, Image back) {
        ImageView imageView = new ImageView();

        imageView.setFitHeight(front.getHeight());
        imageView.setFitWidth(front.getWidth());

        imageView.setFitHeight(175);
        imageView.setFitWidth(115);
        // show front/back depending on value of the showFront property
        imageView.imageProperty().bind(Bindings.when(showFront).then(front).otherwise(back));

        // mirror image, when backside is shown to prevent wrong orientation
        imageView.scaleXProperty().bind(Bindings.when(showFront).then(1d).otherwise(-1d));
        return imageView;
    } //end createCard()

    /**
     * creates the rotator to use in setUpCard method below
     * @param card
     * @param fromAngle
     * @param toAngle
     * @return
     */
    private RotateTransition createRotator(Node card, double fromAngle, double toAngle) {
        // animation length proportional to the rotation angle
        //If you want to alter speed, change the 1000 (less is faster, more is slower)
        RotateTransition rotator = new RotateTransition(Duration.millis(Math.abs(1000 * (fromAngle - toAngle) / 360)), card);
        rotator.setAxis(Rotate.Y_AXIS);
        rotator.setFromAngle(fromAngle);
        rotator.setToAngle(toAngle);
        rotator.setInterpolator(Interpolator.LINEAR);

        return rotator;
    }//end RotateTransition()


    /**
     * private ArrayList<Card> setUpCard method creates the animation for the flipping of the card.
     * Uses createCard methods to create cards within the method based on set proportions and to
     * keep animation uniform for all cards.
     * @param card
     * @return
     */
    private ArrayList<Card> setUpCard(ArrayList<Card> card) {


        for (int i = 0; i < card.size(); i++) {
            BooleanProperty showFront = new SimpleBooleanProperty(true);
            //AtomicReference is needed because you cannot use non-final
            //local variables in lambda expressions, as I do later in my
            //mouseOnClick event handler
            AtomicReference<Boolean> turnOver = new AtomicReference<Boolean>();
            turnOver.set(true);			//turn over is true when face is showing

            //System.out.println("1"+card.get(i).getFruitNames(i));
            //System.out.println("2"+card.get(i).getFruitNames(i).getImgName());

            Node card1 = createCard(showFront, new Image("blue_back.png"), new Image("" + card.get(i).getCardNames(i).getImgName()));

            //setting some spacing in between the cards
            cardGrid.setHgap(5);
            cardGrid.setVgap(5);

            //add the cards to root in rows of 4 and 4 columns
            if (i <4) {
                //cardGrid.add(card1, i, 0);
                cardGrid.getChildren().add(card1);
                GridPane.setColumnIndex(card1, i);
                GridPane.setRowIndex(card1,  0);
            }else if (i< 8) {
                cardGrid.getChildren().add(card1);
                GridPane.setColumnIndex(card1, i-4);
                GridPane.setRowIndex(card1,  1);
                //cardGrid.add(card1, i-4, 1);
            }else if (i<12) {
                cardGrid.getChildren().add(card1);
                GridPane.setColumnIndex(card1, i-8);
                GridPane.setRowIndex(card1,  2);
                //cardGrid.add(card1, i-8, 2);
            }else {
                cardGrid.getChildren().add(card1);
                GridPane.setColumnIndex(card1, i-12);
                GridPane.setRowIndex(card1,  3);
                //cardGrid.add(card1, i-12, 3);
            }



            // first 90� -> show front
            RotateTransition rotator1 = createRotator(card1, 0, 90);

            // from 90� to 180� show backside
            rotator1.setOnFinished(evt -> showFront.set(false));
            RotateTransition rotator2 = createRotator(card1, 90, 180);

            // from 180� to 270� show backside
            //rotator2.setOnFinished(evt -> showFront.set(true));
            RotateTransition rotator3 = createRotator(card1, 180, 270);
            //from 270� to 360� show front
            rotator3.setOnFinished(evt -> showFront.set(true));
            RotateTransition rotator4 = createRotator(card1, 270, 360);

            //turnOver1 is for flipping face up
            SequentialTransition turnOver1 = new SequentialTransition(card1, rotator1, rotator2);
            //turnOver2 is for flipping face down
            SequentialTransition turnOver2 = new SequentialTransition(card1, rotator3, rotator4);
            //You only want them to flip once per click
            turnOver1.setCycleCount(1);
            turnOver2.setCycleCount(1);

            //set the mouse event for this one card
            card1.setOnMouseClicked(event ->
            {
                System.out.println(turnOver.get());
                if ((Boolean)(turnOver.get())) {
                    turnOver1.play();
                } else {
                    turnOver2.play();
                }
                turnOver.set((Boolean)(!turnOver.get()));
            });

            //create an imageView object called img to use in timeline object
            ImageView img = new ImageView(card.get(i).getCardNames(i).getImgName());

            //set img ratios
            img.setX(100);
            img.setY(100);
            img.setFitWidth(150);
            img.setFitHeight(175);

            img.setPreserveRatio(true);



            //setting some spacing in between the cards
            cardGrid.setHgap(5);
            cardGrid.setVgap(5);

            //add the cards to root in rows of 4 and 4 columns
            if (i <4) {
                cardGrid.add(img, i, 0);
            }else if (i< 8) {
                cardGrid.add(img, i-4, 1);
            }else if (i<12) {
                cardGrid.add(img, i-8, 2);
            }else {
                cardGrid.add(img, i-12, 3);
            }

            //after 20 seconds, the cards will flip over
            Timeline timeline = new Timeline( new KeyFrame(Duration.seconds(10), new KeyValue(img.imageProperty(), null)));
            timeline.play();

        }
        return card;
    }//end setUpCard()

    //main method to launch args and initiate MatchingGame MG
    public static void main(String[] args) throws DeucesException {
        // write your code here
        launch(args);
        MatchingGame MG = new MatchingGame();


    }//end main()



    /*creates a Scene*/
    private static Scene createScene() {
        // TODO Auto-generated method stub

        StackPane root = new StackPane();
        Label label1 = new Label("Enter x coordinate here!");
        TextField text1 = new TextField();
        label1.setLabelFor(text1);
        root.getChildren().addAll(label1);
        root.getChildren().addAll(text1);
        Scene scene = new Scene(root, 610, 720, true, SceneAntialiasing.BALANCED);

        scene.setCamera(new PerspectiveCamera());
        root.setStyle("-fx-background-color: green");


        return scene;
    }//end createScene()

    /**
     * private boolean method inBounds checks if the user chosen cards is within the bounds of the
     * grid i.e. the row/column is valid
     * @param r
     * @param c
     * @return
     * @throws DeucesException
     */
    private boolean inBounds(int r, int c) throws DeucesException{
        if(r < 0 || r >= ROWS) throw new DeucesException("InValid row!");
        else if(c < 0 || c >= COL)throw new DeucesException("Invalid column!");
        else return true;
    }//end inBounds()

    /**
     * private void defineList saves the cards at a location on g which represents an 2D Array of Card
     * @param g
     * @param AL
     */
    private void defineList(Card[][] g, ArrayList<Card> AL){
        int ALIndex = 0;
        for (int i = 0; i < card.getRow(); i++){
            for (int j = 0; j < card.getCol(); j++){
                g[i][j] = AL.get(ALIndex);
                ALIndex++;//so i can get the next element in the arraylist and put it in the array at the next location
            }
        }
    }//end defineList()

    /**
     * private method initializeArrayList is meant to add shuffled cards to an arraylist b
     * @param b
     * @return
     */
    private ArrayList<Card> initializeArrayList(ArrayList<Card> b){
        //set how many diff names there
        card.setCardNames(card.getRow());


        //define the cards and put it into an arraylist
        b = addArrayList(b);


        //shuffle the list
        for (int i = 0; i < 50; i++){
            Collections.shuffle(b);

        }

        return b;
    }//end initializeArrayList()

    /**
     *
     * private ArrayList<Card> addArrayList adds a card to the ArrayList cList
     * @param cList
     * @return
     */
    private ArrayList<Card> addArrayList(ArrayList<Card> cList){
        //rows control how many dif cards
        for (int i = 0; i < ROWS; i++){
            for(int j = 0; j < COL; j++){
                //sets the value the different fruit based off the row
                cList.add(card);
            }
        }
        return cList;
    }//end addArrayList()

    /**
     * printGrid is meant to print the grid of the game with the name of the cards
     */
    private void printGrid() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COL - 1; j++) { // col - 1 so the last card on the right wont have boundaries
                System.out.print(Grid[i][j].getName() + " | ");
            }
            System.out.println(Grid[i][COL - 1].getName()); // just print the name, no boundaries
        }
    }//end printGrid()
    /**
     * boolean method decideWinner() decides the winner of the game depending
     * on which player has the most cards
     * @return
     */
    private boolean decideWinner(){
        if (totalCards / 2 == matches){
            if(humanPlayer.getPoints() > computerPlayer.getPoints()){
                System.out.println("User has won with " + humanPlayer.getPoints());
                return true;
            }
            else if(computerPlayer.getPoints() > humanPlayer.getPoints()){
                System.out.println("Computer has won with " + computerPlayer.getPoints());
                return true;
            }
            else if(humanPlayer.getPoints() == computerPlayer.getPoints()){
                System.out.println("It's a tie");
                return true;
            }
        }
        return false;
    }//end decideWinner()

    /**
     * boolean method isChosen() determines if the card has been chosen already
     * and changes the name of the "card" to blank to show that it is unavailable
     * @return
     */

    private boolean isChosen() {
        for (int a = 0; a < ROWS; a++) {
            for (int b = 0; b < COL; b++) {
                if (Grid[a][b].getName().equals("Blank")) return false;
            }
        }
        return true;
    }//end isChosen()

}
