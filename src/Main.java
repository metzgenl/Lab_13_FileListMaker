import javax.swing.*;
import java.io.*;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Declare Variables
        Scanner in = new Scanner(System.in);
        Boolean quit = false;
        ArrayList<String> myArrList = new ArrayList<>();
        String choice = "";
        String userInput = "";
        int location = 0;
        int moveToLocation = 0;
        String itemToMove = "";
        Boolean needsToBeSaved = false;
        Boolean wantsToSave = false;
        Boolean done = false;
        Scanner inFile;
        String line;
        String fileName = "";
        JFileChooser chooser = new JFileChooser();
        Path target = new File(System.getProperty("user.dir")).toPath();
        target = target.resolve("src"); // set the chooser to the project src directory
        chooser.setCurrentDirectory(target.toFile());

        do {
            //Get the choice from the user
            choice = SafeInput.getRegExString(in, "Choose an option: Add (A), Delete (D), Insert (I), Move (M), Open (O), Save (S), Clear (C), View (V), or Quit (Q)", "[AaDdIiMmOoSsCcVvQq]");

            switch (choice) {
                //Add
                case "A":
                case "a": {
                    userInput = SafeInput.getNonZeroLenString(in, "Enter string to add to the end of the array");
                    myArrList.add(userInput);
                    needsToBeSaved = true;
                }
                break;

                //Delete
                case "D":
                case "d": {
                    if (myArrList.size() > 0) {
                        numberedVersion(myArrList);
                        location = SafeInput.getRangedInt(in, "Which item would you like to delete?", 0, myArrList.size() - 1);
                        myArrList.remove(location);
                        in.nextLine();
                        needsToBeSaved = true;
                    } else {
                        System.out.println("No items in list to delete.");
                    }
                }
                break;

                //Insert
                case "I":
                case "i": {
                    if (myArrList.size() > 0) {
                        numberedVersion(myArrList);
                        location = SafeInput.getRangedInt(in, "Where would you like to insert the element?", 0, myArrList.size() - 1);
                        in.nextLine();
                        userInput = SafeInput.getNonZeroLenString(in, "Enter string that you would like to insert");
                        myArrList.add(location, userInput);
                        needsToBeSaved = true;
                    } else {
                        System.out.println("No items in list to replace with Insert.");
                    }
                }
                break;

                //Move
                case "M":
                case "m": {
                    if (myArrList.size() > 0) {
                        numberedVersion(myArrList);
                        location = SafeInput.getRangedInt(in, "Which item would you like to move?", 0, myArrList.size() - 1);
                        moveToLocation = SafeInput.getRangedInt(in, "Where would you like to move it to?", 0, myArrList.size() - 1);
                        in.nextLine();
                        itemToMove = myArrList.get(location);
                        myArrList.remove(location);
                        myArrList.add(moveToLocation, itemToMove);
                        needsToBeSaved = true;
                    }
                    else {
                        System.out.println("No items in list to move.");
                    }
                }
                break;

                //Open
                case "O":
                case "o": {
                    //Check if unsaved
                    if (needsToBeSaved) {
                        //Check is User wants to save
                        wantsToSave = SafeInput.getYNConfirm(in, "You have unsaved changes, would you like to save (Y or N)?");
                        if (wantsToSave) {
                            saveFile(myArrList, fileName);
                            needsToBeSaved = false;
                            wantsToSave = false;
                        }
                    }

                    fileName = "";

                    try {
                        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                            target = chooser.getSelectedFile().toPath();
                            inFile = new Scanner(target);
                            fileName = target.toFile().getName();
                            System.out.println("File: " + target.toFile().getName());
                            while (inFile.hasNextLine()) {
                                line = inFile.nextLine();
                                myArrList.add(line);
                            }
                            inFile.close();
                        }
                        else { // User did not pick a file
                            System.out.println("Sorry, you must select a file! Terminating!");
                            System.exit(0);
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println("File Not Found Error");
                        e.printStackTrace();
                    } catch (Exception e) { // code to handle exception
                        System.out.println("An error occurred");
                        e.printStackTrace();
                    }
                }

                break;

                //Save
                case "S":
                case "s": {
                    saveFile(myArrList, fileName);
                    needsToBeSaved = false;

                }
                break;

                //Clear
                case "C":
                case "c": {
                    myArrList.clear();
                }
                break;

                //View
                case "V":
                case "v": {
                    ViewList(myArrList);
                }
                break;
                //Quit
                case "Q":
                case "q": {
                    //Check if unsaved
                    if (needsToBeSaved) {
                        //Check is User wants to save
                        wantsToSave = SafeInput.getYNConfirm(in, "You have unsaved changes, would you like to save (Y or N)?");
                    }
                    if (wantsToSave) {
                        saveFile(myArrList, fileName);
                        needsToBeSaved = false;
                        wantsToSave = false;
                    }

                    //Confirm Quit
                    done = SafeInput.getYNConfirm(in, "Are you sure you want to quit (Y or N)?");
                }
                break;

            }

        } while (!done);
    }

    private static void ViewList(ArrayList<String> myArrList) {
        System.out.print(myArrList);
        System.out.println();
    }

    private static void numberedVersion(ArrayList<String> myArrList) {
        System.out.print("[");
        for (int i = 0; i < myArrList.size(); i++) {
            System.out.print(i);
            if (i < myArrList.size() - 1) {  // Avoid extra comma at the end
                System.out.print(", ");
            }
        }
        System.out.print("]");
        System.out.println();
    }

    private static void saveFile(ArrayList<String> myArrList, String fileName) {
        Scanner scanner = new Scanner(System.in);
        //Ask user for filename if this is a new array and not an opened one
        if (fileName.isEmpty()){
            fileName = SafeInput.getNonZeroLenString(scanner, "Enter the filename (with .csv extension)");
        }
        // Write to file, code from video
        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath(), "src", fileName); //Put in user file name here
        try {
            OutputStream out =
                    new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(out));
            for (String rec : myArrList) {
                writer.write(rec, 0, rec.length());

                writer.newLine();
            }

            writer.close();
            System.out.println("Data saved successfully to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
