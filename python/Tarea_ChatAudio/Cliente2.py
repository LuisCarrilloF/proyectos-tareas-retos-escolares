from playsound import playsound
import socket
import threading
import sounddevice as sd
import numpy as np

# Configuracion del cliente
HOST = '192.168.97.154'
PORT = 12345

def grabar_audio(indata, frames, time, status):
    # Enviar datos de audio al servidor
    data = indata.tobytes()
    cliente.sendall(b'A' + data)

def manejar_audio_stream():
    # Configurar la transmisión de audio
    with sd.InputStream(callback=grabar_audio):
        print("Listo para grabar y enviar audio al servidor.")
        input()

def enviar_mensaje_texto():
    while True:
        mensaje = input(" mensaje: ")
        
        cliente.sendall(b'T' + mensaje.encode('utf-8'))

# Configurar el socket del cliente
with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as cliente:
    cliente.connect((HOST, PORT))
    
    # Iniciar hilos para la transmision de audio y el envio de mensajes de texto
    audio_thread = threading.Thread(target=manejar_audio_stream)
    texto_thread = threading.Thread(target=enviar_mensaje_texto)

    audio_thread.start()
    texto_thread.start()

    try:
        # Mantener el programa principal en ejecucion
        audio_thread.join()
        texto_thread.join()
    except KeyboardInterrupt:
        print("Cliente desconectado.")
