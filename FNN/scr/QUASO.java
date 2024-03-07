package scr;

public class QUASO extends ProgressMatrix {
    int row;
    public int col;
    public double[][] matrix;

    /**
     * ประกาศการสร้างเมริกโดยที่
     * @param row
     * @param col
     * @param type ถ้าจริงจะเป้นการเติมตัวเลข random ถ้า fals ว่าง
     */
    public QUASO(int row, int col, boolean type) {
        this.row = row;
        this.col = col;
        matrix = new double[row][col];
        if (type) fillRandom();
    }


    public int GetRow(){
        return this.row;
    }


    public int GetCol(){
        return this.col;
    }
    

    private void fillRandom() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                matrix[i][j] = Math.random();
            }
        }
    }

    //!การลบ
    /**
     * การนำเอาการคูณสองอันมาบวกกันโดยที่ other คือตัวหลัง
     * 
     * @param other เมริกที่ต้องการนำมาบวก
     * @return เมทริก ตัวใหม่โดยที่ตัวหลักยังอยู่เหมือนเดิม
     */
    
    @Override
    public QUASO add(QUASO other) {
        if (this.row != other.row || this.col != other.col) {
            throw new IllegalArgumentException("Matrix dimensions must match for addition.");
        }
        QUASO result = new QUASO(this.row, this.col, false);
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                result.matrix[i][j] = this.matrix[i][j] + other.matrix[i][j];
            }
        }
        return result;
    }
    //!การลบ
    /**
     * การนำเอาการคูณสองอันมาลบกันโดยที่ other คือตัวหลัง
     * 
     * @param other เมริกที่ต้องการนำมาลบ
     * @return เมทริก ตัวใหม่โดยที่ตัวหลักยังอยู่เหมือนเดิม
     */
    @Override
    public QUASO subtract(QUASO other) {
        if (this.row != other.row || this.col != other.col) {
            throw new IllegalArgumentException("subtract Matrix dimensions do not match for subtraction.");
        }
        QUASO result = new QUASO(this.row, this.col, false);
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                result.matrix[i][j] = this.matrix[i][j] - other.matrix[i][j];
            }
        }
        return result;
    }

    /** <pre>
     *เป็น method ที่ออกแบบมาเพื่อแก้ปัญหาในการลบไบแอส ต่อเมทริกส์ h
     *โดยที่ bias และ h จะถูกบวกเข้าด้วยกันเพื่อสร้างเมทริกส์ใหม่ที่มีค่า bias ถูกเพิ่มเข้าไปในแต่ละองค์ประกอบของ h <br>
     * bias   h
     * [0.2] [0.1, 0.2, 0.3]
     * [0.4] [0.1, 0.7, 0.5]
     * 
     * h.subtract_forBias(bias)
     * [0.1, 0.0, 0.1]
     * [-0.3, 0.3, 0.1] <br><br>
     * 
     * @param bias ไบแอสที่จะเพิ่มเข้าไปใน h เป็น array ของ double<br>
     * @return เมทริกส์ใหม่ที่ได้จากการลบ bias เข้าไปใน h โดยที่ตัวหลักยังคงอยู่เหมือนเดิม
     */
    
    // public QUASO bub(QUASO bias) {
    //     if (this.row != bias.row) {
    //         throw new IllegalArgumentException("subtract Matrix dimensions do not match for subtraction.");
    //     }
    //     QUASO result = new QUASO(this.row, this.col, false);
    //     for (int i = 0; i < this.row; i++) {
    //         for (int j = 0; j < this.col; j++) {
    //             result.matrix[i][j] = this.matrix[i][j] - bias.matrix[i][0];
    //         }
    //     }
    //     return result;
    // }


    //!การคูณสเลล่า
    /**
     * การนำเอาการคูณเสกลล่า มาคูณใส่ในเมริกแต่ละตำแหน่ง ij
     * 
     * @param scalar ค่าที่ต้องการนำมาคูณ
     * @return เมทริก ตัวใหม่โดยที่ตัวหลักยังอยู่เหมือนเดิม
     */
    @Override
    public QUASO mulScal(double scalar) {
        QUASO result = new QUASO(this.row, this.col, false);
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                result.matrix[i][j] = this.matrix[i][j] * scalar;
            }
        }
        return result;
    }


    //! การด็อทเมทริก
    /**
     * การคูณเมริก แถวตัวแรกคูณหลักเมรทิก other
     * 
     * @param other ค่าที่ต้องการนำมาคูณ
     * @return เมทริก ตัวใหม่โดยที่ตัวหลักยังอยู่เหมือนเดิม
     */
    @Override
    public QUASO dot(QUASO other) {
        if (this.col != other.row) {
            throw new IllegalArgumentException("Dot Matrix dimensions do not match for multiplication.");
        }
        QUASO result = new QUASO(this.row, other.col, false);
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < other.col; j++) {
                for (int k = 0; k < this.col; k++) {
                    result.matrix[i][j] += this.matrix[i][k] * other.matrix[k][j];
                }
            }
        }
        return result;
    }

    /**
     * การคูณเมริก แบบ ตำแหน่งเดียวกันคูณกัน
     * 
     * @param parameter1 matrix ที่ต้องการนำมาคูณกัน
     * @return เมทริก ตัวใหม่โดยที่ตัวหลักยังอยู่เหมือนเดิม
     */
    @Override
    public QUASO mul(QUASO other) {
        if (this.row != other.row || this.col != other.col) {
            throw new IllegalArgumentException("Mul Matrix dimensions do not match for multiplication.");
        }
        QUASO result = new QUASO(this.row, this.col, false);
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < other.col; j++) {
                    result.matrix[i][j] += this.matrix[i][j] * other.matrix[i][j];
                
            }
        }
        return result;
    }

    //! บอก bias 
    /** <pre>
     *เป็น method ที่ออกแบบมาเพื่อแก้ปัญหาในการบวกไบแอส ต่อเมทริกส์ h
     *โดยที่ bias และ h จะถูกบวกเข้าด้วยกันเพื่อสร้างเมทริกส์ใหม่ที่มีค่า bias ถูกเพิ่มเข้าไปในแต่ละองค์ประกอบของ h <br>
     * bias   h
     * [0.2] [0.1, 0.2, 0.3]
     * [0.4] [0.1, 0.7, 0.5]
     * 
     * h.addBias(bias)
     * [0.3, 0.4, 0.5]
     * [0.5, 1.1, 0.9] <br><br>
     * 
     * @param bias ไบแอสที่จะเพิ่มเข้าไปใน h เป็น array ของ double<br>
     * @return เมทริกส์ใหม่ที่ได้จากการเพิ่ม bias เข้าไปใน h โดยที่ตัวหลักยังคงอยู่เหมือนเดิม
     */

    public QUASO addBias(QUASO bias) {
        QUASO result = new QUASO(this.row, this.col, false);

        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                result.matrix[i][j] = this.matrix[i][j] + bias.matrix[i][0];
            }
        }

        return result;
    }



    //!แสดง row col
    public void getSize() {
        System.out.printf("row : %d and col : %d\n",row,col);
    }

    //!แสดงผล
    public void show() {
        for (int i = 0; i < row; i++) {
            System.out.print("| ");
            for (int j = 0; j < col; j++) {
                System.out.printf("%.10f ", matrix[i][j]);
            }
            System.out.println("|");
        }
    }


    /** ประกาศ QUASO ที่กำหนกค่า เมทริกแต่ละตำแหน่งเอง
     * row col เท่าไหร่ก็ได้ 
     * @param data รูปแบบเมริกที่ต้องการ
     */
    public QUASO fromArray(Double[][] data) {
        int rows = data.length;
        int cols = data[0].length;
        double[][] primData = new double[rows][cols]; 

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                primData[i][j] = data[i][j]; 
            }
        }

        return new QUASO(primData); 
    }

    //!ทรานสโพสแบบสร้างใหม่
    /** 
     * 
     * @return เมริกตัวใหม่ ตัวเดิมยังไม่มีการทรานสโส
     */
    public QUASO transpose() {
        QUASO transposedMatrix = new QUASO(this.col, this.row, false);
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                transposedMatrix.matrix[j][i] = this.matrix[i][j];
            }
        }
        return transposedMatrix;
    }

    

    //!ทรานสโพสแบบเปลี่ยนตัวเอง
    /** 
     * 
     *ตัวเดิมทำการทรานสโส
     */
    public void T() {
        double[][] transposedMatrix = new double[this.col][this.row];
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                transposedMatrix[j][i] = this.matrix[i][j];
            }
        }
        this.matrix = transposedMatrix;
        int temp = this.row;
        this.row = this.col;
        this.col = temp;
    }
    

    private QUASO(double[][] data) {
        this.row = data.length;
        this.col = data[0].length;
        this.matrix = new double[row][col];
        for (int i = 0; i < row; i++) {
            System.arraycopy(data[i], 0, this.matrix[i], 0, col);
        }
    }


    // !!----------Function SERIES----------!!
    /** ทุกตำแหน่งทำการผ่าน sigmoid
     * 
     * @return ตัวใหม่ ตัวเดิมไม่ได้มีการผ่่าน sigmoid
     */
    public QUASO sigmoid() {
        QUASO result = new QUASO(this.row, this.col, false);
        Sigmoid sigmoid = new Sigmoid();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                result.matrix[i][j] = sigmoid.forward(this.matrix[i][j]);
            }
        }
        return result;
    }

    /** ทุกตำแหน่งทำการผ่าน relu
     * 
     * @return ตัวใหม่ ตัวเดิมไม่ได้มีการผ่่าน relu
     */
    public QUASO relu() {
        QUASO result = new QUASO(this.row, this.col, false);
        Relu relu = new Relu();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                result.matrix[i][j] = relu.forward(matrix[i][j]);
            }
        }
        return result;
    }

    /** ทุกตำแหน่งทำการผ่าน tanh
     * 
     * @return ตัวใหม่ ตัวเดิมไม่ได้มีการผ่่าน tanh
     */
    public QUASO tanh() {
        QUASO result = new QUASO(this.row, this.col, false);
        Tanh tanh = new Tanh();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                result.matrix[i][j] = tanh.forward(this.matrix[i][j]);
            }
        }
        return result;
    }

    public <T extends activation> QUASO activation(T activationObject){
        QUASO result = new QUASO(this.row, this.col, false);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                result.matrix[i][j] = activationObject.forward(this.matrix[i][j]);
            }
        }
        return result;
    }

    /** ทุกตำแหน่งทำการผ่าน softmax
     * 
     * @return ตัวใหม่ ตัวเดิมไม่ได้มีการผ่่าน softmax
     */
    public QUASO softmax() {
        QUASO result = new QUASO(this.row, this.col, false);
        for (int i = 0; i < this.row; i++) {
            double sum = 0;
            double[] exps = new double[this.col];
            double max = Double.NEGATIVE_INFINITY;
            for (int j = 0; j < this.col; j++) {
                if (this.matrix[i][j] > max) {
                    max = this.matrix[i][j];
                }
            }
            for (int j = 0; j < this.col; j++) {
                exps[j] = Math.exp(this.matrix[i][j] - max);
                sum += exps[j];
            }
            for (int j = 0; j < this.col; j++) {
                result.matrix[i][j] = exps[j] / sum;
            }
        }
        return result;
    }
    
    // !!----------Function DERIVATIVE SERIES----------!!

    /** ทุกตำแหน่งทำการผ่าน sigmoid_prime
     * 
     * @return ตัวใหม่ ตัวเดิมไม่ได้มีการผ่่าน sigmoid_prime
     */
    public QUASO sigmoid_prime(){
        QUASO result = new QUASO(this.row, this.col, false);
        Sigmoid sigmoid = new Sigmoid();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                result.matrix[i][j] = sigmoid.backward(this.matrix[i][j]);
            }
        }
        return result;
    }
    /** ทุกตำแหน่งทำการผ่าน relu_prime
     * 
     * @return ตัวใหม่ ตัวเดิมไม่ได้มีการผ่่าน relu_prime
     */
    public QUASO relu_prime(){
        QUASO result = new QUASO(this.row, this.col, false);
        Relu relu = new Relu();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                result.matrix[i][j] = relu.backward(this.matrix[i][j]);
            }
        }
        return result;
    }

    /** ทุกตำแหน่งทำการผ่าน tanh_prime
     * 
     * @return ตัวใหม่ ตัวเดิมไม่ได้มีการผ่่าน tanh_prime
     */

    public QUASO tanh_prime(){
        QUASO result = new QUASO(this.row, this.col, false);
        Tanh tanh = new Tanh();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                result.matrix[i][j] = tanh.backward(this.matrix[i][j]);
            }
        }
        return result;
    }

    public <T extends activation> QUASO activation_prime(T activationObject){
        QUASO result = new QUASO(this.row, this.col, false);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                result.matrix[i][j] = activationObject.backward(this.matrix[i][j]);
            }
        }
        return result;
    }


    // COST FUNCTION
    public static double costForward(QUASO yPred, QUASO yTrue) {
        if (yPred.row != yTrue.row || yPred.col != yTrue.col) {
            throw new IllegalArgumentException("costForward Dimensions do not match.");
        }

        double sum = 0;
        for (int i = 0; i < yPred.row; i++) {
            for (int j = 0; j < yPred.col; j++) {
                sum += Math.abs(yTrue.matrix[i][j] - yPred.matrix[i][j]);
            }
        }
        sum = sum/(yPred.row);
        return 0.5 * sum * sum;
    }

    
    // Method to calculate gradients for backpropagation
    public QUASO costBackward(QUASO yTrue) {
        if (this.row != yTrue.row || this.col != yTrue.col) {
            throw new IllegalArgumentException("costBackward Dimensions do not match.");
        }

        QUASO gradients = new QUASO(this.row, this.col, false);
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                gradients.matrix[i][j] = this.matrix[i][j] - yTrue.matrix[i][j];
            }
        }
        return gradients;
    }

    public QUASO each_subtrax(double cost){
        QUASO gradients = new QUASO(this.row, this.col, false);
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                gradients.matrix[i][j] = this.matrix[i][j] - cost;
            }
        }
        return gradients;
    }


    public QUASO subtract_forbias(QUASO bias) {
        QUASO result = new QUASO(this.row, this.col, false);

        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                result.matrix[i][j] = this.matrix[i][j] - bias.matrix[i][0];
            }
        }

        return result;
    
    }

}

