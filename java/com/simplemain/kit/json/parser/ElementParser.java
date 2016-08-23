package com.simplemain.kit.json.parser;

import com.simplemain.kit.json.element.JsonElement;

public interface ElementParser
{
	public JsonElement parse(Symbol symbol);
}
