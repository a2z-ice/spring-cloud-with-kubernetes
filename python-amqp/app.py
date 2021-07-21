from flask import Flask
import pika
import schedule
import time
from apscheduler.schedulers.background import BackgroundScheduler


# connection = pika.BlockingConnection(pika.ConnectionParameters(host="114.130.119.67",
# port=80,virtual_host="/dashboard",
# credentials=pika.PlainCredentials(username="test", password="test123")
# ))

connection = pika.BlockingConnection(pika.ConnectionParameters(host="localhost",
port=5672,virtual_host="/",
credentials=pika.PlainCredentials(username="guest", password="guest")
))


channel = connection.channel()

def sensor():
    """ Function for test purposes. """
    connection.process_data_events
    print("Scheduler is alive!" + str(connection.is_closed))

sched = BackgroundScheduler(daemon=True)
sched.add_job(sensor,'interval',seconds=5)
sched.start()    

app = Flask(__name__)
@app.route("/")
def index():

   
    channel.queue_declare(queue='hello')
    channel.basic_publish(exchange='',
                      routing_key='hello',
                      body='Hello World! k8s cluster')
    print(" [x] Sent 'Hello World!'")
    # connection.close()
    return "Hello World" + str(connection.is_closed)





if __name__=="__main__":
    app.run(debug=True)

 
