package com.simplemain.kit.json.parser;

import com.simplemain.kit.json.element.IllegalElement;
import com.simplemain.kit.json.element.JsonArray;
import com.simplemain.kit.json.element.JsonElement;
import com.simplemain.kit.json.error.SyntaxError;

/**
 * @author zgwangbo@simplemain.com
 */
public class ArrayParser implements ElementParser
{
	@Override
	public JsonArray parse(Symbol symbol)
	{
		symbol.mark();
		
		final JsonArray ret = new JsonArray();
		
		final char begin = symbol.nextWithoutSpace();
		if (JsonArray.BEGIN_SYMBOL != begin)
		{
			symbol.reset();
			return null;
		}
		ret.fillBeginSymbol();
		
		for (boolean force = false; symbol.hasNext(); force = true)
		{
			symbol.mark();
			
			final ValueParser vp    = new ValueParser();
			final JsonElement value = vp.parse(symbol);
			if (value == null)
			{
				if (force) // error
				{
					SyntaxError error = new SyntaxError(symbol.getLineNo(), 
							symbol.getRowNo(), 
							symbol.getNearByChars(), 
							"in ARRAY, a valid value is needed after comma(,)");
					
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

			ret.add(value);
			
			symbol.mark();
			char next = symbol.nextWithoutSpace();
			
			if (JsonArray.NEXT_SEPARATOR != next)
			{
				symbol.reset();
				break;
			}
		}
		
		final char end = symbol.nextWithoutSpace();
		if (JsonArray.END_SYMBOL == end)
		{
			ret.fillEndSymbol();
			return ret;
		}
		else
		{
			symbol.reset();
			
			SyntaxError error = new SyntaxError(symbol.getLineNo(), symbol.getRowNo(), 
					symbol.getNearByChars(), "in ARRAY, a right-bracket(]) is needed at the end");
			
			String remaining = symbol.getRemaining();
			IllegalElement ie = new IllegalElement(remaining, error);
			ret.add(ie);
			return ret;
		}
	}
}
