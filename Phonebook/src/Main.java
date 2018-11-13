import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        PhoneBook testPhoneBook = new PhoneBook();
        String fileName = "contactlist.txt";
        testPhoneBook.initialisePhoneBook(fileName);

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
                        if(testPhoneBook.isValidPhoneNumber(phoneNumber)){
                            phoneNumber = testPhoneBook.convertToNormalizedPhoneNumber(phoneNumber);
                            testPhoneBook.addContact(contactName,phoneNumber, outgoingCalls);
                            break;
                        }
                        else {
                            System.out.println("This is not a valid phone number.");
                            System.out.println("Would you like to try again?");
                            phoneNumberLoop = askIfUserWantsToContinue(in);
                        }
                    }
                    while (phoneNumberLoop);
                    break;
                case 2:
                    System.out.println("Please enter the name of the contact you would like to remove:");
                    String contactToBeRemoved = (in.nextLine().toLowerCase());
                    System.out.println(testPhoneBook.removeContact(contactToBeRemoved)? "Contact removed" : "No such contact exists");
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
                    break;
                default:
                    System.out.println("Invalid Selection");
                    break;
            }
            if(mainLoop) {
                System.out.println();
                System.out.println("Would you like to do something else?");
                mainLoop = askIfUserWantsToContinue(in);
            }
            else if(!mainLoop) { // Is it possible to remove highlight?
                testPhoneBook.savePhoneBookToFile(fileName);
            }
        }
        while(mainLoop);
    }

    private static void printUI()
    {
        System.out.println();
        System.out.println("This is the testing menu. What would you like to do?");
        System.out.println("1.Add a contact");
        System.out.println("2.Remove a contact");
        System.out.println("3.Print contact list");
        System.out.println("4.Find a contact");
        System.out.println("5.Print the 5 contact with the most outgoing calls:");
        System.out.println("6.Quit");
        System.out.println();
    }

    private static boolean askIfUserWantsToContinue(Scanner in)
    {
        System.out.println("Y/N");
        String choice = in.nextLine();
        return (choice.equals("y") || choice.equals("Y"));


    }
}
