from playsound import playsound
import socket
import threading

def receive_messages(client_socket):
    while True:
        data = client_socket.recv(1024)
        print(f"Recibido del servidor: {data.decode('utf-8')}")        
        playsound('pato.mp3')
        
def main():
    host = '192.168.97.154' #Host del servidor
    port = 5555

    client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client.connect((host, port))

    print(f"[*] Conectado al servidor en {host}:{port}")

    receive_thread = threading.Thread(target=receive_messages, args=(client,))
    receive_thread.start()

    while True:
        message = input()
        client.send(bytes(message, 'utf-8'))

if __name__ == "__main__":
    main()
