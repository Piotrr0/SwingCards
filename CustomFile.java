import FlashcardTypes.Flashcard;
import FlashcardTypes.FlashcardText;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to manage file system especially serialization and deserialization
 * */
abstract public class CustomFile {


    /**
     * Function deserializes all Flashcards from given filename
     * @param filename is a name of file that will be read
     * */
    public static ArrayList<Flashcard> readSerializefFlashcard(String filename) {
        ArrayList<Flashcard> flashcards = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            while (true) {
                try {
                    Flashcard flashcard = (Flashcard) ois.readObject(); // Read each Flashcard
                    flashcards.add(flashcard);
                } catch (EOFException e) {
                    break; // End of file reached
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            if(isEmpty(filename)){
                System.err.println("Error during deserialization: " +", file is empty");

            }
            else{
                System.err.println("Error during deserialization: ");
            }
        }
        return flashcards;
    }

    /**
     * Function serializes flashcard and APPENDS it into the given file.
     * */
    public static void serializeFlashcard(String filename,Flashcard flashcard){
        //We first read entire file and store objects inside ArrayList. Then we add our new flashcard and save entire file at once.
        //There is no need to read file if it is empty
        ArrayList<Flashcard> flashcards = new ArrayList<>();
        if(!isEmpty(filename)){
            flashcards =  readSerializefFlashcard(filename);
        }
        flashcards.add(flashcard);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            for (Flashcard flashcardx : flashcards) {
                oos.writeObject(flashcardx); // Write each Flashcard object
            }
            System.out.println("Flashcards serialized to " + filename);
        } catch (IOException e) {
            System.err.println("Error during serialization: " + e.getMessage());
        }
    }

    /**
     * Function returns if given file is empty
     * @param path path to the file
     * @return returns boolean
     * */
    public static boolean isEmpty(String path){
        File file = new File(path);
        return file.length()==0;
    }

    /**
     * Function saves information to the raport file without a timestamp
     * @param message message to be saved
     * @param filename location of file to be saved in
     * */
    public static void appendToReport(String message,String filename){
        // Use try-with-resources to ensure buffer is automatically closed
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(message);       // Write the new line
            writer.newLine();           // Add a newline character
        } catch (IOException e) {
            System.out.println("Error while saving to raport!");
        }
    }

    /**
     * Function saves information to the raport file and can add timestamp
     * @param message message to be saved
     * @param filename location of file to be saved in
     * @param timestamp specifies if you want to have timestampt prior the message
     * */
    public static void appendToReport(String message,String filename ,Boolean timestamp){
        //append with timestamp
        if(timestamp) {
            // Get the current time
            LocalTime currentTime = LocalTime.now();

            // Format the time as hh:mm:ss
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedTime = currentTime.format(formatter);
            System.out.println("adding timestamp");
            // Print the current time
            appendToReport(formattedTime+": "+message, filename);
        }
        //append without timestamp
        else{
            appendToReport(message,filename);

        }
    }

    public static List<Path> findAllTextFiles(String directoryPath) throws IOException {
        List<Path> textFiles = new ArrayList<>();
        Path startPath = Paths.get(directoryPath);

        // Walk the file tree
        Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (file.toString().endsWith(".txt")) {
                    textFiles.add(file);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                System.err.println("Failed to access file: " + file + " (" + exc.getMessage() + ")");
                return FileVisitResult.CONTINUE;
            }
        });

        return textFiles;
    }



    }





