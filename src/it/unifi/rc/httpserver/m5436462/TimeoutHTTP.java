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
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			while (check) {
			
			}
			try {
				if (this.runnable.getTimeoutVar()==true) {
					break;
				}
				System.out.println("Avviato il timer :" + this.toString()+" per il Client gestito dal Thread: "+this.runnable.toString());
				Thread.sleep(120 * 1000);
				this.runnable.setTimeoutVariable(true);
				throw new Exception("Timed out from thread " + this.toString()
						+ " Socket closed for Client. Close Thread: " + this.runnable.toString()+"\r\n");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
		}
	}
}
