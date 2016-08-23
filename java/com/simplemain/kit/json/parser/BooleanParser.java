package com.simplemain.kit.json.parser;

import com.simplemain.kit.json.element.JsonBoolean;
import com.simplemain.kit.json.element.JsonElement;

public class BooleanParser implements ElementParser
{
	private static final String TRUE  = "true";
	private static final String FALSE = "false";
	
	@Override
	public JsonElement parse(Symbol symbol)
	{
		symbol.mark();
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < TRUE.length() && symbol.hasNext(); i++)
		{
			char ch = i == 0 ? symbol.nextWithoutSpace() : symbol.next();
			sb.append(ch);
		}
		String s = sb.toString();
		if (TRUE.compareToIgnoreCase(s) == 0)
		{
			return JsonBoolean.TRUE;
		}
		
		sb.append(symbol.next());
		s = sb.toString();
		if (FALSE.compareToIgnoreCase(s) == 0)
		{
			return JsonBoolean.FALSE;
		}
		
		symbol.reset();
		
		return null;
	}

}
