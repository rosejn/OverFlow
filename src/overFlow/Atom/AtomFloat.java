package overFlow.Atom;

public class AtomFloat extends Atom{
	float floatValue;
	Float fVal;
	
	public AtomFloat(float value){
		floatValue = value;
		fVal = new Float(value);
	}

	/**
	 * @return the float value
	 */
	public float getFloat() {
		return floatValue;
	}

	/**
	 * @return the integer value
	 */
	public float getInt() {
		Integer valueInt = fVal.intValue();
		return valueInt;
	}	
	
	/**
	 * @return the integer value
	 */
	public String getString() {
		String sVal = fVal.toString();
		return sVal;
	}
}
