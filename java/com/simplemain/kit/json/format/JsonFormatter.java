package com.simplemain.kit.json.format;

import java.io.PrintWriter;

import com.simplemain.kit.json.element.JsonElement;

public interface JsonFormatter
{
	public void format(JsonElement element, PrintWriter pw);
}
