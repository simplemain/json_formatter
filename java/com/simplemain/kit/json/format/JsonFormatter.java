package com.simplemain.kit.json.format;

/**
 * @author zgwangbo@simplemain.com
 */
import java.io.PrintWriter;

import com.simplemain.kit.json.element.JsonElement;

public interface JsonFormatter
{
	public void format(JsonElement element, PrintWriter pw);
}
