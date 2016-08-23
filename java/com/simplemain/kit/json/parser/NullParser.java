package com.simplemain.kit.json.parser;

import com.simplemain.kit.json.element.JsonElement;
import com.simplemain.kit.json.element.JsonNull;

/**
 * @author zgwangbo@simplemain.com
 */
public class NullParser implements ElementParser
{
	@Override
	public JsonElement parse(Symbol symbol)
	{
		symbol.mark();
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < JsonNull.NULL.length() && symbol.hasNext(); i++)
		{
			char ch = i == 0 ? symbol.nextWithoutSpace() : symbol.next();
			sb.append(ch);
		}
		
		String s = sb.toString();
		if (JsonNull.NULL.compareToIgnoreCase(s) == 0)
		{
			return new JsonNull();
		}
		
		symbol.reset();
		
		return null;
	}

}
