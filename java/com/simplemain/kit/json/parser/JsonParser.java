package com.simplemain.kit.json.parser;

import com.simplemain.kit.json.element.IllegalElement;
import com.simplemain.kit.json.element.JsonElement;
import com.simplemain.kit.json.element.Token;
import com.simplemain.kit.json.error.SyntaxError;

public class JsonParser
{
	private static final ElementParser[] PARSERS = {new ObjectParser(), new ArrayParser()};
	
	public JsonElement parse(String string)
	{
		return parse(string, false);
	}
	
	public JsonElement parse(String string, boolean throwsExceptions)
	{
		if (string == null || string.trim().equals(""))
		{
			return null;
		}
		
		final Symbol symbol = new Symbol(string);
		
		for (ElementParser parser : PARSERS)
		{
			final JsonElement je = parser.parse(symbol);
			if (je != null)
			{
				if (throwsExceptions)
				{
					for (Token token : je)
					{
						if (token.hasExceptions())
						{
							throw token.getExceptions().get(0);
						}
					}
				}
				
				return je;
			}
		}
		
		SyntaxError error = new SyntaxError(1, 1, string, "parse error");
		if (throwsExceptions)
		{
			throw error;
		}
		
		String remaining = symbol.getRemaining();
		IllegalElement ie = new IllegalElement(remaining, error);
		
		return ie;
	}
}
