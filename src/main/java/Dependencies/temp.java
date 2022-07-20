package Dependencies;

import java.util.*;
import java.io.*;

public class temp {

	public static void main(String[] args) {
		FastInputReader in = new FastInputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);

		// Code Tings

		out.close();
	}

}

class FastInputReader {
	private BufferedReader reader;
	private StringTokenizer tokenizer;

	public FastInputReader(InputStream stream) {
		reader = new BufferedReader(new InputStreamReader(stream));
		tokenizer = null;
	}

	public String next() {
		while (tokenizer == null || !tokenizer.hasMoreTokens()) {
			try {
				tokenizer = new StringTokenizer(reader.readLine());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return tokenizer.nextToken();
	}

	public String nextLine() throws IOException {
		return reader.readLine();
	}

	public int nextInt() {
		return Integer.parseInt(next());
	}

	public double nextDouble() {
		return Double.parseDouble(next());
	}
}
