package json.parsers;

import java.util.Optional;

import json.tokens.JToken;
import readers.JReader;

final class JWhitespaceParser extends JBaseParser {

    public JWhitespaceParser(JReader reader) {
        super(reader, false);
    }

    @Override
    boolean accumulate(char c) {
        if (c == ' ' || c == '\t' || c == '\r' || c == '\n')
            return true;
        return false;
    }

    @Override
    Optional<JToken> accept(int position, int length) {
        return Optional.empty();
    }

}
