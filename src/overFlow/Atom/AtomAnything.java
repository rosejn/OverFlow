package overFlow.Atom;

public class AtomAnything extends Atom{
	AtomFloat atomFloat;
	AtomInt atomInt;
	AtomString atomString;
	int type;

	
	AtomAnything(int i){
		atomInt = new AtomInt(i);
		type = 0;
	}

	AtomAnything(float f){
		atomFloat = new AtomFloat(f);
		type = 1;
	}
	
	AtomAnything(String s){
		atomString = new AtomString(s);
		type = 2;
	}
	
	
	/**
	 * @return the atomFloat
	 */
	public AtomFloat getAtomFloat() {
		return atomFloat;
	}

	/**
	 * @param atomFloat the atomFloat to set
	 */
	public void setAtomFloat(AtomFloat atomFloat) {
		this.atomFloat = atomFloat;
	}

	/**
	 * @return the atomInt
	 */
	public AtomInt getAtomInt() {
		return atomInt;
	}

	/**
	 * @param atomInt the atomInt to set
	 */
	public void setAtomInt(AtomInt atomInt) {
		this.atomInt = atomInt;
	}

	/**
	 * @return the atomString
	 */
	public AtomString getAtomString() {
		return atomString;
	}

	/**
	 * @param atomString the atomString to set
	 */
	public void setAtomString(AtomString atomString) {
		this.atomString = atomString;
	}
	
	
}
