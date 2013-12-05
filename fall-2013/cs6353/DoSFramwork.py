import threading
import time, os
import subprocess
import shlex

def crawl():
    #cmd = "curl -i http://10.245.122.50:5000/v2.0/tokens -X POST -H \"Content-Type: application/json\" -H \"Accept: application/json\" -H \"User-Agent: python-novaclient\" -d \'{\"auth\": {\"tenantName\": \"demo\", \"passwordCredentials\": {\"username\": \"demo\", \"password\": \"admin\"}}}\'"
    #cmd = "curl www.google.com"
    #cm = shlex.split(cmd)
    #subprocess.check_output(cm,shell=True)
    #print "Running Nova list command"
    print os.system("./DoSCmd.sh")

threads = []

for n in range(2):
    thread = threading.Thread(target=crawl)
    thread.start()

    threads.append(thread)

# to wait until all three functions are finished

print "Waiting..."

for thread in threads:
    thread.join()

print "Complete."