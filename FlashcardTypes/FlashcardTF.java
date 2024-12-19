package FlashcardTypes;

public class FlashcardTF extends Flashcard {

    boolean answer;
    boolean input_answer;

    public FlashcardTF(String question, boolean answer) {
        this.question = question;
        this.answer = answer;
        this.type = 'f'; //f -> false/true
    }

    @Override
    public String printOut(int which_one) {
        switch(which_one) { //incredibly useful for printing out specific parts of flashcard in swing
            case (0): {
                return question;
            }
            case (1): { //slightly different way of working with booleans
                return String.valueOf(answer);
            }
            case (2): {
                return String.valueOf(input_answer);
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
            case(1): { //again, different way of working with booleans
                answer = true;
                break;
            }
            case(2): {
                answer = false;
                break;
            }
            case(3): {
                input_answer = true;
                break;
            }
            case(4): {
                input_answer = false;
                break;
            }
            default:
            {
                return;
            }
        }

    }

    @Override
    public String toString(){
        return "question: " + question + " answer: " + answer;
    }

    @Override
    public void checkAnswer() {
        if(input_answer == answer){
            is_correct = true;
        }
        //no need to change to 0 it already is 0
    }
}

