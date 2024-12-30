package FlashcardTypes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

public class FlashcardPanel extends JPanel {

    int counter = 0;
    private ArrayList<Flashcard> flashcards_list;


    private JTextField answer_field; //solely used for text flashcard
    private String answer = "";
    private String grade = "";
    private ButtonGroup TF_group;
    private ButtonGroup ABCD_group;

    private final JButton check = new JButton("Check");
    private final JButton next = new JButton("Next");
    private final JButton show = new JButton("Show Answer");

    private JLabel grade_label;
    private JLabel show_answer_label;


    private String getSelectedButtonActionCommand(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getActionCommand();
            }
        }

        return "null"; //nothing chosen option
    }

    public FlashcardPanel (ArrayList<Flashcard> flashcards)
    {;
        setBackground(Color.WHITE);
        this.flashcards_list = flashcards;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        addButtonListeners();
        showInitialUI();

    }

    private void showInitialUI()
    {
        add(Box.createVerticalStrut(10));
        JLabel question = new JLabel(flashcards_list.get(counter).printOut(0));
        question.setAlignmentX(CENTER_ALIGNMENT);
        add(question);
        add(Box.createVerticalStrut(10));
        switch (flashcards_list.get(counter).type) {
            case 't':
                text_UI(flashcards_list.get(counter));
                break;
            case 'f':
                TF_UI(flashcards_list.get(counter));
                break;
            case 'a':
                ABCD_UI(flashcards_list.get(counter));
                break;
            default:
                System.out.println("Unsupported flashcard type.");
                break;
        }

    }

    private void text_UI(Flashcard flashcard)
    {
        answer_field = new JTextField();
        answer_field.setMaximumSize(new Dimension(300, answer_field.getPreferredSize().height));
        answer_field.setAlignmentX(CENTER_ALIGNMENT);
        add(answer_field);

        showAfterQuestion();


    }

    private void TF_UI(Flashcard flashcard)
    {
        JRadioButton true_button = new JRadioButton("True");
        JRadioButton false_button = new JRadioButton("False");

        true_button.setActionCommand("true");
        false_button.setActionCommand("false");
        true_button.setBackground(Color.WHITE);
        false_button.setBackground(Color.WHITE);

        TF_group = new ButtonGroup();
        TF_group.add(true_button);
        TF_group.add(false_button);

        true_button.setAlignmentX(CENTER_ALIGNMENT);
        false_button.setAlignmentX(CENTER_ALIGNMENT);

        Box horizontal_box = Box.createHorizontalBox();
        horizontal_box.add(true_button);
        horizontal_box.add(Box.createRigidArea(new Dimension(30, 0)));
        horizontal_box.add(false_button);
        add(horizontal_box);

        showAfterQuestion();

    }
    private void ABCD_UI(Flashcard flashcard)
    {
        int how_many_options = Integer.parseInt(flashcard.printOut(3));
        ArrayList<JRadioButton> options = new ArrayList<>();;
        ABCD_group = new ButtonGroup();

        for(int i = 0; i < how_many_options; i++)
        {
            options.add(new JRadioButton(flashcard.printOut(4+i)));
            options.get(i).setActionCommand(String.valueOf(i+1)); //numbering from 1, not 0!!!
            options.get(i).setBackground(Color.WHITE);
            options.get(i).setAlignmentX(CENTER_ALIGNMENT);
            ABCD_group.add(options.get(i));
            add(options.get(i));
            add(Box.createVerticalStrut(10));
        }
        showAfterQuestion();
    }

    //creates a box to have 3 buttons in one line
    private Box buttonList()
    {
        Box horizontal_box = Box.createHorizontalBox(); //box to have 3 buttons next to each other
        horizontal_box.add(check);
        horizontal_box.add(Box.createRigidArea(new Dimension(10, 0))); // Space between buttons
        horizontal_box.add(next);
        horizontal_box.add(Box.createRigidArea(new Dimension(10, 0)));
        horizontal_box.add(show);
        return horizontal_box;
    }

    private void showAfterQuestion()
    {
        add(Box.createVerticalStrut(10));
        Box button_list_object = buttonList();
        button_list_object.setAlignmentX(CENTER_ALIGNMENT);
        add(button_list_object);

        add(Box.createVerticalStrut(10));
        grade_label = new JLabel(grade);//printing out the correctness
        grade_label.setAlignmentX(CENTER_ALIGNMENT);
        add(grade_label);

        add(Box.createVerticalStrut(10));
        show_answer_label = new JLabel(answer);
        show_answer_label.setAlignmentX(CENTER_ALIGNMENT);
        add(show_answer_label);
    }

    private void addButtonListeners()
    {
        check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (flashcards_list.get(counter).type) {
                    case 't':
                        flashcards_list.get(counter).overwriteValues(answer_field.getText(), 2);
                        break;
                    case 'f':
                        String selected_button_TF = getSelectedButtonActionCommand(TF_group);
                        switch (selected_button_TF){
                            case "true":
                                flashcards_list.get(counter).overwriteValues("", 3);
                                break;
                            case "false":
                                flashcards_list.get(counter).overwriteValues("", 4);
                                break;
                            default:
                            {
                                grade_label.setText("Choose Answer");
                                return;
                            }
                        }
                        break;
                    case 'a':
                        String selected_button_ABCD = getSelectedButtonActionCommand(ABCD_group);
                        switch(selected_button_ABCD)
                        {
                            case "1":
                            case "2":
                            case "3":
                            case "4":
                                flashcards_list.get(counter).overwriteValues(selected_button_ABCD, 2);
                                break;
                            default:
                            {
                                grade_label.setText("Choose Answer");
                                return;
                            }
                        }

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
                    case 'f':
                        answer = flashcards_list.get(counter).printOut(1);
                        show_answer_label.setText(answer);
                        break;
                    case 'a':
                        answer = flashcards_list.get(counter).printOut(3 + Integer.parseInt(flashcards_list.get(counter).printOut(1)));
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

                showInitialUI();

                revalidate(); // Ensure layout is updated
            }
        });


    }
}
