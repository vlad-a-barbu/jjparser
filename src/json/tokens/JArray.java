package json.tokens;

import java.util.ArrayList;

public record JArray(int position, int length, ArrayList<JToken> value) implements JToken {
}
