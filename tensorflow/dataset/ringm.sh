shuf json_tensorflow.out > json_tensorflow_shuf.out
head -9000 json_tensorflow_shuf.out > train-labels-idx1-ubyte.json
tail -1796 json_tensorflow_shuf.out > eval-labels-idx1-ubyte.json

