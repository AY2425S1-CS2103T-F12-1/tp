package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.ModuleRoleContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;


/**
 * Finds and lists all persons in address book whose name or module contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names or modules contain "
            + "any of the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: "
            + PREFIX_NAME + "KEYWORD ["
            + PREFIX_NAME + "MORE_KEYWORDS]..."
            + PREFIX_MODULE + "KEYWORD ["
            + PREFIX_MODULE + "MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "alice "
            + PREFIX_NAME + "bob "
            + PREFIX_MODULE+ "CS1101S"
            + PREFIX_MODULE+ "CS2030S";

    private final List<Predicate<Person>> predicates;

    public FindCommand(List<Predicate<Person>> predicates) {
        this.predicates = predicates;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        predicates.forEach(model::updateFilteredPersonList);
        String formattedKeywords = getAllKeywordsFromPredicates(predicates);

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                        model.getFilteredPersonList().size(),
                        formattedKeywords
                )
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicates.equals(otherFindCommand.predicates);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicates)
                .toString();
    }

    /**
     * Retrieves all keywords from the provided list of predicates and formats them as a comma-separated string.
     * Each keyword is surrounded by "".
     *
     * @param predicates A list of predicates that may include name or module-based predicates.
     * @return A formatted string containing all keywords from the predicates.
     */
    private String getAllKeywordsFromPredicates(List<Predicate<Person>> predicates) {
        return predicates.stream()
                .flatMap(predicate -> extractKeywords(predicate).stream())
                .map(kw -> "\"" + kw + "\"")
                .collect(Collectors.joining(", "));
    }

    /**
     * Extracts keywords from a given predicate, depending on the type of predicate.
     * If the predicate is of an unrecognized type, an empty list is returned.
     *
     * @param predicate The predicate from which to extract keywords.
     * @return A list of keywords from the predicate, or an empty list if the predicate type is not recognized.
     */
    private List<String> extractKeywords(Predicate<Person> predicate) {
        if (predicate instanceof NameContainsKeywordsPredicate) {
            return ((NameContainsKeywordsPredicate) predicate).getKeywords();
        } else if (predicate instanceof ModuleRoleContainsKeywordsPredicate) {
            return ((ModuleRoleContainsKeywordsPredicate) predicate).getModuleRolePairs();
        }
        return Collections.emptyList();
    }
}
