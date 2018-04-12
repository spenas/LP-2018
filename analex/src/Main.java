import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.*;
import java.io.*;

/*
ERRORES ACTUALES:



 */


public class Main {

    //Declaracion de constantes, se convierten strings en listas, para poder usar la funcion .contains()
    private static final List ALFABETO = Arrays.asList("abcdefghijklmnopqrstuvwyzABCDEFGHIJKLMNOPQRSTUVWYZ".split(""));
    private static final List NUMERO = Arrays.asList("1234567890".split(""));
    private static final List OPERACION = Arrays.asList("+*-/%^".split(""));
    private static final List ESPECIALES = Arrays.asList(".;:#<>=!&,|\"'".split(""));
    private static final List AGRUPACION = Arrays.asList("(){}[]".split(""));




    //Se coge todos los bloques y se dividen en caracteres, para empezar a identificar tokens
    public static void analizar(String bloque, int fila){
        int columna = 1;

        String letra[] = bloque.split("");


        //-------------------------------***********************************----------------------------------
        //Se empieza a recorrer letra por letra
        for (int i = 0; i < letra.length ; i++) {
            //Se detecta un espacio
            if (letra[i].equals(" ") || letra[i].equals("\t")) {
                columna++;
            }
            //-------------------------------***********************************----------------------------------
            else if (NUMERO.contains(letra[i])) {
                // Comprobar que no sea un id que comience por numero

                String num_prov = "";
                boolean verificador = false;
                int contador = i;
                while (contador < letra.length) {
                    String actual = letra[contador];
                    if (ALFABETO.contains(actual) || (ESPECIALES.contains(actual) && !actual.equals(".")&& !actual.equals(":") && !actual.equals(","))) {
                        if (verificador) {
                            System.out.println("<token_double, " + num_prov + "," + fila + "," + columna + " >");
                            columna++;
                        } else {
                            System.out.println("<token_integer, " + num_prov + "," + fila + "," + columna + " >");
                            columna++;
                        }
                        imprimir("error", fila, columna);
                        return;
                    } else if (actual.equals(" ") || OPERACION.contains(actual) || actual.equals(":") || actual.equals(",") || AGRUPACION.contains(actual)) {
                        break;
                    } else if (actual.equals(".")) {
                        if (!verificador) {
                            if (contador != letra.length - 1 && NUMERO.contains(letra[contador + 1])) {
                                verificador = true;
                            } else {
                                System.out.println("<token_integer, " + num_prov + "," + fila + "," + columna + " >");
                                columna++;
                                imprimir("error", fila, columna);
                                return;
                            }

                        } else {
                            System.out.println("<token_double, " + num_prov + "," + fila + "," + columna + " >");
                            columna++;
                            imprimir("error", fila, columna);
                            return;
                        }
                    }
                    num_prov += actual;
                    contador++;
                }
                //Se imprime el numero dependiendo de lo que sea
                if (verificador) {
                    System.out.println("<token_double, " + num_prov + "," + fila + "," + columna + " >");
                    columna = columna + 1;
                } else {
                    System.out.println("<token_integer, " + num_prov + "," + fila + "," + columna + " >");
                    columna++;
                }

                i = contador - 1;

            }


            //-------------------------------***********************************----------------------------------
            //#Vamos a identificar si hay un id o palabra reservada
            else if (ALFABETO.contains(letra[i]) || letra[i].equals("_")) {


                String palabra_prov = "";
                int contador = i;
                while (contador < letra.length) {
                    String actual = letra[contador];
                    if ((ESPECIALES.contains(actual) && !actual.equals("_")) || OPERACION.contains(actual) || actual.equals(" ") || AGRUPACION.contains(actual)) {
                        break;
                    }
                    palabra_prov += actual;
                    contador++;
                }
                String compReservada = esReservada(palabra_prov);
                if (compReservada != null) {
                    System.out.println("<" + compReservada + "," + fila + "," + columna + ">");
                    columna++;
                } else {
                    System.out.println("<id," + palabra_prov + "," + fila + "," + columna + ">");
                    columna++;
                }

                i = contador - 1;
            }


            //-------------------------------***********************************----------------------------------
            //revisar si hay un simbolo u operacion
            else if (ESPECIALES.contains(letra[i]) || OPERACION.contains(letra[i]) || AGRUPACION.contains(letra[i])) {


                //vamos a asegurarnos que no hay fin de linea

                boolean fin = (i +1  == letra.length);


                switch (letra[i]) {


                    case "\"": {
                        if (i != letra.length - 1) {
                            List aux_arr = Arrays.asList(Arrays.copyOfRange(letra, i + 1, letra.length));
                            if (aux_arr.contains("\"")) {
                                int indice = (i + 1) + aux_arr.indexOf("\"");
                                //Se detecto una cadena y se procede a imprimirla
                                String cadena = "";

                                for (int a = i + 1; a < indice; a++) {
                                    cadena += letra[a];

                                }

                                System.out.println("<string," + cadena + "," + fila + "," + columna + ">");
                                columna++;
                                i = indice;

                            } else {
                                imprimir("error", fila, columna);
                                return;
                            }
                        } else {
                            imprimir("error", fila, columna);
                            return;
                        }
                    break;
                    }
                    //Se detectan los caracteres que pueden ser dobles o simples
                    case "<": {
                        if (!fin && letra[i+1].equals("=")){
                            imprimir(esSimbolo("<="),fila,columna);
                            i++;
                        }
                        else{
                            imprimir(esSimbolo("<"),fila,columna);
                        }

                        break;
                    }

                    case ">": {
                        if (!fin && letra[i+1].equals("=")){

                            imprimir(esSimbolo(">="),fila,columna);
                            i++;
                        }
                        else{

                            imprimir(esSimbolo(">"),fila,columna);
                        }
                        break;
                    }

                    case "|": {
                        if (!fin && letra[i+1].equals("|")){
                            imprimir(esSimbolo("||"),fila,columna);
                            i++;
                        }
                        else{
                            imprimir("error",fila,columna);
                            return;
                        }
                        break;
                    }

                    case "&": {
                        if (!fin && letra[i+1].equals("&")){
                            imprimir(esSimbolo("&&"),fila,columna);
                            i++;
                        }
                        else{
                            imprimir("error",fila,columna);
                            return;
                        }
                        break;
                    }

                    case "=":{
                        if (!fin && letra[i+1].equals("=")){
                            imprimir(esSimbolo("=="),fila,columna);
                            i++;
                        }
                        else{
                            imprimir(esSimbolo("="),fila,columna);
                        }
                        break;
                    }

                    case "!":{

                        if (!fin && letra[i+1].equals("=")){
                            imprimir(esSimbolo("!="),fila,columna);
                            i++;
                        }
                        else{
                            imprimir(esSimbolo("!"),fila,columna);
                        }
                        break;

                    }

                    default:{
                        if(esSimbolo(letra[i]) == null){
                            imprimir("error",fila,columna);
                            return;
                        }
                        else
                        {
                            imprimir(esSimbolo(letra[i]),fila,columna);
                            columna++;
                        }
                        break;
                    }

                }
                columna++;

        }

            //No reconoció esa vaina
            else  {
                imprimir("error", fila, columna);
                return;
            }


        }
    }



    //Se verifica si el caracter es algun simbolo aritmetico o corchetes, parentesis etc
    public static String esSimbolo( String texto ){

        Hashtable<String,String> car_res =  new Hashtable<String, String>();
        car_res.put("{","token_llave_izq"); car_res.put("}","token_llave_der"); car_res.put("#","token_com"); car_res.put("[","token_cor_izq");
        car_res.put("]","token_cor_der"); car_res.put("(","token_par_izq"); car_res.put(")","token_par_der"); car_res.put(">","token_mayor");
        car_res.put("<","token_menor"); car_res.put(">=","token_mayor_igual"); car_res.put("<=","token_menor_igual"); car_res.put("in","token_in");
        car_res.put("==","token_igual_num"); car_res.put(".","token_point"); car_res.put("!=","token_diff_num"); car_res.put("&&","token_and");
        car_res.put("||","token_or"); car_res.put("!","token_not"); car_res.put("+","token_mas"); car_res.put("-","token_menos");
        car_res.put("*","token_mul"); car_res.put("/","token_div"); car_res.put("%","token_mod"); car_res.put("^","token_pot");car_res.put("=","token_eq");car_res.put(",","token_comma");
        car_res.put(":","token_dos_puntos");



        return car_res.get(texto);
    }

    //Se verifica si el token es palabra reservada
    public static String esReservada( String texto ){
        switch (texto){
            case "log":
                return "log";
            case "end":
                return "end";
            case "while":
                return "while";
            case "funcion":
                return "funcion";
            case "retorno":
                return "retorno";
            case "for":
                return "for";
            case "fabs":
                return "fabs";
            case "pow":
                return "pow";
            case "sqrt":
                return "sqrt";
            case "cos":
                return "cos";
            case "sin":
                return "sin";
            case "if":
                return "if";
            case "in":
                return "in";


        }

        return  null;
    }
    public static void imprimir (String token, int fila , int colum){
        if (token.equals("error")){
            System.out.println(">>>Error Lexico(Linea: " + fila + ", Columna: " + colum +" ) ");
        }
        else {
            System.out.println("< "+token+"," + fila + "," + colum +" > ");
        }
    }


    public static void main(String[] args) throws FileNotFoundException, IOException {
        String decision = "";
        int cont_fila = 1;
        Scanner input = new Scanner(System.in);
        System.out.println("Ingrese a para cargar un archivo, b para trabajar en consola");
        decision = input.nextLine();

        switch (decision){
            case "a":{
                System.out.println("Ingrese nombre archivo: ");
                String nombre = input.nextLine();
                 File archivo = new File (nombre);
            FileReader fr = new FileReader (archivo);
            BufferedReader br = new BufferedReader(fr);
            String linea;

        while((linea=br.readLine())!= null) {
            if (!linea.isEmpty()){
                analizar(linea,cont_fila);
            }
            cont_fila++;

        }
        br.close();
            }

            case "b":{

                while(input.hasNext()){

                    String linea = input.nextLine();
                    if (!linea.isEmpty()){
                        analizar(linea,cont_fila);
                    }
                    cont_fila++;
                }
            }

            default:
                System.out.println("No ingreso opcion valida");

        }




    }
}
