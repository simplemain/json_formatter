package com.simplemain.kit.json.error;

public class SyntaxWarning extends SyntaxException
{
	private static final long serialVersionUID = -4674194398872598782L;
	
	public SyntaxWarning(String msg)
	{
		super(msg, TYPE_WARN);
	}

	public SyntaxWarning(int lineNo, int rowNo, String nearByString, String msg)
	{
		super(lineNo, rowNo, nearByString, msg, TYPE_WARN);
	}
}
