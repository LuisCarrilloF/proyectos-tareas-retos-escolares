# gen_key_wifi.py - Generador de Claves WPA2/WPA3
#Autor:LUIS ANGEL CARRILLO FLORES
#Descripción: Este script genera una clave segura para redes WiFi WPA2/WPA3.
import random
import string
def main():
    def generar_clave_segura(longitud=20):
        # Conjuntos de caracteres
        caracteres_min = string.ascii_lowercase
        caracteres_may = string.ascii_uppercase
        digitos = string.digits
        simbolos = '!@#$%^&*()_+'
        todos = caracteres_min + caracteres_may + digitos + simbolos

        # Asegurar al menos uno de cada tipo y dos símbolos
        clave = [
            random.choice(caracteres_min),
            random.choice(caracteres_may),
            random.choice(digitos),
            random.choice(simbolos),
            random.choice(simbolos)
        ]

        # Completar hasta la longitud deseada
        clave += random.choices(todos, k=longitud - len(clave))
        random.shuffle(clave)
        return "".join(clave)
    print("Generador de Claves WPA2/WPA3")

    print("Contraseña WPA generada (20 caracteres):")
    print(generar_clave_segura())

if __name__ == "__main__":
    main()