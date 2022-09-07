import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class MatchingGame {
    public static final int ROWS = 4;
    public static final int COL = 4;//i am currently coding for even numbers
    //bug fixing led me to this random ints
    int x = 0;
    int y = 0;
    int f = 0;
    int s = 0;
    //int cpX = 0;
    //1int cpY = 0;

    GridPane pane = new GridPane();
    private Scanner input = new Scanner(System.in);
    //private String[] board;
    boolean currentPlayer;//some point, need to see if it is your turn
    boolean win = false;
    HumanPlayer humanPlayer = new HumanPlayer();
    ComputerPlayer computerPlayer = new ComputerPlayer();
    Card card = new Card();
    Card[][] Grid = new Card[ROWS][COL];
    ArrayList<Card> board = new ArrayList<>();
    int totalCards;
    int matches = 0;
    Card choice1;
    Card choice2;
    Card cpChoice1;
    Card cpChoice2;
    int cpX, cpY;
    int cpF, cpS;
    static boolean chosen = false;
    Card[] memory = new Card[4];
    //Grid grid = new Grid(board, card);
    private ObjectOutputStream out;
    private ObjectInputStream in;


    public MatchingGame() throws DeucesException {


        System.out.println("(Queue the music) DA DA DAD D DAD AD AD AD AD AD DA D AD AD AD D AD AD ADDADADADA DA DADA");
        System.out.println("Welcome to the Mario Party matching game");
        //OBJECT SERIALIZAION, LOADING GAME CAN BE PUT HERE
        //computerPlayer.initializeOptions(ROWS, COL, Grid);
        //initialize the computer players available options so later on it wont take forever to find a non null card
        //computerPlayer.initializeOptions(ROWS, COL);
        //board = new String[ROWS];
       /* if(computerPlayer.getClass().getSimpleName().equals("ComputerPlayer2")){
            //initialize the array so the first condition is not met
            //computerPlayer.mem();
        }
        else if(computerPlayer.getClass().getSimpleName().equals("ComputerPlayer3")){

        }*/

        System.out.print("Would you like to load a previous game? (Y/N)");
        String load = input.next();
        if (load.equalsIgnoreCase("y")) {
            loadGame();
        } else {
            totalCards = card.initRC(ROWS, COL); // initiate R and C
            initializeArrayList(board, totalCards);
            defineList(Grid, board);

        }
        System.out.println("Hooman goes first!");
        do { // WIN / DRAW
            System.out.println("Hooman: " + humanPlayer.getPoints() + "\t Computer: " + computerPlayer.getPoints());
            printGrid();
            //OBJECT SERIALIZATION CAN BUT SAVE GAME INPUT HERE
            //NEED INPUT OR ACTION HERE
            System.out.println("Do you want to save the game?");
            String saveInput = input.next();
            if (saveInput.equals("Y")) {
                saveGame();
                System.out.println("Game has been saved!");

            }
            do { //choose card
                try {
                    System.out.println("Choose your first card!");
                    System.out.println("x: ");
                    x = input.nextInt();
                    System.out.println("y: ");
                    y = input.nextInt();
                    if (inBounds(x, y) == true) {
                        humanPlayer.chooseFirst(x, y, Grid);
                        chosen = true;
                    } else ;
                } catch (Exception e) {
                    System.out.println("x: " + x + ", y: " + y + " did something bad.");
                    e.getMessage();
                    e.printStackTrace();
                    chosen = false;
                }
            } while (!chosen);
            //after it is an actual valid card i need to update the choice for comparing and something to help the
            // computer traverse for a non null card faster
            choice1 = Grid[x][y];
            /*if(computerPlayer.getClass().getSimpleName().equals("ComputerPlayer2")){
                computerPlayer.setMem1(choice1);
            }*/
            /*else if(computerPlayer.getClass().getSimpleName().equals("ComputerPlayer3")){
                computerPlayer.setMem(choice1);
            }*/

            //System.out.println(choice1.getImgName());
            //computerPlayer.setAvailOptions(Grid, x, y);
            do { // im thinking when a player chooses a card, the card will have a "backup (chosen) word" or just
                // become blank

                //if its a valid option, it will THEN assign it as an actual choice
                try {
                    System.out.println("Choose your second card.");
                    System.out.println("x: ");
                    f = input.nextInt();
                    System.out.println("y: ");
                    s = input.nextInt();
                    //if the card you chose is the same as the first card you chose
                    //i did it this way because i comparing the reference, if the card you just chose now has the same
                    // memory as the previous card, ERRR
                    if (Grid[f][s] == Grid[x][y]) {
                        throw new DeucesException("OOPS, you just chose that card");
                    } else {
                        if (inBounds(f, s) == true) {
                            humanPlayer.chooseSecond(f, s, Grid);
                            choice2 = Grid[f][s];
                            /*if(computerPlayer.getClass().getSimpleName().equals("ComputerPlayer2")){
                                computerPlayer.setMem2(choice2);
                            }*/
                            /*else if(computerPlayer.getClass().getSimpleName().equals("ComputerPlayer3")){
                                computerPlayer.setMem(choice2);
                            }*/
                            //System.out.println(choice2.getImgName());
                            //computerPlayer.setAvailOptions(Grid, x, y);
                            //in order to call this if, the card you just chose cannot be the first card you chose
                            if (humanPlayer.match(choice1, choice2)) { //true
                                System.out.println("Yay, you got a match!");
                                humanPlayer.addPoint();
                                matches++;
                                //xy is option 1, fs is option 2
                                //computerPlayer.availOptions[x][y].setChosen();
                                //computerPlayer.availOptions[f][s].setChosen();
                                chosen = true;
                            } else {
                                System.out.println("You did not get a match.");
                                //you still chose a card
                                chosen = true;
                            }
                        } else chosen = false;
                    }
                    ;


                } catch (Exception e) {
                    System.out.println("x: " + x + ", y: " + y + ", your second card did something bad");
                    e.getMessage();
                    e.printStackTrace();
                    chosen = false;
                }
            } while (!chosen);
            //CHECK IF THEY MATCH
            //choice1 = new Card(choice1.getName());
            //choice2 = new Card(choice2.getName());


            //computer turn
            System.out.println("Computer's turn!");
            do {
                //while the function keeps looping, it will realistically loop once
            } while (!computerPlayer.chooseFirst(cpX, cpY, Grid));
            cpChoice1 = Grid[computerPlayer.firstCard.getX()][computerPlayer.firstCard.getY()];
            /*System.out.println(computerPlayer.firstCard.getX() + " " + computerPlayer.firstCard.getY());
            System.out.println(cpChoice1.getName());*/
            //computerPlayer.setAvailOptions(Grid, x, y);
            do {
            } while (!computerPlayer.chooseSecond(cpF, cpS, Grid));
            cpChoice2 = Grid[computerPlayer.secondCard.getX()][computerPlayer.secondCard.getY()];

            System.out.println(cpChoice2.getName() + " @ " + cpChoice2.getX() + " " + cpChoice2.getY());

            //computerPlayer.setAvailOptions(Grid, x, y);
            if (computerPlayer.match(cpChoice1, cpChoice2)) {
                System.out.println("Computer player got a match!");
                computerPlayer.addPoint();
                matches++;
                //computerPlayer.availOptions[x][y].setChosen();
                //computerPlayer.availOptions[f][s].setChosen();
            }


            //after both players go, a win condition is checked
            win = decideWinner();
        } while (!win);


    }

    private boolean inBounds(int r, int c) throws DeucesException {
        if (r < 0 || r >= ROWS) throw new DeucesException("InValid row!");
        else if (c < 0 || c >= COL) throw new DeucesException("Invalid column!");
        else return true;
    }

    private void defineList(Card[][] g, ArrayList<Card> AL) {
        int ALIndex = 0;
        for (int i = 0; i < card.getRow(); i++) {
            for (int j = 0; j < card.getCol(); j++) {
                g[i][j] = AL.get(ALIndex);
                ALIndex++;//so i can get the next element in the arraylist and put it in the array at the next location
            }
        }
    }

    private void initializeArrayList(ArrayList<Card> b, int tC) {
        //set how many diff names there
        card.setCardNames(card.getRow());

        //define the cards and put it into an arraylist
        addArrayList(b);

        //shuffle the list
        for (int i = 0; i < 50; i++) {
            Collections.shuffle(b);
        }
    }

    private void addArrayList(ArrayList<Card> cList) {
        String name = "";
        //rows control how many dif cards
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COL; j++) {
                //sets the value the different Card based off the row
                cList.add(new Card(card.getCardNames(i + 1).name));
                //name = card.getCardNames(i + 1);
                //cList.add(new Card(name));
            }
        }
    }

    private void printGrid() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COL - 1; j++) { // col - 1 so the last card on the right wont have boundaries
                System.out.print(Grid[i][j].getName() + " | ");
            }
            System.out.println(Grid[i][COL - 1].getName()); // just print the name, no boundaries
        }
    }

    private boolean decideWinner() {
        if (totalCards / 2 == matches) {
            if (humanPlayer.getPoints() > computerPlayer.getPoints()) {
                System.out.println("Hooman has won with " + humanPlayer.getPoints());
                return true;
            } else if (computerPlayer.getPoints() > humanPlayer.getPoints()) {
                System.out.println("Computer has won with " + computerPlayer.getPoints());
                return true;
            } else if (humanPlayer.getPoints() == computerPlayer.getPoints()) {
                System.out.println("It's a tie");
                return true;
            }
        }
        return false;
    }


    private boolean isChosen() { //this will determine if the card is chosen - get functions
        for (int a = 0; a < ROWS; a++) {
            for (int b = 0; b < COL; b++) {
                if (Grid[a][b].getName().equals("Blank")) return false;
            }
        }
        return true;
    }

    private void saveGame() {
        try {
            out = new ObjectOutputStream(new FileOutputStream("Game"));
            out.writeObject(board);
            out.writeObject(Grid);
            out.close();
        } catch (Exception e) {
            System.out.println("Could not open da Game!");
            e.printStackTrace();
        }
    }

    private void loadGame() {
        try {
            in = new ObjectInputStream(new FileInputStream("Game"));
            board = (ArrayList<Card>) in.readObject();
            Grid = (Card[][]) in.readObject();
            in.close();
        } catch (Exception e) {
            System.out.println("Could not open da Game!");
            e.printStackTrace();
        }

    }
}