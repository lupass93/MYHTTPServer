package it.unifi.rc.httpserver.m5436462;

public class TimeoutHTTP implements Runnable {
	
	
	private WorkerRunnable runnable;
	private boolean check = true;
	
	
	public TimeoutHTTP(WorkerRunnable runnable) {
		this.runnable = runnable;
	}
	
	
	public synchronized void setCheck(boolean value) {
		System.out.println("Non ha più effetto il thread HTTPTimeout: "+this.toString());
		this.check=value;
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			System.out.println("Avviato :"+this.toString());
			Thread.sleep(30 * 1000);
			if (check) {
			this.runnable.setTimeoutVariable(true);
			throw new Exception("Timed out: Socket closed for Client.");
			} 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
