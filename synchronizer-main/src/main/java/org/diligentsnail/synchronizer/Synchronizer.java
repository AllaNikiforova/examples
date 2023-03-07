package org.diligentsnail.synchronizer;

public class Synchronizer {

	volatile int flag = 0;
	public void acceptFirst(Runnable runnable) {
		// TODO: Сделать так, чтобы runnable.run() вызывался первым
		//  Метод main класса main сюда передаёт runnable, который печатает "1" в консоль
		runnable.run();
		flag = 1;
	}

	public void acceptSecond(Runnable runnable) {
		// TODO: Сделать так, чтобы runnable.run() вызывался вторым
		while (flag != 1) {}
		runnable.run();
		flag = 2;
	}

	public void acceptThird(Runnable runnable) {
		// TODO: Сделать так, чтобы runnable.run() вызывался третьим
		while (flag != 2) {}
		runnable.run();
		flag = 3;
	}
}
