package com.simplemain.kit.json.parser;

import java.nio.CharBuffer;

public class Symbol
{
	private static final int NEAR_BY_MAX_LEN = 10;
	
	private final String string;
	private final CharBuffer buffer;
	
	private int lineNo = 1;
	private int rowNo = 0;
	private int position = 0;
	
	private int markedLineNo = 0;
	private int markedRowNo = 0;
	
	public Symbol(String string)
	{
		this.string = string;
		this.buffer = CharBuffer.wrap(this.string);
	}
	
	public void mark()
	{
		buffer.mark();
		markedLineNo = lineNo;
		markedRowNo  = rowNo;
	}
	
	public void reset()
	{
		buffer.reset();
		
		lineNo = markedLineNo;
		rowNo  = markedRowNo;
		position = buffer.position();
	}
	
	public boolean hasNext()
	{
		return buffer.hasRemaining();
	}
	
	private boolean isNewLine(char ch)
	{
		return ch == '\n';
	}
	
	public char next()
	{
		char ret = buffer.hasRemaining() ? buffer.get() : 0;
		if (isNewLine(ret))
		{
			lineNo++;
			rowNo = 0;
		}
		else
		{
			rowNo++;
		}
		position = buffer.position();

		return ret;
	}
	
	public String getRemaining()
	{
		StringBuffer sb = new StringBuffer();
		while (hasNext())
		{
			sb.append(next());
		}
		return sb.toString();
	}
	
	public char nextWithoutSpace()
	{
		while (true)
		{
			if (!buffer.hasRemaining())
			{
				return 0;
			}
			char ch = buffer.get();
			
			if (isNewLine(ch))
			{
				lineNo++;
				rowNo = 0;
			}
			else
			{
				rowNo++;
			}
			position = buffer.position();
			
			if (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\n')
			{
				continue;
			}

			return ch;
		}
	}

	public int getLineNo()
	{
		return lineNo;
	}

	public int getRowNo()
	{
		return rowNo;
	}
	
	public String getNearByChars()
	{
		final int start = Math.max(position - NEAR_BY_MAX_LEN, 0);
		final int end   = Math.min(position + NEAR_BY_MAX_LEN, string.length());
		
		if (start >= end)
		{
			return null;
		}
		
		return string.substring(start, end);
	}
}
