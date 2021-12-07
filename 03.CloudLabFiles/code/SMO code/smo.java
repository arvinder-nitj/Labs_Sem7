package scheduler;
import javax.annotation.processing.SupportedSourceVersion;
import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

public class smo {

    static int N;  // Number of Spider Monkey
    static int D;  // Dimensions of each Monkey  (Number of Tasks)

    static int globalLeader;
    static int[]localLeader;
    static ArrayList<Integer>gpSt,gpEd;
    static Random rand = new Random();


    static double[][]SM;        // N spider Monkey each of Dimension D
    static double[]SMnew;       // Used to update a Spider Monkey
    static double[]SMmax;       // Upper Bound of Dimension D
    static double[]SMmin;       // Lower Bound of Dimension D
    static double[]fitness;     // Fitness function value
    static double[]fxValue;     // Function value

    // Control Parameters
    static int mxIterations = 50;
    static double pr = 0.7;
    static int globalLimitCount=0,globalLeaderLimit;
    static int localLeaderLimit;
    static ArrayList<Integer>localLimitCount;
    static int noOfGroups = 1;
    static int mnNoOfMonkey = 10;
    static int mxGroups;

    // Problem Description
    static Task task_arr[];
    static  int n_vm;
    static Vm vm_arr[];

    //Problem to solve
    //0 Cost
    //1 Makespan
    //2 Execution Time
    static int option;

    static {
        gpSt = new ArrayList<Integer>();
        gpEd = new ArrayList<Integer>();
        localLimitCount = new ArrayList<Integer>();
    }
    //Number of Monkeys, Number of Tasks , Number of virtual machines , Task array , Virtual machine array
    static void init(int nMonkey,int nTask,int nVm,Task Tarr[],Vm Varr[],int choice){
        n_vm = nVm;
        vm_arr = Varr;
        task_arr = Tarr;
        N = nMonkey;
        D = nTask;
        localLeader = new int[N];
        SM = new double[N][D];       // N spider Monkey each of Dimension D
        SMnew = new double[D];       // Used to update a Spider Monkey
        SMmax = new double[D];       // Upper Bound of Dimension D
        SMmin = new double[D];       // Lower Bound of Dimension D
        fitness = new double[N];     // Fitness function value
        fxValue = new double[N];     // Function value

        globalLeaderLimit=2*N;
        localLeaderLimit=D*N;
        mxGroups = N/mnNoOfMonkey;

        option = choice;

        for(int i=0;i<D;i++)
        {
            SMmax[i] = n_vm-1;
            SMmin[i] = 0;
        }
    }

    static double U(double x,double y) {
        return x + (Math.random() * ((y - x)));
    }

    static double fx(int idx){
        double cost = 0;
        double []time = new double[n_vm];
        for(int i=0;i<n_vm;i++)
            time[i] = 0;

        for(int task = 0;task<D;task++)
        {
            int vmNo = (int)SM[idx][task];
            time[vmNo]+= task_arr[task].length/vm_arr[vmNo].speed;
        }

        if(option==0) // Cost
        {
            for(int i=0;i<n_vm;i++)
            {
                time[i] = Math.ceil(time[i]/((double)3600));
                cost+=(time[i]*vm_arr[i].cost);
            }

        }
        else if(option==1) //MakeSpan
        {
            for(int i=0;i<n_vm;i++)
            {
                cost = Math.max(cost,time[i]/((double)3600));
            }
        }
        else if(option==2) //Execution Time
        {
            for(int i=0;i<n_vm;i++)
            {
                cost+=time[i];
            }

            cost/=((double)3600);
        }
        else if(option==3)  // Energy Consumption
        {
            double mx = time[0]/((double)3600);
            for(int i=0;i<n_vm;i++)
            {
                mx = Math.max(mx,time[i]/((double)3600));
            }

            for(int i=0;i<n_vm;i++)
            {
                double totalTime = time[i]/((double)3600);
                double idleTime = mx - totalTime;
                cost = cost + idleTime*50 + totalTime*100;
            }
            //System.out.println(cost);
        }
        else if(option==4) //Degree Of Load Balancing
        {
            double mn = time[0]/((double)3600);
            double mx = time[0]/((double)3600);
            double avg = 0;
            for(int i=0;i<n_vm;i++)
            {
                mn = Math.min(mn,time[i]/((double)3600));
                mx = Math.max(mx,time[i]/((double)3600));
                avg+=time[i]/((double)3600);
            }
            avg/=((double)n_vm);

            cost = (mx-mn)/avg;
        }
        else if(option==5)  //Task rejection Ratio
        {
            for(int i=0;i<n_vm;i++)
                time[i] = 0.0;

            for(int task = 0;task<D;task++)
            {
                int vmNo = (int)SM[idx][task];
                double deadLine = task_arr[task].deadLine;
                double length = task_arr[task].length/vm_arr[vmNo].speed;
                if(deadLine >= (time[vmNo] + length))
                    time[vmNo]+= length;
                else
                    cost+=(1.0);
            }
            //   System.out.println(cost);
            cost=100*(cost)/((double)D);
        }


//        for(int task =0;task<D;task++) {
//            cost+=(task_arr[task].length/vm_arr[(int)SM[idx][task]].speed)*vm_arr[(int)SM[idx][task]].cost;
//        }
        return cost;
    }


    static double fitness(int idx , double fx) {
        if(fx >= 0) {
            return 1/(1+fx);
        }
        return 1 +Math.abs(fx);
    }

    // return index of spider monkey with max fitness
    static int getGlobalLeader(){
        int idx =0;
        double mx = fitness[0];

        for(int i=1;i<N;i++){
            if(fitness[i] > mx) {
                mx=fitness[i];
                idx=i;
            }
        }
        return idx;
    }

    static void initialization(){
        for(int i=0;i<N;i++){
            for(int j=0;j<D;j++){
                SM[i][j] = SMmin[j] + Math.random()*(SMmax[j]-SMmin[j]);
                if(SM[i][j]>SMmax[j])
                    SM[i][j] = SMmax[j];
                else if(SM[i][j]<SMmin[j])
                    SM[i][j] = SMmin[j];
                //System.out.print(SM[i][j]+ "      ");
            }
            //System.out.println();
        }

        for(int i=0;i<N;i++){
            fxValue[i] = fx(i);
            //System.out.println("Initial Function Value " + fxValue[i]);
        }

        for(int i=0;i<N;i++){
            fitness[i] = fitness(i,fxValue[i]);
            //System.out.println("Initial Fitness Value " + fitness[i]);
        }

        globalLeader = getGlobalLeader();
        gpSt.add(0);
        gpEd.add(N-1);
        localLimitCount.add(0);
        for(int i=0;i<N;i++){
            localLeader[i] = globalLeader;
        }
    }
/*
new_position[j]=Population[i][j]+(LocalLeaderPosition[k][j]-Population[i][j])*(alea(0, 1))+
                            (Population[PopRand][j]-Population[i][j])*(alea(0, 1)-0.5)*2;
 */

    static void localLeaderPhase(){
        for(int k=0;k<gpSt.size();k++){
            int st = gpSt.get(k);
            int ed = gpEd.get(k);

            for(int i=st;i<=ed;i++){

                int r = i;
                while(r==i) {
                    r = rand.nextInt((ed-st)+1)+st;
                }

                for(int j=0;j<D;j++){
                    if(Math.random() >= pr){
                        //SMnew[j] = SM[i][j] + Math.random()*(SM[localLeader[i]][j]-SM[i][j]) + U(-1,1)*(SM[r][j]-SM[i][j]);
                        SMnew[j] = SM[i][j] + Math.random()*(SM[localLeader[i]][j]-SM[i][j]) + (SM[r][j]-SM[i][j])*(Math.random()-0.5)*2;
                        if(SMnew[j] < SMmin[j])
                            SMnew[j] = SMmin[j];
                        if(SMnew[j] > SMmax[j])
                            SMnew[j] = SMmax[j];
                        double prevJ = SM[i][j];
                        double prevFx = fxValue[i];
                        double prevFitness = fitness[i];

                        //System.out.println(SMnew[j] + "      "+SM[i][j]);

                        SM[i][j] = SMnew[j];

                        double newFx = fx(i);
                        double newFitness = fitness(i,newFx);

                        if(newFitness > prevFitness){
                            fitness[i]=newFitness;
                            fxValue[i] =newFx;
                        }
                        else{
                            SM[i][j] = prevJ;
                        }


                    }
                }
                //System.out.println();
            }


        }
    }

    static double getMaxFitness(){

        double mx = fitness[0];
        for(int i=1;i<N;i++){
            if(fitness[i] > mx) {
                mx=fitness[i];
            }
        }
        return mx;
    }


    static void globalLeaderPhase(){

        for(int k=0;k<gpSt.size();k++){
            int st = gpSt.get(k);
            int ed=gpEd.get(k);
            int size = ed-st+1;
            int count=0;
            while(count < size){
                for(int i = st;i<=ed;i++){
                    double probi = 0.9 *(fitness[i]/getMaxFitness())+0.1;
                    if(Math.random() < probi){
                        count++;
                        int j = rand.nextInt(D);
                        int r = i;
                        while(r==i) {
                            r = rand.nextInt((ed-st)+1)+st;
                        }

                        //SMnew[j] = SM[i][j] + Math.random()*(SM[globalLeader][j]-SM[i][j]) + U(-1,1)*(SM[r][j]-SM[i][j]);
                        SMnew[j] = SM[i][j] + Math.random()*(SM[globalLeader][j]-SM[i][j]) + (SM[r][j]-SM[i][j])*(Math.random()-0.5)*2;
                        if(SMnew[j] < SMmin[j])
                            SMnew[j] = SMmin[j];
                        if(SMnew[j] > SMmax[j])
                            SMnew[j] = SMmax[j];

                        double prevJ = SM[i][j];
                        double prevFx = fxValue[i];
                        double prevFitness = fitness[i];

                        //System.out.println(SMnew[j] + "     " + SM[i][j]);

                        SM[i][j] = SMnew[j];

                        double newFx = fx(i);
                        double newFitness = fitness(i,newFx);

                        if(newFitness > prevFitness){
                            fitness[i]=newFitness;
                            fxValue[i] =newFx;
                        }
                        else{
                            SM[i][j] = prevJ;
                        }


                    }
                }
            }
        }

    }

    static void globalLearderLearning(){
        int newGl = globalLeader;
        double globalMin = fxValue[globalLeader];

        for(int i=0;i<N;i++) {
            if(fxValue[i] < globalMin){
                newGl = i;
                globalMin = fxValue[i];
            }

        }

        if(newGl == globalLeader)
            globalLimitCount++;
        else
            globalLeader = newGl;
    }


    static void localLeaderLearning(){

        for(int k=0;k<gpSt.size();k++){
            int st = gpSt.get(k);
            int ed = gpEd.get(k);
            int newLL = st;
            double localMin = fxValue[st];

            for(int i = st+1;i<=ed;i++){
                if(fxValue[i] < localMin) {
                    localMin = fxValue[i];
                    newLL = i;
                }
            }

            if(newLL == localLeader[st]){
                localLimitCount.set(k,localLimitCount.get(k)+1);
            }
            else{
                for(int i=st;i<=ed;i++)
                    localLeader[i] = newLL;
                if(gpSt.size()==1)
                    globalLeader = newLL;
            }

        }

    }

    static void localLeaderDecisionPhase(){
        for(int k=0;k<gpSt.size();k++){

            if(localLimitCount.get(k) > localLeaderLimit){
                localLimitCount.set(k,0);
                int ll = localLeader[k];
                int st= gpSt.get(k);
                int ed = gpEd.get(k);

                for(int i=st;i<=ed;i++){

                    if(i==globalLeader)
                        continue;

                    for(int j=0;j<D;j++){
                        if(U(0,1)>=pr){
                            SM[i][j] = SMmin[j] + Math.random()*(SMmax[j]-SMmin[j]);
                        }
                        else {
                            SM[i][j] = SM[i][j] + Math.random()*(SM[globalLeader][j]-SM[i][j])+Math.random()*(SM[i][j]-SM[ll][j]);
                        }

                        if(SM[i][j] < SMmin[j])
                            SM[i][j] = SMmin[j];
                        if(SM[i][j] > SMmax[j])
                            SM[i][j] = SMmax[j];

                        fxValue[i] = fx(i);
                        fitness[i] = fitness(i,fxValue[i]);

                    }
                }
            }
        }
    }


    static void globalLeaderDecisionPhase(){
        if(globalLimitCount > globalLeaderLimit){
            globalLimitCount = 0;
            if(noOfGroups < mxGroups){
                int size = gpSt.size();
                for(int k=0;k<size;k++){
                    int st= gpSt.get(k);
                    int ed = gpEd.get(k);
                    int lL = localLeader[st];
                    if((ed-st+1)>=2*mnNoOfMonkey){
                        noOfGroups++;

                        int newSize = (ed-st+1)/2;
                        gpEd.set(k,st+newSize-1);

                        gpSt.add(st+newSize);
                        gpEd.add(ed);
                        localLimitCount.add(0);
                        for(int j=st+newSize;j<=ed;j++)
                            localLeader[j] = st+newSize;

                    }
                }
            }
            else{
                // Merge all groups
                noOfGroups = 1;
                for(int i=0;i<N;i++)
                    localLeader[i] = globalLeader;
                gpSt = new ArrayList<Integer>();
                gpEd = new ArrayList<Integer>();
                localLimitCount = new ArrayList<Integer>();

                gpSt.add(0);
                gpEd.add(N-1);
                localLimitCount.add(0);

            }
            localLeaderLearning();
        }
    }
    // Main function
    public static void execute(){

        initialization();
        //System.out.println("Initial Global Leader " + globalLeader);

        while(mxIterations > 0){

            // local leader learning phase - position update in Local leader phase
            localLeaderPhase();

            // Global leader phase - position update in global leader phase
            globalLeaderPhase();

            // Global Leader Learning
            globalLearderLearning();

            // Local Leader Learning
            localLeaderLearning();

            // Local Leader Decision Phase
            localLeaderDecisionPhase();

            // Global Leader Decision Phase
            globalLeaderDecisionPhase();

            // Termination Criteria
            mxIterations--;
        }

        // Double checking
//        double mn = fxValue[globalLeader];
//
//        for(int i=0;i<N;i++){
//            if(fxValue[i] < mn){
//                mn=fxValue[i];
//            }
//        }
//        System.out.println("Best Function Value  " + mn);
//        System.out.println("Minimum Cost         " + fxValue[globalLeader]);
//
//        System.out.println("Best Fitness Value  " + fitness[globalLeader]);
//        System.out.println();
//        System.out.println(" ----- End -----");

        if(option==0) // Cost
        {
            System.out.println("Minimum Cost         " + fxValue[globalLeader]);
            System.out.println("Best Fitness Value  " + fitness[globalLeader]);
            System.out.println();
            System.out.println(" ----- End -----");
        }
        else if(option==1) //MakeSpan
        {
            System.out.println("Minimum Makespan         " + fxValue[globalLeader]);
            System.out.println("Best Fitness Value  " + fitness[globalLeader]);
            System.out.println();
            System.out.println(" ----- End -----");
        }
        else if(option==2) //Execution Time
        {
            System.out.println("Minimum Execution Time         " + fxValue[globalLeader]);
            System.out.println("Best Fitness Value  " + fitness[globalLeader]);
            System.out.println();
            System.out.println(" ----- End -----");
        }
        else if(option==3)  //Energy Consumption
        {
            System.out.println("Minimum Energy Consumption         " + fxValue[globalLeader]);
            System.out.println("Best Fitness Value  " + fitness[globalLeader]);
            System.out.println();
            System.out.println(" ----- End -----");
        }
        else if(option==4) //Degree of Load balancing
        {
            System.out.println("Minimum Degree Of load Balancing         " + fxValue[globalLeader]);
            System.out.println("Best Fitness Value  " + fitness[globalLeader]);
            System.out.println();
            System.out.println(" ----- End -----");
        }
        else if(option==5) //Task Rejection Ratio
        {
            System.out.println("Minimum Task Rejection Ratio         " + fxValue[globalLeader]);
            System.out.println("Best Fitness Value  " + fitness[globalLeader]);
            System.out.println();
            System.out.println(" ----- End -----");
        }

    }
}