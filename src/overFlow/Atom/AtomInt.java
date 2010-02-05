package overFlow.Atom;

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
}
