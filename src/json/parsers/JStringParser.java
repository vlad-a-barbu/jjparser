package json.parsers;

import java.util.Optional;

import json.tokens.JString;
import json.tokens.JToken;
import readers.JReader;

final class JStringParser extends JBaseParser {

    private int numQuotes = 0;
    private StringBuilder accumulator = new StringBuilder();

    public JStringParser(JReader reader) {
        super(reader, true);
    }

    @Override
    boolean accumulate(char c) {
        if (numQuotes == 2)
            return false;
        if (c == '"')
            return ++numQuotes <= 2;
        if (numQuotes == 0)
            return false;
        accumulator.append(c);
        return true;
    }

    @Override
    Optional<JToken> accept(int position, int length) {
        if (length == 0 || this.numQuotes != 2)
            return Optional.empty();
        return Optional.of(new JString(position, length, this.accumulator.toString()));
    }

}
