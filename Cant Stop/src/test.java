
public class test {
	thisClass[] tester = new thisClass[12];
	public static void main(String[] args) {
		test t = new test();
		
		/*int[][] noChoices = new int[0][];
		System.out.println(noChoices.length);*/
		/*int[][] returnVal = { { 8, 8 }, { 9, 7 }, { 8, 8 }, { 6 }, { 6 }, {9,7}, {8,8}, {7,9}};
		int[][] choices = { { 8, 8 }, { 9, 7 }, null, null, null, null };
		if (choices[0][0] == returnVal[0][0] && choices[0][1] == returnVal[0][1]) {
			System.out.println("hello");
		}
		
		 * for(int i = 0; i<returnVal.length; i++) { if(returnVal[i]==null) {
		 * System.out.println("null"); } }
		 
		
		 * int length = 0; if(returnVal[0]==null) { int[][] noChoices = new int[1][]; }
		 * for(int i=0; i<returnVal.length; i++) { if(!(returnVal[i]==null)) { length++;
		 * } } int[][] newArray = new int[length][]; for(int i = 0; i<newArray.length;
		 * i++) { newArray[i] = returnVal[i]; }
		 
		int[][] newArray = removeDuplicates(returnVal);
		for (int[] array : newArray) {
			if (array.length == 2) {
				System.out.println(array[0] + "," + array[1]);
			} else if (array.length == 1) {
				System.out.println(array[0]);
			}
		}*/
	}
	public test() {
		run();
	}
	public void run() {
		System.out.println(this.tester[6]);
	}
	public static int[][] removeDuplicates(int[][] arr) {
		int end = arr.length;
		for (int i = 0; i < end; i++) {
			for (int j = i + 1; j < end; j++) {
				if (arr[i].length==arr[j].length&&arr[i].length == 1) {
					if (arr[i][0] == arr[j][0]) {
						arr = removeTheElement(arr, j);
						end--;
						j--;
					}
				} else if (arr[i].length==arr[j].length&&arr[i].length== 2) {
					if ((arr[i][0] == arr[j][0] && arr[i][1] == arr[j][1])||(arr[i][0] == arr[j][1] && arr[i][0] == arr[j][1])) {
						arr = removeTheElement(arr, j);
						end--;
						j--;
					}
				}
			}
		}
		int[][] whitelist = new int[end][];
		System.arraycopy(arr, 0, whitelist, 0, end);
		return whitelist;
	}

	public static int[][] removeTheElement(int[][] arr, int index) {
		if (arr == null || index < 0 || index >= arr.length) {
			return arr;
		}
		int[][] anotherArray = new int[arr.length - 1][];
		for (int i = 0, k = 0; i < arr.length; i++) {
			if (i == index) {
				continue;
			}
			anotherArray[k++] = arr[i];
		}
		return anotherArray;
	}
	public class thisClass {
		thisClass() {
			
		}
	}
}
