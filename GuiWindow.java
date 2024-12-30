import DirectoryPanel.DictionaryPanel;
import FlashcardTypes.Flashcard;
import FlashcardTypes.FlashcardABCD;
import FlashcardTypes.FlashcardPanel;
import FlashcardTypes.FlashcardText;
import Libraries.FlashcardLibrary;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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
    private final JButton profile_button;
    private final JButton add_folder_button;
    //temporary
    private final JButton work_test; //temporary button for testing flashcards;

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
        profile_button = createMenuButton("Profile");

        //temporary
        work_test = createMenuButton("flashcard test");

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
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
        menu.add(profile_button);
        //temporary
        menu.add(work_test);

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
                        if(directory.mkdirs())
                        {
                            mainSectionDefault();
                        }
                    }
                }
            }
        });

        //TODO:
        inspect_button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                is_inspecting = !is_inspecting;
                System.out.println(is_inspecting);

                //You can only inspect files,not entire folders
                if(selected_file.getName().endsWith(".txt")){
                    //It allows you tou get ArrayList of all Flashcards from given file
                    ArrayList<Flashcard>flashcards =  CustomFile.readSerializefFlashcard(selected_file.getAbsolutePath());


                    if(flashcards.size()==0){
                        JOptionPane.showMessageDialog(null, "Deck is empty","",JOptionPane.ERROR_MESSAGE);
                    }

                    else {
                        for (int i = 0; i < flashcards.size(); i++) {
                            System.out.println(flashcards.get(i) + ", type of flashcard " + flashcards.get(i).type);
                        }
                    }
                }
                else{
                    System.out.println("You are trying to inspect entire folder!");
                }



            }
        });

        //temporary
        work_test.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Flashcard> flashcards = new ArrayList<>();
                //TODO fix incosistent alignment
                flashcards.add(new FlashcardText("w top ilu % fizyków na świecie jest prof. dr. hab. inż. Aleksander Muc?", "2%"));
                flashcards.add(new FlashcardText("czego nienawidzi Piotr Kędra?", "Windowsa"));
                flashcards.add(new FlashcardText("przezwisko Stachniewicza", "Stachu"));
                flashcards.add(new FlashcardText("kompatybilny wstecznie model ps3 na europę kontynentalną", "CECHC04"));
                window.remove(window.getContentPane().getComponent(1)); //removes component in center panel
                flashcard_panel = new FlashcardPanel(flashcards);
                window.add(flashcard_panel, BorderLayout.CENTER);
                RefreshView();

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