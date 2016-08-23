package com.simplemain.kit.json.element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.simplemain.kit.json.error.SyntaxException;

public abstract class JsonElement implements Iterable<Token>
{
	protected int level;
	protected List<SyntaxException> exceptions = new ArrayList<>();
	
	public abstract Iterator<Token> iterator();
	
	protected class SimpleIterator implements Iterator<Token>
	{
		private Token token;
		
		SimpleIterator(Token token)
		{
			this.token = token;
			if (token != null)
			{
				token.exceptions = exceptions;
			}
		}

		@Override
		public boolean hasNext()
		{
			return token != null;
		}

		@Override
		public Token next()
		{
			Token ret = token;
			token = null;
			return ret;
		}
	}

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}
	
	public void addException(SyntaxException e)
	{
		exceptions.add(e);
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
