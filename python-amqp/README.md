# User previlage

```
in your case, you would want to set permissions like this:

vhost: mainvhost
user: foo
configure: ""        <=== empty
write: "^ex-foo.*"
read: "^Q-foo.*"
this will grant permissions for the foo user to write to any exchange that starts with ex-foo and to read from any queue that start with Q-foo
and by configure empty means the user is not able to create any queue or exchange programmatically
```

# For windows
```

pip3 install virtualenv
# create virtual environment
virtualenv venv -p python3

# Activate virtualenv from cmd
venv\Scripts\activate

# from git bash windows 10:
source /f/projects/A2i/dashboard/venv/Scripts/activate

# Create requirements file
pip freeze > requirements.txt

# Install Flask
pip install Flask 

# From visual studio code do following stesps
1. from keyboard ctl + shift + p
2. Write "python: interpretor"
3. Select interpretor path and navigate to virtual environment which is "venv" created earlier

https://code.visualstudio.com/docs/python/environments

pip install -r requirements.txt


# to run application from windows
python app.py 

# For rabbitmq
docker run -d -p 15672:15672 -p 5672:5672 -p 5671:5671 rabbitmq:3.8.2-management-alpine
http://localhost:15672/#/
user: guest
password: guest


# For amqp client

python -m pip install pika --upgrade
python -m pip install apscheduler --upgrade

# Working standalong but not in flask
python -m pip install schedule --upgrade





```
