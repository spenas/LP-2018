// import de librerias de runtime de ANTLR
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.File;

public class Test {
	public static void main(String[] args) throws Exception {
		try{
			//File f = new File(args[0]);
			// crear un analizador léxico que se alimenta apartir de la entrada (archivo  o consola)
			HelloLexer lexer;
			if (args.length>0)
				lexer = new HelloLexer(new ANTLRFileStream(args[0]));
			else
				lexer = new HelloLexer(new ANTLRInputStream(System.in));
			// Identificar al analizador léxico como fuente de tokens para el sintactico
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			// Crear el objeto correspondiente al analizador sintáctico que se alimenta apartir del buffer de tokens
			HelloParser parser = new HelloParser(tokens);
			ParseTree tree = parser.r(); // begin parsing at init rule
			System.out.println(tree.toStringTree(parser)); // print LISP-style tree
		} catch (Exception e){
			System.err.println("Error (Test): " + e);
		}
	}
}
