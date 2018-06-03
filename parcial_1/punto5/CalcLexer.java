// Generated from Calc.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CalcLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, MUL=4, DIV=5, MOD=6, SUM=7, RES=8, TRIG=9, ID=10, 
		INT=11, FLOAT=12, COMPL=13, NEWLINE=14, WS=15;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "MUL", "DIV", "MOD", "SUM", "RES", "TRIG", "ID", 
		"INT", "FLOAT", "COMPL", "NEWLINE", "WS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'='", "'('", "')'", "'*'", "'/'", "'%'", "'+'", "'-'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, "MUL", "DIV", "MOD", "SUM", "RES", "TRIG", "ID", 
		"INT", "FLOAT", "COMPL", "NEWLINE", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public CalcLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Calc.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\21\u008a\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\3\2\3\2\3\3\3\3"+
		"\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\nG\n"+
		"\n\3\13\3\13\7\13K\n\13\f\13\16\13N\13\13\3\f\6\fQ\n\f\r\f\16\fR\3\r\6"+
		"\rV\n\r\r\r\16\rW\3\r\3\r\6\r\\\n\r\r\r\16\r]\3\16\6\16a\n\16\r\16\16"+
		"\16b\3\16\3\16\6\16g\n\16\r\16\16\16h\5\16k\n\16\3\16\3\16\6\16o\n\16"+
		"\r\16\16\16p\3\16\3\16\6\16u\n\16\r\16\16\16v\5\16y\n\16\5\16{\n\16\3"+
		"\16\3\16\3\17\5\17\u0080\n\17\3\17\3\17\3\20\6\20\u0085\n\20\r\20\16\20"+
		"\u0086\3\20\3\20\2\2\21\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f"+
		"\27\r\31\16\33\17\35\20\37\21\3\2\7\4\2C\\c|\5\2\62;C\\c|\3\2\62;\4\2"+
		"--//\4\2\13\13\"\"\2\u009b\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2"+
		"\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2"+
		"\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3"+
		"\2\2\2\3!\3\2\2\2\5#\3\2\2\2\7%\3\2\2\2\t\'\3\2\2\2\13)\3\2\2\2\r+\3\2"+
		"\2\2\17-\3\2\2\2\21/\3\2\2\2\23F\3\2\2\2\25H\3\2\2\2\27P\3\2\2\2\31U\3"+
		"\2\2\2\33`\3\2\2\2\35\177\3\2\2\2\37\u0084\3\2\2\2!\"\7?\2\2\"\4\3\2\2"+
		"\2#$\7*\2\2$\6\3\2\2\2%&\7+\2\2&\b\3\2\2\2\'(\7,\2\2(\n\3\2\2\2)*\7\61"+
		"\2\2*\f\3\2\2\2+,\7\'\2\2,\16\3\2\2\2-.\7-\2\2.\20\3\2\2\2/\60\7/\2\2"+
		"\60\22\3\2\2\2\61\62\7u\2\2\62\63\7k\2\2\63G\7p\2\2\64\65\7e\2\2\65\66"+
		"\7q\2\2\66G\7u\2\2\678\7v\2\289\7c\2\29G\7p\2\2:;\7c\2\2;<\7u\2\2<=\7"+
		"k\2\2=G\7p\2\2>?\7c\2\2?@\7e\2\2@A\7q\2\2AG\7u\2\2BC\7c\2\2CD\7v\2\2D"+
		"E\7c\2\2EG\7p\2\2F\61\3\2\2\2F\64\3\2\2\2F\67\3\2\2\2F:\3\2\2\2F>\3\2"+
		"\2\2FB\3\2\2\2G\24\3\2\2\2HL\t\2\2\2IK\t\3\2\2JI\3\2\2\2KN\3\2\2\2LJ\3"+
		"\2\2\2LM\3\2\2\2M\26\3\2\2\2NL\3\2\2\2OQ\t\4\2\2PO\3\2\2\2QR\3\2\2\2R"+
		"P\3\2\2\2RS\3\2\2\2S\30\3\2\2\2TV\t\4\2\2UT\3\2\2\2VW\3\2\2\2WU\3\2\2"+
		"\2WX\3\2\2\2XY\3\2\2\2Y[\7\60\2\2Z\\\t\4\2\2[Z\3\2\2\2\\]\3\2\2\2][\3"+
		"\2\2\2]^\3\2\2\2^\32\3\2\2\2_a\t\4\2\2`_\3\2\2\2ab\3\2\2\2b`\3\2\2\2b"+
		"c\3\2\2\2cj\3\2\2\2df\7\60\2\2eg\t\4\2\2fe\3\2\2\2gh\3\2\2\2hf\3\2\2\2"+
		"hi\3\2\2\2ik\3\2\2\2jd\3\2\2\2jk\3\2\2\2kl\3\2\2\2lz\t\5\2\2mo\t\4\2\2"+
		"nm\3\2\2\2op\3\2\2\2pn\3\2\2\2pq\3\2\2\2qx\3\2\2\2rt\7\60\2\2su\t\4\2"+
		"\2ts\3\2\2\2uv\3\2\2\2vt\3\2\2\2vw\3\2\2\2wy\3\2\2\2xr\3\2\2\2xy\3\2\2"+
		"\2y{\3\2\2\2zn\3\2\2\2z{\3\2\2\2{|\3\2\2\2|}\7k\2\2}\34\3\2\2\2~\u0080"+
		"\7\17\2\2\177~\3\2\2\2\177\u0080\3\2\2\2\u0080\u0081\3\2\2\2\u0081\u0082"+
		"\7\f\2\2\u0082\36\3\2\2\2\u0083\u0085\t\6\2\2\u0084\u0083\3\2\2\2\u0085"+
		"\u0086\3\2\2\2\u0086\u0084\3\2\2\2\u0086\u0087\3\2\2\2\u0087\u0088\3\2"+
		"\2\2\u0088\u0089\b\20\2\2\u0089 \3\2\2\2\21\2FLRW]bhjpvxz\177\u0086\3"+
		"\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}