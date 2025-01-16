import DictionaryPanel.DictionaryPanel;
import FlashcardTypes.*;
import Libraries.FlashcardLibrary;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;


/**
 * Creates and manages the main application window
 * Handles the layout and interaction of the flashcard application interface.
 */
public class GuiWindow
{
    private static final int DEFAULT_WINDOW_WIDTH = 1280;
    private static final int DEFAULT_WINDOW_HEIGHT = 720;
    private static final int NORMAL_FONT_SIZE = 30;

    /**It is an instance of the window that is being displayed. */
    private final JFrame window;
    /**It is an instance of the menu bar that is displayed at the top of the screen */
    private final JPanel menu;

    // Menu buttons
    private final JButton catalogues_button;
    private final JButton add_button;
    private final JButton inspect_button;

    private final JButton add_folder_button;
    private final JButton delete_button;
    private final JButton learning_button; //temporary button for testing flashcards;

    private File flashcards_directory;
    private DictionaryPanel dictionary_panel;
    //temporary
    private FlashcardPanel flashcard_panel;
    private File selected_file;
    private boolean is_inspecting;

    public GuiWindow() {
        window = createMainWindow();
        menu = createMenuPanel();

        // Initialize menu buttons
        catalogues_button = createMenuButton("Catalogues");
        add_button = createMenuButton("Add Deck/Flashcard");
        add_folder_button = createMenuButton("Add Folder");
        inspect_button = createMenuButton("Inspect");

        delete_button = createMenuButton("Delete");

        learning_button = createMenuButton("Learn Flashcards");

        setupLayout();
        setupEventListeners();

        window.setVisible(true);
    }

    /**
     * Creates and configures the main application window.
     */
    private JFrame createMainWindow()
    {
        JFrame frame = new JFrame("Flashcards");
        frame.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        return frame;
    }

    /**
     * Creates and configures the menu panel.
     */
    private JPanel createMenuPanel()
    {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new MatteBorder(1, 0, 1, 0, Color.BLACK));
        return panel;
    }

    /**
     * Creates and configures the main content section.
     */
    private JPanel createMainSection() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        return panel;
    }

    /**
     * Creates a styled menu button with the given text.
     */
    private JButton createMenuButton(String button_text)
    {
        JButton button = new JButton(button_text);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        return button;
    }

    /**
     * Sets up the main window layout and adds all components.
     */
    private void setupLayout()
    {
        // Add menu buttons to menu panel
        menu.add(catalogues_button);
        menu.add(add_button);
        menu.add(add_folder_button);
        menu.add(inspect_button);

        menu.add(delete_button);
        menu.add(learning_button);

        // Add panels to main window
        window.add(menu, BorderLayout.NORTH);


        // Initialize default view
        mainSectionDefault();
    }

    /**
     * Sets up event listeners for all interactive components.
     */
    private void setupEventListeners()
    {
        add_button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(selected_file != null && selected_file.isFile())
                {
                    String relative_path = FlashcardLibrary.getRelativePath(selected_file.getAbsolutePath());
                    GuiAddFlashcard gui_add_flashcard = new GuiAddFlashcard("Add flash card", 800, 400, relative_path);
                }
                else if(selected_file != null && selected_file.isDirectory())
                {
                    String deck_name = JOptionPane.showInputDialog(JOptionPane.getRootFrame(), "Enter deck name:", "Add Deck", JOptionPane.PLAIN_MESSAGE);
                    File deck = new File(selected_file, deck_name+".txt");
                    try
                    {
                        deck.createNewFile();
                        dictionary_panel.refreshTree();
                        CustomFile.appendToReport("User added a new deck: " + deck_name+".txt", "raport.txt");
                    }
                    catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        catalogues_button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.remove(window.getContentPane().getComponent(1)); //removes component in center panel
                mainSectionDefault();
            }

        });

        add_folder_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(selected_file != null && selected_file.isDirectory())
                {
                    String dir_name = JOptionPane.showInputDialog(JOptionPane.getRootFrame(), "Enter directory name:", "Add Folder", JOptionPane.PLAIN_MESSAGE);
                    if (dir_name != null)
                    {
                        File directory = new File(selected_file, dir_name);
                        CustomFile.appendToReport("User added a new directory: " + dir_name, "raport.txt");
                        if(directory.mkdirs())
                        {
                            dictionary_panel.refreshTree();
                        }
                    }
                }
            }
        });


        inspect_button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(selected_file != null) {
                    is_inspecting = !is_inspecting;
                    System.out.println(is_inspecting);

                    //You can only inspect files,not entire folders
                    if (selected_file.getName().endsWith(".txt")) {
                        //It allows you tou get ArrayList of all Flashcards from given file
                        ArrayList<Flashcard> flashcards = CustomFile.readSerializefFlashcard(selected_file.getAbsolutePath());


                        if (flashcards.size() == 0) {
                            JOptionPane.showMessageDialog(null, "Deck is empty", "", JOptionPane.ERROR_MESSAGE);
                        }
                        //There is at least one flashcard in deck
                        else {
                            window.remove(window.getContentPane().getComponent(1)); //removes component in center panel
                            GuiInspect inspect_panel = new GuiInspect(selected_file.getAbsolutePath());
                            CustomFile.appendToReport("User started editing flashcards from: " + selected_file.getName(), "raport.txt");


                            // Wrap the inspect_panel in a JScrollPane
                            JScrollPane scrollPane = new JScrollPane(inspect_panel);
                            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


                            window.add(scrollPane);

                            window.revalidate();

                        }
                    } else {
                        System.out.println("You are trying to inspect entire folder!");
                        JOptionPane.showMessageDialog(null, "Can inspect files only!", "", JOptionPane.ERROR_MESSAGE);
                    }

                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Choose a file first","",JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        delete_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(selected_file != null)
                {
                    int confirmation = JOptionPane.showConfirmDialog(
                            null,
                            "Are you sure you want to delete: " + selected_file.getName(),
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (confirmation == JOptionPane.YES_OPTION)
                    {
                        if(selected_file.isFile())
                        {
                            CustomFile.appendToReport("User deleted: " + selected_file.getName(), "raport.txt");
                            selected_file.delete();
                        }
                        else if(selected_file.isDirectory())
                        {
                            CustomFile.appendToReport("User deleted: " + selected_file.getName() + " directory", "raport.txt");
                            FlashcardLibrary.deleteFolder(selected_file);
                        }
                        dictionary_panel.refreshTree();
                    }
                }
            }
        });

        //Binding learning button
        learning_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(selected_file != null) {

                    //FLASHCARD ARRAY HAS TO BE CREATED HERE!!!!
                    ArrayList<Flashcard> flashcards = new ArrayList<>();

                    if (selected_file.getName().endsWith(".txt")) {
                        //It allows you tou get ArrayList of all Flashcards from given file
                        flashcards = CustomFile.readSerializefFlashcard(selected_file.getAbsolutePath());


                        if (flashcards.size() == 0) {
                            JOptionPane.showMessageDialog(null, "Deck is empty", "", JOptionPane.ERROR_MESSAGE);
                        } else {
                            CustomFile.appendToReport("User started learning flashcards from: " + selected_file.getName(), "raport.txt");
                            window.remove(window.getContentPane().getComponent(1)); //removes component in center panel
                            flashcard_panel = new FlashcardPanel(flashcards);
                            window.add(flashcard_panel, BorderLayout.CENTER);
                            RefreshView();
                        }
                    }
                    //You want to take a test from the entire folder
                    else {

                        try {
                            //Get all .txt files recursively and convert them into array of strings
                            ArrayList<String> paths = new ArrayList<>();
                            for (Path path : CustomFile.findAllTextFiles(selected_file.getAbsolutePath())) {
                                paths.add(String.valueOf(path));
                            }
                            //If there are not text files
                            if (paths.size() == 0) {
                                JOptionPane.showMessageDialog(null, "Deck is empty", "", JOptionPane.ERROR_MESSAGE);
                            } else {
                                //Iterate over each text file and add flashcards
                                for (int i = 0; i < paths.size(); i++) {
                                    ArrayList<Flashcard> flashcards_from_file = CustomFile.readSerializefFlashcard(paths.get(i));
                                    //Add each flashcard into overal set of flashcards
                                    flashcards.addAll(flashcards_from_file);
                                }

                                //If set of flashcards is empty
                                if (flashcards.size() == 0) {
                                    JOptionPane.showMessageDialog(null, "Deck is empty", "", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    CustomFile.appendToReport("User started learning flashcards from: " + selected_file.getName() + " directory", "raport.txt");
                                    window.remove(window.getContentPane().getComponent(1)); //removes component in center panel
                                    flashcard_panel = new FlashcardPanel(flashcards);
                                    window.add(flashcard_panel, BorderLayout.CENTER);
                                    RefreshView();
                                }


                            }

                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }


                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Choose a file or directory first","",JOptionPane.ERROR_MESSAGE);
                }


            }
        });
    }



    /**
     * Shows the default view with the catalogue listing.
     */
    private void mainSectionDefault()
    {
        flashcards_directory = new File("flashcards");
        if(!flashcards_directory.exists())
        {
            flashcards_directory.mkdir();
        }

        dictionary_panel = new DictionaryPanel(flashcards_directory);
        dictionary_panel.addFileSelectionListener(file ->
        {
            selected_file = file;
        });

        window.add(dictionary_panel, BorderLayout.CENTER);

        RefreshView();
    }

    /**
     * Refresh the view
     */
    private void RefreshView()
    {
        //removing from center panel
        window.getContentPane().getComponent(1).revalidate();
        window.getContentPane().getComponent(1).repaint();
    }
}