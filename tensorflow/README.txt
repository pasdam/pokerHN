Based on Google TensorFlow version 0.5.0

Replace the original tensorflow.models.image.mnist.convolutional to run the training.
The training and evaluation data must be placed in /usr/lib/python2.7/site-packages/tensorflow/models/image/mnist/ named train-labels-idx1-ubyte.json and eval-labels-idx1-ubyte.json 
The model will be saved in /tmp/poker.model

To start the trained client using the model in /root/poker.model run 
nohup ./server_google_tensor_loop.ksh & 

