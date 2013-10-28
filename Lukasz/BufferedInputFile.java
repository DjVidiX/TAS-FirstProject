import java.io.*;

public class BufferedInputFile {
	
	public static String read(String fileName, int k) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String s = new String();
		for (int i = 0; i < k; i++) {
			s = in.readLine();
		}
		s = in.readLine();
		in.close();
		return s;
	}
}