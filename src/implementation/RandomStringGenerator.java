package implementation;

public class RandomStringGenerator {

//	protected String[] getRandomStringArray(int arraySize) {
//		String[] array = new String[arraySize];
//
//		for (int i = 0; i < arraySize; i++) {
//			array[i] = getRandomString(2);
//		}
//
//		return array;
//
//	}

	// generate a random string of length n
	String getRandomString(int stringLength) {

		String allString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz";

		String output = "";

		for (int i = 0; i < stringLength; i++) {

			int index = (int) (Math.random() * allString.length());

			output += allString.charAt(index);
		}

		return output;
	}
}
