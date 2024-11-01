import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import json.parsers.JParser;
import json.tokens.JArray;
import json.tokens.JInt;
import json.tokens.JString;
import json.tokens.JToken;
import readers.JFileReader;
import readers.exceptions.JStreamExhaustedException;

class Main {

	public static void main(String[] args) throws JStreamExhaustedException, FileNotFoundException, IOException {

		if (args.length < 1)
			throw new IllegalArgumentException("invalid args: pass path to json file");

		try (var reader = new JFileReader(args[0])) {
			var option = JParser.parse(reader);
			if (option.isEmpty()) {
				System.out.println("invalid json");
				return;
			}
			var result = option.get();
			var message = switch (result) {
				case JInt(int _, int _, int value) -> value;
				case JString(int _, int _, String value) -> "<<<" + value + ">>>";
				case JArray(int _, int _, ArrayList<JToken> value) -> value;
			};
			System.out.println("parsed value: " + message);
		}

	}

}
