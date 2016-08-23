package com.simplemain.kit.json.element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author zgwangbo@simplemain.com
 */
public class JsonObject extends JsonElement
{
	public static final char BEGIN_SYMBOL   = '{';
	public static final char END_SYMBOL     = '}';
	public static final char KV_SEPARATOR   = ':';
	public static final char NEXT_SEPARATOR = ',';
	
	private static class Pair
	{
		JsonString key;
		String sep;
		JsonElement value;
		
		Pair(JsonString key, String sep, JsonElement value)
		{
			this.key   = key;
			this.sep   = sep;
			this.value = value;
		}
	}
	
	private List<Pair> list = new ArrayList<>();
	
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
	
	public void add(JsonString key, String sep, JsonElement value) 
	{
		list.add(new Pair(key, sep, value));
	}
	
	public void add(IllegalElement ie) 
	{
		list.add(new Pair(null, null, ie));
	}
	
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		boolean first = true;
		for (Pair p : list)
		{
			if (!first)
			{
				sb.append(NEXT_SEPARATOR + " ");
			}
			else
			{
				first = false;
			}
			sb.append(p.key + " " + KV_SEPARATOR + " " + p.value);
		}
		return beginSymbol + sb.toString() + endSymbol;
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
			iters.add(new SimpleIterator(new Token("" + beginSymbol, Token.TYPE_AREA_SEPARATOR, level)));
			
			boolean first = true;
			for (Pair p : list)
			{
				if (!first)
				{
					if (p.key != null && p.value != null)
					{
						iters.add(new SimpleIterator(new Token("" + NEXT_SEPARATOR, Token.TYPE_SEG_SEPARATOR, level + 1)));
					}
				}
				else
				{
					first = false;
				}
				
				if (p.key != null)
				{
					p.key.setLevel(level + 1);
					iters.add(p.key.iterator());
				}
				
				if (p.sep != null)
				{
					iters.add(new SimpleIterator(new Token(p.sep, Token.TYPE_KV_SEPARATOR, level + 1)));
				}
				
				if (p.value != null)
				{
					p.value.setLevel(level + 1);
					iters.add(p.value.iterator());
				}
			}
			
			iters.add(new SimpleIterator(new Token("" + endSymbol, Token.TYPE_AREA_SEPARATOR, level)));
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
