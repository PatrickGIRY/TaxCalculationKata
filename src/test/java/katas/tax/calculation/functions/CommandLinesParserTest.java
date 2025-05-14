package katas.tax.calculation.functions;

import katas.tax.calculation.domain.CommandLine;
import katas.tax.calculation.domain.Product;
import katas.tax.calculation.domain.Quatity;
import katas.tax.calculation.domain.UnitPriceExcludingTax;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommandLinesParserTest {

    private CommandLinesParser commandLinesParser;

    @BeforeEach
    void setUp() {
        commandLinesParser = new CommandLinesParser();
    }

    @Test
    void should_parse_a_command_line() {

        final var input = "1 flacon de parfum importé à 27.99";

        List<CommandLine> commandLines = commandLinesParser.parse(input);

        assertThat(commandLines).containsExactly(
          new CommandLine(new Quatity(1), new Product("flacon de parfum importé"), new UnitPriceExcludingTax("27.99"))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            " 1 flacon de parfum importé à 27.99",
            "1 flacon de parfum importé à 27.99 "
    })
    void should_parse_a_command_line_ignoring_spaces_at_beginning_and_at_end(String input) {

        List<CommandLine> commandLines = commandLinesParser.parse(input);

        assertThat(commandLines).containsExactly(
          new CommandLine(new Quatity(1), new Product("flacon de parfum importé"), new UnitPriceExcludingTax("27.99"))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "flacon de parfum importé à 27.99",
            "flacon de parfum importé 27.99",
            "1 flacon de parfum importé 27.99",
            "1 flacon de parfum importé à"
    })
    void fail_on_invalid_input_line(String invalidInput) {

        assertThatThrownBy(() -> commandLinesParser.parse(invalidInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid input line [ %s ]", invalidInput);
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    void fail_on_invalid_input(String invalidInput) {

        assertThatThrownBy(() -> commandLinesParser.parse(invalidInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid input [ %s ]", invalidInput);
    }
}