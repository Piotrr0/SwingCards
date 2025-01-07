package FlashcardTypes;

public class FlashcardText extends Flashcard   {

    private String answer; //answer to the question
    private String input_answer = ""; //user's inputted answer, nothing by default

    public FlashcardText(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.type = 't'; //t -> text
    }

    @Override
    public String printOut(int which_one) {
        switch(which_one) { //incredibly useful for printing out specific parts of flashcard in swing
            case (0): {
                return question;
            }
            case (1): {
                return answer;
            }
            case (2): {
                return input_answer;
            }
            default:
            {
                return "";
            }
        }
    }

    @Override
    public void overwriteValues(String new_message, int which_one) {
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
            default:
            {
                return;
            }
        }

    }
    public void overwriteInputAnswer(String answer){
        input_answer = answer;
    }

    @Override
    public String toString(){
        return "question: " + question + " answer: " + answer;
    }

    @Override
    public void checkAnswer() {
        if(input_answer.equals(answer)){
            is_correct = true;
        }
        else {
            is_correct = false;
        }
        //no need to change to 0 it already is 0
    }

    public String getAnswer(){
        return this.answer;
    }
}