import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> 
{
  private Node first;
  private Node last;
  private int length = 0;
  
  private class Node
  {
    private Item item;
    private Node next;
    private Node prev;
    
    Node(Item item)
    {
      this.item = item;
      this.next = null;
      this.prev = null;
    }
  }
  
  public Deque()
  {
    first = null;
    last = null;
  }
   
  public boolean isEmpty()
  {
    return first == null ? true : false;
  }
  
  public int size()
  {
    return length;
  }
  
  public void addFirst(Item item)
  {
    if(item == null)
      throw new IllegalArgumentException("variable can't be null");
    if(last == null)
    {
      first = last = new Node(item);
    }
    else
    {
      Node temp = first;
      first = new Node(item);
      first.prev = null;
      first.next = temp;
      temp.prev = first;
    }
    
    length++;
  }
  public void addLast(Item item)
  {
    if(item == null)
      throw new IllegalArgumentException("variable can't be null");
    if(first == null)
    {
      first = last = new Node(item);
    }
    else
    {
      last.next = new Node(item);
      last.next.prev = last;
      last = last.next;
    }
    
    length++;
  }
  
  public Item removeFirst()
  {
    Item value;
    if(first == null)
      throw new java.util.NoSuchElementException("queue is empty");
    if(first == last)
    {
      value = first.item;
      first = last = null;
    }
    else
    {
      Node temp = first;
      value = temp.item;
      first = first.next;
      first.prev = null;
      temp = null;
    }
    
    length--;
    return value;
  }
  
  public Item removeLast()
  {
    Item value;
    if(first == null)
      throw new java.util.NoSuchElementException("queue is empty");
    if(first == last)
    {
      value = first.item;
      first = last = null;
    }
    else
    {
      Node temp = last;
      value = temp.item;
      last = last.prev;
      last.next = null;
      temp = null;
    }
    
    length--;
    return value;
  }
  
  public Iterator<Item> iterator()
  {
    return new DequeIterator();
  }
  
  private class DequeIterator implements Iterator<Item>
  {
    private Node current = first;
    
    public boolean hasNext()
    {
      return current == null ? false : true;
    }
    
    public void remove()
    {
      throw new java.lang.UnsupportedOperationException("This operation is not supported");
    }
    
    public Item next()
    {
      if(current == null)
        throw new java.util.NoSuchElementException("There is no next item in deque");
      Item item = current.item;
      current = current.next;
      return item;
    }
  }
  
}