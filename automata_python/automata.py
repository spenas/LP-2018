from sys import stdin
import sys

alfabeto = []
estados = []
aceptacion = []
estados_in = []


print "Ingrese estado inicial"
inicial = stdin.readline().strip()



#Abrir alfabeto
a = open("alfabeto", "r")
alfabeto = a.readline().strip().split()


#Abrir estados
a = open("estados" , "r")
estados = a.readline().strip().split()

    

#Abrir estados aceptacion
a = open("aceptacion" , "r")
aceptacion = a.readline().strip().split()


f_transicion = {}
#Abrir funcion de trnacision
a = open("funcion", "r")

nline = 0
for line in a:
    elem = line.strip().split()
    f_transicion[str(nline)] = {}
    for i in range(len(elem)):
        f_transicion[str(nline)][alfabeto[i]] = elem[i]

    nline += 1


estado_actu = inicial
print "Estado inicial: "
print inicial
print "Alfabeto: "
print alfabeto
print "Estados: "
print estados
print "Estados finales o de aceptacion"
print aceptacion
print "Funcion de transicion:"
print f_transicion


print "Ingrese la cadena a probar"
cadena = stdin.readline().strip()

#Se mira en la funcion de trancision a que estado lleva el simbolo ingresado
for simbolo in cadena:
    estado_actu = f_transicion[estado_actu][simbolo]

if estado_actu in aceptacion:
    print "La cadena [" + cadena + "] es aceptada."
else:
    print "La cadena [" + cadena + "] no es aceptada."





    

    

