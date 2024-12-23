import FlashcardTypes.Flashcard;
import FlashcardTypes.FlashcardText;

import java.io.*;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

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
            System.err.println("Error during deserialization: " + e.getMessage());
        }
        return flashcards;
    }

    /**
     * Function serializes flashcard and APPENDS it into the given file.
     * */
    public static void serializeFlashcard(String filename,Flashcard flashcard){
        //We first read entire file and store objects inside ArrayList. Then we add our new flashcard and save entire file at once.
        ArrayList<Flashcard> flashcards =  readSerializefFlashcard(filename);
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

}
