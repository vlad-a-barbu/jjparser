package json.parsers;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.Callable;

import json.tokens.JArray;
import json.tokens.JToken;
import readers.JReader;

final class JArrayParser extends JBaseParser {

    private final Callable<Optional<JToken>> parseNext;

    /*
     * state = 0 -> wait for acceptor (read '[' or ',')
     * state = 1 -> accept next element (call to parse)
     * state = 2 -> end (read ']')
     */
    private int state = 0;
    private ArrayList<JToken> accumulator = new ArrayList<>();

    public JArrayParser(JReader reader, Callable<Optional<JToken>> parseNext) {
        super(reader, true);
        this.parseNext = parseNext;
    }

    @Override
    boolean accumulate(char c) {
        if (this.state == 2)
            return false;

        if (this.state == 0 && (c == '[' || c == ',')) {
            this.state = 1;
            return true;
        }

        if (c == ']') {
            this.state = 2;
            return true;
        }

        if (this.state == 1) {
            Optional<JToken> result;
            try {
                result = this.parseNext.call();
            } catch (Exception _) {
                result = Optional.empty();
            }
            if (result.isEmpty())
                return false;
            this.accumulator.add(result.get());
            this.state = 0;
            return true;
        }

        return false;
    }

    @Override
    Optional<JToken> accept(int position, int length) {
        if (length == 0 || this.state != 2)
            return Optional.empty();
        return Optional.of(new JArray(position, length, this.accumulator));
    }

}
