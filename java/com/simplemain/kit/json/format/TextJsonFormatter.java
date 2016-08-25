package com.simplemain.kit.json.format;

import java.io.PrintWriter;
import java.util.List;

import com.simplemain.kit.json.element.JsonElement;
import com.simplemain.kit.json.element.Token;
import com.simplemain.kit.json.error.SyntaxException;
import com.simplemain.kit.json.format.MetaFormatter.Line;

public class TextJsonFormatter implements JsonFormatter
{
	private static final String TAB   = "    ";
	
	private boolean withLineNo      = false; // 是否输出行号
	private boolean withErrorInline = false; // 是否在行内输出错误指示
	private boolean withErrorDetail = false; // 是否输出详细错误信息
	
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
	
	@Override
	public void format(JsonElement element, PrintWriter pw)
	{
		final MetaFormatter format = new MetaFormatter();
		format.format(element, pw);
		
		final List<Line> lines = format.getLines();
		final List<SyntaxException> exceptions = format.getExceptions();
		
		final int lineNoWidth = getLineNoWidth(lines.size());
		int lineNo = 0;
		
		for (Line line : lines)
		{
			lineNo++;
			
			// 打印行号
			if (withLineNo)
			{
				if (lineNoWidth > 0)
				{
					pw.print(String.format("%" + lineNoWidth + "d.", lineNo));
				}
				else
				{
					pw.print(lineNo + ".");
				}
			}
			
			// 行内打印出错
			if (withErrorInline && !line.getExceptions().isEmpty())
			{
				pw.print("=>");
			}
			else if (withLineNo || (withErrorInline && !exceptions.isEmpty()))
			{
				pw.print("  ");
			}
			
			// 打印前导空格
			for (int i = 0; i < line.getTabCount(); i++)
			{
				pw.print(TAB);
			}
			
			// 打印内容
			for (Token token : line.getTokens())
			{
				final String s = token.getValue();
				if (token.getType() == Token.TYPE_KV_SEPARATOR)
				{
					pw.print(" " + s + " ");
				}
				else
				{
					pw.print(s);
				}
			}
			
			pw.println();
		}
		
		// 打印完整错误信息
		if (withErrorDetail && !exceptions.isEmpty())
		{
			pw.println();
			pw.println("==== syntax warnings & errors ====");
			for (SyntaxException e : exceptions)
			{
				pw.println(e.toString());
			}
		}
	}

	private int getLineNoWidth(int totalLineNo)
	{
		int ret = 0;
		for (; totalLineNo > 0; totalLineNo /= 10)
		{
			ret++;
		}
		return ret;
	}

}
