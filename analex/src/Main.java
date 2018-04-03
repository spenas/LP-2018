import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/*
ERRORES ACTUALES:



 */


public class Main {

    //Declaracion de constantes, se convierten strings en listas, para poder usar la funcion .contains()
    public static final List ALFABETO = Arrays.asList("abcdefghijklmnopqrstuvwyz".split(""));
    public static final List NUMERO = Arrays.asList("1234567890".split(""));
    public static final List OPERACION = Arrays.asList("+*-/%^".split(""));
    public static final List ESPECIALES = Arrays.asList(".;:{}[]()#<>=!&|".split(""));



    //Se coge cada linea y se divide en bloques con los espacios en blanco
    public static void  analizar(String linea, int fila){
    String partes[] = linea.split(" ");
        for (int i = 0; i < partes.length ; i++) {
            reanalizar(partes[i], fila);
        }
    }
    //Se coge todos los bloques y se dividen en caracteres, para empezar a identificar tokens
    public static void reanalizar(String bloque, int fila){
        int columna = 0;


        String letra[] = bloque.split("");
        String palabra_prov = "";

       if(comienzaIlegal(letra)){
            imprimir("error", fila , columna);
            //Ta luego, no analiza mas
            return;
        }

        for (int i = 0; i < letra.length ; i++){
            //#Vamos a identificar si hay un id o palabra reservada
            if (ALFABETO.contains(letra[i]) || letra[i].equals("_")){
                boolean verificador = true;
                int contador = i ;
                while (verificador && contador < letra.length){
                    String actual = letra[contador];
                    if ((ESPECIALES.contains(actual) && !actual.equals("_")) || OPERACION.contains(actual) ){
                        break;
                    }
                    palabra_prov += actual;
                    contador++;
                }
                String compReservada = esReservada(palabra_prov);
                if(compReservada != null){
                    System.out.println("<"+compReservada+ "," + fila + "," + columna + ">");
                }
                else{
                    System.out.println("<id,"+palabra_prov+","+fila+","+columna+">");
                }

                i = contador;
            }

        }
    }

    //funcion para verificar que un id no comience por numero o por un caracter no valido para un id
    public static boolean comienzaIlegal( String[] letra ) {

        //Se mira si hay un id que comience por numero
        if (NUMERO.contains(letra[0])) {
            //Nos aseguramos de que en verdad se trate de un token y no de una expresion como "2+a"
            for (int i = 1; i < letra.length; i++) {
                if (ALFABETO.contains(letra[i])) {
                    return true;
                } else {
                    //Nos aseguramos de que se permita hacer por ejemplo: "2+a"
                    if (OPERACION.contains(letra[i])) {
                        return false;
                    }
                }
            }

        } else if (OPERACION.contains(letra[0]) || ESPECIALES.contains(letra[0]) && !letra[0].equals("_")) {
            for (int i = 1; i < letra.length; i++) {
                if (ALFABETO.contains(letra[i])) {
                    return true;
                }

            }
        }
            return false;
        }


    //Se verifica si el caracter es algun simbolo aritmetico o corchetes, parentesis etc
    public static boolean esSimbolo( String texto ){

        return false;
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
            case "in":
                return "in";
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


        }

        return  null;
    }
    public static void imprimir (String token, int fila , int colum){
        if (token.equals("error")){
            System.out.println(">>>Error Lexico(Linea: " + fila + ", Columna: " + colum +" ) ");
        }
    }


    public static void main(String[] args) {

        int cont_fila = 0;
        while(true){
            Scanner input = new Scanner(System.in);
            String linea = input.nextLine();
            analizar(linea,cont_fila);
            cont_fila++;
        }

    }
}
