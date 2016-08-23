package com.simplemain.kit.json.element;

import java.util.Iterator;

public class JsonNull extends JsonElement
{
	public String toString()
	{
		return "null";
	}
	
	@Override
	public Iterator<Token> iterator()
	{
		return new SimpleIterator(new Token(toString(), Token.TYPE_KEYWORD, level));
	}
}
