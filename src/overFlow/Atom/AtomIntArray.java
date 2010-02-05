package overFlow.Atom;

	import java.util.Vector;

	public class AtomIntArray extends Atom{
		
		int[] intArray;
		Vector<AtomInt> atomIntArray = new Vector<AtomInt>();
		
			public AtomIntArray(int[] array){
				type = 3;
				intArray = array;
				setList(true);	//set boolean that this is a list type Atom

				for(int i = 0; i < intArray.length - 1; i++){
					atomIntArray.insertElementAt(new AtomInt(intArray[i]),0);
				}
			}

			public float getFloat(){
				return (float) intArray[0];
			}
			
			public int[] getIntArray() {
				return intArray;
			}
			public Vector<AtomInt> getAtomIntArray() {
				return atomIntArray;
			}
			
			public AtomInt getElementAt(int i) {
				return atomIntArray.get(i);
			}
			
			public int getSize() {
				return intArray.length;
			}
	}
