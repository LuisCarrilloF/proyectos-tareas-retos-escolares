#SISTEMA QUE AYUDA A PROGRAMAR TAREAS Y CUANDO SON COMPLETADAS SE CAMBIA EL ESTADO A COMPLETADA.
from pymongo import MongoClient
from bson.objectid import ObjectId

# Configurar conexión con MongoDB
client = MongoClient("mongodb://mongodb:27017/")
db = client["gestor_tareas_db"]
tasks_collection = db["tasks"]

def add_task(): 
    title = input("Ingrese el título de la tarea: ")
    description = input("Ingrese la descripción de la tarea: ")
    task = {"title": title, "description": description, "completed": False}
    tasks_collection.insert_one(task)
    print("Tarea añadida correctamente.")

def list_tasks():
    tasks = tasks_collection.find()
    for task in tasks:
        status = "COMPLETADA" if task["completed"] else "PENDIENTE"
        print(f"{task['_id']}: {task['title']} - {task['description']} [{status}]")

def update_task():
    task_id = input("Ingrese el ID de la tarea a actualizar: ")
    new_title = input("Nuevo título: ")
    new_description = input("Nueva descripción: ")
    tasks_collection.update_one({"_id": ObjectId(task_id)}, {"$set": {"title": new_title, "description": new_description}})
    print("Tarea actualizada correctamente.")

def delete_task():
    task_id = input("Ingrese el ID de la tarea a eliminar: ")
    tasks_collection.delete_one({"_id": ObjectId(task_id)})
    print("Tarea eliminada correctamente.")

def complete_task():
    task_id = input("Ingrese el ID de la tarea a marcar como completada: ")
    tasks_collection.update_one({"_id": ObjectId(task_id)}, {"$set": {"completed": True}})
    print("Tarea marcada como completada.")

def main():
    print("--- GESTOR DE TAREAS CON MONGODB ---")
    while True:
        print("\n--- MENU ---")
        print("[1] Añadir una tarea")
        print("[2] Listar Tareas")
        print("[3] Modificar una tarea existente")
        print("[4] Eliminar una tarea")
        print("[5] Marcar como COMPLETADA")
        print("[0] Salir ")
        opc = input("opcion: ")
        print("\n")
        if opc == "0":
            print("Gracias por usar el programa")
            break
        elif opc == "1":
            add_task()
        elif opc == "2":
            list_tasks()
        elif opc == "3":
            update_task()
        elif opc == "4":
            delete_task()
        elif opc == "5":
            complete_task()
        else:
            print("Opcion incorrecta, vuelva elegir una opcion valida.")

if __name__ == "__main__":
    main()
