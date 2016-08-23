package com.simplemain.kit.json.element;

import java.util.Iterator;

import com.simplemain.kit.json.error.SyntaxException;

/**
 * @author zgwangbo@simplemain.com
 */
public class IllegalElement extends JsonElement
{
	private String string;
	
	public IllegalElement(String s, SyntaxException e)
	{
		string = s;
		addException(e);
	}
	
	public String toString()
	{
		return string;
	}
	
	@Override
	public Iterator<Token> iterator()
	{
		return new SimpleIterator(new Token(toString(), Token.TYPE_STRING, level));
	}
}
