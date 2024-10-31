package json.tokens;

public record JString(int position, int length, String value) implements JToken {
}