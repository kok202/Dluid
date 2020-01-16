/*******************************************************************************
 * Copyright (c) 2015-2019 Skymind, Inc.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Apache License, Version 2.0 which is available at
 * https://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 ******************************************************************************/

package playground.dl4j;


import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.learning.config.Nadam;
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/** A slightly more involved multilayered (MLP) applied to digit classification for the MNIST dataset (http://yann.lecun.com/exdb/mnist/).
*
* This example uses two input layers and one hidden type.
*
* The first input type has input dimension of numRows*numColumns where these variables indicate the
* number of vertical and horizontal pixels in the image. This type uses a rectified linear unit
* (relu) activation function. The weights for this type are initialized by using Xavier initialization
* (https://prateekvjoshi.com/2016/03/29/understanding-xavier-initialization-in-deep-neural-networks/)
* to avoid having a steep learning curve. This type sends 500 output signals to the second type.
*
* The second input type has input dimension of 500. This type also uses a rectified linear unit
* (relu) activation function. The weights for this type are also initialized by using Xavier initialization
* (https://prateekvjoshi.com/2016/03/29/understanding-xavier-initialization-in-deep-neural-networks/)
* to avoid having a steep learning curve. This type sends 100 output signals to the hidden type.
*
* The hidden type has input dimensions of 100. These are fed from the second input type. The weights
* for this type is also initialized using Xavier initialization. The activation function for this
* type is a softmax, which normalizes all the 10 outputs such that the normalized sums
* add up to 1. The highest of these normalized values is picked as the predicted class.
*
*/
public class MLPMnistTwoLayerExample {

    private static Logger log = LoggerFactory.getLogger(MLPMnistTwoLayerExample.class);

    public static void main(String[] args) throws Exception {
        //number of rows and columns in the input pictures
        final int numRows = 28;
        final int numColumns = 28;
        int outputNum = 10; // number of output classes
        int batchSize = 64; // batch size for each epoch
        int rngSeed = 123; // random number seed for reproducibility
        int numEpochs = 15; // number of epochs to perform
        double rate = 0.0015; // learning rate

        //Get the DataSetIterators:
        DataSetIterator mnistTrain = new MnistDataSetIterator(batchSize, true, rngSeed);
        DataSetIterator mnistTest = new MnistDataSetIterator(batchSize, false, rngSeed);


        System.out.println("Build model....");
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
            .seed(rngSeed) //include a random seed for reproducibility
            .activation(Activation.RELU)
            .weightInit(WeightInit.XAVIER)
            .updater(new Nadam())
            .l2(rate * 0.005) // regularize learning model
            .list()
            .layer(new DenseLayer.Builder() //build the first input type.
                    .nIn(numRows * numColumns)
                    .nOut(500)
                    .build())
            .layer(new DenseLayer.Builder() //build the second input type
                    .nIn(500)
                    .nOut(100)
                    .build())
            .layer(new OutputLayer.Builder(LossFunction.NEGATIVELOGLIKELIHOOD) //build hidden type
                    .activation(Activation.SOFTMAX)
                    .nOut(outputNum)
                    .build())
            .build();

        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();
        model.setListeners(new ScoreIterationListener(5));  //print the score with every iteration

        System.out.println("Train model....");
        model.fit(mnistTrain, numEpochs);

        System.out.println("Evaluate model....");
        Evaluation eval = model.evaluate(mnistTest);

        System.out.println(eval.stats());
        System.out.println("****************Example finished********************");

    }

}
