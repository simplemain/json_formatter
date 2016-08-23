package com.simplemain.kit.json.format;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.simplemain.kit.json.element.JsonElement;
import com.simplemain.kit.json.element.Token;
import com.simplemain.kit.json.error.SyntaxException;

/**
 * @author zgwangbo@simplemain.com
 */
public class TextJsonFormatter implements JsonFormatter
{
	private static final String TAB   = "    ";
	
	private boolean withLineNo      = false;
	private boolean withErrorInline = false;
	private boolean withErrorDetail = false;
	
	private int lineNoWidth = 0;
	private int lineNo = 0;
	
	private Set<Integer> lineHasError = new HashSet<>();
	
	public TextJsonFormatter()
	{
		this(false, false, false);
	}
	
	public TextJsonFormatter(boolean withLineNo, boolean withErrorInline, boolean withErrorDetail)
	{
		this.withLineNo      = withLineNo;
		this.withErrorInline = withErrorInline;
		this.withErrorDetail = withErrorDetail;
	}
	
	public void format(JsonElement element, PrintWriter pw)
	{
		if (element == null || pw == null)
		{
			return;
		}
		
		try
		{
			// 如果要打印行号或者是指出错误位置，需要先预打印一遍，以确定
			// 1、行号的宽度
			// 2、错误所在的行
			if (withLineNo || withErrorInline)
			{
				doFormat(element, null, null);
				for (lineNoWidth = 0; lineNo > 0; lineNo /= 10)
				{
					lineNoWidth++;
				}
			}
			
			// 正式打印
			List<SyntaxException> exs = withErrorDetail ? new ArrayList<SyntaxException>() : null;
			doFormat(element, pw, exs);
			
			println(pw, "");
			println(pw, "");
			
			// 打印完整错误信息
			if (withErrorDetail && !exs.isEmpty())
			{
				pw.println("==== syntax warnings & errors ====");
				for (SyntaxException e : exs)
				{
					println(pw, e.toString());
				}
			}
		}
		finally
		{
			pw.flush();
		}
	}
	
	private void doFormat(JsonElement element, PrintWriter pw, List<SyntaxException> exs)
	{
		lineNo = 0;
		newLine(pw);
		
		int lastLevel = 0;
		
		for (Token token : element)
		{
			int level = token.getLevel();
			
			if (level != lastLevel)
			{
				newLine(pw);
				lastLevel = level;
				
				for (int i = 0; i < level; i++)
				{
					print(pw, TAB);
				}
				print(pw, token.getValue());
			}
			else if (token.getType() == Token.TYPE_SEG_SEPARATOR)
			{
				print(pw, token.getValue());
				newLine(pw);
				for (int i = 0; i < level; i++)
				{
					print(pw, TAB);
				}
			}
			else
			{
				if (token.getType() == Token.TYPE_KV_SEPARATOR)
				{
					print(pw, " " + token.getValue() + " ");
				}
				else
				{
					print(pw, token.getValue());
				}
			}
			
			if (token.hasExceptions())
			{
				lineHasError.add(lineNo);
			}
			
			if (exs != null && token.hasExceptions())
			{
				for (SyntaxException e : token.getExceptions())
				{
					exs.add(e);
				}
			}
		}
	}
	
	private void newLine(PrintWriter pw)
	{
		if (lineNo > 0)
		{
			println(pw, "");
		}
		
		++lineNo;
		
		if (withLineNo)
		{
			if (lineNoWidth > 0)
			{
				print(pw, String.format("%" + lineNoWidth + "d.", lineNo));
			}
			else
			{
				print(pw, lineNo + ".");
			}
		}
		
		if (withErrorInline && lineHasError.contains(lineNo))
		{
			print(pw, "=>");
		}
		else if (withLineNo || (withErrorInline && !lineHasError.isEmpty()))
		{
			print(pw, "  ");
		}
	}
	
	private void print(PrintWriter pw, String msg)
	{
		if (pw != null)
		{
			pw.print(msg);
		}
	}
	
	private void println(PrintWriter pw, String msg)
	{
		if (pw != null)
		{
			pw.println(msg);
		}
	}
}
