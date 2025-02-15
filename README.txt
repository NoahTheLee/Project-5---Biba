# README: Biba Integrity Model Implementation

## Overview
This Java program enforces the **Biba Integrity Model**, which ensures data integrity by preventing unauthorized modifications. The program simulates a secure system where users and objects have assigned integrity levels, enforcing the following two rules:
1. **Simple Integrity Property (No Read Down)**: Users cannot read objects with a lower integrity level.
2. **Star Integrity Property (No Write Up)**: Users cannot write to objects with a higher integrity level.

## System Requirements
- Java 8 or higher
- Required `.txt` files (provided in the download package)
- No additional dependencies beyond standard Java libraries

## Required Files
The following `.txt` files must be present in the same directory as the program:
- `Welcome_Message.txt`
- `Location_List.txt`
- `Director_Contact_List.txt`
- `Nuclear_Codes.txt`
- `Special_Chicken_Recipe.txt`

Alternatively, files with the same names can be substituted, and the program will retain full functionality.

## How to Run
1. **Compile the Java program**:  
   ```sh
   javac Biba.java
   ```
2. **Run the program**:  
   ```sh
   java Biba
   ```
3. **Follow the on-screen prompts** to:
   - Select a user by entering a valid 3-digit ID
   - List secured objects
   - Attempt read and write operations (with access controlled by Biba’s rules)
   - Switch users or exit the program

## Program Features
### User System
Each user has a **name**, **ID**, and **integrity level**. The integrity levels used in this implementation are:
- **NOMINAL** (Lowest)
- **RESTRICTED**
- **SECURE**
- **THAUMIEL**
- **APOLLYON** (Highest)

Users are assigned a predefined integrity level and must abide by Biba’s access control rules when attempting operations.

### Object System
Objects also have assigned integrity levels. Each object corresponds to a `.txt` file with data stored within it.

### Access Control
- **Reading Files**: Users may only read files **at or above** their integrity level.
- **Writing to Files**: Users may only write to files **at or below** their integrity level.

### Interactive Command System
Upon running the program, users are presented with the following options:
```
--------------------------------------------------
 Available Commands:
 1  - Change to different User
 2  - List secured objects
 3  - Try to read a secured object
 4  - Try to write to a secured object
 0  - Exit
--------------------------------------------------
Enter your choice:
```

## Example Usage
### Scenario 1: Attempting an Unauthorized Read
User `Normand` (NOMINAL level) tries to read `Director_Contact_List.txt` (SECURE level):
```
You can not read this object.
```

### Scenario 2: Successful Write Operation
User `Thamir` (THAUMIEL level) writes a signature to `Nuclear_Codes.txt` (THAUMIEL level):
```
Would you like to write your signature to it? (y/n)
y
Signature written to Nuclear Codes
```

## Error Handling
- Invalid user ID inputs prompt re-entry
- Users cannot access non-existent objects
- Access is denied with appropriate messages when Biba’s rules are violated
- Missing files will generate a `File not found` error

## Additional Enhancements
(Optional features for extra credit)
- Users and objects could be dynamically assigned new integrity levels
- A logging system could track all read/write attempts and results

## Submission Details
Ensure the following files are included in your submission:
1. **Java Source Code**: `Biba.java`
2. **Test Results Document**:
   - Includes test cases demonstrating access control enforcement
   - Shows user integrity levels, attempted operations, and results
3. **README File** (this document)

