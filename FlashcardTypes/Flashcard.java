package FlashcardTypes;

import java.io.Serializable;

public abstract class Flashcard implements Serializable {
    public boolean is_correct = false; //checks if answer is correct
    protected String question; //the question.
    public char type; //type of flashcard (so programme knows what to draw on screen)

    abstract String printOut(int which_one); //prints out specific part of flashcard for outputting
    abstract public String toString();
    abstract void checkAnswer();
    abstract void overwriteValues(String new_message, int which_one); //function for changing content of flashcards
    public static void appendToFile(Flashcard flashcard,String filepath){

        System.out.println("appending to file");
    }
}



