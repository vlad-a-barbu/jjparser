package json.tokens;

public sealed interface JToken permits JInt, JString, JArray {

    int position();

    int length();

}
