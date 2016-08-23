package com.simplemain.kit.json.error;

public class SyntaxError extends SyntaxException
{
	private static final long serialVersionUID = -4674194378872598782L;
	
	public SyntaxError(String msg)
	{
		super(msg, TYPE_ERROR);
	}

	public SyntaxError(int lineNo, int rowNo, String nearByString, String msg)
	{
		super(lineNo, rowNo, nearByString, msg, TYPE_ERROR);
	}
}
