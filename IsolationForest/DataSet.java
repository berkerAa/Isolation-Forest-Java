
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.princeton.cs.algs4.In;

public class DataSet {
private In read;
private String[] lines;
public static List<double[]> data;

  public DataSet(String fileName) {
	  read = new In(fileName);
	  lines = read.readAllLines();
	  DataRow();
  }
  private double[] samples(String[] file,int index) {
	  String[] splitValue = {""};
	   splitValue = file[index].split("\\s+");
	  int count =0; 
	  
	  double[] dimens = new double[splitValue.length+1] ;
	 for(String x : splitValue)	{
		 dimens[count] = data.size();
		dimens[count+1] = Double.parseDouble(x);
		count++;
	 }
	 return dimens;
  }
  private void DataRow() {
	  data = new ArrayList<>();
	  for(int i = 0;i<lines.length;i++)
		  data.add(samples(lines,i));
  }
  public static List<double[]> getSubSamples(List<double[]> samples, int sampleNum) throws Exception {



      if (samples == null || samples.size() == 0) {
          throw new Exception("Samples is null or empty, please check...");
      } else if (sampleNum <= 0) {
          throw new Exception("Number of sampleNum must be a positive...");
      }

      if (samples.size() < sampleNum) {
          sampleNum = samples.size();
      }
      List<double[]> subSamples =new ArrayList<>();

      int randomIndex;
      Random random = new Random();
      for (int i = 0; i < sampleNum; i++) {
          randomIndex = random.nextInt(samples.size());
          subSamples.add(samples.get(randomIndex));
      }

      return subSamples;
  }
  public static double[] randomFeature(List<double[]> samples) {
	  double[] normal = new double[samples.size()];
	  for(int i = 0;i<samples.size();i++) {
		  normal[i]=samples.get(i)[1];
	  }
	  return normal;
	  
  }
  
 

  
}
