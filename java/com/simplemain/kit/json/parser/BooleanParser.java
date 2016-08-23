package com.simplemain.kit.json.parser;

import com.simplemain.kit.json.element.JsonBoolean;
import com.simplemain.kit.json.element.JsonElement;

/**
 * @author zgwangbo@simplemain.com
 */
public class BooleanParser implements ElementParser
{
	@Override
	public JsonElement parse(Symbol symbol)
	{
		symbol.mark();
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < JsonBoolean.TRUE.length() && symbol.hasNext(); i++)
		{
			char ch = i == 0 ? symbol.nextWithoutSpace() : symbol.next();
			sb.append(ch);
		}
		String s = sb.toString();
		if (JsonBoolean.TRUE.compareToIgnoreCase(s) == 0)
		{
			return JsonBoolean.createTrue();
		}
		
		sb.append(symbol.next());
		s = sb.toString();
		if (JsonBoolean.FALSE.compareToIgnoreCase(s) == 0)
		{
			return JsonBoolean.createFalse();
		}
		
		symbol.reset();
		
		return null;
	}

}
