package com.simplemain.kit.json.format;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.simplemain.kit.json.element.JsonElement;
import com.simplemain.kit.json.element.Token;
import com.simplemain.kit.json.error.SyntaxException;

public class MetaFormatter implements JsonFormatter
{
	private List<Line> lines = new ArrayList<>();
	private List<SyntaxException> exceptions = new ArrayList<>();
	
	@Override
	public void format(JsonElement element, PrintWriter pw)
	{
		if (element == null)
		{
			return;
		}
		
		Line currentLine = newLine();
		
		int lastLevel = 0;
		
		for (Token token : element)
		{
			int level = token.getLevel();
			
			if (level != lastLevel)
			{
				currentLine = newLine();
				lastLevel = level;
				
				currentLine.setTabCount(level);
				currentLine.getTokens().add(token);
			}
			else if (token.getType() == Token.TYPE_SEG_SEPARATOR)
			{
				currentLine.getTokens().add(token);
				currentLine = newLine();
				currentLine.setTabCount(level);
			}
			else
			{
				currentLine.getTokens().add(token);
			}
			
			if (token.hasExceptions())
			{
				for (SyntaxException e : token.getExceptions())
				{
					if (!currentLine.getExceptions().contains(e))
					{
						currentLine.getExceptions().add(e);
					}
					
					if (!exceptions.contains(e))
					{
						exceptions.add(e);
					}
				}
			}
		}
		
	}

	private Line newLine()
	{
		final Line line = new Line();
		lines.add(line);
		return line;
	}
	
	public static class Line
	{
		private int tabCount = 0;
		private List<SyntaxException> exceptions = new ArrayList<>();
		private List<Token> tokens = new ArrayList<>();
		
		public int getTabCount()
		{
			return tabCount;
		}
		public void setTabCount(int tabCount)
		{
			this.tabCount = tabCount;
		}
		public List<Token> getTokens()
		{
			return tokens;
		}
		public List<SyntaxException> getExceptions()
		{
			return exceptions;
		}
	}

	public List<Line> getLines()
	{
		return lines;
	}

	public List<SyntaxException> getExceptions()
	{
		return exceptions;
	}
	
}
