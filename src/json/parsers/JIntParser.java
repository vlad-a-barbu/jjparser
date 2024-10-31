package json.parsers;

import java.util.Optional;

import json.tokens.JInt;
import json.tokens.JToken;
import readers.JReader;

final class JIntParser extends JBaseParser {

    private int accumulator = 0;

    public JIntParser(JReader reader) {
        super(reader, true);
    }

    @Override
    boolean accumulate(char c) {
        if (!Character.isDigit(c))
            return false;
        this.accumulator = this.accumulator * 10 + (c - '0');
        return true;
    }

    @Override
    Optional<JToken> accept(int position, int length) {
        if (length == 0)
            return Optional.empty();
        return Optional.of(new JInt(position, length, this.accumulator));
    }

}
