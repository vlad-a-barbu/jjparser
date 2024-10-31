package json.parsers;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.Callable;

import json.tokens.JArray;
import json.tokens.JToken;
import readers.JReader;

final class JArrayParser extends JBaseParser {

    private final Callable<Optional<JToken>> parse;

    private int state = 0;
    private ArrayList<JToken> accumulator = new ArrayList<>();

    public JArrayParser(JReader reader, Callable<Optional<JToken>> parse) {
        super(reader, true);
        this.parse = parse;
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
        Optional<JToken> result;
        try {
            result = this.parse.call();
        } catch (Exception _) {
            result = Optional.empty();
        }
        if (this.state == 1) {
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
        if (length == 0)
            return Optional.empty();
        return Optional.of(new JArray(position, length, this.accumulator));
    }

}
