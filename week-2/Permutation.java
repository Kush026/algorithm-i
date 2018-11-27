import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
public class Permutation {
  
   public static void main(String[] args)
   {
     int k = Integer.parseInt(args[0]);     
     RandomizedQueue<String> que = new RandomizedQueue<String>();
     
     while(!StdIn.isEmpty())
     {
       que.enqueue(StdIn.readString());
     }
     
     Iterator<String> iter1 = que.iterator();
     
     for(int i = 0; i < k; i++)
       StdOut.println(iter1.next());
   }
}