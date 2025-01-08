import FlashcardTypes.Flashcard;
import FlashcardTypes.FlashcardTF;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This class is used when user clicks inspect button and wants to browse flashcards. It gives him possibility of editing
 * them and deleting single flashcards.
 * */
public class GuiInspect extends JPanel {

    private String deck_path;
    private ArrayList<Flashcard>flashcards;
    private final ArrayList<singleFlashcard> flashcard_panels = new ArrayList<>();

    public GuiInspect(String path){

        //each time we invoke GuiInspect we want to make sure that number of single flashcards i initially zero
        this.deck_path = path;
        //Acquire all flashcards from given file
        flashcards = CustomFile.readSerializefFlashcard(path);
        System.out.println(flashcards);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //We gather panels for each question
        getDataForPanels();

        //Now we just iterate through a list of panels and just display them
        for(int i =0;i<flashcard_panels.size();i++){
            this.add(flashcard_panels.get(i));
        }

        //We create panel for save and close buttons
        JPanel button_panel = new JPanel();
        JButton save_button = new JButton("Save");
        button_panel.add(save_button);
        this.add(button_panel);

        //Binding save button for saving flashcards
        save_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("It should save ");
                //Make flashcards list empty
                flashcards.clear();
                CustomFile.clear(deck_path);
                //Iterate through all the panels and save flashcards they store
                for (int i = 0;i<flashcard_panels.size();i++) {
                    flashcards.add(flashcard_panels.get(i).returnFlashcard());
                    CustomFile.serializeFlashcard(deck_path,flashcards.get(i));
                }

            }
        });

    }

    /**
     * This function gets information about flashcards from file and creates panels to edit or remove them.
     * */
    private void getDataForPanels(){
        for (Flashcard flashcard : flashcards) {
            //We will create a single flashcard
            singleFlashcard sf;
            switch (flashcard.type) {
                //Any case is POLYMORPHISM becase we create particular types of flashcards
                //Create object of text flashcard
                case 't':
                    sf = new singleTextFlashcardPanel(flashcard);
                    flashcard_panels.add(sf);
                    break;
                //Create object of true/false flashcard
                case 'f':
                    sf = new singleTrueFalseFlashcardPanel(flashcard);
                    flashcard_panels.add(sf);

                    break;
                    //Create object of abcd flashcard
                case 'a':
                    sf = new singleABCDFlashcard(flashcard);
                    flashcard_panels.add(sf);
                    break;
            }

        }
    }




    abstract class singleFlashcard extends JPanel{
        protected JButton remove_button;


        //It stores the flashcard
        Flashcard flashcard;
        public singleFlashcard(Flashcard flashcard){
            this.remove_button = new JButton("Remove");
            this.remove_button.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.flashcard = flashcard;



            //programming remove button
            //Binding save button for saving flashcards
            remove_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //delete current flashcard
                    GuiInspect.this.remove(singleFlashcard.this);

                    flashcard_panels.remove(singleFlashcard.this);

                    //revalidate view
                    GuiInspect.this.revalidate();


                }
            });

        }

        /**
         * This function should return flashcard based on values from panel
         * */
        public abstract Flashcard returnFlashcard();

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
            super(flashcard);


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
            add(Box.createVerticalStrut(10)); //vertical distance

            add(separator);
        }

        @Override
        public Flashcard returnFlashcard() {
            this.flashcard.overwriteValues(question_field.getText(),0);
            this.flashcard.overwriteValues(answer_field.getText(),1);

            return this.flashcard;
        }
    }

    /**
     * Inner class that is used to display true/false flashcards
     * */
    class singleTrueFalseFlashcardPanel extends singleFlashcard{
        private JLabel question_label;
        private JTextField question_field;
        private  JRadioButton trueButton;
        private  JRadioButton falseButton;
        private  ButtonGroup group;
        public singleTrueFalseFlashcardPanel(Flashcard flashcard){

            super(flashcard);

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



            group = new ButtonGroup();
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
            add(Box.createVerticalStrut(10)); //vertical distance
            add(separator);
        }

        @Override
        public Flashcard returnFlashcard() {
            System.out.println(group.getSelection().getActionCommand());
            String selected_answer = group.getSelection().getActionCommand();

            //Overwrite question
            this.flashcard.overwriteValues(question_field.getText(),0);



            //overrwrite correct answer
            if(selected_answer=="true")
                this.flashcard.overwriteValues("",1);
            else
                this.flashcard.overwriteValues("",2);

            return flashcard;
        }
    }

    /**
     * Inner class that is used to display abcd flashcards
     * */
    class singleABCDFlashcard extends singleFlashcard{
        private JLabel question_label;
        private JTextField question_field;
        private JRadioButton [] option_radio_button;
        private JTextField[] option_fields;
        private JPanel[] option_panels;
        private ButtonGroup group;
        public singleABCDFlashcard(Flashcard flashcard){

            super(flashcard);

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
            option_radio_button =  new JRadioButton[n];
            option_fields = new JTextField[n];
            option_panels = new JPanel[n]; //Option panel =radio_button + text_field

            JPanel lower_panel = new JPanel();
            lower_panel.setLayout(new BoxLayout(lower_panel, BoxLayout.Y_AXIS));

            //Group for options(answers)
            group = new ButtonGroup();


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
            add(Box.createVerticalStrut(10)); //vertical distance
            add(separator);
        }

        @Override
        public Flashcard returnFlashcard() {
            String selected_answer = group.getSelection().getActionCommand();
            this.flashcard.overwriteValues(question_field.getText(),0);
            this.flashcard.overwriteValues(selected_answer,1);
            //override option values
            this.flashcard.overwriteValues(option_fields[0].getText(),3);
            this.flashcard.overwriteValues(option_fields[1].getText(),4);
            this.flashcard.overwriteValues(option_fields[2].getText(),5);
            this.flashcard.overwriteValues(option_fields[3].getText(),6);
            return flashcard;
        }
    }

}
