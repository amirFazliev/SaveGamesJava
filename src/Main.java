import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    static List<File> listFiles = new LinkedList<>();
    public static void main(String[] args) {
        GameProgress gp1 = new GameProgress(10,10,10,10.1);
        GameProgress gp2 = new GameProgress(20,20,20,20.2);
        GameProgress gp3 = new GameProgress(30,30,30,30.3);

        File gp1File = new File("C:/Games/savegames/save1.dat");
        File gp2File = new File("C:/Games/savegames/save2.dat");
        File gp3File = new File("C:/Games/savegames/save3.dat");
        textWriteFiles(gp1File);
        textWriteFiles(gp2File);
        textWriteFiles(gp3File);

        saveGame(gp1File, gp1);
        saveGame(gp2File, gp2);
        saveGame(gp3File, gp3);

        File zipFile = new File("C:/Games/savegames/zip.zip");
        textWriteFiles(zipFile);

        zipFiles(zipFile, listFiles);

        for (File file : listFiles) {
            file.delete();
        }
    }

    public static void saveGame(File file, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            listFiles.add(file);
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(File file, List <File> list) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(file))) {
            for (File listString : list) {
                FileInputStream fis = new FileInputStream(listString.toString());
                ZipEntry entry = new ZipEntry(listString.getName());
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void textWriteFiles (File file) {
        try {
            if (file.createNewFile()) {
                System.out.println("Файл " + file.getName() + " создан в папке " + file.getParent());
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

