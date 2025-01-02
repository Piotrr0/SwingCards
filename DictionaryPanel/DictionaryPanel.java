package DictionaryPanel;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.awt.*;
import java.io.File;

public class DictionaryPanel extends JPanel
{
    private final JTree file_tree;
    private FileSelectionListener file_selection_listener;

    /*
     * Why "Loading..." is necessary:
     * Allows directories to be expandable even if initially empty
     * Provides visual indication that more content can be loaded
     * Enables lazy loading of directory contents for better performance
     */

    public DictionaryPanel(File rootDirectory)
    {
        setLayout(new BorderLayout());

        file_tree = createFileTree(rootDirectory);
        file_tree.setRowHeight(28);
        file_tree.setFont(new Font("Monospaced", Font.PLAIN, 24));

        // Add tree expansion listener for dynamic folder loading
        file_tree.addTreeExpansionListener(new TreeExpansionListener()
        {
            @Override
            public void treeExpanded(TreeExpansionEvent event)
            {
                TreePath path = event.getPath();
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();

                // Remove the dummy "Loading..." node and add real subdirectories
                if (node.getChildCount() == 1 && node.getFirstChild().toString().equals("Loading..."))
                {
                    node.removeAllChildren();
                    File file = (File) node.getUserObject();
                    createTreeNodes(node, file);

                    // Reload the tree model to reflect changes
                    ((DefaultTreeModel)file_tree.getModel()).reload(node);
                }
            }

            @Override
            public void treeCollapsed(TreeExpansionEvent event)
            {

            }
        });

        // Add the file tree to panel
        add(new JScrollPane(file_tree), BorderLayout.CENTER);

        // Add tree listener to handle file and directory selection
        file_tree.addTreeSelectionListener(event ->
        {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)file_tree.getLastSelectedPathComponent();
            if (node == null) return;

            //File can be directory or file
            File selectedFile = (File) node.getUserObject();

            if (selectedFile.isFile() || selectedFile.isDirectory())
            {
                if (file_selection_listener != null)
                {
                    file_selection_listener.onFileSelected(selectedFile);
                }
            }
        });
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    public void addFileSelectionListener(FileSelectionListener listener)
    {
        this.file_selection_listener = listener;
    }

    // Create the JTree with the directory structure starting from the root directory
    private JTree createFileTree(File rootDirectory)
    {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootDirectory);
        createTreeNodes(root, rootDirectory);

        JTree tree = new JTree(root);
        tree.setRootVisible(true);
        tree.setShowsRootHandles(true);
        tree.setCellRenderer(new FileTreeCellRenderer());
        return tree;
    }

    // Recursively populate the tree with nodes
    private void createTreeNodes(DefaultMutableTreeNode parent, File file)
    {
        File[] files = file.listFiles();
        if (files != null)
        {
            for (File f : files)
            {
                // Skip hidden files or files you can't read
                if (f.isHidden() || !f.canRead()) continue;

                DefaultMutableTreeNode child = new DefaultMutableTreeNode(f);
                parent.add(child);

                if (f.isDirectory())
                {
                    // Add a dummy node to allow expansion
                    child.add(new DefaultMutableTreeNode("Loading..."));
                    child.setAllowsChildren(true);
                }
            }
        }
    }

    public void refreshTree()
    {
        TreePath selectedPath = file_tree.getSelectionPath();

        DefaultMutableTreeNode root = (DefaultMutableTreeNode) file_tree.getModel().getRoot();
        root.removeAllChildren();
        File rootDirectory = (File) root.getUserObject();
        createTreeNodes(root, rootDirectory);

        DefaultTreeModel model = (DefaultTreeModel) file_tree.getModel();
        model.reload();

        if (selectedPath != null) {
            file_tree.setSelectionPath(selectedPath);
        }
    }

    // Custom cell renderer for displaying file names
    private static class FileTreeCellRenderer extends DefaultTreeCellRenderer
    {
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                      boolean sel, boolean expanded,
                                                      boolean leaf, int row, boolean hasFocus)
        {
            Component c = super.getTreeCellRendererComponent(
                    tree, value, sel, expanded, leaf, row, hasFocus);

            if (value instanceof DefaultMutableTreeNode node)
            {
                // Handle the "Loading..." dummy node
                if (node.toString().equals("Loading..."))
                {
                    setText("Loading...");
                    return c;
                }

                // Regular file/directory rendering
                if (node.getUserObject() instanceof File file)
                {
                    setText(file.getName());

                    if (file.isDirectory())
                    {
                        setIcon(UIManager.getIcon("FileView.directoryIcon"));
                    } else
                    {
                        setIcon(UIManager.getIcon("FileView.fileIcon"));
                    }
                }
            }

            return c;
        }
    }
}