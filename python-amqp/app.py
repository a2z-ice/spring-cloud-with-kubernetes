from flask import Flask
import pika

connection = pika.BlockingConnection(pika.ConnectionParameters("localhost"))
channel = connection.channel()
channel.queue_declare(queue='hello')

app = Flask(__name__)
@app.route("/")
def index():
    channel.basic_publish(exchange='',
                      routing_key='hello',
                      body='Hello World!')
    print(" [x] Sent 'Hello World!'")
    # connection.close()
    return "Hello World"

if __name__=="__main__":
    app.run(debug=True)
