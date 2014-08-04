/**
* Copyright (c) 2014 Digi International Inc.,
* All rights not expressly granted are reserved.
*
* This Source Code Form is subject to the terms of the Mozilla Public
* License, v. 2.0. If a copy of the MPL was not distributed with this file,
* You can obtain one at http://mozilla.org/MPL/2.0/.
*
* Digi International Inc. 11001 Bren Road East, Minnetonka, MN 55343
* =======================================================================
*/
package com.digi.xbee.api.utils;

/**
 * Utility class containing methods to work with hexadecimal values
 * and several data type conversions.
 */
public class HexUtils {

	static final String HEXES = "0123456789ABCDEF";
	static final String HEX_HEADER = "0x";
	
	/**
	 * Converts the given byte array into a hex string.
	 * 
	 * @param value Byte array to convert to hex string.
	 * @return Converted byte array to hex string.
	 */
	public static String byteArrayToHexString(byte[] value) {
		if (value == null )
			return null;
		final StringBuilder hex = new StringBuilder(2 * value.length );
		for (final byte b : value) {
			hex.append(HEXES.charAt((b & 0xF0) >> 4))
			.append(HEXES.charAt((b & 0x0F)));
		}
		return hex.toString();
	}
	
	/**
	 * Converts the given byte  into a hex string.
	 * 
	 * @param value Byte to convert to hex string.
	 * @return Converted byte to hex string.
	 */
	public static String byteToHexString(byte value) {
		final StringBuilder hex = new StringBuilder(2);
		byte b = value;
		hex.append(HEXES.charAt((b & 0xF0) >> 4))
		.append(HEXES.charAt((b & 0x0F)));
		return hex.toString();
	}
	
	/**
	 * Converts the given hex string into a byte array.
	 * 
	 * @param value Hex string to convert to.
	 * @return Byte array of the given hex string.
	 * 
	 * @throws NullPointerException if {@code value == null}.
	 */
	public static byte[] hexStringToByteArray(String value) {
		if (value == null)
			throw new NullPointerException("Value cannot be null.");
		
		value = value.trim();
		if (value.startsWith(HEX_HEADER))
			value = value.substring((HEX_HEADER).length());
		int len = value.length();
		if (len % 2 != 0) {
			value = "0" + value;
			len = value.length();
		}
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(value.charAt(i), 16) << 4)
					+ Character.digit(value.charAt(i+1), 16));
		}
		return data;
	}
	
	/**
	 * Internal function to check if the parameter is a string or a
	 * numeric value
	 * 
	 * @param parameter parameter to check.
	 * @return true if contains letters, false otherwise.
	 * 
	 * @throws NullPointerException if {@code parameter == null}.
	 */
	public static boolean containsLetters(String parameter) {
		if (parameter == null)
			throw new NullPointerException("Parameter cannot be null.");
		
		byte[] byteArray = parameter.getBytes();
		for (int i = 0; i < byteArray.length; i++){
			if (!((byteArray[i] >= '0') && (byteArray[i] <= '9')))
				return true;
		}
		return false;
	}
	
	/**
	 * Retrieves the given integer as hexadecimal string.
	 * 
	 * @param value The integer value to convert to hexadecimal string.
	 * @param minBytes The minimum number of bytes to be represented.
	 * @return The integer value as hexadecimal string.
	 */
	public static String integerToHexString(int value, int minBytes) {
		byte[] intAsByteArray = ByteUtils.intToByteArray(value);
		String intAsHexString = "";
		boolean numberFound = false;
		for (int i = 0; i < intAsByteArray.length; i++) {
			if (intAsByteArray[i] == 0x00 && !numberFound && intAsByteArray.length - i > minBytes)
				continue;
			intAsHexString += HexUtils.byteArrayToHexString(new byte[] {(byte)(intAsByteArray[i] & 0xFF)});
			numberFound = true;
		}
		return intAsHexString;
	}
	
	/**
	 * Retrieves the given hexadecimal string splitting the content byte by byte.
	 * 
	 * @param hexString The hexadecimal string to split.
	 * @return The hexadecimal string with the bytes split.
	 * 
	 * @throws NullPointerException if {@code hexString == null}.
	 */
	public static String prettyHexString(String hexString) {
		if (hexString == null)
			throw new NullPointerException("Hexadecimal string cannot be null.");
		
		String prettyHexString = "";
		if (hexString.length() % 2 != 0)
			hexString = "0" + hexString;
		int iterations = hexString.length() / 2;
		for (int i = 0; i < iterations; i++)
			prettyHexString += hexString.substring(2 * i, 2 * i + 2) + " ";
		return prettyHexString.trim();
	}
	
	/**
	 * Converts the given byte array into a hex string and retrieves it 
	 * splitting the content byte by byte.
	 * 
	 * @param value The byte array to convert and split.
	 * @return The hexadecimal string with the bytes split.
	 */
	public static String prettyHexString(byte[] value) {
		return prettyHexString(byteArrayToHexString(value));
	}
}
