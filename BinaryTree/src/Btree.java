import java.util.*;
//import java.util.concurrent.atomic.AtomicReference;

	class Node{
		int data;
		Node left,right;
		Node(int d)
		{
			data=d;
			left=null;
			right=null;
		}
	}


public class Btree {
	static int preindex=0;
	void preorder(Node root)
	{
		Stack<Node>s=new Stack<>();
		Node p=root;
		while(true)
		{
			while (p!=null) {
				System.out.print(p.data+" ");
				s.push(p);
				p=p.left;
			}
			if(s.empty())
				break;
			p=s.pop();
			p=p.right;
		}
	}
	
	void preorder1(Node root) //preorder with previous
	{
		Stack<Node>s=new Stack<>();
		Node prev=null;
		Node p=root;
		while(true)
		{
			while (p!=null) {
				if(prev!=null)
			    {
					System.out.print(p.data+"-->"+prev.data+" ");
					System.out.println();
			    }
				else
				{
					System.out.print(p.data+"-->NULL ");
					System.out.println();
				}
				s.push(p);
				prev=p;
				p=p.left;
			}
			if(s.empty())
				break;
			p=s.pop();	
			p=p.right;
		}
	}
	
	void inorder(Node root)
	{
		Stack<Node>s=new Stack<>();
		Node p=root;
		while (true) {
			while(p!=null) {
				s.push(p);
				p=p.left;
			}
			if(s.empty())
				break;
			p=s.pop();
			System.out.print(p.data+" ");
			p=p.right;
		}
	}
	
	void inorderwithprev(Node n)
	{
		Stack<Node>s=new Stack<>();
		Node p=n,prev=null;
		System.out.println();
		while (true)
		{
			while(p!=null) 
			{
				s.push(p);
				p=p.left;
			}
			if(s.empty())
				break;
			p=s.pop();
			if(prev!=null)
		    {
				System.out.print(p.data+"-->"+prev.data+" ");
				System.out.println();
		    }
			else
			{
				System.out.print(p.data+"-->NULL ");
				System.out.println();
			}
			prev=p; 
			p=p.right;
		}
	}
	
	Node createBT(int[]in,int[]pre)// used to create Btree from in[] and pre[]
	{
		if((in.length==0)||(in.length!=pre.length))
			return null;
		return createBtreeUtil(in,0,in.length-1,pre);
	}
	
	int search(int[]a,int start,int end,int value)//instead use binary search
	{
		int i=start;
		for(;i<=end;i++)
			if(a[i]==value)
				break;
		return i;
	}
	
	Node createBtreeUtil(int[]in,int is,int ie,int[] pre )
	{
		Node temp=new Node(pre[preindex++]);
		if(is>ie)
			return null;
		if(is==ie)
			return temp;
		int k=search(in, is, ie, temp.data);
		temp.left=createBtreeUtil(in, is, k-1, pre);
		temp.right=createBtreeUtil(in, k+1, ie, pre);
		return temp;
	}
	/*special tree : 
	 * https://www.geeksforgeeks.org/construct-a-special-tree-from-given-preorder-traversal/
	 * Node constructTreeUtil(int pre[], char preLN[], Index index_ptr, 
													int n) 
	{ 
		// store the current value of index in pre[] 
		int index = index_ptr.index; 

		// Base Case: All nodes are constructed 
		if (index == n) 
			return null; 

		// Allocate memory for this node and increment index for 
		// subsequent recursive calls 
		Node temp = new Node(pre[index]); 
		(index_ptr.index)++; 

		// If this is an internal node, construct left and right subtrees 
		// and link the subtrees 
		if (preLN[index] == 'N') 
		{ 
			temp.left = constructTreeUtil(pre, preLN, index_ptr, n); 
			temp.right = constructTreeUtil(pre, preLN, index_ptr, n); 
		} 

		return temp; 
	} 

	// A wrapper over constructTreeUtil() 
	Node constructTree(int pre[], char preLN[], int n) 
	{ 
		// Initialize index as 0. Value of index is used in recursion to 
		// maintain the current index in pre[] and preLN[] arrays. 
		//int index = 0; 
		Index myindex = new Index(); 
		return constructTreeUtil(pre, preLN, myindex, n); 
	} 

	 * 
	 * */
	static Node previous=null,head=null; //instead of using these static variables
	Node Btree2DLL(Node n)				 //follow method-2
	{ //method-1
		if(n==null)
			return n;
		Btree2DLL(n.left);
		if(previous==null)
		{
			head=n;
		}
		else
		{
			previous.right=n;
			n.left=previous;
		}
		previous=n;
		Btree2DLL(n.right);
		return head;
	}
	
	//method-2
	class PrevandHead
	{
		Node HEADOFDLL,PREV;
	}
	
	Node B2DLL(Node n)
	{
		PrevandHead p=new PrevandHead();
		return Btree2DLL(n,p);	
	}
	
	Node Btree2DLL(Node n,PrevandHead p)
	{	
		if(n==null)
			return n;
		Btree2DLL(n.left,p);
		if(p.PREV==null)
		{
			p.HEADOFDLL=n;
			//System.out.println(headofDLL.data);
		}
		else
		{
			p.PREV.right=n;
			n.left=p.PREV;
		}
		p.PREV=n;
		Btree2DLL(n.right,p);
		return p.HEADOFDLL;
	}
	
	//method-3 (iterative)
	Node b2dll(Node n)
	{
		if(n==null || (n.left==null && n.right==null))
			return n;
		Stack<Node>s=new Stack<>();
		Node p=n,prev=null,headofdll=null;
		System.out.println();
		while (true)
		{
			while(p!=null) 
			{
				s.push(p);
				p=p.left;
			}
			if(s.empty())
				break;
			p=s.pop();
			if(prev==null)
		    {
				headofdll=p;
				System.out.print(p.data+"-->null ");
				System.out.println();
		    }
			else
			{
				prev.right=p;
				p.left=prev;
				System.out.print(p.data+"-->"+prev.data+" ");
				System.out.println();
			}
			prev=p; 
			p=p.right;
		}
		return headofdll;
	}
	
	
	/*
	 * Node b2DLL(Node n)
    {
        return btree2DLL(n, new AtomicReference<>(), new AtomicReference<>());
    }

    Node btree2DLL(Node n, AtomicReference<Node> previous, AtomicReference<Node> head)
    {
        if(n == null)
            return n;
        btree2DLL(n.left, previous, head);
        if(previous.get() == null)
        {
            head.set(n);
        }
        else
        {
            previous.get().right = n;
            n.left = previous.get();
        }
        previous.set(n);
        btree2DLL(n.right, previous, head);
        return head.get();
}
*/
	int heightofBtree(Node n)
	{
		if(n==null)
			return 0;
		int left=heightofBtree(n.left);
		int right=heightofBtree(n.right);
		return Math.max(left,right)+1;
	}
	
	void lvlorder(Node n)
	{
		Queue<Node>q=new LinkedList<Node>();
		q.offer(n);
		int height=0,width=0;
		while(!q.isEmpty())
		{
			height++;
			int s=q.size();
			width=(s>width)?s:width;
			while(s>0)
			{
				Node temp=q.poll();
				System.out.print(temp.data+" ");
				if(temp.left!=null)
					q.offer(temp.left);
				if(temp.right!=null)
					q.offer(temp.right);
				s--;
			}
			System.out.println();
		}
		System.out.print("height="+height+" width="+width);
	}
	
	void lvlorderRecur(Node n)
	{
		int h=heightofBtree(n);
		for(int i=1;i<=h;i++)
		{
			printLevel(n, i);
			System.out.println();
		}
	}
	
	void lvlorderWithNulls(Node n)
	{
		Queue<Node>q = new LinkedList<Node>();
		q.offer(n);
		q.offer(null);
		while(q.isEmpty()==false)
		{
			Node temp=q.poll();
			if(temp==null)
			{
				System.out.println();
				if(q.peek()!=null)
					q.offer(null);
			}
			else
			{
				System.out.print(temp.data+" ");
				if(temp.left!=null)
					q.offer(temp.left);
				if(temp.right!=null)
					q.offer(temp.right);
			}
		}
	}
	
	
	
	void printStack(Stack<Node>s) //to print stack from bottom to top
	{
		if(s.empty())
			return;
		Node temp=s.pop();
		printStack(s);
		s.push(temp);
		System.out.print(temp.data+" ");
	}
	
	void printDLL(Node n)
	{
		Node temp=n;
		while(temp!=null)
		{
			System.out.print(temp.data+" ");
			temp=temp.right;
		}
	}
	
	void printLevel(Node n,int l)
	{
		if(l==1)
			{
				System.out.print(n.data+" ");
				return;
			}
		printLevel(n.left, l-1);
		printLevel(n.right, l-1);
	}
	//similar to above, we can find width at a level
	int width(Node n,int level)
	{
		if(n==null)
			return 0;
		else
		{
			if(level==0)
				return 1;
			else
				return width(n.left,level-1)+width(n.right, level-1);
		}
	}
	
	int postorderWithSinglePushToStack(Node n) // returns height
	{
		int height=0;
	    Stack<Node>s=new Stack<Node>();
	    Node p=n,temp=null;
	    while(true)
	    {
	        while(p!=null)
	        {
	            s.push(p);
	            p=p.left;
	        }
	        if(s.empty())
	        	break;
	        //to calculate height via postorder count nodes in stack
	        height=Math.max(height, s.size());
	        
	        temp=s.peek().right;//crucial
	        if(temp==null) //steps
	        {
	        	temp=s.pop();
	        	System.out.print(temp.data+" ");
	        	while(!s.empty() && temp==s.peek().right)
	        	{
	        		temp=s.pop();
	        		System.out.print(temp.data+" ");
	        	}
	        }
	        else
	        	p=temp;
	    }
	    return height;
	}
	
	int postorderWithSinglePushToStack(Node n) // postorder with prev
	{
		int height=0;
	    Stack<Node>s=new Stack<Node>();
	    Node p=n,temp=null,prev=null;
	    while(true)
	    {
	        while(p!=null)
	        {
	            s.push(p);
	            p=p.left;
	        }
	        if(s.empty())
	        	break;
	        //to calculate height via postorder count nodes in stack
	        height=Math.max(height, s.size());
	        
	        temp=s.peek().right;//crucial
	        if(temp==null) //steps
	             {
	                 
	        	    temp=s.pop();
	        	  //  System.out.print(temp.data+" ");
	        	    if(prev!=null)
		            {
				        System.out.print(temp.data+"-->"+prev.data+" ");
				        System.out.println();
		            }
			        else
			        {
				        System.out.print(temp.data+"-->NULL ");
				        System.out.println();
			        }
			        prev=temp;
	        	    while(!s.empty() && temp==s.peek().right)
	        	    {
    	        		temp=s.pop();
	        		    if(prev!=null)
		            {
				        System.out.print(temp.data+"-->"+prev.data+" ");
				        System.out.println();
		            }
			        else
			        {
				        System.out.print(temp.data+"-->NULL ");
				        System.out.println();
			        }
	        		    prev=temp;
	        	    }
	            }
	            else
    	        	p=temp;
	            }
	    return height;
	}
	
	int diameter(Node n) //O(n^2)
	{
		if(n==null)
			return 0;
		int lh=heightofBtree(n.left),rh=heightofBtree(n.right);
		return Math.max(lh+rh+1,Math.max(diameter(n.left), diameter(n.right)));
	}
	
	class Height
	{
		int h;
	}
	int diameter1(Node n)
	{
		Height height=new Height();
		return diameter1(n, height);
	}
	int diameter1(Node n,Height height) //O(n)
	{
		if(n==null)
		{
			height.h=0;
			return 0;
		}	
		Height lh=new Height(),rh=new Height();
		int ld=diameter1(n.left,lh),rd=diameter1(n.right,rh);
		height.h=1+Math.max(lh.h,rh.h);
		return Math.max(1+lh.h+rh.h,Math.max(ld,rd));
	}
	
	void rootToLeafPath(Node n) // used postorder
	{
		System.out.println("Root to leaf paths:");
	    Stack<Node>s=new Stack<Node>(); 
	    Node p=n,temp=null;
	    while(true)
	    {
	        while(p!=null)
	        {
	            s.push(p);
	            p=p.left;
	        }
	        if(s.empty())
	        	break;
	        temp=s.peek().right;//crucial
	        if(temp==null) //steps
	        {
	        	temp=s.peek();
	        	if(temp.left==null && temp.right==null)
	        		{
	        			printStack(s);
	        			System.out.println();
	        		}
	        	temp=s.pop();
	        	//System.out.print(temp.data+" ");
	        	while(!s.empty() && temp==s.peek().right)
	        	{
	        		temp=s.peek();
	        		if(temp.left==null && temp.right==null)
	        		{
	        			printStack(s);
	        			System.out.println();
	        		}
	        		temp=s.pop();
	        	//System.out.print(temp.data+" ");
	        	}
	        }
	        else
	        	p=temp;
	    }
	}
	
	void printAncestors(Node n,int key) //used postorder
	{
		System.out.println("\nAncestors of "+key+" are:");
		Stack<Node>s=new Stack<Node>();
		Node p=n;
		while(true)
		{
			while(p!=null && p.data	!=key)
			{
				s.push(p);
				p=p.left;
			}
			if(p!=null && p.data==key)
				break;
			if(s.empty())
				break;
			Node temp=s.peek().right;
			if(temp!=null)
				p=temp;
			else
			{
				temp=s.pop();
				//print data if u want post-order
				while(!s.empty()&&temp==s.peek().right)
				{
					temp=s.pop();
					//print data if u want post-order
				}
			}
		}
		if(s.empty() && key!=n.data)
			System.out.println("none because "+key+" is not in the tree");
		if(key==n.data)
			System.out.println("none because "+key+" is root of the tree");
		printStack(s);
	}
	
	class Pair
	{
		Node n;
		int hd;
		Pair(Node a,int b)
		{
			n=a;hd=b;
		}
	}
	void verticalOrder(Node n)//use level order traversal with Map
	{
		System.out.println("\nVertical Order Traversal:");
		Map<Integer,ArrayList<Node>>m = new TreeMap<>();
		Queue<Pair>q=new LinkedList<>();
		q.offer(new Pair(n,0));
		ArrayList<Node>al;
		while(!q.isEmpty())
		{
			Pair tempPair=q.poll();
			int hd=tempPair.hd;
			Node temp=tempPair.n;
			if(temp!=null)
			{
				if(temp.left!=null)
				{
					q.offer(new Pair(temp.left, hd-1));
				}
				if(temp.right!=null)
				{
					q.offer(new Pair(temp.right, hd+1));
				}
			}
			al=m.get(hd);
			if(al==null)
			{
				al=new ArrayList<>();
				al.add(temp);
			}
			else
			{
				al.add(temp);
			}
			m.put(hd, al);
		}
		for(Map.Entry<Integer, ArrayList<Node>>k: m.entrySet())
		{
			for(Node x:k.getValue())
			{
				System.out.print(x.data+" ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		Node r=new Node(1);
		r.left=new Node(2);
		r.right=new Node(3);
		r.left.left=new Node(4);
		r.left.right=new Node(5);
		r.right.left=new Node(6);
		r.right.right=new Node(7);
		Btree b=new Btree();
//		b.lvlorder(r);
//		int[]in= {4,2,5,1,6,3,7};
//		int[]pre= {1,2,4,5,3,6,7};
//		Node k=b.createBT(in,pre);
//		b.preorder(k);

// 		Node o=null;
//		o=b.b2dll(r); //use this method for the use of static variables else
		//use below method
		//o=b.B2DLL(r);
//		b.printDLL(o);	
		//b.lvlorderRecur(r);
		//b.lvlorderWithNulls(r);
//		int h=b.postorderWithSinglePushToStack(r);
//		System.out.println(b.diameter1(r));
//		System.out.println(b.width(r, 0));
		System.out.println();
		b.rootToLeafPath(r);
		b.printAncestors(r,1);
		b.verticalOrder(r);
	}
}
