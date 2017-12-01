import requests
import random
from random import randint
import time
import datetime

url = 'http://2flf3l7wp7.hardtobelieve.me/'

def randomString(N):
    return ''.join(random.choice(string.ascii_uppercase + string.digits) for _ in range(N))

def strTimeProp(start, end, format, prop):
    stime = time.mktime(time.strptime(start, format))
    etime = time.mktime(time.strptime(end, format))
    ptime = stime + prop * (etime - stime)
    return time.strftime(format, time.localtime(ptime))

def randomDate(start, end, prop):
    return strTimeProp(start, end, '%m/%d/%Y', prop)

user_id = []
bookID = []
copyID = []
activate_code = []


def user(name, password, address, date_of_birth, email, job):
    data = {
        'name' : name,
        'password' : password,
        'address' : address,
        'date_of_birth' : date_of_birth,
        'email' : email,
        'job' : job,
        'user_id' : randomString(5)
    }
    endpoint = 'user/post.php'
    result = requests.post(url + endpoint, data=data)
    return result.status_code

def book(title, author, publisher):
    bookID = randomString(5)
    data = {
        'title' : title,
        'author' : author,
        'publisher' : publisher,
        'bookID' : bookID,
        'isbn' : randomString(5)
    }
    endpoint = 'bookinfo/post.php'
    result = requests.post(url + endpoint, data=data)
    return result.status_code, bookID

def bookcopy(copyID, bookID):
    price = randint(0, 100);
    typeCopy = random.choice(['Borrowable', 'Reference'])
    data = {
        'copyID' : copyID,
        'bookID' : bookID,
        'price' : price,
        'type' : typeCopy
    }
    endpoint = 'bookcopy/post.php'
    result = requests.post(url + endpoint, data=data)
    return result.status_code

def card_number(user_id, is_active, is_student):
    activate_code = randomString(5)
    now = datetime.datetime.now()
    expired_date = randomDate(now.strftime("%d/%m/%Y"), "1/1/2009", random.random())
    data = {
        'user_id' : user_id,
        'is_active' : is_active,
        'is_student' : is_student,
        'activate_code' : activate_code,
        'expired_date' : expired_date
    }

