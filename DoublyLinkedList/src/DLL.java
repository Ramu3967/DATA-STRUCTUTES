import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

class Dnode
{
	int data;
	Dnode next,prev;
	Dnode(int a)
    {
        data=a;prev=null;next=null;
    }
}
class DLL
{
	static Dnode head;
	static void addLast(int d)
    {
        Dnode curr=head;
        while(curr.next!=null)
        	curr=curr.next;
        Dnode temp=new Dnode(d);
        curr.next=temp;
        temp.prev=curr;
    }
	
	static void printDLL(Dnode head)
    {
		Dnode temp=head;
        while(temp!=null)
        {
            System.out.print(temp.data+" ");
            temp=temp.next;
        }
        System.out.println();
    }
	
	Dnode findMid(Dnode n)
	{
		if(n==null)
			return n;
		Dnode s=n,f=n;
		while(f.next!=null && f.next.next!=null)
		{
			s=s.next;f=f.next.next;
		}
		return s;
	}
	
	Dnode modify(Dnode n) //Top-to-Bottom takes O(nlogn)
	{
		if(n==null || n.next==null)
			return n;
		Dnode mid=findMid(n);
		if(mid.prev!=null)
		{
			mid.prev.next=null;
		}
		if(mid.next!=null)
		{
			mid.next.prev=null;
		}
		mid.prev=modify(n);
		Dnode right=modify(mid.next);
		mid.next=right;
		return mid;
	}
	
	Dnode modify(int start,int end) //Bottom-to-Top takes O(n)
	{
		if(start>end)
			return null;
		int mid=start+(end-start)/2;
		Dnode leftchild=modify(start, mid-1);
		Dnode root=head;
		root.prev=leftchild;
		head=head.next;
		Dnode rightchild=modify(mid+1, end);
		root.next=rightchild;
		return root;
	}
	
	void lvlorder(Dnode n)
	{
		Queue<Dnode>q=new LinkedList<Dnode>();
		q.offer(n);
		int height=0,width=0;
		while(!q.isEmpty())
		{
			height++;
			int s=q.size();
			width=(s>width)?s:width;
			while(s>0)
			{
				Dnode temp=q.poll();
				System.out.print(temp.data+" ");
				if(temp.prev!=null)
					q.offer(temp.prev);
				if(temp.next!=null)
					q.offer(temp.next);
				s--;
			}
			System.out.println();
		}
		System.out.print("height="+height+" width="+width);
	}
	
	static int length(Dnode n)
	{
		Dnode temp=n;
		int c=0;
		while(temp!=null)
		{
			c++;
			temp=temp.next;
		}
		return c;
	}
	
	public static void main(String[] args) {
		DLL.head=new Dnode(1);
	     addLast(2);
	     addLast(3);
	     addLast(4);
	     addLast(5);
	     addLast(6);
	     addLast(7);
	     printDLL(head);
	     DLL dll=new DLL();
	     Dnode root=null;
//	     root=dll.modify(head);
	     root = dll.modify(0,length(head)-1);
	     dll.lvlorder(root);
	     
	}
}