package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // assertion fail
        assertThrows(AssertionError.class, () -> Phone.isValidPhone(" ")); // spaces only
        assertThrows(AssertionError.class, () -> Phone.isValidPhone("93 12 15 34")); // spaces within digits

        // invalid phone numbers
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone("91")); // less than 3 numbers
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9p11")); // should not have alphabets in the phone number

        // valid phone numbers
        assertTrue(Phone.isValidPhone("911")); // exactly 3 numbers
        assertTrue(Phone.isValidPhone("9-11")); // non-consecutive 3 numbers
        assertTrue(Phone.isValidPhone("93121534"));
        assertTrue(Phone.isValidPhone("124293842033123")); // long phone numbers
    }

    @Test
    public void isValidPhoneField() {
        // ---- The test cases for isValidPhone() are repeated here and should give a similar result.

        // null phone number
        assertThrows(NullPointerException.class, () -> Phone.isValidPhoneField(null));

        // invalid phone numbers, but will not trigger assertion based on code logic
        assertFalse(Phone.isValidPhoneField(" ")); // spaces only
        assertFalse(Phone.isValidPhoneField("93 12 15 34")); // spaces within digits

        // invalid phone numbers
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone("91")); // less than 3 numbers
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9p11")); // should not have alphabets in the phone number

        // valid phone numbers
        assertTrue(Phone.isValidPhone("911")); // exactly 3 numbers
        assertTrue(Phone.isValidPhone("9-11")); // non-consecutive 3 numbers
        assertTrue(Phone.isValidPhone("93121534"));
        assertTrue(Phone.isValidPhone("124293842033123")); // long phone numbers

        // ---- Unique test cases for isValidPhoneField()
        
        // invalid
        assertFalse(Phone.isValidPhoneField("91h 23a 1")); // each token has less than 3 numbers
        assertFalse(Phone.isValidPhoneField("91h     23a 1")); // extra spaces

        // valid
        String VALID_PHONE_NUMBER = "8123-4567";
        assertTrue(Phone.isValidPhoneField(VALID_PHONE_NUMBER + " misc text")); // valid phone number at start
        assertTrue(Phone.isValidPhoneField("misc text " + VALID_PHONE_NUMBER)); // valid phone number at end
        assertTrue(Phone.isValidPhoneField("misc text           " + VALID_PHONE_NUMBER)); // extra spaces
    }

    @Test
    public void equals() {
        Phone phone = new Phone("999");

        // same values -> returns true
        assertTrue(phone.equals(new Phone("999")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new Phone("995")));
    }
}
