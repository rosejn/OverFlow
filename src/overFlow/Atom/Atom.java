package overFlow.Atom;

import java.util.Vector;

public class Atom {
	int type;   //in order 0 - 8, int, int[], float, float[], String, String[], Atom, Atom[]
	private boolean isList = false;

	AtomFloat atomFloat;
	AtomInt atomInt;
	AtomString atomString;
	
	/**
	 * @param empty constructor.
	 */
	public Atom() {
		type = 6;
	}
	
	/**
	 * @param int input constructor.
	 */
	Atom(int i) {
		type = 0;
		atomInt = new AtomInt(i);
	}
	
	/**
	 * @param float input constructor.
	 */
	Atom(float f) {
		type = 2;
		atomFloat = new AtomFloat(f);
	}
	
	Atom(String s) {
		type = 4;
		atomString = new AtomString(s);
	}
	
	/**
	 * @return the isList boolean.
	 */
	public boolean isList() {
		return isList;
	}

	/**
	 * @param isList set boolean isList if Atom is a list.
	 */
	public void setList(boolean isList) {
		this.isList = isList;
	}
	
	/**
	 * @return the int value of the this atom.
	 */
	public int getInt() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * @return the int array of this atom.
	 */
	public int[] getIntArray() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @return the float array of this atom.
	 */
	public float getFloat() {
		// TODO Auto-generated method stub
		return 0f;
	}

	/**
	 * @return the string of this atom.
	 */
	public String getString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @return the string array of this atom.
	 */
	public String[] getStringArray() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @return the data type of this atom. (0-7, int, int[], float, float[], string, string[], Atom, AtomArray).
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * @return this atom.
	 */
	public Atom getAtom() {
		// TODO Auto-generated method stub
		return this;
	}

	/**
	 * @return returns how many elements in this atom (defaults to one).
	 */
	public int getSize() {
		// TODO Auto-generated method stub
		return 1;
	}
	
	/**
	 * @return return specified atom at given location.
	 */
	public Atom getElementAt(int i){
		return null;
	}

	/**
	 * @return the float array representation of this Atom.
	 */
	public float[] getFloatArray() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @return the Vector representation of this Atom.
	 */
	public AtomArray getAtomArray() {
		return null;
	}
	

	public static Atom parseAtom( String arg ) {
		Atom atom = new Atom();
	  if (arg.matches("[a-zA-Z][a-zA-Z0-9]*")) {
	    atom = new AtomString(arg);
	  } else if (arg.matches("[0-9]+\\.[0-9]+")) {
		    atom = new AtomFloat(Float.parseFloat(arg));
	  }  else if (arg.matches("[0-9]+")) {
	    atom = new AtomInt( Integer.parseInt(arg));
	  }
	  return atom;
	}
	
}