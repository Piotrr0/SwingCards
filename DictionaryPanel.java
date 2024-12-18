import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.io.File;

// Class is not finished yet!

public class DictionaryPanel extends JPanel
{
    private JPanel main_section;

    public DictionaryPanel(JPanel main_section, File rootDirectory)
    {
        this.main_section = main_section;
        setLayout(new BorderLayout());

        JTree file_tree = createFileTree(rootDirectory);
        file_tree.setRowHeight(28);
        file_tree.setFont(new Font("Monospaced", Font.PLAIN, 24));

        // Add the file tree to panel
        add(new JScrollPane(file_tree), BorderLayout.CENTER);

        // Add tree listener to handle file and directory selection
        file_tree.addTreeSelectionListener(event ->
        {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                    file_tree.getLastSelectedPathComponent();
            if (node == null) return;

            File selectedFile = (File) node.getUserObject();

            if (selectedFile.isFile())
            {
                // TODO: Add a listener
            }
        });
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    // Create the JTree with the directory structure starting from the root directory
    private static JTree createFileTree(File rootDirectory)
    {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootDirectory);
        createTreeNodes(root, rootDirectory);

        JTree tree = new JTree(root);
        tree.setRootVisible(true);
        tree.setShowsRootHandles(true);
        tree.setCellRenderer(new FileTreeCellRenderer()); // Customize appearance
        return tree;
    }

    // Recursively populate the tree with nodes
    private static void createTreeNodes(DefaultMutableTreeNode parent, File file)
    {
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(f);
                parent.add(child);
                if (f.isDirectory()) {
                    createTreeNodes(child, f);
                }
            }
        }
    }

    // Custom cell renderer for displaying file names
    private static class FileTreeCellRenderer extends DefaultTreeCellRenderer
    {
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                      boolean sel, boolean expanded,
                                                      boolean leaf, int row, boolean hasFocus) {
            Component c = super.getTreeCellRendererComponent(
                    tree, value, sel, expanded, leaf, row, hasFocus);
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            File file = (File) node.getUserObject();
            setText(file.getName());
            return c;
        }
    }
}