import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Map<String, byte[]> types;

    static {
        types = new HashMap<>();
        types.put("mp3", new byte[]{0x49, 0x44, 0x33});
        types.put("png", new byte[]{(byte) 0x89, 0x50, 0x4E});
        types.put("gif", new byte[]{0x47, 0x49, 0x46});
        types.put("jpg", new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF});
    }

    public static String detectFileType(String filePath) {
        try (FileInputStream in = new FileInputStream(filePath)) {
            byte[] header = in.readNBytes(3);
            if (header.length < 3) return "Неизвестно";

            for (Map.Entry<String, byte[]> entry : types.entrySet()) {
                if (Arrays.equals(header, entry.getValue())) {
                    return entry.getKey();
                }
            }
            return "Неизвестно";
        } catch (IOException e) {
            return "Ошибка чтения: " + e.getMessage();
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in, java.nio.charset.StandardCharsets.UTF_8)) {
            System.out.print("Введите путь к файлу: ");
            if (!scanner.hasNextLine()) return;

            String path = scanner.nextLine().trim();
            if (path.length() >= 2 && path.startsWith("\"") && path.endsWith("\"")) {
                path = path.substring(1, path.length() - 1);
            }
            if (path.isBlank()) {
                System.out.println("Путь не указан.");
                return;
            }

            System.out.println("Тип файла: " + detectFileType(path));
        }
    }
}
