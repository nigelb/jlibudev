package jlibudev.generated;
import com.sun.jna.Structure;
/**
 * <i>native declaration : target/checkout/libudev/libudev-types.h:309</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class sock_fprog extends Structure {
	/// 0     2
	public int len;
	/**
	 * 8     8<br>
	 * C type : sock_filter*
	 */
	public jlibudev.generated.sock_filter.ByReference filter;
	public sock_fprog() {
		super();
		initFieldOrder();
	}
	protected void initFieldOrder() {
		setFieldOrder(new String[]{"len", "filter"});
	}
	/**
	 * @param len 0     2<br>
	 * @param filter 8     8<br>
	 * C type : sock_filter*
	 */
	public sock_fprog(int len, jlibudev.generated.sock_filter.ByReference filter) {
		super();
		this.len = len;
		this.filter = filter;
		initFieldOrder();
	}
	public static class ByReference extends sock_fprog implements Structure.ByReference {
		
	};
	public static class ByValue extends sock_fprog implements Structure.ByValue {
		
	};
}
