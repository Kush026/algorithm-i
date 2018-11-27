import java.util.Arrays;

public class BruteCollinearPoints
{
  private final LineSegment[] lineSeg;
  
   public BruteCollinearPoints(Point[] points)
   {
     Point[] points1;
     if(points == null)
       throw new java.lang.IllegalArgumentException("Null is not allowed");
     points1 = new Point[points.length];
     for(int i = 0; i < points.length; i++)
     {
       if(points[i] == null)
         throw new java.lang.IllegalArgumentException("Null is not allowed");
       points1[i] = points[i];
     }
     
     Arrays.sort(points1);
     
     for(int i = 1; i < points.length; i++)
     {
       if(points1[i].compareTo(points1[i-1]) == 0 )
         throw new java.lang.IllegalArgumentException("duplicate points");
     }
     
     int length = points1.length;
     int maxLength = length*(length-1)*(length-2)/24;
     if(maxLength < 0)
       maxLength = 1;
     LineSegment[] tempStorage = new LineSegment[maxLength];
     int ptr = 0;
     Point p,q,r,s;
     double pqSlope, prSlope, psSlope;
     
     for(int i = 0; i < length-3; i++)
     {
       for(int j = i+1; j < length-2; j++)
       {
         for(int k = j+1; k < length-1; k++)
         {
           for(int l = k+1; l < length; l++)
           {
             p = points1[i];
             q = points1[j];
             r = points1[k];
             s = points1[l];
             pqSlope = p.slopeTo(q);
             prSlope = p.slopeTo(r);
             
             if(pqSlope == prSlope)
             {
               psSlope = p.slopeTo(s);
               
               if(psSlope == pqSlope)
               {
                 tempStorage[ptr++] = new LineSegment(p, s);
               }
             }
             
           }
         }
       }
     }
     
     lineSeg = new LineSegment[ptr];
     for(int i = 0; i < ptr; i++)
     {
       lineSeg[i] = tempStorage[i];
     }
   }
   
   public int numberOfSegments()
   {
     return lineSeg.length;
   }
   
   public LineSegment[] segments()
   {
     return lineSeg.clone();
   }
}