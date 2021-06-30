from flask import Flask
import pika
import schedule
import time
from apscheduler.schedulers.background import BackgroundScheduler


connection = pika.BlockingConnection(pika.ConnectionParameters("localhost"))
channel = connection.channel()

def sensor():
    """ Function for test purposes. """
    connection.process_data_events
    print("Scheduler is alive!")

sched = BackgroundScheduler(daemon=True)
sched.add_job(sensor,'interval',seconds=5)
sched.start()    

app = Flask(__name__)
@app.route("/")
def index():

    if connection.is_closed:
        connection.reconnect()
    
    channel.queue_declare(queue='hello')
    channel.basic_publish(exchange='',
                      routing_key='hello',
                      body='Hello World!')
    print(" [x] Sent 'Hello World!'")
    # connection.close()
    return "Hello World" + str(connection.is_closed)





if __name__=="__main__":
    app.run(debug=True)

 
