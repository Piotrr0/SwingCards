import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/* Gui that pops up while add button is clicked */
public class GuiAddFlashcard
{
    private final JButton add_button;
    private final JButton close_button;

    private final JFrame add_flashcard_window;

    GuiAddFlashcard(String title, int width, int height)
    {
        // Init JFrame
        add_flashcard_window = new JFrame(title);
        add_flashcard_window.setSize(width, height);
        add_flashcard_window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add_flashcard_window.setLayout(new BorderLayout());

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
    }
}