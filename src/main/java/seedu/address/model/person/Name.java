package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    private static final String NAME_FIRST_WORD_REGEX = ""
        + "(\\p{Alnum}+"              // Starts with alphanumeric
        + "(?:-\\p{Alnum}+)*,?)";     // Allow mid-word hyphens or trailing comma

    /**
     * Matches a name string where the first word is alphanumeric with optional hyphens and trailing comma.
     * Subsequent words are separated by spaces and can be either similar words or "s/o", "d/o", "S/O", "D/O".
     * Entire string must follow this structure from start to end.
     */
    public static final String VALIDATION_REGEX = ""
        + "^"
        + NAME_FIRST_WORD_REGEX                // 1st word
        + "(\\s+("                             // Additional words are separated by a space, then
        + NAME_FIRST_WORD_REGEX + "|"          // (either: 1st word |
        + "(?:[sSdD]/[oO]))"                   // or: Allow s/o, d/o, S/O, D/O as non-first words)
        + ")*"                                 // Close group, allow 0 or more
        + "$";                                 // End of the string

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
