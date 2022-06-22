#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from ast import arg
from email import contentmanager
from flask import Flask
from flask import request
from flask import jsonify
from flask import current_app

import numpy as np
from tensorflow.keras.models import load_model

import math
import time
import random
import numpy as np

from werkzeug.serving import WSGIRequestHandler
import json
WSGIRequestHandler.protocol_version = "HTTP/1.1"

app = Flask(__name__)

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
    
if __name__ == "__main__":
    with app.app_context():
        current_app.model = load_model("hard.h5")
        current_app.submodel = load_model("easy.h5")
    app.run(host='127.0.0.1', port=2500)