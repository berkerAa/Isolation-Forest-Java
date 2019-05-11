import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdStats;

public class Statistical 
{
	
	private In in;

	private double[] samples, movingAvg,localMean,samplesOrg;
	private int lo,hi;
	private List<Double> anomalys;
	Stack<Double> bag;
	private double mean;
	public Statistical(double[] sample) {
	 	StdDraw.setCanvasSize(1024,500);
	 	  new Histogram(sample, 100);//create && draw histogram
   	 try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	StdDraw.setCanvasSize(1024,300);
	}
    public Statistical() {
    	in = new In("messages.txt");
    	samples = in.readAllDoubles();
    	 mean = StdStats.mean(samples);
    	anomalys = new ArrayList<>();
    	bag = new Stack<Double>();//Idea ->bag of Anomalies
    	movingAvg = new double[samples.length-10];//avarage of elements in range 10
    	localMean = new double[samples.length];
    	samplesOrg = samples;
  
   
    	lo = 0;
    	hi = 10;
    	
    	//loop for moving average list
    	while(hi<samples.length) {
    		movingAvg[lo] = StdStats.mean(samples, lo, hi);
    		lo++;
    		hi++;
    	}
    	lo=0;
    	while(lo<samples.length) {
    	
    		localMean[lo] = lo<10? Math.abs(samples[lo] -movingAvg[0]):Math.abs(samples[lo] -movingAvg[lo-10]);
    		lo++;
    	}
    	
    	 	lo=0;
    	  threeS(localMean,samplesOrg); // Implementing three sigma rule 
    	movingAvg = normal(movingAvg); // for normalize moving average
    	samples = normal(samples);// for Normalize given Samples
    	localMean = normal(localMean);// for Normalize local mean
    	
    	StdDraw.setCanvasSize(1024,300);
    	StdStats.plotLines(localMean); // difference from local mean
    	try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	while(bag.isEmpty() != true) {
    		double anom = bag.pop();
    		System.out.println(anom + " is an anomaly");
    		anomalys.add(anom);
    	}//for print anomalies since program 
    	
     	StdDraw.setCanvasSize(1024,300);
    	plotLines(movingAvg); // drawing moving average
    	StdDraw.setPenColor(StdDraw.RED);
    	StdDraw.setPenRadius(0.09);
        plotPoints(samples,samplesOrg); // drawing samples 
       
    	
    	
    }
    // Calculation of Three Sigma Rule
    private Stack<Double> threeS(double[] samples,double[] org) {
    	mean=StdStats.mean(samples);
    	double sd=StdStats.stddevp(samples);
        
    	 Arrays.stream(samples).forEach(d->{
            double sf = Math.abs(d-mean);
    		 if(sf >=sd*3) 
    		 bag.push(org[lo]);
    		 lo++;
    	 });	
    	 return bag;
    }
    // Normalizetion data between 0-1 
    public static double[] normal(double[] data) {
    	int i = 0;
    	double[] dataC = new double[data.length];
    	for(double x:data) {
    		
    		dataC[i] = (x - StdStats.min(data))/(StdStats.max(data)-StdStats.min(data));
    		i++;
    	}
    	return dataC;
    }
    private  void plotPoints(double[] a,double[] data) {
       
        int n = a.length;
        StdDraw.setXscale(-1, n+1);
        StdDraw.setPenRadius(0.02);
        for (int i = 0; i < n; i++) 
        	if(anomalys.contains(data[i])) {
        		StdDraw.setPenColor(StdDraw.GREEN);
        		 StdDraw.point(i, a[i]);
        		StdDraw.setPenColor(StdDraw.RED);
        	}
        	else	
            StdDraw.point(i, a[i]);	
            
      }
    public void plotLines(double[] a) {
       
        int n = a.length;
        StdDraw.setXscale(-1, n);
        StdDraw.setPenRadius();
        for (int i = 1; i < n; i++) {
            StdDraw.line(i-1, a[i-1]/1.23, i, a[i]/1.23);
        }
    }
}
