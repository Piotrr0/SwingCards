package Libraries;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;

/*
* class for static functions
* */
public class FlashcardLibrary
{
    /**
     * Converts an absolute file path to a relative path based on the project directory.
     *
     * @param absoluteFilePath The absolute file path.
     * @return The relative path of the file from the project directory, or null if the file is not inside the project.
     */
    public static String getRelativePath(String absoluteFilePath)
    {
        // Get the absolute path of the project directory
        Path projectPath = Paths.get("").toAbsolutePath();
        // Convert the absolute file path to a Path object
        Path absolutePath = Paths.get(absoluteFilePath);

        // Compute the relative path
        Path relativePath = projectPath.relativize(absolutePath);
        return relativePath.toString();
    }


    /**
     * Delete folder and its contents recursively.
     *
     * @param folder The folder (directory) to be deleted.
     */
    public static void deleteFolder(File folder)
    {
        if (folder != null && folder.exists())
        {
            if (folder.isDirectory())
            {
                File[] files = folder.listFiles();
                if (files != null) {
                    for (File file : files)
                    {
                        deleteFolder(file);
                    }
                }
            }
            folder.delete();
        }
    }
}
