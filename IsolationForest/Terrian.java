

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdStats;

public class Terrian {
	static int sum1 = 0;
public static void main(String[] args) throws Exception {
	
	new DataSet("messages.txt"); 
	List<List<ArrayList<String>>> pathM = new ArrayList<>();
	List<Integer> iso = new ArrayList<>();
	BST<Integer, ArrayList<Integer>> counter = new BST<Integer,ArrayList<Integer>>();
	BST<Double, Integer> counter_len = new BST<Double,Integer>(); // Symbol Table (to store average path length of isolations)
	
	 new IsolationForest(DataSet.data);
	System.out.print(IsolationForest.iso + "   ");
	
	System.out.println(IsolationForest.path.size());
	
	for(int i = 0;i<100;i++) {
		new IsolationForest(DataSet.data);
	    int isolated,score;
	   	isolated=IsolationForest.iso;
	    score = IsolationForest.score();
	    pathM.add(IsolationForest.path);
	    if(counter.contains(isolated) == false ) {
	    iso.add(isolated);
	   counter.put(isolated,new ArrayList<Integer>());
	    }
	   
	   counter.get(isolated).add(score);
	     
	}
	System.out.println(counter.size());

	   iso.stream().forEach(x->{
		   int[] mean  = new int[counter.get(x).size()];
		   for(int i = 0;i<mean.length;i++) 
			   mean[i] = counter.get(x).get(i);
		   
		 counter_len.put( StdStats.mean(mean),x);
		
	   });
	   double[][] anomalys = new double[3][2];//getting best three possibiltys
	   for(int i = 0;i<3;i++) {
		   anomalys[i][1] = counter_len.min(); 
		   anomalys[i][0] = counter_len.get(counter_len.min());
		   counter_len.deleteMin();
	   }
	   for(double[] x:anomalys) {
		   for(double a : x) {
			   System.out.print(a + "  ");
		   }
		   System.out.println();
	   }
	  
	  
	   Terrian.plotPoints(Terrian.normal(DataSet.randomFeature(DataSet.data)),anomalys);
	 }
public static double[] normal(double[] data) {
	int i = 0;
	double[] dataC = new double[data.length];
	for(double x:data) {
		
		dataC[i] = (x - StdStats.min(data))/(StdStats.max(data)-StdStats.min(data));
		i++;
	}
	return dataC;
}
private static  void plotPoints(double[] a,double[][] anomaly) {

    int n = a.length;
    StdDraw.setXscale(-1, n+1);
    StdDraw.setPenRadius(0.02);
    StdDraw.setPenColor(StdDraw.RED);
    for (int i = 0; i < n; i++) 
    	if(i == anomaly[0][0] ||i == anomaly[1][0]||i == anomaly[2][0]) 
    	{
    		StdDraw.setPenColor(StdDraw.GREEN);
    		 StdDraw.point(i, a[i]);
    		StdDraw.setPenColor(StdDraw.RED);
    		System.out.println(DataSet.data.get(i)[1]);
    	}
    	else	
        StdDraw.point(i, a[i]);	
        
  }

}
