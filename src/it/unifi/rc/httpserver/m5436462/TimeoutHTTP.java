package it.unifi.rc.httpserver.m5436462;

public class TimeoutHTTP implements Runnable {

	private WorkerRunnable runnable;
	private boolean check;

	public TimeoutHTTP(WorkerRunnable runnable) {
		this.runnable = runnable;
		this.check = true;
	}

	public synchronized void setCheck(boolean value) {
		this.check = value;
		System.out.println("Non ha più effetto il thread HTTPTimeout: " + this.toString());
		System.out.print("Il valore deve essere false di check: "+this.check);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			System.out.println("Avviato :" + this.toString());
			Thread.sleep(30 * 1000);
			if (check) {
				this.runnable.setTimeoutVariable(true);
				throw new Exception("Timed out from thread "+this.toString()+" Socket closed for Client. Close Thread: "+this.runnable.toString());
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
