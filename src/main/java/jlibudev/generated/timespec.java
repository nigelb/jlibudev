package jlibudev.generated;
import com.sun.jna.Structure;
/**
 * <i>native declaration : target/checkout/libudev/libudev-types.h:101</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class timespec extends Structure {
	/**
	 * 0     8<br>
	 * C type : __time_t
	 */
	public int tv_sec;
	/// 8     8
	public int tv_nsec;
	public timespec() {
		super();
		initFieldOrder();
	}
	protected void initFieldOrder() {
		setFieldOrder(new String[]{"tv_sec", "tv_nsec"});
	}
	/**
	 * @param tv_sec 0     8<br>
	 * C type : __time_t<br>
	 * @param tv_nsec 8     8
	 */
	public timespec(int tv_sec, int tv_nsec) {
		super();
		this.tv_sec = tv_sec;
		this.tv_nsec = tv_nsec;
		initFieldOrder();
	}
	public static class ByReference extends timespec implements Structure.ByReference {
		
	};
	public static class ByValue extends timespec implements Structure.ByValue {
		
	};
}
