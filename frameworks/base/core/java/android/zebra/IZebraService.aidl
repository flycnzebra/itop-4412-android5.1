package android.zebra;

interface IZebraService {
/**
* {@hide}
*/
	String read(int maxLength);
	int write(String mString);
}