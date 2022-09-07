public abstract class Player {

    protected Card firstCard;
    protected Card secondCard;
    protected int points = 0;
    protected boolean match;

    public Player(){
        match = false;
    }
    public Player(Card fC, Card sC){
        firstCard = fC;
        secondCard = sC;
    }
    public Card getFirstCard(){
        return firstCard;
    }
    public void addPoint(){
        points++;
    }
    public int getPoints(){
        return points;
    }
    public void setFirstCard(int x, int y, Card [][] g){
        firstCard = g[x][y];
    }



    public abstract boolean chooseFirst(int x, int y, Card[][] Grid) throws DeucesException;
    public abstract boolean chooseSecond(int x, int y, Card [][] Grid) throws DeucesException;
    public abstract boolean match(Card c1, Card c2);
    public void act() {
        // TODO Auto-generated method stub

    }


}