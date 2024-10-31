package json.parsers;

import java.util.Optional;

import json.tokens.JToken;
import readers.JReader;
import readers.exceptions.JStreamExhaustedException;

abstract class JBaseParser {

    private final JReader reader;

    private final boolean rewindOnError;

    JBaseParser(JReader reader, boolean rewindOnError) {
        this.reader = reader;
        this.rewindOnError = rewindOnError;
    }

    Optional<JToken> parse() throws JStreamExhaustedException {
        var startPosition = this.reader.position();
        var length = 0;

        while (true) {
            var position = this.reader.position();
            var charOption = this.readNext();
            if (charOption.isEmpty())
                break;
            if (!this.accumulate(charOption.get())) {
                this.reader.seek(position);
                break;
            }
            length += 1;
        }

        var accepted = this.accept(startPosition, length);
        if (accepted.isEmpty() && this.rewindOnError) {
            this.reader.seek(startPosition);
        }

        return accepted;
    }

    abstract boolean accumulate(char c);

    abstract Optional<JToken> accept(int position, int length);

    private Optional<Character> readNext() {
        try {
            return Optional.of(this.reader.read());
        } catch (JStreamExhaustedException _) {
            return Optional.empty();
        }
    }

}
