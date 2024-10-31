package json.tokens;

public record JInt(int position, int length, int value) implements JToken {
}
