import random
def main():
    print("GENERADOR DE CONTRASEÑAS SEGURAS.")
    valores = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~"
    password=""
    for i in range(0,50):
        caracter = random.choice(valores)
        password += caracter
    
    print("Tu contraseña es:")    
    print(password)
    
    
if __name__ == "__main__":
    main()