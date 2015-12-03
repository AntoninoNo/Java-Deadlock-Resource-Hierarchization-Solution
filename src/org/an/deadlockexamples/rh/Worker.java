package org.an.deadlockexamples.rh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


public class Worker extends Thread{

	static int var=0;
	int id;
	ArrayList<Resource> r_arr;
	ArrayList<Integer> r_owned;
	ArrayList<Integer> r_wanted;
	int last_ind;

	public Worker(ArrayList<Resource> r_arr){
		this.r_arr=r_arr;
		id=var++;

	}

	public void run()
	{
		int turn=0;
		while(turn<10)
		{
			r_owned=new ArrayList<Integer>();
			r_wanted=new ArrayList<Integer>();
			last_ind=-1;
			int c=0;

			while(c<3)
			{
				

				int ind=getR();
				
				System.out.println(id+" wants another resource "+ind);
				if(ind<last_ind){

					for (Iterator<Integer> iterator = r_owned.iterator(); iterator.hasNext(); ) {
						Integer already= iterator.next();
						if(already > ind){
							r_arr.get(already).release(id);
							iterator.remove();
							r_wanted.add(already);
//							System.out.println(id+" releases "+already);
						}
					}				
				}
				r_wanted.add(ind);
				acquire_resources();
				c++;
				try{
					sleep((int)(Math.random()*1000));
				}catch(Exception e){}
			}
			try{
				sleep((int)(Math.random()*2000));
			}catch(Exception e){}
			releaseAll();
			System.out.println(id+" done "+turn++);
			
		}
	}

	private void releaseAll() {
		// TODO Auto-generated method stub
		
		for(Integer i: r_owned)
			r_arr.get(i).release(id);
		
	}

	private void acquire_resources() {
		// TODO Auto-generated method stub
		
		Collections.sort(r_wanted);
		for(Integer i: r_wanted){
			r_arr.get(i).acquire(id);
			r_owned.add(i);
			last_ind=i;
//			System.out.println(id+" acquires "+i);
		}
		r_wanted=new ArrayList<Integer>();
		
	}

	private int getR() {
		// TODO Auto-generated method stub
		int ind;
		do{
			ind=(int)(Math.random()*r_arr.size());
		}while(r_owned.contains(ind));
		
		return ind;
	}
	
	public static void main(String[] args) {
		
		ArrayList<Resource> resources=new ArrayList<Resource>();
		for(int i=0;i<5;i++)
			resources.add(new Resource(i));
		
		for(int i=0;i<100;i++)
			(new Worker(resources)).start();
			
	}

}