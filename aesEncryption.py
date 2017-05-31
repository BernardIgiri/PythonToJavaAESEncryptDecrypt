import pyaes, base64, os

key = None
plaintext = None
readCypherText = None
readIv = None
iv = os.urandom(16)

with open('encryption.key', 'rb') as file:
	key=file.read()
	file.close()

with open('sampleFile.txt', 'r') as file:
	plaintext=file.read()
	file.close()

encrypter = pyaes.Encrypter(pyaes.AESModeOfOperationCBC(key, iv=iv))
cyphertext = encrypter.feed(plaintext) + encrypter.feed()

with open('encryptedFile', 'w') as file:
	file.write(base64.b64encode(iv + cyphertext).decode('utf8'))
	file.close()

with open('encryptedFile', 'r') as file:
	content = file.read()
	decodedContent = base64.b64decode(content);
	readIv = decodedContent[:16]
	readCypherText = decodedContent[16:]
	file.close()

decrypter = pyaes.Decrypter(pyaes.AESModeOfOperationCBC(key, iv=iv))
decrypted = (decrypter.feed(readCypherText) + decrypter.feed()).decode('utf8')

print (plaintext == decrypted)
print (decrypted)
