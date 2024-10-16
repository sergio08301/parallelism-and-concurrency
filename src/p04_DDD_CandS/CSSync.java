package p04_DDD_CandS;

import java.util.concurrent.atomic.*;

import p00_CommonB.*;

public class CSSync implements InterfaceSync {
	
	/* Declare and initialize your single instance of AtomicBoolean */
	AtomicInteger state = new AtomicInteger(DINGorDONG);
	private static final int DINGorDONG = 1;
	private static final int DINGorDANG = 2;
	private static final int ONLYDONG = 3;
	private static final int ONLYDANG = 4;
	private static final int WRITING = 5;
	/* Declare and initialize all the simple-typed variables required */
	private volatile int dingCounter=0;
	private volatile int dangCounter=0;
	private volatile int nextNeededId=0;
	
	private final int NUMINSTANCES; 
	
	public CSSync (int numinstances) {
		this.NUMINSTANCES = numinstances;
	}
	
	public void letMeDing(int id) {
		/* COMPLETE */
		while(id!=nextNeededId
				||
				!(state.compareAndSet(DINGorDONG, WRITING) || state.compareAndSet(DINGorDANG, WRITING))){backOff();}
		nextNeededId = (nextNeededId+1)%NUMINSTANCES;
	}

	public void dingDone(int id) {
		/* COMPLETE */
		dingCounter++;
		
		if(dingCounter==3)state.set(ONLYDANG);
		else {
			state.set(DINGorDANG);
			
		}
		
	}


	public void letMeDang(int id) {
		/* COMPLETE */
		while(id!=nextNeededId
				||
				!(state.compareAndSet(ONLYDANG, WRITING)||state.compareAndSet(DINGorDANG, WRITING))){backOff();}
		nextNeededId = (nextNeededId+1)%NUMINSTANCES;
	}


	public void dangDone() {
		/* COMPLETE */
		dangCounter++;
		
		if(dangCounter<dingCounter)state.set(ONLYDANG);
		else state.set(ONLYDONG);
		
	}

	
	public void letMeDong(int id) {
		/* COMPLETE */
		while(id!=nextNeededId
				||
				!(state.compareAndSet(ONLYDONG, WRITING)||state.compareAndSet(DINGorDONG, WRITING))){backOff();}
		nextNeededId = (nextNeededId+1)%NUMINSTANCES;
	}

	
	public void dongDone() {
		/* COMPLETE */
		
		dingCounter=0;
		dangCounter=0;
		state.set(DINGorDONG);
	}

	// use this method instead of Thread.yield()
	public void backOff () {
		try {Thread.sleep(0,1);} catch (InterruptedException ie) {}
	}
	
}
