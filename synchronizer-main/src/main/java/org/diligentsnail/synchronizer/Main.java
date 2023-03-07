package org.diligentsnail.synchronizer;

import java.util.Set;

@SuppressWarnings("Convert2MethodRef")
public class Main {
	public static void main(String[] args) {
		final Synchronizer synchronizer = new Synchronizer();
		final Set<Thread> threads = Set.of(
				// Создать поток, который с задержкой через Synchronizer выведет число
				new Thread(Delays.delayAnd(() -> synchronizer.acceptFirst(new PrintNumber(1)))),
				new Thread(Delays.delayAnd(() -> synchronizer.acceptSecond(new PrintNumber(2)))),
				new Thread(Delays.delayAnd(() -> synchronizer.acceptThird(new PrintNumber(3))))
		);

		threads.forEach(thread -> thread.start());

		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new UncheckedInterruptedException(e);
			}
		}
	}
}
