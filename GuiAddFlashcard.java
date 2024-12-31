import FlashcardTypes.Flashcard;
import FlashcardTypes.FlashcardABCD;
import FlashcardTypes.FlashcardTF;
import FlashcardTypes.FlashcardText;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;

/* Gui that pops up while add button is clicked */
public class GuiAddFlashcard
{
    private final JButton add_button;
    private final JButton close_button;

    //It is a group of radio buttons, each radio button represents different flashcard type
    private final ButtonGroup flashcard_type_group;



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
        this.add_flashcard_window = new JFrame(title);
        add_flashcard_window.setSize(width, height);
        add_flashcard_window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add_flashcard_window.setLayout(new BorderLayout());

        this.current_file = filename;


        //JPanel for input section
        JPanel input_panel = new JPanel();
        //top to bottom component placement
        input_panel.setLayout(new BoxLayout(input_panel, BoxLayout.Y_AXIS));



        // Init buttons
        add_button = new JButton("Add");
        close_button = new JButton("Close");



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
        //Event trigerred when user decides that they want flashcard of text type.
        text_type.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                displayAddTextFlashcardGui(input_panel,filename);

                input_panel.revalidate();
            }
        });



        text_type.setActionCommand("text");
        //Make text a default type of flashcard
        text_type.doClick(); //it is clicked so it performs event of showing right input_panel
        text_type.setSelected(true);

        // true/false option
        JRadioButton tf_type = new JRadioButton(("True/False"));
        tf_type.setActionCommand("tf");
        //Event for selecting true/false flashcard
        tf_type.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAddTFFlashcardGui(input_panel,filename);

                input_panel.revalidate();
            }
        });

        /*If user chooses abcd_type, the text_type button returns string "abcd"*/
        JRadioButton abcd_type = new JRadioButton("ABCD");
        abcd_type.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAddABCDlashcardGui(input_panel,filename);

                input_panel.revalidate();
            }
        });
        abcd_type.setActionCommand("abcd");
        flashcard_type_group.add(text_type);
        flashcard_type_group.add(tf_type);
        flashcard_type_group.add(abcd_type);


        flashcard_type_panel.add(select_flashcard_type_label);
        flashcard_type_panel.add(text_type);
        flashcard_type_panel.add(tf_type);
        flashcard_type_panel.add(abcd_type);



        add_flashcard_window.add(flashcard_type_panel, BorderLayout.CENTER);





        // Create a panel at the bottom for the Add and Close buttons
        JPanel bottom_panel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Align buttons to the right
        bottom_panel.add(add_button);
        bottom_panel.add(close_button);

        add_flashcard_window.add(bottom_panel, BorderLayout.SOUTH); // Add to the bottom

        addButtonListeners();



        add_flashcard_window.setVisible(true);
    }


    /**
     * This function changes <code>input_panel</code> so you can create text flashcard. It takes responsibility of binding and rebinding add_button
     * */
    private void displayAddTextFlashcardGui(JPanel input_panel,String filename){
        input_panel.removeAll();

        // Remove all existing listeners from the add_button
        for (ActionListener listener : add_button.getActionListeners()) {
            add_button.removeActionListener(listener);
        }


        //input for reading question from user
        JTextField text_question = new JTextField();
        text_question.setMaximumSize(new Dimension(300, text_question.getPreferredSize().height));

        //input for reading answer from user
        JTextField text_answer = new JTextField();
        text_answer.setMaximumSize(new Dimension(300, text_question.getPreferredSize().height));


        JLabel add_question = new JLabel("Enter Question:");
        add_question.setAlignmentX(Component.CENTER_ALIGNMENT); //centers the field
        //same thing but for answer
        JLabel add_answer = new JLabel("Enter Answer:");
        add_answer.setAlignmentX(Component.CENTER_ALIGNMENT); //centers the field

        //adding question
        input_panel.add(add_question);
        input_panel.add(Box.createVerticalStrut(10)); //vertical distance
        input_panel.add(text_question);
        input_panel.add(Box.createVerticalStrut(10)); //vertical distance

        input_panel.add(Box.createVerticalStrut(40)); //vertical distance

        //adding answer
        input_panel.add(add_answer);

        input_panel.add(text_answer);


        JLabel catalogue_name_label = new JLabel("You are adding a flashcard to "+filename);

        catalogue_name_label.setAlignmentX(Component.CENTER_ALIGNMENT); //centers the field

        input_panel.add(catalogue_name_label);

        //Bindind add button to create text flashcard
        add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //We create flashcard based on its type,POLYMORPHISM!!!!!!!
                Flashcard new_flashcard;
                new_flashcard = new FlashcardText(text_question.getText(), text_answer.getText()); //Polymorpism
                CustomFile.serializeFlashcard(current_file,new_flashcard);
                System.out.println("Im trying to add text_flashcard");

                CustomFile.appendToReport("User added text flashcard:["+new_flashcard+"]","raport.txt",true);
                JOptionPane.showMessageDialog(add_flashcard_window, "Flashcard added!");
                add_flashcard_window.dispose(); // Close the window

            }
        });
    }

    /**
     * This function changes <code>input_panel</code> so you can create True/False flashcard. It takes responsibility of binding and rebinding add_button
     * */
    private  void displayAddTFFlashcardGui(JPanel input_panel,String filename){
        input_panel.removeAll();
        // Remove all existing listeners from the add_button
        for (ActionListener listener : add_button.getActionListeners()) {
            add_button.removeActionListener(listener);
        }

        JLabel question_label = new JLabel("Enter Question:");
        question_label.setAlignmentX(Component.CENTER_ALIGNMENT);


        JTextField question_field = new JTextField();
        question_field.setMaximumSize(new Dimension(300, question_field.getPreferredSize().height));
        question_field.setAlignmentX(Component.CENTER_ALIGNMENT);



        input_panel.add(question_label);
        input_panel.add(Box.createVerticalStrut(10));
        input_panel.add(question_field);
        input_panel.add(Box.createVerticalStrut(10)); //vertical distance


        //Creating radio buttons for true/false
        JRadioButton trueButton = new JRadioButton("True");
        JRadioButton falseButton = new JRadioButton("False");
        trueButton.setActionCommand("true");
        falseButton.setActionCommand("false");
        trueButton.setSelected(true);

        trueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        falseButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        ButtonGroup group = new ButtonGroup();
        group.add(trueButton);
        group.add(falseButton);

        input_panel.add(trueButton);
        input_panel.add(falseButton);

        input_panel.add(Box.createVerticalStrut(10)); //vertical distance


        //Bindind add button for saving true/false flashcard
        add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //We create flashcard based on its type,POLYMORPHISM!!!!!!!
                Flashcard new_flashcard;
                String selected_button = getSelectedButtonActionCommand(group);
                Boolean answer = false;
                if(selected_button=="true")
                    answer = true;
                new_flashcard = new FlashcardTF(question_field.getText(),answer);
                CustomFile.serializeFlashcard(current_file,new_flashcard);

                System.out.println("Im trying to add tf_flashcard:"+new_flashcard);

                CustomFile.appendToReport("User added True/False flashcard:["+new_flashcard+"]","raport.txt",true);
                JOptionPane.showMessageDialog(add_flashcard_window, "Flashcard added!");
                add_flashcard_window.dispose(); // Close the window
            }
        });
    }

    private  void displayAddABCDlashcardGui(JPanel input_panel,String filename){
        input_panel.removeAll();
        // Remove all existing listeners from the add_button
        for (ActionListener listener : add_button.getActionListeners()) {
            add_button.removeActionListener(listener);
        }

        //Inner pannel that will be centerted relatively to input_panel
        JPanel upper_panel = new JPanel();
        upper_panel.setLayout(new BoxLayout(upper_panel, BoxLayout.Y_AXIS));



        JLabel question_label = new JLabel("Enter question:");
        upper_panel.add(question_label);
        question_label.setAlignmentX(Component.CENTER_ALIGNMENT);
        upper_panel.add(Box.createVerticalStrut(10)); //vertical distance
        JTextField question_field = new JTextField();
        upper_panel.add(question_field);

        upper_panel.setMaximumSize(new Dimension(300, upper_panel.getPreferredSize().height));
        upper_panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        input_panel.add(upper_panel);





        //We create lower panel that stores ABCD options
        JPanel lower_panel = new JPanel();
        lower_panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        lower_panel.setLayout(new BoxLayout(lower_panel, BoxLayout.Y_AXIS));

        //We create arrays of radio buttons,text fields and panels
        JRadioButton [] option_radio_button =  new JRadioButton[4];
        JTextField[] option_fields = new JTextField[4];
        JPanel[] option_panels = new JPanel[4]; //Option panel =radio_button + text_field
        lower_panel.add(Box.createVerticalStrut(30)); //vertical distance

        //Group for options(answers)
        ButtonGroup group = new ButtonGroup();


        //All options should have the same GUIS thus we use a loop.
        for(int i = 0;i<option_panels.length;i++){
            lower_panel.add(Box.createVerticalStrut(10)); //vertical distance

            option_radio_button[i] = new JRadioButton("Option"+(i+1));
            option_radio_button[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            option_radio_button[i].setActionCommand(String.valueOf(i+1));
            group.add(option_radio_button[i]);


            option_fields[i] = new JTextField();
            option_panels[i] = new JPanel();
            option_panels[i].setBackground(Color.yellow);
            option_panels[i].add(option_radio_button[i]);
            option_panels[i].add(option_fields[i]);
            option_panels[i].setLayout(new BoxLayout(option_panels[i], BoxLayout.X_AXIS));



            lower_panel.add(option_panels[i]);


        }

        //Make first button a default option
        option_radio_button[0].setSelected(true);
        input_panel.add(lower_panel);
        lower_panel.setMaximumSize(new Dimension(300, lower_panel.getPreferredSize().height));


        lower_panel.setVisible(true);

        //Bindind add button for saving true/false flashcard
        add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //We create flashcard based on its type,POLYMORPHISM!!!!!!!
                Flashcard new_flashcard;
                String selected_button = getSelectedButtonActionCommand(group);
                System.out.println("Selection option:"+selected_button);

                ArrayList<String> all_options = new ArrayList<String>();
                for(int i =0;i<option_fields.length;i++){
                    all_options.add(option_fields[i].getText());
                }
                new_flashcard = new FlashcardABCD(question_field.getText(),Integer.parseInt(selected_button),all_options);
                CustomFile.serializeFlashcard(current_file,new_flashcard);



                CustomFile.appendToReport("User added ABCD flashcard:["+new_flashcard+"]","raport.txt",true);

                JOptionPane.showMessageDialog(add_flashcard_window, "Flashcard added!");
                add_flashcard_window.dispose(); // Close the window


            }
        });
    }

    /**
     * Function is responsible for binding a button to the operation of adding a flashcard.
     */
    private void addButtonListeners()
    {


        close_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add_flashcard_window.dispose(); // Close the window
            }
        });




    }
}