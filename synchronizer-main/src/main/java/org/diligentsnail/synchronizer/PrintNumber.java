package org.diligentsnail.synchronizer;

/**
 * {@link PrintNumber} выводит заданное число в консоль при вызове метода {@link #run()}
 */
public class PrintNumber implements Runnable {
	/**
	 * Число для вывода
	 */
	private final int number;

	public PrintNumber(int number) {
		this.number = number;
	}

	@Override
	public void run() {
		System.out.println(number);
	}
}
