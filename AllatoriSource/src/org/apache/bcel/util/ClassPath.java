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

	public static final ClassPath SYSTEM_CLASS_PATH = new ClassPath();
	private PathEntry[] paths;
	private String class_path;

	public ClassPath(String class_path) {
		this.class_path = class_path;
		List vec = new ArrayList();
		for (StringTokenizer tok = new StringTokenizer(class_path, System.getProperty("path.separator")); tok
				.hasMoreTokens();) {
			String path = tok.nextToken();
			if (!path.equals("")) {
				File file = new File(path);
				try {
					if (file.exists()) {
						if (file.isDirectory()) {
							vec.add(new Dir(path));
						} else {
							vec.add(new Zip(new ZipFile(file)));
						}
					}
				} catch (IOException e) {
					System.err.println("CLASSPATH component " + file + ": " + e);
				}
			}
		}
		paths = new PathEntry[vec.size()];
		vec.toArray(paths);
	}

	public ClassPath() {
		this(getClassPath());
	}

	public String toString() {
		return class_path;
	}

	public int hashCode() {
		return class_path.hashCode();
	}

	public boolean equals(Object o) {
		if (o instanceof ClassPath) {
			return class_path.equals(((ClassPath) o).class_path);
		}
		return false;
	}

	private static final void getPathComponents(String path, List list) {
		if (path != null) {
			StringTokenizer tok = new StringTokenizer(path, File.pathSeparator);
			while (tok.hasMoreTokens()) {
				String name = tok.nextToken();
				File file = new File(name);
				if (file.exists()) {
					list.add(name);
				}
			}
		}
	}

	public static final String getClassPath() {
		String class_path = System.getProperty("java.class.path");
		String boot_path = System.getProperty("sun.boot.class.path");
		String ext_path = System.getProperty("java.ext.dirs");
		List list = new ArrayList();
		getPathComponents(class_path, list);
		getPathComponents(boot_path, list);
		List dirs = new ArrayList();
		getPathComponents(ext_path, dirs);
		for (Iterator e = dirs.iterator(); e.hasNext();) {
			File ext_dir = new File((String) e.next());
			String[] extensions = ext_dir.list(new FilenameFilter() {

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
		StringBuffer buf = new StringBuffer();
		for (Iterator e = list.iterator(); e.hasNext();) {
			buf.append((String) e.next());
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
		} catch (Exception e) {
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
			InputStream is = getInputStream(name, suffix);
			if (is == null) {
				throw new IOException("Couldn't find: " + name + suffix);
			}
			dis = new DataInputStream(is);
			byte[] bytes = new byte[is.available()];
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
		int index = name.lastIndexOf('.');
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
		private String dir;

		Dir(String d) {
			dir = d;
		}

		ClassFile getClassFile(String name, String suffix) throws IOException {
			final File file = new File(dir + File.separatorChar + name.replace('.', File.separatorChar) + suffix);
			return file.exists() ? new ClassFile() {
				public InputStream getInputStream() throws IOException {
					return new FileInputStream(file);
				}

				public String getPath() {
					try {
						return file.getCanonicalPath();
					} catch (IOException e) {
						return null;
					}
				}
				
				public long getTime() {
					return file.lastModified();
				}

				public long getSize() {
					return file.length();
				}

				public String getBase() {
					return dir;
				}
			} : null;
		}

		public String toString() {
			return dir;
		}
	}

	private static class Zip extends PathEntry {
		private ZipFile zip;
		
		Zip(ZipFile z) {
			zip = z;
		}
		
		ClassFile getClassFile(String name, String suffix) throws IOException {
			final ZipEntry entry = zip.getEntry(name.replace('.', '/') + suffix);
			return (entry != null) ? new ClassFile() {
				public InputStream getInputStream() throws IOException {
					return zip.getInputStream(entry);
				}

				public String getPath() {
					return entry.toString();
				}

				public long getTime() {
					return entry.getTime();
				}

				public long getSize() {
					return entry.getSize();
				}

				public String getBase() {
					return zip.getName();
				}
			} : null;
		}
	}
}
