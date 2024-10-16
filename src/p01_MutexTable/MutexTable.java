package p01_MutexTable;

import java.util.concurrent.Semaphore;

import p00_CommonA.Table;

public class MutexTable extends Table{

    
    /* Declare and initialize your semaphore here */
    private Semaphore mutex = new Semaphore(1);
    
    protected void gainExclusiveAccess () {
        try {mutex.acquire();} catch (InterruptedException e) {    }
    }
    
    protected void releaseExclusiveAccess() {
        mutex.release();
    }
    
    
    public void putJack(int id) {
        gainExclusiveAccess();
        while( ffs>=NUM_SLOTS ||
                (ffs==0 && id!=0)
                ||ffs==3 && id!=1) {
            releaseExclusiveAccess();
            gainExclusiveAccess();
        }
    }
    
    public void putQueen(int id) {
        gainExclusiveAccess();
        while( ffs>=NUM_SLOTS ||
                (ffs==0 && id!=0)
                ||ffs==3 && id!=1) {
            releaseExclusiveAccess();
            gainExclusiveAccess();
        }
        
    }
    
    public void putKing(int id) {
        gainExclusiveAccess();
        while( ffs>=NUM_SLOTS ||
                (ffs==0 && id!=0)
                ||ffs==3 && id!=1) {
            releaseExclusiveAccess();
            gainExclusiveAccess();
        }
        
    }

    
    public void startCheck(int id) {
        gainExclusiveAccess();
        while(ffs<NUM_SLOTS) {
            releaseExclusiveAccess();
            gainExclusiveAccess();
        }
    }

    
    public void endCheck(int id) {
        ffs=0;
        releaseExclusiveAccess();
    }

    
    public void cardPut() {
        releaseExclusiveAccess();
    }

}