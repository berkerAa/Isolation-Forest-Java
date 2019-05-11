

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


class IsolationForest{
	public static List<ArrayList<String>> path;
	public static int iso;
	private Random rand;
    public IsolationForest(List<double[]> df) throws Exception {
    	df = DataSet.getSubSamples(df, 256);
      path= new ArrayList<>(); 
     
       iso = recursive_partition(df, 8,0,0);
     
    }    
    private int recursive_partition(List<double[]> df,int depth,int level,int pathI) {
     
       rand = new Random();
        if (level == depth)
        	 return -1;
        if(df.size()==0)
        	return iso;
       int f = 0;
       List<Double> feature = new ArrayList<>();
      
        f = rand.nextInt(df.get(0).length);
        int ft = f;
        df.stream().forEach(x->{
        	feature.add(x[ft]);
        });
      
       int mini,maxi;
       mini = 0;
       maxi = feature.size();
       int cut = 0;  
      
       cut =rand.nextInt(maxi) ;
      
      path.add(new ArrayList<String>());
       path.get(pathI).add("level is : " + level);
       path.get(pathI).add("cut is :" + cut);
       path.get(pathI).add("feature is :" + f);
       path.get(pathI).add("mini is : " + mini);
       path.get(pathI).add("maxi is : " + maxi);
       pathI++;
        
        int cutS = cut;
       List<double[]> smaller,greater;
       smaller = new ArrayList<>();
       greater = new ArrayList<>();
       feature.stream().forEach(x->{
    	   
    	   double e = x;
    	   if(x<cutS) {
    		   smaller.add(new double[2]);
    		   smaller.get(smaller.size()-1)[1] = e;
    		   smaller.get(smaller.size()-1)[0] = feature.indexOf(x);
    	
       }
    	   else {
    		   greater.add(new double[2]);
    		   greater.get(greater.size()-1)[1] = e;
    		   greater.get(greater.size()-1)[0] = feature.indexOf(x);
    	   }
       });
       
        if(smaller.size()==1)
        	return (int)smaller.get(0)[0];
        if(greater.size()==1)
        	return (int)greater.get(0)[0];
        
        int isolated_index  = 0;
    
        level++;
        isolated_index = recursive_partition(smaller,depth,level,pathI);
                                                  
        if (isolated_index != -1)
        	return isolated_index ;

       level++;
        isolated_index  =recursive_partition(greater, depth,level,pathI);
        if (isolated_index != -1)
        	return isolated_index;

        return -1;
    }
    public  static int score() {
       
        if (iso != -1)
         return path.size();
        else 
        	return -1;
        	
    }
    
  
   
   
}