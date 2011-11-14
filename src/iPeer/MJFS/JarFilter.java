package iPeer.MJFS;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class JarFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
			return f.isDirectory() || f.getName().endsWith(".jar");
	}

	@Override
	public String getDescription() {
		return "*.jar";
	}

}
