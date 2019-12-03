from keras import layers
from keras import models 
from sklearn.model_selection import train_test_split
import numpy as np
import matplotlib.pyplot as plt 

def one_hot(a):
    m = np.zeros(4)
    m[int(a)] = 1
    return m

X = []
y = []

for i in range(75):
    f = open('inputs/input_{}.txt'.format(i))
    data = f.read().split(',')
    f.close()
    for i in range(len(data) - 1):
        entry = data[i]
        tmpX = entry.split(' ')[:-1]
        X.append(np.asarray(tmpX[1:]).reshape(20,20,1))
        y.append(np.asarray(one_hot(tmpX[0])))


X_train, X_test, y_train, y_test = train_test_split(np.array(X),np.array(y),test_size=0.33,shuffle=True)
print(len(X_train))

model = models.Sequential()
model.add(layers.Conv2D(16, (3,3),activation='relu',input_shape=(20,20,1)))
model.add(layers.MaxPooling2D((2,2)))
model.add(layers.Conv2D(8, (3,3), activation='relu'))
model.add(layers.MaxPooling2D((2,2)))
# model.add(layers.Conv2D(4, (3,3), activation='relu'))
model.add(layers.Flatten())
model.add(layers.Dense(32, activation='relu'))
model.add(layers.Dense(16, activation='relu'))
model.add(layers.Dense(4, activation='softmax'))

model.summary()

model.compile(optimizer='rmsprop',
                      loss='categorical_crossentropy',
                      metrics=['accuracy'])

history = model.fit(X_train, y_train, epochs=20, batch_size=64, validation_data=(X_test,y_test))

# test_loss, test_acc = model.evaluate(x_test, y_test)

# print('Test accuracy:', test_acc)

# predictions = model.predict(x_test)


loss = history.history['loss']
val_loss = history.history['val_loss']

epochs = range(1, len(loss) + 1)

plt.plot(epochs, loss, 'bo', label='Training loss')
plt.plot(epochs, val_loss, 'b', label='Validation loss')
plt.title('Training and validation loss')
plt.xlabel('Epochs')
plt.ylabel('Loss')
plt.legend()

plt.show()

plt.clf()

acc = history.history['accuracy']
val_acc = history.history['val_accuracy']

plt.plot(epochs, acc, 'bo', label='Training acc')
plt.plot(epochs, val_acc, 'b', label='Validation acc')
plt.title('Training and Validation accuracy')
plt.xlabel('Epochs')
plt.ylabel('Accuracy')
plt.legend()

plt.show()



