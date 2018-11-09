import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

class PhoneBook {
    //NOTE - MOST USED OPERATION - PRINTING
    private SortedMap<String, Contact> contactList = new TreeMap<>();
    private List<Contact> outgoingSortedList = new LinkedList<>();

    void initialisePhoneBook(String fileName) throws IOException {
        FileReader fstream = new FileReader(fileName);
        BufferedReader in = new BufferedReader(fstream);
        String line;
        Contact newContact;
        while ((line = in.readLine()) != null) {
            String[] split = line.split("=");
            if(isValidNumber(split[1])) { // remove? - Skips invalid numbers
                String key = split[0];
                String number = convertToNormalized(split[1]); //converts valid numbers to normalized state
                newContact = new Contact(key, number,Integer.parseInt(split[2]));
                contactList.put(key, newContact);
            }
        }
        in.close();
        fstream.close();
        generateTopFive(); // Generates top5 list
    }

    void savePhoneBookToFile(String fileName) throws IOException {
        //Clear file first
        File file = new File(fileName);
        boolean delete = file.delete();
        FileWriter fstream = new FileWriter("contactlist.txt");
        BufferedWriter out = new BufferedWriter(fstream);
        contactList.forEach((key,value) ->{
            try {
                out.write(key + "=" + value.getNumber() + "=" + value.getOutgoingCalls());
                out.newLine();
            }
            catch(IOException e){System.out.println("Failed to print to file");}});
        out.close();
        fstream.close();

    }

    private void generateTopFive(){ //Better?
        contactList.forEach((key,value) -> outgoingSortedList.add(value));
        outgoingSortedList.sort(this::compare);
        trimTopFive();
    }

    void addContact (String name,String number, int outgoingCalls) {
        Contact newContact = new Contact(name, convertToNormalized(number), outgoingCalls);
        contactList.put(name,newContact);
        isNewTopFive(newContact);
    }

    boolean removeContact(String remove) {
        if(contactList.containsKey(remove)) {
            contactList.remove(remove);
            return true;
        }
        else
            return false;
    }

    String lookUpContact(String name) {
        return contactList.get(name) !=null ? contactList.get(name).getNumber() : "No such contact exists";
    }

    void printContacts(){
        if (contactList.size() == 0){ //Does this need to be outside of here?
            System.out.println("There are no contacts in the Phone Book");
        }
        else{
            contactList.forEach((key,value) -> System.out.print(value.printContact()));
        }
    }

    void printTopFive() {
        for(Contact contact : outgoingSortedList) {
            System.out.println(contact.printContact());
        }
    }

    private static boolean isValidNumber(String number){
        return (number.matches("(0|00359|\\+359)(8[7-9])[2-9]\\d{6}"));
    }

    private String convertToNormalized(String number) { // Converts numbers to normalized state when reading from file
        if (number.matches("(0)(8[7-9])[2-9]\\d{6}")) {
            number = "+359" + number.substring(1);
        } else if (number.matches("(00359)(8[7-9])[2-9]\\d{6}")) {
            number = "+359" + number.substring(5);
        }
        return number;
    }

    private void isNewTopFive(Contact newContact) { //Way to add element under another
        for(Contact contact : outgoingSortedList) {
            if(contact.getOutgoingCalls() < newContact.getOutgoingCalls()) {
            outgoingSortedList.add(newContact);
            System.out.println("Added!");
            break;
            }
        }
        trimTopFive();
    }

    private void trimTopFive(){
        outgoingSortedList.sort(this::compare);
        outgoingSortedList.subList(Math.min(5,outgoingSortedList.size()),outgoingSortedList.size()).clear();
    }

    private int compare(Contact c1, Contact c2) {
        return Integer.compare(c2.getOutgoingCalls(),c1.getOutgoingCalls());
    }

}
