package implementation;

public class MyTester {
	public static void main(String[] args) {

		RandomStringGenerator rsg = new RandomStringGenerator();

		// create an ArrayMultiset with 5 elements
		String elem0 = rsg.getRandomString(2);
		String elem1 = rsg.getRandomString(2);
		String elem2 = rsg.getRandomString(2);
		String elem3 = rsg.getRandomString(2);
		String elem4 = rsg.getRandomString(2);

		int testTime = 5;

		long[] timeDuration = new long[testTime];

		// Growing
		for (int i = 0; i < testTime; i++) {
			RmitMultiset s1 = createNewArrayMultiset(elem0, elem1, elem2, elem3, elem4);
			long startTime = System.nanoTime();
			s1.add(rsg.getRandomString(2));
			long stopTime = System.nanoTime();
			timeDuration[i] = stopTime - startTime;
		}
		System.out.println("Average execution time of Growing Multiset = " + getAverage(timeDuration));

		// Shrinking
		for (int i = 0; i < testTime; i++) {
			RmitMultiset s1 = createNewArrayMultiset(elem0, elem1, elem2, elem3, elem4);
			long startTime = System.nanoTime();
			s1.removeOne(elem4);
			long stopTime = System.nanoTime();
			timeDuration[i] = stopTime - startTime;
		}
		System.out.println("Average execution time of Shrinking Multiset = " + getAverage(timeDuration));

		// Intersection
		for (int i = 0; i < testTime; i++) {
			RmitMultiset s1 = createNewArrayMultiset(elem0, elem1, elem2, elem3, elem4);
			RmitMultiset s2 = createNewArrayMultiset(rsg.getRandomString(2), rsg.getRandomString(2),
					rsg.getRandomString(2), rsg.getRandomString(2), rsg.getRandomString(2));
			long startTime = System.nanoTime();
			s1.intersect(s2);
			long stopTime = System.nanoTime();
			timeDuration[i] = stopTime - startTime;
		}
		System.out.println("Average execution time of Intersection = " + getAverage(timeDuration));

		// Print
		for (int i = 0; i < testTime; i++) {
			RmitMultiset s1 = createNewArrayMultiset(elem0, elem1, elem2, elem3, elem4);
			long startTime = System.nanoTime();
			s1.print();
			long stopTime = System.nanoTime();
			timeDuration[i] = stopTime - startTime;
		}
		System.out.println("Average execution time Print = " + getAverage(timeDuration));

	}

	// get average of all values in an array
	private static long getAverage(long[] valueArray) {
		long sum = 0;
		for (int i = 0; i < valueArray.length; i++) {
			sum += valueArray[i];
		}
		return sum / (valueArray.length);
	}

	private static RmitMultiset createNewArrayMultiset(String elem0, String elem1, String elem2, String elem3,
			String elem4) {
		RmitMultiset s1 = new ArrayMultiset(); // create s1
		s1.add(elem0);
		s1.add(elem1);
		s1.add(elem2);
		s1.add(elem3);
		s1.add(elem4);
		return s1;
	}

}
