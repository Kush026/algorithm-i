import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item>
{
  private int length = 0;
  private Item[] elements;
  private int capacity;
  private boolean shuffleStatus = false;
  
  public RandomizedQueue()
  {
    elements = (Item[]) new Object[10];
    capacity = 10;
  }
  
  public boolean isEmpty()
  {
    return (length == 0) ? true : false;
  }
  
  public int size()
  {
    return length;
  }
     
  public void enqueue(Item item)
  {
    if(item == null)
      throw new IllegalArgumentException("item can't be null");
    
    if(length == capacity)
    {
      capacity = 2*capacity;
      resizeArray(capacity);
    }
    
    elements[length] = item;
    length++;
    shuffleStatus = true;
  }
  
  private void resizeArray(int capacity)
  {
    this.capacity = capacity;
    Item[] newElements = (Item[]) new Object[capacity];
    
    for(int i = 0; i < length; i++)
    {
      newElements[i] = elements[i];
    }
    elements = newElements;
  }
   
  public Item dequeue()
  {
    if(isEmpty())
      throw new java.util.NoSuchElementException("queue is empty");
    if(length == capacity/4)
    {
      capacity = capacity/2;
      resizeArray(capacity);
    }
    
    if(shuffleStatus)
    {
      StdRandom.shuffle(elements, 0, length);
      shuffleStatus = false;
    }
    
    Item popped = elements[length-1];
    elements[length-1] = null;
    length--;
    return popped;
  }
  
  public Item sample()
  {
    if(isEmpty())
      throw new java.util.NoSuchElementException("queue is empty");
    int i = StdRandom.uniform(length);
    return elements[i];
  }
  
  public Iterator<Item> iterator()
  {
    return new ArrayIterator();
  }
  
  private class ArrayIterator implements Iterator<Item>
  {
    private Item[] newArray;
    ArrayIterator()
    {
      newArray = (Item[]) new Object[length];
      for(int i = 0; i < length; i++)
      {
        newArray[i] = elements[i];
      }
      
      StdRandom.shuffle(newArray);
    }
    
    private int current = 0;
    public boolean hasNext()
    {
      return (current < length) ? true : false;
    }
    
    public Item next()
    {
      if(!hasNext())
        throw new NoSuchElementException("No next element");
      return newArray[current++];
    }
    
    public void remove()
    {
      throw new UnsupportedOperationException("this operation is not supported");
    }
    
  }
  
}