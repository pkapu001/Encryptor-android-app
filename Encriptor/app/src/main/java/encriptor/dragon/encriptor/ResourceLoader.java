package encriptor.dragon.encriptor;

import java.io.InputStream;

final public class ResourceLoader {

	static ResourceLoader rl = new ResourceLoader();

	public static InputStream rload(String path) {
		InputStream input = rl.getClass().getResourceAsStream("" + path);

		return input;
	}
}
