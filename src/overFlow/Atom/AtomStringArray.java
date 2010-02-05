package overFlow.Atom;

public class AtomStringArray extends Atom{
	
	String[] stringArray;
	
		AtomStringArray(String[] sArray){
			type = 5;
			stringArray = sArray;
		}

		public String[] getStringArray() {
			return stringArray;
		}
}
