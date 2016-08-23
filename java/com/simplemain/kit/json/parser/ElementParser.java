package com.simplemain.kit.json.parser;

import com.simplemain.kit.json.element.JsonElement;

/**
 * @author zgwangbo@simplemain.com
 */
public interface ElementParser
{
	public JsonElement parse(Symbol symbol);
}
