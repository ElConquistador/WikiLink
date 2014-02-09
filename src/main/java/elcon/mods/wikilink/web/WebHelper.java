package elcon.mods.wikilink.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import elcon.mods.wikilink.WLReference;

public class WebHelper {

	public static String encode(String str) {
		return str.replace(" ", "%20").replace("!", "%21").replace("#", "%23").replace("$", "%24").replace("&", "%26").replace("'", "%27").replace("(", "%28").replace(")", "%29").replace("*", "%2A").replace("+", "%2B").replace(",", "%2C").replace("/", "%2F").replace(":", "%3A").replace(";", "%3B").replace("=", "%3D").replace("?", "%3F").replace("@", "%40").replace("[", "%5B").replace("]", "%5D");
	}

	public static String decode(String str) {
		return str.replace("%20", " ").replace("%21", "!").replace("%23", "#").replace("%24", "$").replace("%26", "&").replace("%27", "'").replace("%28", "(").replace("%29", ")").replace("%2A", "*").replace("%2B", "+").replace("%2C", ",").replace("%2F", "/").replace("%3A", ":").replace("%3B", ";").replace("%3D", "=").replace("%3F", "?").replace("%40", "@").replace("%5B", "[").replace("%5D", "]");
	}

	public static String getResponse(String str) {
		String response = null;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new URL(str).openStream()));
			String line;
			while((line = in.readLine()) != null) {
				response += line;
			}
			in.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public static String shortenlink(String str) {
		String link = str;
		String url = String.format("https://api-ssl.bitly.com/v3/shorten?access_token=%s" + "&longUrl=%s", WLReference.BITLY_API_TOKEN, encode(str));
		String response = getResponse(url);
		try {
			if(response != null) {
				link = "http://" + response.substring(response.indexOf("wikilink.info"), response.indexOf("wikilink.info") + 22).replace("\\/", "/");
			}
		} catch(Exception e) {
			return link;
		}
		return link;
	}
}
