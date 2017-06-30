package org.ccps.auth.server.config;

import java.util.Date;
import java.util.Locale;

import org.ccps.auth.server.util.AesException;
import org.ccps.auth.server.util.SSOCrypt;
import org.ccps.common.util.StringUtil;
import org.springframework.security.authentication.encoding.BasePasswordEncoder;

public class MyPasswordEncoder extends BasePasswordEncoder {
	// ~ Instance fields
	// ================================================================================================

	private boolean ignorePasswordCase = false;

	// ~ Methods
	// ========================================================================================================

	public String encodePassword(String rawPass, Object salt) {
		return mergePasswordAndSalt(rawPass, salt, true);
	}

	public boolean isIgnorePasswordCase() {
		return ignorePasswordCase;
	}

	public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
		if(salt == null)return false;
		MySaltSource source = (MySaltSource)salt;
		SSOCrypt pc = new SSOCrypt(source.getUser(), MySaltSource.clientEncodeAESKey, MySaltSource.clientId);
		try {
			String stmp = pc.decrypt(rawPass);
			if(!StringUtil.isBlank(stmp)){
				long prestmp = Long.parseLong(stmp);
				if(Math.abs(System.currentTimeMillis()/1000-prestmp)<5)
					return true;
				/*Date d= new Date();
				if(d!=null)return true;*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Demerges the previously {@link #encodePassword(String, Object)}<code>String</code>.
	 * <P>
	 * The resulting array is guaranteed to always contain two elements. The first is the
	 * password, and the second is the salt.
	 * </p>
	 * <P>
	 * Throws an exception if <code>null</code> or an empty <code>String</code> is passed
	 * to the method.
	 * </p>
	 *
	 * @param password from {@link #encodePassword(String, Object)}
	 *
	 * @return an array containing the password and salt
	 */
	public String[] obtainPasswordAndSalt(String password) {
		return demergePasswordAndSalt(password);
	}

	/**
	 * Indicates whether the password comparison is case sensitive.
	 * <P>
	 * Defaults to <code>false</code>, meaning an exact case match is required.
	 * </p>
	 *
	 * @param ignorePasswordCase set to <code>true</code> for less stringent comparison
	 */
	public void setIgnorePasswordCase(boolean ignorePasswordCase) {
		this.ignorePasswordCase = ignorePasswordCase;
	}
}
