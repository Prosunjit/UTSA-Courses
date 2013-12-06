from  Crypto.PublicKey import RSA
from Crypto.Cipher import PKCS1_OAEP
import datetime, time
import random



def generate_RSA(keysize=1024):
    rsa = RSA.generate(keysize)
    publicKey = rsa.publickey().exportKey()
    privateKey = rsa.exportKey()
    return publicKey, privateKey

def createMessage(size=256):
    return ''.join(chr(random.randint(97,122)) for i in range(0,255))


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

def timestamp(date):  
    return time.mktime(date.timetuple())

def diffKeySize100MsgTest():
    keysize = [ i*1024 for i in range(1,10) ]
    for size in keysize:
      avg=0
      for i in range(1,101):
	(pub_key, priv_key) = generate_RSA(size)
	(s1_time,end1_time) = encryptNDecryptLongMessage(pub_key,priv_key)
	avg = (avg * (i -1) + (timestamp (end1_time) - timestamp (s1_time) ))/i
	#(s2_time,end2_time) = encryptNDecryptLongMessage(priv_key,pub_key)
      print size, avg
    

def diffKeySingleMessage():
   keysize = [ 1024 for i in range(1,101) ]
   for size in keysize:         
	(pub_key, priv_key) = generate_RSA(size)
	(s1_time,end1_time) = encryptNDecryptLongMessage(pub_key,priv_key)
	#req_time =  (timestamp (end1_time) - timestamp (s1_time) )
	#(s2_time,end2_time) = encryptNDecryptLongMessage(priv_key,pub_key)
	print size, end1_time - s1_time
   
  
#diffKeySingleMessage()
diffKeySize100MsgTest()


