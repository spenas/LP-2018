/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class Lenguajes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner entrada = new Scanner (System.in);
        
        String noTerminales[]=entrada.nextLine().split(" "); 
        String terminales[]=entrada.nextLine().split(" "); 
        int numero= Integer.parseInt(entrada.nextLine());
        Gramatica miGramatica = new Gramatica(noTerminales, terminales); 
        
        int contador=0;
        do{
            String arregloLinea[]=entrada.nextLine().split(" ");              
            miGramatica.agregarRegla(arregloLinea);            
        
        }while(++contador < numero);
        
        miGramatica.calcularPrimeros1();
         
        miGramatica.calcularSiguientes(); 
        
        miGramatica.calcularPrediccion();
        
        // la regla con su conjunto de primeros
       /* for(Regla reglatmp : miGramatica.reglas){
            System.out.print(reglatmp.pIzquierda+" ");
            for(String elemento: reglatmp.pDerecha){
                System.out.print(elemento+" ");
            }
            System.out.println();
            for(String elemento: reglatmp.primeros){
                System.out.print(elemento+" ");
            }
            System.out.println();
            
        }*/
        
        // imprime el conjunto de predicciones para cada regla        
        /*for(Regla reglatmp : miGramatica.reglas){ 
            System.out.print(reglatmp.pIzquierda+"    ");
            
            for(String elemento: reglatmp.primeros){
                System.out.print(elemento+" ");
            }
            System.out.println();            
        }*/
        
        //entre el token f
        
        Lexico miLexico = new Lexico();
        int fila=1;
        String renglon = entrada.nextLine()+"  ";
        ArrayList<Token> listaTokens = miLexico.obtenerTokens(renglon,fila);
        miGramatica.iniciarDerivacion();
        
        
        do{
            fila++;
            
             
            for(Token t : listaTokens){
                //System.out.print(t.lexema + ":"+t.traduccion + ", ");
                miGramatica.emparejar(t);
            }
            renglon = entrada.nextLine()+"  "; 
            listaTokens = miLexico.obtenerTokens(renglon,fila);
            //System.out.println();
        
        }while(!renglon.equals("  "));
        
        System.out.print("El analisis sintactico ha finalizado correctamente.");
        
        
        
    }
}

class Gramatica {
    LinkedList<String> derivacion;
    
    void iniciarDerivacion(){
        
        this.derivacion = new LinkedList<> ();
        int posicion = reglas.get(0).pDerecha.size();                
        
        derivacion.addFirst(this.sInicial);
        
    }
    
    String ordenar (String[] words) {
        
        for(int i = 0; i < 3; ++i) {
            for (int j = i + 1; j < words.length; ++j) {
                if (words[i].compareTo(words[j]) > 0) {

                    // swap words[i] with words[j[
                    String temp = words[i];
                    words[i] = words[j];
                    words[j] = temp;
                }
            }
        }
        String salida="";
        for(int i = 0; i < words.length; i++) {
            salida += "'"+words[i]+"'";
        }
        return salida;
    }
    
    void emparejar(Token tokenTmp){
        if(variablesPrimeros.containsKey(derivacion.get(0))){
        //buscar en las reglas que tengan en la parte derecha derivacion.get(0) el token que llega y aplicar esa regla
            boolean reglaAplicada = false;
            for(Regla reglaTmp: reglas){
                if(!reglaTmp.pIzquierda.equals(derivacion.get(0))) continue;
                if(!(reglaTmp.primeros.contains(tokenTmp.lexema) || reglaTmp.primeros.contains(tokenTmp.traduccion))) continue;
                
                derivacion.removeFirst();
                int posicion = reglaTmp.pDerecha.size();                
                for (int i = posicion; i > 0; i--) {
                    if(!reglaTmp.pDerecha.get(i-1).equals("eps")){
                        derivacion.addFirst(reglaTmp.pDerecha.get(i-1));
                    }
                }
                emparejar(tokenTmp);
                reglaAplicada = true;
                break;
                
            }
            if(reglaAplicada == false){
                LinkedHashSet<String> esperados = new LinkedHashSet<String>();
                for(Regla reglaTmp: reglas){
                    if(!reglaTmp.pIzquierda.equals(derivacion.get(0))) continue;
                    esperados.addAll(reglaTmp.primeros);
                }
                
                ArrayList<String> nombres= new ArrayList<>(esperados.size());
                int i=0;
                for(String esperadoTmp: esperados){
                     nombres.add(esperadoTmp);
                }
                System.out.print("<"+tokenTmp.fila+":"+tokenTmp.columna+"> Error sintactico. Encontrado: '"+tokenTmp.traduccion+"'; se esperaba: "+nombres+".");
                System.exit(0);
            }
            
        }else{ 
            if(tokenTmp.traduccion.equals(derivacion.get(0)) || tokenTmp.lexema.equals(derivacion.get(0))){
                derivacion.removeFirst();
            }else{
                System.out.print("<"+tokenTmp.fila+":"+tokenTmp.columna+"> Error sintactico. Encontrado: '"+tokenTmp.traduccion+"'; se esperaba: '"+derivacion.get(0)+"'.");
                System.exit(0);
            }
        }
        
        
    }
    // terminales, variables, inicial final
    public Gramatica(String[] noTerminals, String[] terminals){
        this.sInicial = noTerminals[0];
        this.reglas = new LinkedList<>(); 
        this.variablesPrimeros = new HashMap<>() ;
        this.variablesSiguientes = new HashMap<>() ;
        this.terminales = new HashMap<> ();
        
        for(int i = 0; i < noTerminals.length; i++){
            HashSet<String> primeros = new HashSet<String>();
            variablesPrimeros.put(noTerminals[i], primeros);
            HashSet<String> siguientes = new HashSet<String>();
            variablesSiguientes.put(noTerminals[i], siguientes);
        }
        for(int i = 0; i < terminals.length; i++){
            terminales.put(terminals[i], terminals[i]);
        }
    }
    
    void agregarRegla(String[] arregloLinea) {
        
        Regla reglaTmp = new Regla(arregloLinea[0]);
        reglaTmp.adicionarSecuencia(arregloLinea); 
        reglas.add(reglaTmp);
    } 
    
    void calcularSiguientes() {
        variablesSiguientes.get(sInicial).add("EOF");
        
        for(Regla reglaTmp: reglas){
            int an=0;
        
            for(String simbolo : reglaTmp.pDerecha){
                an++;
                if(!variablesPrimeros.containsKey(simbolo)) continue;
                LinkedHashSet<String> primerosSubCadena = new LinkedHashSet<String>();
                
                ArrayList <String> subCadena = reglaTmp.sub(reglaTmp.pDerecha,an, reglaTmp.pDerecha.size());
                //LinkedHashSet<String> primerosSubCadena = new LinkedHashSet<String>();
                fuPrimeros(subCadena, primerosSubCadena);
                
                if(primerosSubCadena.isEmpty() && an==reglaTmp.pDerecha.size() && !reglaTmp.pIzquierda.equals(variablesSiguientes.get(simbolo))){
                    primerosSubCadena.add(reglaTmp.pIzquierda);
                }
                
                if(primerosSubCadena.contains("eps") && !reglaTmp.pIzquierda.equals(variablesSiguientes.get(simbolo))){
                    primerosSubCadena.remove("eps");
                    primerosSubCadena.add(reglaTmp.pIzquierda);
                }
                variablesSiguientes.get(simbolo).addAll(primerosSubCadena);
                                
            }
            
        }
        
        
        for (Map.Entry<String, HashSet<String>> entry : variablesSiguientes.entrySet()) {
            variablesSiguientes.get(entry.getKey()).add(entry.getKey());
            calcularSiguientesE(entry);
        }

        
        
        
    }
    
    void calcularSiguientes2( Map.Entry<String, HashSet<String>> entry) {
        LinkedList <String>lista = (LinkedList <String>) entry.getValue().iterator(); 
        for(String d: entry.getValue()){            
            if(variablesPrimeros.containsKey(d)){
                
                HashSet<String> posibles = variablesSiguientes.get(d);//si el mismo
                for (String s : posibles) {
                    if(variablesPrimeros.containsKey(s)){
                        if(variablesSiguientes.get(entry.getKey()).add(s)){
                            calcularSiguientes2(entry);
                        }
                    }else{
                        variablesSiguientes.get(entry.getKey()).add(s);
                    }
                }
                
            } 
        }
    }
    
    void calcularSiguientesE( Map.Entry<String, HashSet<String>> entry) {
        LinkedList<String> elementos = new LinkedList<String>();
        for(String d: entry.getValue()){
            elementos.add(d);
        }
        for (int i = 0; i < elementos.size(); i++) {
            if(variablesPrimeros.containsKey(elementos.get(i))){
                HashSet<String> posibles = variablesSiguientes.get(elementos.get(i));//si el mismo
                for (String s : posibles) {
                    
                    boolean existe = false;
                    for (String StringCompr: elementos) {
                        if(s.equals(StringCompr)){
                            existe = true;
                            break;
                        }
                    }
                    if(existe==true){
                        continue;
                    }
                    variablesSiguientes.get(entry.getKey()).add(s);
                    elementos.add(s);
                   
                        
                }
            }//metalo de otra forma, y si ya esta
        }
        variablesSiguientes.get(entry.getKey()).clear();
        //HashSet<String> nuevoSiguientes = new HashSet<String> ();
        for (String StringCompr: elementos) {
            if(variablesPrimeros.containsKey(StringCompr))continue;
            //nuevoSiguientes.add(StringCompr);
            variablesSiguientes.get(entry.getKey()).add(StringCompr);
        }
    }
    
    void calcularPrimeros1() {
        
        for(Regla reglaTmp: reglas){
            //System.out.println("" + reglaTmp.pIzquierda + "  -> " + reglaTmp.pIzquierda);

            fPrimeros(reglaTmp,reglaTmp.primeros);
        }
        //System.out.println(reglas);
    }
    
    
    void calcularPrediccion() {
        for(Regla reglaTmp: reglas){
            prediccion(reglaTmp,reglaTmp.primeros);
        }
    }
    
    void prediccion(Regla reglaTmp, LinkedHashSet<String> primeros) {
       
        if(primeros.contains("eps")){
            primeros.remove("eps");
            
            primeros.addAll(variablesSiguientes.get(reglaTmp.pIzquierda));
        }
    }
    
    
    void fPrimeros(Regla reglaTmp, LinkedHashSet<String> primeros) {
       
        int an=0;
        
        for(String simbolo : reglaTmp.pDerecha){
            ++an;
            if(!variablesPrimeros.containsKey(simbolo) ){
                primeros.add(simbolo);
                return;
            }else{ // variable
                for(Regla simb: reglas){
                    if(!simb.pIzquierda.equals(simbolo))continue;
                    if(reglaTmp.equals(simb))continue;
                    LinkedHashSet<String> salida = new LinkedHashSet<String>();
                    //System.out.println("       " + simb.pIzquierda + "  -> " + simb.pIzquierda);
                    fPrimeros(simb, salida); 
                    primeros.addAll(salida);
                }
            }           
            if(an!= reglaTmp.pDerecha.size() && !primeros.contains("eps")){
                break;
            }
            else if(an!= reglaTmp.pDerecha.size() && primeros.contains("eps")){                
                primeros.remove("eps");
            }
            //System.out.println(reglaTmp);
            
        }
        
    }
    
    void fuPrimeros(ArrayList<String> reglaTmp, LinkedHashSet<String> primeros) {
       
        int an=0;
        
        for(String simbolo : reglaTmp){
            ++an;
            if(!variablesPrimeros.containsKey(simbolo) ){
                primeros.add(simbolo);
                return;
            }else{ // variable
                for(Regla simb: reglas){
                    if(!simb.pIzquierda.equals(simbolo))continue;
                    if(reglaTmp.equals(simb.pDerecha))continue;
                    LinkedHashSet<String> salida = new LinkedHashSet<String>();
                    //System.out.println("" + simb.pIzquierda + "  -> " + simb.pIzquierda);
                    fuPrimeros(simb.pDerecha, salida); 
                    primeros.addAll(salida);
                }
            }           
            if(an!= reglaTmp.size() && !primeros.contains("eps")){
                break;
            }
            else if(an!= reglaTmp.size() && primeros.contains("eps")){                
                primeros.remove("eps");
            }
            
        }
        
    }   
    
    
    String sInicial;
    LinkedList<Regla> reglas;
    
    HashMap<String,HashSet<String>> variablesPrimeros;
    HashMap<String,HashSet<String>> variablesSiguientes;
    HashMap<String,String> terminales;

    
}

class Regla {
    Regla(String sInicial) {    
        primeros = new LinkedHashSet<String>();
        pDerecha = new ArrayList<>();
        this.pIzquierda= sInicial;
    }
    void adicionarSecuencia(String[] arregloLinea) {    
        for(int i = 2; i < arregloLinea.length; i++){
            pDerecha.add(arregloLinea[i]);
        }        
    }
    
    ArrayList<String> sub(ArrayList<String> orig,int i,int j){
        ArrayList<String> sub = new ArrayList<String>();
        for(int in=i; in<j;in++){
            String d= orig.get(in);
            sub.add(d);
        }
        return sub;
    }

@Override
       public String toString()
{
  return  "++++++++++++++++++" + pIzquierda+" - " + pDerecha + "\n-" + primeros;
}

    String pIzquierda;
    ArrayList<String> pDerecha;
    LinkedHashSet<String> primeros;
}


class Lexico {
    Hashtable<String,String> EstadosPalabrasR;
    
    Lexico() {    
        EstadosPalabrasR=new Hashtable<String,String>();
        CargarPalabrasR(EstadosPalabrasR);
    }
    
    public static void CargarPalabrasR (Hashtable<String,String> lista){
        lista.put("while", "while");
        lista.put("for", "for");
        lista.put("if", "if");
        lista.put("else", "else");
        lista.put("log", "log");
        lista.put("leer", "leer");
        lista.put("funcion", "funcion");
        lista.put("false", "false");
        lista.put("true", "true");
	lista.put("nil", "true");
        lista.put("end", "end");
        lista.put("retorno", "retorno");
        lista.put("importar", "importar");
        lista.put("desde", "desde");
        lista.put("todo", "todo");
        lista.put("in", "token_in");
       ;
    }
    
     public static int imprimir(Token tokenTmp, String renglon, Hashtable<String,String> EstadosPalabrasR, int i){
        int col= tokenTmp.columna;
        
        switch(tokenTmp.tipo){
            case 1:
                if(!EstadosPalabrasR.containsKey(renglon.substring(col-1, i-1))){
                    tokenTmp.lexema=renglon.substring(col-1, i-1);
                    System.out.println("<id,"+tokenTmp.lexema+ ","+ tokenTmp.fila + ","+ tokenTmp.columna+ ">");
                }else{
                    tokenTmp.tipo=6;
                    tokenTmp.lexema=renglon.substring(col-1, i-1);
                    System.out.println("<"+tokenTmp.lexema + ","+ tokenTmp.fila + ","+ tokenTmp.columna+ ">");
                }
                return 0;
            case 2:{
                tokenTmp.lexema=renglon.substring(col-1, i-1);
                System.out.println("<token_integer,"+tokenTmp.lexema + ","+ tokenTmp.fila + ","+ col+ ">");
                return -1;
            }
            case 3:{
                tokenTmp.lexema=renglon.substring(col-1, i-1);
                System.out.println("<token_float,"+tokenTmp.lexema + ","+ tokenTmp.fila + ","+ col+ ">");
                return -1;
            }
            case 4:{
                tokenTmp.lexema=renglon.substring(col, i-1);
                System.out.println("<token_string,"+tokenTmp.lexema + ","+ tokenTmp.fila + ","+ col+ ">");
                return -1;
            }
            case 5:{
                System.out.println("<"+tokenTmp.lexema +","+ tokenTmp.fila + ","+ tokenTmp.columna+ ">");
            }
        }
        
        
        return 0;
    }
    
    public static int obtener (Token tokenTmp, String renglon, Hashtable<String,String> EstadosPalabrasR, int i){
        int col= tokenTmp.columna;
        
        switch(tokenTmp.tipo){
            case 1:
                if(!EstadosPalabrasR.containsKey(renglon.substring(col-1, i-1))){
                    tokenTmp.traduccion=renglon.substring(col-1, i-1);
                    tokenTmp.lexema="id";
                    //System.out.println("<id,"+tokenTmp.lexema+ ","+ tokenTmp.fila + ","+ tokenTmp.columna+ ">");
                }else{
                    tokenTmp.tipo=6;
                    tokenTmp.traduccion=renglon.substring(col-1, i-1);
                    tokenTmp.lexema=renglon.substring(col-1, i-1);
                    //System.out.println("<"+tokenTmp.lexema + ","+ tokenTmp.fila + ","+ tokenTmp.columna+ ">");
                }
                return 0;
            case 2:{
                tokenTmp.traduccion=renglon.substring(col-1, i-1);
                tokenTmp.lexema="valor_entero";
                //System.out.println("<token_integer,"+tokenTmp.lexema + ","+ tokenTmp.fila + ","+ col+ ">");
                return -1;
            }
            case 3:{
                tokenTmp.traduccion=renglon.substring(col-1, i-1);
                tokenTmp.lexema="valor_float";
                //System.out.println("<token_float,"+tokenTmp.lexema + ","+ tokenTmp.fila + ","+ col+ ">");
                return -1;
            }
            case 4:{
                tokenTmp.traduccion=renglon.substring(col, i-1);
                tokenTmp.lexema="valor_string";
                //System.out.println("<token_string,"+tokenTmp.lexema + ","+ tokenTmp.fila + ","+ col+ ">");
                return -1;
            }
            case 5:{
                //System.out.println("<"+tokenTmp.lexema +","+ tokenTmp.fila + ","+ tokenTmp.columna+ ">");
            }
        }
        
        
        return 0;
    }
    
     public static int delta(int estado, char c, Token t){
        switch(estado){
            case 0: 
                switch(c){
                    case ' ': return 0;
                    
                    case '0': case '1': case '2': case '3': case '4':
                    case '5': case '6': case '7': case '8': case '9':
                        return 1;
                    
                    case 'q': case 'w': case 'e': case 'r': case 't': case 'y': 
                    case 'u': case 'i': case 'o': case 'p': case 'a': case 's': 
                    case 'd': case 'f': case 'g': case 'h': case 'j': case 'k': 
                    case 'l': case 'z': case 'x': case 'c': case 'v': case 'b':
                    case 'n': case 'm': 
                    case 'Q': case 'W': case 'E': case 'R': case 'T': case 'Y': 
                    case 'U': case 'I': case 'O': case 'P': case 'A': case 'S':
                    case 'D': case 'F': case 'G': case 'H': case 'J': case 'K': 
                    case 'L': case 'Z': case 'X': case 'C': case 'V': case 'B': 
                    case 'N': case 'M': case '_': 
                        return 7;
                    
                    case '"':   
                        return 9;
                    case '.':   
                        return 3;
                    case '{': return 11;
                    case '}': return 12;
                    case ',': return 13;
                    case ':': return 14;
                    case '[': return 15;
                    case ']': return 16;
                    case '(': return 17;
                    case ')': return 18;
                    case '>': return 19;
                    case '<': return 22;
                    case '=': return 25;
                    case '!': return 27;
                    case '&': return 30;
                    case '|': return 32;
                    case '+': return 34;   
                    case '*': return 36;
                    case '/': return 39;
                    case '%': return 40;
                    case '^': return 41;
                    case '-': return 35;   
                    default : return -1;
                        
                    
                }
                
            case 1:
                switch(c){
                   
                    case '0': case '1': case '2': case '3': case '4':
                    case '5': case '6': case '7': case '8': case '9':
                        return 1;

                         case 'q': case 'w': case 'e': case 'r': case 't': case 'y': 
                    case 'u': case 'i': case 'o': case 'p': case 'a': case 's': 
                    case 'd': case 'f': case 'g': case 'h': case 'j': case 'k': 
                    case 'l': case 'z': case 'x': case 'c': case 'v': case 'b':
                    case 'n': case 'm':
                    case 'Q': case 'W': case 'E': case 'R': case 'T': case 'Y': 
                    case 'U': case 'I': case 'O': case 'P': case 'A': case 'S':
                    case 'D': case 'F': case 'G': case 'H': case 'J': case 'K': 
                    case 'L': case 'Z': case 'X': case 'C': case 'V': case 'B': 
                    case 'N': case 'M':
                    case '\'': case '!': case '"': case '$': case '%': case '&': 
                    case '(': case ')': case '*': case '+': case ',': case '-': 
                    case '/': case ':': case ';': case '<': case '=': 
                    case '>': case '?': case '@': case '[': case '\\': case ']': 
                    case '^': case '_': case '`': case '{': case '|': case '}': 
                    case '~': case ' ': 
                        return 2;
                        
                    case '.': 
                       return 3;
                    
                    default : return -1;
                }
            case 2:
                t.lexema = "valor_entero";t.tipo = 2; return -2;
          case 3:
                switch(c){
                    case '0': case '1': case '2': case '3': case '4':
                    case '5': case '6': case '7': case '8': case '9': 
                        return 5;
                        
                    case '\'': case '!': case '"': case '$': case '%': case '&': 
                    case '(': case ')': case '*': case '+': case ',': case '-': 
                    case '.': case '/': case ':': case ';': case '<': case '=': 
                    case '>': case '?': case '@': case '[': case '\\': case ']': 
                    case '^': case '_': case '`': case '{': case '|': case '}': 
                    case '~': case 'q': case 'w': case 'e': case 'r': case 't': case 'y': 
                    case 'u': case 'i': case 'o': case 'p': case 'a': case 's': 
                    case 'd': case 'f': case 'g': case 'h': case 'j': case 'k': 
                    case 'l': case 'z': case 'x': case 'c': case 'v': case 'b':
                    case 'n': case 'm':
                    case 'Q': case 'W': case 'E': case 'R': case 'T': case 'Y': 
                    case 'U': case 'I': case 'O': case 'P': case 'A': case 'S':
                    case 'D': case 'F': case 'G': case 'H': case 'J': case 'K': 
                    case 'L': case 'Z': case 'X': case 'C': case 'V': case 'B': 
                    case 'N': case 'M': case ' ': 
                        return 50;
                        
                    default : return -1;
                }
              
            case 50:
                t.traduccion = "."; t.lexema = "token_point";t.tipo = 5; return -1;
                
            case 4:
                t.lexema = "valor_entero";t.tipo = 2; return -2;
            case 5:
                switch(c){
                   
                    case '0': case '1': case '2': case '3': case '4':
                    case '5': case '6': case '7': case '8': case '9':
                        return 5;
                    case '\'': case '!': case '"': case '$': case '%': case '&': 
                    case '(': case ')': case '*': case '+': case ',': case '-': 
                    case '.': case '/': case ':': case ';': case '<': case '=': 
                    case '>': case '?': case '@': case '[': case '\\': case ']': 
                    case '^': case '_': case '`': case '{': case '|': case '}': 
                    case '~': case 'q': case 'w': case 'e': case 'r': case 't': case 'y': 
                    case 'u': case 'i': case 'o': case 'p': case 'a': case 's': 
                    case 'd': case 'f': case 'g': case 'h': case 'j': case 'k': 
                    case 'l': case 'z': case 'x': case 'c': case 'v': case 'b':
                    case 'n': case 'm':
                    case 'Q': case 'W': case 'E': case 'R': case 'T': case 'Y': 
                    case 'U': case 'I': case 'O': case 'P': case 'A': case 'S':
                    case 'D': case 'F': case 'G': case 'H': case 'J': case 'K': 
                    case 'L': case 'Z': case 'X': case 'C': case 'V': case 'B': 
                    case 'N': case 'M': case ' ': 
                        return 6;   
                    
                                                          
                }
            case 6:
                t.lexema = "valor_float";t.tipo = 3; return -2;
            
            case 7:
                switch(c){
                   
                    case '0': case '1': case '2': case '3': case '4':
                    case '5': case '6': case '7': case '8': case '9':
                    case 'q': case 'w': case 'e': case 'r': case 't': case 'y': 
                    case 'u': case 'i': case 'o': case 'p': case 'a': case 's': 
                    case 'd': case 'f': case 'g': case 'h': case 'j': case 'k': 
                    case 'l': case 'z': case 'x': case 'c': case 'v': case 'b':
                    case 'n': case 'm':
                    case 'Q': case 'W': case 'E': case 'R': case 'T': case 'Y': 
                    case 'U': case 'I': case 'O': case 'P': case 'A': case 'S':
                    case 'D': case 'F': case 'G': case 'H': case 'J': case 'K': 
                    case 'L': case 'Z': case 'X': case 'C': case 'V': case 'B': 
                    case 'N': case 'M': 
                        
                    case '_': 
                        return 7;
                        
                    case '\'': case '!': case '"': case '$': case '%': case '&': 
                    case '(': case ')': case '*': case '+': case ',':  
                    case '.': case '/': case ':': case ';': case '<': case '=': 
                    case '>': case '?': case '@': case '[': case '\\': case ']': 
                    case '^': case '`': case '{': case '|': case '}': 
                    case '~': case ' ': case '-':
                        return 8;
                     
                }
            case 8:
                t.lexema = "token_id";t.tipo = 1; return -2;
            
            case 9:
                switch(c){
                   
                    case '0': case '1': case '2': case '3': case '4':
                    case '5': case '6': case '7': case '8': case '9':
                    case 'q': case 'w': case 'e': case 'r': case 't': case 'y': 
                    case 'u': case 'i': case 'o': case 'p': case 'a': case 's': 
                    case 'd': case 'f': case 'g': case 'h': case 'j': case 'k': 
                    case 'l': case 'z': case 'x': case 'c': case 'v': case 'b':
                    case 'n': case 'm':
                    case 'Q': case 'W': case 'E': case 'R': case 'T': case 'Y': 
                    case 'U': case 'I': case 'O': case 'P': case 'A': case 'S':
                    case 'D': case 'F': case 'G': case 'H': case 'J': case 'K': 
                    case 'L': case 'Z': case 'X': case 'C': case 'V': case 'B': 
                    case 'N': case 'M': 
                    case '\'':  case '$': case '%': case '&': 
                    case '(': case ')': case '*': case '+': case ',': case '-': 
                    case '.': case '/': case ':': case ';': case '<': case '=':
                    case '>': case '?': case '@': case '[': case '\\': case ']': 
                    case '^': case '_': case '`': case '{': case '|': case '}': 
                    case '~': case ' ': case '#':
                    
                        return 9;
                        
                    case '"': return 10;
                     
                }
            case 10:
                t.lexema = "valor_string";t.tipo = 4; return -1;
                
            case 11:
                t.traduccion = "{"; t.lexema = "token_llave_izq";t.tipo = 5; return -1;
            case 12:
                t.traduccion = "}"; t.lexema = "token_llave_der";t.tipo = 5; return -1;
            case 13:
                t.traduccion = ","; t.lexema = "token_coma";t.tipo = 5; return -1;
            case 14:
                t.traduccion = ":"; t.lexema = "token_dosp";t.tipo = 5; return -1;
            case 15:
                t.traduccion = "["; t.lexema = "token_cor_izq";t.tipo = 5; return -1;
            case 16:
                t.traduccion = "]"; t.lexema = "token_cor_der";t.tipo = 5; return -1;
            case 17:
                t.traduccion = "("; t.lexema = "token_par_izq";t.tipo = 5; return -1;
            case 18:
                t.traduccion = ")"; t.lexema = "token_par_der";t.tipo = 5; return -1;
            case 19:
                switch(c){
                    case '=':
                        return 20;
                    
                    case '0': case '1': case '2': case '3': case '4':
                    case '5': case '6': case '7': case '8': case '9':
                    case 'q': case 'w': case 'e': case 'r': case 't': case 'y': 
                    case 'u': case 'i': case 'o': case 'p': case 'a': case 's': 
                    case 'd': case 'f': case 'g': case 'h': case 'j': case 'k': 
                    case 'l': case 'z': case 'x': case 'c': case 'v': case 'b':
                    case 'n': case 'm':
                    case 'Q': case 'W': case 'E': case 'R': case 'T': case 'Y': 
                    case 'U': case 'I': case 'O': case 'P': case 'A': case 'S':
                    case 'D': case 'F': case 'G': case 'H': case 'J': case 'K': 
                    case 'L': case 'Z': case 'X': case 'C': case 'V': case 'B': 
                    case 'N': case 'M':
                    case '\'': case '!': case '"': case '$': case '%': case '&': 
                    case '(': case ')': case '*': case '+': case ',': case '-': 
                    case '.': case '/': case ':': case ';': case '<':  
                    case '>': case '?': case '@': case '[': case '\\': case ']': 
                    case '^': case '_': case '`': case '{': case '|': case '}': 
                    case '~': case ' ':
                        return 21;
            
                }
            case 20:
                t.traduccion = ">="; t.lexema = "token_mayor_igual";t.tipo = 5; return -1;
            case 21:
                t.traduccion = ">"; t.lexema = "token_mayor";t.tipo = 5; return -2;    
            case 22:
                switch(c){
                    case '=':
                        return 23;
                    
                    case '0': case '1': case '2': case '3': case '4':
                    case '5': case '6': case '7': case '8': case '9':
                    case 'q': case 'w': case 'e': case 'r': case 't': case 'y': 
                    case 'u': case 'i': case 'o': case 'p': case 'a': case 's': 
                    case 'd': case 'f': case 'g': case 'h': case 'j': case 'k': 
                    case 'l': case 'z': case 'x': case 'c': case 'v': case 'b':
                    case 'n': case 'm':
                    case 'Q': case 'W': case 'E': case 'R': case 'T': case 'Y': 
                    case 'U': case 'I': case 'O': case 'P': case 'A': case 'S':
                    case 'D': case 'F': case 'G': case 'H': case 'J': case 'K': 
                    case 'L': case 'Z': case 'X': case 'C': case 'V': case 'B': 
                    case 'N': case 'M':
                    case '\'': case '!': case '"': case '$': case '%': case '&': 
                    case '(': case ')': case '*': case '+': case ',': case '-': 
                    case '.': case '/': case ':': case ';': case '<':  
                    case '>': case '?': case '@': case '[': case '\\': case ']': 
                    case '^': case '_': case '`': case '{': case '|': case '}': 
                    case '~': case ' ':
                        return 24;
            
                }
            case 23:
                t.traduccion = "<="; t.lexema = "token_menor_igual";t.tipo = 5; return -1;
            case 24:
                t.traduccion = "<"; t.lexema = "token_menor";t.tipo = 5; return -2;
            case 25:
                
                    //t.lexema = "token_igual_num";t.tipo = 5; return -1;
                
                    
                switch(c){
                    case '=':
                        return 26;
                    
                    case '0': case '1': case '2': case '3': case '4':
                    case '5': case '6': case '7': case '8': case '9':
                    case 'q': case 'w': case 'e': case 'r': case 't': case 'y': 
                    case 'u': case 'i': case 'o': case 'p': case 'a': case 's': 
                    case 'd': case 'f': case 'g': case 'h': case 'j': case 'k': 
                    case 'l': case 'z': case 'x': case 'c': case 'v': case 'b':
                    case 'n': case 'm':
                    case 'Q': case 'W': case 'E': case 'R': case 'T': case 'Y': 
                    case 'U': case 'I': case 'O': case 'P': case 'A': case 'S':
                    case 'D': case 'F': case 'G': case 'H': case 'J': case 'K': 
                    case 'L': case 'Z': case 'X': case 'C': case 'V': case 'B': 
                    case 'N': case 'M':
                    case '\'': case '!': case '"': case '$': case '%': case '&': 
                    case '(': case ')': case '*': case '+': case ',': case '-': 
                    case '.': case '/': case ':': case ';': case '<':  
                    case '>': case '?': case '@': case '[': case '\\': case ']': 
                    case '^': case '_': case '`': case '{': case '|': case '}': 
                    case '~': case ' ':
                        return 45;
                }
                        
                
            case 26:
                t.traduccion = "=="; t.lexema = "token_igual_num";t.tipo = 5; return -1;
	    case 45:
                t.traduccion = "="; t.lexema = "token_assign";t.tipo = 5; return -2;
            case 27:
                switch(c){
                    case '=':
                        return 29;
                    
                    case '0': case '1': case '2': case '3': case '4':
                    case '5': case '6': case '7': case '8': case '9':
                    case 'q': case 'w': case 'e': case 'r': case 't': case 'y': 
                    case 'u': case 'i': case 'o': case 'p': case 'a': case 's': 
                    case 'd': case 'f': case 'g': case 'h': case 'j': case 'k': 
                    case 'l': case 'z': case 'x': case 'c': case 'v': case 'b':
                    case 'n': case 'm':
                    case 'Q': case 'W': case 'E': case 'R': case 'T': case 'Y': 
                    case 'U': case 'I': case 'O': case 'P': case 'A': case 'S':
                    case 'D': case 'F': case 'G': case 'H': case 'J': case 'K': 
                    case 'L': case 'Z': case 'X': case 'C': case 'V': case 'B': 
                    case 'N': case 'M':
                    case '\'': case '!': case '"': case '$': case '%': case '&': 
                    case '(': case ')': case '*': case '+': case ',': case '-': 
                    case '.': case '/': case ':': case ';': case '<':  
                    case '>': case '?': case '@': case '[': case '\\': case ']': 
                    case '^': case '_': case '`': case '{': case '|': case '}': 
                    case '~': case ' ':
                        return 28;
            
                }
            case 28:
                t.traduccion = "!"; t.lexema = "token_not";t.tipo = 5; return -2;
            case 29:
                t.traduccion = "!="; t.lexema = "token_diff_num";t.tipo = 5; return -1;
            case 30:
                switch(c){
                    case '&':
                        return 31;
                    
                    case '0': case '1': case '2': case '3': case '4':
                    case '5': case '6': case '7': case '8': case '9':
                    case 'q': case 'w': case 'e': case 'r': case 't': case 'y': 
                    case 'u': case 'i': case 'o': case 'p': case 'a': case 's': 
                    case 'd': case 'f': case 'g': case 'h': case 'j': case 'k': 
                    case 'l': case 'z': case 'x': case 'c': case 'v': case 'b':
                    case 'n': case 'm':
                    case 'Q': case 'W': case 'E': case 'R': case 'T': case 'Y': 
                    case 'U': case 'I': case 'O': case 'P': case ' ': case 'A': case 'S':
                    case 'D': case 'F': case 'G': case 'H': case 'J': case 'K': 
                    case 'L': case 'Z': case 'X': case 'C': case 'V': case 'B': 
                    case 'N': case 'M':
                    case '\'': case '!': case '"': case '$': case '%':  
                    case '(': case ')': case '*': case '+': case ',': case '-': 
                    case '.': case '/': case ':': case ';': case '<': case '=':
                    case '>': case '?': case '@': case '[': case '\\': case ']': 
                    case '^': case '_': case '`': case '{': case '|': case '}': 
                    case '~': 
                        return -1;
            
                }
            case 31:
                t.traduccion = "&&"; t.lexema = "token_and";t.tipo = 5; return -1;
            case 32:
                switch(c){
                    case '|':
                        return 33;
                    
                    case '0': case '1': case '2': case '3': case '4':
                    case '5': case '6': case '7': case '8': case '9':
                    case 'q': case 'w': case 'e': case 'r': case 't': case 'y': 
                    case 'u': case 'i': case 'o': case 'p': case 'a': case 's': 
                    case 'd': case 'f': case 'g': case 'h': case 'j': case 'k': 
                    case 'l': case 'z': case 'x': case 'c': case 'v': case 'b':
                    case 'n': case 'm':
                    case 'Q': case 'W': case 'E': case 'R': case 'T': case 'Y': 
                    case 'U': case 'I': case 'O': case 'P': case 'A': case 'S':
                    case 'D': case 'F': case 'G': case 'H': case 'J': case 'K': 
                    case 'L': case 'Z': case 'X': case 'C': case 'V': case 'B': 
                    case 'N': case 'M':
                    case '\'': case '!': case '"': case '$': case '%': case '&':
                    case '(': case ')': case '*': case '+': case ',': case '-': 
                    case '.': case '/': case ':': case ';': case '<': case '=':
                    case '>': case '?': case '@': case '[': case '\\': case ']': 
                    case '^': case '_': case '`': case '{':  case '}': 
                    case '~': case ' ':
                        return -1;
            
                }
            case 33:
                t.traduccion = "||"; t.lexema = "token_or";t.tipo = 5; return -1;
            case 34:
                t.traduccion = "+"; t.lexema = "token_mas";t.tipo = 5; return -1;
            case 35:
                t.traduccion = "-"; t.lexema = "token_menos";t.tipo = 5; return -1;
            case 36:
                switch(c){
                    case '*':
                        return 37;
                    
                    case '0': case '1': case '2': case '3': case '4':
                    case '5': case '6': case '7': case '8': case '9':
                    case 'q': case 'w': case 'e': case 'r': case 't': case 'y': 
                    case 'u': case 'i': case 'o': case 'p': case 'a': case 's': 
                    case 'd': case 'f': case 'g': case 'h': case 'j': case 'k': 
                    case 'l': case 'z': case 'x': case 'c': case 'v': case 'b':
                    case 'n': case 'm':
                    case 'Q': case 'W': case 'E': case 'R': case 'T': case 'Y': 
                    case 'U': case 'I': case 'O': case 'P': case 'A': case 'S':
                    case 'D': case 'F': case 'G': case 'H': case 'J': case 'K': 
                    case 'L': case 'Z': case 'X': case 'C': case 'V': case 'B': 
                    case 'N': case 'M':
                    case '\'': case '!': case '"': case '$': case '%': case '&': 
                    case '(': case ')':  case '+': case ',': case '-': 
                    case '.': case '/': case ':': case ';': case '<':  case '=':
                    case '>': case '?': case '@': case '[': case '\\': case ']': 
                    case '^': case '_': case '`': case '{': case '|': case '}': 
                    case '~': case ' ':
                        return 38;
            
                }
            case 37:
                t.traduccion = "**"; t.lexema = "token_mul2";t.tipo = 5; return -1;
            case 38:
                t.traduccion = "*"; t.lexema = "token_mul";t.tipo = 5; return -2;
            case 39:
                t.traduccion = "/"; t.lexema = "token_div";t.tipo = 5; return -1;
            case 40:
                t.traduccion = "%"; t.lexema = "token_mod";t.tipo = 5; return -1;
            case 41:
                t.traduccion = "^"; t.lexema = "token_pot";t.tipo = 5; return -1;
            
       }
       return -1;//error
    }

    ArrayList<Token> obtenerTokens(String renglon, int fila) {
        ArrayList<Token> salida = new ArrayList<Token>();
        char arrayRenglon[]=renglon.toCharArray();
            
        int estado=0;
        Token tokenTmp = null;

        boolean error = false;

        for(int i=0;i<arrayRenglon.length;i++){ //cambia de esdo segun el DT; se queda en estado 0 si es caracter=' '

            if(arrayRenglon[i]=='#' && estado!=9){
                renglon = renglon.substring(0, i)+"  ";
                renglon.replace('#',' ');
                //renglon = renglon.substring(0, i-1)+"   ";
                arrayRenglon=renglon.toCharArray();
            }

            estado = delta(estado, arrayRenglon[i] ,tokenTmp);


            if (tokenTmp == null && estado !=0){
                tokenTmp =new Token(fila,i+1);                    
            }else if (tokenTmp != null && tokenTmp.tipo!=0){
                //el i cambia si hay que retroceder en la lectura de la cadena
                obtener(tokenTmp, renglon, EstadosPalabrasR, i);
                salida.add(tokenTmp);


                i=i+estado;
                estado=0;
                tokenTmp = null;
            } 

            if(estado==-1 || (i+1 == arrayRenglon.length && estado==9)){
                error = true;
                System.out.println(">>> Error lexico(linea:"+ tokenTmp.fila + ",posicion:"+ tokenTmp.columna+ ")");
                break;
            }
        }
        if (error);// error lexinco
        return salida;
    }
}   
class Token{
        
    public Token( int f, int c)
    {
        traduccion="";
        fila = f; 
        columna = c; 
        lexema="";
        tipo=0;//id=1; entero=2; real=3; string=4; operEspecial=5; palReservada=6;
    }

    public  String traduccion;
    public int fila ;
    public int columna ;
    public  String lexema;
    public  int tipo;

}

