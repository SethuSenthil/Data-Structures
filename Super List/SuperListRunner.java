//custom SuperList default superlist is (2)
public class SuperListRunner {
	public SuperListRunner() {
		SuperList<Integer> list = new SuperList<Integer>();
		for (int i = 0; i < 30; i++)
			list.add((int) (Math.random() * 1000) + 1);

		System.out.println("List:\n" + list);
		System.out.println("Size of the List: " + list.size());

		SuperList<Integer> stack = new SuperList<Integer>();
		for (int i = 0; i < list.size(); i++) {
			stack.push(list.remove(i));
			i--;
		}

		System.out.println("\nStack:\n" + stack);

		SuperList<Integer> queue = new SuperList<Integer>();
		while (!stack.isEmpty())
			queue.add(stack.pop());

		System.out.println("\nQueue:\n" + queue);
		while (!queue.isEmpty()) {
			int index = (int) (Math.random() * list.size());
			list.add(index, queue.poll());
		}

		System.out.println("\nList:\n" + list);
		int sum = 0;

		for (int i = 0; i < list.size(); i++)
			sum += list.get(i);
		System.out.println("Sum of the list: " + sum);

		int numEvenSum = 0;
		for (int i = 0; i < list.size(); i += 2)
			numEvenSum += list.get(i);
		System.out.println("Sum of even indexes: " + numEvenSum);

		int numOddSum = 0;
		for (int i = 1; i < list.size(); i += 2)
			numOddSum += list.get(i);
		System.out.println("Sum of odd indexes: " + numOddSum);

		for (int i = 0; i < 30; i++)
			if (list.get(i) % 2 == 0)
				list.add(list.size() - 1, list.get(i));

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) % 3 == 0) {
				list.remove(i);
				i--;
			}
		}

		list.add(4, 5555);

		for (int i = 1; i < list.size(); i++) {
			int valueTosort = list.get(i);
			int j = i;
			while (j > 0 && list.get(j - 1) > valueTosort) {
				list.remove(j);
				list.add(j, list.get(j - 1));
				j--;
			}

			list.remove(j);
			list.add(j, valueTosort);

		}

		System.out.println("\nList:\n" + list);
		int median = 0;
		int index = list.size() / 2;

		if (list.size() % 2 != 0)
			median = list.get(index);
		else {
			median = list.get(index) + list.get(index - 1);
			median /= 2;
		}

		System.out.println("The median is: " + median);

		if (list.size() % 2 != 0)
			System.out.println("Value before the median: " + list.get(index - 1) + "\nValue after the median: "
					+ list.get(index + 1));
		else
			System.out.println(
					"Value before the median: " + list.get(index - 1) + "\nValue after the median: " + list.get(index));

		SuperList<String> stringList = new SuperList<String>();
		String sentence = "The greatest glory in living lies not in never falling but in rising every time we fall";
		String[] piece = sentence.split(" ");

		for (int i = 0; i < piece.length; i++)
			stringList.add(piece[i]);

		for (int i = 0; i < stringList.size(); i++) {
			if (stringList.get(i).length() <= 3) {
				stringList.remove(i);
				i--;
			}
		}

		String temp;
		for (int i = 1; i < stringList.size(); i++) {
			temp = stringList.get(i);
			int j = 0;
			for (j = i; j > 0; j--)
				if (temp.compareTo(stringList.get(j - 1)) < 0) {
					stringList.remove(j);
					stringList.add(j, stringList.get(j - 1));
				} else
					break;
			stringList.remove(j);
			stringList.add(j, temp);
		}

		System.out.println("\nString:\n" + stringList);
		double avgWordLength = 0.0;

		for (int i = 0; i < stringList.size(); i++)
			avgWordLength += stringList.get(i).length();
		avgWordLength /= stringList.size();

		System.out.println("Average word length: " + avgWordLength);
	}

	public static void main(String[] args) {
		SuperListRunner thread = new SuperListRunner();
	}
}