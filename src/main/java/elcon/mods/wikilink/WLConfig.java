package elcon.mods.wikilink;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class WLConfig {

	public Configuration config;
	
	public WLConfig(Configuration config) {
		this.config = config;
	}
	
	public WLConfig(File config) {
		this(new Configuration(config));
	}
	
	public void load() {
		config.load();
	}
	
	public void save() {
		config.save();
	}
}
