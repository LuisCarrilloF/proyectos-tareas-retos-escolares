#Autor: Luis Angel Carrillo Flores.
def main():
    def chechSum(clabe_bancaria):
        print("VERIFICACION:")
        if len(clabe_bancaria) != 18 or not clabe_bancaria.isdigit():
            print(" La CLABE debe tener exactamente 18 dígitos numéricos. clave correcta: {clabe_bancaria}")
            return
    
        factores = [3, 7, 1] 
        suma = 0  
        for i in range(17):
            suma += (int(clabe_bancaria[i]) * factores[i % 3]) % 10  

        digito_verificador_calculado = (10 - (suma % 10)) % 10

        if digito_verificador_calculado == int(clabe_bancaria[17]):
            print(" La CLABE es válida.")
        else:
            print(f" La CLABE es inválida. El dígito verificador debería ser {digito_verificador_calculado}.")
    
    
    print("\n---PROGRAMA PARA VALIDAR UNA CLAVE INTERBANCARIA----")
    while(True):
        print("\nIngrese [0] para salir del programa")
        clabe_bancaria=input("Ingrese su clabe bancaria:")
        if (clabe_bancaria=="0"):
            print("Gracias por usar el programa.")
            break
        chechSum(clabe_bancaria)


if __name__ == "__main__":
    main()