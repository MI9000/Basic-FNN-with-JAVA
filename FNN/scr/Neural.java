package scr;


@SuppressWarnings("unused")
public class Neural {

    private QUASO[] weight, bias, hiddenlayer, hiddenlayer_ac;
    private QUASO data_train, target_data;
    private int[] neural;
    private double learning_rate = 0,loss;
    private int epoch = 0;

    

    /**
     * @param data_train ชุดข้อมุลที่เราต้องการนำมาเทรน
     * @param data_target ผลลัพของชุดข้อมูล
     * @param neural array กำหนดจำนวนชั้นเลเยอ ตัวแรกคือจำนวนอินพุท ตัวสุดท้ายคือ จำนวนเอ้าพุท ตรงกลางระหว่างสองตัวเลขนี้คือ hiddenlayer
     */
    public Neural (QUASO data_train ,QUASO data_target ,int[] neural){
        this.data_train = data_train;
        this.target_data = data_target;
        this.neural = neural;

        this.hiddenlayer = new QUASO[neural.length-1];
        this.hiddenlayer_ac = new QUASO[neural.length-1];

        this.weight = new QUASO[neural.length-1];
        this.bias = new QUASO[neural.length-1];

        for(int i = 0;i < neural.length -1; i++){
            weight[i] = new QUASO(neural[i+1], neural[i], true); bias[i] = new QUASO(neural[i+1], 1, true); // hid 1
        }
    }

    public Neural (int[] neural){
        this.neural = neural;
    }

    public void setInput(QUASO input){
        this.data_train = input;
    }

    public void setOutput(QUASO output){
        this.target_data = output;
    }

    public void setEpoch(int x){
        this.epoch = x;
    }

    public void setLearningRate(double x){
        this.learning_rate = x;
    }

    public void setWeight(QUASO[] x){
        this.weight = x;
    }

    public void setBias(QUASO[] x){
        this.bias= x;
    }

    public void showData(){
        hiddenlayer_ac[neural.length-2].show();
    }
    
    public QUASO[] getBias(){
        return bias;
    }

    public QUASO[] getWeight(){
        return weight;
    }

    public void setup(){
        if(data_train == null && target_data == null){
            throw new IllegalArgumentException("Input and Output is null.");
        }
        else if(data_train == null){
            throw new IllegalArgumentException("Input is null.");
        }
        this.hiddenlayer = new QUASO[neural.length-1];
        this.hiddenlayer_ac = new QUASO[neural.length-1];

        this.weight = new QUASO[neural.length-1];
        this.bias = new QUASO[neural.length-1];
        for(int i = 0;i < neural.length -1; i++){
            weight[i] = new QUASO(neural[i+1], neural[i], true); 
            bias[i] = new QUASO(neural[i+1], 1, true);
        }
    }

    public<T extends activation> void train(T act) {

        long startTime = System.currentTimeMillis();

        System.out.println("Start training");
        for (int i = 0; i < epoch; i++) {
            forward(act);
            double error = QUASO.costForward(hiddenlayer_ac[hiddenlayer_ac.length-1],target_data);
            System.out.printf("epoch %d  loss = %f\n",i+1, error);
            backward(act);
        }

    }

    public<T extends activation> void predict(QUASO input, T act){
        QUASO[] z = new QUASO[hiddenlayer.length];
        QUASO[] a = new QUASO[hiddenlayer.length];

        for (int i = 0; i < hiddenlayer.length; i++) {
            if(i == 0){
                z[0] = weight[0].dot(input).addBias(bias[0]);
                a[0] = z[0].activation(act);
            }

            else if (i != hiddenlayer.length - 1){
                z[i] = weight[i].dot(a[i-1]).addBias(bias[i]);
                a[i] = z[i].activation(act);
            }

            else{
                z[i] = weight[i].dot(a[i-1]).addBias(bias[i]);
                a[i] = z[i].activation(act);
            }

       }

        a[hiddenlayer.length-1].show();
    }
    

    private<T extends activation> void forward(T act){

        for (int i = 0; i < hiddenlayer.length; i++) {
            
            if(i == 0){
                hiddenlayer[i] = weight[i].dot(data_train).addBias(bias[i]);
                hiddenlayer_ac[i] = hiddenlayer[i].activation(act);
            }
            
            else if (i != hiddenlayer.length-1){
                hiddenlayer[i] = weight[i].dot(hiddenlayer_ac[i-1]).addBias(bias[i]);
                hiddenlayer_ac[i] = hiddenlayer[i].activation(act);
            }
            
            else{
                hiddenlayer[i] = weight[i].dot(hiddenlayer_ac[i-1]).addBias(bias[i]);
                hiddenlayer_ac[i] = hiddenlayer[i].activation(act);
            }
            

       }

    }

    private<T extends activation> void backward(T act) {
        QUASO cost = hiddenlayer_ac[hiddenlayer_ac.length-1].costBackward(target_data);
        QUASO delta = cost.mul(hiddenlayer[hiddenlayer_ac.length-1].activation_prime(act));
        QUASO temp;
        
        for (int i = hiddenlayer_ac.length-1 ; i > -1; i--) {
            
            if(i != hiddenlayer_ac.length-1){ 
                temp = weight[i+1].transpose().dot(delta);
                delta = temp.mul(hiddenlayer[i].activation_prime(act));
            
            } 
            
            if(i == 0){
                weight[i] = weight[i].subtract(delta.dot(data_train.transpose()).mulScal(learning_rate));
                bias[i] = bias[i].subtract_forbias(delta.mulScal(learning_rate));
            }
            

        }


    }


    
}
