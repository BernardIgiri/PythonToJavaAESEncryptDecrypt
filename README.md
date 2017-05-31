# AES Encryption in Python decoded in Java

This project was an attempt to encrypt short text file using AES encyption in a Python script and decode it in Java. I needed the encrypted file to also remain in plaintext and I need to store the key on disk in binary format.

To achieve this I installed the pyaes library through:
```
pip install pyaes --user
```
Then I downloaded the Apache Commons Codec library version 1.10 to compile with my Java code.
(NOTE: To run the java code you will need to download commons-codec-1.10.jar and include it in the class path of the Java code.)

Initially I attempted to use CTR encryption in PyAES but the Java library expected an IV while the Python library didn't seem to have that feature. When I settled on using CBC I didn't understand what I should do with the IV. Eventually, I figured I can just add the IV to the encrypted file as the first 16 bytes.

Since the output needs to be plaintext, I used Base64 encoding on the entire file which is what I needed the Apache Commons Codec for.

Additionally, I needed a solution for padding since my source file was not 16 byte aligned and CBC requires it. To address that I used PKS7 padding which seems to be the common solution.

Thus, my final solution involved four primary steps for encryption. First I generated a random 16 bit IV. Then I encrypted the plain text input using that IV and the key file. Then I preppended the encrypted data with the IV. Finally base 64 encoded the output and wrote it to disk.

For decryption, I reversed these four steps. First I base 64 decode the plaintext. Then I extract the 16 bit IV from the first 16 bits. Then I decrypt the contents using the IV and the keyfile on disk.
