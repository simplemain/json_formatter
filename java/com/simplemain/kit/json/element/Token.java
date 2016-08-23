package com.simplemain.kit.json.element;

import java.util.List;

import com.simplemain.kit.json.error.SyntaxException;

/**
 * @author zgwangbo@simplemain.com
 */
public class Token
{
	private static int idx = 0;
	
	public static final int TYPE_NORAML          = idx++;
	public static final int TYPE_KEYWORD         = idx++;
	public static final int TYPE_AREA_SEPARATOR  = idx++;
	public static final int TYPE_KV_SEPARATOR    = idx++;
	public static final int TYPE_SEG_SEPARATOR   = idx++;
	public static final int TYPE_NUMBER          = idx++;
	public static final int TYPE_STRING          = idx++;
	
	public static final int TYPE_ILLEGAL         = idx++;
	
	private String value;
	private int type;
	private int level;
	List<SyntaxException> exceptions;
	
	public Token() {}
	
	public Token(String value, int type, int level)
	{
		this.value = value;
		this.type  = type;
		this.level = level;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}
	
	public List<SyntaxException> getExceptions()
	{
		return exceptions;
	}
	
	public boolean hasExceptions()
	{
		return !exceptions.isEmpty();
	}
}
