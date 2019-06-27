import java.util.*;

class Demo
{
	public static void main(String[] args) {
		ArrayList<Integer>sharedList =new ArrayList<Integer>();
		Thread P=new Thread(new Producer(sharedList),"producer-thread");
		Thread C=new Thread(new Consumer(sharedList),"consumer-thread");
		P.start();
		C.start();
	}
}

class Producer implements Runnable
{
	ArrayList<Integer>sharedList=null;
	public Producer(ArrayList<Integer>a) {
		// TODO Auto-generated constructor stub
		//super();
		this.sharedList=a;
	}
	final int MAX=10;
	int i=0;
	@Override
	public void run() {
		for(int c=0;c<MAX;c++)
			try {
				produce(i++);    //JUST using the below method here
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	void produce(int i) throws InterruptedException
	{
		synchronized (sharedList) {
			while(sharedList.size()==MAX)	//When shared_list if full Producer must wait
			{
				System.out.println("Producer is waiting for the consumer to consume");
				sharedList.wait();
			}
		synchronized (sharedList) {
			sharedList.add(i);
			System.out.println("Produced "+i);
			Thread.sleep(750);
			System.out.println("Produced item....Notifying the Consumer");
			sharedList.notify();
		}	
		}
	}
	
}

class Consumer implements Runnable
{
	ArrayList<Integer>sharedList=null;
	public Consumer(ArrayList<Integer>b) {
		// TODO Auto-generated constructor stub
		//super();
		this.sharedList=b;
	}
	
	@Override
	public void run() {
		for(int c=5;c>0;c--)
		{
			// TODO Auto-generated method stub
			try {
				consume();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	void consume() throws InterruptedException
	{
		synchronized (sharedList) {
			while(sharedList.isEmpty())
			{
				System.out.println("Consumer is waiting for the Producer to Produce");
				sharedList.wait();
			}
		synchronized (sharedList) {
			Thread.sleep(750);
			System.out.println("Consumed "+sharedList.remove(0));
			System.out.println("Consumed item....Notifying the Producer");
			sharedList.notify();
		}	
		}
	}
	
}


/*
class A implements Runnable
{
	public void run()
	{
		for(int i =0;i<5;i++)
		{
			System.out.println("Hello");
			try{Thread.sleep(1000);}catch(Exception e){}
		}	
	}
}
class Demo
{
	public static void main(String[] args) {
		A a=new A();  //Thread 1
		
		Runnable b=new Runnable() { //Thread 2 which Anonymous Inner Class
			
			@Override
			public void run() {
				for(int i =0;i<5;i++)
				{
					System.out.println("Hi");
					try{Thread.sleep(1000);}catch(Exception e){}
				}	
				
			}
		};
		
		Runnable c=()->{ //Thread 3 using Lambda expression 
				for(int i =0;i<5;i++)
				{
					System.out.println("Hey");
					try{Thread.sleep(1000);}catch(Exception e){}
				}		
		};
		
		Thread t1=new Thread(a);
		t1.setName("Hello Thread");
		Thread t2=new Thread(b,"Hi Thread");
		Thread t3=new Thread(c);
		t1.start();
		
		t2.start();t3.start();try {
			t1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Main has ended");
	}
}
*/