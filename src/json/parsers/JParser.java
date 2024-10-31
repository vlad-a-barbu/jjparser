package json.parsers;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import json.tokens.JToken;
import readers.JReader;
import readers.exceptions.JStreamExhaustedException;

public class JParser {

    private static final List<Function<JReader, JBaseParser>> parserBuilders = List.of(
            reader -> new JWhitespaceParser(reader),
            reader -> new JArrayParser(reader, () -> parse(reader)),
            reader -> new JStringParser(reader),
            reader -> new JIntParser(reader));

    public static Optional<JToken> parse(JReader reader) throws JStreamExhaustedException {
        for (var builder : parserBuilders) {
            var parser = builder.apply(reader);
            var result = parser.parse();
            if (result.isPresent())
                return result;
        }
        return Optional.empty();
    }

}
