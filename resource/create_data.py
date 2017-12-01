import requests
import random
import time
import datetime
import string
import json
import sys

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

def user(name, password, address, date_of_birth, email, job):
    global user_id
    tmp = randomString(5)
    user_id.append(tmp)
    data = {
        'name' : name,
        'password' : password,
        'address' : address,
        'date_of_birth' : date_of_birth,
        'email' : email,
        'job' : job,
        'user_id' : tmp
    }
    endpoint = 'user/post.php'
    result = requests.post(url + endpoint, data=data)
    return json.loads(result.text)['status_code']

def book(title, author, publisher):
    global bookID
    tmp = randomString(5)
    bookID.append(tmp)
    data = {
        'title' : title,
        'author' : author,
        'publisher' : publisher,
        'bookID' : tmp,
        'isbn' : randomString(5)
    }
    endpoint = 'bookinfo/post.php'
    result = requests.post(url + endpoint, data=data)
    return json.loads(result.text)['status_code']

def bookcopy(copyid, bookid):
    global copyID
    price = random.randint(0, 100);
    copyID.append(copyid)
    typeCopy = random.choice(['Borrowable', 'Reference'])
    copyID.append(copyid)
    data = {
        'copyID' : copyid,
        'bookID' : bookid,
        'price' : price,
        'type' : typeCopy
    }
    endpoint = 'bookcopy/post.php'
    result = requests.post(url + endpoint, data=data)
    return json.loads(result.text)['status_code']

def card_number(userID, is_active, is_student):
    activate_code = randomString(5)
    now = datetime.datetime.now()
    expired_date = randomDate(now.strftime("%d/%m/%Y"), "1/1/2039", random.random())
    data = {
        'user_id' : userID,
        'is_active' : is_active,
        'is_student' : is_student,
        'activate_code' : activate_code,
        'expired_date' : expired_date
    }
    endpoint = 'card/post.php'
    result = requests.post(url + endpoint, data=data)
    return json.loads(result.text)['status_code']

def booklenthistory(userID, copyid, cardnNumber, is_returned):
    now = datetime.datetime.now()
    tmpdate = randomDate(now.strftime("%d/%m/%Y"), "1/1/2039", random.random())
    data = {
        'user_id' : userID,
        'copyID' : copyid,
        'card_number' : cardnNumber,
        'is_returned' : is_returned,
        'date' : tmpdate
    }
    endpoint = 'booklenthistory/post.php'
    result = requests.post(url + endpoint, data=data)
    return json.loads(result.text)['status_code']

tmp = user('Nguyen Van A', '1234', 'St 312', '11/11/1971', 'haha@yopmail.com', 'librarian')
if tmp != 'Success':
    print '[-] Failed to add user'

tmp = user('Nguyen Van B', '1234', 'St 313', '12/12/1982', 'hihi@yopmail.com', 'student')
if tmp != 'Success':
    print '[-] Failed to add user'

tmp = book('Inferno', 'Dan Brown', 'NXB Kim Dong')
if tmp != 'Success':
    print '[-] Failed to add book'

tmp = book('Sherlock Holmes', 'Conan Doyle', 'NXB Giao Duc')
if tmp != 'Success':
    print '[-] Failed to add book'

for i in range(len(bookID)):
    for j in range(3):
        tmp = bookcopy(bookID[i] + '_' + str(j), bookID[i])
        if tmp != 'Success':
            print '[-] Failed to add copy'

tmp = card_number(user_id[1], 1, 1)
if tmp != 'Success':
    print '[-] Failed to add card_number'

tmp = card_number(user_id[1], 1, 1)
if tmp != 'Success':
    print '[-] Failed to add card_number'

tmp = card_number(user_id[1], 1, 1)
if tmp != 'Success':
    print '[-] Failed to add card_number'

tmp = booklenthistory(user_id[1], copyID[2], 2, 'NO')
if tmp != 'Success':
    print '[-] Failed to add history'

