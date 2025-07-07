package ar.edu.et32;

public class RenglonTuti implements Comparable<RenglonTuti> {
    private char letra;
    private String color;
    private String animal;
    private String objetos;
    private String alimento;

    public RenglonTuti(char letra, String color, String animal, String objetos, String alimento) {
        this.letra = Character.toUpperCase(letra);
        this.color = color;
        this.animal = animal;
        this.objetos = objetos;
        this.alimento = alimento;
    }

    public char getLetra() { return letra; }
    public String getColor() { return color; }
    public String getAnimal() { return animal; }
    public String getObjetos() { return objetos; }
    public String getAlimento() { return alimento; }

    public String toCSVString() {
        return letra + ";" + color + ";" + animal + ";" + objetos + ";" + alimento;
    }

    public int compareTo(RenglonTuti otro) {
        return Character.compare(this.letra, otro.letra);
    }
}