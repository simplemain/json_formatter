package com.simplemain.kit.json.parser;

import com.simplemain.kit.json.element.JsonString;
import com.simplemain.kit.json.error.SyntaxError;
import com.simplemain.kit.json.error.SyntaxWarning;

/**
 * @author zgwangbo@simplemain.com
 */
public class StringParser implements ElementParser
{
	@Override
	public JsonString parse(Symbol symbol)
	{
		final JsonString ret = new JsonString();
		
		char next = 0;

		symbol.mark();
		
		next = symbol.nextWithoutSpace();
		
		char END = JsonString.SIGN;
		if (JsonString.SIGN == next)
		{
			
		}
		else if (JsonString.POSSIBLE_SIGN == next)
		{
			SyntaxWarning warn = new SyntaxWarning(symbol.getLineNo(), symbol.getRowNo(), 
					symbol.getNearByChars(), "in STRING, \"'\" is NOT permitted");
			
			ret.addException(warn);
			
			END = JsonString.POSSIBLE_SIGN;
		}
		else
		{
			symbol.reset();
			return null;
		}
		ret.fillBeginSymbol();
		
		
		StringBuffer sb = new StringBuffer();
		while (symbol.hasNext())
		{
			next = symbol.next();
			if (END == next)
			{
				ret.setString(sb.toString());
				ret.fillEndSymbol();
				return ret;
			}
			else if ('\\' == next)
			{
				// check : " \ / b f n r t uxxxx
				sb.append(next);
				next = symbol.next();
				if (next == '"' || next == '\\' || next == 'b' || next == 'f' || 
						next == 'n' || next == 'r' || next == 't')
				{
					sb.append(next);
				}
				else if (next == 'u')
				{
					sb.append(next);
					for (int i = 0; i < 4; i++)
					{
						next = symbol.next();
						next = Character.toLowerCase(next);
						if (next >= '0' && next <= '9' ||
								next >= 'a' && next <= 'f')
						{
							sb.append(next);
						}
						else // error
						{
							SyntaxWarning warn = new SyntaxWarning(symbol.getLineNo(), symbol.getRowNo(), 
									symbol.getNearByChars(), "in STRING, only [0-9a-fA-F] are permitted after \\u");
							
							ret.addException(warn);
//							return null;
						}
					}
				}
			}
			else
			{
				sb.append(next);
			}
		}
		
		
		ret.setString(sb.toString());
		SyntaxError error = new SyntaxError(symbol.getLineNo(), symbol.getRowNo(), 
				symbol.getNearByChars(), "in STRING, a quote(\") is need at the end");
		
		ret.addException(error);
		return ret;
	}

}
