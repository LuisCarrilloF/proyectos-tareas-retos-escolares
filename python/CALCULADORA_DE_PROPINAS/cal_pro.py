def main():
    def MostrarPropina(monto):
        propina=monto*.1    
        print("La propina debe ser de $",propina)
        
        #Pues ya esta echo, aqui podemos jugar bastante, ya que puede crecer a mayor escala
        #Se puede agregar una seccion para comprar comida, los platillos, eventualmente puede ser un menu.
        
    print("Claculadora de Propinas")
    a=input("Ingrese el monto a pagar: ")
    monto=int(a)
    MostrarPropina(monto)
    
    
    
if __name__=="__main__":
    main()