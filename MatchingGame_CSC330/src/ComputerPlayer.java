import java.util.ArrayList;
import java.util.Scanner;

public class ComputerPlayer extends Player {
    /*protected Card firstCard;
    protected Card secondCard;
    protected int points = 0;
    protected boolean match;
    protected Card[][] availOptions;*/

    public ComputerPlayer() {
        match = false;
    }

    public ComputerPlayer(Card fC, Card sC) {
        super(fC, sC);
    }

    public boolean chooseFirst(int x, int y, Card[][] b) throws DeucesException {


        do {

            x = (int) ((Math.random() * 100) % 4);

            y = (int) ((Math.random() * 100) % 4);

            System.out.println("x: " + x);

            System.out.println("y: " + y);

            System.out.println(b[x][y].getName());
        } while (b[x][y].getName().equals("Blank"));
        firstCard = new Card(x, y, b);
        return true;


    }
    public boolean chooseSecond(int x, int y, Card[][] b) throws DeucesException {
        do {
            x = (int) ((Math.random() * 100) % 4);
            y = (int) ((Math.random() * 100) % 4);
            System.out.println("x: " + x);
            System.out.println("y: " + y);
            System.out.println(b[x][y].getName());
        } while (b[x][y].getName().equals("Blank"));
        secondCard = new Card(x, y, b);
        return true;

    }
    public boolean match(Card c1, Card c2){

        System.out.println(c1.getName() + " " + c2.getName());

        if (c1.getName().equals(c2.getName())) {
            c1.setChosen();
            c2.setChosen();
            System.out.println("The computer got a match.");
            return true;
        }
        else{
            return false;
        }
    }


    /*
     *//*
	/*public void easy method represents an easy level. The method takes one parameter,
	 * a 2D int array which represents the deck of cards to be shuffled and displayed
	 * to match. The computer will pick its cards randomly based on a random number
	 * generator.


	//at the moment we have 4 cards, so hard code a grid of 4 and create method in this way
	@Override
	public void easy(int[][] deck) {
		//TODO Auto-generated method stub
		//the deck will always have the same number of rows and columns so we can
		//just do deck.length
		int numRows = deck.length;
		int numCols = deck.length;
		//create an integer variable randPick1 and randPick2 to represent computer's random pair
		int randPick1 = (int)(Math.random() * (numCols - numRows + 1 )+ numRows);
		int randPick2 = (int)(Math.random() * (numCols - numRows + 1 )+ numRows);

		//computer player needs to pick two cards so we create a for loop to iterate twice
		for (int i = 0; i < 1; i++) {
			//first attempt, here the first and second card will be the same so need to alter this
			//maybe do 2 for loops or make another random generator, or rg within the for loop?
			firstCard[i][i] = firstCard[randPick1][randPick2];
			secondCard[i][i] = secondCard[randPick1][randPick2];
		}


	}

}*//*

        //easy method will just choose a random column and row: randPick1 and randPick2 respectively
        //and then it will choose this as its first card choice and second card choice
        public void easy() {
            // TODO Auto-generated method stub
            //the deck will always have the same number of rows and columns so we can
            //just do deck.length

            //create an integer variable randPick1 and randPick2 to represent computer's random pair




            //	}


        }
        // the medium difficulty will just choose a spot in card memory as its first choice and then go ahead and
        // choose a random choice for its second card

        public void medium() {
            // TODO Auto-generated method stub

            //for the first choice computer will randomly choose a spot inside the memory and if it was matched(true)
            //it will try to choose another card in memory



            int randPick1 = (int)(Math.random() * 4);
            int randPick2 = (int)(Math.random() * 4);
            secondCard[col][row] = firstCard[randPick1][randPick2];

        }
        //Hard difficulty. The hard dificulty will also choose a random spot in memory
        // then it will just search the memory array for a matching pair
        //if the string name matches then that will be its second choice
        public void hard() {
            // TODO Auto-generated method stub
            String fc;
            String sc = " ";
            //random choice in memory
            int randPick1 = (int)(Math.random() * 4);
            int randPick2 = (int)(Math.random() * 4);
            firstCard[col][row] = firstCard[randPick1][randPick2];
            //assign value of first card that spot in memory
            fc = memory[randPick1][randPick2];

            while(!fc.equals(sc)) {

                for(int i = 0; i<= col; i++) {

                    for(int j = 0; j<=row; j++) {
                        sc = memory[i][j];
                        if(firstCard.equals(sc)) {
                            secondCard[col][row] = secondCard[i][j];
                        }
                    }
                }
            }


        }*/

}