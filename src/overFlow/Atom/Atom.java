package overFlow.Atom;

public class Atom {
	int type;   //in order 0 - 8, int, int[], float, float[], String, String[], Atom, Atom[]
	private boolean isList = false;

	Atom() {
		type = 6;
	}
	
	/**
	 * @return the isList
	 */
	public boolean isList() {
		return isList;
	}


	/**
	 * @param isList the isList to set
	 */
	public void setList(boolean isList) {
		this.isList = isList;
	}
	
	public int getInt() {
		// TODO Auto-generated method stub
		return 0;
	}
	public int[] getIntArray() {
		// TODO Auto-generated method stub
		return null;
	}
	public float getFloat() {
		// TODO Auto-generated method stub
		return 0f;
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
	public Atom getAtom() {
		// TODO Auto-generated method stub
		return this;
	}

	public int getSize() {
		// TODO Auto-generated method stub
		return 1;
	}
	public Atom getElementAt(int i){
		return null;
	}

	public float[] getFloatArray() {
		// TODO Auto-generated method stub
		return null;
	}

}
