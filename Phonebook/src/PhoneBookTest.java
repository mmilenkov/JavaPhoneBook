import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PhoneBookTest {
    PhoneBook testPhoneBook = new PhoneBook();
    String name = "testNameOne";
    String invalidName = "thisisajoke";
    String validNumber = "+359884807750";
    int outgoingCalls = 5;

    @Before
    public void initialiseTests() {
        testPhoneBook.addContact("searchName","+359884807750");
        testPhoneBook.generateTopFive();
    }

    @Test
    public void canAddContact() {
        testPhoneBook.addContact(name,validNumber);

        Assert.assertEquals(validNumber,testPhoneBook.lookUpContact(name).getPhoneNumber());
        System.out.println("test1 ran");
    }

    @Test
    public void canLookUpContact() {
        Contact contact = testPhoneBook.lookUpContact("searchName");

        Assert.assertEquals(validNumber,contact.getPhoneNumber());
        System.out.println("test2 ran");
    }

    @Test
    public void canLookUpNonexistentContact() {
        Contact contact = testPhoneBook.lookUpContact(invalidName);

        Assert.assertNull(contact);
        System.out.println("test3 ran");
    }

    @Test
    public void canRemoveContact(){
        Assert.assertTrue(testPhoneBook.removeContact("searchName"));
        System.out.println("test4 ran");
    }

    @Test
    public void canRemoveInvalidContact() {
        Assert.assertFalse(testPhoneBook.removeContact("valio"));
        System.out.println("test5 ran");
    }

    /*
    @Test
    public void canPrintContactList() {
        testPhoneBook.printContacts();
    }

    @Test
    public void canPrintTopFive() {
        testPhoneBook.printTopFive();
    }
    */
}