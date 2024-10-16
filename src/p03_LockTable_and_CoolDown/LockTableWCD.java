package p03_LockTable_and_CoolDown;

import java.util.concurrent.locks.ReentrantLock;

import p00_CommonA.*;

public class LockTableWCD extends Table implements CoolDownSupport{
	
	private ReentrantLock lock = new ReentrantLock();
	
	/* Declare and initialize here the required simple-typed variables */
	private volatile boolean boardBusy = false;
	private volatile boolean canBeChecked=false;
	private volatile boolean isChecked=false;
	private volatile boolean wantCD=false;
	private volatile boolean goCD=false;
	
	
	protected void gainExclusiveAccess() {
		/* COMPLETE */
		lock.lock();
	}


	protected void releaseExclusiveAccess() {
		/* COMPLETE */
		lock.unlock();
	}


	public void putJack(int id) {
		/* COMPLETE */
		while(true) {
			gainExclusiveAccess();	
			if(!goCD&&!boardBusy&&!canBeChecked&&ffs!=NUM_SLOTS-1) {
				boardBusy=true;
				return;
			}
			releaseExclusiveAccess();
		}
	}

	
	public void putQueen(int id) {
		/* COMPLETE */
		while(true) {
			gainExclusiveAccess();	
			if(!goCD&&!boardBusy&&!canBeChecked&&(ffs==NUM_SLOTS-1&&id%2==0 || ffs!=NUM_SLOTS-1)) {
				boardBusy=true;
				return;
			}
			releaseExclusiveAccess();
		}
	}

	
	public void putKing(int id) {
		/* COMPLETE */
		while(true) {
			gainExclusiveAccess();	
			if(!goCD&&!boardBusy&&!canBeChecked&&ffs!=NUM_SLOTS-1 ) {
				boardBusy=true;
				if(ffs==1&&wantCD) {
					goCD=true;
				}
				return;
			}
			releaseExclusiveAccess();
		}
	}


	public void cardPut() {
		/* COMPLETE */
		boardBusy=false;
		if(ffs==NUM_SLOTS) {
			canBeChecked=true;
		}
		releaseExclusiveAccess();
	}

	
	public void startCheck(int id) {
		/* COMPLETE */
		while(true) {
			gainExclusiveAccess();	
			if(!isChecked&&canBeChecked) {
				isChecked=true;
				return;
			}
			releaseExclusiveAccess();
			
		}
	}

	
	public void endCheck(int id) {
		/* COMPLETE */
		ffs=0;
		isChecked=false;
		canBeChecked=false;
		releaseExclusiveAccess();
	}


	
	// --- IMPLEMENTATION of the CoolDownSupport interface
	
	
	public void coolDownDone() {
		/* COMPLETE */
		wantCD=false;
		goCD=false;
		boardBusy=false;
		ffs=0;
		releaseExclusiveAccess();
	}


	@Override
	public void letCoolDownRun() {
		// TODO Auto-generated method stub
		while(true) {
			gainExclusiveAccess();	
			if(goCD&&!boardBusy&&!canBeChecked) {
				boardBusy=true;
				return;
			}
			releaseExclusiveAccess();
		}
	}


	@Override
	public void coolDownIsReady() {
		// TODO Auto-generated method stub
		wantCD=true;
	}

}
