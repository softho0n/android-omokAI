# android-omokAI
> Android version of Omok AI game

## Development Tools & Library
* Android Studio & Java
* Okhttp
* Gson
* Python & Flask
* AWS S3, Lambda, RDS

## Server side
### [AWS Server](https://github.com/softho0n/android-omokAI/blob/main/server/aws_server.py)
1. `login`
```python
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
```
2. `register`
```python
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
```
3. `ranking system`
```python
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
```
### [Omok AI Server](https://github.com/softho0n/android-omokAI/blob/main/server/omok_ai_server.py)
* Prediction of AI
```python
@app.route("/get/prediction", methods=['GET'])
def get_ai_pos():
    args_dict = request.args.to_dict()

    input_str = args_dict['req'].rstrip(',')

    input_val = input_str.split(',')
    input_val = [int(item) for item in input_val]
    input_val = np.array(input_val)
    input_val = np.reshape(input_val, (20, 20))
    input_val = np.expand_dims(input_val, axis=(0, -1)).astype(np.float32)

    if args_dict["type"] == "hard" :
        output = current_app.model.predict(input_val).squeeze()
    elif args_dict["type"] == "easy" :
        output = current_app.submodel.predict(input_val).squeeze()

    output = output.reshape((20, 20))
    o_x, o_y = np.unravel_index(np.argmax(output), output.shape)
    ret = {}
    ret["o_x"] = str(o_x)
    ret["o_y"] = str(o_y)
    return jsonify (ret)
```

## Client side
### Game
* [`HardModeGameActivity.java`](https://github.com/softho0n/android-omokAI/blob/main/OmokWithMVP/app/src/main/java/edu/skku/cs/omokwithmvp/HardModeGameActivity.java)
* [`EasyModeGameActivity.java`](https://github.com/softho0n/android-omokAI/blob/main/OmokWithMVP/app/src/main/java/edu/skku/cs/omokwithmvp/EasyModeGameActivity.java)
### User
* [`MainActivity.java`](https://github.com/softho0n/android-omokAI/blob/main/OmokWithMVP/app/src/main/java/edu/skku/cs/omokwithmvp/MainActivity.java)
* [`SignUpActivity.java`](https://github.com/softho0n/android-omokAI/blob/main/OmokWithMVP/app/src/main/java/edu/skku/cs/omokwithmvp/SignUpActivity.java)
### Ranking
* [`RankingModeSelectionActivity.java`](https://github.com/softho0n/android-omokAI/blob/main/OmokWithMVP/app/src/main/java/edu/skku/cs/omokwithmvp/RankingModeSelectionActivity.java)

## Examples
