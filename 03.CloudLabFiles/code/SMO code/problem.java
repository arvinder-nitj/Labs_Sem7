package scheduler;
import java.lang.Math;
public class problem {
    static int partitionIndex(Task arr[], int low, int high)
    {
        double pivot = arr[high].deadLine;
        int i = (low-1);
        for (int j=low; j<high; j++) {
            if (arr[j].deadLine < pivot) {
                i++;
                Task temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        Task temp = arr[i+1];
        arr[i+1] = arr[high];
        arr[high] = temp;
        return i+1;
    }
    static void sortTasks(Task arr[],int st,int ed) {
        if(st<ed) {
            int p = partitionIndex(arr,st,ed);
            sortTasks(arr,st,p-1);
            sortTasks(arr,p+1,ed);
        }
    }

    static void execute(int vm,int t,Task tarr[],Vm varr[])
    {
        smo Obj = new smo();
        int option = 1;
        // 0 for Cost Minimization
        // 1 for Makespan Minimization
        // 2 for Execution time Minimization
        // 3 for Energy Consumption
        // 4 for Degree of load balancing
        // 5 for Task Rejection Ratio

        if(option==5) {
            sortTasks(tarr,0,t-1);
        }

        Obj.init(20,t,vm,tarr,varr,option);
        Obj.execute();

        // Solve All Problems
        /*
        for(int op = 0;op<=5;op++) {
            Obj.init(20,t,vm,tarr,varr,op);
            Obj.execute();
        }
         */

    }
    public static void main(String[] args)
    {
        // Initialize Virtual Machines
        int n_VM = 100;

        Vm vm_arr[]= new Vm[n_VM];
        for(int i = 0; i<n_VM;i++)
        {
            double x=100; double y=1000;
            double speed= (int) (x+(Math.random()*(y-x)));

            //    double speed = Math.random()*1000;

            double cost;
            if(speed <= 250)            // macro VM
                cost = 0.0084;
            else if(speed > 200 && speed<=500)       // Medium Speed VM
                cost = 0.0255;

            else if(speed > 500 && speed<=750)       // large VM
                cost = 0.0501;
            else
                cost = 0.102;                         //high speed VM
            vm_arr[i] = new Vm();
            vm_arr[i].init_Vm(speed,cost);
        }


        // Initialize Tasks
        int n_Task = 300;
        Task task_arr[] = new Task[n_Task];
        for(int i = 0; i<n_Task;i++)
        {
            double x=200000; double y=500000;
            double length= (int) (x+(Math.random()*(y-x)));

            double x1=500; double y1=3000;
            double deadLine= (int) (x1+(Math.random()*(y1-x1)));
            //deadLine/=10.0;
            task_arr[i] = new Task();
            task_arr[i].init_Task(length,deadLine);
        }

        execute(n_VM,n_Task,task_arr,vm_arr);
    }
}

