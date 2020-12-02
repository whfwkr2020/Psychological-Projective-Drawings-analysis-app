package packageName;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Server {

    public static void main(String args[]) {
        ServerSocket serverSocket = null;
        String temp = "empty";
        try {
            // 서버소켓을 생성하여 7777번 포트와 결합(bind)시킨다.
            serverSocket = new ServerSocket(7777);
            System.out.println(getTime() + "서버가 준비되었습니다.");
//            Socket socket = serverSocket.accept();


        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {

                System.out.println(getTime() + "연결요청을 기다립니다.");
                // 서버소켓은 클라이언트의 연결요청이 올 때까지
                // 실행을 멈추고 계속 기다린다.
                // 클라이언트의 연결요청이 오면 클라이언트 소켓과 통신할
                // 새로운 소켓을 생성한다.
                Socket socket = serverSocket.accept();
                System.out.println(getTime() + socket.getInetAddress()
                        + "로부터 연결요청이 들어왔습니다.");

                OutputStream out = socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(out);
                // 원격 소켓(remote socket)에 데이터를 보낸다.
                dos.writeUTF("[Notice] Test Message1 from Server.");
                System.out.println(getTime() + "데이터를 전송했습니다.");
                dos.flush();

                // 스트림과 소켓을 닫아준다.

                Receiver receiver = new Receiver(socket, temp);
                receiver.start();

//                dos.close();
//                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    // 현재시간을 문자열로 반환하는 함수
    static String getTime() {
        SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
        return f.format(new Date());
    }


}

// 이미지 받고 분석 후 알고르즘 실
class Receiver extends Thread {
    Socket socket;
    DataInputStream dis = null;
    FileOutputStream fos = null;
    BufferedOutputStream bos = null;
    String fileNM;
    String name;
    PrintWriter out;
    String txtResult = "";
    int count;
    int score = 0;
//
//    public void setTxtResult(String txtResult) {
//        this.txtResult = txtResult;
//    }
//
//    public String getTxtResult() {
//        return txtResult;
//    }

    public Receiver(Socket socket, String txtResult) {

        this.txtResult = txtResult;
        this.socket = socket;
    }

    //@Override
    public void run() {
        try {
            dis = new DataInputStream(socket.getInputStream());
            String type = dis.readUTF();
            name = type;
            if (type.equals("file")) {
                String result = fileWrite(dis);
                System.out.println("result : " + result);
                writeText(fileNM, "file");
            }
            // 수정해야할 부분
            else if (type.equals("PITR")) {
                String result = fileWrite(dis);
                System.out.println("result : " + result);
                writeText(fileNM, "PITR");
            } else if (type.equals("House")) {
                System.out.println("hello");
                String result = fileWrite(dis);
                System.out.println("result : " + result);
                writeText(fileNM, "House");
            } else if (type.equals("Tree")) {
                System.out.println("hello");
                String result = fileWrite(dis);
                System.out.println("result : " + result);
                writeText(fileNM, "Tree");
            } else if (type.equals("Person")) {
                System.out.println("hello");
                String result = fileWrite(dis);
                System.out.println("result : " + result);
                writeText(fileNM, "Person");
            } else if (type.equals("result")) {
                sleep(3000);
                OutputStream out = socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(out);
                // 원격 소켓(remote socket)에 데이터를 보낸다.
                dos.writeUTF(readResultText());
                System.out.println("보냈다");
                dos.flush();
                dos.close();

            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {

            try {
                dis.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    private String fileWrite(DataInputStream dis) {
        String result;
//        String filePath = "/home/lee/darknet/test";
//        String filePath = "C:/Users/Lee/Desktop";
//        String filePath = "C:/Users/dltkd/OneDrive/바탕 화면";
       String filePath = "/Users/hs/seniorProject";

        try {
            System.out.println("파일 수신 작업을 시작합니다.");

            String fileNm = dis.readUTF();
            fileNM = fileNm;
            System.out.println("파일명 " + fileNm + "을 전송받았습니다.");

            File file = new File(filePath + "/" + fileNm + ".png");
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            System.out.println(fileNm + "파일을 생성하였습니다.");

            int len;
            int size = 4096;
            byte[] data = new byte[size];
            while ((len = dis.read(data)) != -1) {
                bos.write(data, 0, len);
            }

            System.out.println("끝");

            result = "Success";

            System.out.println("파일 수신 작업을 완료했습니다.");
            System.out.println("받은 파일 사이즈 : " + file.length());
            //darknet(); 이미지를 저장하고 먼저해야되는거 아닌가? (의문)
        } catch (IOException e) {
            e.printStackTrace();
            result = "Error";
        } finally {
            try {
                bos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return result;
    }

    private void resultText(String result) {
        // 결과
        String txt = score + "/" + result;
        // 결과 text address
//        String fileName = "C:/Users/dltkd/OneDrive/바탕 화면/analysis.txt";
//        String fileName = "C:/Users/Lee/Desktop/analysis.txt";
        String fileName = "/Users/hs/seniorProject/analysis.txt";
        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(fileName, false));
            fw.write(txt);
            fw.flush();
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String readResultText() {
        String result = "";
        try {
            //파일 객체 생성
//            File file = new File("C:/Users/Lee/Desktop/analysis.txt");
        	File file = new File("/Users/hs/seniorProject/analysis.txt");
            //입력 스트림 생성
            FileReader filereader = new FileReader(file);
            //입력 버퍼 생성
            BufferedReader bufReader = new BufferedReader(filereader);
            String line = "";
            while ((line = bufReader.readLine()) != null) {
                result = line;
            }

            bufReader.close();
            return result;
        } catch (FileNotFoundException e) {
            // TODO: handle exception
            return "fail";
        } catch (IOException e) {
            System.out.println(e);
            return "fail";
        }

    }


    private void writeText(String fileNM, String type) {
        // image path
//        String txt = "/home/lee/darknet/test/" + fileNM + ".png";
//        String txt = "C:/Users/Lee/Desktop/" + fileNM + ".png";
    	String txt = "/Users/hs/seniorProject/" + fileNM + ".png";
    	
        // train text address
//        String fileName = "C:/Users/Lee/Desktop/train.txt";
    	String fileName = "/Users/hs/seniorProject/train.txt";
        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(fileName, false));

            fw.write(txt);
            fw.flush();
            fw.close();

            //darknet(); 여기에 들어가야되는거 아닌가? (의문)

            if (type.equals("PITR")) {
                analysisPITR();
            } else if (type.equals("House")) {
                analysisHouse();
            } else if (type.equals("Tree")) {
                analysisTree();
            } else if (type.equals("Person")) {
                analysisPerson();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void analysisHouse() throws IOException {

        // object list
        String[] object = {"apple", "arm", "bird", "circle", "door", "ear", "eye", "fence", "flower", "grapes", "grass", "leaf", "mouth", "nose", "sun", "tree"};
        // object detection result
        String[] result = (String[]) readResult();
        Object[] obj = new Object[count];
        //parsing
        String[] parsing;
        int num = 0;
        while (result[num] != null) {
            System.out.println(result[num]);
            parsing = result[num].split(": ");
            for (int j = 0; j < parsing.length; j++) {
                parsing[j] = parsing[j].trim();
            }
            String[] w = parsing[4].split("   ");
            String[] h = parsing[5].split("\\)");
            obj[num] = new Object(parsing[0].trim(), Float.parseFloat(w[0]), Float.parseFloat(h[0]));
            parsing = null;
            num++;
        }

        int door = 0;
        int sun = 0;
        int grass = 0;
        int fence = 0;

        // House 객체 여부
        for (int i = 0; i < obj.length; i++) {
            if (obj[i].category.equals("door")) {
                door++;
            } else if (obj[i].category.equals("sun")) {
                sun++;
            } else if (obj[i].category.equals("grass")) {
                grass++;
            } else if (obj[i].category.equals("fence")) {
                fence++;
            }
        }
        System.out.println("hello");
        txtResult = houseReco(door, sun, grass, fence);
//        System.out.println(txtResult);
        resultText(txtResult);
        // try catch to send result

    }


    private String houseReco(int d, int s, int g, int f) {
        String result = "";
        if (d < 1) {
            result += ("고립 위축 ");
            score++;
        } else if (d == 1) {
            result += ("개방적 ");
        } else {
            result += ("은둔적 ");
            score++;
        }
        if (s == 1) {
            result += ("의존적 ");
            score++;
        } else if (s > 1) {
            result += ("애정욕구 ");
            score++;
        } else {
            result += ("자립적 ");
        }
        if (g > 5) {
            result += ("안정감부족 ");
            score++;
        } else {
            result += ("안정적 ");
        }
        if (f > 1) {
            score++;
            result += ("방어적 ");
        } else {
            result += ("개방적 ");
        }

        // 최종
        if (score == 4) {
            result += ("/다른 사람이 자기 자신의 삶, 세계 안에 들어오는 것에 " +
                    "대해서 불안감 혹은 저항감을 느끼며 자신만의 세계에 고립되고 위축되어 있음을 의미한다. " +
                    " 사회적 접근 가능성이 과다함을 의미하여 사회적인 인정이나 수용에" +
                    " 지나치게 의존적이거나 타인과의 친밀한 관계에 지나친 비중을 두거나 과도하게 예민해 한다.");
        } else if (score == 3) {
            result += ("/다른 사람들과 관계를 맺고 싶은 욕구도 있지만 다른 한편으로는 이에 대한 거부감, 두려움," +
                    " 불편감 등의 양가 감정을 느끼고 있을 가능성이 있다.");
        } else if (score == 2) {
            result += ("/강력한 부모와 같은 자기 대상 존재를 갈망하고 있음을 암시할 수 있다. 아동의 경우 발달적으로" +
                    " 미성숙하므로 이러한 양상이 나타나는 것이 일반적이나 태양을 지나치게 강조해서 그릴 경우는 강한 애정 " +
                    "욕구 및 이에 대한 좌절감을 암시할 수 있다.");
        } else if (score == 1) {
            result += ("/자기를 돌보거나 지배하는 강력한 부모와 같은 자기 대상을 경험하고 있음을 의미할 수 있다.");
        } else {
            result += ("/정상적이며 문제되는 부분이 없어보인다.");
        }

        System.out.println("///////////////////////");
        System.out.println(score);
        return result;
    }

    private void analysisTree() throws IOException {
        String txtResult;
        // object list
        String[] object = {"apple", "arm", "bird", "circle", "door", "ear", "eye", "fence", "flower", "grapes", "grass", "leaf", "mouth", "nose", "sun", "tree"};
        // object detection result
        String[] result = (String[]) readResult();
        Object[] obj = new Object[count];
        //parsing
        String[] parsing;
        int num = 0;
        while (result[num] != null) {
            System.out.println(result[num]);
            parsing = result[num].split(": ");
            for (int j = 0; j < parsing.length; j++) {
                parsing[j] = parsing[j].trim();
            }
            String[] w = parsing[4].split("   ");
            String[] h = parsing[5].split("\\)");
            obj[num] = new Object(parsing[0].trim(), Float.parseFloat(w[0]), Float.parseFloat(h[0]));
            parsing = null;
            num++;
        }

        int apple = 0;
        int grapes = 0;
        int bird = 0;
        int flower = 0;
        int leaf = 0;
        // Tree 객체 여부
        for (int i = 0; i < obj.length; i++) {
            if (obj[i].category.equals("apple")) {
                apple++;
            } else if (obj[i].category.equals("grapes")) {
                grapes++;
            } else if (obj[i].category.equals("bird")) {
                bird++;
            } else if (obj[i].category.equals("flower")) {
                flower++;
            }
        }
        txtResult = treeReco(apple, grapes, bird, flower, leaf);

        OutputStream out = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(out);
        // 원격 소켓(remote socket)에 데이터를 보낸다.
        dos.writeUTF(txtResult);

    }

    private String treeReco(int a, int g, int b, int f, int l) {
        String result = "";
        if (a + g > 2) {
            score++;
            result += ("애정욕구 ");
        } else {
            result += ("자립적 ");
        }
        if (b == 1) {
            score++;
            result += ("안정감부족 ");
        } else {
            result += ("안정적 ");
        }
        if (f > 0) {
            score++;
            result += ("무력함 ");
        } else {
            result += ("활기참 ");
        }
        if (l > 2) {
            score++;
            result += ("좌절감 ");
        } else {
            result += ("성취감 ");
        }

        // 최종
        if (score == 4) {
            result += ("/심리적으로 유약함이나 무력감, 위축감을 갖고 있으며 손상되고 고갈된 자아의 힘을 회복, 보상하고 싶은 욕구가 나타난다.");
        } else if (score == 3) {
            result += ("/심리적인 불안을 겪고 있거나 의존적인 성향일 가능성이 높아 정신적으로 독립하려는 노력이 필요하다. 외상이나 자아 손상감이 나타난다.");
        } else if (score == 2) {
            result += ("/성장 과정에서 경험한 외상적 사건, 자아의 상처를 갖고 있다");
        } else if (score == 1) {
            result += ("/대인관계에서의 좌절감과 이로 인한 정서적 고통감을 갖고 있다.");
        } else {
            result += ("/정상적이며 문제되는 부분이 없어보인다.");
        }

        return result;
    }

    private void analysisPerson() throws IOException {
        String txtResult;
        // object list
        String[] object = {"apple", "arm", "bird", "circle", "door", "ear", "eye", "fence", "flower", "grapes", "grass", "leaf", "mouth", "nose", "sun", "tree"};
        // object detection result
        String[] result = (String[]) readResult();
        Object[] obj = new Object[count];
        //parsing
        String[] parsing;
        int num = 0;
        while (result[num] != null) {
            System.out.println(result[num]);
            parsing = result[num].split(": ");
            for (int j = 0; j < parsing.length; j++) {
                parsing[j] = parsing[j].trim();
            }
            String[] w = parsing[4].split("   ");
            String[] h = parsing[5].split("\\)");
            obj[num] = new Object(parsing[0].trim(), Float.parseFloat(w[0]), Float.parseFloat(h[0]));
            parsing = null;
            num++;
        }

        int ear = 0;
        int nose = 0;
        int mouth = 0;
        int arm = 0;
        int eye = 0;
        // Tree 객체 여부
        for (int i = 0; i < obj.length; i++) {
            if (obj[i].category.equals("ear")) {
                ear++;
            } else if (obj[i].category.equals("nose")) {
                nose++;
            } else if (obj[i].category.equals("mouth")) {
                mouth++;
            } else if (obj[i].category.equals("arm")) {
                arm++;
            } else if (obj[i].category.equals("eye")) {
                eye++;
            }
        }
        txtResult = personReco(ear, nose, mouth, arm, eye);

        OutputStream out = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(out);
        // 원격 소켓(remote socket)에 데이터를 보낸다.
        dos.writeUTF(txtResult);
    }

    private String personReco(int ear, int n, int m, int a, int e) {
        String result = "";
        if (ear == 1) {
            score++;
            result += ("회피적태도 ");
        } else if (ear == 0) {
            score++;
            result += ("두려움 ");
        } else {
            result += ("자립적 ");
        }
        if (n == 0) {
            score++;
            result += ("위축감 ");
        } else if (n == 1) {
            result += ("안정적 ");
        }
        if (m < 1) {
            score++;
            result += ("의사소통거부 ");
        }
        if (a != 2) {
            score++;
            result += ("죄의식 ");
        }
        if (e == 1) {
            score++;
            result += ("회피적태도 ");
        } else if (e != 2) {
            score++;
            result += ("소극적 ");
        }
        // 최종
        if (score == 5) {
            result += ("/때때로 동성연애자에게 나타나며, 비평에 극도로 민감한 신경증 환자 성에대한 무언가 갈등이 있으며 남성적인 것을 거부하며 거세불안이 있고 동성애 경향이 있을 가능성이 있다.");
        } else if (score == 4) {
            result += ("/죄의식, 극단적 우울증, 일반적인 무력감, 환경에 대한 불만, 강한 철회 경향 등을 나타난다. 사회적 교류에 대해 접근 회피의 양가감정, 사회적 불안 등을 반영 열등감, 부적절감, 낮은 자존감, 불안, 수줍음과 위축, 과도한 자기 억제, 사회적 철수 경향, 낮은 자아강도, 의존적 경향등이 나타난다.");
        } else if (score == 3) {
            result += ("/대인관계에 대한 두려움, 위축감, 회피적 태도를 나타낸다. 상당한 심리 신체적 천식의 상태이며 우울 상태의 가능성이 높고 타인들과 의사소통하는 것을 원하지 않는다. 공격적, 행동화 경향, 과장적 경향, 조증상태, 과도한 자신감, 자아팽창, 부적절감을 보상 또는 억압 방어 등이 나타난다.");
        } else if (score == 2) {
            result += ("/애정과 관심에 대한 욕구가 강하거나 대인관계에서 지나치게 예민함을 보이며 사회적 교류에 대해 접근 회피의 양가감정, 사회적 불안 등이 나타난다.");
        } else if (score == 1) {
            result += ("/애정 욕구의 강한 거부, 심한 죄의식, 천식 환자, 우울 감정을 갖고 있다.");
        } else {
            result += ("/정상적이며 문제되는 부분이 없어보인다.");
        }
        return result;
    }

    private Serializable readResult() {
        String[] object = new String[100];

        try {
            //파일 객체 생성
//            File file = new File("C:/Users/Lee/Desktop/result.txt");
        	File file = new File("/Users/hs/seniorProject/result.txt");
            //입력 스트림 생성
            FileReader filereader = new FileReader(file);
            //입력 버퍼 생성
            BufferedReader bufReader = new BufferedReader(filereader);
            String line = "";
            int cnt = 0;
            while ((line = bufReader.readLine()) != null) {
                if (cnt > 0) {
                    object[cnt - 1] = line;
                    cnt++;
                }
                if (line.contains("milli-seconds.")) {
                    cnt++;
                }
            }
            count = cnt - 1;

            bufReader.close();
            return object;
        } catch (FileNotFoundException e) {
            // TODO: handle exception
            return "fail";
        } catch (IOException e) {
            System.out.println(e);
            return "fail";
        }
    }

    private void analysisPITR() throws IOException {
        String txtResult;
        // object list
        String[] object = {"apple", "arm", "bird", "circle", "door", "ear", "eye", "fence", "flower", "grapes", "grass", "leaf", "mouth", "nose", "sun", "tree"};
        // object detection result
        String[] result = (String[]) readResult();
        Object[] obj = new Object[count];
        //parsing
        String[] parsing;
        int num = 0;
        while (result[num] != null) {
            System.out.println(result[num]);
            parsing = result[num].split(": ");
            for (int j = 0; j < parsing.length; j++) {
                parsing[j] = parsing[j].trim();
            }
            String[] w = parsing[4].split("   ");
            String[] h = parsing[5].split("\\)");
            obj[num] = new Object(parsing[0].trim(), Float.parseFloat(w[0]), Float.parseFloat(h[0]));
            parsing = null;
            num++;
        }

        int ear = 0;
        int nose = 0;
        int mouth = 0;
        int arm = 0;
        int eye = 0;
        // Tree 객체 여부
        for (int i = 0; i < obj.length; i++) {
            if (obj[i].category.equals("ear")) {
                ear++;
            } else if (obj[i].category.equals("nose")) {
                nose++;
            } else if (obj[i].category.equals("mouth")) {
                mouth++;
            } else if (obj[i].category.equals("arm")) {
                arm++;
            } else if (obj[i].category.equals("eye")) {
                eye++;
            }
        }
        txtResult = PITRReco(ear, nose, mouth, arm, eye);

        OutputStream out = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(out);
        // 원격 소켓(remote socket)에 데이터를 보낸다.
        dos.writeUTF(txtResult);

    }

    private String PITRReco(int ear, int n, int m, int a, int e) {
        String result = "";
        if (ear == 1) {
            result += ("회피적태도 ");
        } else if (ear == 0) {
            result += ("두려움 ");
        } else {
            result += ("자립적 ");
        }
        if (n == 0) {
            result += ("위축감 ");
        } else if (n == 1) {
            result += ("안정적 ");
        }
        if (m < 1) {
            result += ("의사소통거부 ");
        }
        if (a != 2) {
            result += ("죄의식 ");
        }
        if (e == 1) {
            result += ("회피적태도 ");
        } else if (e != 2) {
            result += ("소극적 ");
        }
        return result;
    }

    private void darknet() throws IOException {
        //////  step 4 yolo execute and received result  //////
//        BufferedReader trainReader = new BufferedReader(new FileReader("/home/lee/darknet/data/train.txt"));
    	BufferedReader trainReader = new BufferedReader(new FileReader("/Users/hs/seniorProject/train.txt"));
        String trainData = "";

        String trainLine;
        while ((trainLine = trainReader.readLine()) != null) {
            trainData += trainLine;
        }
        trainData = trainData.trim();

        Process process = new ProcessBuilder("/home/lee/darknet/darknet",
                "detector", "test", "/home/lee/darknet/data/obj.data", "/home/lee/darknet/yolo-obj.cfg",
                "/home/lee/darknet/backup/yolo-obj_12000.weights",
                "-dont_show", "-ext_output", trainData).start();
        System.out.println(process.pid());
        OutputStreamWriter writer = new OutputStreamWriter(process.getOutputStream());
        writer.write(trainData + "\n");
        writer.flush();

        while (process.isAlive()) {
        }
        process.onExit().thenRun((Runnable) () -> {
            String buffer = "";
            String line = "";
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                    buffer += line + "\n";
                }
//                FileOutputStream outputStream = new FileOutputStream("/home/lee/darknet/result.txt");
                FileOutputStream outputStream = new FileOutputStream("/Users/hs/seniorProject/result.txt");
                outputStream.write(buffer.getBytes());
                outputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}

class Object {
    String category;
    float width;
    float height;
    float size;
    String result;


    public Object(String category, float width, float height) {
        this.category = category;
        this.width = width;
        this.height = height;
        this.size = width * height;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
