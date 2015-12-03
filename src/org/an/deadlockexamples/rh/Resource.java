package org.an.deadlockexamples.rh;

public class Resource {

	private int r_id;
	private int owner_id;
	private boolean lock;

	public Resource(int r_id){

		this.r_id=r_id;
		owner_id=-1;
		lock=false;
	}

	public synchronized void acquire(int id) {
		// TODO Auto-generated method stub

		if(lock)
			try{
				wait();
			}catch(Exception e){}

		lock=true;
		owner_id=id;
		System.out.println(id+" acquires "+r_id);

	}


	public synchronized void release(int id) {
		// TODO Auto-generated method stub

		if(owner_id==id){
			lock=false;
			System.out.println(id+" releases "+r_id);
			notify();
		}

	}


}
