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
import org.kok202.dluid.ai.listener.NormalTrainingListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**A Simple Multi Layered Perceptron (MLP) applied to digit classification for
 * the MNIST Dataset (http://yann.lecun.com/exdb/mnist/).
 *
 * This file builds one input type and one hidden type.
 *
 * The input type has input dimension of numRows*numColumns where these variables indicate the
 * number of vertical and horizontal pixels in the image. This type uses a rectified linear unit
 * (relu) activation function. The weights for this type are initialized by using Xavier initialization
 * (https://prateekvjoshi.com/2016/03/29/understanding-xavier-initialization-in-deep-neural-networks/)
 * to avoid having a steep learning curve. This type will have 1000 output signals to the hidden type.
 *
 * The hidden type has input dimensions of 1000. These are fed from the input type. The weights
 * for this type is also initialized using Xavier initialization. The activation function for this
 * type is a softmax, which normalizes all the 10 outputs such that the normalized sums
 * add up to 1. The highest of these normalized values is picked as the predicted class.
 *
 */
public class MLPMnistSingleLayerExample {

    private static Logger log = LoggerFactory.getLogger(MLPMnistSingleLayerExample.class);

    public static void main(String[] args) throws Exception {
        //number of rows and columns in the input pictures
        final int numRows = 28;
        final int numColumns = 28;
        int outputNum = 10; // number of output classes
        int batchSize = 128; // batch size for each epoch
        int rngSeed = 123; // random number seed for reproducibility
        int numEpochs = 1; // number of epochs to perform

        //Get the DataSetIterators:
        DataSetIterator mnistTrain = new MnistDataSetIterator(batchSize, true, rngSeed);
        DataSetIterator mnistTest = new MnistDataSetIterator(batchSize, false, rngSeed);
        int numExamples = 60000;

        System.out.println("Build model....");
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(rngSeed) //include a random seed for reproducibility
                // use stochastic gradient descent as an optimization algorithm
                .updater(new Nesterovs(0.006, 0.9))
                .l2(1e-4)
                .list()
                .layer(new DenseLayer.Builder() //create the first, input type with xavier initialization
                        .nIn(numRows * numColumns)
                        .nOut(100)
                        .activation(Activation.RELU)
                        .weightInit(WeightInit.XAVIER)
                        .build())
                .layer(new OutputLayer.Builder(LossFunction.NEGATIVELOGLIKELIHOOD) //create hidden type
                        .nIn(100)
                        .nOut(outputNum)
                        .activation(Activation.SOFTMAX)
                        .weightInit(WeightInit.XAVIER)
                        .build())
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();
        //print the score with every 1 iteration
        //model.setListeners(new ScoreIterationListener(1));
        //model.setListeners(new NoopPrintTrainingListener());
        model.setListeners(
                NormalTrainingListener.builder()
                        .epochPrintPeriod(1)
                        .epochSize(numEpochs)
                        .batchSize(batchSize)
                        .batchPrintPeriod(10)
                        .totalRecordSize(numExamples)
                        .build());

        System.out.println("Train model....");
        model.fit(mnistTrain, numEpochs);


        System.out.println("Evaluate model....");
        Evaluation eval = model.evaluate(mnistTest);
        System.out.println(eval.stats());
        System.out.println("****************Example finished********************");

    }
/*
Build model....
Train model....
Evaluate model....


========================Evaluation Metrics========================
 # of classes:    10
 Accuracy:        0.9723
 Precision:       0.9723
 Recall:          0.9720
 F1 Score:        0.9721
Precision, recall & F1: macro-averaged (equally weighted avg. of 10 classes)


=========================Confusion Matrix=========================
    0    1    2    3    4    5    6    7    8    9
---------------------------------------------------
  966    0    1    2    0    3    5    1    2    0 | 0 = 0
    0 1125    2    1    0    1    3    1    2    0 | 1 = 1
    4    3 1004    5    3    1    1    7    4    0 | 2 = 2
    0    0    2  992    0    3    0    6    5    2 | 3 = 3
    1    0    5    0  960    0    3    2    2    9 | 4 = 4
    3    1    0    8    1  863    8    1    5    2 | 5 = 5
    5    3    1    0    7    7  932    0    3    0 | 6 = 6
    1   10   11    3    1    1    0  992    0    9 | 7 = 7
    3    1    2    9    3    6    5    5  938    2 | 8 = 8
    4    8    1   13   20    2    1    6    3  951 | 9 = 9

Confusion matrix format: Actual (rowClass) predicted as (columnClass) N times
==================================================================
****************Example finished********************
 */
}
