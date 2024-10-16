package seedu.address.model.person;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class ModuleContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public ModuleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsSubstringIgnoreCase(person.getModule().moduleCode, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleContainsKeywordsPredicate)) {
            return false;
        }

        ModuleContainsKeywordsPredicate otherModuleContainsKeywordsPredicate = (ModuleContainsKeywordsPredicate) other;
        return keywords.equals(otherModuleContainsKeywordsPredicate.keywords);
    }

    public List<String> getKeywords() {
        return Collections.unmodifiableList(keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
