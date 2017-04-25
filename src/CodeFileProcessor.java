import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Project 4, CS 2334, Section 010, May 4, 2017
 * <P>
 * This class provides helper methods to read a code file and turn it into a map
 * from <code>int</code>s to <code>String</code>s.
 * </P>
 * <P>
 * Note that the field <code>codeMap</code> and all methods in this class are
 * static because we don't need to make several <code>codeFileProcessor</code>
 * objects and have them maintain their own maps of codes. Instead, we simply
 * need a collection of useful methods to create a map and return it to the
 * calling method that will keep track of it.
 * </P>
 * 
 * @author Dean Hougen
 * @version 1.0
 * 
 */
public class CodeFileProcessor {
	private static Map<String, String> codeMap = new TreeMap<String, String>();

	/**
	 * The method for reading code files.
	 * <P>
	 * Code files are auxiliary data files for Nooz data files. Code files are
	 * comma separated values files that associate code numbers with words. Each
	 * line of a code file should contain one numeral followed by one set of
	 * words.
	 * </P>
	 * <P>
	 * Note that the field <code>codeMap</code> and all methods in this class
	 * are static because we don't need to make several
	 * <code>CodeFileProcessor</code> objects and have them maintain their own
	 * lists of codes. Instead, we simply need a collection of useful methods to
	 * create a code map and return it to the calling method that will keep
	 * track of it. <B>However, this means that when <code>readCodeFile</code>
	 * is called, it needs to clear out its map, since the old map data will
	 * still be in it. Also, the method that calls <code>readCodeFile</code>
	 * will need to make its own copy of the map returned, not simply use a
	 * pointer to the returned map.</B>
	 * </P>
	 * <P>
	 * Note that this program doesn't attempt to deal with I/O errors. This is
	 * allowable at this point to keep this project relatively simple and
	 * because we haven't covered this topic yet. However, this is something to
	 * be refined in the future.
	 * </P>
	 * 
	 * @param fileName
	 *            The name of the Nooz style data file to read.
	 * @return The list of news makers created.
	 * @throws IOException
	 *             If there is an I/O problem reading the data file.
	 */
	public static Map<String, String> readCodeFile(String fileName) throws IOException {
		// Need to clear map of old data before reading new.
		codeMap.clear();
		// TODO Handle possible I/O errors (Eventually)
		FileReader fr = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fr);
		String nextLine = br.readLine();
		while (nextLine != null) {
			String parts[] = nextLine.split(",");
			if (parts.length == 2) {
				codeMap.put(parts[0], parts[1].replaceAll("\"", ""));
			} else {
				System.err.println("Wrong number of components in line: " + parts.length);
				System.err.println("Skipping bad line in " + fileName + ": " + nextLine);
			}
			nextLine = br.readLine();
		}
		br.close();

		return codeMap;
	}
}
