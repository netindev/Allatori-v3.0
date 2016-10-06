package org.apache.bcel.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ClassPath implements Serializable {

	private static final long serialVersionUID = -7769396876028050656L;
	public static final ClassPath SYSTEM_CLASS_PATH = new ClassPath();
	private final PathEntry[] paths;
	private final String class_path;

	public ClassPath(String class_path) {
		this.class_path = class_path;
		final List<PathEntry> vec = new ArrayList<PathEntry>();
		for (final StringTokenizer tok = new StringTokenizer(class_path, System.getProperty("path.separator")); tok
				.hasMoreTokens();) {
			final String path = tok.nextToken();
			if (!path.equals("")) {
				final File file = new File(path);
				try {
					if (file.exists()) {
						if (file.isDirectory()) {
							vec.add(new Dir(path));
						} else {
							vec.add(new Zip(new ZipFile(file)));
						}
					}
				} catch (final IOException e) {
					System.err.println("CLASSPATH component " + file + ": " + e);
				}
			}
		}
		paths = new PathEntry[vec.size()];
		vec.toArray(paths);
	}

	@Deprecated
	public ClassPath() {
		this(getClassPath());
	}

	@Override
	public String toString() {
		return class_path;
	}

	@Override
	public int hashCode() {
		return class_path.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof ClassPath) {
			return class_path.equals(((ClassPath) o).class_path);
		}
		return false;
	}

	private static final void getPathComponents(String path, List<String> list) {
		if (path != null) {
			final StringTokenizer tok = new StringTokenizer(path, File.pathSeparator);
			while (tok.hasMoreTokens()) {
				final String name = tok.nextToken();
				final File file = new File(name);
				if (file.exists()) {
					list.add(name);
				}
			}
		}
	}

	public static final String getClassPath() {
		final String class_path = System.getProperty("java.class.path");
		final String boot_path = System.getProperty("sun.boot.class.path");
		final String ext_path = System.getProperty("java.ext.dirs");
		final List<String> list = new ArrayList<String>();
		getPathComponents(class_path, list);
		getPathComponents(boot_path, list);
		final List<String> dirs = new ArrayList<String>();
		getPathComponents(ext_path, dirs);
		for (final Iterator<String> e = dirs.iterator(); e.hasNext();) {
			final File ext_dir = new File(e.next());
			final String[] extensions = ext_dir.list(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					name = name.toLowerCase(Locale.ENGLISH);
					return name.endsWith(".zip") || name.endsWith(".jar");
				}
			});
			if (extensions != null) {
				for (int i = 0; i < extensions.length; i++) {
					list.add(ext_dir.getPath() + File.separatorChar + extensions[i]);
				}
			}
		}
		final StringBuffer buf = new StringBuffer();
		for (final Iterator<String> e = list.iterator(); e.hasNext();) {
			buf.append(e.next());
			if (e.hasNext()) {
				buf.append(File.pathSeparatorChar);
			}
		}
		return buf.toString().intern();
	}

	public InputStream getInputStream(String name) throws IOException {
		return getInputStream(name.replace('.', '/'), ".class");
	}

	public InputStream getInputStream(String name, String suffix) throws IOException {
		InputStream is = null;
		try {
			is = getClass().getClassLoader().getResourceAsStream(name + suffix);
		} catch (final Exception e) {
		}
		if (is != null) {
			return is;
		}
		return getClassFile(name, suffix).getInputStream();
	}

	public ClassFile getClassFile(String name, String suffix) throws IOException {
		for (int i = 0; i < paths.length; i++) {
			ClassFile cf;
			if ((cf = paths[i].getClassFile(name, suffix)) != null) {
				return cf;
			}
		}
		throw new IOException("Couldn't find: " + name + suffix);
	}

	public ClassFile getClassFile(String name) throws IOException {
		return getClassFile(name, ".class");
	}

	public byte[] getBytes(String name, String suffix) throws IOException {
		DataInputStream dis = null;
		try {
			final InputStream is = getInputStream(name, suffix);
			if (is == null) {
				throw new IOException("Couldn't find: " + name + suffix);
			}
			dis = new DataInputStream(is);
			final byte[] bytes = new byte[is.available()];
			dis.readFully(bytes);
			return bytes;
		} finally {
			if (dis != null) {
				dis.close();
			}
		}
	}

	public byte[] getBytes(String name) throws IOException {
		return getBytes(name, ".class");
	}

	public String getPath(String name) throws IOException {
		final int index = name.lastIndexOf('.');
		String suffix = "";
		if (index > 0) {
			suffix = name.substring(index);
			name = name.substring(0, index);
		}
		return getPath(name, suffix);
	}

	public String getPath(String name, String suffix) throws IOException {
		return getClassFile(name, suffix).getPath();
	}

	private static abstract class PathEntry implements Serializable {

		private static final long serialVersionUID = -8416003584705594546L;

		abstract ClassFile getClassFile(String name, String suffix) throws IOException;
	}

	public interface ClassFile {

		public abstract InputStream getInputStream() throws IOException;

		public abstract String getPath();

		public abstract String getBase();

		public abstract long getTime();

		public abstract long getSize();
	}

	private static class Dir extends PathEntry {

		private static final long serialVersionUID = 4456654190221513714L;
		private final String dir;

		Dir(String d) {
			dir = d;
		}

		@Override
		ClassFile getClassFile(String name, String suffix) throws IOException {
			final File file = new File(dir + File.separatorChar + name.replace('.', File.separatorChar) + suffix);
			return file.exists() ? new ClassFile() {

				@Override
				public InputStream getInputStream() throws IOException {
					return new FileInputStream(file);
				}

				@Override
				public String getPath() {
					try {
						return file.getCanonicalPath();
					} catch (final IOException e) {
						return null;
					}
				}

				@Override
				public long getTime() {
					return file.lastModified();
				}

				@Override
				public long getSize() {
					return file.length();
				}

				@Override
				public String getBase() {
					return dir;
				}
			} : null;
		}

		@Override
		public String toString() {
			return dir;
		}
	}

	private static class Zip extends PathEntry {

		private static final long serialVersionUID = 6091477032709796156L;
		private final ZipFile zip;

		Zip(ZipFile z) {
			zip = z;
		}

		@Override
		ClassFile getClassFile(String name, String suffix) throws IOException {
			final ZipEntry entry = zip.getEntry(name.replace('.', '/') + suffix);
			return (entry != null) ? new ClassFile() {

				@Override
				public InputStream getInputStream() throws IOException {
					return zip.getInputStream(entry);
				}

				@Override
				public String getPath() {
					return entry.toString();
				}

				@Override
				public long getTime() {
					return entry.getTime();
				}

				@Override
				public long getSize() {
					return entry.getSize();
				}

				@Override
				public String getBase() {
					return zip.getName();
				}
			} : null;
		}
	}
}
