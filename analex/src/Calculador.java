import sun.tools.jar.Main;

import java.io.*;
import java.util.*;

public class Calculador {
    private static HashMap<String, ArrayList<String>> primeros = new HashMap<String, ArrayList<String>>();


    public static boolean esTerminal(String simbolo) {
        if (Character.isUpperCase(simbolo.charAt(0)))
            return true;

        return false;
    }

    public static ArrayList<String> separador(String cadena) {
        String[] arr = cadena.split("=>");
        String nombre = arr[0];

        ArrayList<String> resultado = new ArrayList<>();
        resultado.add(nombre);
        String[] arr2 = arr[1].split(" ");

        for (int i = 1; i < arr2.length; i++) {
            resultado.add(arr2[i]);
        }

        return resultado;
    }




    public static void primeros(ArrayList<String> reglas) {

        String llave = reglas.get(0);
//Se verfica si ya se esta trabajando sobre ese no terminal
        ArrayList<String> conjunto = new ArrayList<String>();
        if (primeros.containsKey(llave))
        {
            conjunto = primeros.get(llave);
        }


        //Se determina el primero de esa regla
        if (reglas.get(1).compareTo("epsilon") == 1) {
            //El caso del que el primer simbolo sea un epsilon
            if(reglas.size() >= 2 )
            {
                conjunto.add(reglas.get(2));
            }
            else if (reglas.size() == 1 && !conjunto.contains("epsilon"))
            {
                conjunto.add("epsilon");
            }


        }
        else
            {
                //En caso de que sea un terminal o no terminal se agrega y se verifica después
                conjunto.add(reglas.get(1));
            }

        primeros.put(llave,conjunto);
        System.out.println(primeros);
    }
    public  static ArrayList<String> desmenuzar(ArrayList<String> inicial, ArrayList<String> finale)
    {

    }
    //Se mira que el conjunto de primeros contenga solo terminales
    public static void  completar_prim(){
        for (Map.Entry<String,ArrayList<String>> entry : primeros.entrySet()) {

            for (String juan:entry.getValue()) {
                if (!esTerminal(juan))
                {
                    completar_prim();
                }
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Ingrese nombre archivo: ");
        String nombre = input.nextLine();
        File archivo = new File (nombre);
        FileReader fr = new FileReader (archivo);
        BufferedReader br = new BufferedReader(fr);
        String linea;

        while((linea=br.readLine())!= null) {
            ArrayList<String> separados = new ArrayList<String>();
            separados = separador(linea);
            //System.out.println(separados);
            primeros(separados);
            }
            completar_prim();
            br.close();


    }

}
