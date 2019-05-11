import edu.princeton.cs.algs4.In;

public class AnomalyMain {
	private static In in;
	private static double[] values;
public static void main(String[] args) throws Exception {

	in = new In("messages.txt");
	values =in.readAllDoubles();
	 new Statistical(values);
	new Statistical();
	
}
}

