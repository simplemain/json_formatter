package com.simplemain.kit.json.parser;

import com.simplemain.kit.json.element.IllegalElement;
import com.simplemain.kit.json.element.JsonElement;
import com.simplemain.kit.json.element.JsonObject;
import com.simplemain.kit.json.element.JsonString;
import com.simplemain.kit.json.error.SyntaxError;

public class ObjectParser implements ElementParser
{
	@Override
	public JsonObject parse(Symbol symbol)
	{
		symbol.mark();
		
		final JsonObject ret = new JsonObject();
		
		final char begin = symbol.nextWithoutSpace();
		if (JsonObject.BEGIN_SYMBOL != begin)
		{
			symbol.reset();
			return null;
		}
		ret.fillBeginSymbol();
		
		for (boolean force = false; symbol.hasNext(); force = true)
		{
			symbol.mark();
			
			final StringParser sp = new StringParser();
			final JsonString key = sp.parse(symbol);
			if (key == null)
			{
				if (force) // error
				{
					SyntaxError error = new SyntaxError(symbol.getLineNo(), symbol.getRowNo(), 
							symbol.getNearByChars(), "in OBJECT, a STRING key is needed");
					
					String remaining = symbol.getRemaining();
					IllegalElement ie = new IllegalElement(remaining, error);
					ret.add(ie);
					return ret;
				}
				else
				{
					symbol.reset();
					break;
				}
			}
			
			symbol.mark();
			char next = symbol.nextWithoutSpace();
			
			if (JsonObject.KV_SEPARATOR != next) // error
			{
				symbol.reset();
				
				SyntaxError error = new SyntaxError(symbol.getLineNo(), symbol.getRowNo(), 
						symbol.getNearByChars(), "in OBJECT, a colon(:) is needed after key");
				
				String remaining = symbol.getRemaining();
				IllegalElement ie = new IllegalElement(remaining, error);
				ret.add(key, null, ie);
				return ret;
			}
			
			final ValueParser vp    = new ValueParser();
			final JsonElement value = vp.parse(symbol);
			if (value == null) // error
			{
				SyntaxError error = new SyntaxError(symbol.getLineNo(), symbol.getRowNo(), 
						symbol.getNearByChars(), "in OBJECT, a valid value is needed after the colon");
				
				String remaining = symbol.getRemaining();
				IllegalElement ie = new IllegalElement(remaining, error);
				ret.add(key, "" + JsonObject.KV_SEPARATOR, ie);
				return ret;
			}

			ret.add(key, "" + JsonObject.KV_SEPARATOR, value);
			
			symbol.mark();
			next = symbol.nextWithoutSpace();
			
			if (JsonObject.NEXT_SEPARATOR != next)
			{
				symbol.reset();
				break;
			}
		}
		
		final char end = symbol.nextWithoutSpace();
		if (JsonObject.END_SYMBOL == end)
		{
			ret.fillEndSymbol();
			return ret;
		}
		else
		{
			symbol.reset();
			
			SyntaxError error = new SyntaxError(symbol.getLineNo(), symbol.getRowNo(), 
					symbol.getNearByChars(), "in OBJECT, a right-brace(}) is needed at the end");
			
			String remaining = symbol.getRemaining();
			IllegalElement ie = new IllegalElement(remaining, error);
			ret.add(ie);
			return ret;
		}
	}

}
