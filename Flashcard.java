abstract class Flashcard {
    public boolean is_correct = false; //checks if answer is correct
    protected String question; //the question.
    public char type; //type of flashcard (so programme knows what to draw on screen)

    abstract void printOut(); //prints out the flashcard for usage
    abstract void printOutDebug(); //prints out the flashcard for debugging
    abstract public String toString();
    abstract void checkAnswer();
    abstract void overwriteValues(String new_message, int which_one); //function for changing content of flashcards
}

class FlashcardText extends Flashcard {

    private String answer; //answer to the question
    private String input_answer =""; //user's inputted answer, nothing by default

    FlashcardText(String question, String answer) {
      this.question = question;
      this.answer = answer;
      this.type = 't'; //t -> text
    }

    @Override
    void printOut() {
        System.out.println(question);
    }

    @Override
    void printOutDebug() {
        System.out.println("pytanie" +  question);
        System.out.println("odpowiedz" + answer);
    }

    @Override
    void overwriteValues(String new_message, int which_one) {
        switch(which_one) { //choosing what to overwrite
            case(0): {
                question = new_message;
                break;
            }
            case(1): {
                answer = new_message;
                break;
            }
            case(2): {
                input_answer = new_message; //btw this whole overwriting values function exists as an excuse for being able to save the input answer somewhere
                break;
            }
        }

    }

    @Override
    public String toString(){
        return "question: " + question + " answer: " + answer;
    }



    @Override
    void checkAnswer() {
        if(input_answer == answer){
            is_correct = true;
        }
        //no need to change to 0 it already is 0
    }


}

