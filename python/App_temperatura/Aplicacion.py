import serial
import openai
import serial.tools.list_ports

# Configura la clave de la API de OpenAI
openai.api_key = 'your-api-key-here'

# Listar puertos seriales disponibles
def list_serial_ports():
    ports = list(serial.tools.list_ports.comports())
    if ports:
        for port in ports:
            print(f"Puerto encontrado: {port.device}")
        return ports
    else:
        print("No se encontraron puertos seriales.")
        return None

# Verificar si el puerto COM3 o COM4 está disponible
def verify_serial_connection(port='COM3'):
    try:
        arduino = serial.Serial(port, 9600, timeout=1)
        print(f"Conexión exitosa con {port}")
        return arduino
    except serial.SerialException as e:
        print(f"Error al conectar con {port}: {e}")
        return None

# Obtener respuesta de ChatGPT
def get_chatgpt_response(prompt):
    try:
        response = openai.Completion.create(
            model="text-davinci-003",  # Cambia esto si necesitas usar otro modelo
            prompt=prompt,
            max_tokens=100
        )
        return response.choices[0].text.strip()
    except Exception as e:
        print(f"Error al obtener respuesta de ChatGPT: {e}")
        return "Lo siento, no pude obtener una respuesta."

# Configurar el puerto serial
def setup_serial_connection():
    # Verificar y listar puertos disponibles
    print("Buscando puertos seriales...")
    ports = list_serial_ports()
    if ports:
        # Intentamos conectar al primer puerto encontrado (COM3, COM4, etc.)
        for port in ports:
            arduino = verify_serial_connection(port.device)
            if arduino:
                return arduino
    return None

# Interactuar con el Arduino y enviar comandos
def interact_with_arduino(arduino, response):
    if "turn on the light" in response.lower():
        arduino.write(b'turn on\n')
    elif "turn off the light" in response.lower():
        arduino.write(b'turn off\n')
    elif "temperature" in response.lower():
        arduino.write(b'temperature\n')
        temp = arduino.readline().decode('utf-8').strip()
        print(f"Temperatura actual: {temp}°C")
    else:
        print("Comando no reconocido.")

# Función principal
def main():
    # Configurar la conexión serial con Arduino
    arduino = setup_serial_connection()
    if not arduino:
        print("No se pudo establecer una conexión serial.")
        return
    
    while True:
        # Solicitar entrada del usuario
        user_input = input("Tú: ")
        
        # Obtener respuesta de ChatGPT
        response = get_chatgpt_response(user_input)
        print(f"ChatGPT: {response}")
        
        # Enviar comando a Arduino basado en la respuesta de ChatGPT
        interact_with_arduino(arduino, response)

# Ejecutar la función principal
if __name__ == "__main__":
    main()
