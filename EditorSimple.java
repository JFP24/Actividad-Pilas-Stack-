import java.util.ArrayList;
import java.util.Scanner;


public class EditorSimple {
//un comit simple
    // ===== Pila manual  (solo Strings) =====
    static class Pila {
        private final ArrayList<String> data = new ArrayList<>();

        void push(String s) { data.add(s); }
        String pop() {
            if (isEmpty()) return null;
            return data.remove(data.size() - 1);
        }
        String peek() {
            if (isEmpty()) return null;
            return data.get(data.size() - 1);
        }
        boolean isEmpty() { return data.isEmpty(); }
    }

    // ===== Estado =====
    private final ArrayList<String> documento = new ArrayList<>();
    private final Pila pilaUndo = new Pila();
    private final Pila pilaRedo = new Pila();

    // ===== Acciones =====
    void escribir(String linea) {
        documento.add(linea);
        pilaUndo.push(linea);
        // Si hago una acción nueva, vacío Redo
        while (!pilaRedo.isEmpty()) pilaRedo.pop();
        System.out.println("✔ Línea agregada.");
    }

    void deshacer() {
        String ultima = pilaUndo.pop();
        if (ultima == null) {
            System.out.println("⛔ Nada que deshacer.");
            return;
        }
        // quita del documento la última línea
        if (!documento.isEmpty()) {
            documento.remove(documento.size() - 1);
        }
        // guarda en Redo para poder rehacer
        pilaRedo.push(ultima);
        System.out.println("↩ Deshecho.");
    }

    void rehacer() {
        String rec = pilaRedo.pop();
        if (rec == null) {
            System.out.println("⛔ Nada que rehacer.");
            return;
        }
        documento.add(rec);
        pilaUndo.push(rec);
        System.out.println("↪ Rehecho.");
    }

    void mostrar() {
        System.out.println("\n=== TEXTO ACTUAL ===");
        if (documento.isEmpty()) {
            System.out.println("(vacío)");
        } else {
            for (int i = 0; i < documento.size(); i++) {
                System.out.printf("%2d | %s%n", i + 1, documento.get(i));
            }
        }
        System.out.println("====================\n");
    }

    // ===== Menú =====
    static void menu() {
        System.out.println("===== EDITOR SENCILLO (Undo/Redo) =====");
        System.out.println("1) Escribir línea");
        System.out.println("2) Deshacer (Undo)");
        System.out.println("3) Rehacer (Redo)");
        System.out.println("4) Mostrar texto");
        System.out.println("0) Salir");
        System.out.print("Opción: ");
    }

    public static void main(String[] args) {
        EditorSimple app = new EditorSimple();
        Scanner sc = new Scanner(System.in);
        boolean run = true;

        while (run) {
            menu();
            String op = sc.nextLine().trim();

            switch (op) {
                case "1":
                    System.out.print("Escribe la línea: ");
                    String linea = sc.nextLine();
                    app.escribir(linea);
                    break;
                case "2":
                    app.deshacer();
                    break;
                case "3":
                    app.rehacer();
                    break;
                case "4":
                    app.mostrar();
                    break;
                case "0":
                    run = false;
                    System.out.println("¡Chao!");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
        sc.close();
    }
}
