import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
  
public class PercolationStats 
{
  private Percolation perc;
  private int trials;
  private double[] fractions;
  private double mean = 0.0;
  private double stdDev = 0.0;
  private double confidIntervalLo;
  private double confidIntervalHi;
  private Point[] points;
  
  private class Point
  {
    private int x;
    private int y;
    
    Point(int x, int y)
    {
      this.x = x;
      this.y = y;
    }
  }
  
   public PercolationStats(int n, int trials)
   {
     if (n < 1 || trials < 1)
     {
       throw new java.lang.IllegalArgumentException("values can't be less than 1");
     }
     this.trials = trials;
     fractions = new double[trials];
     points = new Point[n*n];
     
     for (int i = 1; i <= n; i++)
     {
       for (int j = 1; j <= n; j++)
       {
         points[(i-1)*n+j-1] = new Point(i, j);
       }
     }
     
     for (int i = 0; i < trials; i++)
     {
       perc = new Percolation(n);
       shufflePoints(points);
       int length = points.length;
       
       for (int j = 0; j < length; j++)
       {
         perc.open(points[j].x, points[j].y);
         if (perc.percolates())
         {
           break;
         }
       }
       fractions[i] = (double) perc.numberOfOpenSites()/length;
     }
     
     mean = StdStats.mean(fractions);
     
     stdDev = StdStats.stddev(fractions);
     
     confidIntervalLo = mean-(1.96*stdDev/Math.sqrt(trials));
     confidIntervalHi = mean+(1.96*stdDev/Math.sqrt(trials));
   }
   
   private void shufflePoints(Point[] points)
   {
     int length = points.length;
     Point temp;
     for (int i = 1; i < length; i++)
     {
       int r = StdRandom.uniform(i+1);
       if (i != r)
       {
         temp = points[i];
         points[i] = points[r];
         points[r] = temp;
       }
     }
   }
   
   public double mean()
   {
     return mean;
   }
   
   public double stddev()
   {
     if (trials == 1)
     {
       return Double.NaN;
     }
     return stdDev;
   }
   
   public double confidenceLo()
   {
     return confidIntervalLo;
   }
   
   public double confidenceHi()
   {
     return confidIntervalHi;
   }

   public static void main(String[] args)
   {
     int n = Integer.parseInt(args[0]);
     int T = Integer.parseInt(args[1]);
     
     PercolationStats statObject = new PercolationStats(n, T);
     System.out.println("mean                    = "+statObject.mean());
     System.out.println("stddev                  = "+statObject.stddev());
     System.out.println("95% confidence interval = ["+statObject.confidenceLo()+", "+statObject.confidenceHi()+"]");
   }
}