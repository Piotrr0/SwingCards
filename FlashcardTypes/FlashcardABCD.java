package FlashcardTypes;

import java.io.Serializable;
import java.util.ArrayList;

public class FlashcardABCD extends Flashcard {

    int input_answer;
    OptionsList option_list;

    //inner class
    class OptionsList implements Serializable {
        int how_many; //how many options
        int answer;
        ArrayList<String> options; //array for options

        OptionsList(int answer, ArrayList<String> options){
            this.how_many = options.size();
            this.answer = answer;
            this.options = options; //we have to .clear() the original ArrayList afterwards because it's a shallow copy and only passes reference
        }
        public void checkAnswer()
        {
            if (input_answer == answer)
            {
                is_correct = true;
            }
            else {
                is_correct = false;
            }
        }
    }


    public FlashcardABCD(String question, int answer, ArrayList<String> options) {
        this.question = question;
        option_list = new OptionsList(answer, options);
        this.type = 'a'; //a -> abcd
    }

    @Override
    public String printOut(int which_one) {
        switch(which_one) { //incredibly useful for printing out specific parts of flashcard in swing
            case (0): {
                return question;
            }
            case (1): { //slightly different way of working with booleans
                return String.valueOf(option_list.answer);
            }
            case (2): {
                return String.valueOf(input_answer);
            }
            case (3): {
                return String.valueOf(option_list.how_many);
            }
            default:
            {
                //if which_one is in boundaries of list of answers, which starts from 3 up if it was case
                if(which_one-4 < option_list.how_many && which_one-4 >= 0){
                    return option_list.options.get(which_one-4);
                }
                else return "";
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
            case(1): { //again, different way of working with this function
                int temp_num;
                try {
                    temp_num = Integer.parseInt(new_message);
                }
                catch (NumberFormatException e ){
                    return;
                }
                option_list.answer = temp_num;
                break;
            }
            case(2): {
                int temp_num;
                try {
                    temp_num = Integer.parseInt(new_message);
                }
                catch (NumberFormatException e ){
                    return;
                }
                input_answer = temp_num;
                break;
            }
            default:
            {
                //if which_one is in boundaries of list of answers, which starts from 3 up if it was case
                if(which_one-3 < option_list.how_many && which_one-3 >= 0){
                    option_list.options.set(which_one-3, new_message);
                }
                else return;
            }
        }

    }

    @Override
    public String toString(){
        return "question: " + question + " answer: " + option_list.answer + option_list.options;
    }

    @Override
    public void checkAnswer() {
       option_list.checkAnswer();
}

}


