package com.simplemain.kit.json.parser;

import com.simplemain.kit.json.element.JsonElement;

public class ValueParser implements ElementParser
{
	private static final ElementParser[] PARSERS = 
	{
		new StringParser(), 
		new NumberParser(),
		new BooleanParser(),
		new NullParser(),
		new ObjectParser(),
		new ArrayParser(),
	};
	
	@Override
	public JsonElement parse(Symbol symbol)
	{
		for (ElementParser parser : PARSERS)
		{
			JsonElement je = parser.parse(symbol);
			if (je != null)
			{
				return je;
			}
		}
		return null;
	}

}
