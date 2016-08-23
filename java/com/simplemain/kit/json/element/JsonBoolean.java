package com.simplemain.kit.json.element;

import java.util.Iterator;

/**
 * @author zgwangbo@simplemain.com
 */
public class JsonBoolean extends JsonElement
{
	public static final String TRUE  = "true";
	public static final String FALSE = "false";
	
	private final String string;
	
	public static JsonBoolean createTrue()
	{
		return new JsonBoolean("true");
	}
	
	public static JsonBoolean createFalse()
	{
		return new JsonBoolean("false");
	}
	
	private JsonBoolean(String s)
	{
		this.string = s;
	}
	
	public String toString()
	{
		return string;
	}

	@Override
	public Iterator<Token> iterator()
	{
		return new SimpleIterator(new Token(string, Token.TYPE_KEYWORD, level));
	}
}
