import java.util.Arrays;
import java.util.Scanner;

public class Main {
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
        String[] alfabeto = "abcdefghijklmnopqrstuvwyz".split("");
        String[] numeros = "1234567890".split("");

        String letra[] = bloque.split("");
        String palabra_prov = "";
        //Se mira si hay un id que comience por numero
        if (Arrays.asList(numeros).contains(letra[0]) && Arrays.asList(alfabeto).contains(letra[1])){
            imprimir("error", fila , columna);
        }

        for (int i = 0; i < letra.length ; i++){

        }
    }

    //Se verifica si el caracter es algun simbolo aritmetico o corchetes, parentesis etc
    public boolean esSimbolo( String texto ){
        return false;
    }
    //Se verifica si el token es palabra reservada
    public boolean esReservada( String texto ){
        return  false;
    }
    public static void imprimir (String token, int fila , int colum){
        if (token.equals("error")){
            System.out.println(">>>Error Lexico(Linea: " + fila + ", Columna: " + colum +" ) ");
        }
    }


    public static void main(String[] args) {
        /*String prueba = "a1+a2 + 4";
        String prueba2[] = prueba.split("" );
        for (int i = 0; i < prueba2.length ; i++) {
            System.out.println(prueba2[i]);
        }*/
        int cont_fila = 0;
        while(true){
            Scanner input = new Scanner(System.in);
            String linea = input.nextLine();
            analizar(linea,cont_fila);
            cont_fila++;
        }

    }
}
