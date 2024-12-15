import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/* Gui that pops up while add button is clicked */
public class GuiAddFlashcard
{
    private final JButton add_button;
    private final JButton close_button;
    private final JButton save_question_button;
    private final JButton save_answer_button;

    private final JTextField text_answer;
    private final JTextField text_question;

    private String saved_question;
    private String saved_answer;

    private final JLabel saved_question_label;
    private final JLabel saved_answer_label;

    private final JFrame add_flashcard_window;



    GuiAddFlashcard(String title, int width, int height, Vector<Flashcard> text_ones)
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
        save_question_button = new JButton("Save Question");
        save_question_button.setAlignmentX(Component.CENTER_ALIGNMENT);

        //same thing but for answer
        JLabel add_answer = new JLabel("Enter Answer:");
        add_answer.setAlignmentX(Component.CENTER_ALIGNMENT); //centers the field
        //input for reading answer from user
        text_answer = new JTextField();
        text_answer.setMaximumSize(new Dimension(300, text_question.getPreferredSize().height));
        //button for saving answer
        save_answer_button = new JButton("Save Answer");
        save_answer_button.setAlignmentX(Component.CENTER_ALIGNMENT);

        //adding question
        input_panel.add(add_question);
        input_panel.add(Box.createVerticalStrut(10)); //vertical distance
        input_panel.add(text_question);
        input_panel.add(Box.createVerticalStrut(10)); //vertical distance
        input_panel.add(save_question_button);
        input_panel.add(Box.createVerticalStrut(40)); //vertical distance

        //adding answer
        input_panel.add(add_answer);
        input_panel.add(Box.createVerticalStrut(10)); //vertical distance
        input_panel.add(text_answer);
        input_panel.add(Box.createVerticalStrut(10)); //vertical distance
        input_panel.add(save_answer_button);
        add_flashcard_window.add(input_panel, BorderLayout.NORTH);

        //JPanel for showing current saved stuff
        JPanel output_panel = new JPanel();
        //top to bottom component placement
        output_panel.setLayout(new BoxLayout(output_panel, BoxLayout.Y_AXIS));

        //output labels
        saved_question_label= new JLabel("Saved Question: ");
        saved_question_label.setAlignmentX(Component.CENTER_ALIGNMENT);
        saved_answer_label = new JLabel("Saved Answer: ");
        saved_answer_label.setAlignmentX(Component.CENTER_ALIGNMENT);

        input_panel.add(Box.createVerticalStrut(40)); //vertical distance
        output_panel.add(saved_question_label);
        input_panel.add(Box.createVerticalStrut(10)); //vertical distance
        output_panel.add(saved_answer_label);
        add_flashcard_window.add(output_panel, BorderLayout.CENTER);



        // Init buttons
        add_button = new JButton("Add");
        close_button = new JButton("Close");

        // Create a panel at the bottom for the Add and Close buttons
        JPanel bottom_panel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Align buttons to the right
        bottom_panel.add(add_button);
        bottom_panel.add(close_button);

        add_flashcard_window.add(bottom_panel, BorderLayout.SOUTH); // Add to the bottom

        addButtonListeners(text_ones);

        add_flashcard_window.setVisible(true);
    }

    private void addButtonListeners(Vector<Flashcard> text_ones)
    {
        add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                text_ones.add(new FlashcardText(saved_question, saved_answer)); //polymorphism!
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

        save_question_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Save question from text field
                saved_question = text_question.getText();

                // Display the saved answer in the label
                saved_question_label.setText("Saved Question: " + saved_question);
            }

        });

        save_answer_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Save question from text field
                saved_answer = text_answer.getText();

                // Display the saved text in the label
                saved_answer_label.setText("Saved Answer: " + saved_answer);
            }

        });
    }
}