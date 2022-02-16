package Log;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Log {
	
	public static Writer writer;
	public static String fileName;
	public static String path;
	
	public static Charset utf8 = StandardCharsets.UTF_8;
	
	public static void create() {
		Date dataHoraAtual = new Date();
		String hora = new SimpleDateFormat("HH:mm").format(dataHoraAtual);
		
		fileName = "genetic_algorithm_" + hora + ".log";
		path = "logs/" + fileName;
	}
	
	public static void write(String data) {
		
		try (Writer writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(path, true), utf8)
        )) {
            writer.write(data + "\n");
       } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
		
	}
}