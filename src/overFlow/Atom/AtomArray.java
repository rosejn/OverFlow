package overFlow.Atom;

import java.util.Vector;

public class AtomArray extends Atom {
	
	Vector<Atom> atomArray = new Vector<Atom>();
	
	public AtomArray()	{
		type = 7;
		setList(true);
	}
	
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
	
	public void add(Atom atom) {
		atomArray.add(atom);
	}

	public AtomArray getAtomArray() {
		// TODO Auto-generated method stub
		return this;
	}
	
	public void insertElementAt(Atom atom, int i){
		atomArray.insertElementAt(atom, i);
	}

	public Atom get(int i){
		return atomArray.get(i);
	}
	
	public Atom getElementAt(int i){
		return atomArray.get(i);
	}
	
	public void moveElementTo(int object, int index){
		Atom atom = atomArray.get(object);
		atomArray.remove(object);
		atomArray.insertElementAt(atom, index);
	}
	
 
}
