package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ModuleRoleContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private final FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() throws ParseException {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new ArrayList<>(Arrays.asList(
                        new NameContainsKeywordsPredicate(List.of("Alice")),
                        new ModuleRoleContainsKeywordsPredicate(ParserUtil.parseModuleRolePairs(
                                List.of("CS2103T")))
                )));
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice " + PREFIX_MODULE + "CS2103T", expectedFindCommand);

        // Test with keywords and multiple whitespaces between keywords
        assertParseSuccess(parser, " \n " + PREFIX_NAME + "Alice \n \t " + PREFIX_MODULE + "CS2103T  \t",
                expectedFindCommand);
    }

}

