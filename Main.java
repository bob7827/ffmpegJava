import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

class ShellCommandForffmpeg {
    public static void main(String[] args)
    {
        try {
            System.out.println(
                    "*** constructing command line, process builder ****");

            ProcessBuilder pb
                    = new ProcessBuilder("ffmpeg", "-i", "hello.mp4", "-i", "torso.png",
                    "-filter_complex", "[0:v]scale=640:360:force_original_aspect_ratio=decrease[fg];[1][fg]overlay=(W-w)/10:(H-h)/10",
                    "outputZZxxz4.mp4");

            pb.directory(
                    new File(System.getProperty("user.home")));
            // It will throw and exception
            Process process = pb.start();

            StringBuilder output = new StringBuilder();
            BufferedReader reader
                    = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();

            if (exitVal == 0) {
                System.out.println("Hopefully, successfully generated composited mp4...");
                System.out.println(output);
                System.exit(0);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println(
                    "*** process builder exception ***");
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(
                    "*** process builder  interruption ****");
        }
    }
}