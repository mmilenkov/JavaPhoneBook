import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        PhoneBook testPhoneBook = new PhoneBook();
        String fileName = "contactlist.txt";
        testPhoneBook.initialisePhoneBook(fileName);
        testPhoneBook.generateTopFive();
        boolean changesMade = false;
        boolean mainLoop = true;
        boolean phoneNumberLoop;
        do {
            Scanner in = new Scanner(System.in);
            printUI();
            int option = in.nextInt();
            in.nextLine();
            switch (option) {
                case 1:
                    String phoneNumber;
                    int outgoingCalls = (int)Math.floor(Math.random()*100);
                    System.out.println("Please enter a name for the contact:");
                    String contactName = (in.nextLine().toLowerCase());
                    do {
                        System.out.println("Please enter a valid phone number.");
                        phoneNumber = in.nextLine();
                        if(testPhoneBook.isValidPhoneNumber(phoneNumber)){ //Would using this check be classed as bad code? I.E pointing back to main for the check
                            phoneNumber = testPhoneBook.convertToNormalizedPhoneNumber(phoneNumber);
                            testPhoneBook.addContact(contactName,phoneNumber, outgoingCalls);
                            changesMade = true;
                            break;
                        }
                        else {
                            System.out.println("This is not a valid phone number.");
                            System.out.println("Would you like to try again?");
                            phoneNumberLoop = askIfUserWantsToContinue(in);
                        }
                    } while (phoneNumberLoop); //Meant to be here on the next line?
                    break;
                case 2:
                    System.out.println("Please enter the name of the contact you would like to remove:");
                    String contactToBeRemoved = (in.nextLine().toLowerCase());
                    System.out.println(testPhoneBook.removeContact(contactToBeRemoved)? "Contact removed" : "No such contact exists");
                    changesMade = true;
                    break;
                case 3:
                    testPhoneBook.printContacts();
                    break;
                case 4:
                    System.out.println("Please enter the name of the contact you would like to find:");
                    contactName = in.nextLine().toLowerCase();
                    System.out.println(testPhoneBook.lookUpContact(contactName));
                    break;
                case 5:
                    testPhoneBook.printTopFive();
                    break;
                case 6:
                    mainLoop=false;
                    testPhoneBook.savePhoneBookToFile(fileName);
                    break;
                default:
                    System.out.println("Invalid Selection");
                    break;
            }
            if(mainLoop) {
                System.out.println();
                System.out.println("Would you like to do something else?");
                mainLoop = askIfUserWantsToContinue(in);
                if(!mainLoop && changesMade) {
                    testPhoneBook.savePhoneBookToFile(fileName);
                }
            }
            else if(!mainLoop) { // Never called even after mainloop changes. Why?
                testPhoneBook.savePhoneBookToFile(fileName);
            }
        } while(mainLoop); //Meant to be here on the next line?
    }

    private static void printUI() {
        System.out.println();
        System.out.println("What would you like to do?");
        System.out.println("1. Add a contact");
        System.out.println("2. Remove a contact");
        System.out.println("3. Print contact list");
        System.out.println("4. Find a contact");
        System.out.println("5. Print the 5 contact with the most outgoing calls:");
        System.out.println("6. Quit");
        System.out.println();
    }

    private static boolean askIfUserWantsToContinue(Scanner in) {
        System.out.println("Y/N");
        String choice = in.nextLine();
        return (choice.equals("y") || choice.equals("Y"));


    }
}
