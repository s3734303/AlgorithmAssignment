import implementation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.lang.System;

public class DataGenerator {

	RmitMultiset multiset;

	public RmitMultiset newMultiset(int type) {
		switch (type) {
		case (1):
			return new ArrayMultiset();
		case (0):
			return new BstMultiset();
		case (2):
			return new DualLinkedListMultiset();
		case (3):
			return new OrderedLinkedListMultiset();
		default:
			return null;
		}
	}

	public void growth(int size, int type) {
		multiset = newMultiset(type);
		Random random = new Random();
		Instant start = Instant.now();
		for (int i = 0; i < size; i++) {
			multiset.add(String.valueOf(random.nextInt(99)));
		}
		Instant finish = Instant.now();
		long time = Duration.between(start, finish).toNanos();
		System.out.println("Size:" + size + "\t\t" + "Time:" + time / 1000 + "\t\t");
	}

	public void prints(int size, int type) {
		multiset = newMultiset(type);
		Random random = new Random();
		for (int i = 0; i < size; i++) {
			multiset.add(String.valueOf(random.nextInt(99)));
		}
		Instant start = Instant.now();
		multiset.print();
		Instant finish = Instant.now();
		long time = Duration.between(start, finish).toNanos();
		System.out.println("Size:" + size + "\t\t" + "Time:" + time / 1000 + "\t\t");
	}

	public void shrink(int size, int type) {
		multiset = newMultiset(type);
		Random random = new Random();
		for (int i = 0; i < 10000; i++) {
			multiset.add(String.valueOf(random.nextInt(99)));
		}
		Instant start = Instant.now();
		for (int i = 0; i < size; i++) {
			multiset.removeOne(String.valueOf(random.nextInt(99)));
		}
		Instant finish = Instant.now();
		long time = Duration.between(start, finish).toNanos();
		System.out.println("Size:" + size + "\t\t" + "Time:" + time / 1000 + "\t\t");

	}

	public void intersection(int size, int type) {
		multiset = newMultiset(type);
		RmitMultiset other = newMultiset(type);
		Random random = new Random();
		for (int i = 0; i < size; i++) {
			multiset.add(String.valueOf(random.nextInt(99)));
			other.add(String.valueOf(random.nextInt(99)));
		}
		Instant start = Instant.now();
		multiset.intersect(other);
		Instant finish = Instant.now();
		long time = Duration.between(start, finish).toNanos();
		System.out.println("Size:" + size + "\t\t" + "Time:" + time / 1000 + "\t\t");
	}

	public static void main(String[] args) {

		for (int t = 0; t < 4; t++) {
			switch (t) {
			case (1):
				System.out.print("Array\n");
				break;
			case (0):
				System.out.print("Binary Search Tree\n");
				break;
			case (2):
				System.out.print("Dual Linked List\n");
				break;
			case (3):
				System.out.print("Ordered Linked List\n");
				break;
			}
			DataGenerator dataGenerator = new DataGenerator();
			System.out.println("Growing Multiset:");
			for (int i = 500; i <= 5000; i = i + 500) {
				dataGenerator.growth(i, t);
			}
			System.out.println("Shrinking Multiset:");
			for (int i = 500; i <= 5000; i = i + 500) {
				dataGenerator.shrink(i, t);
			}
			System.out.println("Intersection:");
			for (int i = 500; i <= 5000; i = i + 500) {
				dataGenerator.intersection(i, t);
			}
			System.out.println("print:");
			for (int i = 500; i <= 5000; i = i + 500) {
				dataGenerator.prints(i, t);
			}

		}

	}

}
