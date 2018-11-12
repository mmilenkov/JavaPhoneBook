import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PhoneBookTest {
    PhoneBook testPhoneBook = new PhoneBook();
    String name = "testNameOne";
    String invalidName = "thisisajoke";
    String validNumber = "+359884807750";
    String invalidNumber = "85";
    int outgoingCalls = 5;


    {
        testPhoneBook.addContact("searchName","+359884807750", 0);
    }

    @Test
    public void canAddContact() {
        testPhoneBook.addContact(name,validNumber, outgoingCalls);

        Assert.assertEquals(validNumber,testPhoneBook.lookUpContact(name));//Better way to check?
        System.out.println("test1 ran");
    }

    @Test
    public void canLookUpContact() {
        String contact = testPhoneBook.lookUpContact("searchName");

        Assert.assertEquals(validNumber,contact);
        System.out.println("test2 ran");
    }

    @Test
    public void canLookUpNonexistentContact() {
        String contact = testPhoneBook.lookUpContact(invalidName);

        Assert.assertEquals("No such contact exists",contact);
        System.out.println("test3 ran");
    }

    @Test
    public void canRemoveContact(){
        Assert.assertTrue(testPhoneBook.removeContact("searchName"));
        System.out.println("test3 ran");
    }

    @Test
    public void canRemoveInvalidContact() {
        Assert.assertFalse(testPhoneBook.removeContact("valio"));
        System.out.println("test4 ran");
    }

    @Test
    public void canPrintContactList() {
        testPhoneBook.printContacts();
    }


}