import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/* Gui that pops up while add button is clicked */
public class GuiAddFlashcard
{
    private final JButton add_button;
    private final JButton close_button;
    private final JButton saveQuestionButton;
    private final JButton saveAnswerButton;

    private final JTextField textAnswer;
    private final JTextField textQuestion;

    private String savedQuestion;
    private String savedAnswer;



    private final JLabel savedQuestionLabel;
    private final JLabel savedAnswerLabel;

    private final JFrame add_flashcard_window;



    GuiAddFlashcard(String title, int width, int height)
    {
        // Init JFrame
        add_flashcard_window = new JFrame(title);
        add_flashcard_window.setSize(width, height);
        add_flashcard_window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add_flashcard_window.setLayout(new BorderLayout());


        //JPanel for input section
        JPanel inputPanel = new JPanel();
        //top to bottom component placement
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        JLabel addQuestion = new JLabel("Enter Question:");
        addQuestion.setAlignmentX(Component.CENTER_ALIGNMENT); //centers the field
        //input for reading question from user
        textQuestion = new JTextField();
        textQuestion.setMaximumSize(new Dimension(300, textQuestion.getPreferredSize().height));
        //button for saving question
        saveQuestionButton = new JButton("Save Question");
        saveQuestionButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        //same thing but for answer
        JLabel addAnswer = new JLabel("Enter Answer:");
        addAnswer.setAlignmentX(Component.CENTER_ALIGNMENT); //centers the field
        //input for reading answer from user
        textAnswer = new JTextField();
        textAnswer.setMaximumSize(new Dimension(300, textQuestion.getPreferredSize().height));
        //button for saving answer
        saveAnswerButton = new JButton("Save Answer");
        saveAnswerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        //adding question
        inputPanel.add(addQuestion);
        inputPanel.add(Box.createVerticalStrut(10)); //vertical distance
        inputPanel.add(textQuestion);
        inputPanel.add(Box.createVerticalStrut(10)); //vertical distance
        inputPanel.add(saveQuestionButton);
        inputPanel.add(Box.createVerticalStrut(40)); //vertical distance

        //adding answer
        inputPanel.add(addAnswer);
        inputPanel.add(Box.createVerticalStrut(10)); //vertical distance
        inputPanel.add(textAnswer);
        inputPanel.add(Box.createVerticalStrut(10)); //vertical distance
        inputPanel.add(saveAnswerButton);
        add_flashcard_window.add(inputPanel, BorderLayout.NORTH);

        //JPanel for showing current saved stuff
        JPanel outputPanel = new JPanel();
        //top to bottom component placement
        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));

        //output labels
        savedQuestionLabel= new JLabel("Saved Question: ");
        savedQuestionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        savedAnswerLabel = new JLabel("Saved Answer: ");
        savedAnswerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        inputPanel.add(Box.createVerticalStrut(40)); //vertical distance
        outputPanel.add(savedQuestionLabel);
        inputPanel.add(Box.createVerticalStrut(10)); //vertical distance
        outputPanel.add(savedAnswerLabel);
        add_flashcard_window.add(outputPanel, BorderLayout.CENTER);



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

    private void addButtonListeners()
    {
        add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

        saveQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Save question from text field
                savedQuestion = textQuestion.getText();

                // Display the saved text in the label
                savedQuestionLabel.setText("Saved Question: " + savedQuestion);
            }

        });

        saveAnswerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Save question from text field
                savedAnswer = textAnswer.getText();

                // Display the saved text in the label
                savedAnswerLabel.setText("Saved Answer: " + savedAnswer);
            }

        });
    }
}