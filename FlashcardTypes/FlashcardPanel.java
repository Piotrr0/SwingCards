package FlashcardTypes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.ThreadLocalRandom;


/**
 * This interface specifies what UIs in learn mode have to be implemented
 * */
interface FlashcardTestUIInterface{
    /**
     * Shows the common thing among all flashcards which is question at the top of the screen
     * */
    void showInitialUI();
    /**
     * Manages the UI for the flashcard that is of text type
     * @param flashcard particular flashcard to be displayed
     * */
    void text_UI(Flashcard flashcard);
    /**
     * Manages the UI for the flashcard that is of TRUE/FALSE type
     * @param flashcard particular flashcard to be displayed
     * */
    void TF_UI(Flashcard flashcard);
    /**
     * Manages the UI for the flashcard that is of ABCD type
     * @param flashcard particular flashcard to be displayed
     * */
    void ABCD_UI(Flashcard flashcard);

    /**
     * Shows information on screen after question.
     * */
    void showAfterQuestion();

}

public class FlashcardPanel extends JPanel implements FlashcardTestUIInterface{


    int counter;
    int how_many_correct = 0;
    int how_many_total;
    boolean finite_mode = false; //finite - correctly answered questions are removed
    private ArrayList<Flashcard> flashcards_list;
    boolean answered_correctly = false; //even if someone does weird stuff after they answered correctly it will be remembered


    private JTextField answer_field = new JTextField(); //solely used for text flashcard
    private String answer = "";
    private String grade = "";
    private ButtonGroup TF_group;
    private ButtonGroup ABCD_group;

    private final JButton check = new JButton("Check");
    private final JButton next = new JButton("Next");
    private final JButton show = new JButton("Show Answer");
    private final JButton skip = new JButton ("Skip");

    private final JButton infinite = new JButton("infinite");
    private final JButton finite = new JButton(" finite ");


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
    {


        setBackground(Color.WHITE);
        this.flashcards_list = flashcards;
        how_many_total = flashcards_list.size();
        counter = ThreadLocalRandom.current().nextInt(0, flashcards_list.size());
        System.out.println(counter);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        addButtonListeners();
        chooseLearningMode();

    }

    public void chooseLearningMode()
    {
        add(Box.createVerticalStrut(10));
        //spaces prevent text from being cut off
        JLabel mode_choose = new JLabel(" Choose Learning Mode ");
        mode_choose.setAlignmentX(CENTER_ALIGNMENT);
        add(mode_choose);
        add(Box.createVerticalStrut(10));
        infinite.setAlignmentX(CENTER_ALIGNMENT);
        add(infinite);
        add(Box.createVerticalStrut(10));
        finite.setAlignmentX(CENTER_ALIGNMENT);
        add(finite);
    }

    public void showInitialUI()
    {

        add(Box.createVerticalStrut(10));
        if(finite_mode)
        {
            JLabel progress = new JLabel(" Progress: " + how_many_correct + "\\" + how_many_total + " ");
            progress.setAlignmentX(CENTER_ALIGNMENT);
            add(progress);
            add(Box.createVerticalStrut(10));
        }
        //spaces prevent text from being cut off
        JLabel question = new JLabel(" " + flashcards_list.get(counter).printOut(0) + " ");
        question.setAlignmentX(CENTER_ALIGNMENT);
        add(question);
        add(Box.createVerticalStrut(10));
        //It switches between UI based on current flashcard type
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

    public void text_UI(Flashcard flashcard)
    {
        answer_field.setMaximumSize(new Dimension(300, answer_field.getPreferredSize().height));
        answer_field.setAlignmentX(CENTER_ALIGNMENT);
        add(answer_field);

        showAfterQuestion();


    }

    public void TF_UI(Flashcard flashcard)
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
    public void ABCD_UI(Flashcard flashcard)
    {
        int how_many_options = Integer.parseInt(flashcard.printOut(3));
        ArrayList<JRadioButton> options = new ArrayList<>();;
        ABCD_group = new ButtonGroup();

        for(int i = 0; i < how_many_options; i++)
        {
            options.add(new JRadioButton(flashcard.printOut(4+i)));
            options.get(i).setActionCommand(flashcard.printOut(4+i)); //action command is answer not number because random printing out
            options.get(i).setBackground(Color.WHITE);
            options.get(i).setAlignmentX(CENTER_ALIGNMENT);
            ABCD_group.add(options.get(i));

            //add(options.get(i));
            //add(Box.createVerticalStrut(10));
        }
        int random_ABCD;
        //randomly printing out ABCD options so you can't just memorise where the answer is
        while(!options.isEmpty())
        {
            random_ABCD = ThreadLocalRandom.current().nextInt(0, options.size());
            add(options.get(random_ABCD));
            add(Box.createVerticalStrut(10));
            options.remove(random_ABCD);
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

        if(finite_mode) //skip button for finite mode
        {
            horizontal_box.add(Box.createRigidArea(new Dimension(10, 0)));
            horizontal_box.add(skip);
        }
        return horizontal_box;
    }

    public void showAfterQuestion()
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
        infinite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finite_mode = false;
                removeAll();
                repaint();

                showInitialUI();

                revalidate();
            }
        });

        finite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finite_mode = true;
                removeAll();
                repaint();

                showInitialUI();

                revalidate();
            }
        });

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
                            case "null":
                                grade_label.setText("Choose Answer");
                                return;
                            default:
                            {
                                flashcards_list.get(counter).overwriteValues(selected_button_ABCD, 2);
                                break;
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
                    answered_correctly = true;
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
                if(finite_mode && answered_correctly)//finite mode
                {
                    flashcards_list.remove(counter);//removing correctly answered question
                    how_many_correct++;
                }
                answered_correctly = false;
                answer = "";
                grade = "";
                answer_field.setText("");
                removeAll();//clears the thing and repaints with new flashcard yay
                repaint();
                if(flashcards_list.isEmpty())
                {
                    add(Box.createVerticalStrut(10));
                    JLabel complete = new JLabel("All Questions Answered Correctly!");
                    complete.setAlignmentX(CENTER_ALIGNMENT);
                    add(complete);
                    revalidate();
                }
                else
                {
                    counter = ThreadLocalRandom.current().nextInt(0, flashcards_list.size());
                    showInitialUI();
                    revalidate();
                }
            }
        });

        skip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flashcards_list.get(counter).is_correct=false;

                flashcards_list.remove(counter);//removing skipped question
                how_many_correct++;

                answered_correctly = false;
                answer = "";
                grade = "";
                answer_field.setText("");
                removeAll();//clears the thing and repaints with new flashcard yay
                repaint();
                if(flashcards_list.isEmpty())
                {
                    add(Box.createVerticalStrut(10));
                    JLabel complete = new JLabel("All Questions Answered Correctly!");
                    complete.setAlignmentX(CENTER_ALIGNMENT);
                    add(complete);
                    revalidate();
                }
                else
                {
                    counter = ThreadLocalRandom.current().nextInt(0, flashcards_list.size());
                    showInitialUI();
                    revalidate();
                }
            }
        });


    }
}
