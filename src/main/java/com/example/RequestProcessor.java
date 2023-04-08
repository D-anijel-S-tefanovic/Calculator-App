package com.example;

import java.io.*;
import java.net.Socket;
import org.json.JSONObject;
import java.util.ArrayList;

class RequestProcessor implements Runnable {
    private Socket socket = null;
    private OutputStream os = null;
    private BufferedReader in = null;
    private DataInputStream dis = null;
    private String msgToClient = "HTTP/1.1 200 OK\n"
            + "Server: HTTP server/0.1\n"
            + "Access-Control-Allow-Origin: *\n\n";
    private JSONObject jsonObject = new JSONObject();

    public RequestProcessor(Socket Socket) {
        super();
        try {
            socket = Socket;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            os = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        // write your code here

        Integer leftOperand = null;
        Integer rightOperand = null;
        String operator = null;
        String expression = null;
        Integer result = null;
        StringBuilder str = new StringBuilder();
        GetParams queryParams = new GetParams();
        ArrayList<String> valueParams = new ArrayList<String>();

        try {
            String initialCleansing = in.readLine().replace("GET", "");
            String finalCleansing = initialCleansing.replace("HTTP/1.1", "");
            valueParams = queryParams.parseQueryString(finalCleansing);
            leftOperand = Integer.parseInt(valueParams.get(0));
            rightOperand = Integer.parseInt(valueParams.get(1));
            operator = valueParams.get(2).toString();

            str.append(leftOperand);
            str.append(" ");

            if (operator.contains("+")) {
                str.append(operator);
                result = leftOperand + rightOperand;

            } else if (operator.contains("-")) {
                str.append(operator);
                result = leftOperand - rightOperand;

            } else if (operator.contains("*")) {
                str.append(operator);
                result = leftOperand * rightOperand;

            } else if (operator.contains("/")) {
                str.append(operator);
                result = leftOperand / rightOperand;

            } else {
                str.append(operator);
                Integer initalCalculation = Math.round(leftOperand / rightOperand) * rightOperand;
                result = leftOperand - initalCalculation;
            }
            str.append(rightOperand);
            expression = str.toString();

            jsonObject.put("expression", expression);
            jsonObject.put("result", result);

            // end of your code
            String response = msgToClient + jsonObject.toString();
            os.write(response.getBytes());
            os.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
