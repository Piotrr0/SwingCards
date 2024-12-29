package FlashcardTypes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FlashcardPanel extends JPanel {

    int counter = 0;
    private ArrayList<Flashcard> flashcards_list;


    private JTextField answer_field; //solely used for text flashcard
    private String answer = "";
    private String grade = "";

    private final JButton check = new JButton("Check");
    private final JButton next = new JButton("Next");
    private final JButton show = new JButton("Show Answer");

    private JLabel grade_label;
    private JLabel show_answer_label;




    public FlashcardPanel (ArrayList<Flashcard> flashcards)
    {;
        setBackground(Color.WHITE);
        this.flashcards_list = flashcards;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel question = new JLabel(flashcards.get(counter).printOut(0));//printing out the question
        question.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(10));
        add(question);
        answer_field = new JTextField();
        addButtonListeners();

        //determining the type of flashcard to display after the question
        switch(flashcards_list.get(counter).type)
        {
            case 't':
                text_UI(flashcards_list.get(counter));
                break;
            default:
                break;
        }

    }

    private void text_UI(Flashcard flashcard)
    {
        add(Box.createVerticalStrut(10));
        answer_field.setMaximumSize(new Dimension(300, answer_field.getPreferredSize().height));
        answer_field.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(answer_field);

        add(Box.createVerticalStrut(10));
        Box button_list_object = button_list();
        button_list_object.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(button_list_object);

        add(Box.createVerticalStrut(10));
        grade_label = new JLabel(grade);//printing out the correctness
        grade_label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(grade_label);

        add(Box.createVerticalStrut(10));
        show_answer_label = new JLabel(answer);
        show_answer_label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(show_answer_label);

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
                removeAll();//clears the thing and repaints with new flashcard yay
                repaint();
                //TODO make that a function
                JLabel question = new JLabel(flashcards_list.get(counter).printOut(0));
                question.setAlignmentX(Component.CENTER_ALIGNMENT);
                add(question);
                switch (flashcards_list.get(counter).type) {
                    case 't':
                        text_UI(flashcards_list.get(counter));
                        break;
                    default:
                        System.out.println("Unsupported flashcard type.");
                        break;
                }
                revalidate(); // Ensure layout is updated
            }
        });


    }
}
