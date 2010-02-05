package overFlow.Atom;

import java.util.Vector;

public class AtomArray extends Atom {
	
	Vector<Atom> atomArray = new Vector<Atom>();
	
	public AtomArray(Vector<Atom> aArray) {
		type = 7;
		atomArray = aArray;
		setList(true);
	}

	public String getString() {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getStringArray() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getType() {
		return type;
	}

	public int getSize() {
		return atomArray.size();
	}

	public Vector<Atom> getAtomArray() {
		// TODO Auto-generated method stub
		return atomArray;
	}
	
	public void insertElementAt(Atom atom, int i){
		atomArray.insertElementAt(atom, i);
	}
	
	public Atom getElementAt(int i){
		return atomArray.get(i);
	}
	
 
}
