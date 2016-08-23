package com.simplemain.kit.json.element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonArray extends JsonElement
{
	public static final char BEGIN_SYMBOL   = '[';
	public static final char END_SYMBOL     = ']';
	public static final char NEXT_SEPARATOR = ',';

	private List<JsonElement> elements = new ArrayList<>();
	
	private String beginSymbol = "";
	private String endSymbol = "";
	
	public void fillBeginSymbol()
	{
		beginSymbol = "" + BEGIN_SYMBOL;
	}
	
	public void fillEndSymbol()
	{
		endSymbol = "" + END_SYMBOL;
	}
	
	public void add(JsonElement e)
	{
		elements.add(e);
	}
	
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(beginSymbol);
		
		boolean first = true;
		for (JsonElement e : elements)
		{
			if (!first) sb.append(NEXT_SEPARATOR + " ");
			else first = false;
			
			sb.append(e.toString());
		}
		sb.append(endSymbol);
		
		return sb.toString();
	}

	@Override
	public Iterator<Token> iterator()
	{
		return new DefaultIterator();
	}
	
	private class DefaultIterator implements Iterator<Token>
	{
		protected List<Iterator<Token>> iters = new ArrayList<>();
		protected int elementIdx = 0;
		
		DefaultIterator()
		{
			iters.add(new SimpleIterator(new Token(beginSymbol, Token.TYPE_AREA_SEPARATOR, level)));
			
			boolean first = true;
			for (JsonElement e : elements)
			{
				if (!first)
				{
					iters.add(new SimpleIterator(new Token("" + NEXT_SEPARATOR, Token.TYPE_SEG_SEPARATOR, level + 1)));
				}
				else
				{
					first = false;
				}
				
				e.setLevel(level + 1);
				iters.add(e.iterator());
			}
			
			iters.add(new SimpleIterator(new Token(endSymbol, Token.TYPE_AREA_SEPARATOR, level)));
		}
		
		@Override
		public boolean hasNext()
		{
			while (elementIdx < iters.size())
			{
				if (iters.get(elementIdx).hasNext())
				{
					return true;
				}
				elementIdx++;
			}
			
			return false;
		}

		@Override
		public Token next()
		{
			while (elementIdx < iters.size())
			{
				Token t = iters.get(elementIdx).next();
				if (t != null)
				{
					return t;
				}
				elementIdx++;
			}
			
			return null;
		}
	}
}
