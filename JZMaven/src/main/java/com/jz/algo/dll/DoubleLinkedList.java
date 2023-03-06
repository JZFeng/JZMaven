package com.jz.algo.dll;

import java.util.Iterator;

public class DoubleLinkedList implements Iterable
{
    ListNode head;

    private int size;
    
    private int modAmount = 0;

    public DoubleLinkedList()
    {
        head = new ListNode();
        head.prev = head;
        head.next = head;
        size = 0;
    }
    
    public int size()
    {
    	return this.size;
    }

    public DoubleLinkedList(Object[] a)
    {
        head = new ListNode();
        head.prev = head;
        head.next = head;

        for(int i = 0; i<a.length; i++) 
        	addBefore(head,a[i]);
        size = a.length;

    }

    public void addBefore(ListNode p, Object x)
    {
    	ListNode newNode = new ListNode(p.prev,x,p);
    	p.prev.next = newNode;
    	p.prev = newNode;
    }

    public void addAfter(ListNode p, Object x)
    {
    	ListNode newNode = new ListNode(p,x,p.next);
    	p.next.prev = newNode;
    	p.next = newNode;
    }

    boolean isEmpty()
    {
    	return (size == 0);
    }
    
    public void clear()
    {
    	if(size()>0)
    	{
    		ListNode current = head.next;
    		while(current != head)
    		{
    			ListNode next = current.next;
    			current.prev = null;
    			current.element = null;
    			current.next = null;
    			current = next;
    		}
    		
    		this.head = new ListNode(null,null,null);
    		head.prev = head = head.next;
    		this.size = 0;
    	}
    	
    }


    // better solution
    public ListNode getNodeAtIndex(int index)
    {
    	checkIndexRange(index);
    	
    	ListNode current = head.next;
    	
    	if(index < ((size()>>1)))
    	{
    		for (int i = 0; i < index; i++) {
				current = current.next;
			}
    		
    	}
    	else
    	{
    		current = head.prev;
    		for(int i = size-1; i > index; i-- )
    		{
    			
    			current = current.prev;
    		}
    		
    	}
    	
    	return current;
    }

    
    private void checkIndexRange(int index)
    {
    	if(index < 0 || index > size())
    		throw new IndexOutOfBoundsException();
    }
    
    
    
    public int len()
    {
        return size;
    }

    // get the index of an object.
    public int indexOf(Object o)
    {
        int index = 0;
        ListNode p = head.next;

        if (o == null)
        {
            while (p != head)
            {
                if (p.element == null)
                    return index;
                p = p.next;
                index++;
            }
        }
        else
        {
            while (p != head)
            {
                if (p.element.equals(o))
                    return index;
                p = p.next;
                index++;
            }
        }

        return -1;
    }

    public boolean contains(Object obj)
    {
        if (indexOf(obj) == -1)
            return false;
        return true;

    }

    /**
     * *
     * 
     * @param position //the position you want to add
     * @param obj //the element you want to add into the list
     */
    public void add(int position, Object obj)
    {
        checkIndexRange(position);

        int index = 0;
        ListNode p = head;
        while (index <= position)
        {
            p = p.next;
            index++;
        }
        addBefore(p, obj);
        size++;
    }

    public void add(Object obj)
    {
        addBefore(head, obj);
    }

    /*
     * remove the node at specified index
     */
    public void remove(int index)
    {
        checkIndexRange(index);

        ListNode removedNode = null;
        ListNode p = head;

        for (int i = 0; i <= index; i++)
        {
            p = p.next;
        }

        removedNode = p;
        p.prev.next = p.next;
        p.next.prev = p.prev;

        size--;
        modAmount++;
    }
    
    /** remove a node */
    public Object remove(ListNode p)
    {

        ListNode removedNode = p;

        p.prev.next = p.next;
        p.next.prev = p.prev;

        size--;
        modAmount++;
        
        return removedNode;
    }
    
    

    public boolean empty()
    {
        return head.next == head;
    }

    public Iterator iterator()
    {
    	if(this.size != 0)
    		return new DoubleLinkedListIterator();
    	else 
    		return null; 
    }
    
    //inner class
    private class DoubleLinkedListIterator implements Iterator
    {
        private int iteratorModAmount = modAmount;
        
        	
        ListNode current = head.next;
        
        boolean okToRemove = false;
        

        public boolean hasNext()
        {
            return (current != head );
        }

 
        public Object next()
        {
            if(modAmount != iteratorModAmount)
                throw new java.util.ConcurrentModificationException();
            if(!hasNext())
                throw new java.util.NoSuchElementException();
            
            Object p = current.getData();
            current = current.next;
            okToRemove = true;
            return p;
        }

        public void remove()
        {
            if(modAmount != iteratorModAmount)
                throw new java.util.ConcurrentModificationException();
            if(!okToRemove)
                throw new IllegalStateException();
            
            DoubleLinkedList.this.remove(current.prev);
            okToRemove = false;
            iteratorModAmount++;
            
        }
        
    }

}