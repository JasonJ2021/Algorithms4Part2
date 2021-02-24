package BaseballElimination;

import edu.princeton.cs.algs4.*;

//% cat teams5.txt
//        5
//        New_York    75 59 28   0 3 8 7 3
//        Baltimore   71 63 28   3 0 2 7 7
//        Boston      69 66 27   8 2 0 0 3
//        Toronto     63 72 27   7 7 0 0 3
//        Detroit     49 86 27   3 7 3 3 0
public class BaseballElimination {
    private int N; // 队伍的数量
    private ST<String,int[]> st;
    private int idPosition;
    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename){
        if (filename == null){
            throw new IllegalArgumentException("file is null");
        }
        st = new ST<String, int[]>();
        In input = new In(filename);
        N = input.readInt();
        idPosition = N + 3 ;
        //N = 5 int[] 要有9个位置储存数据，最后一个是储存队伍的id
        int capacity = N + 3;
        for (int i = 0; i < N; i++) {
            String name = input.readString();
            int[] data = new int[capacity + 1];
            for (int j = 0; j < capacity; j++) {
                data[j] = input.readInt();
            }
            data[capacity] = i;
            st.put(name,data);
        }
    }

    // number of teams
    public int numberOfTeams(){
        return N;
    }

    // all teams
    public Iterable<String> teams(){
        return st.keys();
    }
    private void validateTeam(String team){
        if (team == null || !st.contains(team)){
            throw new IllegalArgumentException("team is invalid\n");
        }
    }
    // number of wins for given team
    public int wins(String team){
        validateTeam(team);
        return st.get(team)[0];
    }

    // number of losses for given team
    public int losses(String team){
        validateTeam(team);
        return st.get(team)[1];
    }

    // number of remaining games for given team
    public int remaining(String team){
        validateTeam(team);
        return st.get(team)[2];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2){
        validateTeam(team1);
        validateTeam(team2);
        int team2_id = st.get(team2)[N+3];
        return st.get(team1)[3+team2_id];
    }
    private FlowNetwork createFlowNetWork(String x){
        validateTeam(x);
        int teamX_id = st.get(x)[N+3];
        Queue<int[]> queue = new Queue<>(); // 记录谁和谁打，剩余的场数
        int count = 0;
        String[] name = new String[N];
        for (String key : st.keys()) {
            name[st.get(key)[idPosition]] = key;
        }
        for (int i = 0; i < N; i++) {
            if (teamX_id != i){
                int[] data = st.get(name[i]);
                for (int j = i+3 ; j < idPosition;j++){
                    if (data[j]!=0 && j-3 != teamX_id){
                        count++;
                        int[] games = new int[3];
                        games[0]= i;
                        games[1] = j-3;
                        games[2] = data[j];
                        queue.enqueue(games);
                    }
                }
            }
        }
        int vertix = N + count + 2;
        FlowNetwork baseball = new FlowNetwork(vertix);
        //先连接最后一个和各个team vertix;
        int xWin = st.get(x)[0];
        int xleft = st.get(x)[2];
        for (int i = 0; i < N; i++) {
            if (i != teamX_id){
                int win = st.get(name[i])[0];
                baseball.addEdge(new FlowEdge(i,vertix-1,xWin+xleft -win));
            }
        }
        //再连中间games 和 team 以及source和 games之间的边
        for (int i = N ; i < vertix-2;i++){
            int[] game = queue.dequeue();
            baseball.addEdge(new FlowEdge(vertix-2 , i,game[2]));
            baseball.addEdge(new FlowEdge(i,game[0],Double.POSITIVE_INFINITY));
            baseball.addEdge(new FlowEdge(i,game[1],Double.POSITIVE_INFINITY));
        }
        return baseball;
    }
    // is given team eliminated?
    public boolean isEliminated(String team){
        return certificateOfElimination(team) !=null;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team){
        validateTeam(team);
        Queue<String> queue = new Queue<>();
        int[] dataX = st.get(team);
        int max = dataX[0] + dataX[2];
        for (String key : st.keys()) {
            if(st.get(key)[0] > max){
                queue.enqueue(key);
            }
        }
        if (queue.isEmpty()){
            FlowNetwork baseball = createFlowNetWork(team);
            int v = baseball.V();
            FordFulkerson ff = new FordFulkerson(baseball,v-1,v-2);
            for (String s : st) {
                int id = st.get(s)[idPosition];
                if (ff.inCut(id)){
                    queue.enqueue(s);
                }
            }
        }
        if (queue.isEmpty()){
            return null;
        }else{
            return queue;
        }
    }
}