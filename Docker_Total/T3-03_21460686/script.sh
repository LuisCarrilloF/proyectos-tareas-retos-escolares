#!/bin/bash

# Datos del estudiante
NOMBRE="Luis Angel Carrillo Flores"
NUM_CONTROL="21460686"
CARRERA="Ingeniería Informatica"

# 1. Crear par de llaves (privada y pública)
openssl genrsa -out privada.pem 2048
openssl rsa -in privada.pem -pubout -out publica.pem

# 2. Crear archivo tarea.md
cat > tarea.md <<EOF
# Datos del estudiante

- **Nombre:** $NOMBRE
- **Número de control:** $NUM_CONTROL
- **Carrera:** $CARRERA

## Pasos realizados:

### Crear llaves asimétricas:
\`\`\`bash
openssl genrsa -out privada.pem 2048
openssl rsa -in privada.pem -pubout -out publica.pem
\`\`\`

### Firmar archivo:
\`\`\`bash
openssl dgst -sha256 -sign privada.pem -out firma.bin tarea.md
\`\`\`

### Verificar firma:
\`\`\`bash
openssl dgst -sha256 -verify publica.pem -signature firma.bin tarea.md
\`\`\`

### Cifrado del archivo:
\`\`\`bash
openssl rsautl -encrypt -inkey publica.pem -pubin -in tarea.md -out tarea.enc
\`\`\`
EOF

# 3. Firmar tarea.md con SHA256
openssl dgst -sha256 -sign privada.pem -out firma.bin tarea.md

# 4. Verificar firma (opcional)
echo "Verificando firma:"
openssl dgst -sha256 -verify publica.pem -signature firma.bin tarea.md

# 5. Crear el .zip
ZIPNAME="T3-03_${NUM_CONTROL}.zip"
zip $ZIPNAME publica.pem tarea.md firma.bin

echo "Archivo $ZIPNAME generado correctamente."
