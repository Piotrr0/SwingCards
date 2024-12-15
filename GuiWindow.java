import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Class is responsible for creating  a window using java swing
 */
public class GuiWindow {
    /**It is an instance of the window that is being displayed. */
    private JFrame window;
    /**It is an instance of the menu bar that is displayed at the top of the screen */
    private JPanel menu;
    /**It is the section that displays the most important section under the menu bar*/
    private JPanel main_section;
    /**Button used inside menu bar*/
    private JButton catalogues_button,add_button,inspect_button,profile_button;
    /**It is the font sized used for normal text*/
    private int normal_font_size =30;



    public GuiWindow(){

        //Most of these is just styling components to look better

        //Creating instances of window and frame with title
        window = new JFrame("Flashcards");
        //so that not-maximized window has a resolution higher than 0
        window.setSize(1280, 720);

        menu = new JPanel();
        // Add a thin border at the bottom
        menu.setBackground(Color.white);
        menu.setBorder(new MatteBorder(1, 0, 1, 0, Color.BLACK));

        catalogues_button = new JButton("Catalogues");
        add_button = new JButton("Add");
        inspect_button = new JButton("Inspect");
        profile_button = new JButton("Profile");

        /**It is a collection of buttons that are inside menu bar, it is useful for group styling*/
        ArrayList<JButton> button_list = new ArrayList<>(){{
            add(catalogues_button);
            add(add_button);
            add(inspect_button);
            add(profile_button);
        }};


        //Styling all buttons inside collection
        for(int i =0;i<button_list.size();i++){
            button_list.get(i).setBackground(Color.white);
            button_list.get(i).setForeground(Color.black);
            button_list.get(i).setFocusPainted(false);

        }


        main_section = new JPanel();
        main_section.setBackground(Color.WHITE);
        mainSectionDefault();

        //Window uses BorderLayout, menu is at the top(north) and main content is below
        window.setLayout(new BorderLayout());

        //It makes app fullscreen during first launch
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);

        window.add(menu,BorderLayout.NORTH);
        window.add(main_section,BorderLayout.CENTER);

        menu.add(catalogues_button);
        menu.add(add_button);
        menu.add(inspect_button);
        menu.add(profile_button);
        menu.setLayout(new FlowLayout()); // Arrange components in a row

        //Exit the program when you close the window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);



        add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuiAddFlashcard guiAddFlashcard = new GuiAddFlashcard("Add flash card", 800, 400);
            }
        });
    }

    /**
     * It sets <code>main_section</code> to default.
     * */
    private void mainSectionDefault(){
        //It removes all components
        main_section.removeAll();

        JLabel test = new JLabel("There should be a list of your catalogues");
        main_section.add(test);
    }

    /**
     * It sets <code>main_section</code> to change its content for learning.
     * */
    private void mainSectionLearning(){
        //It removes all components
        main_section.removeAll();

        JLabel test = new JLabel("It should display while learning flashcards");
        main_section.add(test);
    }


}
