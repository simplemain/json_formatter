package com.simplemain.kit.json.element;

import java.util.Iterator;

public class JsonNumber extends JsonElement
{
	private String string;
	
	public JsonNumber(String s)
	{
		string = s;
	}
	
	public String toString()
	{
		return string;
	}
	
	@Override
	public Iterator<Token> iterator()
	{
		return new SimpleIterator(new Token(string, Token.TYPE_NUMBER, level));
	}
}
