def main():
    def diagnostico(sintoma1, sintoma2, sintoma3):
        print("Hola")
        if sintoma1 =="si" and sintoma2 =="si" and sintoma3=="si" :
            print("Muy pposiblemente tienes covid 19, acude con tu medico y realizate una prueba.")
        elif (sintoma1 =="si" or sintoma2 =="si" or sintoma3=="si"):
            print("Posible caso de COVID-19, realiza un estudio para descartar")
        elif (sintoma1 =="no" or sintoma2 =="no" or sintoma3=="no"):
            print("No precentas sintomas, de igual forma sigue cuidando de tu persona.")
    while True:
        print("Hola mundo.")
        break        
    print("DIAGNOSTICO DE COVID 19 (Contesta con si/no)")
    sintoma1= input("¿Tienes fiebre? ")
    sintoma2 = input("¿Tienes dolor de cabeza? ")
    sintoma3 = input("¿Tienes Dificultad para hablar? ")
    diagnostico(sintoma1, sintoma2, sintoma3)

    
    print("Fin del sistema.")    
    

if __name__ == "__main__":
    main()