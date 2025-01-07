import FlashcardTypes.Flashcard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * This class is used when user clicks inspect button and wants to browse flashcards. It gives him possibility of editing
 * them and deleting single flashcards.
 * */
public class GuiInspect extends JPanel {

    private String deck_path;
    private ArrayList<Flashcard>flashcards;
    private ArrayList<singleFlashcard> flashcard_panels;

    public GuiInspect(String path){
        this.deck_path = path;
        //Acquire all flashcards from given file
        flashcards = CustomFile.readSerializefFlashcard(path);
        System.out.println(flashcards);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        getDataForPanels();



    }

    /**
     * This function gets information about flashcards from file and creates panels to edit or remove them.
     * */
    private void getDataForPanels(){
        for (Flashcard flashcard : flashcards) {
            switch (flashcard.type) {
                //Create object of text flashcard
                case 't':
                    singleFlashcard s = new singleTextFlashcardPanel(flashcard);
                    add(s);
                    break;
                //Create object of true/false flashcard
                case 'f':
                    singleFlashcard x = new singleTrueFalseFlashcardPanel(flashcard);
                    add(x);
                    break;
                    //Create object of abcd flashcard
                case 'a':
                    singleFlashcard g = new singleABCDFlashcard(flashcard);
                    add(g);
                    break;
            }
        }
    }




    abstract class singleFlashcard extends JPanel{
        protected JButton remove_button;
        public singleFlashcard(){
            this.remove_button = new JButton("Remove");
            this.remove_button.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
    }

    /**
     * Inner class that is used to display text flashcards
     * */
    class singleTextFlashcardPanel extends singleFlashcard{
        private JLabel question_label;
        private JLabel answer_label;
        private JTextField question_field;
        private JTextField answer_field;
        public singleTextFlashcardPanel(Flashcard flashcard){


            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


            question_label = new JLabel("Enter Question:");
            question_label.setAlignmentX(Component.CENTER_ALIGNMENT); //centers the field
            //same thing but for answer
            answer_label = new JLabel("Enter Answer:");
            answer_label.setAlignmentX(Component.CENTER_ALIGNMENT); //centers the field


            question_field= new JTextField(flashcard.printOut(0));
            question_field.setMaximumSize(new Dimension(300, question_field.getPreferredSize().height));
            answer_field = new JTextField(flashcard.printOut(1));
            answer_field.setMaximumSize(new Dimension(300, answer_field.getPreferredSize().height));


            //adding question
            add(question_label);
            add(Box.createVerticalStrut(10)); //vertical distance
            add(question_field);


            add(Box.createVerticalStrut(20)); //vertical distance
            add(answer_label);
            add(answer_field);

            JSeparator separator = new JSeparator();
            separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1)); // Stretch width, fixed height
            separator.setForeground(Color.BLACK); // Optional: Change color
            add(remove_button);
            add(separator);
        }
    }

    class singleTrueFalseFlashcardPanel extends singleFlashcard{
        private JLabel question_label;
        private JTextField question_field;
        private  JRadioButton trueButton;
        private  JRadioButton falseButton;
        public singleTrueFalseFlashcardPanel(Flashcard flashcard){


            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


            question_label = new JLabel("Enter Question:");
            question_label.setAlignmentX(Component.CENTER_ALIGNMENT); //centers the field
            question_field= new JTextField(flashcard.printOut(0));
            question_field.setMaximumSize(new Dimension(300, question_field.getPreferredSize().height));







            //adding question
            add(question_label);
            add(Box.createVerticalStrut(10)); //vertical distance
            add(question_field);

            add(Box.createVerticalStrut(20)); //vertical distance

            //Creating panel for true/false button
            JPanel true_false_panel = new JPanel();

            true_false_panel.setLayout(new FlowLayout());



            //Creating radio buttons for true/false
            trueButton = new JRadioButton("True");
            falseButton = new JRadioButton("False");
            trueButton.setActionCommand("true");
            falseButton.setActionCommand("false");

            //When you inspect flashcard correct answer should be selected
            Boolean correct_ans = Boolean.valueOf(flashcard.printOut(1));

            if(correct_ans)
                trueButton.setSelected(true);
            else
                falseButton.setSelected(true);



            ButtonGroup group = new ButtonGroup();
            group.add(trueButton);
            group.add(falseButton);

            true_false_panel.add(trueButton);
            true_false_panel.add(falseButton);
            true_false_panel.setMaximumSize(new Dimension(300, true_false_panel.getPreferredSize().height));


            add(true_false_panel);
            JSeparator separator = new JSeparator();
            separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1)); // Stretch width, fixed height
            separator.setForeground(Color.BLACK); // Optional: Change color
            add(remove_button);
            add(separator);
        }
    }

    class singleABCDFlashcard extends singleFlashcard{
        private JLabel question_label;
        private JTextField question_field;
        public singleABCDFlashcard(Flashcard flashcard){


            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


            question_label = new JLabel("Enter Question:");
            question_label.setAlignmentX(Component.CENTER_ALIGNMENT); //centers the field
            question_field= new JTextField(flashcard.printOut(0));
            question_field.setMaximumSize(new Dimension(300, question_field.getPreferredSize().height));
            //same thing but for answer

            //adding question
            add(question_label);
            add(Box.createVerticalStrut(10)); //vertical distance
            add(question_field);

            add(Box.createVerticalStrut(20)); //vertical distance

            //number of options
            int n = Integer.valueOf(flashcard.printOut(3));

            //We create arrays of radio buttons,text fields and panels
            JRadioButton [] option_radio_button =  new JRadioButton[n];
            JTextField[] option_fields = new JTextField[n];
            JPanel[] option_panels = new JPanel[n]; //Option panel =radio_button + text_field

            JPanel lower_panel = new JPanel();
            lower_panel.setLayout(new BoxLayout(lower_panel, BoxLayout.Y_AXIS));

            //Group for options(answers)
            ButtonGroup group = new ButtonGroup();


            //All options should have the same GUIS thus we use a loop.
            for(int i = 0;i<option_panels.length;i++) {
                lower_panel.add(Box.createVerticalStrut(10)); //vertical distance


                option_radio_button[i] = new JRadioButton("Option" + (i + 1));
                option_radio_button[i].setAlignmentX(Component.LEFT_ALIGNMENT);
                option_radio_button[i].setActionCommand(String.valueOf(i + 1));
                group.add(option_radio_button[i]);


                //Jezeli teraz iterujemy przez poprawna odpowiedz to ja zaznaczmy
                if(i == Integer.valueOf(flashcard.printOut(1))){
                    option_radio_button[i-1].setSelected(true);
                }

                option_fields[i] = new JTextField(flashcard.printOut(4+i));
                option_panels[i] = new JPanel();
                option_panels[i].add(option_radio_button[i]);
                option_panels[i].add(option_fields[i]);
                option_panels[i].setLayout(new BoxLayout(option_panels[i], BoxLayout.X_AXIS));
                lower_panel.add(option_panels[i]);



            }
            lower_panel.setMaximumSize(new Dimension(300, lower_panel.getPreferredSize().height));

            add(lower_panel);
            JSeparator separator = new JSeparator();
            separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1)); // Stretch width, fixed height
            separator.setForeground(Color.BLACK); // Optional: Change color
            add(remove_button);
            add(separator);
        }
    }

}
