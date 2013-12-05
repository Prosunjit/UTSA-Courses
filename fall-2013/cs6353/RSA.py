from  Crypto.PublicKey import RSA
from Crypto.Cipher import PKCS1_OAEP
import datetime
import random

global rsa

def generate_RSA(keysize=1024):
    global rsa
    rsa = RSA.generate(keysize)
    publicKey = rsa.publickey().exportKey()
    privateKey = rsa.exportKey()
    return publicKey, privateKey

def createMessage(size=256):

    #return [chr(random.randint(0,255)) for i in range(0,255)]
    #return "Hello World Hello World Hello World Hello WorldHello World Hello World Hello World Hello World"
    with open('./RSA.py', 'r') as content_file:
        content = content_file.read()
    return content


def encryptNDecryptLongMessage (pub_key,priv_key, msg=None):

    if msg == None:
        msg = createMessage()

    chunks = msg.__len__()
    chunk_size = 50
    splitedStr = [ msg[i:i+chunk_size] for i in range(0, chunks, chunk_size) ]

    stime = datetime.datetime.now()
    for msg in splitedStr :
        encrypted_msg = encrypt(pub_key,msg)
        decrypted_msg = decrypt(priv_key,encrypted_msg)
        #print encrypted_msg, decrypted_msg
    end_time = datetime.datetime.now()

    return stime, end_time
def encrypt(key, msg):

    publicKey = RSA.importKey(key)
    cipher = PKCS1_OAEP.new(publicKey)
    cipher_text = cipher.encrypt(msg)
    return cipher_text

def decrypt (key, encypted_msg=None):
    privKey = RSA.importKey(key)
    cipher = PKCS1_OAEP.new(privKey)
    message = cipher.decrypt(encypted_msg)
    return message



def hw4():
    keysize = [ i*1024 for i in range(1,20) ]

    for size in keysize:
        (pub_key, priv_key) = generate_RSA(size)
        (s1_time,end1_time) = encryptNDecryptLongMessage(pub_key,priv_key)
        #(s2_time,end2_time) = encryptNDecryptLongMessage(priv_key,pub_key)
        print size, end1_time - s1_time


hw4()


