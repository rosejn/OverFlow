package overFlow.Atom;

public class AtomString extends Atom{
	String stringValue;
	
	public AtomString(String value){
		stringValue = value;

	}
	
	/**
	 * @return the integer value
	 */
	public String getString() {
		return stringValue;
	}
}