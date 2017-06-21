package com.ddjf.handler;

public abstract class AbstractAsyncHandler implements Runnable {

	private Object obj;
	
	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	@Override
	public void run() {
		handle(obj);
	}

	protected abstract void handle(Object obj);
}
