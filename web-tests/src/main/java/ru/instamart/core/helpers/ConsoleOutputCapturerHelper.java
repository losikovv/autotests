package instamart.core.helpers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

public class ConsoleOutputCapturerHelper {
    private ByteArrayOutputStream baos;
    private PrintStream previous;
    private boolean capturing;

    /**Функция стартует перехват записей в консоль*/
    public void start() {
        if (capturing) {
            return;
        }

        capturing = true;
        previous = System.out;
        baos = new ByteArrayOutputStream();

        OutputStream outputStreamCombiner =
                new OutputStreamCombiner(Arrays.asList(previous, baos));
        PrintStream custom = new PrintStream(outputStreamCombiner);

        System.setOut(custom);
    }
    /**Функция останавливает перехват записей в консоль и возвращает записанную стрингу*/
    public String stop(){
        try {
            if (!capturing) {
                return "";
            }

            System.setOut(previous);

            String capturedValue = baos.toString();

            baos = null;
            previous = null;
            capturing = false;

            return capturedValue;
        }catch (NullPointerException ex){
            return "!!! can not capture logs for this test!!!";
        }
    }

    private static class OutputStreamCombiner extends OutputStream {
        private List<OutputStream> outputStreams;

        public OutputStreamCombiner(List<OutputStream> outputStreams) {
            this.outputStreams = outputStreams;
        }

        public void write(int b) throws IOException {
            for (OutputStream os : outputStreams) {
                os.write(b);
            }
        }

        public void flush() throws IOException {
            for (OutputStream os : outputStreams) {
                os.flush();
            }
        }

        public void close() throws IOException {
            for (OutputStream os : outputStreams) {
                os.close();
            }
        }
    }
}
