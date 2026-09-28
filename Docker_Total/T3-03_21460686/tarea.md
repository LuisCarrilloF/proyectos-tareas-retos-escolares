# Datos del estudiante

- **Nombre:** Luis Angel Carrillo Flores
- **Número de control:** 21460686
- **Carrera:** Ingeniería Informatica

## Pasos realizados:

### Crear llaves asimétricas:
```bash
openssl genrsa -out privada.pem 2048
openssl rsa -in privada.pem -pubout -out publica.pem
```

### Firmar archivo:
```bash
openssl dgst -sha256 -sign privada.pem -out firma.bin tarea.md
```

### Verificar firma:
```bash
openssl dgst -sha256 -verify publica.pem -signature firma.bin tarea.md
```

### Cifrado del archivo:
```bash
openssl rsautl -encrypt -inkey publica.pem -pubin -in tarea.md -out tarea.enc
```
