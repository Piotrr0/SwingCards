package FlashcardTypes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FlashcardPanel extends JPanel {

    int counter = 0;
    private ArrayList<Flashcard> flashcards_list;

    private final JPanel main_section;

    private JTextField answer_field; //solely used for text flashcard
    private String answer = "";
    private String grade = "";

    private final JButton check = new JButton("Check");
    private final JButton next = new JButton("Next");
    private final JButton show = new JButton("Show Answer");

    private JLabel grade_label;
    private JLabel show_answer_label;




    public FlashcardPanel (JPanel main_section, ArrayList<Flashcard> flashcards)
    {
        this.main_section = main_section;
        this.main_section.removeAll();//clears the thing
        this.main_section.repaint();
        this.main_section.revalidate();

        this.flashcards_list = flashcards;

        this.main_section.setLayout(new BoxLayout(this.main_section, BoxLayout.Y_AXIS));
        JLabel question = new JLabel(flashcards.get(counter).printOut(0));//printing out the question
        question.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.main_section.add(question);
        answer_field = new JTextField();
        addButtonListeners();

        //determining the type of flashcard to display after the question
        switch(flashcards_list.get(counter).type)
        {
            case 't':
                text_UI(this.main_section, flashcards_list.get(counter));
                break;
            default:
                break;
        }

    }

    private void text_UI(JPanel main_section, Flashcard flashcard)
    {
        main_section.add(Box.createVerticalStrut(10));
        answer_field.setMaximumSize(new Dimension(300, answer_field.getPreferredSize().height));
        answer_field.setAlignmentX(Component.CENTER_ALIGNMENT);
        main_section.add(answer_field);

        main_section.add(Box.createVerticalStrut(10));
        Box button_list_object = button_list();
        button_list_object.setAlignmentX(Component.CENTER_ALIGNMENT);
        main_section.add(button_list_object);

        main_section.add(Box.createVerticalStrut(10));
        grade_label = new JLabel(grade);//printing out the correctness
        grade_label.setAlignmentX(Component.CENTER_ALIGNMENT);
        main_section.add(grade_label);

        main_section.add(Box.createVerticalStrut(10));
        show_answer_label = new JLabel(answer);
        show_answer_label.setAlignmentX(Component.CENTER_ALIGNMENT);
        main_section.add(show_answer_label);

    }

    //creates a box to have 3 buttons in one line
    private Box button_list()
    {
        Box horizontal_box = Box.createHorizontalBox(); //box to have 3 buttons next to each other
        horizontal_box.add(check);
        horizontal_box.add(Box.createRigidArea(new Dimension(10, 0))); // Space between buttons
        horizontal_box.add(next);
        horizontal_box.add(Box.createRigidArea(new Dimension(10, 0)));
        horizontal_box.add(show);
        return horizontal_box;
    }

    private void addButtonListeners()
    {
        check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO add flashcard function to get rid of switches
                switch (flashcards_list.get(counter).type) {
                    case 't':
                        flashcards_list.get(counter).overwriteValues(answer_field.getText(), 2);
                        break;
                    default:
                        System.out.println("Unknown flashcard type.");

                        break;
                }
                flashcards_list.get(counter).checkAnswer();
                if(flashcards_list.get(counter).is_correct)
                {
                    grade = "Correct";
                }
                else
                {
                    grade = "Incorrect";
                }
                grade_label.setText(grade);
            }
        });

        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                switch (flashcards_list.get(counter).type) {
                    case 't':
                        answer = flashcards_list.get(counter).printOut(1);
                        show_answer_label.setText(answer);

                        break;
                    default:
                        System.out.println("Unknown flashcard type.");

                        break;
                }
            }
        });

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                flashcards_list.get(counter).is_correct=false;
                answer = "";
                grade = "";
                answer_field = new JTextField();
                counter = (counter+1)% flashcards_list.size();
                main_section.removeAll();//clears the thing and repaints with new flashcard yay
                main_section.repaint();
                //TODO make that a function
                JLabel question = new JLabel(flashcards_list.get(counter).printOut(0));
                question.setAlignmentX(Component.CENTER_ALIGNMENT);
                main_section.add(question);
                switch (flashcards_list.get(counter).type) {
                    case 't':
                        text_UI(main_section, flashcards_list.get(counter));
                        break;
                    default:
                        System.out.println("Unsupported flashcard type.");
                        break;
                }
                main_section.revalidate(); // Ensure layout is updated
            }
        });


    }
}
