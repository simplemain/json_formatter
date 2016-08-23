package com.simplemain.kit.json.error;

public abstract class SyntaxException extends RuntimeException
{
	private static final long serialVersionUID = 1230377296133173902L;

	protected static final String TYPE_WARN  = "warning";
	protected static final String TYPE_ERROR = "error";
	
	protected int lineNo;
	protected int rowNo;
	protected String nearByString;
	protected String type;
	
	public SyntaxException(String msg, String type)
	{
		this(0, 0, null, msg, type);
	}

	public SyntaxException(int lineNo, int rowNo, String nearByString, String msg, String type)
	{
		super(msg);
		this.lineNo = lineNo;
		this.rowNo = rowNo;
		this.nearByString = nearByString;
		this.type = type;
	}

	public int getLineNo()
	{
		return lineNo;
	}

	public int getRowNo()
	{
		return rowNo;
	}
	
	public String toString()
	{
		return String.format("syntax %s at [%d:%d] near [%s] caused by [%s]", 
				type, lineNo, rowNo, nearByString, super.getMessage());
	}
}
