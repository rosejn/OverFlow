package overFlow.Objects;

import overFlow.main.Node;

public class Print extends Node {
	boolean prepend;
	String prependString;

	public Print(String pString, float tx, float ty) {
		super("print " + pString, tx, ty, 1, 0);
		prepend = true;
		prependString = pString;
	}

	public Print(float tx, float ty) {
		super("print", tx, ty, 1, 0);
		prepend = false;
	}

	public void update() {
		try {
			if (inputValues[0].getType() == 0) {
				messageToPrint(inputValues[0].getInt());
			} else if (inputValues[0].getType() == 1) {
				messageToPrint(inputValues[0].getIntArray());
			} else if (inputValues[0].getType() == 2) {
				messageToPrint(inputValues[0].getFloat());
			} else if (inputValues[0].getType() == 3) {
				messageToPrint(inputValues[0].getFloatArray());
			} else if (inputValues[0].getType() == 4) {
				messageToPrint(inputValues[0].getString());
			} else if (inputValues[0].getType() == 5) {
				messageToPrint(inputValues[0].getStringArray());
			}
		} catch (NullPointerException e) {
		}
	}

	void messageToPrint(int[] i) {
		if (prepend) {
			System.out.print(prepend + " ");
			for (int j = 0; j < i.length; j++) {
				System.out.print(i[j]);
				System.out.print(" ");
			}
			System.out.println();
		} else {
			for (int j = 0; j < i.length; j++) {
				System.out.print(i[j]);
				System.out.print(" ");
			}
			System.out.println();
		}
	}

	void messageToPrint(int i) {
		if (prepend) {
			System.out.println(prependString + " " + i);
		} else {
			System.out.println(i);
		}
	}

	void messageToPrint(float[] f) {
		if (prepend) {
			System.out.print(prepend + " ");
			for (int i = 0; i < f.length; i++) {
				System.out.print(f[i]);
				System.out.print(" ");
			}
			System.out.println();
		} else {
			for (int i = 0; i < f.length; i++) {
				System.out.print(f[i]);
				System.out.print(" ");
			}
			System.out.println();
		}
	}

	void messageToPrint(float f) {
		if (prepend) {
			System.out.println(prependString + " " + f);
		} else {
			System.out.println(f);
		}
	}

	void messageToPrint(String[] s) {
		if (prepend) {
			System.out.print(prepend + " ");
			for (int i = 0; i < s.length; i++) {
				System.out.print(s[i]);
				System.out.print(" ");
			}
			System.out.println();
		} else {
			for (int i = 0; i < s.length; i++) {
				System.out.print(s[i]);
				System.out.print(" ");
			}
			System.out.println();
		}
	}

	void messageToPrint(String s) {
		if (prepend) {
			System.out.println(prependString + " " + s);
		} else {
			System.out.println(s);
		}
	}

}
