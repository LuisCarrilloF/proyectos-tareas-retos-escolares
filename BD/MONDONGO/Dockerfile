FROM ubuntu:20.04

# Evita la interacción durante la instalación
ENV DEBIAN_FRONTEND=noninteractive

# Actualiza e instala python y otras dependencias necesarias
RUN apt-get update && \
    apt-get install -y --no-install-recommends python3 python3-pip wget gnupg2 lsb-release && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Establece el directorio de trabajo
WORKDIR /usr/src/app

# Copia el archivo de requisitos y luego instala las dependencias de Python
COPY requirements.txt ./
RUN pip3 install --no-cache-dir -r requirements.txt

# Copia el resto de la aplicación
COPY . .

# Comando para ejecutar la aplicación
CMD ["python3", "app.py"]
