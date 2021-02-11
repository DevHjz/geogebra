package org.geogebra.common.kernel.geos;

import org.geogebra.common.main.Localization;
import org.geogebra.common.main.ScreenReader;

import com.himamis.retex.renderer.share.serialize.DefaultSerializationAdapter;

public class ScreenReaderSerializationAdapter extends DefaultSerializationAdapter {

	private final Localization loc;

	ScreenReaderSerializationAdapter(Localization loc) {
		this.loc = loc;
	}

	@Override
	public String subscriptContent(String base, String sub, String sup) {
		StringBuilder ret = new StringBuilder(base);
		if (sub != null) {
			ret.append(" start subscript ").append(sub).append(" end subscript ");
		}
		if (sup != null) {
			ret.append(' ');
			ScreenReader.appendPower(ret, sup, loc);
		}
		return ret.toString();
	}

	@Override
	public String transformBrackets(String left, String base, String right) {
		return readBracket(left) + base + readBracket(right);
	}

	private String readBracket(String left) {
		if (left.length() == 1) {
			return convertCharacter(left.charAt(0));
		}
		return left;
	}

	@Override
	public String sqrt(String base) {
		return "square root of " + base;
	}

	public String convertCharacter(char character) {
		switch (character) {
		case '+': return " plus ";
		case '-': return " minus ";
		case '(':
			return " open parenthesis ";
		case ')':
			return " close parenthesis ";
		case '{':
			return " open brace ";
		case '}':
			return " close brace ";
		case '[':
			return " open bracket ";
		case ']':
			return " close bracket ";
		}
		return character + "";
	}

	@Override
	public String fraction(String numerator, String denominator) {
		return "begin fraction numerator " + numerator + " denominator "
				+ denominator + " end fraction ";
	}
}
