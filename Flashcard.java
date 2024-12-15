abstract class Flashcard {
    public boolean is_correct = false; //checks if answer is correct
    protected String question; //the question.

    abstract void printOut(); //prints out the flashcard for usage
    abstract void printOutDebug(); //prints out the flashcard for debugging
    abstract public String toString();
}

class FlashcardText extends Flashcard {

private String answer; //answer to the question

    FlashcardText(String question, String answer) {
      this.question = question;
      this.answer = answer;
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
    public String toString(){
        return "question: " + question + " answer: " + answer;
    }

    void checkAnswer(String input) {
        if(input == answer){
            is_correct = true;
        }
        //no need to change to 0 it already is 0
    }


}

