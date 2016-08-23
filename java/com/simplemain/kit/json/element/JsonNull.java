package com.simplemain.kit.json.element;

import java.util.Iterator;

/**
 * @author zgwangbo@simplemain.com
 */
public class JsonNull extends JsonElement
{
	public static final String NULL = "null";
	
	public String toString()
	{
		return NULL;
	}
	
	@Override
	public Iterator<Token> iterator()
	{
		return new SimpleIterator(new Token(toString(), Token.TYPE_KEYWORD, level));
	}
}
