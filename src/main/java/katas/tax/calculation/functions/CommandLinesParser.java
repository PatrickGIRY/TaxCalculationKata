package katas.tax.calculation.functions;

import katas.tax.calculation.domain.CommandLine;
import katas.tax.calculation.domain.Product;
import katas.tax.calculation.domain.Quatity;
import katas.tax.calculation.domain.UnitPriceExcludingTax;

import java.util.List;
import java.util.regex.Pattern;

public class CommandLinesParser {
    private static final Pattern LINE_PATTERN = Pattern.compile("(\\d+)\\s+(.*?)\\s*à\\s*(\\d+(?:\\.\\d+)?)");

    public List<CommandLine> parse(String commandLines) {
        if (commandLines == null || commandLines.isEmpty()) {
            throw  new IllegalArgumentException(String.format("Invalid input [ %s ]", commandLines));
        }
        return commandLines.lines()
                .map(CommandLinesParser::parseLine)
                .toList();
    }

    private static CommandLine parseLine(String input) {
        final var matcher = LINE_PATTERN.matcher(input.trim());
        if (matcher.lookingAt() && matcher.groupCount() == 3) {
            return new CommandLine(
                    new Quatity(Integer.parseInt(matcher.group(1))),
                    new Product(matcher.group(2)),
                    new UnitPriceExcludingTax(matcher.group(3))
            );
        } else {
            throw new IllegalArgumentException(String.format("Invalid input line [ %s ]", input));
        }
    }
}
