import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/*
ERRORES ACTUALES:
-Identifica cuando hay un error lexico (un id que comienza por numero) pero sigue analizando y ahi si encuentra un id.
Ejemplo:
1b
>>>Error Lexico(Linea: 0, Columna: 0 )
<id,b,0,0>


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

       if(comienzaNumero(letra)){
            imprimir("error", fila , columna);
        }

        for (int i = 0; i < letra.length ; i++){
            //#Vamos a identificar si hay un id o palabra reservada
            if (ALFABETO.contains(letra[i])){
                boolean verificador = true;
                int contador = i ;
                while (verificador && contador < letra.length){
                    String actual = letra[contador];
                    if (ESPECIALES.contains(actual) || OPERACION.contains(actual) ){
                        break;
                    }
                    palabra_prov += actual;
                    contador++;
                }
                System.out.println("<id,"+palabra_prov+","+fila+","+columna+">");
                i = contador;
            }

        }
    }

    //funcion para verificar que un id no comience por numero
    public static boolean comienzaNumero( String[] letra ){

        //Se mira si hay un id que comience por numero
        if (NUMERO.contains(letra[0]) ){
            //Nos aseguramos de que en verdad se trate de un token y no de una expresion como "2+a"
            for (int i = 1; i < letra.length ; i++){
                if(ALFABETO.contains(letra[i])){
                   return true;
                }
                else{
                    //Nos aseguramos de que se permita hacer por ejemplo: "2+a"
                    if (OPERACION.contains(letra[i])){
                        return false;
                    }
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
    public static boolean esReservada( String texto ){
        return  false;
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
