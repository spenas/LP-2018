#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*
Reglas
S -> A B uno
A -> dos B
A -> e
B -> C D
B -> tres
B -> e
C -> cuatro A B
C -> cinco
D -> seis
D -> e

*/

/*int S (){

}

int A (){

}

int B (){

}

int C(){

}

int D(){
    
}*/
int main(int argv,char *argc[]){

/*
    char* buf;
    char* partidos;
    FILE *gramatica;
    gramatica = fopen("gram.txt","r");
    if(!gramatica){
        perror("gram.txt");
        exit(1);
    }
    while (fgets(buf,1000, gramatica)!=NULL)
    {
        partidos = strtok(buf , " ");
        while(partidos != NULL)
        {
            printf("%s\t",partidos);
            partidos = strtok(NULL, " ");
        }
        
        
    }*/
    char* cadena;
    char* partidos;

    char condicion = '0';
    while(condicion != '1'){
        printf("Ingrese la cadena a analizar o 1 para salir: \n");
        scanf("%s",cadena);
        partidos = strtok(cadena, " ");
        printf("%s",cadena);
        
    }
  

  
}