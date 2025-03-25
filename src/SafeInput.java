import java.util.Scanner;


public class SafeInput {
    //Task A
    public static String getNonZeroLenString(Scanner pipe, String prompt) {
        String retString = ""; // Set this to zero length. Loop runs until it isn't
        do {
            System.out.print("\n" + prompt + ": "); // show prompt add space
            retString = pipe.nextLine();
        } while (retString.length() == 0);

        return retString;

    }
    //Task B
    public static int getInt(Scanner pipe, String prompt) {
        Boolean done = Boolean.FALSE;
        int retInt = 0;
        String trash;
        do {
            System.out.print("\n" + prompt + ": ");

            if (pipe.hasNextInt()) {
                retInt = pipe.nextInt();
                pipe.nextLine();
                done = Boolean.TRUE;
            } else {
                trash = pipe.nextLine();
                System.out.println("\n" + trash + " is not a valid int, please try again.");
            }

        } while (!done);

        return retInt;
    }
    //Task C
    public static double getDouble(Scanner pipe, String prompt) {
        Boolean done = Boolean.FALSE;
        double retDouble = 0;
        String trash;
        do {
            System.out.print("\n" + prompt + ": ");

            if (pipe.hasNextDouble()) {
                retDouble = pipe.nextDouble();
                pipe.nextLine();
                done = Boolean.TRUE;
            } else {
                trash = pipe.nextLine();
                System.out.println("\n" + trash + " is not a valid double, please try again.");
            }

        } while (!done);

        return retDouble;
    }
    //Task D
    public static int getRangedInt(Scanner pipe, String prompt, int low, int high){
        Boolean done = Boolean.FALSE;
        int retInt = 0;
        String trash = "";
        do{
            System.out.print(prompt + "[" + low + "-" + high + "]: ");
            if(pipe.hasNextInt()){
                retInt = pipe.nextInt();
                if (retInt >= low && retInt <= high){
                    done = Boolean.TRUE;
                } else{
                    System.out.println("Out of range, try again");
                }
            }else{
                trash = pipe.next();
                System.out.println("\n" + trash + " is not a valid int, please try again.");
                pipe.nextLine();
            }
        }while(!done);

        return retInt;
    }
    //Task E
    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high){
        Boolean done = Boolean.FALSE;
        double retDouble = 0;
        String trash = "";
        do{
            System.out.print(prompt + "[" + low + "-" + high + "]: ");
            if(pipe.hasNextDouble()){
                retDouble = pipe.nextDouble();
                if (retDouble >= low && retDouble <= high){
                    done = Boolean.TRUE;
                } else{
                    System.out.println("Out of range, try again");
                }
            }else{
                trash = pipe.next();
                System.out.println("\n" + trash + " is not a valid double, please try again.");
                pipe.nextLine();
            }
        }while(!done);

        return retDouble;
    }
    //Task F
    public static boolean getYNConfirm(Scanner pipe, String prompt){
        Boolean done = Boolean.FALSE;
        String yOrNString = "";
        Boolean outcome = Boolean.FALSE;
        do{
            System.out.print("\n" + prompt + ": ");
            yOrNString = pipe.next().trim();
            pipe.nextLine();
            if(yOrNString.equalsIgnoreCase("Y")){
                done = Boolean.TRUE;
                outcome = Boolean.TRUE;
            } else if (yOrNString.equalsIgnoreCase("N")){
                done = Boolean.TRUE;
                outcome = Boolean.FALSE;
            }else{
                System.out.println("\n" + yOrNString + " is not a valid entry (y or n), please try again.");
            }

        }while(!done);
        return outcome;
    }
    //Task G
    public static String getRegExString(Scanner pipe, String prompt, String regEx){
        String value = "";
        Boolean gotAValue = Boolean.FALSE;

        do{
            System.out.print(prompt + ": ");
            value = pipe.nextLine();
            if(value.matches(regEx)){
                gotAValue = true;
            }
            else{
                System.out.println("\nInvalid input: " + value);
            }
        }while(!gotAValue);

        return value;
    }
    //Part H
    public static void prettyHeader(String msg){
        int width = 60;
        int msgLength = msg.length();

        //Check for
        int totalMessage = width - (msgLength + 6); // 3 * for each side
        int leftMessage = totalMessage / 2;
        int rightMessage = totalMessage - leftMessage;

        //Top
        for (int i = 0; i < width; i++) {
            System.out.print("*");
        }
        System.out.println();

        //Middle
        System.out.print("***"); //Left side
        for (int i = 0; i < leftMessage; i++) {
            System.out.print(" ");
        }
        System.out.print(msg);
        for (int i = 0; i < rightMessage; i++) {
            System.out.print(" ");
        }
        System.out.println("***"); // Right side

        // Bottom
        for (int i = 0; i < width; i++) {
            System.out.print("*");
        }
        System.out.println();
    }

}

