import java.util.Arrays;

public class FastCollinearPoints {
  private LineSegment[] lineSeg;
  
  public FastCollinearPoints(Point[] points)
  {
    if(points == null)
       throw new java.lang.IllegalArgumentException("Null is not allowed");
    
    int length = points.length;
    Point[] points1;
    points1 = new Point[length];
    Arrays.sort(points);
     
    Point tempP;
    double prevSlope, tempSlope;
    
    int maxLength = length*(length-1)*(length-2)/24;
    if(maxLength < 0)
      maxLength = 1;
    LineSegment[] tempStorage = new LineSegment[maxLength];
    
    int k, ptr = 0;
    
    for(int i = 0; i < length-3; i++)
    {
      points1 = points.clone();
      tempP = points1[i];
      
      Arrays.sort(points1, i+1, length, tempP.slopeOrder());
      k = 1;
      prevSlope = tempP.slopeTo(points1[i+1]);
      for(int j = i+2; j < length; j++)
      {
        tempSlope = tempP.slopeTo(points1[j]);
        if(tempSlope == prevSlope)
        {
          k++;
          if(j == length-1 && k >= 3)
          {
            tempStorage[ptr++] = new LineSegment(tempP, points1[j]);
          }
        }
        else
        {
          if(k >= 3)
          {
            tempStorage[ptr++] = new LineSegment(tempP, points1[j-1]);
          }
          
          prevSlope = tempSlope;
          k = 1;
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