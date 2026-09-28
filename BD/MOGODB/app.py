from flask import Flask, request, jsonify, render_template, redirect, url_for
from pymongo import MongoClient
from bson.objectid import ObjectId

app = Flask(__name__)

# Configura la conexión a MongoDB
client = MongoClient("mongodb://localhost:27017/")
db = client["todolist"]
tasks_collection = db["tasks"]

# Rutas

# Página principal - Lista de tareas
@app.route('/')
def home():
    tasks = list(tasks_collection.find())
    return render_template('index.html', tasks=tasks)

# Añadir una tarea
@app.route('/add', methods=['POST'])
def add_task():
    title = request.form['title']
    description = request.form['description']
    task = {"title": title, "description": description, "completed": False}
    tasks_collection.insert_one(task)
    return redirect(url_for('home'))

# Marcar tarea como realizada
@app.route('/complete/<task_id>')
def complete_task(task_id):
    tasks_collection.update_one({"_id": ObjectId(task_id)}, {"$set": {"completed": True}})
    return redirect(url_for('home'))

# Actualizar una tarea
@app.route('/update/<task_id>', methods=['GET', 'POST'])
def update_task(task_id):
    task = tasks_collection.find_one({"_id": ObjectId(task_id)})
    if request.method == 'POST':
        new_title = request.form['title']
        new_description = request.form['description']
        tasks_collection.update_one({"_id": ObjectId(task_id)}, {"$set": {"title": new_title, "description": new_description}})
        return redirect(url_for('home'))
    return render_template('update.html', task=task)

# Eliminar una tarea
@app.route('/delete/<task_id>')
def delete_task(task_id):
    tasks_collection.delete_one({"_id": ObjectId(task_id)})
    return redirect(url_for('home'))

if __name__ == '__main__':
    app.run(debug=True)
