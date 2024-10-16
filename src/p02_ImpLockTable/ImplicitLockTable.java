package p02_ImpLockTable;

import p00_CommonA.Table;

public class ImplicitLockTable extends Table{
	
	/* Declare and initialize tour simple-typed variables here */
	private final int WRITE_CARD=1;
	private final int CHECK=2;
	private final int WAITING=3;
	private volatile int state=WRITE_CARD;
	private volatile char lastFirstCard='j';
	

	protected void gainExclusiveAccess() {
		/* COMPLETE */
		state=WAITING;
	}


	protected void releaseExclusiveAccess() {
		/* COMPLETE */
		if(ffs==NUM_SLOTS)state=CHECK;
		else state=WRITE_CARD;
	}


	public void putJack(int id) {
		/* COMPLETE */
		
		boolean goAhead = false;
    	while (!goAhead) {
    		synchronized (this) {
    			if (state==WRITE_CARD&&(ffs==3&&lastFirstCard=='j'||ffs!=3)) {
    				gainExclusiveAccess();
    				if(ffs==0)lastFirstCard='j';
    				goAhead = true;
    			}
    		}
    	}
	}

	
	public void putQueen(int id) {
		/* COMPLETE */
		boolean goAhead = false;
    	while (!goAhead) {
    		synchronized (this) {
    			if (state==WRITE_CARD&&(ffs==3&&lastFirstCard=='q'||ffs!=3)) {
    				gainExclusiveAccess();
    				if(ffs==0)lastFirstCard='q';
    				goAhead = true;
    			}
    		}
    	}
	}

	
	public void putKing(int id) {
		/* COMPLETE */
		boolean goAhead = false;
    	while (!goAhead) {
    		synchronized (this) {
    			if (state==WRITE_CARD&&(ffs==3&&lastFirstCard=='k'||ffs!=3)) {
    				gainExclusiveAccess();
    				if(ffs==0)lastFirstCard='k';
    				goAhead = true;
    			}
    		}
    	}
	}


	public void cardPut() {
		/* COMPLETE */
		releaseExclusiveAccess();
	}

	
	public void startCheck(int id) {
		/* COMPLETE */
		boolean goAhead = false;
    	while (!goAhead) {
    		synchronized (this) {
    			if (state==CHECK) {
    				gainExclusiveAccess();
    				goAhead = true;
    			}
    		}
    	}
	}

	
	public void endCheck(int id) {
		/* COMPLETE */
		ffs=0;
		releaseExclusiveAccess();
	}

}
