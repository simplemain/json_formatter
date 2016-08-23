package com.simplemain.kit.json.element;

import java.util.Iterator;

/**
 * @author zgwangbo@simplemain.com
 */
public class JsonString extends JsonElement
{
	public static final char SIGN          = '"';
	public static final char POSSIBLE_SIGN = '\'';
	
	private String string;
	
	private String beginSymbol = "";
	private String endSymbol = "";
	
	public JsonString()
	{
	}
	
	public JsonString(String s)
	{
		string = s;
	}
	
	public void fillBeginSymbol()
	{
		beginSymbol = "" + SIGN;
	}
	
	public void fillEndSymbol()
	{
		endSymbol = "" + SIGN;
	}
	
	public String toString()
	{
		return beginSymbol + string + endSymbol;
	}
	
	@Override
	public Iterator<Token> iterator()
	{
		return new SimpleIterator(new Token(toString(), Token.TYPE_STRING, level));
	}

	public String getString()
	{
		return string;
	}

	public void setString(String string)
	{
		this.string = string;
	}
}
