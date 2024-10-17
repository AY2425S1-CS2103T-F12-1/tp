package seedu.address.model.person;

import org.junit.jupiter.api.Test;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.PersonBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ModuleRoleContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        ModuleRoleMap firstPredicateKeyword = new ModuleRoleMap(new ModuleCode[]{new ModuleCode("CS1101S")},
                new RoleType[]{RoleType.TUTOR});
        ModuleRoleMap secondPredicateKeyword = new ModuleRoleMap(new ModuleCode[]{new ModuleCode("MA1522")},
                new RoleType[]{RoleType.TUTOR});

        ModuleRoleContainsKeywordsPredicate first = new ModuleRoleContainsKeywordsPredicate(firstPredicateKeyword);
        ModuleRoleContainsKeywordsPredicate second = new ModuleRoleContainsKeywordsPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(first.equals(first));

        // same values -> returns true
        ModuleRoleContainsKeywordsPredicate firstCopy = new ModuleRoleContainsKeywordsPredicate(firstPredicateKeyword);
        assertTrue(first.equals(firstCopy));

        // different types -> returns false
        assertFalse(first.equals(1));

        // null -> returns false
        assertFalse(first.equals(null));

        // different person -> returns false
        assertFalse(first.equals(second));
    }

    @Test
    public void test_moduleRoleContainsKeywords_returnsTrue() {
        // One keyword
        ModuleRoleContainsKeywordsPredicate predicate = new ModuleRoleContainsKeywordsPredicate(
                new ModuleRoleMap(new ModuleCode[]{new ModuleCode("CS1101S")}, new RoleType[]{RoleType.STUDENT}));
        assertTrue(predicate.test(new PersonBuilder()
                .withModuleRoleMap(new ModuleCode("CS1101S"), RoleType.STUDENT).build()));

        // Multiple keywords
        predicate = new ModuleRoleContainsKeywordsPredicate(new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS1101S"), new ModuleCode("MA1521")},
                new RoleType[]{RoleType.STUDENT, RoleType.TUTOR}));
        assertTrue(predicate.test(new PersonBuilder()
                .withModuleRoleMap(new ModuleCode[]{new ModuleCode("CS1101S"), new ModuleCode("MA1521")},
                        new RoleType[]{RoleType.STUDENT, RoleType.TUTOR}).build()));

        // Only one matching keyword
        predicate = new ModuleRoleContainsKeywordsPredicate(new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS1101S")}, new RoleType[]{RoleType.STUDENT}));
        assertTrue(predicate.test(new PersonBuilder()
                .withModuleRoleMap(new ModuleCode[]{new ModuleCode("CS1101S"), new ModuleCode("MA1521")},
                        new RoleType[]{RoleType.STUDENT, RoleType.TUTOR}).build()));


        // Mixed-case keywords
        predicate = new ModuleRoleContainsKeywordsPredicate(
                new ModuleRoleMap(new ModuleCode[]{new ModuleCode("cS1101s")}, new RoleType[]{RoleType.STUDENT}));
        assertTrue(predicate.test(new PersonBuilder()
                .withModuleRoleMap(new ModuleCode("CS1101S"), RoleType.STUDENT).build()));
    }

    @Test
    public void test_moduleRoleDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ModuleRoleContainsKeywordsPredicate predicate = new ModuleRoleContainsKeywordsPredicate(
                new ModuleRoleMap(new ModuleCode[]{}, new RoleType[]{}));
        assertFalse(predicate.test(new PersonBuilder()
                .withModuleRoleMap(new ModuleCode("CS1101S"), RoleType.STUDENT).build()));

        // Non-matching keyword
        predicate = new ModuleRoleContainsKeywordsPredicate(
                new ModuleRoleMap(new ModuleCode[]{new ModuleCode("CS1101S")}, new RoleType[]{RoleType.STUDENT}));
        assertFalse(predicate.test(new PersonBuilder()
                .withModuleRoleMap(new ModuleCode("MA1522"), RoleType.STUDENT).build()));

        // Module matched, role not matched
        predicate = new ModuleRoleContainsKeywordsPredicate(
                new ModuleRoleMap(new ModuleCode[]{new ModuleCode("MA1521")}, new RoleType[]{RoleType.STUDENT}));
        assertFalse(predicate.test(new PersonBuilder()
                .withModuleRoleMap(new ModuleCode("MA1521"), RoleType.TUTOR).build()));
    }

    @Test
    public void toStringMethod() throws ParseException {
        List<String> keywords = List.of("CS1101S", "MA1522-ta");
        ModuleRoleContainsKeywordsPredicate predicate = new ModuleRoleContainsKeywordsPredicate(
                ParserUtil.parseModuleRolePairs(keywords));

        String expected = ModuleRoleContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
