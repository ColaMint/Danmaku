package com.danmaku.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.danmaku.state.StateManager;

public class Painter extends StateBlockedThread implements PaintProcessManager {

	private static final int PAINT_INTERVAL = 30;
	private List<PaintProcess> paintProcesses = new ArrayList<PaintProcess>();
	private ExecutorService exector = new ThreadPoolExecutor(1, 4, 30, TimeUnit.SECONDS,
			new LinkedBlockingQueue<Runnable>());

	public Painter(StateManager stateManager) {
		super(stateManager);
	}

	protected void loop() {
		try {
			for (final PaintProcess paintProcess : paintProcesses) {
				exector.submit(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						paintProcess.onPaint();
					}

				});
			}
			sleep(PAINT_INTERVAL);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void addPaintProcess(PaintProcess p) {
		paintProcesses.add(p);
	}

	@Override
	public void removePaintProcess(PaintProcess p) {
		paintProcesses.remove(p);
	}
}
