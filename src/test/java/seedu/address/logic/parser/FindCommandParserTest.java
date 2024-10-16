package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.ModuleContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand1 =
                new FindCommand(new ArrayList<>(Arrays.asList(
                        new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"))
                )));
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice " + PREFIX_NAME + "Bob", expectedFindCommand1);

        // Test with name keywords and multiple whitespaces between keywords
        assertParseSuccess(parser, " \n " + PREFIX_NAME + "Alice \n \t " + PREFIX_NAME + "Bob  \t",
                expectedFindCommand1);

        FindCommand expectedFindCommand2 =
                new FindCommand(new ArrayList<>(Arrays.asList(
                        new ModuleContainsKeywordsPredicate(Arrays.asList("CS2103T", "CS1101S"))
                )));
        assertParseSuccess(parser, " " + PREFIX_MODULE + "CS2103T " + PREFIX_MODULE + "CS1101S",
                expectedFindCommand2);

        // Test with module keywords and multiple whitespaces between keywords
        assertParseSuccess(parser, " \n " + PREFIX_MODULE + "CS2103T \n \t " + PREFIX_MODULE + "CS1101S  \t",
                expectedFindCommand2);

        FindCommand expectedFindCommand3 =
                new FindCommand(new ArrayList<>(Arrays.asList(
                        new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")),
                        new ModuleContainsKeywordsPredicate(Arrays.asList("CS2103", "CS1231"))
                )));

        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice " + PREFIX_NAME + "Bob "
                + PREFIX_MODULE + "CS2103 " + PREFIX_MODULE + "CS1231", expectedFindCommand3);

        // Test with both name and module keywords and multiple whitespaces
        assertParseSuccess(parser, " \n " + PREFIX_NAME + "Alice \n \t " + PREFIX_NAME + "Bob  \t "
                + PREFIX_MODULE + "CS2103  \n  " + PREFIX_MODULE + "CS1231", expectedFindCommand3);
    }

}
