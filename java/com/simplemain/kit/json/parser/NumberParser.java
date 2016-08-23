package com.simplemain.kit.json.parser;

import com.simplemain.kit.json.element.IllegalElement;
import com.simplemain.kit.json.element.JsonElement;
import com.simplemain.kit.json.element.JsonNumber;
import com.simplemain.kit.json.error.SyntaxError;

public class NumberParser implements ElementParser
{

	@Override
	public JsonElement parse(Symbol symbol)
	{
		StringBuffer sb = new StringBuffer();
		
		// 符号
		final String sign = getSign(symbol);
		if (sign != null)
		{
			sb.append(sign);
		}
		boolean hasSign = sign != null;
		
		// 整数部分
		final String intPart = getIntegerPart(symbol, hasSign);
		if (intPart != null)
		{
			sb.append(intPart);
		}
		else
		{
			if (hasSign) // 出错
			{
				SyntaxError error = new SyntaxError(symbol.getLineNo(), symbol.getRowNo(), 
						symbol.getNearByChars(), "in NUMBER, expect DIGIT after minus(-)");
				
				IllegalElement ie = new IllegalElement(sb.toString(), error);
				return ie;
				
//				return null;
			}
			else
			{
				symbol.reset();
				return null;
			}
		}
		
		// 小数部分
		final String decimalPart = getDecimalPart(symbol);
		if (decimalPart != null)
		{
			sb.append(decimalPart);
		}
		
		// 指数部分
		final String exponentPart = getExponentPart(symbol);
		if (exponentPart != null)
		{
			sb.append(exponentPart);
		}
		
		if (sb.length() == 0)
		{
			return null;
		}
		
		JsonNumber ret = new JsonNumber(sb.toString());
		
		return ret;
	}
	
	private String getExponentPart(Symbol symbol)
	{
		StringBuffer sb = new StringBuffer();
		
		symbol.mark();
		char ch = symbol.next();
		
		if ('e' == Character.toLowerCase(ch))
		{
			sb.append(ch);
			symbol.mark();
			ch = symbol.next();
			if ('+' == ch || '-' == ch)
			{
				sb.append(ch);
			}
			else
			{
				symbol.reset();
			}
			
			ch = symbol.next();
			if (!Character.isDigit(ch))
			{
				throw new RuntimeException();
			}
			sb.append(ch);
			
			while (symbol.hasNext())
			{
				symbol.mark();
				ch = symbol.next();
				if (!Character.isDigit(ch))
				{
					symbol.reset();
					break;
				}
				sb.append(ch);
			}
			
			return sb.toString();
		}
		else
		{
			symbol.reset();
			return null;
		}
	}

	private String getSign(Symbol symbol)
	{
		symbol.mark();
		char ch = symbol.nextWithoutSpace();
		if (ch == '-')
		{
			return "" + ch;
		}
		symbol.reset();
		return null;
	}
	
	private String getIntegerPart(Symbol symbol, boolean hasSign)
	{
		StringBuffer sb = new StringBuffer();
		
		symbol.mark();
		char ch = hasSign ? symbol.next() : symbol.nextWithoutSpace();
		
		if (ch == '0')
		{
			sb.append(ch);
		}
		else if (ch >= '1' && ch <= '9')
		{
			sb.append(ch);
			while (symbol.hasNext())
			{
				symbol.mark();
				ch = symbol.next();
				if (!Character.isDigit(ch))
				{
					symbol.reset();
					break;
				}
				sb.append(ch);
			}
		}
		else
		{
			symbol.reset();
			return null;
		}
		
		return sb.toString();
	}
	
	private String getDecimalPart(Symbol symbol)
	{
		StringBuffer sb = new StringBuffer();
		
		symbol.mark();
		char ch = symbol.next();
		if ('.' == ch)
		{
			sb.append(ch);
			ch = symbol.next();
			if (!Character.isDigit(ch))
			{
				throw new RuntimeException();
			}
			sb.append(ch);
			
			while (symbol.hasNext())
			{
				symbol.mark();
				ch = symbol.next();
				if (!Character.isDigit(ch))
				{
					symbol.reset();
					break;
				}
				sb.append(ch);
			}
			return sb.toString();
		}
		else
		{
			symbol.reset();
			return null;
		}
	}
	
	public static void main(String[] args)
	{
		String[] numbers = {"12345", "-123", "123.4", "123.456", "0.123", "0.012", "123E12", "123.23e10"};
		
		NumberParser p = new NumberParser();
		for (String num : numbers)
		{
			JsonElement jn  = p.parse(new Symbol(num));
			System.out.println(num.equalsIgnoreCase(jn.toString()) + " : " + num + " ==> " + jn);
		}
	}
}
