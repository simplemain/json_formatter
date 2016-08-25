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
		
		char ch = 0;

		symbol.mark();
		
		ch = symbol.nextWithoutSpace();
		
		char END = JsonString.SIGN;
		if (JsonString.SIGN == ch)
		{
			
		}
		else if (JsonString.POSSIBLE_SIGN == ch)
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
			ch = symbol.next();
			if (END == ch)
			{
				ret.setString(sb.toString());
				ret.fillEndSymbol();
				return ret;
			}
			else if ('\\' == ch)
			{
				// check : " \ / b f n r t uxxxx
//				sb.append(next);
				char next = symbol.next();
				
				if (next != 'u')
				{
					sb.append(ch);
					sb.append(next);
				}
				
				if (next == '"' || next == '\\' || next == 'b' || next == 'f' || 
						next == 'n' || next == 'r' || next == 't')
				{
					// permit
				}
				else if (next == 'u')
				{
					int num = 0;
					boolean hasError = false;
					String tmp = "" + ch + next;
					for (int i = 0; i < 4; i++)
					{
						next = symbol.next();
						next = Character.toLowerCase(next);
						tmp += next;
						
						if (next >= '0' && next <= '9' ||
								next >= 'a' && next <= 'f')
						{
							num *= 16;
							num += Integer.valueOf("" + next, 16);
						}
						else // error
						{
							SyntaxWarning warn = new SyntaxWarning(symbol.getLineNo(), symbol.getRowNo(), 
									symbol.getNearByChars(), "in STRING, only [0-9a-fA-F] are permitted after \\u");
							
							ret.addException(warn);
							hasError = true;
						}
					}
					
					if (!hasError)
					{
						char c = (char)num;
						sb.append(c);
					}
					else
					{
						sb.append(tmp);
					}
				}
				else
				{
					SyntaxWarning warn = new SyntaxWarning(symbol.getLineNo(), symbol.getRowNo(), 
							symbol.getNearByChars(), "in STRING, only [\\/bfnrtu] are permitted after \\");
					
					ret.addException(warn);
				}
			}
			else
			{
				sb.append(ch);
			}
		}	
		
		ret.setString(sb.toString());
		SyntaxError error = new SyntaxError(symbol.getLineNo(), symbol.getRowNo(), 
				symbol.getNearByChars(), "in STRING, a quote(\") is need at the end");
		
		ret.addException(error);
		return ret;
	}

}
