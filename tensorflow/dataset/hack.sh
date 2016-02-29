cat logHand-MS.txt > hack.log
cat logHand-MS2-notte.txt >> hack.log
cat logHand-v-notte.txt >> hack.log
cat logHand-v.txt >> hack.log
cat logHand-v2-fold.txt >> hack.log
cat logHand_MS_Fold.txt >> hack.log
shuf hack.log > hack_shuf.log
head -18000 hack_shuf.log > train-labels-idx1-ubyte.json
tail -3652 hack_shuf.log > eval-labels-idx1-ubyte.json

