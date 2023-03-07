package org.diligentsnail.synchronizer;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Delays {
	private static final int MAX_DELAY = 1000;

	/**
	 * Добавить случайную задержку к {@code otherRunnable}.
	 *
	 * Метод принимает {@link Runnable} и возвращает другой {@link Runnable}.
	 * Возвращаемый {@link Runnable} при вызове метода {@link Runnable#run()}:
	 * <ol>
	 *     <li>Спит случайное время в диапазоне [0; {@link #MAX_DELAY})</li>
	 *     <li>Вызывает метод {@link Runnable#run()} {@code otherRunnable}</li>
	 * </ol>
	 *
	 * Пример:
	 *
	 * <pre>
	 *     Runnable runnable = delayAnd(() -> System.out.println("Hello"));
	 *     runnable.run();
	 * </pre>
	 *
	 * выведет "Hello" со случайной задержкой
	 *
	 * @param otherRunnable для добавления задержки
	 * @return {@link Runnable} с задержкой
	 */
	public static Runnable delayAnd(Runnable otherRunnable) {
		Objects.requireNonNull(otherRunnable, "otherRunnable == null");
		final int delay = ThreadLocalRandom.current().nextInt(MAX_DELAY);
		return () -> {
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new UncheckedInterruptedException(e);
			}
			otherRunnable.run();
		};
	}
}
