import socket
import threading

def handle_client(client_socket):
    while True:
        data = client_socket.recv(1024)
        if not data:
            break
        print(f"Recibido del cliente: {data.decode('utf-8')}")
        response = input("Respuesta: ")
        client_socket.send(bytes(response, 'utf-8'))
    client_socket.close()

def main():
    host = '127.0.0.1'
    port = 5555

    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server.bind((host, port))
    server.listen()

    print(f"[*] Servidor escuchando en {host}:{port}")

    while True:
        client, addr = server.accept()
        print(f"[*] Conexión aceptada de {addr[0]}:{addr[1]}")

        client_handler = threading.Thread(target=handle_client, args=(client,))
        client_handler.start()

if __name__ == "__main__":
    main()
