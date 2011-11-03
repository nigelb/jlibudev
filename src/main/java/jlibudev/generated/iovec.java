package jlibudev.generated;
import com.ochafik.lang.jnaerator.runtime.NativeSize;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
/**
 * <i>native declaration : target/checkout/libudev/libudev-types.h:235</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class iovec extends Structure {
	/**
	 * 0     8<br>
	 * C type : void*
	 */
	public Pointer iov_base;
	/// 8     8
	public NativeSize iov_len;
	public iovec() {
		super();
		initFieldOrder();
	}
	protected void initFieldOrder() {
		setFieldOrder(new String[]{"iov_base", "iov_len"});
	}
	/**
	 * @param iov_base 0     8<br>
	 * C type : void*<br>
	 * @param iov_len 8     8
	 */
	public iovec(Pointer iov_base, NativeSize iov_len) {
		super();
		this.iov_base = iov_base;
		this.iov_len = iov_len;
		initFieldOrder();
	}
	public static class ByReference extends iovec implements Structure.ByReference {
		
	};
	public static class ByValue extends iovec implements Structure.ByValue {
		
	};
}
