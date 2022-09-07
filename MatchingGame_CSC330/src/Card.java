public class Card {
    private int rAmount; // this var keeps track how many different cards there are
    private int cAmount; // when i call a function, this variable will determine how many cards will be in each
    private String name; // Card name
    private String chosenName = "Blank";//will change to name to blank if this card has been picked
    //private boolean chosen = false;
    private Info [] CardNames = new Info[4]; // names and img
    private int total;//available cards, determined later
    boolean chosen = false;
    private String imgName;
    int x;
    int y;
    //private Info Info[];

    //need images

    //when i declare a constructor(the only time) is initializing the options my computer has to not continuously
    // keeping getting a null card
    public Card() {
        name = "Blank";
    }
    //maybe
    public Card(int x ,int y, Card[][] b){
        name = b[x][y].getName();
        this.x = x;
        this.y = y;
        //chosen = true;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public Card(String name) {
        this.name = name;
    }


    public void setName(String name) { //used differently than the constructor
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setChosen() {
        name = chosenName; //"Blank"
        chosen = true;
    }
    public boolean getChosen(Card[][] g, int x, int y){ // need the location
        if(g[x][y].getName().equals("Blank")){
            return true;
        }
        else return false;
    }


    public Info[] setCardNames(int rows){//how many different types of Cards we need

        //Info[] i = {new Info("Ace of Spades", "AS.png")};
        if (rows == 4){

            //CardNames = new Info[4];
            CardNames[0] = new Info("Ace of Spades", "AS.png");
            CardNames[1] = new Info("Ace of Hearts", "AH.png");
            CardNames[2] = new Info("Ace of Diamonds", "AD.png");
            CardNames[3] = new Info("Ace of Clubs", "AC.png");

        }
        return CardNames;
    }
    public Info getCardNames(int i){
        if(i == 1) {
            return CardNames[0];
        }
        else if (i == 2){
            return CardNames[1];
        }
        else if (i == 3){
            return CardNames[2];
        }
        else{
            return CardNames[3];
        }

    }

    public int getCol() { // i dont want set functions because it implies the software will possibly give an option
        // to do so ... no no no,
        return cAmount;
    }
    public int getRow() {
        return rAmount;
    }

    //if in the main code, the R and C would change, this would chnage along with another function
    public int initRC(int r, int c){
        rAmount = r;
        cAmount = c;
        return total = r * c;
    }
}
class Info{
    String name;
    String imgName;

    public Info(String name, String imgName){
        this.name = name;
        this.imgName = imgName;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setImgName(String name){
        imgName = name;
    }
    public String getName(){
        return name;
    }
    public String getImgName(){
        return imgName;
    }
}

