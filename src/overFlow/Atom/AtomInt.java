package overFlow.Atom;

import java.util.Vector;

public class AtomInt extends Atom{
	int intValue;
	Float floatValue;
	
	public AtomInt(int iVal){
		type = 0;
		intValue = iVal;
	}
	
	public int getInt() {
		return intValue;
	}
	public float getFloat() {
		floatValue = new Float(intValue);
		return floatValue;
	}
	public int getType() {
		return 0;
	}
	
	/**
	 * @return a new AtomArray with the int value set as the only element
	 */
	public AtomArray getAtomArray() {
		Vector<Atom> atoms = new Vector<Atom>();
		atoms.add(new AtomInt(intValue));
		AtomArray array = new AtomArray(atoms);
		return array;
	}
}
