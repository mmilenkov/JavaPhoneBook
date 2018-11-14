import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

class PhoneBook {
    private SortedMap<String, Contact> contactMap = new TreeMap<>();
    private List<Contact> topFiveList = new ArrayList<>();

    void initialisePhoneBook(String fileName) {
        try(BufferedReader in = new BufferedReader(new FileReader(fileName))) { //Move the try block to main? I.E stop execution if this fails
            String line;
            Contact newContact;
            while ((line = in.readLine()) != null) {
                String[] split = line.split("=");
                if (isValidPhoneNumber(split[1])) {
                    String key = split[0];
                    String number = convertToNormalizedPhoneNumber(split[1]);
                    newContact = new Contact(key, number, Integer.parseInt(split[2]));
                    contactMap.put(key, newContact);
                }
            }
        }
        catch (Exception e) {
            System.out.println("Exception" + e);
        }
    }

    void addContact (String name,String phoneNumber, int outgoingCalls) {
        Contact newContact = new Contact(name, convertToNormalizedPhoneNumber(phoneNumber), outgoingCalls);
        contactMap.put(name,newContact);
        checkForNewTopFive(newContact);
    }

    boolean isValidPhoneNumber(String phoneNumber){
        return (phoneNumber.matches("(0|00359|\\+359)(8[7-9])[2-9]\\d{6}"));
    }

    String convertToNormalizedPhoneNumber(String phoneNumber) {
        if (phoneNumber.matches("(0)(8[7-9])[2-9]\\d{6}")) {
            phoneNumber = "+359" + phoneNumber.substring(1);
        }
        else if (phoneNumber.matches("(00359)(8[7-9])[2-9]\\d{6}")) {
            phoneNumber = "+359" + phoneNumber.substring(5);
        }
        return phoneNumber;
    }

    boolean removeContact(String contactToBeRemoved) {
        if(contactMap.containsKey(contactToBeRemoved)) {
            contactMap.remove(contactToBeRemoved);
            return true;
        }
        else {
            return false;
        }
    }

    String lookUpContact(String contactName) {
        return contactMap.get(contactName) != null ? contactMap.get(contactName).getPhoneNumber() : "No such contact exists";
    }

    void printContacts(){
        if (contactMap.size() == 0){ //Does this need to be outside of here?
            System.out.println("There are no contacts in the Phone Book");
        }
        else {
            contactMap.forEach((key, value) -> System.out.print(value.printContact()));
            System.out.println();
        }
    } // How to test without changing function?

    void printTopFive() {
        if(topFiveList.size() != 0) {
            for (Contact contact : topFiveList) {
                System.out.println(contact.printContact());
            }
        }
        else
            System.out.println("There are no contacts in the Phone book");
    } // How to test without changing function?

    void savePhoneBookToFile(String fileName) {
        try(BufferedWriter out = new BufferedWriter(new FileWriter(fileName))) { // Move to main block and handle the failure? Try again maybe?
            File file = new File(fileName);
            boolean deleteFile = file.delete();
            contactMap.forEach((key, value) -> {
                try {
                    out.write(key + "=" + value.getPhoneNumber() + "=" + value.getOutgoingCalls());
                    out.newLine();
                }
                catch (IOException e) {
                    System.out.println("Failed to print to file");
                }
            });
        }
        catch (Exception e) {
            System.out.println("Exception" + e);
        }
    }

    void generateTopFive() { //Better?
        contactMap.forEach((key, value) -> topFiveList.add(value));
        sortTopFiveList();
        trimTopFiveList();
    }

    private void checkForNewTopFive(Contact newContact) {
        for(Contact contact : topFiveList) {
            if(contact.getOutgoingCalls() < newContact.getOutgoingCalls()) {
                addNewTopFive(newContact);
                break;
            }
        }
    }

    private void addNewTopFive(Contact newContact) {
        topFiveList.add(newContact);
        sortTopFiveList();
        trimTopFiveList();
    }

    private void trimTopFiveList(){
        int size = topFiveList.size();
        topFiveList.subList(Math.min(5,size),size).clear();
    }

    private void sortTopFiveList() {
        topFiveList.sort(this::compare);
    }

    private int compare(Contact c1, Contact c2) {
        return Integer.compare(c2.getOutgoingCalls(),c1.getOutgoingCalls());
    }
}
