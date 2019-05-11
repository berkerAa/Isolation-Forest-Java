
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.Quick;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdStats;

public class Histogram {
   private double diff,groupDiff;
   private Comparable<Double>[] data;
   private List<double[]> finalD;
   private double[] hD;
  
    public Histogram(double[] samples,int size) {
    	 
	   data = convert(samples,data);
	   data = sort(data);
	   diff = (double)data[data.length-1] - (double)data[0];
	   groupDiff =  diff/(double)size;
	   hD = samples;
	    finalD = new ArrayList<>();
	    createHistogram(data, groupDiff, (double)data[0], 0);
	    StdDraw.setCanvasSize(1024, 300);
	    draw(finalD);
	  /*  finalD.stream().forEach(d->{
	    System.out.println(Arrays.toString(d));
	    });*/
   }
   private Comparable<Double>[] convert(double[] data,Comparable<Double>[] dataC) {
	   dataC = new Comparable[data.length];
	   for(int i = 0;i<data.length;i++) {
		   dataC[i] = data[i];
	   }
	   return dataC;
   }
   private Comparable<Double>[] sort(Comparable<Double>[] data) {
	Quick.sort(data);
	   return data;
   }
   private void createHistogram(Comparable<Double>[] data,double hi,double lo,int index){
	   double range =0;
	   if(index<data.length)
	     range = (double)data[index] + hi;
 
  ArrayList<Double> bag = new ArrayList<Double>();
   while(index < data.length) {
	  
	   if((double)data[index]<range) {
		   bag.add((double)data[index]);
	       index++;
	   }
	   else {
		   finalD.add(fill(bag));
		  index++;
		  createHistogram(data,hi,lo,index);
		  break;
	   }
   }
   
   }
   private double[] fill(ArrayList<Double> fill) {
	   double[] d = new double[fill.size()];
	   int i = 0;
	   for(double x:fill) {
		   d[i] = x;
		   i++;
	   }
	   return d;
   }
   private void draw(List<double[]> d) {
	   d = normalize(d);
	   double[] drw = new double[d.size()];
	   int i = 0;
	  for(double[] x : d) {
		 drw[i] = (double)x.length/10;
		i++;
	  }
	  Arrays.sort(drw);
	 plotBars(drw);
	  
   }
   public static void plotBars(double[] a) {
      
       int n = a.length;
       StdDraw.setXscale(-1, n);
       int split = 2;
	   int xr = 0;
	   int xl = n-1;
       for (int i = 0; i < n; i++) {
    	  if(split%2 == 0) {
           StdDraw.filledRectangle(xl, a[i]/2, 0.30, a[i]/2);
    	  xl--;
    	  split++;}
    	  else {
    		  StdDraw.filledRectangle(xr, a[i]/2, 0.30, a[i]/2);
        	split++;  xr++;
    	  }
       }
   }
   private List<double[]> normalize(List<double[]> d) {
	   List<double[]> x = new ArrayList<>();
	d.stream().forEach(a->{
		 x.add(zScore(a));
	});
	d = x;
		
	 return d;
   }
   public double[] zScore(double[] data) {
   	int i = 0;
   	for(double x:data) {
   		
   		data[i] = (x - StdStats.min(hD))/(StdStats.max(hD)- StdStats.min(hD));
   		i++;
   	}
   	return data;
   }
}
