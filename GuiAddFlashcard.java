import FlashcardTypes.Flashcard;
import FlashcardTypes.FlashcardABCD;
import FlashcardTypes.FlashcardText;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

/* Gui that pops up while add button is clicked */
public class GuiAddFlashcard
{
    private final JButton add_button;
    private final JButton close_button;

    //It is a group of radio buttons, each radio button represents different flashcard type
    private final ButtonGroup flashcard_type_group;

    private final JTextField text_answer;
    private final JTextField text_question;

    private String saved_question;
    private String saved_answer;

    private String current_file;


    private final JFrame add_flashcard_window;

    /**
     * Function is used to return the string based on selected radio button
     * @param buttonGroup is a group of radio buttons
     * @return <code>getActionCommand()</code> of selected radio button
     * */
    private String getSelectedButtonActionCommand(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getActionCommand();
            }
        }

        return null;
    }

    /**
     * @param filename indicate the name of catalogue we are adding a flashcard to.
     * */
    GuiAddFlashcard(String title, int width, int height,String filename)
    {
        // Init JFrame
        add_flashcard_window = new JFrame(title);
        add_flashcard_window.setSize(width, height);
        add_flashcard_window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add_flashcard_window.setLayout(new BorderLayout());


        //JPanel for input section
        JPanel input_panel = new JPanel();
        //top to bottom component placement
        input_panel.setLayout(new BoxLayout(input_panel, BoxLayout.Y_AXIS));

        JLabel add_question = new JLabel("Enter Question:");
        add_question.setAlignmentX(Component.CENTER_ALIGNMENT); //centers the field
        //input for reading question from user
        text_question = new JTextField();
        text_question.setMaximumSize(new Dimension(300, text_question.getPreferredSize().height));
        //button for saving question

        JLabel catalogue_name_label = new JLabel("You are adding a flashcard to "+filename);
        this.current_file = filename;
        catalogue_name_label.setAlignmentX(Component.CENTER_ALIGNMENT); //centers the field


        //same thing but for answer
        JLabel add_answer = new JLabel("Enter Answer:");
        add_answer.setAlignmentX(Component.CENTER_ALIGNMENT); //centers the field
        //input for reading answer from user
        text_answer = new JTextField();
        text_answer.setMaximumSize(new Dimension(300, text_question.getPreferredSize().height));
        //button for saving answer


        //adding question
        input_panel.add(add_question);
        input_panel.add(Box.createVerticalStrut(10)); //vertical distance
        input_panel.add(text_question);
        input_panel.add(Box.createVerticalStrut(10)); //vertical distance

        input_panel.add(Box.createVerticalStrut(40)); //vertical distance

        //adding answer
        input_panel.add(add_answer);
        input_panel.add(Box.createVerticalStrut(10)); //vertical distance
        input_panel.add(text_answer);
        input_panel.add(Box.createVerticalStrut(10)); //vertical distance
        input_panel.add(catalogue_name_label);

        add_flashcard_window.add(input_panel, BorderLayout.NORTH);

        //JPanel that allows user to choose desired flashcard type (e.g writing or yes/no question)
        JPanel flashcard_type_panel= new JPanel();
        //top to bottom component placement
        flashcard_type_panel.setLayout(new BoxLayout(flashcard_type_panel, BoxLayout.Y_AXIS));

        JLabel select_flashcard_type_label = new JLabel("Select flashcard type");
        //It is a group of radio buttons, each radio button represents different flashcard type
        this.flashcard_type_group = new ButtonGroup();
        /*If user chooses text_type, the text_type button returns string "text"*/
        JRadioButton text_type = new JRadioButton("Text (default)");
        text_type.setActionCommand("text");
        //Make text a default type of flashcard
        text_type.setSelected(true);

        /*If user chooses abcd_type, the text_type button returns string "abcd"*/
        JRadioButton abcd_type = new JRadioButton("ABCD");
        abcd_type.setActionCommand("abcd");
        flashcard_type_group.add(text_type);
        flashcard_type_group.add(abcd_type);

        flashcard_type_panel.add(select_flashcard_type_label);
        flashcard_type_panel.add(text_type);
        flashcard_type_panel.add(abcd_type);

        input_panel.add(Box.createVerticalStrut(40)); //vertical distance

        input_panel.add(Box.createVerticalStrut(10)); //vertical distance

        add_flashcard_window.add(flashcard_type_panel, BorderLayout.CENTER);



        // Init buttons
        add_button = new JButton("Add");
        close_button = new JButton("Close");

        // Create a panel at the bottom for the Add and Close buttons
        JPanel bottom_panel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Align buttons to the right
        bottom_panel.add(add_button);
        bottom_panel.add(close_button);

        add_flashcard_window.add(bottom_panel, BorderLayout.SOUTH); // Add to the bottom

        addButtonListeners();



        add_flashcard_window.setVisible(true);
    }

    /**
     * Function is responsible for binding a button to the operation of adding a flashcard.
     */
    private void addButtonListeners()
    {
        add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saved_question = text_question.getText();
                saved_answer = text_answer.getText();

                //We check which radio button was selected, below are possibilites
                /*
                * text - indicates that type of flashcard is text
                * abcd - indicated that type of flashcard is abcd
                * */
                String flashcard_type = getSelectedButtonActionCommand(flashcard_type_group);
                //We create flashcard based on its type,POLYMORPHISM!!!!!!!
                Flashcard new_flashcard;
                switch (flashcard_type) {
                    case "text":
                        System.out.println("Flashcard type is 'TEXT'.");
                        new_flashcard = new FlashcardText(saved_question,saved_answer); //Polymorpism
                        CustomFile.serializeFlashcard(current_file,new_flashcard);

                        break;
                    case "abcd":
                        System.out.println("Flashcard type is 'ABCD'.");

                        break;
                    default:
                        System.out.println("Unknown flashcard type.");

                        break;
                }


                JOptionPane.showMessageDialog(add_flashcard_window, "Flashcard added!");
                add_flashcard_window.dispose(); // Close the window
            }
        });

        close_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add_flashcard_window.dispose(); // Close the window
            }
        });




    }
}