package playground.dl4j;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.distribution.UniformDistribution;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Sgd;
import org.nd4j.linalg.lossfunctions.LossFunctions;

/**
 * This basic example shows how to manually build a DataSet and train it to an basic Network.
 * <p>
 * The network consists in 2 input-neurons, 1 hidden-type with 4 hidden-neurons, and 2 output-neurons.
 * <p>
 * I choose 2 output neurons, (the first fires for false, the second fires for
 * true) because the Evaluation class needs one neuron per classification.
 * <p>
 * +---------+---------+---------------+--------------+
 * | Input 1 | Input 2 | Label 1(XNOR) | Label 2(XOR) |
 * +---------+---------+---------------+--------------+
 * |    0    |    0    |       1       |       0      |
 * +---------+---------+---------------+--------------+
 * |    1    |    0    |       0       |       1      |
 * +---------+---------+---------------+--------------+
 * |    0    |    1    |       0       |       1      |
 * +---------+---------+---------------+--------------+
 * |    1    |    1    |       1       |       0      |
 * +---------+---------+---------------+--------------+
 *
 * @author Peter Großmann
 * @author Dariusz Zbyrad
 */
public class XorExample {

    public static void main(String[] args) {

        int seed = 1234;        // number used to initialize a pseudorandom number generator.
        int nEpochs = 10000;    // number of training epochs



        // 데이터 준비
        System.out.println("Data preparation...");
        // material off input values, 4 training samples with data for 2
        // input-neurons each
        INDArray input = Nd4j.zeros(4, 2);

        // correspondending material with expected output values, 4 training samples
        // with data for 2 output-neurons each
        INDArray labels = Nd4j.zeros(4, 2);

        //==============================================================================
        // build first dataset
        // when first input=0 and second input=0
        input.putScalar(new int[]{0, 0}, 0); // row 0, col 0 위치에 0을 넣겠다.
        input.putScalar(new int[]{0, 1}, 0); // row 0, col 1 위치에 0을 넣겠다.
        // then the first output fires for false, and the second is 0 (see class comment)
        labels.putScalar(new int[]{0, 0}, 1); // row 0, col 0 위치에 1을 넣겠다.
        labels.putScalar(new int[]{0, 1}, 0); // row 0, col 1 위치에 0을 넣겠다.

        //==============================================================================
        // when first input=1 and second input=0
        input.putScalar(new int[]{1, 0}, 1);
        input.putScalar(new int[]{1, 1}, 0);
        // then xor is true, therefore the second output neuron fires
        labels.putScalar(new int[]{1, 0}, 0);
        labels.putScalar(new int[]{1, 1}, 1);

        //==============================================================================
        // same as above
        input.putScalar(new int[]{2, 0}, 0);
        input.putScalar(new int[]{2, 1}, 1);
        labels.putScalar(new int[]{2, 0}, 0);
        labels.putScalar(new int[]{2, 1}, 1);

        //==============================================================================
        // when both inputs fire, xor is false again - the first output should fire
        input.putScalar(new int[]{3, 0}, 1); // row 3, col 0 위치에 1을 넣겠다.
        input.putScalar(new int[]{3, 1}, 1); // row 3, col 1 위치에 1을 넣겠다.
        labels.putScalar(new int[]{3, 0}, 1); // row 3, col 0 위치에 1을 넣겠다.
        labels.putScalar(new int[]{3, 1}, 0); // row 3, col 1 위치에 0을 넣겠다.
        //==============================================================================








        // 모델 설계
        // build dataset object
        // input
        // labels : output from input
        DataSet ds = new DataSet(input, labels);
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .updater(new Sgd(0.1))
                .seed(seed)
                .biasInit(0)
                .miniBatch(false)
                .list()
                .layer(new DenseLayer.Builder()
                        .nIn(2)
                        .nOut(4)
                        .activation(Activation.SIGMOID)
                        .weightInit(new UniformDistribution(0, 1))
                        .build())
                .layer(new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .nOut(2)
                        .activation(Activation.SOFTMAX) // XNOR 과 XOR 이 SOFTMAX 로 나올 수 있는 값이여서 SOFTMAX 를 적용한 모습
                        .weightInit(new UniformDistribution(0, 1))
                        .build())
                .build();

        MultiLayerNetwork net = new MultiLayerNetwork(conf);
        net.init();
        net.setListeners(new ScoreIterationListener(100));
        // C&P from LSTMCharModellingExample
        // Print the number of parameters in the network (and for each type)
        System.out.println(net.summary());







        // 학습
        // here the actual learning takes place
        for( int i=0; i < nEpochs; i++ ) {
            net.fit(ds);
        }

        // 학습 결과
        // build output for every training sample
        INDArray output = net.output(ds.getFeatures());
        System.out.println(output);

        // 테스트 (평가)
        // let Evaluation prints stats how often the right output had the highest value
        Evaluation eval = new Evaluation();
        eval.eval(ds.getLabels(), output);
        System.out.println(eval.stats());

    }
}
