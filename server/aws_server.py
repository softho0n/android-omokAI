from re import U
from sqlalchemy import create_engine
from sqlalchemy.orm import scoped_session, sessionmaker
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, Integer, String

USER = "USER"
PW = "PW"
URL="YOUR_END_POINT"
PORT = "PORT"
DB = "postgres"

engine = create_engine("postgresql://{}:{}@{}:{}/{}".format(USER, PW, URL,PORT, DB))
db_session = scoped_session(sessionmaker(autocommit=False, autoflush=False, bind=engine))
Base = declarative_base()
Base.query = db_session.query_property()

class User(Base):
	__tablename__ = 'pa3Users_v3'
	id = Column(Integer, primary_key=True)
	name = Column(String(50), unique=True)
	passwd = Column(String(120), unique=False)
	nickname = Column(String(50), unique=True)
	easyscore = Column(String(20), unique=False)
	hardscore = Column(String(20), unique=False)
	
	def __init__(self, name=None, passwd=None, nickname=None, easyscore=None, hardscore=None):
		self.name = name
		self.passwd = passwd
		self.nickname = nickname
		self.easyscore = easyscore
		self.hardscore = hardscore
	
	def __repr__(self):
		return f'<User {self.name!r}>'

Base.metadata.create_all(bind=engine)

from flask import Flask
from flask import request
from flask import jsonify
from werkzeug.serving import WSGIRequestHandler
import json

WSGIRequestHandler.protocol_version = "HTTP/1.1"

app = Flask(__name__)

@app.route("/post/signUp", methods=['POST'])
def add_user():
    content = request.get_json(silent=True)
    name = content["name"]
    passwd = content["passwd"]
    nickname = content["nickname"]
    flag = False
    if db_session.query(User).filter_by(name=name).first() is None:
        if db_session.query(User).filter_by(nickname=nickname).first() is None:
            u = User(name=name, passwd=passwd, nickname=nickname, easyscore="0", hardscore="0")
            db_session.add(u)
            db_session.commit()
            flag = True
    
    if flag :
        return jsonify(success=True)
    return jsonify(success=False)

@app.route("/login", methods=['POST'])
def login():
	content = request.get_json(silent=True)
	name = content["name"]
	passwd = content["passwd"]
	check = False
	ret={}
	result = db_session.query(User).all()
	for i in result:
		if i.name == name and i.passwd == passwd:
			ret["name"] = i.name
			ret["passwd"] = i.passwd
			ret["nickname"] = i.nickname
			ret["easyscore"] = i.easyscore
			ret["hardscore"] = i.hardscore
			check = True
			break
	if check :
		return jsonify (ret)
	else :
		ret["name"] = None
		ret["passwd"] = None
		ret["nickname"] = None
		ret["easyscore"] = None
		ret["hardscore"] = None
		return jsonify (ret)

@app.route("/update/easyscore", methods=['POST'])
def updateEasyScore():
    content = request.get_json(silent=True)
    name = content["name"]
    passwd = content["passwd"]
    target_score = content["score"]
    
    i = db_session.query(User).filter_by(name=name, passwd=passwd).first()
    if i is not None :
        if(int(i.easyscore) < int(target_score)) :
            i.easyscore = target_score
            db_session.commit()
        return jsonify(success=True)
    else:
        return jsonify(success=False)

@app.route("/update/hardscore", methods=['POST'])
def updateHardScore():
    content = request.get_json(silent=True)
    name = content["name"]
    passwd = content["passwd"]
    target_score = content["score"]
    
    i = db_session.query(User).filter_by(name=name, passwd=passwd).first()
    if i is not None :
        if(int(i.hardscore) < int(target_score)) :
            i.hardscore = target_score
            db_session.commit()
        return jsonify(success=True)
    else:
        return jsonify(success=False)
    
@app.route("/get/easyUsers", methods=['GET'])
def getEasyUsers():
    elements_list = []
    result = db_session.query(User).all()
    for i in result:
        if i.easyscore == "0" :
            continue
        temp_ret = {}
        temp_ret["name"] = i.name
        temp_ret["nickname"] = i.nickname
        temp_ret["score"] = i.easyscore
        elements_list.append(temp_ret)
    elements_list = sorted(elements_list, key=lambda info: info["score"], reverse=True)
    return jsonify(elements_list)

@app.route("/get/hardUsers", methods=['GET'])
def getHardUsers():
    elements_list = []
    result = db_session.query(User).all()
    for i in result:
        if i.hardscore == "0" :
            continue
        temp_ret = {}
        temp_ret["name"] = i.name
        temp_ret["nickname"] = i.nickname
        temp_ret["score"] = i.hardscore
        elements_list.append(temp_ret)
    
    elements_list = sorted(elements_list, key=lambda info: info["score"], reverse=True)
    return jsonify(elements_list)

@app.route("/get/allRawUsers", methods=['GET'])
def getAllRawUsers():
    elements_list = []
    result = db_session.query(User).all()
    for i in result:
        temp_ret = {}
        temp_ret["name"] = i.name
        temp_ret["nickname"] = i.nickname
        temp_ret["h_score"] = i.hardscore
        temp_ret["e_score"] = i.easyscore
        elements_list.append(temp_ret)

    return jsonify (elements_list)

@app.route("/get/allUsers", methods=['GET'])
def getAllUsers():
    ret = {}
    
    elements_list = []
    result = db_session.query(User).all()
    for i in result:
        if i.hardscore == "0":
            continue
        temp_ret = {}
        temp_ret["name"] = i.name
        temp_ret["nickname"] = i.nickname
        temp_ret["score"] = i.hardscore
        elements_list.append(temp_ret)
    
    elements_list = sorted(elements_list, key=lambda info: int(info["score"]), reverse=True)
    ret["hard"] = elements_list
    
    elements_list = []
    result = db_session.query(User).all()
    for i in result:
        if i.easyscore == "0" :
            continue
        temp_ret = {}
        temp_ret["name"] = i.name
        temp_ret["nickname"] = i.nickname
        temp_ret["score"] = i.easyscore
        elements_list.append(temp_ret)
    elements_list = sorted(elements_list, key=lambda info: int(info["score"]), reverse=True)
    ret["easy"] = elements_list
    return jsonify(ret)

if __name__ == "__main__":
	app.run(host='localhost', port=5050)
