package elcon.mods.wikilink.web;

import java.lang.reflect.Method;

import elcon.mods.wikilink.WLConfig;
import elcon.mods.wikilink.WikiLink;

public class BrowserHelper {

	public static String osName = System.getProperty("os.name");

	public static void openLink(String link) {
		if(link != null && !link.contains("google.com")) {
			if(WLConfig.shortenLinks) {
				link = WebHelper.shortenlink(link);
			}
		}
		try {
			if(osName.startsWith("Windows")) {
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + link);
			} else if(osName.startsWith("Mac OS")) {
				Class<?> fileMgr = Class.forName("com.apple.eio.FileManager");
				Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[]{String.class});
				openURL.invoke(null, new Object[]{link});
			} else {
				String[] browsers = {"xdg-open", "google-chrome", "firefox", "opera", "epiphany", "konqueror", "conkeror", "midori", "kazehakase", "mozilla", "netscape"};
				String browser = null;
				for(int count = 0; count < browsers.length && browser == null; count++) {
					if(Runtime.getRuntime().exec(new String[]{"which", browsers[count]}).waitFor() == 0) {
						browser = browsers[count];
					}
				}
				if(browser == null) {
					throw new Exception();
				} else {
					Runtime.getRuntime().exec(new String[]{browser, link});
				}
			}
		} catch(Exception e) {
			WikiLink.log.error("Can't find a browser to open links!");
		}
	}
}
