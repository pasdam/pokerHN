"""Simple, end-to-end, LeNet-5-like convolutional MNIST model example.

This should achieve a test error of 0.8%. Please keep this model as simple and
linear as possible, it is meant as a tutorial for simple convolutional models.
Run with --self_test on the command line to exectute a short self-test.
"""
import gzip
import os
import sys
import urllib

import tensorflow.python.platform

import numpy
import tensorflow as tf
import json

SOURCE_URL = 'http://yann.lecun.com/exdb/mnist/'
WORK_DIRECTORY = 'data'
IMAGE_SIZE = 17
NUM_CHANNELS = 23
PIXEL_DEPTH = 1
NUM_LABELS = 5
VALIDATION_SIZE = 50  # Size of the validation set.
SEED = 66478  # Set to None for random seed.
BATCH_SIZE = 64
NUM_EPOCHS = 10


tf.app.flags.DEFINE_boolean("self_test", False, "True if running a self test.")
FLAGS = tf.app.flags.FLAGS

def convert_json(data, line, kk):
		j = json.loads(line)
                #print(j)
                for i in [0,1]:
                        suit=int(j['playerCards'][i]['suit'])
                        value=int(j['playerCards'][i]['value'])
                        data[kk][suit+2][value+2][i]=1
                        data[kk][suit+2][value+2][7]=1
                for i in range(5):
                        suit=int(j['communityCards'][i]['suit'])
                        value=int(j['communityCards'][i]['value'])
                        if suit != -1 and value != -1:
                                data[kk][suit+2][value+2][i+2]=1
                                data[kk][suit+2][value+2][7]=1
                for i in range(j['handPhase'],3):
                        data[kk][:,:,i+8]=1
                if j['turnPosition'] == 1:
                        data[kk][:,:,11]=1
                pot50 = j['pot']/50
                x=0
                y=0
                for i in range(min(pot50,169)):
                        data[kk][x+2,y+2,12]=1
                        x+=1
                        if x >= 13:
                                x=0
                                y+=1
                if j['currentOpponentAction'] != -1:
                        data[kk][:,:,j['currentOpponentAction']+13]=1
                if j['previousActionDone'] != -1:
                        data[kk][:,:,j['previousActionDone']+18]=1
	
		return j	

def extract_data_json(filename, num_images):
    """Extract the images into a 4D tensor [image index, y, x, channels].

    Values are rescaled from [0, 255] down to [-0.5, 0.5].
    """
    print 'Extracting', filename
    with open(filename) as f:
        content = f.readlines();	
        data = numpy.zeros(shape=(len(content),IMAGE_SIZE,IMAGE_SIZE,NUM_CHANNELS), dtype=numpy.float32)
	labels = numpy.zeros(shape=(len(content),NUM_LABELS), dtype=numpy.int64)
	kk=0
	for line in content:
		j=convert_json(data, line, kk)
		labels[kk,j['currentActionDone']]=1
		kk+=1	
		
        
	
    return data, labels


def error_rate(predictions, labels):
    """Return the error rate based on dense predictions and 1-hot labels."""
    print(predictions)
    return 100.0 - (
        100.0 *
        numpy.sum(numpy.argmax(predictions, 1) == numpy.argmax(labels, 1)) /
        predictions.shape[0])


def client_tf(jsoninput):  # pylint: disable=unused-argument
    # Get the data.
    test_data = numpy.zeros(shape=(1,IMAGE_SIZE,IMAGE_SIZE,NUM_CHANNELS), dtype=numpy.float32)
    j = convert_json(test_data, jsoninput, 0)
	
    # For the validation and test data, we'll just hold the entire dataset in
    # one constant node.
    #validation_data_node = tf.constant(validation_data)
    test_data_node = tf.constant(test_data)

    # The variables below hold all the trainable weights. They are passed an
    # initial value which will be assigned when when we call:
    # {tf.initialize_all_variables().run()}
    conv1_weights = tf.Variable(
        tf.truncated_normal([5, 5, NUM_CHANNELS, 32],  # 5x5 filter, depth 32.
                            stddev=0.1,
                            seed=SEED))
    conv1_biases = tf.Variable(tf.zeros([32]))
    conv2_weights = tf.Variable(
        tf.truncated_normal([5, 5, 32, 64],
                            stddev=0.1,
                            seed=SEED))
    conv2_biases = tf.Variable(tf.constant(0.1, shape=[64]))
    fc1_weights = tf.Variable(  # fully connected, depth 512.
        tf.truncated_normal([1600, 512],
                            stddev=0.1,
                            seed=SEED))
    fc1_biases = tf.Variable(tf.constant(0.1, shape=[512]))
    fc2_weights = tf.Variable(
        tf.truncated_normal([512, NUM_LABELS],
                            stddev=0.1,
                            seed=SEED))
    fc2_biases = tf.Variable(tf.constant(0.1, shape=[NUM_LABELS]))

    # We will replicate the model structure for the training subgraph, as well
    # as the evaluation subgraphs, while sharing the trainable parameters.


    def model(data, train=False):
        """The Model definition."""
        # 2D convolution, with 'SAME' padding (i.e. the output feature map has
        # the same size as the input). Note that {strides} is a 4D array whose
        # shape matches the data layout: [image index, y, x, depth].
        conv = tf.nn.conv2d(data,
                            conv1_weights,
                            strides=[1, 1, 1, 1],
                            padding='SAME')
        # Bias and rectified linear non-linearity.
        relu = tf.nn.relu(tf.nn.bias_add(conv, conv1_biases))
        # Max pooling. The kernel size spec {ksize} also follows the layout of
        # the data. Here we have a pooling window of 2, and a stride of 2.
        pool = tf.nn.max_pool(relu,
                              ksize=[1, 2, 2, 1],
                              strides=[1, 2, 2, 1],
                              padding='SAME')
        conv = tf.nn.conv2d(pool,
                            conv2_weights,
                            strides=[1, 1, 1, 1],
                            padding='SAME')
        relu = tf.nn.relu(tf.nn.bias_add(conv, conv2_biases))
        pool = tf.nn.max_pool(relu,
                              ksize=[1, 2, 2, 1],
                              strides=[1, 2, 2, 1],
                              padding='SAME')
        # Reshape the feature map cuboid into a 2D matrix to feed it to the
        # fully connected layers.
        pool_shape = pool.get_shape().as_list()
        reshape = tf.reshape(
            pool,
            [pool_shape[0], pool_shape[1] * pool_shape[2] * pool_shape[3]])
        # Fully connected layer. Note that the '+' operation automatically
        # broadcasts the biases.
        hidden = tf.nn.relu(tf.matmul(reshape, fc1_weights) + fc1_biases)
        # Add a 50% dropout during training only. Dropout also scales
        # activations such that no rescaling is needed at evaluation time.
        return tf.matmul(hidden, fc2_weights) + fc2_biases




    # Create a local session to run this computation.
    with tf.Session() as s:
        # Run all the initializers to prepare the trainable parameters.
        #tf.initialize_all_variables().run()
        #print 'Initialized!'

	saver = tf.train.Saver()
	saver.restore (s,'/root/poker.model')
	test_prediction = tf.nn.softmax(model(test_data_node)).eval()
	#test_prediction = model(test_data_node)
	print(test_prediction)
	print(numpy.argmax(test_prediction, 1)[0])
	return numpy.argmax(test_prediction, 1)[0]
