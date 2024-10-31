package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isInvalidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters

        // '-' should be mid-word
        assertFalse(Name.isValidName("-peter"));          // start of first word
        assertFalse(Name.isValidName("peter-"));          // end of first word
        assertFalse(Name.isValidName("peter -tan"));      // start of nonfirst word
        assertFalse(Name.isValidName("peter tan-"));      // end of nonfirst word
        assertFalse(Name.isValidName("peter tan--lee"));  // consecutive dashes
        assertFalse(Name.isValidName("-"));

        // ',' should be end-word, and not the first character
        assertFalse(Name.isValidName(",peter"));       // start of first word
        assertFalse(Name.isValidName("p,eter"));       // mid of first word
        assertFalse(Name.isValidName("peter ,tan"));   // start of nonfirst word
        assertFalse(Name.isValidName("peter t,an"));   // mid of nonfirst word
        assertFalse(Name.isValidName("peter,, tan"));  // consecutive commas
        assertFalse(Name.isValidName(","));

        // '/' should only appear in the context of [sSdD]/[oO]
        assertFalse(Name.isValidName("peter a/o david"));  // mismatch of [sSdD]
        assertFalse(Name.isValidName("peter s/a david"));  // mismatch of [oO]
        assertFalse(Name.isValidName("/peter"));           // start of first word
        assertFalse(Name.isValidName("peter/"));           // end of first word
        assertFalse(Name.isValidName("peter /tan"));       // start of nonfirst word
        assertFalse(Name.isValidName("peter tan/"));       // end of nonfirst word
        assertFalse(Name.isValidName("peter s//o tan"));   // consecutive slashes
        assertFalse(Name.isValidName("/"));

        // [sSdD]/[oO] should be its own word
        assertFalse(Name.isValidName("peter as/o david"));   // extra chars before
        assertFalse(Name.isValidName("peter s/oa david"));   // extra chars after
        assertFalse(Name.isValidName("peter as/oa david"));  // extra chars before after

        // [sSdD]/[oO] should not be first or last word
        assertFalse(Name.isValidName("s/o peter david"));    // should not be the first word
        assertFalse(Name.isValidName("peter david s/o"));    // should not be the last word

        // combination
        assertFalse(Name.isValidName("peter tan-,"));  // comma should be preceded by alphanumeric
    }

    @Test
    public void isValidName() {
        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names

        // '-' should be mid-word
        assertTrue(Name.isValidName("peter tan-lee"));
        assertTrue(Name.isValidName("peter tan-l-ee"));  // multiple dashes in a word

        // ',' should be end-word, and not the first character
        assertTrue(Name.isValidName("tan ah kow, peter"));
        assertTrue(Name.isValidName("tan, ah, kow, peter"));  // multiple commas

        // '/' should only appear in the context of [sSdD]/[oO]
        assertTrue(Name.isValidName("peter s/o david"));
        assertTrue(Name.isValidName("peter S/o david"));
        assertTrue(Name.isValidName("peter s/O david"));
        assertTrue(Name.isValidName("emily d/o david"));
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Name("Other Valid Name")));
    }
}
