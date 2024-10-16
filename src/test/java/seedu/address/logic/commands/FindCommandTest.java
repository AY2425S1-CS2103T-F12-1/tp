package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ModuleContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstNamePredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondNamePredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));
        ModuleContainsKeywordsPredicate firstModulePredicate =
                new ModuleContainsKeywordsPredicate(Collections.singletonList("CS1010"));
        ModuleContainsKeywordsPredicate secondModulePredicate =
                new ModuleContainsKeywordsPredicate(Collections.singletonList("CS2103"));

        List<Predicate<Person>> firstNamePredicates = new ArrayList<>();
        firstNamePredicates.add(firstNamePredicate);

        List<Predicate<Person>> secondNamePredicates = new ArrayList<>();
        secondNamePredicates.add(secondNamePredicate);

        List<Predicate<Person>> firstModulePredicates = new ArrayList<>();
        firstNamePredicates.add(firstModulePredicate);

        List<Predicate<Person>> secondModulePredicates = new ArrayList<>();
        secondNamePredicates.add(secondModulePredicate);

        List<Predicate<Person>> firstNameAndModulePredicates = new ArrayList<>();
        firstNameAndModulePredicates.add(firstNamePredicate);
        firstNameAndModulePredicates.add(firstModulePredicate);

        List<Predicate<Person>> secondNameAndModulePredicates = new ArrayList<>();
        secondNameAndModulePredicates.add(secondNamePredicate);
        secondNameAndModulePredicates.add(secondModulePredicate);

        FindCommand findFirstNameCommand = new FindCommand(firstNamePredicates);
        FindCommand findSecondNameCommand = new FindCommand(secondNamePredicates);

        FindCommand findFirstModuleCommand = new FindCommand(firstModulePredicates);
        FindCommand findSecondModuleCommand = new FindCommand(secondModulePredicates);

        // Create FindCommand with both name and module predicates
        FindCommand findFirstNameAndModuleCommand = new FindCommand(firstNameAndModulePredicates);
        FindCommand findSecondNameAndModuleCommand = new FindCommand(secondNameAndModulePredicates);

        // same object -> returns true
        assertTrue(findFirstNameCommand.equals(findFirstNameCommand));
        assertTrue(findFirstModuleCommand.equals(findFirstModuleCommand));

        // same values -> returns true
        FindCommand findFirstNameCommandCopy = new FindCommand(new ArrayList<>(firstNamePredicates));
        FindCommand findFirstModuleCommandCopy = new FindCommand(new ArrayList<>(firstModulePredicates));
        assertTrue(findFirstNameCommand.equals(findFirstNameCommandCopy));
        assertTrue(findFirstModuleCommand.equals(findFirstModuleCommandCopy));

        // different types -> returns false
        assertFalse(findFirstNameCommand.equals(1));
        assertFalse(findFirstModuleCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstNameCommand.equals(null));
        assertFalse(findFirstModuleCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstNameCommand.equals(findSecondNameCommand));

        // different module -> returns false
        assertFalse(findFirstModuleCommand.equals(findSecondModuleCommand));

        // different name and module predicates -> returns false
        assertFalse(findFirstNameAndModuleCommand.equals(findSecondNameAndModuleCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0, "");
        NameContainsKeywordsPredicate predicate = prepareNamePredicate(" ");
        List<Predicate<Person>> predicates = new ArrayList<>();
        predicates.add(predicate);

        FindCommand command = new FindCommand(predicates);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3, "\"Kurz\", \"Elle\", \"Kunz\"");
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate("Kurz Elle Kunz");
        List<Predicate<Person>> predicates = new ArrayList<>();
        predicates.add(namePredicate);

        FindCommand command = new FindCommand(predicates);
        expectedModel.updateFilteredPersonList(namePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_nameAndModuleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2, "\"Kurz\", \"CS2103T\"");
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate("Kurz");
        ModuleContainsKeywordsPredicate modulePredicate = prepareModulePredicate("CS2103T");

        List<Predicate<Person>> predicates = new ArrayList<>();
        predicates.add(namePredicate);
        predicates.add(modulePredicate);

        FindCommand command = new FindCommand(predicates);
        expectedModel.updateFilteredPersonList(namePredicate.and(modulePredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        ModuleContainsKeywordsPredicate modulePredicate = new ModuleContainsKeywordsPredicate(Arrays.asList("CS2103T"));
        List<Predicate<Person>> predicates = new ArrayList<>();
        predicates.add(namePredicate);
        predicates.add(modulePredicate);

        FindCommand findCommand = new FindCommand(predicates);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicates + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate prepareNamePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private ModuleContainsKeywordsPredicate prepareModulePredicate(String userInput) {
        return new ModuleContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
