package com.simplemain.kit.json;

import java.io.PrintWriter;

import com.simplemain.kit.json.element.JsonElement;
import com.simplemain.kit.json.format.TextJsonFormatter;
import com.simplemain.kit.json.parser.JsonParser;

/**
 * @author zgwangbo@simplemain.com
 */
public class Main
{
	public static void main(String[] args)
	{
		final String json = "{'number':123, \"hello\":\"\\u1343\", \"aa\": {'a':'b'}, 'array':['asdf', 123, null, true, false, 0.00003e-17, {'ddd':123,'hahah':[{}]},true]}";
		
		System.out.println("==== origin json string ====");
		System.out.println(json);
		System.out.println();
		
		final JsonParser jp  = new JsonParser();
		final JsonElement je = jp.parse(json);
		
		System.out.println("==== formatted json string ====");
		try (PrintWriter pw = new PrintWriter(System.out))
		{
			new TextJsonFormatter(true, true, true).format(je, pw);
		}
	}
}