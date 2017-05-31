import os

encryptionKey = os.urandom(16)

with open('encryption.key', 'wb') as file:
	file.write(encryptionKey)
	file.close()
