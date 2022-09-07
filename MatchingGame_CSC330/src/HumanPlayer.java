import java.util.Scanner;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class HumanPlayer extends Player{

    public HumanPlayer(){
        match = false;
    }
    public HumanPlayer(Card FC, Card SC){ //also can be when they choose a card,
        super(FC, SC);
    }

    public Card getFirsCard(){
        return firstCard;
    }
    public Card getSecondCard(){
        return secondCard;
    }



    public boolean chooseFirst(int x, int y, Card[][] b) throws DeucesException{
        //if this card was chosen, boolean is still false, do while loop again
        if (b[x][y].getName().equals("Blank")) {
            throw new DeucesException("OOPS, that card has already been chosen.");
        }
        else{
            firstCard = new Card(x, y, b);
            //prints the name of the card
            System.out.println("You chose " + firstCard.getName());
            return true;
        }


    }
    public boolean chooseSecond(int x, int y, Card[][] b) throws DeucesException{
        if(b[x][y].getName().equals("Blank")) {
            throw new DeucesException("OOPS, that card has already been chosen");
        }
        else{
            secondCard = new Card(x, y, b);
            //prints the name of the card
            System.out.println("You chose " + secondCard.getName());
            return true;
        }
    }

    public boolean match(Card c1, Card c2){
        if(c1.getName().equals(c2.getName())){
            c1.setChosen();
            c2.setChosen();
            System.out.println("Hooray, you got a match! ");
            return true;
        }
        else {
            return false;
        }
    }


}
