package org.diligentsnail.synchronizer;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SynchronizerTest {
	private static class CollectTurnsRunnable implements Runnable {
		private final List<Integer> turns;
		private final int turn;

		private CollectTurnsRunnable(List<Integer> turns, int turn) {
			this.turns = turns;
			this.turn = turn;
		}

		@Override
		public void run() {
			turns.add(turn);
		}
	}

	@Test
	void synchronizerMethodsAreInvokedInOrder() throws InterruptedException {
		final List<Integer> expected = List.of(1, 2, 3);
		final Synchronizer synchronizer = new Synchronizer();

		final List<Integer> turns = Collections.synchronizedList(new ArrayList<>());

		final Set<Thread> threads = Set.of(
				new Thread(Delays.delayAnd(() -> synchronizer.acceptFirst(new CollectTurnsRunnable(turns, 1)))),
				new Thread(Delays.delayAnd(() -> synchronizer.acceptSecond(new CollectTurnsRunnable(turns, 2)))),
				new Thread(Delays.delayAnd(() -> synchronizer.acceptThird(new CollectTurnsRunnable(turns, 3))))
		);

		threads.forEach(Thread::start);

		for (Thread thread : threads) {
			thread.join();
		}

		assertEquals(expected, turns);
	}
}