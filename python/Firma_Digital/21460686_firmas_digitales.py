from cryptography.hazmat.primitives.asymmetric import rsa, padding
from cryptography.hazmat.primitives import serialization, hashes

# ==========================
# 1. Generación de claves
# ==========================
private_key = rsa.generate_private_key(
    public_exponent=65537,
    key_size=2048
)

public_key = private_key.public_key()

pem_private = private_key.private_bytes(
    encoding=serialization.Encoding.PEM,
    format=serialization.PrivateFormat.PKCS8,
    encryption_algorithm=serialization.NoEncryption()
)

pem_public = public_key.public_bytes(
    encoding=serialization.Encoding.PEM,
    format=serialization.PublicFormat.SubjectPublicKeyInfo
)

print("Clave privada:\n", pem_private.decode())
print("Clave pública:\n", pem_public.decode())

# ==========================
# 2. Firmar un mensaje
# ==========================
mensaje = b"Este es mi mensaje secreto"

firma = private_key.sign(
    mensaje,
    padding.PSS(
        mgf=padding.MGF1(hashes.SHA256()),
        salt_length=padding.PSS.MAX_LENGTH
    ),
    hashes.SHA256()
)

print("\nFirma generada:\n", firma.hex())

# ==========================
# 3. Verificar la firma
# ==========================
try:
    public_key.verify(
        firma,
        mensaje,
        padding.PSS(
            mgf=padding.MGF1(hashes.SHA256()),
            salt_length=padding.PSS.MAX_LENGTH
        ),
        hashes.SHA256()
    )
    print("\n La firma es válida. El mensaje no ha sido alterado.")   
except Exception:
    print("\n La firma NO es válida.")
