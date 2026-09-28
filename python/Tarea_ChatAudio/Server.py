from playsound import playsound
import socket
import threading
import sounddevice as sd
import numpy as np

HOST = '0.0.0.0'
PORT = 55555

clientes = []
lock = threading.Lock()

def manejar_cliente(cliente, direccion):
    print(f"Conexión establecida desde {direccion}")

    with lock:
        clientes.append(cliente)

    try:
        while True:
            tipo_mensaje = cliente.recv(1).decode('utf-8')

            if tipo_mensaje == 'T':
                mensaje_texto = cliente.recv(1024).decode('utf-8')
                print(f"Mensaje de {direccion}: {mensaje_texto}")

                # Reenviar mensaje a otros clientes
                with lock:
                    for c in clientes:
                        if c != cliente:
                            c.sendall(b'T' + mensaje_texto.encode('utf-8'))

            elif tipo_mensaje == 'A':
                data = bytearray()
                while True:
                    audio_chunk = cliente.recv(1024)
                    if audio_chunk == b'':
                        break
                    data.extend(audio_chunk)

                # Reenviar audio a otros clientes
                with lock:
                    for c in clientes:
                        if c != cliente:
                            c.sendall(b'A' + data)

    except Exception as e:
        print(f"Error en la conexión con {direccion}: {e}")
    finally:
        with lock:
            clientes.remove(cliente)
            cliente.close()
            print(f"Conexión con {direccion} cerrada")

def enviar_mensaje_texto():
    while True:
        mensaje = input("Escribe un mensaje para enviar a todos los clientes: ")
        with lock:
            for cliente in clientes:
                cliente.sendall(b'T' + mensaje.encode('utf-8'))

def enviar_audio():
    while True:
        input("Presiona Enter para enviar un audio a todos los clientes...")
        with lock:
            for cliente in clientes:
                # Aquí puedes implementar la lógica para leer un archivo de audio
                # y enviarlo al cliente.
                pass

def main():
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as servidor:
        servidor.bind((HOST, PORT))
        servidor.listen()

        print(f"Servidor escuchando en {HOST}:{PORT}")

        # Iniciar hilo para enviar mensajes de texto desde el servidor
        texto_thread = threading.Thread(target=enviar_mensaje_texto)
        texto_thread.start()

        # Iniciar hilo para enviar audio desde el servidor
        audio_thread = threading.Thread(target=enviar_audio)
        audio_thread.start()

        while True:
            cliente, direccion = servidor.accept()
            cliente_thread = threading.Thread(target=manejar_cliente, args=(cliente, direccion))
            cliente_thread.start()

if __name__ == "__main__":
    main()

