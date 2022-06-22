import os
import numpy as np

import tensorflow as tf
import tensorflow.keras as keras
from tensorflow.keras import layers, models

from glob import glob
from tqdm import tqdm
from datetime import datetime
from sklearn.model_selection import train_test_split
from tensorflow.keras.callbacks import ModelCheckpoint, ReduceLROnPlateau


def generate_train_test_dataset(base_url="/Users/softhoon/Desktop/gomocup2021", gameStyle="Freestyle"):
    file_list = glob(os.path.join(base_url, '%s*/*.psq' % (gameStyle, )))

    x = []
    y = []

    for index, file_path in enumerate(file_list):
        with open(file_path, 'r') as f:
            lines = f.read().splitlines() 

        w, h = lines[0].split(' ')[1].strip(',').split('x')
        w, h = int(w), int(h)

        lines = lines[1:]

        inputs, outputs = [], []
        board = np.zeros([h, w], dtype=np.int8)

        for i, line in enumerate(lines):
            if ',' not in line:
                break

            x, y, t = np.array(line.split(','), np.int8)

            if i % 2 == 0:
                player = 1
            else:
                player = 2

            input = board.copy().astype(np.int8)
            input[(input != player) & (input != 0)] = -1
            input[(input == player) & (input != 0)] = 1

            output = np.zeros([h, w], dtype=np.int8)
            output[y-1, x-1] = 1

        x.append(inputs)
        y.append(outputs)
    
    return x, y

def split_and_preprocessing_data(X, Y):
    w, h = 20, 20
    x_data = np.array(X, np.float32).reshape((-1, h, w, 1))
    y_data = np.array(Y, np.float32).reshape((-1, h * w))

    x_train, x_val, y_train, y_val = train_test_split(x_data, y_data, test_size=0.2, random_state=1111)
    return x_train, x_val, y_train, y_val

def train_hard_mode(x_train, y_train, x_val, y_val):
    model = models.Sequential([
        layers.Conv2D(64,  5, activation='relu', padding='same', input_shape=(20, 20, 1)),
        layers.Conv2D(128, 5, activation='relu', padding='same'),
        layers.Conv2D(256, 5, activation='relu', padding='same'),
        layers.Conv2D(512, 5, activation='relu', padding='same'),
        layers.Conv2D(256, 5, activation='relu', padding='same'),
        layers.Conv2D(128, 5, activation='relu', padding='same'),
        layers.Conv2D(64,  5, activation='relu', padding='same'),
        layers.Conv2D(1,   1, activation=None, padding='same'),
        layers.Reshape((20 * 20,)),
        layers.Activation('sigmoid')
    ])

    model.compile(
        optimizer='adam', loss='binary_crossentropy', metrics=['acc']
    )

    model.fit(
        x=x_train, y=y_train, batch_size=512, epochs=10,
        callbacks=[
            ModelCheckpoint('./models/%s.h5' % ("hard"), monitor='val_acc', verbose=1, save_best_only=True, mode='auto'),
            ReduceLROnPlateau(monitor='val_acc', factor=0.3, patience=7, verbose=1, mode='auto')
        ],
        validation_data=(x_val, y_val), use_multiprocessing=True, workers=8
    )

    print("Hard Training Finish")

def train_easy_mode(x_train, y_train, x_val, y_val):
    model = models.Sequential([
        layers.Conv2D(64,  5, activation='relu', padding='same', input_shape=(20, 20, 1)),
        layers.Conv2D(128, 5, activation='relu', padding='same'),
        layers.Conv2D(256, 5, activation='relu', padding='same'),
        layers.Conv2D(512, 5, activation='relu', padding='same'),
        layers.Conv2D(256, 5, activation='relu', padding='same'),
        layers.Conv2D(128, 5, activation='relu', padding='same'),
        layers.Conv2D(64,  5, activation='relu', padding='same'),
        layers.Conv2D(1,   1, activation=None, padding='same'),
        layers.Reshape((20 * 20,)),
        layers.Activation('sigmoid')
    ])

    model.compile(
        optimizer='adam', loss='binary_crossentropy', metrics=['acc']
    )

    model.fit(
        x=x_train, y=y_train, batch_size=512, epochs=5,
        callbacks=[
            ModelCheckpoint('./models/%s.h5' % ("easy"), monitor='val_acc', verbose=1, save_best_only=True, mode='auto'),
            ReduceLROnPlateau(monitor='val_acc', factor=0.3, patience=7, verbose=1, mode='auto')
        ],
        validation_data=(x_val, y_val), use_multiprocessing=True, workers=8
    )

    print("Easy Training Finish")

if __name__ == "__main__":
    x, y = generate_train_test_dataset()
    x_train, x_val, y_train, y_val = split_and_preprocessing_data(x, y)
    train_hard_mode(x_train, y_train, x_val, y_val)
    train_easy_mode(x_train, y_train, x_val, y_val)
    print("All process finish")