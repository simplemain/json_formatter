package com.simplemain.kit.json.element;

import java.util.Iterator;

public class JsonBoolean extends JsonElement
{
	public static final JsonBoolean TRUE  = new JsonBoolean("true");
	public static final JsonBoolean FALSE = new JsonBoolean("false");
	
	private final String string;
	
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
