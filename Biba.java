import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Biba {
    enum ClearanceLevel {
        // These names are different becuase I'm a nerd and I think it's fun
        NOMINAL, // Equivalent to "Unclassified (U)"
        RESTRICTED, // Equivalent to "Confidential (C)"
        SECURE, // Equivalent to "Secret (S)"
        THAUMIEL, // Equivalent to "Top Secret (TS)"
        APOLLYON // Equivalent to "Cosmic Top Secret (CTS)"
    }

    static class Secured_Object { // Custom class to handle secure objects
        String name; // Name of object
        ClearanceLevel clearanceLevel; // Clearance level required to read its contents
        String path; // Path to the file

        // Simple initializer
        public Secured_Object(String name, ClearanceLevel clearanceLevel, String path) {
            this.name = name;
            this.clearanceLevel = clearanceLevel;
            this.path = path;
        }

        // Getters
        public String getPath() {
            return path;
        }

        public ClearanceLevel getClearanceLevel() {
            return clearanceLevel;
        }

        public String getName() {
            return name;
        }
    }

    static class User { // Custom class to handle a user with clearance levels
        String name; // User name
        int ID; // ID number, not mandatated but 3-digits generally preferred
        ClearanceLevel clearanceLevel; // Clearance level assigned to each user

        // Simple initializer
        public User(String name, ClearanceLevel clearanceLevel, int ID) {
            this.name = name;
            this.clearanceLevel = clearanceLevel;
            this.ID = ID;
        }

        // Getters
        public ClearanceLevel getClearanceLevel() {
            return clearanceLevel;
        }

        public String getName() {
            return name;
        }

        public int getID() {
            return ID;
        }

        // Signature used for "writing" to files in a simple way
        public String getSignature() {
            return "Signed by " + this.getName() + ", ID: " + this.getID() + ", on "
                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"))
                    + "\nUpdates/Changes Made: " + (int) (Math.random() * 10);
        }

    }

    // Checkers for read/write capability
    public static boolean canRead(User user, Secured_Object object) {
        return user.getClearanceLevel().ordinal() <= object.getClearanceLevel().ordinal();
    }

    // Changed these values to support Biba instead of Bell-LaPadula

    public static boolean canWrite(User user, Secured_Object object) {
        return user.getClearanceLevel().ordinal() >= object.getClearanceLevel().ordinal();
    }

    public static void main(String[] args) throws FileNotFoundException {
        // Declaring basic users
        User userNormand = new User("Normand", ClearanceLevel.NOMINAL, 142);
        User userRestaries = new User("Restaries", ClearanceLevel.RESTRICTED, 256);
        User userSekura = new User("Sakura", ClearanceLevel.SECURE, 731);
        User userThamir = new User("Thamir", ClearanceLevel.THAUMIEL, 824);
        User userApolius = new User("Apolius", ClearanceLevel.APOLLYON, 915);
        // Declare a list of users for easy manipulation
        User[] users = { userNormand, userRestaries, userSekura, userThamir, userApolius };

        // Declaring basic objects
        Secured_Object objectWelcomeMessage = new Secured_Object("Welcome Message", ClearanceLevel.NOMINAL,
                "Welcome_Message.txt");
        Secured_Object objectLocationList = new Secured_Object("Location List", ClearanceLevel.RESTRICTED,
                "Location_List.txt");
        Secured_Object objectDirectorContact = new Secured_Object("Director Contact List", ClearanceLevel.SECURE,
                "Director_Contact_List.txt");
        Secured_Object objectNuclearCodes = new Secured_Object("Nuclear Codes", ClearanceLevel.THAUMIEL,
                "Nuclear_Codes.txt");
        Secured_Object objectSpecialChicken = new Secured_Object("Special Chicken", ClearanceLevel.APOLLYON,
                "Special_Chicken_Recipe.txt");
        // Declare a list of objects for easy manipulation
        Secured_Object[] objects = { objectWelcomeMessage, objectLocationList, objectDirectorContact,
                objectNuclearCodes, objectSpecialChicken };

        // Variable declaration for the main loop
        Scanner lineReader = new Scanner(System.in);
        int command;
        Secured_Object currentObject;

        // Initial prompt
        System.out.println(
                "Welcome to the Daedalus Systems Bell-LaPadula Security System. A tall terminal is recommended to view this system.");
        System.out.println("Please enter a 3-digit ID number to begin.\n");
        User currentUser = getUser(users); // Setting the starting user

        // Main functioning loop
        while (true) {
            { // Command prompt and input
                getCurrentStatus(currentUser);
                System.out.print("""
                        --------------------------------------------------
                         Available Commands:
                         1  - Change to different User
                         2  - List secured objects
                         3  - Try to read a secured object
                         4  - Try to write to a secured object
                         0  - Exit
                        --------------------------------------------------
                        Enter your choice: """);

                command = getInt(lineReader);
            }

            switch (command) {
                case 1: // Change user
                    currentUser = getUser(users);
                    break;
                case 2: // List the current objects
                    listObjects(objects);
                    break;
                case 3: // Read an object
                    currentObject = getObject(objects); // Get an object from the list
                    System.out.println(
                            "You can " + (canRead(currentUser, currentObject) ? "" : "not ") + "read this object.");
                    // Prompt for readability
                    if (canRead(currentUser, currentObject)) {
                        System.out.println("Would you like to read it? (y/n)"); // Ask about reading the file
                        String read = lineReader.nextLine();
                        if (read.equals("y")) {
                            doRead(currentObject);
                        }
                    }
                    break;
                case 4: // Write a signature to the file
                    currentObject = getObject(objects); // Get an object from the list
                    System.out.println(
                            "You can " + (canWrite(currentUser, currentObject) ? "" : "not ")
                                    + "write to this object.");
                    // Prompt for writability
                    if (canWrite(currentUser, currentObject)) {
                        System.out.println("Would you like to write your signature to it? (y/n)"); // Ask about writing
                                                                                                   // to the file
                        String write = lineReader.nextLine();
                        if (write.equals("y")) {
                            doWrite(currentObject, currentUser);
                        }
                    }
                    break;
                case 0: // Exit case
                    System.out.println("Exiting...");
                    lineReader.close();
                    return;
                default: // Bad input case
                    System.out.println("Invalid command. Please try again.");
                    break;
            }
            currentObject = null; // Nuking the object- not sure if this is necessary?
        }

    }

    // Response posted to every loop of the main function loop
    public static void getCurrentStatus(User currUser) {
        System.out.println("\n\n\n"); // Spacing
        System.out.println("===================CURRENT USER===================");
        System.out.printf("NAME: %-10s|| CLEARANCE: %-10s%n",
                currUser.getName(), currUser.getClearanceLevel());
        // Printing the status of the current user
    }

    // "Getter" for the purpose of handling the while-switch body
    public static int getInt(Scanner lineReader) {
        int result = 0;
        boolean valid = false;
        while (!valid) {
            try {
                result = Integer.parseInt(lineReader.nextLine());
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("\n\n\nPlease enter a valid integer.");
            }
        }
        return result;
    }

    // Printing out the complete list of users
    public static void listUsers(User[] users) {
        System.out.println("===================CURRENT USERS===================");
        for (User user : users) {
            System.out.printf("NAME: %-10s|| ID: %-3d || CLEARANCE: %-10s%n",
                    user.getName(), user.getID(), user.getClearanceLevel());

        }
    }

    // Function to get a user, based on a 3-digit ID number
    public static User getUser(User[] users) {
        // Declare scanner to get input and an ID# placeholder
        Scanner lineReader = new Scanner(System.in);
        int id = 0;

        while (true) { // Outer while loop to get a valid User

            // Initial prompt and user list
            listUsers(users);

            // Inner while loop to get an id. If we pass this loop, we have a valid ID
            // number (3 digits)
            while (true) {
                // Get an integer
                // Check if it is an integer first
                // Then, check if >100 <999
                System.out.print("Enter a 3-digit ID number: ");

                // Check to see if the input is an integer
                if (lineReader.hasNextInt()) {
                    // Set to the input
                    id = lineReader.nextInt();
                    // Check the ID to be within range
                    if (id > 100 && id < 999) {
                        break; // If it's within range, check for valid user
                    } else {
                        // If it was not within range, loop again
                        System.out.println("\nInvalid ID. IDs Must be 3 digits long.\n\n");
                    }
                } else {
                    // If it was not an integer, loop again
                    System.out.println("\nInvalid ID. IDs can only contain numbers.\n\n");
                    lineReader.next();
                }
            } // End of inner while loop

            // Check the list of users against the 3-digit ID to see if a valid user exists
            for (User user : users) {
                if (user.getID() == id) {
                    return user; // As soon as one is found, return it
                }
            }
            // If no user was found, state this, and loop again
            System.out.println("\nNo user found with that ID number.\n\n");

        }

    }

    // Printing the entire list of objects
    public static void listObjects(Secured_Object[] objects) {
        System.out.println("\n\n\n===================CURRENT OBJECTS===================");
        for (int i = 0; i < objects.length; i++) {
            Secured_Object object = objects[i];
            // "forcing" an ID onto the objects for readability
            System.out.printf("ID: %-3d || NAME: %-22s|| CLEARANCE: %-10s%n",
                    i + 1, object.getName(), object.getClearanceLevel());
        }
    }

    // Getting a secure object for use in read/writing
    public static Secured_Object getObject(Secured_Object[] objects) {
        Scanner lineReader = new Scanner(System.in);
        int id;
        while (true) {
            listObjects(objects);
            System.out.print("Enter the number of the object you wish to access: ");
            // Getting an object id
            id = getInt(lineReader);
            if (id > 0 && id <= objects.length) {
                return objects[id - 1];
            } else {
                System.out.println("Invalid object. Please try again.");
                // Handling invalid input
            }
        }
    }

    // Reading the data in a file
    // Doesn't need to check security since it's only called after checking
    public static void doRead(Secured_Object object) {
        try {
            System.out.println("\n\n");
            System.out.println("Reading " + object.getName() + "...");
            Scanner fileReader = new Scanner(new File(object.getPath()));
            while (fileReader.hasNextLine()) { // Dumping the entire file to the console (it's a bit ugly)
                System.out.println(fileReader.nextLine());
            }
            System.out.println("=x=x=x=End of file=x=x=x="); // slightly more eye-catching marker for eof?
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + object.getPath());
        }
    }

    // Writing the user's signature to the file
    // it's a sig becuase i don't want to make it be custom input lololol
    public static void doWrite(Secured_Object object, User user) {
        try {
            File file = new File(object.getPath());
            if (!file.exists()) {
                System.out.println("File not found: " + object.getPath());
                return; // This shouldn't ever error, since the files are both local and static
            }
            FileWriter sigWriter = new FileWriter(file, true);
            // Focus the end of the file, instead of writing over the file
            sigWriter.write("\n\n" + user.getSignature());
            sigWriter.close();
            System.out.println("Signature written to " + object.getName());
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}