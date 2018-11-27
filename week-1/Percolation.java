import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation 
{
  private int size;
  private int[] siteStatus;
  private int openSitesCount = 0;
  private WeightedQuickUnionUF ufObject;
  private int length;
  
   public Percolation(int n)
   {
    if (n <= 0)
    {
      throw new IllegalArgumentException("Size can not be less than one");
    }
    
    size = n;
    length = n*n+2;
    ufObject = new WeightedQuickUnionUF(length);
    siteStatus = new int[length];
    siteStatus[0] = 2;
    siteStatus[length-1] = 1;
   }
   
   private int xyTo1D(int row, int col)
   {
     validate(row, col);
     return (row-1)*size+col;
   }
   
   private void validate(int row, int col)
   {
     if (row < 1 || col < 1 || row > size || col > size)
     {
       throw new IndexOutOfBoundsException("Index row = "+row+" and column = "+col+" out of bounds");
     }
   }
   
   private void makeNeighbourFull(int row, int col)
   {
//     top neighbour
     if (row > 1)
     {
       if (isOpen(row-1, col) && !isFull(row-1, col))
       {
         siteStatus[xyTo1D(row-1, col)] = 2;
         makeNeighbourFull(row-1, col);
       }
     }
//     bottom neighbour
     if (row < size)
     {
       if (isOpen(row+1, col) && !isFull(row+1, col))
       {
         siteStatus[xyTo1D(row+1, col)] = 2;
         makeNeighbourFull(row+1, col);
       }
     }
     
     //     left neighbour
     if (col > 1)
     {
       if (isOpen(row, col-1) && !isFull(row, col-1))
       {
         siteStatus[xyTo1D(row, col-1)] = 2;
         makeNeighbourFull(row, col-1);
       }
     }
//     bottom neighbour
     if (col < size)
     {
       if (isOpen(row, col+1) && !isFull(row, col+1))
       {
         siteStatus[xyTo1D(row, col+1)] = 2;
         makeNeighbourFull(row, col+1);
       }
     }
   }
   
   public void open(int row, int col)
   {
     int mapped = xyTo1D(row, col);
     siteStatus[mapped] = 1;
     openSitesCount++;
     int top = row-1;
     int bottom = row+1;
     int left = col-1;
     int right = col+1;
     
//     connecting top neighbour if its open
     if (top == 0)
     {
       ufObject.union(0, mapped);
       siteStatus[mapped] = 2;
     }
     else if (top > 0)
     {
       if (isOpen(top, col))
       {
         ufObject.union( xyTo1D(top, col),mapped);
       }
     }
     
//     connecting bottom neighbour
     if (bottom == size+1)
     {
       ufObject.union(length-1, mapped);
     }
     else if (bottom <= size)
     {
       if (isOpen(bottom, col))
       {
         ufObject.union(xyTo1D(bottom, col), mapped);
       }
     }
     
//     left neighbour
     if (left > 0 && isOpen(row, left))
     {
       ufObject.union(xyTo1D(row, left), mapped);
     }
     
//     right neighbour
     if (right <= size && isOpen(row, right))
     {
       ufObject.union( xyTo1D(row, right),mapped);
     }
     
     if (siteStatus[mapped] == 2 || (left > 0 && isFull(row, left)) || (right <= size && isFull(row, right)) || (top > 0 && isFull(top, col)) || (bottom <= size && isFull(bottom, col)))
     {
       siteStatus[mapped] = 2;
       makeNeighbourFull(row, col);
     }
     
   }
   
   public boolean isOpen(int row, int col)
   {
     int mapped = xyTo1D(row, col);
     return (siteStatus[mapped] != 0) ? true : false;
   }
   
   public boolean isFull(int row, int col)
   {
     int mapped = xyTo1D(row, col);
     if (siteStatus[mapped] == 2)
     {
       return true;
     }
     else
     {
       return false;
     }
   }
   
   public int numberOfOpenSites()
   {
     return openSitesCount;
   }
   
   public boolean percolates()
   {
     return ufObject.connected(0, length-1);
   }
}