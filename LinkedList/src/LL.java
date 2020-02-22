class Node
{
	int data;
	Node next;
	Node(int a)
    {
        data=a;next=null;
    }
}
class LL
{
	static Node head;
	static void printLL(Node head)
    {
        while(head!=null)
        {
            System.out.print(head.data+" ");
            head=head.next;
        }
        System.out.println();
    }
	static void addLast(Node head,int d)
    {
        Node curr=head;
        while(curr.next!=null)
        curr=curr.next;
        curr.next=new Node(d);
    }
	Node reverse(Node CURRENT,Node PREVIOUS)
	{
		if(CURRENT.next==null)
		{
			head=CURRENT;
			CURRENT.next=PREVIOUS;
			return head;
		}
		Node next=CURRENT.next;
		CURRENT.next=PREVIOUS;
		reverse(next, CURRENT);
		return head;
	}
	Node reverseinK(Node n,int k) //recursive
	{
		if(n==null || n.next==null)
			return n;
		Node curr=n,prev=null,next=null;
		int c=0;
		while(curr!=null && c<k)
		{
			next=curr.next;
			curr.next=prev;
			prev=curr;
			curr=next;
			c++;
		}
		n.next=reverseinK(curr,k);
		return prev;
	}
	Node reverseinK1(Node n,int k) //iterative
	{
		if(n==null || n.next==null)
			return n;
		Node curr=n;
		Node prevTail=null,prevCurrent=n;
		while(curr!=null)
		{
			int c=0;
			Node prev=null,next=null;
			while(curr!=null && c<k)
			{
				next=curr.next;
				curr.next=prev;
				prev=curr;
				curr=next;
				c++;
			}
			//we've reversed first k numbers,so the kth element(which is pointed by prev at that instance)
			// would be the head of the final list
			if(prevTail==null)
			{
				n=prev;
			}
			else
			{
				prevTail.next=prev;
			}
			prevTail=prevCurrent;//after reversing k nodes, previous current would become 
			//tail, so prevtail=prevcurrent
			prevCurrent=curr;
		}
		return n;
	}
	Node findMid(Node n)
	{
		Node s=n,f=n;
		while(f.next!=null && f.next.next!=null)
		{
			s=s.next;f=f.next.next;
		}
		return s;
	}
	void isPalin(Node n) //for a palindrome check, n must be an odd length list(131,12321 etc),therefore
	{					//this code works for odd length lists only
		Node mid=findMid(n);
		boolean flag=true;
		Node x=mid.next;
		mid.next=null;
		Node x1=reverse(x, null);
		Node temp1=n,temp2=x1;
		while(temp1!=mid && temp2!=null && flag)
		{
			if (temp1.data==temp2.data) {
				temp1=temp1.next;temp2=temp2.next;
			}
			else 
				flag=false;
		}
		//attaching to get the original list
		x=reverse(x1, null);x1=null;
		mid.next=x;
		head=n;
		System.out.println(flag);
	}
	void L1LnL2Ln_1L3Ln_2(Node head)//reverse the 2nd half n join alternatively
	{
		Node n=head;
		Node mid=findMid(n);
		Node x=mid.next;
		mid.next=null;
		Node nrev=reverse(x, null);
		while(n!=null && nrev!=null)
		{
			Node nextofN=n.next;
			Node nextofNrev=nrev.next;
			n.next=nrev;
			nrev.next=nextofN;
			n=nextofN;
			nrev=nextofNrev;
		}
		printLL(head);
	}
	
	void addAsHead(Node headOfTheList,Node toBeAdded,Node prev)
	{
		if(prev==null)
			return;
		prev.next=toBeAdded.next;
		toBeAdded.next=headOfTheList;
		headOfTheList=toBeAdded;
	}
	
	void cutPaste(Node nodeToBeCut,Node prev,Node Destination)
	{
		if(prev==null)
			return;
		if(prev==Destination)
			return;			
		Node temp=Destination.next;
		prev.next=nodeToBeCut.next;
		Destination.next=nodeToBeCut;
		nodeToBeCut.next=temp;
	}
	
	int findMin(Node n)
	{
		int min=Integer.MAX_VALUE;
		for(Node temp=n;temp!=null;temp=temp.next)
		{
			min=(temp.data<min)?temp.data:min;
		}
		return min;
	}	
	
	//Place nodes such that nodes <k come before nodes >=k maintaining the order
	Node placeNodes(Node n, int k)
	{
		if(k<=findMin(n))
			return n;
		Node prev=null,smallthanK=null,newHead=null;
		Node curr=n;
		while(curr!=null)
		{
			if(curr.data>=k)
			{
				prev=curr; //update prev b4 changing curr
				curr=curr.next;
			}
			else
			{
				if(smallthanK == null) //found first element smaller than k
				{
					smallthanK=curr;
					newHead=smallthanK;
					
					//adding first node smaller than K as the head node
					//because smaller elements should be first.
					addAsHead(n, smallthanK, prev);
				}
				else//if you already put smaller element than k at the start
				{
					cutPaste(curr, prev, smallthanK);
					smallthanK=smallthanK.next;
				}
				prev=curr; // update prev b4 changing curr
				curr=prev.next;
			}
		}
		return newHead;
	}
	
	//Add two linked lists(recursion)
	Node tempNodeInFirstList;
	
	class Result
	{
		Node result;
	}
	
	int carry=0;
	
	void sumWithSameSizes(Node l1,Node l2,Result R)
	{
		if(l1==null)
			return;
		sumWithSameSizes(l1.next, l2.next,R);
		int sum=l1.data+l2.data+carry;
		carry=sum/10;
		sum%=10;
		if(R.result==null)
		{
			R.result=new Node(sum);
		}
		else
		{
			Node temp=new Node(sum);
			temp.next=R.result;
			R.result=temp;
		}
	}
	
	void addRestSum(Node n,Result R)
	{
		if(n!=tempNodeInFirstList)
		{
			addRestSum(n.next,R);
			int sum=n.data+carry;
			carry=sum/10;
			sum%=10;
			//adding this sum to result list
			Node temp=new Node(sum);
			temp.next=R.result;
			R.result=temp;
		}
	}
	
	int getSize(Node n)
	{
		int i=0;
		for(Node temp=n;temp!=null;temp=temp.next)
			i++;
		return i;
	}
	//main method for adding 2 lists
	void addLists(Node l1,Node l2)
	{
		Result R=new Result();
		addLists(l1,l2,R);
	}
	
	void addLists(Node l1,Node l2,Result R)
	{
		//get the sizes of 2 lists
		int Size_l1=getSize(l1),Size_l2=getSize(l2);
		if(Size_l1==Size_l2)
			sumWithSameSizes(l1, l2,R);
		else //not of same sizes
		{
			if(Size_l1 < Size_l2)//making sure l1 is always bigger list
			{
				Node temp=l1;
				l1=l2;
				l2=temp;
			}
			int diff=Math.abs(Size_l2 - Size_l1);
			tempNodeInFirstList=l1;//hence linking with bigger list
			while(diff>0)
			{
				tempNodeInFirstList=tempNodeInFirstList.next;
				diff--;
			}
			//System.out.println("this is temp "+tempNodeInFirstList.data);
			sumWithSameSizes(tempNodeInFirstList, l2, R);
			addRestSum(l1,R);
		}
		if(carry>0)
		{
			Node temp=new Node(carry);
			temp.next=R.result;
			R.result=temp;
		}
		printLL(R.result);
	}
	
	public static void main(String args[]) {
	     LL.head=new Node(8);
	     addLast(head,6);
	     addLast(head,7);
	     addLast(head,3);
	     addLast(head,2);
	     //addLast(head,2);
	    // printLL(head);
	     LL ll=new LL();
//	     ll.isPalin(head);
//	     Node changedhead=ll.reverseinK(head, 4);
//	     printLL(changedhead);
//	     changedhead=ll.reverseinK1(changedhead, 4);
//	     printLL(changedhead);
//	     ll.L1LnL2Ln_1L3Ln_2(head);
	     Node head2=ll.placeNodes(head, 100);
	     printLL(head2);
	     Node l1=new Node(8);
	     l1.next=new Node(0);
	     l1.next.next=new Node(2);
	     l1.next.next.next=new Node(4);
	     l1.next.next.next.next=new Node(5);
	     
	     Node l2=new Node(3);
	     l2.next=new Node(6);
	     l2.next.next=new Node(7);
	     ll.addLists(l1, l2);
	     }
	
}
