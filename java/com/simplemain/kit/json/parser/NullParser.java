package com.simplemain.kit.json.parser;

import com.simplemain.kit.json.element.JsonElement;
import com.simplemain.kit.json.element.JsonNull;

/**
 * @author zgwangbo@simplemain.com
 */
public class NullParser implements ElementParser
{
	private static final String NULL  = "null";
	
	@Override
	public JsonElement parse(Symbol symbol)
	{
		symbol.mark();
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < NULL.length() && symbol.hasNext(); i++)
		{
			char ch = i == 0 ? symbol.nextWithoutSpace() : symbol.next();
			sb.append(ch);
		}
		
		String s = sb.toString();
		if (NULL.compareToIgnoreCase(s) == 0)
		{
			return new JsonNull();
		}
		
		symbol.reset();
		
		return null;
	}

}
