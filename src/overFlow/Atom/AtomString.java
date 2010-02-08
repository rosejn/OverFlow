package overFlow.Atom;

import java.util.Vector;

public class AtomString extends Atom {
	String stringValue;

	public AtomString(String value) {
		type = 4;
		stringValue = value;

	}

	/**
	 * @return the integer value
	 */
	public String getString() {
		return stringValue;
	}

	/**
	 * @return the integer value
	 */
	public int getInt() {
		int val = 0;
		try {
			val = Integer.parseInt(stringValue);
		} catch (java.lang.NumberFormatException e) {
			
		}
		return val;
	}

	/**
	 * @return the integer value
	 */
	public float getFloat() {
		float val = 0;
		try {
			val = Float.parseFloat(stringValue);
		} catch (java.lang.NumberFormatException e) {

		}
		return val;
	}
	
	/**
	 * @return a new AtomArray with the string value set as the only element
	 */
	public AtomArray getAtomArray() {
		Vector<Atom> atoms = new Vector<Atom>();
		atoms.add(new AtomString(stringValue));
		AtomArray array = new AtomArray(atoms);
		return array;
	}
}
